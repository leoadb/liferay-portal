/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.report.definitions.web.portlet.action;

import com.liferay.data.engine.rest.client.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.client.dto.v1_0.DataLayout;
import com.liferay.data.engine.rest.client.resource.v1_0.DataDefinitionResource;
import com.liferay.data.engine.rest.client.resource.v1_0.DataLayoutResource;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStorageLink;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapter;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterSaveRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterSaveResponse;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterTracker;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.report.definitions.model.ReportDefinition;
import com.liferay.report.definitions.service.ReportDefinitionLocalService;
import com.liferay.report.definitions.web.constants.ReportDefinitionPortletKeys;
import com.liferay.report.definitions.web.portlet.action.forms.AvailableColumnsForm;

import java.util.HashMap;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ReportDefinitionPortletKeys.PORTLET_NAME,
		"mvc.command.name=abc"
	},
	service = MVCActionCommand.class
)
public class SaveReportDefinitionMVCActionCommand
	extends BaseTransactionalMVCActionCommand {

	@Override
	protected void doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		LiferayPortletURL portletURL = PortletURLFactoryUtil.create(
			actionRequest, themeDisplay.getPpid(), PortletRequest.RENDER_PHASE);

		String mvcRenderCommandName = ParamUtil.getString(
			actionRequest, "mvcRenderCommandName");

		portletURL.setParameter("mvcRenderCommandName", mvcRenderCommandName);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		try {
			ReportDefinition reportDefinition = _saveReportDefinition(
				actionRequest);

			portletURL.setParameter(
				"reportDefinitionId",
				String.valueOf(reportDefinition.getReportDefinitionId()));

			portletURL.setParameter("redirect", redirect);

			actionRequest.setAttribute(WebKeys.REDIRECT, portletURL.toString());
		}
		catch (PortalException pe) {
			SessionErrors.add(actionRequest, pe.getClass(), pe);
		}
	}

	private ReportDefinition _addReportDefinition(ActionRequest actionRequest)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			ReportDefinition.class.getName(), actionRequest);

		long columnsDDMContentId = _saveAvailableAndSortColumns(
			actionRequest, serviceContext);

		long dataDefinitionId = _saveDataDefinition(
			ParamUtil.getLong(actionRequest, "dataDefinitionId"),
			ParamUtil.getString(actionRequest, "dataDefinition"),
			ParamUtil.getString(actionRequest, "description"),
			serviceContext.getScopeGroupId(), serviceContext.getLanguageId(),
			ParamUtil.getString(actionRequest, "name"));

		long dataLayoutId = _saveDataLayout(
			dataDefinitionId, ParamUtil.getLong(actionRequest, "dataLayoutId"),
			ParamUtil.getString(actionRequest, "dataLayout"),
			ParamUtil.getString(actionRequest, "description"),
			serviceContext.getLanguageId(),
			ParamUtil.getString(actionRequest, "name"));

		return _reportDefinitionLocalService.addReportDefinition(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			ParamUtil.getString(actionRequest, "name"),
			ParamUtil.getString(actionRequest, "description"), dataDefinitionId,
			dataLayoutId, columnsDDMContentId, serviceContext);
	}

	private DDMStorageAdapter _getDDMStorageAdapter() {
		return _ddmStorageAdapterTracker.getDDMStorageAdapter(
			StorageType.JSON.toString());
	}

	private long _saveAvailableAndSortColumns(
			ActionRequest actionRequest, ServiceContext serviceContext)
		throws Exception {

		DDMForm ddmForm = DDMFormFactory.create(AvailableColumnsForm.class);

		DDMFormValues ddmFormValues = _ddmFormValuesFactory.create(
			actionRequest, ddmForm);

		DDMStorageAdapterSaveRequest.Builder builder =
			DDMStorageAdapterSaveRequest.Builder.newBuilder(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				ddmFormValues);

		DDMStorageAdapterSaveRequest ddmStorageAdapterSaveRequest =
			builder.withUuid(
				serviceContext.getUuid()
			).withClassName(
				DDMStorageLink.class.getName()
			).build();

		DDMStorageAdapter ddmStorageAdapter = _getDDMStorageAdapter();

		DDMStorageAdapterSaveResponse ddmStorageAdapterSaveResponse =
			ddmStorageAdapter.save(ddmStorageAdapterSaveRequest);

		return ddmStorageAdapterSaveResponse.getPrimaryKey();
	}

	private long _saveDataDefinition(
			long dataDefinitionId, String dataDefinitionJSON,
			String description, long groupId, String languageId, String name)
		throws Exception {

		//TODO deserialize definition
		DataDefinition dataDefinition = new DataDefinition();

		dataDefinition.setId(dataDefinitionId);

		if (Validator.isNotNull(description)) {
			dataDefinition.setDescription(
				new HashMap() {
					{
						put(languageId, description);
					}
				});
		}

		if (Validator.isNotNull(name)) {
			dataDefinition.setName(
				new HashMap() {
					{
						put(languageId, name);
					}
				});
		}

		dataDefinition = DataDefinitionResource.postSiteDataDefinition(
			groupId, dataDefinition);

		return dataDefinition.getId();
	}

	private long _saveDataLayout(
			long dataDefinitionId, long dataLayoutId, String dataLayoutJSON,
			String description, String languageId, String name)
		throws Exception {

		//TODO deserialize layout
		DataLayout dataLayout = new DataLayout();

		dataLayout.setId(dataLayoutId);

		if (Validator.isNotNull(description)) {
			dataLayout.setDescription(
				new HashMap() {
					{
						put(languageId, description);
					}
				});
		}

		if (Validator.isNotNull(name)) {
			dataLayout.setName(
				new HashMap() {
					{
						put(languageId, name);
					}
				});
		}

		dataLayout = DataLayoutResource.postDataDefinitionDataLayout(
			dataDefinitionId, dataLayout);

		return dataLayout.getId();
	}

	private ReportDefinition _saveReportDefinition(ActionRequest actionRequest)
		throws Exception {

		long reportDefinitionId = ParamUtil.getLong(
			actionRequest, "reportDefinitionId");

		if (reportDefinitionId == 0) {
			return _addReportDefinition(actionRequest);
		}

		return _updateReportDefinition(actionRequest, reportDefinitionId);
	}

	private ReportDefinition _updateReportDefinition(
			ActionRequest actionRequest, long reportDefinitionId)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			ReportDefinition.class.getName(), actionRequest);

		ReportDefinition reportDefinition =
			_reportDefinitionLocalService.fetchReportDefinition(
				reportDefinitionId);

		long columnsDDMContentId = _saveAvailableAndSortColumns(
			actionRequest, serviceContext);

		_saveDataDefinition(
			reportDefinition.getDataDefinitionId(),
			ParamUtil.getString(actionRequest, "dataDefinition"),
			ParamUtil.getString(actionRequest, "description"),
			serviceContext.getScopeGroupId(), serviceContext.getLanguageId(),
			ParamUtil.getString(actionRequest, "name"));

		_saveDataLayout(
			reportDefinition.getDataDefinitionId(),
			reportDefinition.getDataLayoutId(),
			ParamUtil.getString(actionRequest, "dataLayout"),
			ParamUtil.getString(actionRequest, "description"),
			serviceContext.getLanguageId(),
			ParamUtil.getString(actionRequest, "name"));

		return _reportDefinitionLocalService.updateReportDefinition(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			reportDefinitionId, ParamUtil.getString(actionRequest, "name"),
			ParamUtil.getString(actionRequest, "description"),
			reportDefinition.getDataDefinitionId(),
			reportDefinition.getDataLayoutId(), columnsDDMContentId,
			serviceContext);
	}

	@Reference
	private DDMFormValuesFactory _ddmFormValuesFactory;

	@Reference
	private DDMStorageAdapterTracker _ddmStorageAdapterTracker;

	@Reference
	private ReportDefinitionLocalService _reportDefinitionLocalService;

}
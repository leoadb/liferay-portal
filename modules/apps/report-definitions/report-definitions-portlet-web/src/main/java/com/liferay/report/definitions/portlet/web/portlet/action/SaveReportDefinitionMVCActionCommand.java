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

package com.liferay.report.definitions.portlet.web.portlet.action;

import com.liferay.data.engine.exception.DataDefinitionException;
import com.liferay.data.engine.io.DataDefinitionDeserializer;
import com.liferay.data.engine.io.DataDefinitionDeserializerApplyRequest;
import com.liferay.data.engine.io.DataDefinitionDeserializerApplyResponse;
import com.liferay.data.engine.model.DataDefinition;
import com.liferay.data.engine.model.DataDefinitionColumn;
import com.liferay.data.engine.service.DataDefinitionLocalService;
import com.liferay.data.engine.service.DataDefinitionSaveRequest;
import com.liferay.data.engine.service.DataDefinitionSaveResponse;
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
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.report.definitions.model.ReportDefinition;
import com.liferay.report.definitions.portlet.web.constants.ReportDefinitionPortletKeys;
import com.liferay.report.definitions.portlet.web.portlet.action.forms.AvailableColumnsForm;
import com.liferay.report.definitions.service.ReportDefinitionLocalService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
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

	protected ReportDefinition addReportDefinition(ActionRequest actionRequest)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			ReportDefinition.class.getName(), actionRequest);

		String languageId = serviceContext.getLanguageId();

		long userId = serviceContext.getUserId();
		long groupId = serviceContext.getScopeGroupId();

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		// Data Definition

		String definition = ParamUtil.getString(actionRequest, "definition");

		long dataDefinitionId = _saveDataDefinition(
			languageId, userId, groupId, 0L, name, description, definition);

		// Available Columns

		long columnsDDMContentId = saveAvailableAndSortColumns(
			actionRequest, serviceContext);

		// Report Definition

		return _reportDefinitionLocalService.addReportDefinition(
			userId, groupId, name, description, dataDefinitionId,
			columnsDDMContentId, serviceContext);
	}

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
			ReportDefinition reportDefinition = saveReportDefinition(
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

	protected DDMStorageAdapter getDDMStorageAdapter() {
		return _ddmStorageAdapterTracker.getDDMStorageAdapter(
			StorageType.JSON.toString());
	}

	protected long saveAvailableAndSortColumns(
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

		DDMStorageAdapter ddmStorageAdapter = getDDMStorageAdapter();

		DDMStorageAdapterSaveResponse ddmStorageAdapterSaveResponse =
			ddmStorageAdapter.save(ddmStorageAdapterSaveRequest);

		return ddmStorageAdapterSaveResponse.getPrimaryKey();
	}

	protected ReportDefinition saveReportDefinition(ActionRequest actionRequest)
		throws Exception {

		long reportDefinitionId = ParamUtil.getLong(
			actionRequest, "reportDefinitionId");

		if (reportDefinitionId == 0) {
			return addReportDefinition(actionRequest);
		}

		return updateReportDefinition(actionRequest, reportDefinitionId);
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesFactory(
		DDMFormValuesFactory ddmFormValuesFactory) {

		_ddmFormValuesFactory = ddmFormValuesFactory;
	}

	protected ReportDefinition updateReportDefinition(
			ActionRequest actionRequest, long reportDefinitionId)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			ReportDefinition.class.getName(), actionRequest);

		String languageId = serviceContext.getLanguageId();

		long userId = serviceContext.getUserId();
		long groupId = serviceContext.getScopeGroupId();

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String definition = ParamUtil.getString(actionRequest, "definition");

		ReportDefinition reportDefinition =
			_reportDefinitionLocalService.fetchReportDefinition(
				reportDefinitionId);

		// Available and Sort Columns

		long columnsDDMContentId = saveAvailableAndSortColumns(
			actionRequest, serviceContext);

		// Data Definition

		long dataDefinitionId = reportDefinition.getDataDefinitionId();

		_saveDataDefinition(
			languageId, userId, groupId, dataDefinitionId, name, description,
			definition);

		return _reportDefinitionLocalService.updateReportDefinition(
			userId, groupId, reportDefinitionId, name, description,
			dataDefinitionId, columnsDDMContentId, serviceContext);
	}

	private long _saveDataDefinition(
			String languageId, long userId, long groupId, long dataDefinitionId,
			String name, String description, String definition)
		throws DataDefinitionException {

		DataDefinitionDeserializerApplyRequest
			dataDefinitionDeserializerApplyRequest =
				DataDefinitionDeserializerApplyRequest.Builder.of(definition);

		DataDefinitionDeserializerApplyResponse
			dataDefinitionDeserializerApplyResponse =
				_dataDefinitionDeserializer.apply(
					dataDefinitionDeserializerApplyRequest);

		DataDefinition dataDefinition = new DataDefinition();

		for (DataDefinitionColumn dataDefinitionColumn :
				dataDefinitionDeserializerApplyResponse.
					getDataDefinitionColumns()) {

			dataDefinition.addColumn(dataDefinitionColumn);
		}

		dataDefinition.addName(languageId, name);
		dataDefinition.addDescription(languageId, description);
		dataDefinition.setDataDefinitionId(dataDefinitionId);

		DataDefinitionSaveRequest dataDefinitionSaveRequest =
			DataDefinitionSaveRequest.Builder.of(
				userId, groupId, dataDefinition);

		DataDefinitionSaveResponse dataDefinitionSaveResponse =
			_dataDefinitionLocalService.save(dataDefinitionSaveRequest);

		return dataDefinitionSaveResponse.getDataDefinitionId();
	}

	@Reference(target = "(data.definition.deserializer.type=json)")
	private DataDefinitionDeserializer _dataDefinitionDeserializer;

	@Reference
	private DataDefinitionLocalService _dataDefinitionLocalService;

	private DDMFormValuesFactory _ddmFormValuesFactory;

	@Reference
	private DDMStorageAdapterTracker _ddmStorageAdapterTracker;

	@Reference
	private ReportDefinitionLocalService _reportDefinitionLocalService;

}
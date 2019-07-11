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

package com.liferay.report.definitions.web.internal.portlet.action;

import com.liferay.data.engine.rest.client.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.client.dto.v1_0.DataLayout;
import com.liferay.data.engine.rest.client.resource.v1_0.DataDefinitionResource;
import com.liferay.data.engine.rest.client.resource.v1_0.DataLayoutResource;
import com.liferay.data.engine.rest.client.serdes.v1_0.DataDefinitionSerDes;
import com.liferay.data.engine.rest.client.serdes.v1_0.DataLayoutSerDes;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.report.definitions.model.ReportDefinition;
import com.liferay.report.definitions.service.ReportDefinitionLocalService;
import com.liferay.report.definitions.web.internal.constants.ReportDefinitionPortletKeys;

import java.util.HashMap;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

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
		"mvc.command.name=saveReportDefinition"
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

		DataDefinition dataDefinition = _saveDataDefinition(
			actionRequest, ParamUtil.getLong(actionRequest, "dataDefinitionId"),
			ParamUtil.getString(actionRequest, "dataDefinition"),
			ParamUtil.getString(actionRequest, "description"),
			serviceContext.getScopeGroupId(), serviceContext.getLanguageId(),
			ParamUtil.getString(actionRequest, "name"));

		_saveDataLayout(
			actionRequest, dataDefinition,
			ParamUtil.getLong(actionRequest, "dataLayoutId"),
			ParamUtil.getString(actionRequest, "dataLayout"),
			ParamUtil.getString(actionRequest, "description"),
			serviceContext.getLanguageId(),
			ParamUtil.getString(actionRequest, "name"));

		return _reportDefinitionLocalService.addReportDefinition(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			dataDefinition.getId(), ParamUtil.getString(actionRequest, "name"),
			ParamUtil.getString(actionRequest, "description"),
			ParamUtil.getString(actionRequest, "availableColumns"),
			ParamUtil.getString(actionRequest, "sortColumns"), serviceContext);
	}

	private DataDefinition _saveDataDefinition(
			ActionRequest actionRequest, long dataDefinitionId,
			String dataDefinitionJSON, String description, long groupId,
			String languageId, String name)
		throws Exception {

		DataDefinition dataDefinition = DataDefinitionSerDes.toDTO(
			dataDefinitionJSON);

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

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user = permissionChecker.getUser();

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);

		DataDefinitionResource dataDefinitionResource =
			DataDefinitionResource.builder(
			).authentication(
				user.getEmailAddress(), "test"
			).endpoint(
				_portal.getHost(httpServletRequest),
				httpServletRequest.getServerPort(),
				httpServletRequest.getScheme()
			).build();

		dataDefinition = dataDefinitionResource.postSiteDataDefinition(
			groupId, dataDefinition);

		return dataDefinition;
	}

	private void _saveDataLayout(
			ActionRequest actionRequest, DataDefinition dataDefinition,
			long dataLayoutId, String dataLayoutJSON, String description,
			String languageId, String name)
		throws Exception {

		DataLayout dataLayout = DataLayoutSerDes.toDTO(dataLayoutJSON);

		dataLayout.setId(dataLayoutId);
		dataLayout.setDataLayoutKey(dataDefinition.getDataDefinitionKey());

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

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user = permissionChecker.getUser();

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);

		DataLayoutResource dataLayoutResource = DataLayoutResource.builder(
		).authentication(
			user.getEmailAddress(), "test"
		).endpoint(
			_portal.getHost(httpServletRequest),
			httpServletRequest.getServerPort(), httpServletRequest.getScheme()
		).build();

		dataLayoutResource.postDataDefinitionDataLayout(
			dataDefinition.getId(), dataLayout);
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

		DataDefinition dataDefinition = _saveDataDefinition(
			actionRequest, ParamUtil.getLong(actionRequest, "dataDefinitionId"),
			ParamUtil.getString(actionRequest, "dataDefinition"),
			ParamUtil.getString(actionRequest, "description"),
			serviceContext.getScopeGroupId(), serviceContext.getLanguageId(),
			ParamUtil.getString(actionRequest, "name"));

		_saveDataLayout(
			actionRequest, dataDefinition,
			ParamUtil.getLong(actionRequest, "dataLayoutId"),
			ParamUtil.getString(actionRequest, "dataLayout"),
			ParamUtil.getString(actionRequest, "description"),
			serviceContext.getLanguageId(),
			ParamUtil.getString(actionRequest, "name"));

		return _reportDefinitionLocalService.updateReportDefinition(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			reportDefinitionId, dataDefinition.getId(),
			ParamUtil.getString(actionRequest, "name"),
			ParamUtil.getString(actionRequest, "description"),
			ParamUtil.getString(actionRequest, "availableColumns"),
			ParamUtil.getString(actionRequest, "sortColumns"), serviceContext);
	}

	@Reference
	private Portal _portal;

	@Reference
	private ReportDefinitionLocalService _reportDefinitionLocalService;

}
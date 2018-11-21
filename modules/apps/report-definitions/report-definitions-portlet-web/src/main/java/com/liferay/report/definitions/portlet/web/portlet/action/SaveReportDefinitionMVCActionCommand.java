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

import com.liferay.data.engine.io.DataDefinitionDeserializer;
import com.liferay.data.engine.io.DataDefinitionDeserializerApplyRequest;
import com.liferay.data.engine.io.DataDefinitionDeserializerApplyResponse;
import com.liferay.data.engine.model.DataDefinition;
import com.liferay.data.engine.model.DataDefinitionColumn;
import com.liferay.data.engine.service.DataDefinitionLocalService;
import com.liferay.data.engine.service.DataDefinitionSaveRequest;
import com.liferay.data.engine.service.DataDefinitionSaveResponse;
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

	protected ReportDefinition addReportDefinition(
			PortletRequest portletRequest)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			ReportDefinition.class.getName(), portletRequest);

		String languageId = serviceContext.getLanguageId();

		long userId = serviceContext.getUserId();
		long groupId = serviceContext.getScopeGroupId();

		String name = ParamUtil.getString(portletRequest, "name");
		String description = ParamUtil.getString(portletRequest, "description");

		String definition = ParamUtil.getString(portletRequest, "definition");

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

		DataDefinitionSaveRequest dataDefinitionSaveRequest =
			DataDefinitionSaveRequest.Builder.of(
				userId, groupId, dataDefinition);

		DataDefinitionSaveResponse dataDefinitionSaveResponse =
			_dataDefinitionLocalService.save(dataDefinitionSaveRequest);

		return _reportDefinitionLocalService.addReportDefinition(
			userId, groupId, name, description,
			dataDefinitionSaveResponse.getDataDefinitionId(), serviceContext);
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
				actionRequest, actionResponse);

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

	protected ReportDefinition saveReportDefinition(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long reportDefinitionId = ParamUtil.getLong(
			actionRequest, "reportDefinitionId");

		if (reportDefinitionId == 0) {
			return addReportDefinition(actionRequest);
		}

		return updateReportDefinition(actionRequest, reportDefinitionId);
	}

	protected ReportDefinition updateReportDefinition(
			PortletRequest portletRequest, long reportDefinitionId)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			ReportDefinition.class.getName(), portletRequest);

		String name = ParamUtil.getString(portletRequest, "name");
		String description = ParamUtil.getString(portletRequest, "description");

		return _reportDefinitionLocalService.updateReportDefinition(
			reportDefinitionId, name, description, serviceContext);
	}

	@Reference(target = "(data.definition.deserializer.type=json)")
	private DataDefinitionDeserializer _dataDefinitionDeserializer;

	@Reference
	private DataDefinitionLocalService _dataDefinitionLocalService;

	@Reference
	private ReportDefinitionLocalService _reportDefinitionLocalService;

}
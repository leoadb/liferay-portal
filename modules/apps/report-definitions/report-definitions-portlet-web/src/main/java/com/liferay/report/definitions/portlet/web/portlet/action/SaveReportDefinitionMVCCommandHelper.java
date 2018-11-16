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

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.report.definitions.model.ReportDefinition;
import com.liferay.report.definitions.service.ReportDefinitionLocalService;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true, service = SaveReportDefinitionMVCCommandHelper.class
)
public class SaveReportDefinitionMVCCommandHelper {

	public ReportDefinition saveReportDefinition(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		long reportDefinitionId = ParamUtil.getLong(
			portletRequest, "reportDefinitionId");

		if (reportDefinitionId == 0) {
			return addReportDefinition(portletRequest);
		}

		return updateReportDefinition(portletRequest, reportDefinitionId);
	}

	protected ReportDefinition addReportDefinition(
			PortletRequest portletRequest)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			ReportDefinition.class.getName(), portletRequest);

		long userId = serviceContext.getUserId();
		long groupId = ParamUtil.getLong(portletRequest, "groupId");
		String name = ParamUtil.getString(portletRequest, "name");
		String description = ParamUtil.getString(portletRequest, "description");

		return reportDefinitionLocalService.addReportDefinition(
			userId, groupId, name, description, serviceContext);
	}

	protected ReportDefinition updateReportDefinition(
			PortletRequest portletRequest, long reportDefinitionId)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			ReportDefinition.class.getName(), portletRequest);

		String name = ParamUtil.getString(portletRequest, "name");
		String description = ParamUtil.getString(portletRequest, "description");

		return reportDefinitionLocalService.updateReportDefinition(
			reportDefinitionId, name, description, serviceContext);
	}

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected ReportDefinitionLocalService reportDefinitionLocalService;

	@Reference
	private Portal _portal;

}
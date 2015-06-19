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

package com.liferay.dynamic.data.mapping.web.panel;

import com.liferay.portal.service.PortletLocalService;
import com.liferay.portal.util.PortletKeys;
import com.liferay.productivity.center.panel.BaseControlPanelEntryPanelApp;
import com.liferay.productivity.center.panel.PanelApp;
import com.liferay.productivity.center.panel.constants.PanelCategoryKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"panel.category.key=" + PanelCategoryKeys.SITE_ADMINISTRATION_CONFIGURATION,
		"service.ranking:Integer=300"
	},
	service = PanelApp.class
)
public class PortletDisplayTemplatePanelApp
	extends BaseControlPanelEntryPanelApp {

	@Override
	public String getPortletId() {
		return PortletKeys.PORTLET_DISPLAY_TEMPLATE;
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

}
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

package com.liferay.depot.web.internal.portlet.action;

import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.web.internal.constants.DepotPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"javax.portlet.name=" + DepotPortletKeys.DEPOT_ADMIN,
		"javax.portlet.name=" + DepotPortletKeys.DEPOT_SETTINGS,
		"mvc.command.name=/depot_entry/edit_depot_entry_group_rel"
	},
	service = MVCActionCommand.class
)
public class EditDepotEntryGroupRelMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		try {
			_depotEntryGroupRelLocalService.updateSearchable(
				ParamUtil.getLong(actionRequest, "depotEntryGroupRelId"),
				ParamUtil.getBoolean(actionRequest, "searchable"));
		}
		catch (PortalException pe) {
			throw new PortletException(pe);
		}
	}

	@Reference
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

}
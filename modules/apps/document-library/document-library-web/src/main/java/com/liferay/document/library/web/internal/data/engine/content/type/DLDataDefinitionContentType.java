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

package com.liferay.document.library.web.internal.data.engine.content.type;

import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portlet.documentlibrary.constants.DLConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(
	immediate = true, property = "content.type=document-library",
	service = DataDefinitionContentType.class
)
public class DLDataDefinitionContentType implements DataDefinitionContentType {

	@Override
	public long getClassNameId() {
		return _portal.getClassNameId(DLFileEntryMetadata.class);
	}

	@Override
	public String getContentType() {
		return "document-library";
	}

	@Override
	public String getPortletResourceName() {
		return DLConstants.RESOURCE_NAME;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long companyId, long groupId,
			String resourceName, long primKey, long userId, String actionId)
		throws PortalException {

		if (_portletResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(), groupId,
				ActionKeys.MANAGE)) {

			return true;
		}

		if (permissionChecker.hasOwnerPermission(
				companyId, resourceName, primKey, userId, actionId)) {

			return true;
		}

		if (actionId.equals("ADD_DATA_RECORD") ||
			actionId.equals("UPDATE_DATA_RECORD")) {

			return permissionChecker.hasPermission(
				groupId, resourceName, primKey, ActionKeys.UPDATE);
		}

		if (actionId.equals("DELETE_DATA_RECORD")) {
			return permissionChecker.hasPermission(
				groupId, resourceName, primKey, ActionKeys.DELETE);
		}

		if (actionId.equals("EXPORT_DATA_RECORDS") ||
			actionId.equals("VIEW_DATA_RECORD")) {

			return permissionChecker.hasPermission(
				groupId, resourceName, primKey, ActionKeys.VIEW);
		}

		return permissionChecker.hasPermission(
			groupId, resourceName, primKey, actionId);
	}

	@Override
	public boolean hasPortletPermission(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException {

		if (actionId.equals("ADD_DATA_DEFINITION") ||
			actionId.equals("ADD_DATA_RECORD_COLLECTION")) {

			return _portletResourcePermission.contains(
				permissionChecker, groupId, "ADD_STRUCTURE");
		}

		return _portletResourcePermission.contains(
			permissionChecker, groupId, actionId);
	}

	@Reference
	private Portal _portal;

	@Reference(target = "(resource.name=" + DLConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

}
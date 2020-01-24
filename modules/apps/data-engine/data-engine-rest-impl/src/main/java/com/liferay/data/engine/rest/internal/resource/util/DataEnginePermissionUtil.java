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

package com.liferay.data.engine.rest.internal.resource.util;

import com.liferay.data.engine.constants.DataEngineConstants;
import com.liferay.data.engine.rest.internal.constants.DataActionKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;

/**
 * @author Brian Wing Shun Chan
 */
public class DataEnginePermissionUtil {

	public static void checkPermission(
			String actionId, GroupLocalService groupLocalService, Long siteId)
		throws PortalException {

		if (hasManageDataDefinitionPermission(groupLocalService, siteId)) {
			return;
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		Group group = groupLocalService.fetchGroup(siteId);

		if ((group != null) && group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		if (!permissionChecker.hasPermission(
				group, DataEngineConstants.RESOURCE_NAME, siteId, actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, DataEngineConstants.RESOURCE_NAME, siteId,
				actionId);
		}
	}

	public static boolean hasManageDataDefinitionPermission(
		GroupLocalService groupLocalService, Long siteId) {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		Group group = groupLocalService.fetchGroup(siteId);

		if ((group != null) && group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		return permissionChecker.hasPermission(
			group, DataEngineConstants.RESOURCE_NAME, siteId,
			DataActionKeys.MANAGE_DATA_DEFINITION);
	}

}
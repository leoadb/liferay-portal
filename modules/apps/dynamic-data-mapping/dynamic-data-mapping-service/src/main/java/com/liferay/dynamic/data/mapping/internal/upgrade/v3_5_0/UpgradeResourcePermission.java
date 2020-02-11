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

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_5_0;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Leonardo Barros
 */
public class UpgradeResourcePermission extends UpgradeProcess {

	public UpgradeResourcePermission(
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			_resourcePermissionLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property nameProperty = PropertyFactoryUtil.forName("name");

				dynamicQuery.add(nameProperty.eq("com.liferay.journal"));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(ResourcePermission resourcePermission) -> {
				if (resourcePermission.hasActionId("ADD_STRUCTURE")) {
					resourcePermission.addResourceAction("ADD_DATA_DEFINITION");
					resourcePermission.addResourceAction(
						"ADD_DATA_RECORD_COLLECTION");
				}
			});

		actionableDynamicQuery.performActions();
	}

	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}
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

package com.liferay.data.engine.service.impl;

import com.liferay.data.engine.constants.DataActionKeys;
import com.liferay.data.engine.internal.util.DataEnginePermissionUtil;
import com.liferay.data.engine.model.DEDataLayout;
import com.liferay.data.engine.service.DEDataLayoutLocalService;
import com.liferay.data.engine.service.base.DEDataLayoutServiceBaseImpl;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Brian Wing Shun Chan
 * @see DEDataLayoutServiceBaseImpl
 */
@Component(
	property = {
		"json.web.service.context.name=de",
		"json.web.service.context.path=DEDataLayout"
	},
	service = AopService.class
)
public class DEDataLayoutServiceImpl extends DEDataLayoutServiceBaseImpl {

	@Override
	public DEDataLayout addDataLayout(
			DEDataLayout deDataLayout, ServiceContext serviceContext)
		throws PortalException {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			deDataLayout.getDeDataDefinitionId());

		DataEnginePermissionUtil.checkPermission(
			DataActionKeys.ADD_DATA_LAYOUT, _groupLocalService,
			ddmStructure.getGroupId());

		return _deDataLayoutLocalService.addDataLayout(
			deDataLayout, serviceContext);
	}

	@Override
	public DEDataLayout getDataLayout(long deDataLayoutId)
		throws PortalException {

		_modelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), deDataLayoutId,
			ActionKeys.VIEW);

		return _deDataLayoutLocalService.getDataLayout(deDataLayoutId);
	}

	@Override
	public DEDataLayout getDataLayout(long groupId, String deDataLayoutKey)
		throws Exception {

		DEDataLayout deDataLayout = _deDataLayoutLocalService.getDataLayout(
			groupId, deDataLayoutKey);

		if (deDataLayout != null) {
			_modelResourcePermission.check(
				PermissionThreadLocal.getPermissionChecker(),
				deDataLayout.getDeDataLayoutId(), ActionKeys.VIEW);
		}

		return deDataLayout;
	}

	@Override
	public DEDataLayout updateDataLayout(
			DEDataLayout deDataLayout, ServiceContext serviceContext)
		throws PortalException {

		_modelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			deDataLayout.getDeDataLayoutId(), ActionKeys.UPDATE);

		return _deDataLayoutLocalService.updateDataLayout(
			deDataLayout, serviceContext);
	}

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DEDataLayoutLocalService _deDataLayoutLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.data.engine.model.DEDataLayout)"
	)
	private volatile ModelResourcePermission<DEDataLayout>
		_modelResourcePermission;

}
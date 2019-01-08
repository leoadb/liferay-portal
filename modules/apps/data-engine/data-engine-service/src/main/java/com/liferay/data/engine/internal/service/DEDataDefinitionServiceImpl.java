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

package com.liferay.data.engine.internal.service;

import com.liferay.data.engine.constants.DEActionKeys;
import com.liferay.data.engine.exception.DEDataDefinitionException;
import com.liferay.data.engine.internal.executor.DEDataDefinitionDeleteRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataDefinitionGetRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataDefinitionSaveModelPermissionsRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataDefinitionSavePermissionsRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataDefinitionSaveRequestExecutor;
import com.liferay.data.engine.internal.security.permission.DEDataEnginePermissionSupport;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.service.DEDataDefinitionDeleteRequest;
import com.liferay.data.engine.service.DEDataDefinitionDeleteResponse;
import com.liferay.data.engine.service.DEDataDefinitionGetRequest;
import com.liferay.data.engine.service.DEDataDefinitionGetResponse;
import com.liferay.data.engine.service.DEDataDefinitionSaveModelPermissionsRequest;
import com.liferay.data.engine.service.DEDataDefinitionSaveModelPermissionsResponse;
import com.liferay.data.engine.service.DEDataDefinitionSavePermissionsRequest;
import com.liferay.data.engine.service.DEDataDefinitionSavePermissionsResponse;
import com.liferay.data.engine.service.DEDataDefinitionSaveRequest;
import com.liferay.data.engine.service.DEDataDefinitionSaveResponse;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DEDataDefinitionService.class)
public class DEDataDefinitionServiceImpl implements DEDataDefinitionService {

	@Override
	public DEDataDefinitionDeleteResponse execute(
			DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequest)
		throws DEDataDefinitionException {

		try {
			long deDataDefinitionId =
				deDataDefinitionDeleteRequest.getDEDataDefinitionId();

			_modelResourcePermission.check(
				getPermissionChecker(), deDataDefinitionId, ActionKeys.DELETE);

			return deDataDefinitionDeleteRequestExecutor.execute(
				deDataDefinitionDeleteRequest);
		}
		catch (NoSuchStructureException nsse) {
			_log.error(nsse, nsse);

			throw new DEDataDefinitionException.NoSuchDataDefinition(
				deDataDefinitionDeleteRequest.getDEDataDefinitionId(), nsse);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new DEDataDefinitionException(e);
		}
	}

	@Override
	public DEDataDefinitionGetResponse execute(
			DEDataDefinitionGetRequest deDataDefinitionGetRequest)
		throws DEDataDefinitionException {

		try {
			long deDataDefinitionId =
				deDataDefinitionGetRequest.getDEDataDefinitionId();

			_modelResourcePermission.check(
				getPermissionChecker(), deDataDefinitionId, ActionKeys.VIEW);

			return deDataDefinitionGetRequestExecutor.execute(
				deDataDefinitionGetRequest);
		}
		catch (NoSuchStructureException nsse) {
			_log.error(nsse, nsse);

			throw new DEDataDefinitionException.NoSuchDataDefinition(
				deDataDefinitionGetRequest.getDEDataDefinitionId(), nsse);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new DEDataDefinitionException(e);
		}
	}

	@Override
	public DEDataDefinitionSaveModelPermissionsResponse execute(
			DEDataDefinitionSaveModelPermissionsRequest
				deDataDefinitionSaveModelPermissionsRequest)
		throws DEDataDefinitionException {

		try {
			checkPermission(
				deDataDefinitionSaveModelPermissionsRequest.getScopedGroupId(),
				ActionKeys.DEFINE_PERMISSIONS, getPermissionChecker());

			return deDataDefinitionSaveModelPermissionsRequestExecutor.execute(
				deDataDefinitionSaveModelPermissionsRequest);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataDefinitionException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new DEDataDefinitionException(e);
		}
	}

	@Override
	public DEDataDefinitionSavePermissionsResponse execute(
			DEDataDefinitionSavePermissionsRequest
				deDataDefinitionSavePermissionsRequest)
		throws DEDataDefinitionException {

		try {
			checkPermission(
				deDataDefinitionSavePermissionsRequest.getScopedGroupId(),
				ActionKeys.DEFINE_PERMISSIONS, getPermissionChecker());

			return deDataDefinitionSavePermissionsRequestExecutor.execute(
				deDataDefinitionSavePermissionsRequest);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataDefinitionException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (DEDataDefinitionException dedde) {
			_log.error(dedde, dedde);

			throw dedde;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new DEDataDefinitionException(e);
		}
	}

	@Override
	public DEDataDefinitionSaveResponse execute(
			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest)
		throws DEDataDefinitionException {

		DEDataDefinition deDataDefinition =
			deDataDefinitionSaveRequest.getDEDataDefinition();

		try {
			long deDataDefinitionId = deDataDefinition.getDEDataDefinitionId();

			if (deDataDefinitionId == 0) {
				checkPermission(
					deDataDefinitionSaveRequest.getGroupId(),
					DEActionKeys.ADD_DATA_DEFINITION_ACTION,
					getPermissionChecker());
			}
			else {
				_modelResourcePermission.check(
					getPermissionChecker(), deDataDefinitionId,
					ActionKeys.UPDATE);
			}

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponse =
				deDataDefinitionSaveRequestExecutor.execute(
					deDataDefinitionSaveRequest);

			return DEDataDefinitionSaveResponse.Builder.of(
				deDataDefinitionSaveResponse.getDEDataDefinitionId());
		}
		catch (DEDataDefinitionException dedde) {
			_log.error(dedde, dedde);

			throw dedde;
		}
		catch (NoSuchStructureException nsse) {
			_log.error(nsse, nsse);

			throw new DEDataDefinitionException.NoSuchDataDefinition(
				deDataDefinition.getDEDataDefinitionId(), nsse);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataDefinitionException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new DEDataDefinitionException(e);
		}
	}

	protected void checkPermission(
			long classPK, String actionId, PermissionChecker permissionChecker)
		throws PortalException {

		String resourceName = DEDataEnginePermissionSupport.RESOURCE_NAME;

		if (!deDataEnginePermissionSupport.contains(
				permissionChecker, resourceName, classPK, actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, resourceName, classPK, actionId);
		}
	}

	protected PermissionChecker getPermissionChecker()
		throws PrincipalException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker == null) {
			throw new PrincipalException(
				"Permission checker is not initialized");
		}

		return permissionChecker;
	}

	@Reference(
		target = "(model.class.name=com.liferay.data.engine.model.DEDataDefinition)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<DEDataDefinition> modelResourcePermission) {

		_modelResourcePermission = modelResourcePermission;
	}

	@Reference
	protected DEDataDefinitionDeleteRequestExecutor
		deDataDefinitionDeleteRequestExecutor;

	@Reference
	protected DEDataDefinitionGetRequestExecutor
		deDataDefinitionGetRequestExecutor;

	@Reference
	protected DEDataDefinitionSaveModelPermissionsRequestExecutor
		deDataDefinitionSaveModelPermissionsRequestExecutor;

	@Reference
	protected DEDataDefinitionSavePermissionsRequestExecutor
		deDataDefinitionSavePermissionsRequestExecutor;

	@Reference
	protected DEDataDefinitionSaveRequestExecutor
		deDataDefinitionSaveRequestExecutor;

	@Reference
	protected DEDataEnginePermissionSupport deDataEnginePermissionSupport;

	@Reference
	protected Portal portal;

	private static final Log _log = LogFactoryUtil.getLog(
		DEDataDefinitionServiceImpl.class);

	private ModelResourcePermission<DEDataDefinition> _modelResourcePermission;

}
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

import com.liferay.data.engine.exception.DataStorageException;
import com.liferay.data.engine.service.DataStorageDeleteRequest;
import com.liferay.data.engine.service.DataStorageDeleteResponse;
import com.liferay.data.engine.service.DataStorageExportRequest;
import com.liferay.data.engine.service.DataStorageExportResponse;
import com.liferay.data.engine.service.DataStorageGetCountRequest;
import com.liferay.data.engine.service.DataStorageGetCountResponse;
import com.liferay.data.engine.service.DataStorageGetListRequest;
import com.liferay.data.engine.service.DataStorageGetListResponse;
import com.liferay.data.engine.service.DataStorageGetRequest;
import com.liferay.data.engine.service.DataStorageGetResponse;
import com.liferay.data.engine.service.DataStorageLocalService;
import com.liferay.data.engine.service.DataStorageSaveRequest;
import com.liferay.data.engine.service.DataStorageSaveResponse;
import com.liferay.data.engine.service.DataStorageService;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DataStorageService.class)
public class DataStorageServiceImpl implements DataStorageService {

	@Override
	public DataStorageDeleteResponse delete(
			DataStorageDeleteRequest dataStorageDeleteRequest)
		throws DataStorageException {

		try {
			//TODO check permission

			return dataStorageLocalService.delete(dataStorageDeleteRequest);
		}
		catch (DataStorageException dse)
		{
			throw dse;
		}
		catch (Exception e) {
			throw new DataStorageException(e);
		}
	}

	@Override
	public DataStorageExportResponse export(
			DataStorageExportRequest dataStorageExportRequest)
		throws DataStorageException {

		try {
			//TODO check permission

			return dataStorageLocalService.export(dataStorageExportRequest);
		}
		catch (DataStorageException dse)
		{
			throw dse;
		}
		catch (Exception e) {
			throw new DataStorageException(e);
		}
	}

	@Override
	public DataStorageGetResponse get(
			DataStorageGetRequest dataStorageGetRequest)
		throws DataStorageException {

		try {
			//TODO check permission

			return dataStorageLocalService.get(dataStorageGetRequest);
		}
		catch (DataStorageException dse)
		{
			throw dse;
		}
		catch (Exception e) {
			throw new DataStorageException(e);
		}
	}

	@Override
	public DataStorageGetCountResponse getCount(
			DataStorageGetCountRequest dataStorageGetCountRequest)
		throws DataStorageException {

		try {
			//TODO check permission

			return dataStorageLocalService.getCount(dataStorageGetCountRequest);
		}
		catch (DataStorageException dse)
		{
			throw dse;
		}
		catch (Exception e) {
			throw new DataStorageException(e);
		}
	}

	@Override
	public DataStorageGetListResponse getList(
			DataStorageGetListRequest dataStorageGetListRequest)
		throws DataStorageException {

		try {
			//TODO check permission

			return dataStorageLocalService.getList(dataStorageGetListRequest);
		}
		catch (DataStorageException dse)
		{
			throw dse;
		}
		catch (Exception e) {
			throw new DataStorageException(e);
		}
	}

	@Override
	public DataStorageSaveResponse save(
			DataStorageSaveRequest dataStorageSaveRequest)
		throws DataStorageException {

		try {
			return dataStorageLocalService.save(dataStorageSaveRequest);
		}
		catch (DataStorageException dse)
		{
			throw dse;
		}
		catch (Exception e) {
			throw new DataStorageException(e);
		}
	}

	protected PermissionChecker getPermissionChecker()
		throws PrincipalException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker == null) {
			throw new PrincipalException("PermissionChecker not initialized");
		}

		return permissionChecker;
	}

	@Reference
	protected DataStorageLocalService dataStorageLocalService;

}
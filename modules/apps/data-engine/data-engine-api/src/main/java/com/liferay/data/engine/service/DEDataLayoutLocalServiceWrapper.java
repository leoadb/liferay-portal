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

package com.liferay.data.engine.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides a wrapper for {@link DEDataLayoutLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DEDataLayoutLocalService
 * @generated
 */
@ProviderType
public class DEDataLayoutLocalServiceWrapper
	implements DEDataLayoutLocalService,
			   ServiceWrapper<DEDataLayoutLocalService> {

	public DEDataLayoutLocalServiceWrapper(
		DEDataLayoutLocalService deDataLayoutLocalService) {

		_deDataLayoutLocalService = deDataLayoutLocalService;
	}

	@Override
	public com.liferay.data.engine.model.DEDataLayout addDataLayout(
			com.liferay.data.engine.model.DEDataLayout deDataLayout,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _deDataLayoutLocalService.addDataLayout(
			deDataLayout, serviceContext);
	}

	@Override
	public com.liferay.data.engine.model.DEDataLayout getDataLayout(
			long deDataLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _deDataLayoutLocalService.getDataLayout(deDataLayoutId);
	}

	@Override
	public com.liferay.data.engine.model.DEDataLayout getDataLayout(
			long groupId, String deDataLayoutKey)
		throws Exception {

		return _deDataLayoutLocalService.getDataLayout(
			groupId, deDataLayoutKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _deDataLayoutLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.data.engine.model.DEDataLayout updateDataLayout(
			com.liferay.data.engine.model.DEDataLayout deDataLayout,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _deDataLayoutLocalService.updateDataLayout(
			deDataLayout, serviceContext);
	}

	@Override
	public DEDataLayoutLocalService getWrappedService() {
		return _deDataLayoutLocalService;
	}

	@Override
	public void setWrappedService(
		DEDataLayoutLocalService deDataLayoutLocalService) {

		_deDataLayoutLocalService = deDataLayoutLocalService;
	}

	private DEDataLayoutLocalService _deDataLayoutLocalService;

}
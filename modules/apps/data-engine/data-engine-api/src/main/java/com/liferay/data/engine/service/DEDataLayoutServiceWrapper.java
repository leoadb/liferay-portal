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
 * Provides a wrapper for {@link DEDataLayoutService}.
 *
 * @author Brian Wing Shun Chan
 * @see DEDataLayoutService
 * @generated
 */
@ProviderType
public class DEDataLayoutServiceWrapper
	implements DEDataLayoutService, ServiceWrapper<DEDataLayoutService> {

	public DEDataLayoutServiceWrapper(DEDataLayoutService deDataLayoutService) {
		_deDataLayoutService = deDataLayoutService;
	}

	@Override
	public com.liferay.data.engine.model.DEDataLayout addDataLayout(
			com.liferay.data.engine.model.DEDataLayout deDataLayout,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _deDataLayoutService.addDataLayout(deDataLayout, serviceContext);
	}

	@Override
	public com.liferay.data.engine.model.DEDataLayout getDataLayout(
			long deDataLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _deDataLayoutService.getDataLayout(deDataLayoutId);
	}

	@Override
	public com.liferay.data.engine.model.DEDataLayout getDataLayout(
			long groupId, String deDataLayoutKey)
		throws Exception {

		return _deDataLayoutService.getDataLayout(groupId, deDataLayoutKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _deDataLayoutService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.data.engine.model.DEDataLayout updateDataLayout(
			com.liferay.data.engine.model.DEDataLayout deDataLayout,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _deDataLayoutService.updateDataLayout(
			deDataLayout, serviceContext);
	}

	@Override
	public DEDataLayoutService getWrappedService() {
		return _deDataLayoutService;
	}

	@Override
	public void setWrappedService(DEDataLayoutService deDataLayoutService) {
		_deDataLayoutService = deDataLayoutService;
	}

	private DEDataLayoutService _deDataLayoutService;

}
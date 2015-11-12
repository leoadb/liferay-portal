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

package com.liferay.dynamic.data.mapping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDMDataProviderInstanceService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderInstanceService
 * @generated
 */
@ProviderType
public class DDMDataProviderInstanceServiceWrapper
	implements DDMDataProviderInstanceService,
		ServiceWrapper<DDMDataProviderInstanceService> {
	public DDMDataProviderInstanceServiceWrapper(
		DDMDataProviderInstanceService ddmDataProviderInstanceService) {
		_ddmDataProviderInstanceService = ddmDataProviderInstanceService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _ddmDataProviderInstanceService.getOSGiServiceIdentifier();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public DDMDataProviderInstanceService getWrappedDDMDataProviderInstanceService() {
		return _ddmDataProviderInstanceService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedDDMDataProviderInstanceService(
		DDMDataProviderInstanceService ddmDataProviderInstanceService) {
		_ddmDataProviderInstanceService = ddmDataProviderInstanceService;
	}

	@Override
	public DDMDataProviderInstanceService getWrappedService() {
		return _ddmDataProviderInstanceService;
	}

	@Override
	public void setWrappedService(
		DDMDataProviderInstanceService ddmDataProviderInstanceService) {
		_ddmDataProviderInstanceService = ddmDataProviderInstanceService;
	}

	private DDMDataProviderInstanceService _ddmDataProviderInstanceService;
}
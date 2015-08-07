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

package com.liferay.dynamic.data.mapping.internal;

import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.StorageEngineManager;
import com.liferay.portlet.dynamicdatamapping.StorageFieldRequiredException;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true)
public class StorageEngineManagerImpl implements StorageEngineManager {

	@Override
	public long create(
			long companyId, long ddmStructureId, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			com.liferay.dynamic.data.mapping.storage.DDMFormValues _ddmFormValues = BeanPropertiesUtil.deepCopyProperties(ddmFormValues);
			
			return _storageEngine.create(
				companyId, ddmStructureId, _ddmFormValues, serviceContext);
		}
		catch (PortalException pe) {
			throw translate(pe);
		} 
		catch (Exception e) {

			if (_log.isDebugEnabled()) {
				_log.debug(e,e);
			}

			return 0;
		}
	}

	@Override
	public void deleteByClass(long classPK) throws PortalException {
		_storageEngine.deleteByClass(classPK);
	}

	@Override
	public void deleteByDDMStructure(long ddmStructureId)
		throws PortalException {

		_storageEngine.deleteByDDMStructure(ddmStructureId);
	}

	@Override
	public DDMFormValues getDDMFormValues(long classPK) throws PortalException {
		com.liferay.dynamic.data.mapping.storage.DDMFormValues _ddmFormValues = _storageEngine.getDDMFormValues(classPK);

		try {
			return BeanPropertiesUtil.deepCopyProperties(_ddmFormValues);
		}
		catch (Exception e) {

			if (_log.isDebugEnabled()) {
				_log.debug(e,e);
			}

			return null;
		}
	}

	@Override
	public DDMFormValues getDDMFormValues(
			long ddmStructureId, String fieldNamespace,
			ServiceContext serviceContext)
		throws PortalException {
		com.liferay.dynamic.data.mapping.storage.DDMFormValues _ddmFormValues = _ddm.getDDMFormValues(
				ddmStructureId, fieldNamespace, serviceContext);

		try {
			return BeanPropertiesUtil.deepCopyProperties(_ddmFormValues);
		}
		catch (Exception e) {

			if (_log.isDebugEnabled()) {
				_log.debug(e,e);
			}

			return null;
		}
	}

	@Override
	public void update(
			long classPK, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			
			com.liferay.dynamic.data.mapping.storage.DDMFormValues _ddmFormValues = BeanPropertiesUtil.deepCopyProperties(ddmFormValues);
			
			_storageEngine.update(classPK, _ddmFormValues, serviceContext);
		}
		catch (PortalException pe) {
			throw translate(pe);
		}
		catch (Exception e) {

			if (_log.isDebugEnabled()) {
				_log.debug(e,e);
			}

		}
	}

	@Reference
	protected void setDDM(DDM ddm) {
		_ddm = ddm;
	}

	@Reference
	protected void setStorageEngine(StorageEngine storageEngine) {
		_storageEngine = storageEngine;
	}

	protected PortalException translate(PortalException portalException) {
		if (portalException instanceof
				com.liferay.dynamic.data.mapping.exception.
					StorageFieldRequiredException) {

			return new StorageFieldRequiredException(
				portalException.getMessage(), portalException.getCause());
		}

		return portalException;
	}

	private DDM _ddm;
	private StorageEngine _storageEngine;
	private static final Log _log =
			LogFactoryUtil.getLog(StorageEngineManagerImpl.class);
}
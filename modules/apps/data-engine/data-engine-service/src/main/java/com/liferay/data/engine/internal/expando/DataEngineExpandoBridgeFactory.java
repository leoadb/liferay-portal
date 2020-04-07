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

package com.liferay.data.engine.internal.expando;

import com.liferay.data.engine.nativeobject.DataEngineNativeObject;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactory;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Jeyvison Nascimento
 */
@Component(immediate = true, service = ExpandoBridgeFactory.class)
public class DataEngineExpandoBridgeFactory implements ExpandoBridgeFactory {

	@Override
	public ExpandoBridge getExpandoBridge(long companyId, String className) {
		if (_dataEngineNativeObjects.containsKey(className)) {
			return new DataEngineExpandoBridgeImpl(
				className, 0, companyId, _companyLocalService,
				_ddlRecordLocalService, _groupLocalService, _userLocalService);
		}

		return new ExpandoBridgeImpl(companyId, className);
	}

	@Override
	public ExpandoBridge getExpandoBridge(
		long companyId, String className, long classPK) {

		if (_dataEngineNativeObjects.containsKey(className)) {
			return new DataEngineExpandoBridgeImpl(
				className, classPK, companyId, _companyLocalService,
				_ddlRecordLocalService, _groupLocalService, _userLocalService);
		}

		return new ExpandoBridgeImpl(companyId, className, classPK);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDataEngineNativeObject(
		DataEngineNativeObject dataEngineNativeObject) {

		_dataEngineNativeObjects.put(
			dataEngineNativeObject.getClassName(),
			dataEngineNativeObject.getClassName());
	}

	protected void removeDataEngineNativeObject(
		DataEngineNativeObject dataEngineNativeObject) {

		_dataEngineNativeObjects.remove(dataEngineNativeObject.getClassName());
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	private final Map<String, String> _dataEngineNativeObjects =
		new ConcurrentHashMap<>();

	@Reference
	private DDLRecordLocalService _ddlRecordLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
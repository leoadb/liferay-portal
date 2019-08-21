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

package com.liferay.dynamic.data.mapping.internal.data.provider;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderTracker;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DDMDataProviderTracker.class)
public class DDMDataProviderTrackerImpl implements DDMDataProviderTracker {

	@Override
	public DDMDataProvider getDDMDataProvider(String type) {
		return _ddmDataProviderTypes.get(type);
	}

	@Override
	public DDMDataProvider getDDMDataProviderByInstanceId(String instanceId) {
		return _ddmDataProviderInstanceIds.get(instanceId);
	}

	@Override
	public Set<String> getDDMDataProviderTypes() {
		return _ddmDataProviderTypes.keySet();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setDDMDataProvider(
		DDMDataProvider ddmDataProvider, Map<String, Object> properties) {

		_ddmDataProviderInstanceIds.put(
			MapUtil.getString(properties, "ddm.data.provider.instance.id"),
			ddmDataProvider);

		_ddmDataProviderTypes.put(
			MapUtil.getString(properties, "ddm.data.provider.type"),
			ddmDataProvider);
	}

	protected void unsetDDMDataProvider(
		DDMDataProvider ddmDataProvider, Map<String, Object> properties) {

		_ddmDataProviderInstanceIds.remove(
			MapUtil.getString(properties, "ddm.data.provider.instance.id"));

		_ddmDataProviderTypes.remove(
			MapUtil.getString(properties, "ddm.data.provider.type"));
	}

	private final Map<String, DDMDataProvider> _ddmDataProviderInstanceIds =
		new ConcurrentHashMap<>();
	private final Map<String, DDMDataProvider> _ddmDataProviderTypes =
		new ConcurrentHashMap<>();

}
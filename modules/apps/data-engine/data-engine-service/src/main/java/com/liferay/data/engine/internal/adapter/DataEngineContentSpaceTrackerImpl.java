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

package com.liferay.data.engine.internal.adapter;

import com.liferay.data.engine.adapter.DataEngineContentSpace;
import com.liferay.data.engine.adapter.DataEngineContentSpaceTracker;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;
import java.util.TreeMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DataEngineContentSpaceTracker.class)
public class DataEngineContentSpaceTrackerImpl
	implements DataEngineContentSpaceTracker {

	@Override
	public DataEngineContentSpace getContentSpace(String name) {
		return _dataEngineContentSpaces.getOrDefault(
			name, new DefaultDataEngineContentSpace());
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDataEngineContentSpace(
		DataEngineContentSpace dataEngineContentSpace,
		Map<String, Object> properties) {

		String name = MapUtil.getString(
			properties, "data.engine.content.space.name");

		_dataEngineContentSpaces.put(name, dataEngineContentSpace);
	}

	@Deactivate
	protected void deactivate() {
		_dataEngineContentSpaces.clear();
	}

	protected void removeDataEngineContentSpace(
		DataEngineContentSpace dataEngineContentSpace,
		Map<String, Object> properties) {

		String name = MapUtil.getString(
			properties, "data.engine.content.space.name");

		_dataEngineContentSpaces.remove(name);
	}

	private final Map<String, DataEngineContentSpace> _dataEngineContentSpaces =
		new TreeMap<>();

	private static class DefaultDataEngineContentSpace
		implements DataEngineContentSpace {

		@Override
		public long getClassNameId() {
			return 0;
		}

	}

}
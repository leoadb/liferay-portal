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

package com.liferay.data.engine.internal.io;

import com.liferay.data.engine.io.DataDefinitionDeserializer;
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
@Component(immediate = true, service = DataDefinitionDeserializerTracker.class)
public class DataDefinitionDeserializerTracker {

	public DataDefinitionDeserializer getDataDefinitionDeserializer(
		String type) {

		return _dataDefinitionDeserializers.get(type);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDataDefinitionDeserializer(
		DataDefinitionDeserializer dataDefinitionDeserializer,
		Map<String, Object> properties) {

		String type = MapUtil.getString(
			properties, "data.definition.deserializer.type");

		_dataDefinitionDeserializers.put(type, dataDefinitionDeserializer);
	}

	@Deactivate
	protected void deactivate() {
		_dataDefinitionDeserializers.clear();
	}

	protected void removeDataDefinitionDeserializer(
		DataDefinitionDeserializer dataDefinitionDeserializer,
		Map<String, Object> properties) {

		String type = MapUtil.getString(
			properties, "data.definition.deserializer.type");

		_dataDefinitionDeserializers.remove(type);
	}

	private final Map<String, DataDefinitionDeserializer>
		_dataDefinitionDeserializers = new TreeMap<>();

}
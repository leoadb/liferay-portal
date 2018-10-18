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

import com.liferay.data.engine.io.DataDefinitionSerializer;
import com.liferay.data.engine.io.DataDefinitionSerializerApplyRequest;
import com.liferay.data.engine.io.DataDefinitionSerializerApplyResponse;
import com.liferay.data.engine.model.DataDefinitionColumn;
import com.liferay.data.engine.model.DataDefinitionColumnType;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "data.definition.serializer.type=json",
	service = DataDefinitionSerializer.class
)
public class DataDefinitionJSONSerializer implements DataDefinitionSerializer {

	@Override
	public DataDefinitionSerializerApplyResponse apply(
		DataDefinitionSerializerApplyRequest
			dataDefinitionSerializerApplyRequest) {

		List<DataDefinitionColumn> dataDefinitionColumns =
			dataDefinitionSerializerApplyRequest.getDataDefinitionColumns();

		JSONArray jsonArray = jsonFactory.createJSONArray();

		Stream<DataDefinitionColumn> stream = dataDefinitionColumns.stream();

		stream.map(
			this::mapColumn
		).forEach(
			jsonArray::put
		);

		return DataDefinitionSerializerApplyResponse.Builder.of(
			jsonArray.toJSONString());
	}

	protected JSONObject mapColumn(DataDefinitionColumn dataDefinitionColumn) {
		JSONObject jsonObject = jsonFactory.createJSONObject();

		DataDefinitionColumnType dataDefinitionColumnType =
			dataDefinitionColumn.getType();

		jsonObject.put("indexable", dataDefinitionColumn.isIndexable());
		jsonObject.put("localizable", dataDefinitionColumn.isLocalizable());
		jsonObject.put("name", dataDefinitionColumn.getName());
		jsonObject.put("repeatable", dataDefinitionColumn.isRepeatable());
		jsonObject.put("type", dataDefinitionColumnType.getValue());

		setProperty("label", jsonObject, dataDefinitionColumn.getLabel());

		return jsonObject;
	}

	protected void setProperty(
		String property, JSONObject jsonObject, Map<String, String> map) {

		JSONObject languageJSONObject = jsonFactory.createJSONObject();

		Set<Map.Entry<String, String>> entries = map.entrySet();

		Stream<Map.Entry<String, String>> stream = entries.stream();

		stream.forEach(
			entry -> languageJSONObject.put(entry.getKey(), entry.getValue()));

		jsonObject.put(property, languageJSONObject);
	}

	@Reference
	protected JSONFactory jsonFactory;

}
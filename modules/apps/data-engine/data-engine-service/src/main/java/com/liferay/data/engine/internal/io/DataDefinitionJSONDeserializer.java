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
import com.liferay.data.engine.io.DataDefinitionDeserializerApplyRequest;
import com.liferay.data.engine.io.DataDefinitionDeserializerApplyResponse;
import com.liferay.data.engine.model.DataDefinitionColumn;
import com.liferay.data.engine.model.DataDefinitionColumnType;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "data.definition.deserializer.type=json",
	service = DataDefinitionDeserializer.class
)
public class DataDefinitionJSONDeserializer
	implements DataDefinitionDeserializer {

	@Override
	public DataDefinitionDeserializerApplyResponse apply(
		DataDefinitionDeserializerApplyRequest
			dataDefinitionDeserializerApplyRequest) {

		List<DataDefinitionColumn> dataDefinitionColumns = new ArrayList<>();

		try {
			JSONArray jsonArray = jsonFactory.createJSONArray(
				dataDefinitionDeserializerApplyRequest.getContent());

			for (int i = 0; i < jsonArray.length(); i++) {
				DataDefinitionColumn dataDefinitionColumn =
					getDataDefinitionColumn(jsonArray.getJSONObject(i));

				dataDefinitionColumns.add(dataDefinitionColumn);
			}

			return DataDefinitionDeserializerApplyResponse.Builder.of(
				dataDefinitionColumns);
		}
		catch (JSONException e)
		{
			return DataDefinitionDeserializerApplyResponse.Builder.of(null);
		}
	}

	protected DataDefinitionColumn getDataDefinitionColumn(
		JSONObject jsonObject) {

		JSONObject labelJSONObject = jsonObject.getJSONObject("label");

		Iterator<String> keys = labelJSONObject.keys();

		Map<String, String> labels = new TreeMap<>();

		while (keys.hasNext()) {
			String key = keys.next();

			labels.put(key, labelJSONObject.getString(key));
		}

		return DataDefinitionColumn.Builder.newBuilder(
			jsonObject.getString("name"),
			DataDefinitionColumnType.parse(jsonObject.getString("type"))
		).indexable(
			jsonObject.getBoolean("indexable")
		).localizable(
			jsonObject.getBoolean("localizable")
		).repeatable(
			jsonObject.getBoolean("repeatable")
		).labels(
			labels
		).build();
	}

	@Reference
	protected JSONFactory jsonFactory;

}
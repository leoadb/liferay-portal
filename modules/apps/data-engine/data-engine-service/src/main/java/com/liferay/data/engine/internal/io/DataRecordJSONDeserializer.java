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

import com.liferay.data.engine.io.DataRecordDeserializer;
import com.liferay.data.engine.io.DataRecordDeserializerApplyRequest;
import com.liferay.data.engine.io.DataRecordDeserializerApplyResponse;
import com.liferay.data.engine.model.DataDefinition;
import com.liferay.data.engine.model.DataDefinitionColumn;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "data.record.deserializer.type=json",
	service = DataRecordDeserializer.class
)
public class DataRecordJSONDeserializer implements DataRecordDeserializer {

	@Override
	public DataRecordDeserializerApplyResponse apply(
		DataRecordDeserializerApplyRequest dataRecordDeserializerApplyRequest) {

		JSONObject jsonObject = null;

		try {
			jsonObject = jsonFactory.createJSONObject(
				dataRecordDeserializerApplyRequest.getContent());
		}
		catch (JSONException jsone)
		{
			return DataRecordDeserializerApplyResponse.Builder.of(
				Collections.emptyMap());
		}

		DataDefinition dataDefinition =
			dataRecordDeserializerApplyRequest.getDataDefinition();

		List<DataDefinitionColumn> dataDefinitionColumns =
			dataDefinition.getColumns();

		Map<String, Object> values = new HashMap<>();

		for (DataDefinitionColumn dataDefinitionColumn :
				dataDefinitionColumns) {

			String dataDefinitionColumnName = dataDefinitionColumn.getName();

			if (!jsonObject.has(dataDefinitionColumnName)) {
				continue;
			}

			if (dataDefinitionColumn.isLocalizable()) {
				JSONObject localizedJSONObject = jsonObject.getJSONObject(
					dataDefinitionColumnName);

				Map<String, Object> localizedValues = new HashMap<>();

				Iterator<String> keys = localizedJSONObject.keys();

				while (keys.hasNext()) {
					String languageId = keys.next();

					localizedValues.put(
						languageId, localizedJSONObject.get(languageId));
				}

				values.put(dataDefinitionColumnName, localizedValues);
			}
			else if (dataDefinitionColumn.isRepeatable()) {
				JSONArray jsonArray = jsonObject.getJSONArray(
					dataDefinitionColumnName);

				Object[] repeatableValues = new Object[jsonArray.length()];

				for (int i = 0; i < jsonArray.length(); i++) {
					repeatableValues[i] = jsonArray.get(i);
				}

				values.put(dataDefinitionColumnName, repeatableValues);
			}
			else {
				values.put(
					dataDefinitionColumnName,
					jsonObject.get(dataDefinitionColumnName));
			}
		}

		return DataRecordDeserializerApplyResponse.Builder.of(values);
	}

	@Reference
	protected JSONFactory jsonFactory;

}
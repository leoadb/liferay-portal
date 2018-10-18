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

import com.liferay.data.engine.io.DataRecordSerializer;
import com.liferay.data.engine.io.DataRecordSerializerApplyRequest;
import com.liferay.data.engine.io.DataRecordSerializerApplyResponse;
import com.liferay.data.engine.model.DataDefinition;
import com.liferay.data.engine.model.DataDefinitionColumn;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "data.record.serializer.type=json",
	service = DataRecordSerializer.class
)
public class DataRecordJSONSerializer implements DataRecordSerializer {

	@Override
	public DataRecordSerializerApplyResponse apply(
		DataRecordSerializerApplyRequest dataRecordSerializerApplyRequest) {

		JSONObject jsonObject = jsonFactory.createJSONObject();

		addDataDefinitionColumnsValue(
			jsonObject, dataRecordSerializerApplyRequest);

		return DataRecordSerializerApplyResponse.Builder.of(
			jsonObject.toJSONString());
	}

	protected void addDataDefinitionColumnsValue(
		JSONObject jsonObject,
		DataRecordSerializerApplyRequest dataRecordSerializerApplyRequest) {

		DataDefinition dataDefinition =
			dataRecordSerializerApplyRequest.getDataDefinition();

		Map<String, DataDefinitionColumn> dataDefinitionColumns =
			getDataDefinitionColumnMap(dataDefinition);

		Map<String, Object> values =
			dataRecordSerializerApplyRequest.getValues();

		for (Map.Entry<String, DataDefinitionColumn> entry :
				dataDefinitionColumns.entrySet()) {

			String dataDefinitionColumnName = entry.getKey();

			if (!values.containsKey(dataDefinitionColumnName)) {
				continue;
			}

			DataDefinitionColumn dataDefinitionColumn = entry.getValue();

			if (dataDefinitionColumn.isLocalizable()) {
				JSONObject localizedJSONObject = jsonFactory.createJSONObject();

				addLocalizedValues(
					localizedJSONObject,
					(Map<String, Object>)values.get(dataDefinitionColumnName));

				jsonObject.put(dataDefinitionColumnName, localizedJSONObject);
			}
			else if (dataDefinitionColumn.isRepeatable()) {
				JSONArray jsonArray = jsonFactory.createJSONArray();

				addValues(
					jsonArray, (Object[])values.get(dataDefinitionColumnName));

				jsonObject.put(dataDefinitionColumnName, jsonArray);
			}
			else {
				jsonObject.put(
					dataDefinitionColumnName,
					values.get(dataDefinitionColumnName));
			}
		}
	}

	protected void addLocalizedValues(
		JSONObject jsonObject, Map<String, Object> values) {

		for (Map.Entry<String, Object> entry : values.entrySet()) {
			jsonObject.put(
				entry.getKey(),
				GetterUtil.get(entry.getValue(), StringPool.BLANK));
		}
	}

	protected void addValues(JSONArray jsonArray, Object[] values) {
		for (Object value : values) {
			jsonArray.put(value);
		}
	}

	protected Map<String, DataDefinitionColumn> getDataDefinitionColumnMap(
		DataDefinition dataDefinition) {

		List<DataDefinitionColumn> columns = dataDefinition.getColumns();

		Stream<DataDefinitionColumn> stream = columns.stream();

		return stream.collect(
			Collectors.toMap(column -> column.getName(), Function.identity()));
	}

	@Reference
	protected JSONFactory jsonFactory;

}
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

package com.liferay.data.engine.rest.internal.dto.v1_0.util;

import com.liferay.data.engine.rest.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Jeyvison Nascimento
 */
public class DataRecordValueUtil {

	public static Map<String, ?> toDataRecordValues(
			DataDefinition dataDefinition, String json)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

		Map<String, Object> map = new HashMap<>();

		for (DataDefinitionField dataDefinitionField :
				dataDefinition.getDataDefinitionFields()) {

			map.put(
				dataDefinitionField.getName(),
				_toDataRecordValueValue(dataDefinitionField, jsonObject));
		}

		return map;
	}

	public static String toJSON(
			DataDefinition dataDefinition, Map<String, ?> dataRecordValues)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		Map<String, Object> dataRecordValuesValues = _toDataRecordValuesValues(
			dataRecordValues);

		Map<String, DataDefinitionField> dataDefinitionFields = Stream.of(
			dataDefinition.getDataDefinitionFields()
		).collect(
			Collectors.toMap(
				dataDefinitionField -> dataDefinitionField.getName(),
				Function.identity())
		);

		for (Map.Entry<String, DataDefinitionField> entry :
				dataDefinitionFields.entrySet()) {

			if (!dataRecordValuesValues.containsKey(entry.getKey())) {
				continue;
			}

			DataDefinitionField dataDefinitionField = entry.getValue();

			if (dataDefinitionField.getLocalizable()) {
				jsonObject.put(
					entry.getKey(),
					_toJSONObject(
						(Map<String, Object>)dataRecordValuesValues.get(
							dataDefinitionField.getName())));
			}
			else if (dataDefinitionField.getRepeatable()) {
				jsonObject.put(
					entry.getKey(),
					JSONUtil.toJSONArray(
						(Object[])dataRecordValuesValues.get(entry.getKey()),
						object -> object));
			}
			else {
				jsonObject.put(
					entry.getKey(), dataRecordValuesValues.get(entry.getKey()));
			}
		}

		return jsonObject.toString();
	}

	private static Map<String, Object> _toDataRecordValuesValues(
		Map<String, ?> dataRecordValues) {

		Map<String, Object> dataRecordValuesValues = new HashMap<>();

		for (Map.Entry<String, ?> entry : dataRecordValues.entrySet()) {
			dataRecordValuesValues.put(entry.getKey(), entry.getValue());
		}

		return dataRecordValuesValues;
	}

	private static Object _toDataRecordValueValue(
		DataDefinitionField dataDefinitionField, JSONObject jsonObject) {

		if (dataDefinitionField.getLocalizable()) {
			Map<String, Object> localizedValues = new HashMap<>();

			JSONObject dataRecordValueJSONObject = jsonObject.getJSONObject(
				dataDefinitionField.getName());

			Iterable<String> iterable = dataRecordValueJSONObject::keys;

			StreamSupport.stream(
				iterable.spliterator(), false
			).forEach(
				key -> localizedValues.put(
					key, dataRecordValueJSONObject.get(key))
			);

			return localizedValues;
		}

		if (dataDefinitionField.getRepeatable()) {
			return JSONUtil.toObjectArray(
				jsonObject.getJSONArray(dataDefinitionField.getName()));
		}

		return jsonObject.get(dataDefinitionField.getName());
	}

	private static JSONObject _toJSONObject(
		Map<String, Object> localizedValues) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (Map.Entry<String, Object> entry : localizedValues.entrySet()) {
			jsonObject.put(
				entry.getKey(),
				GetterUtil.get(entry.getValue(), StringPool.BLANK));
		}

		return jsonObject;
	}

}
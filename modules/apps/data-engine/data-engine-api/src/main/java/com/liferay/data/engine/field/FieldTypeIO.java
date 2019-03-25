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

package com.liferay.data.engine.field;

import com.liferay.data.engine.exception.DEDataDefinitionDeserializerException;
import com.liferay.data.engine.exception.DEDataDefinitionSerializerException;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.util.DataEngineUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

/**
 * @author Leonardo Barros
 */
public interface FieldTypeIO {

	public default DataDefinitionField deserialize(JSONObject jsonObject)
		throws DEDataDefinitionDeserializerException {

		if (!jsonObject.has("name")) {
			throw new DEDataDefinitionDeserializerException("Name is required");
		}

		if (!jsonObject.has("type")) {
			throw new DEDataDefinitionDeserializerException("Type is required");
		}

		DataDefinitionField dataDefinitionField = new DataDefinitionField();

		dataDefinitionField.setName(jsonObject.getString("name"));
		dataDefinitionField.setFieldType(jsonObject.getString("type"));

		dataDefinitionField.setDefaultValue(
			GetterUtil.getString(jsonObject.get("defaultValue")));
		dataDefinitionField.setIndexable(
			jsonObject.getBoolean("indexable", true));

		Map<String, String> label = DataEngineUtil.getLocalizedValues(
			"label", jsonObject);

		if (label != null) {
			dataDefinitionField.setLabel(label);
		}

		dataDefinitionField.setLocalizable(
			jsonObject.getBoolean("localizable", false));
		dataDefinitionField.setRepeatable(
			jsonObject.getBoolean("repeatable", false));

		Map<String, String> tip = DataEngineUtil.getLocalizedValues(
			"tip", jsonObject);

		if (tip != null) {
			dataDefinitionField.setTip(tip);
		}

		return dataDefinitionField;
	}

	public default JSONObject serialize(
			DataDefinitionField dataDefinitionField, JSONFactory jsonFactory)
		throws DEDataDefinitionSerializerException {

		JSONObject jsonObject = jsonFactory.createJSONObject();

		Object defaultValue = dataDefinitionField.getDefaultValue();

		if (defaultValue != null) {
			jsonObject.put("defaultValue", defaultValue);
		}

		jsonObject.put("indexable", dataDefinitionField.getIndexable());

		Map<String, String> label = dataDefinitionField.getLabel();

		if (!label.isEmpty()) {
			DataEngineUtil.setLocalizedProperty(
				"label", jsonFactory, jsonObject, label);
		}

		jsonObject.put("localizable", dataDefinitionField.getLocalizable());

		String name = dataDefinitionField.getName();

		if (Validator.isNull(name)) {
			throw new DEDataDefinitionSerializerException("Name is required");
		}

		jsonObject.put("name", name);

		jsonObject.put("repeatable", dataDefinitionField.getRepeatable());

		Map<String, String> tip = dataDefinitionField.getTip();

		if (!tip.isEmpty()) {
			DataEngineUtil.setLocalizedProperty(
				"tip", jsonFactory, jsonObject, tip);
		}

		String type = dataDefinitionField.getFieldType();

		if ((type == null) || type.isEmpty()) {
			throw new DEDataDefinitionSerializerException("Type is required");
		}

		jsonObject.put("type", type);

		return jsonObject;
	}

}
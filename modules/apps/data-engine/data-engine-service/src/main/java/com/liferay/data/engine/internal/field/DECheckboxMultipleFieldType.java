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

package com.liferay.data.engine.internal.field;

import com.liferay.data.engine.exception.DEDataDefinitionDeserializerException;
import com.liferay.data.engine.exception.DEDataDefinitionSerializerException;
import com.liferay.data.engine.field.DEFieldType;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.data.engine.util.DEDataEngineUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = "de.data.definition.field.type=checkbox_multiple",
	service = DEFieldType.class
)
public class DECheckboxMultipleFieldType implements DEFieldType {

	@Override
	public DEDataDefinitionField deserialize(JSONObject jsonObject)
		throws DEDataDefinitionDeserializerException {

		DEDataDefinitionField deDataDefinitionField =
			DEFieldType.super.deserialize(jsonObject);

		if (jsonObject.has("inline")) {
			deDataDefinitionField.setCustomProperty(
				"inline", jsonObject.getBoolean("inline"));
		}

		if (jsonObject.has("showAsSwitcher")) {
			deDataDefinitionField.setCustomProperty(
				"showAsSwitcher", jsonObject.getBoolean("showAsSwitcher"));
		}

		if (jsonObject.has("options")) {
			deDataDefinitionField.setCustomProperty(
				"options",
				DEDataEngineUtil.getOptionsProperty(jsonObject, "options"));
		}

		if (jsonObject.has("predefinedValue")) {
			deDataDefinitionField.setCustomProperty(
				"predefinedValue",
				DEDataEngineUtil.getValues(
					jsonObject.getString("predefinedValue"), jsonFactory));
		}

		return deDataDefinitionField;
	}

	@Override
	public void includeContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Map<String, Object> context,
		DEDataDefinitionField deDataDefinitionField, boolean readOnly) {

		DEFieldType.super.includeContext(
			httpServletRequest, httpServletResponse, context,
			deDataDefinitionField, readOnly);

		String languageId = LanguageUtil.getLanguageId(httpServletRequest);

		if (deDataDefinitionField.hasCustomProperty("inline")) {
			context.put(
				"inline",
				GetterUtil.getBoolean(
					deDataDefinitionField.getCustomProperty("inline")));
		}

		if (deDataDefinitionField.hasCustomProperty("options")) {
			context.put(
				"options",
				DEDataEngineUtil.getOptions(
					deDataDefinitionField, "options", languageId));
		}

		if (deDataDefinitionField.hasCustomProperty("predefinedValue")) {
			context.put(
				"predefinedValue",
				deDataDefinitionField.getCustomProperty("predefinedValue"));
		}

		if (deDataDefinitionField.hasCustomProperty("showAsSwitcher")) {
			context.put(
				"showAsSwitcher",
				GetterUtil.getBoolean(
					deDataDefinitionField.getCustomProperty("showAsSwitcher")));
		}

		context.put(
			"value",
			DEDataEngineUtil.getValues(
				GetterUtil.getString(
					deDataDefinitionField.getCustomProperty("value"), "[]"),
				jsonFactory));
	}

	@Override
	public JSONObject serialize(
			DEDataDefinitionField deDataDefinitionField,
			JSONFactory jsonFactory)
		throws DEDataDefinitionSerializerException {

		JSONObject jsonObject = DEFieldType.super.serialize(
			deDataDefinitionField, jsonFactory);

		if (deDataDefinitionField.hasCustomProperty("inline")) {
			jsonObject.put(
				"inline",
				GetterUtil.getBoolean(
					deDataDefinitionField.getCustomProperty("inline")));
		}

		if (deDataDefinitionField.hasCustomProperty("showAsSwitcher")) {
			jsonObject.put(
				"showAsSwitcher",
				GetterUtil.getBoolean(
					deDataDefinitionField.getCustomProperty("showAsSwitcher")));
		}

		if (deDataDefinitionField.hasCustomProperty("options")) {
			DEDataEngineUtil.setOptionsProperty(
				deDataDefinitionField, "options", jsonFactory, jsonObject);
		}

		if (deDataDefinitionField.hasCustomProperty("predefinedValue")) {
			List<String> predefinedValues =
				(List<String>)deDataDefinitionField.getCustomProperty(
					"predefinedValue");

			JSONArray jsonArray = jsonFactory.createJSONArray();

			for (String predefinedValue : predefinedValues) {
				jsonArray.put(predefinedValue);
			}

			jsonObject.put("predefinedValue", jsonArray);
		}

		return jsonObject;
	}

	@Reference
	protected JSONFactory jsonFactory;

}
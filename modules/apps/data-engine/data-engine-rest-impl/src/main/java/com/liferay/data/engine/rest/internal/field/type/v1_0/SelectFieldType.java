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

package com.liferay.data.engine.rest.internal.field.type.v1_0;

import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.internal.field.type.v1_0.util.CustomPropertyUtil;
import com.liferay.data.engine.rest.internal.field.type.v1_0.util.DataFieldOptionUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.template.soy.data.SoyDataFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gabriel Albuquerque
 */
public class SelectFieldType extends BaseFieldType {

	public SelectFieldType(
		DataDefinitionField dataDefinitionField,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		SoyDataFactory soyDataFactory) {

		super(
			dataDefinitionField, httpServletRequest, httpServletResponse,
			soyDataFactory);
	}

	@Override
	public DataDefinitionField deserialize(JSONObject jsonObject)
		throws Exception {

		dataDefinitionField.setCustomProperties(
			CustomPropertyUtil.add(
				dataDefinitionField.getCustomProperties(), "dataSourceType",
				jsonObject.getString("dataSourceType")));
		dataDefinitionField.setCustomProperties(
			CustomPropertyUtil.add(
				dataDefinitionField.getCustomProperties(), "multiple",
				jsonObject.getBoolean("multiple")));
		dataDefinitionField.setCustomProperties(
			CustomPropertyUtil.add(
				dataDefinitionField.getCustomProperties(), "options",
				DataFieldOptionUtil.toDataFieldOptions(
					jsonObject.getJSONObject("options"))));
		dataDefinitionField.setCustomProperties(
			CustomPropertyUtil.add(
				dataDefinitionField.getCustomProperties(), "predefinedValue",
				LocalizedValueUtil.toLocalizedValues(
					jsonObject.getJSONObject("predefinedValue"))));

		return dataDefinitionField;
	}

	@Override
	public JSONObject toJSONObject() throws Exception {
		JSONObject jsonObject = super.toJSONObject();

		return jsonObject.put(
			"dataSourceType",
			CustomPropertyUtil.getString(
				dataDefinitionField.getCustomProperties(), "dataSourceType")
		).put(
			"multiple",
			CustomPropertyUtil.getBoolean(
				dataDefinitionField.getCustomProperties(), "multiple", false)
		).put(
			"options",
			DataFieldOptionUtil.toJSONObject(
				CustomPropertyUtil.getDataFieldOptions(
					dataDefinitionField.getCustomProperties(), "options"))
		).put(
			"predefinedValue",
			CustomPropertyUtil.getLocalizedValue(
				dataDefinitionField.getCustomProperties(), "predefinedValue")
		);
	}

	@Override
	protected void addContext(Map<String, Object> context) {
		context.put(
			"dataSourceType",
			CustomPropertyUtil.getString(
				dataDefinitionField.getCustomProperties(), "dataSourceType"));
		context.put(
			"multiple",
			CustomPropertyUtil.getBoolean(
				dataDefinitionField.getCustomProperties(), "multiple", false));
		context.put(
			"options",
			DataFieldOptionUtil.toDataFieldOptions(
				CustomPropertyUtil.getDataFieldOptions(
					dataDefinitionField.getCustomProperties(), "options"),
				LanguageUtil.getLanguageId(httpServletRequest)));
		context.put(
			"predefinedValue",
			LocalizedValueUtil.getLocalizedValue(
				httpServletRequest.getLocale(),
				CustomPropertyUtil.getLocalizedValue(
					dataDefinitionField.getCustomProperties(),
					"predefinedValue")));
		context.put("strings", getStringsMap());
		context.put(
			"value",
			getValues(
				CustomPropertyUtil.getString(
					dataDefinitionField.getCustomProperties(), "value", "[]")));
	}

	protected Map<String, String> getStringsMap() {
		Map<String, String> stringsMap = new HashMap<>();

		stringsMap.put(
			"chooseAnOption",
			LanguageUtil.get(httpServletRequest, "choose-an-option"));
		stringsMap.put(
			"chooseOptions",
			LanguageUtil.get(httpServletRequest, "choose-options"));
		stringsMap.put(
			"dynamicallyLoadedData",
			LanguageUtil.get(httpServletRequest, "dynamically-loaded-data"));
		stringsMap.put(
			"emptyList", LanguageUtil.get(httpServletRequest, "empty-list"));
		stringsMap.put(
			"search", LanguageUtil.get(httpServletRequest, "search"));

		return stringsMap;
	}

	protected List<String> getValues(String valueString) {
		JSONArray jsonArray = null;

		try {
			jsonArray = JSONFactoryUtil.createJSONArray(valueString);
		}
		catch (JSONException jsone) {
			jsonArray = JSONFactoryUtil.createJSONArray();
		}

		List<String> values = new ArrayList<>(jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			values.add(String.valueOf(jsonArray.get(i)));
		}

		return values;
	}

}
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

package com.liferay.data.engine.internal.util;

import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.data.engine.model.DEDataFieldOption;
import com.liferay.data.engine.model.DEDataFieldOptions;
import com.liferay.data.engine.model.DEDataFieldValue;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.MapUtil;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Gabriel Albuquerque
 */
public final class DEDataEngineUtil {

	public static Map<String, DEDataDefinitionField>
		getDEDataDefinitionFieldsMap(DEDataDefinition deDataDefinition) {

		List<DEDataDefinitionField> deDataDefinitionFields =
			deDataDefinition.getDEDataDefinitionFields();

		Stream<DEDataDefinitionField> stream = deDataDefinitionFields.stream();

		return stream.collect(
			Collectors.toMap(field -> field.getName(), Function.identity()));
	}

	public static Object getDEDataDefinitionFieldValue(
		DEDataDefinitionField deDataDefinitionField,
		Map<String, Object> values) {

		if (deDataDefinitionField.isLocalizable()) {
			return (Map<String, Object>)values.get(
				deDataDefinitionField.getName());
		}
		else if (deDataDefinitionField.isRepeatable()) {
			return (Object[])values.get(deDataDefinitionField.getName());
		}

		return values.get(deDataDefinitionField.getName());
	}

	public static <T> T getLocalizedValue(
		DEDataDefinitionField deDataDefinitionField, String property,
		String languageId) {

		DEDataFieldValue deDataFieldValue =
			(DEDataFieldValue)deDataDefinitionField.getCustomProperty(property);

		if (deDataFieldValue == null) {
			return null;
		}

		return (T)deDataFieldValue.getValue(languageId);
	}

	public static DecimalFormat getNumberFormat(Locale locale) {
		DecimalFormat formatter = _decimalFormattersMap.get(locale);

		if (formatter == null) {
			formatter = (DecimalFormat)DecimalFormat.getInstance(locale);

			formatter.setGroupingUsed(false);
			formatter.setMaximumFractionDigits(Integer.MAX_VALUE);
			formatter.setParseBigDecimal(true);

			_decimalFormattersMap.put(locale, formatter);
		}

		return formatter;
	}

	public static List<KeyValuePair> getOptions(
		DEDataDefinitionField deDataDefinitionField, String property,
		String languageId) {

		DEDataFieldOptions deDataFieldOptions =
			(DEDataFieldOptions)deDataDefinitionField.getCustomProperty(
				property);

		if (deDataFieldOptions == null) {
			return null;
		}

		List<KeyValuePair> keyValuePairs = new ArrayList<>();

		List<DEDataFieldOption> deDataFieldOptionList =
			deDataFieldOptions.getDEDataFieldOptions();

		for (DEDataFieldOption deDataFieldValue : deDataFieldOptionList) {
			String key = deDataFieldValue.getLabel();

			if (deDataFieldValue.isLocalized()) {
				key = MapUtil.getString(
					deDataFieldValue.getLabels(), languageId);
			}

			keyValuePairs.add(
				new KeyValuePair(
					String.valueOf(deDataFieldValue.getValue()), key));
		}

		return keyValuePairs;
	}

	public static List<String> getValues(String json, JSONFactory jsonFactory) {
		JSONArray jsonArray = null;

		try {
			jsonArray = jsonFactory.createJSONArray(json);
		}
		catch (JSONException jsone) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsone, jsone);
			}

			jsonArray = jsonFactory.createJSONArray();
		}

		List<String> values = new ArrayList<>(jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			values.add(String.valueOf(jsonArray.get(i)));
		}

		return values;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DEDataEngineUtil.class);

	private static final Map<Locale, DecimalFormat> _decimalFormattersMap =
		new ConcurrentHashMap<>();

}
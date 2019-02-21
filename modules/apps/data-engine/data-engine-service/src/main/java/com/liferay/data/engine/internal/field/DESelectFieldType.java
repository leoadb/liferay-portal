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

import com.liferay.data.engine.field.DEFieldType;
import com.liferay.data.engine.field.DEFieldTypeGetFieldPropertiesRequest;
import com.liferay.data.engine.field.DEFieldTypeGetFieldPropertiesResponse;
import com.liferay.data.engine.field.DEFieldTypeResponseBuilder;
import com.liferay.data.engine.internal.util.DEDataEngineUtil;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "de.data.definition.field.type=select",
	service = DEFieldType.class
)
public class DESelectFieldType implements DEFieldType {

	@Override
	public DEFieldTypeGetFieldPropertiesResponse getFieldProperties(
		DEFieldTypeGetFieldPropertiesRequest
			deFieldTypeGetFieldPropertiesRequest) {

		DEFieldTypeGetFieldPropertiesResponse
			deFieldTypeGetFieldPropertiesResponse =
				DEFieldType.super.getFieldProperties(
					deFieldTypeGetFieldPropertiesRequest);

		Map<String, Object> fieldProperties =
			deFieldTypeGetFieldPropertiesResponse.getFieldProperties();

		DEDataDefinitionField deDataDefinitionField =
			deFieldTypeGetFieldPropertiesRequest.getDEDataDefinitionField();

		Locale locale = deFieldTypeGetFieldPropertiesRequest.getLocale();

		String languageId = LanguageUtil.getLanguageId(locale);

		fieldProperties.put(
			"dataSourceType",
			GetterUtil.getString(
				deDataDefinitionField.getCustomProperty("dataSourceType"),
				"manual"));
		fieldProperties.put(
			"multiple",
			GetterUtil.getBoolean(
				deDataDefinitionField.getCustomProperty("multiple")));

		fieldProperties.put(
			"options",
			DEDataEngineUtil.getOptions(
				deDataDefinitionField, "options", languageId));

		ResourceBundle resourceBundle = getResourceBundle(locale);

		Map<String, String> stringsMap = new HashMap<>();

		stringsMap.put(
			"chooseAnOption",
			LanguageUtil.get(resourceBundle, "choose-an-option"));
		stringsMap.put(
			"chooseOptions",
			LanguageUtil.get(resourceBundle, "choose-options"));
		stringsMap.put(
			"dynamicallyLoadedData",
			LanguageUtil.get(resourceBundle, "dynamically-loaded-data"));
		stringsMap.put(
			"emptyList", LanguageUtil.get(resourceBundle, "empty-list"));
		stringsMap.put("search", LanguageUtil.get(resourceBundle, "search"));

		fieldProperties.put("strings", stringsMap);

		String predefinedValue = DEDataEngineUtil.getLocalizedValue(
			deDataDefinitionField, "predefinedValue", languageId);

		if (predefinedValue != null) {
			fieldProperties.put("predefinedValue", predefinedValue);
		}

		fieldProperties.put(
			"value",
			DEDataEngineUtil.getValues(
				GetterUtil.getString(
					deDataDefinitionField.getCustomProperty("value"), "[]"),
				jsonFactory));

		return DEFieldTypeResponseBuilder.getFieldPropertiesBuilder(
			fieldProperties
		).build();
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		Class<?> clazz = getClass();

		ResourceBundle portalResourceBundle = portal.getResourceBundle(locale);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, clazz.getClassLoader());

		return new AggregateResourceBundle(
			resourceBundle, portalResourceBundle);
	}

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected Portal portal;

}
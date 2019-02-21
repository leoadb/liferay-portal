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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.KeyValuePair;

import java.util.List;
import java.util.Map;

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
	public DEFieldTypeGetFieldPropertiesResponse getFieldProperties(
		DEFieldTypeGetFieldPropertiesRequest
			deFieldTypeGetFieldPropertiesRequest) {

		DEFieldTypeGetFieldPropertiesResponse
			deFieldTypeGetFieldPropertiesResponse =
				DEFieldType.super.getFieldProperties(
					deFieldTypeGetFieldPropertiesRequest);

		DEDataDefinitionField deDataDefinitionField =
			deFieldTypeGetFieldPropertiesRequest.getDEDataDefinitionField();

		Map<String, Object> fieldProperties =
			deFieldTypeGetFieldPropertiesResponse.getFieldProperties();

		String languageId = LanguageUtil.getLanguageId(
			deFieldTypeGetFieldPropertiesRequest.getLocale());

		fieldProperties.put(
			"inline",
			GetterUtil.getBoolean(
				deDataDefinitionField.getCustomProperty("inline")));

		List<KeyValuePair> options = DEDataEngineUtil.getOptions(
			deDataDefinitionField, "options", languageId);

		if (options != null) {
			fieldProperties.put("options", options);
		}

		List<String> predefinedValue = DEDataEngineUtil.getValues(
			DEDataEngineUtil.getLocalizedValue(
				deDataDefinitionField, "predefinedValue", languageId),
			jsonFactory);

		if (predefinedValue != null) {
			fieldProperties.put("predefinedValue", predefinedValue);
		}

		fieldProperties.put(
			"showAsSwitcher",
			GetterUtil.getBoolean(
				deDataDefinitionField.getCustomProperty("showAsSwitcher")));
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

	@Reference
	protected JSONFactory jsonFactory;

}
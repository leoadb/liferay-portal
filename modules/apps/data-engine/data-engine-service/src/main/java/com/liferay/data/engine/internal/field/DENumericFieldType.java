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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "de.data.definition.field.type=numeric",
	service = DEFieldType.class
)
public class DENumericFieldType implements DEFieldType {

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
			"dataType",
			GetterUtil.getString(
				deDataDefinitionField.getCustomProperty("dataType")));

		String placeholder = DEDataEngineUtil.getLocalizedValue(
			deDataDefinitionField, "placeholder", languageId);

		if (placeholder != null) {
			fieldProperties.put("placeholder", placeholder);
		}

		fieldProperties.put(
			"predefinedValue",
			getFormattedValue(
				DEDataEngineUtil.getLocalizedValue(
					deDataDefinitionField, "predefinedValue", languageId),
				locale));

		fieldProperties.put("symbols", getSymbolsMap(locale));

		String tooltip = DEDataEngineUtil.getLocalizedValue(
			deDataDefinitionField, "tooltip", languageId);

		if (tooltip != null) {
			fieldProperties.put("tooltip", tooltip);
		}

		return DEFieldTypeResponseBuilder.getFieldPropertiesBuilder(
			fieldProperties
		).build();
	}

	protected String getFormattedValue(Integer value, Locale locale) {
		if (value == null) {
			return StringPool.BLANK;
		}

		DecimalFormat numberFormat = DEDataEngineUtil.getNumberFormat(locale);

		return numberFormat.format(GetterUtil.getNumber(value));
	}

	protected Map<String, String> getSymbolsMap(Locale locale) {
		DecimalFormat formatter = DEDataEngineUtil.getNumberFormat(locale);

		DecimalFormatSymbols decimalFormatSymbols =
			formatter.getDecimalFormatSymbols();

		Map<String, String> symbolsMap = new HashMap<>();

		symbolsMap.put(
			"decimalSymbol",
			String.valueOf(decimalFormatSymbols.getDecimalSeparator()));
		symbolsMap.put(
			"thousandsSeparator",
			String.valueOf(decimalFormatSymbols.getGroupingSeparator()));

		return symbolsMap;
	}

}
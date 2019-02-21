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

import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.portal.kernel.language.LanguageConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public interface DEFieldType {

	public default DEFieldTypeGetFieldPropertiesResponse getFieldProperties(
		DEFieldTypeGetFieldPropertiesRequest
			deFieldTypeGetFieldPropertiesRequest) {

		Locale locale = deFieldTypeGetFieldPropertiesRequest.getLocale();

		String languageId = LanguageUtil.getLanguageId(locale);

		DEDataDefinitionField deDataDefinitionField =
			deFieldTypeGetFieldPropertiesRequest.getDEDataDefinitionField();

		Map<String, Object> fieldProperties = new HashMap<>();

		fieldProperties.put(
			"dir", LanguageUtil.get(locale, LanguageConstants.KEY_DIR));
		fieldProperties.put(
			"label",
			MapUtil.getString(deDataDefinitionField.getLabel(), languageId));
		fieldProperties.put("name", deDataDefinitionField.getName());
		fieldProperties.put(
			"readOnly",
			GetterUtil.getBoolean(
				deDataDefinitionField.getCustomProperty("readOnly")));
		fieldProperties.put(
			"required",
			GetterUtil.getBoolean(
				deDataDefinitionField.getCustomProperty("required")));
		fieldProperties.put(
			"showLabel",
			GetterUtil.getBoolean(
				deDataDefinitionField.getCustomProperty("showLabel"), true));
		fieldProperties.put(
			"tip",
			MapUtil.getString(deDataDefinitionField.getTip(), languageId));
		fieldProperties.put("type", deDataDefinitionField.getType());
		fieldProperties.put(
			"visible",
			GetterUtil.getBoolean(
				deDataDefinitionField.getCustomProperty("visible"), true));

		return DEFieldTypeResponseBuilder.getFieldPropertiesBuilder(
			fieldProperties
		).build();
	}

}
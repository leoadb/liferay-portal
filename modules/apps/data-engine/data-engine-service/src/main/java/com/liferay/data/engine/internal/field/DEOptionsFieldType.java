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
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "de.data.definition.field.type=options",
	service = DEFieldType.class
)
public class DEOptionsFieldType implements DEFieldType {

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
			"allowEmptyOptions",
			GetterUtil.getBoolean(
				deDataDefinitionField.getCustomProperty("allowEmptyOptions")));

		fieldProperties.put("defaultLanguageId", languageId);

		fieldProperties.put(
			"value", deDataDefinitionField.getCustomProperty("value"));

		return DEFieldTypeResponseBuilder.getFieldPropertiesBuilder(
			fieldProperties
		).build();
	}

}
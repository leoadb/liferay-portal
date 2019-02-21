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
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "de.data.definition.field.type=fieldset",
	service = DEFieldType.class
)
public class DEGridFieldType implements DEFieldType {

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

		String languageId = LanguageUtil.getLanguageId(
			deFieldTypeGetFieldPropertiesRequest.getLocale());

		fieldProperties.put(
			"columns",
			DEDataEngineUtil.getOptions(
				deDataDefinitionField, "columns", languageId));
		fieldProperties.put(
			"rows",
			DEDataEngineUtil.getOptions(
				deDataDefinitionField, "rows", languageId));

		Object value = deDataDefinitionField.getCustomProperty("value");

		if (Validator.isNull(value)) {
			value = "{}";
		}

		fieldProperties.put(
			"value", jsonFactory.looseDeserialize(value.toString()));

		return DEFieldTypeResponseBuilder.getFieldPropertiesBuilder(
			fieldProperties
		).build();
	}

	@Reference
	protected JSONFactory jsonFactory;

}
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

import com.liferay.data.engine.field.DEFieldTypeGetFieldPropertiesRequest;
import com.liferay.data.engine.field.DEFieldTypeGetFieldPropertiesResponse;
import com.liferay.data.engine.field.DEFieldTypeRequestBuilder;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.data.engine.model.DEDataFieldValue;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Leonardo Barros
 */
@RunWith(PowerMockRunner.class)
public class DENumericFieldTypeTest extends DEBaseFieldTypeTest {

	@Test
	public void testGetFieldProperties() {
		DEDataDefinitionField deDataDefinitionField =
			createDEDataDefinitionField("field1", "numeric");

		deDataDefinitionField.setCustomProperty("dataType", "integer");

		Map<String, String> placeholder = new HashMap() {
			{
				put("en_US", "Placeholder US");
				put("pt_BR", "Placeholder BR");
			}
		};

		deDataDefinitionField.setCustomProperty(
			"placeholder", new DEDataFieldValue(placeholder, true));

		Map<String, String> tooltip = new HashMap() {
			{
				put("en_US", "Tooltip US");
				put("pt_BR", "Tooltip BR");
			}
		};

		deDataDefinitionField.setCustomProperty(
			"tooltip", new DEDataFieldValue(tooltip, true));

		Map<String, String> predefinedValue = new HashMap() {
			{
				put("en_US", 1);
				put("pt_BR", 1);
			}
		};

		deDataDefinitionField.setCustomProperty(
			"predefinedValue", new DEDataFieldValue(predefinedValue, true));

		DEFieldTypeGetFieldPropertiesRequest
			deFieldTypeGetFieldPropertiesRequest =
				DEFieldTypeRequestBuilder.getFieldPropertiesBuilder(
					deDataDefinitionField, locale
				).build();

		DENumericFieldType deNumericFieldType = new DENumericFieldType();

		DEFieldTypeGetFieldPropertiesResponse
			deFieldTypeGetFieldPropertiesResponse =
				deNumericFieldType.getFieldProperties(
					deFieldTypeGetFieldPropertiesRequest);

		Map<String, Object> fieldProperties =
			deFieldTypeGetFieldPropertiesResponse.getFieldProperties();

		assertFieldProperties(fieldProperties);

		Assert.assertEquals(
			"integer", MapUtil.getString(fieldProperties, "dataType"));
		Assert.assertEquals(
			"Placeholder BR",
			MapUtil.getString(fieldProperties, "placeholder"));
		Assert.assertEquals(
			1, MapUtil.getInteger(fieldProperties, "predefinedValue"));
		Assert.assertEquals(
			"Tooltip BR", MapUtil.getString(fieldProperties, "tooltip"));
	}

}
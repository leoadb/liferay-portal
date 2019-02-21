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
import com.liferay.data.engine.model.DEDataFieldOption;
import com.liferay.data.engine.model.DEDataFieldOptions;
import com.liferay.data.engine.model.DEDataFieldValue;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Leonardo Barros
 */
@RunWith(PowerMockRunner.class)
public class DECheckboxMultipleFieldTypeTest extends DEBaseFieldTypeTest {

	@Test
	public void testGetFieldProperties() throws Exception {
		DEDataDefinitionField deDataDefinitionField =
			createDEDataDefinitionField("field1", "checkbox_multiple");

		Map<String, String> predefinedValue = new HashMap() {
			{
				put("en_US", "[\"1\"]");
				put("pt_BR", "[\"1\"]");
			}
		};

		deDataDefinitionField.setCustomProperty("inline", true);

		deDataDefinitionField.setCustomProperty(
			"predefinedValue", new DEDataFieldValue(predefinedValue, true));

		DEDataFieldOption deDataFieldOption1 = new DEDataFieldOption(
			"Label 1", "Value 1");
		DEDataFieldOption deDataFieldOption2 = new DEDataFieldOption(
			"Label 2", "Value 2");

		DEDataFieldOptions deDataFieldOptions = new DEDataFieldOptions();

		deDataFieldOptions.setDEDataFieldOptions(
			Arrays.asList(deDataFieldOption1, deDataFieldOption2));

		deDataDefinitionField.setCustomProperty("options", deDataFieldOptions);

		deDataDefinitionField.setCustomProperty("showAsSwitcher", true);
		deDataDefinitionField.setCustomProperty("value", "[\"1\"]");

		DEFieldTypeGetFieldPropertiesRequest
			deFieldTypeGetFieldPropertiesRequest =
				DEFieldTypeRequestBuilder.getFieldPropertiesBuilder(
					deDataDefinitionField, locale
				).build();

		DECheckboxMultipleFieldType deCheckboxMultipleFieldType =
			new DECheckboxMultipleFieldType();

		field(
			DECheckboxMultipleFieldType.class, "jsonFactory"
		).set(
			deCheckboxMultipleFieldType, new JSONFactoryImpl()
		);

		DEFieldTypeGetFieldPropertiesResponse
			deFieldTypeGetFieldPropertiesResponse =
				deCheckboxMultipleFieldType.getFieldProperties(
					deFieldTypeGetFieldPropertiesRequest);

		Map<String, Object> fieldProperties =
			deFieldTypeGetFieldPropertiesResponse.getFieldProperties();

		assertFieldProperties(fieldProperties);

		List<String> stringList = (List<String>)fieldProperties.get(
			"predefinedValue");

		Assert.assertArrayEquals(
			new String[] {"1"}, ArrayUtil.toStringArray(stringList));

		Assert.assertEquals(
			true, MapUtil.getBoolean(fieldProperties, "inline"));
		Assert.assertEquals(
			true, MapUtil.getBoolean(fieldProperties, "showAsSwitcher"));

		stringList = (List<String>)fieldProperties.get("value");

		Assert.assertArrayEquals(
			new String[] {"1"}, ArrayUtil.toStringArray(stringList));

		List<KeyValuePair> keyValuePairs =
			(List<KeyValuePair>)fieldProperties.get("options");

		Assert.assertEquals(keyValuePairs.toString(), 2, keyValuePairs.size());

		KeyValuePair keyValuePair = keyValuePairs.get(0);

		Assert.assertEquals("Label 1", keyValuePair.getValue());
		Assert.assertEquals("Value 1", keyValuePair.getKey());

		keyValuePair = keyValuePairs.get(1);

		Assert.assertEquals("Label 2", keyValuePair.getValue());
		Assert.assertEquals("Value 2", keyValuePair.getKey());
	}

}
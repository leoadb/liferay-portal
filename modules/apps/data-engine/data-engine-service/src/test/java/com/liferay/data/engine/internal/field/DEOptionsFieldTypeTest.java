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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Arrays;
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
public class DEOptionsFieldTypeTest extends DEBaseFieldTypeTest {

	@Test
	public void testGetFieldProperties() throws Exception {
		DEDataDefinitionField deDataDefinitionField =
			createDEDataDefinitionField("field1", "options");

		deDataDefinitionField.setCustomProperty("allowEmptyOptions", true);
		deDataDefinitionField.setCustomProperty("value", Arrays.asList("1"));

		DEFieldTypeGetFieldPropertiesRequest
			deFieldTypeGetFieldPropertiesRequest =
				DEFieldTypeRequestBuilder.getFieldPropertiesBuilder(
					deDataDefinitionField, locale
				).build();

		DEOptionsFieldType deOptionsFieldType = new DEOptionsFieldType();

		DEFieldTypeGetFieldPropertiesResponse
			deFieldTypeGetFieldPropertiesResponse =
				deOptionsFieldType.getFieldProperties(
					deFieldTypeGetFieldPropertiesRequest);

		Map<String, Object> fieldProperties =
			deFieldTypeGetFieldPropertiesResponse.getFieldProperties();

		assertFieldProperties(fieldProperties);

		List<String> stringList = (List<String>)fieldProperties.get("value");

		Assert.assertArrayEquals(
			new String[] {"1"}, ArrayUtil.toStringArray(stringList));

		Assert.assertEquals(
			true, MapUtil.getBoolean(fieldProperties, "allowEmptyOptions"));

		stringList = (List<String>)fieldProperties.get("value");

		Assert.assertEquals("pt_BR", fieldProperties.get("defaultLanguageId"));
		Assert.assertArrayEquals(
			new String[] {"1"}, ArrayUtil.toStringArray(stringList));
	}

}
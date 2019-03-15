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

import com.liferay.data.engine.field.DEFieldTypeGetContextRequest;
import com.liferay.data.engine.field.DEFieldTypeGetContextResponse;
import com.liferay.data.engine.field.DEFieldTypeRequestBuilder;
import com.liferay.data.engine.model.DEDataDefinitionField;
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
public class DEKeyValueFieldTypeTest extends DEBaseFieldTypeTest {

	@Test
	public void testGetFieldProperties() {
		DEDataDefinitionField deDataDefinitionField =
			createDEDataDefinitionField("field1", "key_value");

		deDataDefinitionField.setCustomProperty("autoFocus", true);

		deDataDefinitionField.setCustomProperty(
			"placeholder",
			new HashMap() {
				{
					put("en_US", "Placeholder US");
					put("pt_BR", "Placeholder BR");
				}
			});

		deDataDefinitionField.setCustomProperty(
			"tooltip",
			new HashMap() {
				{
					put("en_US", "Tooltip US");
					put("pt_BR", "Tooltip BR");
				}
			});

		DEFieldTypeGetContextRequest deFieldTypeGetFieldPropertiesRequest =
			DEFieldTypeRequestBuilder.getContextBuilder(
				deDataDefinitionField, locale
			).build();

		DEKeyValueFieldType deKeyValueFieldType = new DEKeyValueFieldType();

		DEFieldTypeGetContextResponse deFieldTypeGetFieldPropertiesResponse =
			deKeyValueFieldType.getContext(
				deFieldTypeGetFieldPropertiesRequest);

		Map<String, Object> fieldProperties =
			deFieldTypeGetFieldPropertiesResponse.getContext();

		assertFieldProperties(fieldProperties);

		Assert.assertEquals(
			true, MapUtil.getBoolean(fieldProperties, "autoFocus"));
		Assert.assertEquals(
			"Placeholder BR",
			MapUtil.getString(fieldProperties, "placeholder"));
		Assert.assertEquals(
			"Tooltip BR", MapUtil.getString(fieldProperties, "tooltip"));
	}

}
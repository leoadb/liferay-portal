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
import com.liferay.portal.kernel.language.LanguageConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
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
public class DETextFieldTypeTest extends DEBaseFieldTypeTest {

	@Test
	public void testGetFieldProperties() {
		DEDataDefinitionField deDataDefinitionField =
			createDEDataDefinitionField("field1", "text");

		deDataDefinitionField.setCustomProperty("displayStyle", "multiline");

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

		deDataDefinitionField.setCustomProperty(
			"predefinedValue",
			new HashMap() {
				{
					put("en_US", "simple text");
					put("pt_BR", "simple text");
				}
			});

		DEFieldTypeGetContextRequest deFieldTypeGetFieldPropertiesRequest =
			DEFieldTypeRequestBuilder.getContextBuilder(
				deDataDefinitionField, locale
			).build();

		DETextFieldType deTextFieldType = new DETextFieldType();

		DEFieldTypeGetContextResponse deFieldTypeGetFieldPropertiesResponse =
			deTextFieldType.getContext(deFieldTypeGetFieldPropertiesRequest);

		Map<String, Object> fieldProperties =
			deFieldTypeGetFieldPropertiesResponse.getContext();

		assertFieldProperties(fieldProperties);

		Assert.assertEquals(
			LanguageUtil.get(locale, LanguageConstants.KEY_DIR),
			MapUtil.getString(fieldProperties, "dir"));
		Assert.assertEquals(
			"multiline", MapUtil.getString(fieldProperties, "displayStyle"));
		Assert.assertEquals(
			"Placeholder BR",
			MapUtil.getString(fieldProperties, "placeholder"));
		Assert.assertEquals(
			"simple text",
			MapUtil.getString(fieldProperties, "predefinedValue"));
		Assert.assertEquals(
			"Tooltip BR", MapUtil.getString(fieldProperties, "tooltip"));
	}

}
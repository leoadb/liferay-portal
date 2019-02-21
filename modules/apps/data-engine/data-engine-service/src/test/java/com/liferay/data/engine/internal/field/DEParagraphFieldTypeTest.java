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
import com.liferay.portal.template.soy.data.SoyDataFactory;
import com.liferay.portal.template.soy.data.SoyHTMLData;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Leonardo Barros
 */
@RunWith(PowerMockRunner.class)
public class DEParagraphFieldTypeTest extends DEBaseFieldTypeTest {

	@Test
	public void testGetFieldProperties() {
		DEDataDefinitionField deDataDefinitionField =
			createDEDataDefinitionField("field1", "paragraph");

		Map<String, String> predefinedValue = new HashMap() {
			{
				put("en_US", "<b>simple text</b>");
				put("pt_BR", "<b>simple text</b>");
			}
		};

		deDataDefinitionField.setCustomProperty(
			"text", new DEDataFieldValue(predefinedValue, true));

		DEFieldTypeGetFieldPropertiesRequest
			deFieldTypeGetFieldPropertiesRequest =
				DEFieldTypeRequestBuilder.getFieldPropertiesBuilder(
					deDataDefinitionField, locale
				).build();

		DEParagraphFieldType deParagraphFieldType = new DEParagraphFieldType();

		when(
			_soyDataFactory.createSoyHTMLData(Matchers.anyString())
		).thenReturn(
			_soyHTMLData
		);

		deParagraphFieldType.soyDataFactory = _soyDataFactory;

		DEFieldTypeGetFieldPropertiesResponse
			deFieldTypeGetFieldPropertiesResponse =
				deParagraphFieldType.getFieldProperties(
					deFieldTypeGetFieldPropertiesRequest);

		Map<String, Object> fieldProperties =
			deFieldTypeGetFieldPropertiesResponse.getFieldProperties();

		assertFieldProperties(fieldProperties);

		Assert.assertEquals(_soyHTMLData, fieldProperties.get("text"));
	}

	@Mock
	private SoyDataFactory _soyDataFactory;

	@Mock
	private SoyHTMLData _soyHTMLData;

}
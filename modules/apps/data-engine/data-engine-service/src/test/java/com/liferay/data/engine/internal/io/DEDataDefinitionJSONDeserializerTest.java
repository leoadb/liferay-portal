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

package com.liferay.data.engine.internal.io;

import com.liferay.data.engine.constants.DEDataDefinitionValidationRuleConstants;
import com.liferay.data.engine.exception.DEDataDefinitionDeserializerException;
import com.liferay.data.engine.io.DEDataDefinitionDeserializerApplyRequest;
import com.liferay.data.engine.io.DEDataDefinitionDeserializerApplyResponse;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.data.engine.model.DEDataDefinitionValidationRule;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class DEDataDefinitionJSONDeserializerTest extends BaseTestCase {

	@Test
	public void testApply() throws Exception {
		String json = read("data-definition-deserializer.json");

		DEDataDefinitionDeserializerApplyRequest
			deDataDefinitionFieldsDeserializerApplyRequest =
				DEDataDefinitionDeserializerApplyRequest.Builder.of(json);

		DEDataDefinitionJSONDeserializer
			deDataDefinitionFieldsJSONDeserializer =
				new DEDataDefinitionJSONDeserializer();

		deDataDefinitionFieldsJSONDeserializer.jsonFactory =
			new JSONFactoryImpl();

		DEDataDefinitionDeserializerApplyResponse
			deDataDefinitionDeserializerApplyResponse =
				deDataDefinitionFieldsJSONDeserializer.apply(
					deDataDefinitionFieldsDeserializerApplyRequest);

		DEDataDefinition deDataDefinition =
			deDataDefinitionDeserializerApplyResponse.getDEDataDefinition();

		List<DEDataDefinitionField> deDataDefinitionFields =
			deDataDefinition.getDEDataDefinitionFields();

		Assert.assertEquals(
			deDataDefinitionFields.toString(), 3,
			deDataDefinitionFields.size());

		DEDataDefinitionField deDataDefinitionField =
			deDataDefinitionFields.get(0);

		Assert.assertEquals(false, deDataDefinitionField.isIndexable());
		Assert.assertEquals(true, deDataDefinitionField.isLocalizable());
		Assert.assertEquals(false, deDataDefinitionField.isRepeatable());
		Assert.assertEquals("name", deDataDefinitionField.getName());
		Assert.assertEquals("text", deDataDefinitionField.getType());

		Map<String, String> labels = deDataDefinitionField.getLabel();

		Assert.assertEquals("Name", labels.get("en_US"));
		Assert.assertEquals("Nome", labels.get("pt_BR"));

		deDataDefinitionField = deDataDefinitionFields.get(1);

		Assert.assertEquals(true, deDataDefinitionField.isIndexable());
		Assert.assertEquals(false, deDataDefinitionField.isLocalizable());
		Assert.assertEquals(true, deDataDefinitionField.isRepeatable());
		Assert.assertEquals("email", deDataDefinitionField.getName());
		Assert.assertEquals("text", deDataDefinitionField.getType());

		labels = deDataDefinitionField.getLabel();

		Assert.assertEquals("Email Address", labels.get("en_US"));
		Assert.assertEquals("Endereço de Email", labels.get("pt_BR"));

		Map<String, String> tips = deDataDefinitionField.getTip();

		Assert.assertEquals("Enter an email address", tips.get("en_US"));
		Assert.assertEquals("Informe um endereço de email", tips.get("pt_BR"));

		deDataDefinitionField = deDataDefinitionFields.get(2);

		Assert.assertEquals(false, deDataDefinitionField.isIndexable());
		Assert.assertEquals(true, deDataDefinitionField.isLocalizable());
		Assert.assertEquals(false, deDataDefinitionField.isRepeatable());
		Assert.assertEquals("salary", deDataDefinitionField.getName());
		Assert.assertEquals("numeric", deDataDefinitionField.getType());

		List<DEDataDefinitionValidationRule> deDataDefinitionValidationRules =
			deDataDefinition.getDEDataDefinitionValidationRules();

		Assert.assertEquals(
			deDataDefinitionValidationRules.toString(), 3,
			deDataDefinitionValidationRules.size());

		DEDataDefinitionValidationRule deDataDefinitionValidationRule =
			deDataDefinitionValidationRules.get(0);

		Assert.assertEquals(
			DEDataDefinitionValidationRuleConstants.EMAIL_ADDRESS_RULE,
			deDataDefinitionValidationRule.getName());
		Assert.assertArrayEquals(
			new String[] {"email"},
			ArrayUtil.toStringArray(
				deDataDefinitionValidationRule.
					getDEDataDefinitionFieldNames()));

		deDataDefinitionValidationRule = deDataDefinitionValidationRules.get(1);

		Assert.assertEquals(
			DEDataDefinitionValidationRuleConstants.REQUIRED_RULE,
			deDataDefinitionValidationRule.getName());
		Assert.assertArrayEquals(
			new String[] {"name"},
			ArrayUtil.toStringArray(
				deDataDefinitionValidationRule.
					getDEDataDefinitionFieldNames()));

		deDataDefinitionValidationRule = deDataDefinitionValidationRules.get(2);

		Assert.assertEquals(
			DEDataDefinitionValidationRuleConstants.MATCH_EXPRESSION_RULE,
			deDataDefinitionValidationRule.getName());
		Assert.assertArrayEquals(
			new String[] {"salary"},
			ArrayUtil.toStringArray(
				deDataDefinitionValidationRule.
					getDEDataDefinitionFieldNames()));

		Map<String, Object> parameters =
			deDataDefinitionValidationRule.getParameters();

		Assert.assertEquals(
			"^[0-9]+(\\.[0-9]{1,2})?", parameters.get("expression"));
	}

	@Test(expected = DEDataDefinitionDeserializerException.class)
	public void testInvalidLabel() throws Exception {
		String json = read("data-definition-deserializer-invalid-label.json");

		DEDataDefinitionDeserializerApplyRequest
			deDataDefinitionFieldsDeserializerApplyRequest =
				DEDataDefinitionDeserializerApplyRequest.Builder.of(json);

		DEDataDefinitionJSONDeserializer
			deDataDefinitionFieldsJSONDeserializer =
				new DEDataDefinitionJSONDeserializer();

		deDataDefinitionFieldsJSONDeserializer.jsonFactory =
			new JSONFactoryImpl();

		deDataDefinitionFieldsJSONDeserializer.apply(
			deDataDefinitionFieldsDeserializerApplyRequest);
	}

	@Test(expected = DEDataDefinitionDeserializerException.class)
	public void testInvalidTip() throws Exception {
		String json = read("data-definition-deserializer-invalid-tip.json");

		DEDataDefinitionDeserializerApplyRequest
			deDataDefinitionFieldsDeserializerApplyRequest =
				DEDataDefinitionDeserializerApplyRequest.Builder.of(json);

		DEDataDefinitionJSONDeserializer
			deDataDefinitionFieldsJSONDeserializer =
				new DEDataDefinitionJSONDeserializer();

		deDataDefinitionFieldsJSONDeserializer.jsonFactory =
			new JSONFactoryImpl();

		deDataDefinitionFieldsJSONDeserializer.apply(
			deDataDefinitionFieldsDeserializerApplyRequest);
	}

	@Test(expected = DEDataDefinitionDeserializerException.class)
	public void testRequiredName() throws Exception {
		String json = read("data-definition-deserializer-required-name.json");

		DEDataDefinitionDeserializerApplyRequest
			deDataDefinitionFieldsDeserializerApplyRequest =
				DEDataDefinitionDeserializerApplyRequest.Builder.of(json);

		DEDataDefinitionJSONDeserializer
			deDataDefinitionFieldsJSONDeserializer =
				new DEDataDefinitionJSONDeserializer();

		deDataDefinitionFieldsJSONDeserializer.jsonFactory =
			new JSONFactoryImpl();

		deDataDefinitionFieldsJSONDeserializer.apply(
			deDataDefinitionFieldsDeserializerApplyRequest);
	}

	@Test(expected = DEDataDefinitionDeserializerException.class)
	public void testRequiredType() throws Exception {
		String json = read("data-definition-deserializer-required-type.json");

		DEDataDefinitionDeserializerApplyRequest
			deDataDefinitionFieldsDeserializerApplyRequest =
				DEDataDefinitionDeserializerApplyRequest.Builder.of(json);

		DEDataDefinitionJSONDeserializer
			deDataDefinitionFieldsJSONDeserializer =
				new DEDataDefinitionJSONDeserializer();

		deDataDefinitionFieldsJSONDeserializer.jsonFactory =
			new JSONFactoryImpl();

		deDataDefinitionFieldsJSONDeserializer.apply(
			deDataDefinitionFieldsDeserializerApplyRequest);
	}

}
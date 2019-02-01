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

package com.liferay.data.engine.internal.rules.validation;

import com.liferay.data.engine.constants.DEDataDefinitionValidationRuleConstants;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.data.engine.rules.DEDataDefinitionValidationRuleFunctionApplyRequest;
import com.liferay.data.engine.rules.DEDataDefinitionValidationRuleFunctionApplyResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class DEEmailAddressValidationRuleFunctionTest {

	@Before
	public void setUp() {
		_deDataDefinitionValidationRuleFunctionApplyRequest =
			new DEDataDefinitionValidationRuleFunctionApplyRequest();

		_deDataDefinitionValidationRuleFunctionApplyRequest.
			setDEDataDefinitionField(_deDataDefinitionField);

		_deEmailAddressValidationRuleFunction =
			new DEEmailAddressValidationRuleFunction();
	}

	@Test
	public void testInvalidEmailAddress1() {
		_deDataDefinitionValidationRuleFunctionApplyRequest.setValue("TEXT");

		DEDataDefinitionValidationRuleFunctionApplyResponse
			deDataDefinitionValidationRuleFunctionApplyResponse =
				_deEmailAddressValidationRuleFunction.apply(
					_deDataDefinitionValidationRuleFunctionApplyRequest);

		Assert.assertFalse(
			deDataDefinitionValidationRuleFunctionApplyResponse.isValid());
		Assert.assertEquals(
			DEDataDefinitionValidationRuleConstants.INVALID_EMAIL_ADDRESS_ERROR,
			deDataDefinitionValidationRuleFunctionApplyResponse.getErrorCode());
		Assert.assertEquals(
			_deDataDefinitionField,
			deDataDefinitionValidationRuleFunctionApplyResponse.
				getDEDataDefinitionField());
	}

	@Test
	public void testInvalidEmailAddress2() {
		_deDataDefinitionValidationRuleFunctionApplyRequest.setValue(
			"TEXT,test@liferay.com");

		DEDataDefinitionValidationRuleFunctionApplyResponse
			deDataDefinitionValidationRuleFunctionApplyResponse =
				_deEmailAddressValidationRuleFunction.apply(
					_deDataDefinitionValidationRuleFunctionApplyRequest);

		Assert.assertFalse(
			deDataDefinitionValidationRuleFunctionApplyResponse.isValid());
		Assert.assertEquals(
			DEDataDefinitionValidationRuleConstants.INVALID_EMAIL_ADDRESS_ERROR,
			deDataDefinitionValidationRuleFunctionApplyResponse.getErrorCode());
		Assert.assertEquals(
			_deDataDefinitionField,
			deDataDefinitionValidationRuleFunctionApplyResponse.
				getDEDataDefinitionField());
	}

	@Test
	public void testMultipleEmailAddress() {
		_deDataDefinitionValidationRuleFunctionApplyRequest.setValue(
			"test1@liferay.com,test2@liferay.com");

		DEDataDefinitionValidationRuleFunctionApplyResponse
			deDataDefinitionValidationRuleFunctionApplyResponse =
				_deEmailAddressValidationRuleFunction.apply(
					_deDataDefinitionValidationRuleFunctionApplyRequest);

		Assert.assertTrue(
			deDataDefinitionValidationRuleFunctionApplyResponse.isValid());
		Assert.assertNull(
			deDataDefinitionValidationRuleFunctionApplyResponse.getErrorCode());
		Assert.assertEquals(
			_deDataDefinitionField,
			deDataDefinitionValidationRuleFunctionApplyResponse.
				getDEDataDefinitionField());
	}

	@Test
	public void testNullValue() {
		DEDataDefinitionValidationRuleFunctionApplyResponse
			deDataDefinitionValidationRuleFunctionApplyResponse =
				_deEmailAddressValidationRuleFunction.apply(
					_deDataDefinitionValidationRuleFunctionApplyRequest);

		Assert.assertFalse(
			deDataDefinitionValidationRuleFunctionApplyResponse.isValid());
		Assert.assertEquals(
			DEDataDefinitionValidationRuleConstants.INVALID_EMAIL_ADDRESS_ERROR,
			deDataDefinitionValidationRuleFunctionApplyResponse.getErrorCode());
		Assert.assertEquals(
			_deDataDefinitionField,
			deDataDefinitionValidationRuleFunctionApplyResponse.
				getDEDataDefinitionField());
	}

	@Test
	public void testSingleEmailAddress() {
		_deDataDefinitionValidationRuleFunctionApplyRequest.setValue(
			"test@liferay.com");

		DEDataDefinitionValidationRuleFunctionApplyResponse
			deDataDefinitionValidationRuleFunctionApplyResponse =
				_deEmailAddressValidationRuleFunction.apply(
					_deDataDefinitionValidationRuleFunctionApplyRequest);

		Assert.assertTrue(
			deDataDefinitionValidationRuleFunctionApplyResponse.isValid());
		Assert.assertNull(
			deDataDefinitionValidationRuleFunctionApplyResponse.getErrorCode());
		Assert.assertEquals(
			_deDataDefinitionField,
			deDataDefinitionValidationRuleFunctionApplyResponse.
				getDEDataDefinitionField());
	}

	private final DEDataDefinitionField _deDataDefinitionField =
		new DEDataDefinitionField("email", "text");
	private DEDataDefinitionValidationRuleFunctionApplyRequest
		_deDataDefinitionValidationRuleFunctionApplyRequest;
	private DEEmailAddressValidationRuleFunction
		_deEmailAddressValidationRuleFunction;

}
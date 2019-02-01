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

package com.liferay.data.engine.model;

import com.liferay.data.engine.constants.DEDataDefinitionValidationRuleConstants;

import java.util.List;

/**
 * This is a factory to create validation rules to be added to a data
 * definition.
 *
 * @author Leonardo
 */
public class DEDataDefinitionValidationRuleFactory {

	/**
	 * It creates a custom validation rule.
	 *
	 * @param name A name of validation rule function.
	 * The name must match the value of the property
	 * "data.definition.validation.rule.function.name" of a registered OSGI
	 * component which implements {@link
	 * com.liferay.data.engine.rules.DEDataDefinitionValidationRuleFunction}.
	 *
	 * @param deDataDefinitionFieldNames A list of data definition field's name.
	 * @return The validation rule created.
	 */
	public static DEDataDefinitionValidationRule custom(
		String name, List<String> deDataDefinitionFieldNames) {

		return new DEDataDefinitionValidationRule(
			name, deDataDefinitionFieldNames);
	}

	/**
	 * It creates a validation rule to verify if a value is a decimal number.
	 *
	 * @param deDataDefinitionFieldNames A list of data definition field's name.
	 * @return The validation rule created.
	 */
	public static DEDataDefinitionValidationRule decimalLiteral(
		List<String> deDataDefinitionFieldNames) {

		return new DEDataDefinitionValidationRule(
			DEDataDefinitionValidationRuleConstants.DECIMAL_LITERAL_RULE,
			deDataDefinitionFieldNames);
	}

	/**
	 * It creates a validation rule to verify if a value is a valid email
	 * address.
	 *
	 * @param deDataDefinitionFieldNames A list of data definition field's name.
	 * @return The validation rule created.
	 */
	public static DEDataDefinitionValidationRule emailAddress(
		List<String> deDataDefinitionFieldNames) {

		return new DEDataDefinitionValidationRule(
			DEDataDefinitionValidationRuleConstants.EMAIL_ADDRESS_RULE,
			deDataDefinitionFieldNames);
	}

	/**
	 * It crated a validation rule to verify if a value is empty.
	 *
	 * @param deDataDefinitionFieldNames A list of data definition field's name.
	 * @return The validation rule created.
	 */
	public static DEDataDefinitionValidationRule empty(
		List<String> deDataDefinitionFieldNames) {

		return new DEDataDefinitionValidationRule(
			DEDataDefinitionValidationRuleConstants.EMPTY_RULE,
			deDataDefinitionFieldNames);
	}

	/**
	 * It creates a validation rule to verify if a value is an integer number.
	 * @param deDataDefinitionFieldNames A list of data definition field's name.
	 * @return The validation rule created.
	 */
	public static DEDataDefinitionValidationRule integerLiteral(
		List<String> deDataDefinitionFieldNames) {

		return new DEDataDefinitionValidationRule(
			DEDataDefinitionValidationRuleConstants.INTEGER_LITERAL_RULE,
			deDataDefinitionFieldNames);
	}

	/**
	 * It creates a validation rule to verify if a value matches a regular
	 * expression.
	 *
	 * @param expression A regular expression.
	 * @param deDataDefinitionFieldNames A list of data definition field's name.
	 * @return The validation rule created.
	 */
	public static DEDataDefinitionValidationRule matchExpression(
		String expression, List<String> deDataDefinitionFieldNames) {

		DEDataDefinitionValidationRule deDataDefinitionValidationRule =
			new DEDataDefinitionValidationRule(
				DEDataDefinitionValidationRuleConstants.MATCH_EXPRESSION_RULE,
				deDataDefinitionFieldNames);

		deDataDefinitionValidationRule.addParameter(
			DEDataDefinitionValidationRuleConstants.EXPRESSION_PARAMETER,
			expression);

		return deDataDefinitionValidationRule;
	}

	/**
	 * It creates a validation rule to verify if a value is not null or empty.
	 *
	 * @param deDataDefinitionFieldNames A list of data definition field's name.
	 * @return The validation rule created.
	 */
	public static DEDataDefinitionValidationRule required(
		List<String> deDataDefinitionFieldNames) {

		return new DEDataDefinitionValidationRule(
			DEDataDefinitionValidationRuleConstants.REQUIRED_RULE,
			deDataDefinitionFieldNames);
	}

	/**
	 * It creates a validation rule to verify if a value is a valid URL.
	 *
	 * @param deDataDefinitionFieldNames A list of data definition field's name.
	 * @return The validation rule created.
	 */
	public static DEDataDefinitionValidationRule url(
		List<String> deDataDefinitionFieldNames) {

		return new DEDataDefinitionValidationRule(
			DEDataDefinitionValidationRuleConstants.URL_RULE,
			deDataDefinitionFieldNames);
	}

}
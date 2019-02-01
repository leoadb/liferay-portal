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
import com.liferay.data.engine.rules.DEDataDefinitionValidationRuleFunction;
import com.liferay.data.engine.rules.DEDataDefinitionValidationRuleFunctionApplyRequest;
import com.liferay.data.engine.rules.DEDataDefinitionValidationRuleFunctionApplyResponse;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * It validates if a value is a valid email address.
 *
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = "data.definition.validation.rule.function.name=" + DEDataDefinitionValidationRuleConstants.EMAIL_ADDRESS_RULE,
	service = DEDataDefinitionValidationRuleFunction.class
)
public class DEEmailAddressValidationRuleFunction
	implements DEDataDefinitionValidationRuleFunction {

	@Override
	/**
	 * @see DEDataDefinitionValidationRuleFunction
	 */
	public DEDataDefinitionValidationRuleFunctionApplyResponse apply(
		DEDataDefinitionValidationRuleFunctionApplyRequest
			deDataDefinitionValidationRuleFunctionApplyRequest) {

		DEDataDefinitionValidationRuleFunctionApplyResponse
			deDataDefinitionValidationRuleFunctionApplyResponse =
				new DEDataDefinitionValidationRuleFunctionApplyResponse();

		DEDataDefinitionField deDataDefinitionField =
			deDataDefinitionValidationRuleFunctionApplyRequest.
				getDEDataDefinitionField();

		deDataDefinitionValidationRuleFunctionApplyResponse.
			setDEDataDefinitionField(deDataDefinitionField);

		deDataDefinitionValidationRuleFunctionApplyResponse.setValid(false);
		deDataDefinitionValidationRuleFunctionApplyResponse.setErrorCode(
			DEDataDefinitionValidationRuleConstants.
				INVALID_EMAIL_ADDRESS_ERROR);

		Object value =
			deDataDefinitionValidationRuleFunctionApplyRequest.getValue();

		if (value == null) {
			return deDataDefinitionValidationRuleFunctionApplyResponse;
		}

		boolean result = Stream.of(
			StringUtil.split(value.toString(), CharPool.COMMA)
		).map(
			String::trim
		).allMatch(
			Validator::isEmailAddress
		);

		deDataDefinitionValidationRuleFunctionApplyResponse.setValid(result);

		if (result) {
			deDataDefinitionValidationRuleFunctionApplyResponse.setErrorCode(
				null);
		}

		return deDataDefinitionValidationRuleFunctionApplyResponse;
	}

}
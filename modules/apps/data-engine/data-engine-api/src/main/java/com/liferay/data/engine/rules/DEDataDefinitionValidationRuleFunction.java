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

package com.liferay.data.engine.rules;

/**
 * This is an interface that a validator must implements in order to validate
 * values of a list of data definition fields.
 *
 * @author Leonardo Barros
 */
public interface DEDataDefinitionValidationRuleFunction {

	/**
	 * It executes the validator.
	 *
	 * @param deDataDefinitionValidationRuleFunctionApplyRequest
	 * An object with parameters passed to the validator.
	 *
	 * @return An object with the output parameters.
	 */
	public DEDataDefinitionValidationRuleFunctionApplyResponse apply(
		DEDataDefinitionValidationRuleFunctionApplyRequest
			deDataDefinitionValidationRuleFunctionApplyRequest);

}
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

package com.liferay.data.engine.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class DataDefinitionRuleExpressionException
	extends DataDefinitionException {

	public DataDefinitionRuleExpressionException() {
	}

	public DataDefinitionRuleExpressionException(String msg) {
		super(msg);
	}

	public DataDefinitionRuleExpressionException(
		String expression, String message, Throwable cause) {

		super(message, cause);

		_expression = expression;
	}

	public DataDefinitionRuleExpressionException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public DataDefinitionRuleExpressionException(Throwable cause) {
		super(cause);
	}

	public String getExpression() {
		return _expression;
	}

	private String _expression;

}
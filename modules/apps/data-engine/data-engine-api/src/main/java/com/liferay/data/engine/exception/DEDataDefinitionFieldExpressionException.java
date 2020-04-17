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
public class DEDataDefinitionFieldExpressionException
	extends DEDataDefinitionException {

	public DEDataDefinitionFieldExpressionException() {
	}

	public DEDataDefinitionFieldExpressionException(String msg) {
		super(msg);
	}

	public DEDataDefinitionFieldExpressionException(
		String expression, String fieldName, String message, Throwable cause) {

		super(message);

		this.expression = expression;
		this.fieldName = fieldName;
	}

	public DEDataDefinitionFieldExpressionException(
		String msg, Throwable cause) {

		super(msg, cause);
	}

	public DEDataDefinitionFieldExpressionException(Throwable cause) {
		super(cause);
	}

	public String getExpression() {
		return expression;
	}

	public String getFieldName() {
		return fieldName;
	}

	protected String expression;
	protected String fieldName;

}
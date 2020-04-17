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

import com.liferay.petra.string.StringPool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 */
public class DEDataDefinitionValidationExpressionException
	extends DEDataDefinitionFieldExpressionException {

	public DEDataDefinitionValidationExpressionException() {
	}

	public DEDataDefinitionValidationExpressionException(String msg) {
		super(msg);
	}

	public DEDataDefinitionValidationExpressionException(
		String expression, String fieldName, Throwable cause) {

		super("validation", fieldName, expression, cause);
	}

	public DEDataDefinitionValidationExpressionException(
		String msg, Throwable cause) {

		super(msg, cause);
	}

	public DEDataDefinitionValidationExpressionException(Throwable cause) {
		super(cause);
	}

	public String getValidationExpression() {
		return expression;
	}

	public String getValidationExpressionArgument() {
		Matcher matcher = _validationExpressionPattern.matcher(expression);

		if (matcher.find()) {
			return matcher.group(3);
		}

		return StringPool.BLANK;
	}

	private static final Pattern _validationExpressionPattern = Pattern.compile(
		"(contains|match)\\((.+), \"(.+)\"\\)");

}
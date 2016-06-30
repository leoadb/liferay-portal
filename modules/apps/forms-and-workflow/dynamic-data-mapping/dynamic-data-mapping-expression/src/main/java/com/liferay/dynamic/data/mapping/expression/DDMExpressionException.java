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

package com.liferay.dynamic.data.mapping.expression;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marcellus Tavares
 */
public class DDMExpressionException extends PortalException {

	public DDMExpressionException() {
	}

	public DDMExpressionException(String msg) {
		super(msg);
	}

	public DDMExpressionException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public DDMExpressionException(Throwable cause) {
		super(cause);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static class FunctionNotAllowed extends DDMExpressionException {

		public FunctionNotAllowed(String functionName) {
			super(
				String.format(
					"The function name \"%s\" is not allowed", functionName));

			_functionName = functionName;
		}

		public String getFunctionName() {
			return _functionName;
		}

		private final String _functionName;

	}

	public static class FunctionNotDefined extends DDMExpressionException {

		public FunctionNotDefined(String functionName) {
			super(
				String.format(
					"The function name \"%s\" was not defined", functionName));

			_functionName = functionName;
		}

		public String getFunctionName() {
			return _functionName;
		}

		private final String _functionName;

	}

	public static class IncompatipleReturnType extends DDMExpressionException {

		public IncompatipleReturnType() {
			super(
				"The evaluation return type differs from DDM Expression type");
		}

	}

	public static class InvalidSyntax extends DDMExpressionException {

		public InvalidSyntax(Throwable cause) {
			super("The expression syntax is invalid", cause);
		}

	}

	public static class NumberExceedsSupportedRange
		extends DDMExpressionException {

		public NumberExceedsSupportedRange() {
			super("The number entered exceeds the supported range");
		}

	}

}
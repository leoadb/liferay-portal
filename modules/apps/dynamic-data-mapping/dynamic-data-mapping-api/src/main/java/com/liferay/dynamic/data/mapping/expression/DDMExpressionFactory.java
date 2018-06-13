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

import aQute.bnd.annotation.ProviderType;

/**
 * @author Marcellus Tavares
 * @author Leonardo Barros
 */
@ProviderType
public interface DDMExpressionFactory {

	/**
	 * @deprecated As of 4.0.0, replaced by {@link
	 * DDMExpressionFactory#createDDMExpression(String)}
	 */
	@Deprecated
	public default DDMExpression<Boolean> createBooleanDDMExpression(
			String ddmExpressionString)
		throws DDMExpressionException {

		return createDDMExpression(ddmExpressionString);
	}

	public <T> DDMExpression<T> createDDMExpression(String expression)
		throws DDMExpressionException;

	/**
	 * @deprecated As of 4.0.0, replaced by {@link
	 * DDMExpressionFactory#createDDMExpression(String)}
	 */
	@Deprecated
	public default DDMExpression<Double> createDoubleDDMExpression(
			String ddmExpressionString)
		throws DDMExpressionException {

		return createDDMExpression(ddmExpressionString);
	}

	/**
	 * @deprecated As of 4.0.0, replaced by {@link
	 * DDMExpressionFactory#createDDMExpression(String)}
	 */
	@Deprecated
	public default DDMExpression<Float> createFloatDDMExpression(
			String ddmExpressionString)
		throws DDMExpressionException {

		return createDDMExpression(ddmExpressionString);
	}

	/**
	 * @deprecated As of 4.0.0, replaced by {@link
	 * DDMExpressionFactory#createDDMExpression(String)}
	 */
	@Deprecated
	public default DDMExpression<Integer> createIntegerDDMExpression(
			String ddmExpressionString)
		throws DDMExpressionException {

		return createDDMExpression(ddmExpressionString);
	}

	/**
	 * @deprecated As of 4.0.0, replaced by {@link
	 * DDMExpressionFactory#createDDMExpression(String)}
	 */
	@Deprecated
	public default DDMExpression<Long> createLongDDMExpression(
			String ddmExpressionString)
		throws DDMExpressionException {

		return createDDMExpression(ddmExpressionString);
	}

	/**
	 * @deprecated As of 4.0.0, replaced by {@link
	 * DDMExpressionFactory#createDDMExpression(String)}
	 */
	@Deprecated
	public default DDMExpression<Number> createNumberDDMExpression(
			String ddmExpressionString)
		throws DDMExpressionException {

		return createDDMExpression(ddmExpressionString);
	}

	/**
	 * @deprecated As of 4.0.0, replaced by {@link
	 * DDMExpressionFactory#createDDMExpression(String)}
	 */
	@Deprecated
	public default DDMExpression<String> createStringDDMExpression(
			String ddmExpressionString)
		throws DDMExpressionException {

		return createDDMExpression(ddmExpressionString);
	}

}
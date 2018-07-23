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

import com.liferay.dynamic.data.mapping.expression.model.Expression;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Miguel Angelo Caldas Gallindo
 * @author Leonardo Barros
 */
@ProviderType
public interface DDMExpression<T> {

	public void addFunctions(
		Map<String, DDMExpressionFunction> ddmExpressionFunctions);

	public void addObserver(DDMExpressionObserver ddmExpressionObserver);

	public T evaluate() throws DDMExpressionException;

	public Expression getModel();

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public default Map<String, VariableDependencies>
			getVariableDependenciesMap()
		throws DDMExpressionException {

		return Collections.emptyMap();
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link DDMExpression#setVariable(
	 * String, Object)}
	 */
	@Deprecated
	public default void setBooleanVariableValue(
		String variableName, Boolean variableValue) {

		setVariable(variableName, variableValue);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link DDMExpression#addFunctions(
	 * Map)}
	 */
	@Deprecated
	public default void setDDMExpressionFunction(
		String functionName, DDMExpressionFunction ddmExpressionFunction) {

		Map<String, DDMExpressionFunction> ddmExpressionFunctions =
			new HashMap() {
				{
					put(functionName, ddmExpressionFunction);
				}
			};

		addFunctions(ddmExpressionFunctions);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link DDMExpression#setVariable(
	 * String, Object)}
	 */
	@Deprecated
	public default void setDoubleVariableValue(
		String variableName, Double variableValue) {

		setVariable(variableName, variableValue);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link DDMExpression#setVariable(
	 * String, Object)}
	 */
	@Deprecated
	public default void setExpressionStringVariableValue(
		String variableName, String variableValue) {

		setVariable(variableName, variableValue);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link DDMExpression#setVariable(
	 * String, Object)}
	 */
	@Deprecated
	public default void setFloatVariableValue(
		String variableName, Float variableValue) {

		setVariable(variableName, variableValue);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link DDMExpression#setVariable(
	 * String, Object)}
	 */
	@Deprecated
	public default void setIntegerVariableValue(
		String variableName, Integer variableValue) {

		setVariable(variableName, variableValue);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link DDMExpression#setVariable(
	 * String, Object)}
	 */
	@Deprecated
	public default void setLongVariableValue(
		String variableName, Long variableValue) {

		setVariable(variableName, variableValue);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link DDMExpression#setVariable(
	 * String, Object)}
	 */
	@Deprecated
	public default void setNumberVariableValue(
		String variableName, Number variableValue) {

		setVariable(variableName, variableValue);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link DDMExpression#setVariable(
	 * String, Object)}
	 */
	@Deprecated
	public default void setObjectVariableValue(
		String variableName, Object variableValue) {

		setVariable(variableName, variableValue);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link DDMExpression#setVariable(
	 * String, Object)}
	 */
	@Deprecated
	public default void setStringVariableValue(
			String variableName, String variableValue)
		throws DDMExpressionException {

		setVariable(variableName, variableValue);
	}

	public void setVariable(String name, Object value);

}
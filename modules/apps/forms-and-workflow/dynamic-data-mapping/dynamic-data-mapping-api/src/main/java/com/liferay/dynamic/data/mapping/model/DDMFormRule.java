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

package com.liferay.dynamic.data.mapping.model;

import java.io.Serializable;

/**
 * @author Leonardo Barros
 */
public class DDMFormRule implements Serializable {

	public DDMFormRule(DDMFormRule ddmFormRule) {
		_expression = ddmFormRule._expression;
		_ddmFormRuleType = ddmFormRule._ddmFormRuleType;
	}

	public DDMFormRule(String expression, DDMFormRuleType ddmFormRuleType) {
		_expression = expression;
		_ddmFormRuleType = ddmFormRuleType;
	}

	public DDMFormRuleType getDDMFormFieldRuleType() {
		return _ddmFormRuleType;
	}

	public String getExpression() {
		return _expression;
	}

	public void setDDMFormRuleType(DDMFormRuleType ddmFormRuleType) {
		_ddmFormRuleType = ddmFormRuleType;
	}

	public void setExpression(String expression) {
		_expression = expression;
	}

	private DDMFormRuleType _ddmFormRuleType;
	private String _expression;

}
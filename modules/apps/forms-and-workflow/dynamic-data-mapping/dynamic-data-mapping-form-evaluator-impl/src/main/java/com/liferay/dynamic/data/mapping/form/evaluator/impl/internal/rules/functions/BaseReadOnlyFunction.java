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

package com.liferay.dynamic.data.mapping.form.evaluator.impl.internal.rules.functions;

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public abstract class BaseReadOnlyFunction extends BasePropertyFunction {

	public BaseReadOnlyFunction(
		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResults, boolean readOnly) {

		super(ddmFormFieldEvaluationResults);

		_readOnly = readOnly;
	}

	@Override
	public Object evaluate(Object... parameters) {
		if (ArrayUtil.isEmpty(parameters) || (parameters.length != 1)) {
			throw new IllegalArgumentException("Expected 1 parameter.");
		}

		String fieldName = String.valueOf(parameters[0]);

		List<DDMFormFieldEvaluationResult> formFieldEvaluationResults =
			ddmFormFieldEvaluationResults.get(fieldName);

		if (ListUtil.isEmpty(formFieldEvaluationResults)) {
			return true;
		}

		for (DDMFormFieldEvaluationResult formFieldEvaluationResult :
				formFieldEvaluationResults) {

			formFieldEvaluationResult.setReadOnly(_readOnly);
		}

		return true;
	}

	private final boolean _readOnly;

}
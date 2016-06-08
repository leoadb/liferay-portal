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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.rules.type;

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.rules.DDMFormRuleEvaluatorContext;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldRuleType;

import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DataProviderRule extends BaseRule {

	public DataProviderRule(
		String ddmFormFieldName, String instanceId, String expression,
		DDMFormRuleEvaluatorContext ddmFormRuleEvaluatorContext) {

		super(
			ddmFormFieldName, instanceId, DDMFormFieldRuleType.DATA_PROVIDER,
			expression, ddmFormRuleEvaluatorContext);
	}

	@Override
	public void execute() {
		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultMap =
				ddmFormRuleEvaluatorContext.getDDMFormFieldEvaluationResults();

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			ddmFormFieldEvaluationResultMap.get(getDDMFormFieldName());

		try {
			executeFunctions();
		}
		catch (Exception e) {
			ddmFormFieldEvaluationResult.setValid(false);
		}
	}

}
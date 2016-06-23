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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.rules;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderTracker;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationException;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.rules.type.Rule;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.rules.type.RuleFactory;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldRuleType;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DDMFormRuleEvaluator {

	public DDMFormRuleEvaluator(
		DDMDataProviderInstanceService ddmDataProviderInstanceService,
		DDMDataProviderTracker ddmDataProviderTracker,
		DDMExpressionFactory ddmExpressionFactory, DDMForm ddmForm,
		DDMFormRuleEvaluatorGraph ddmFormRuleEvaluatorGraph,
		DDMFormValues ddmFormValues,
		DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer,
		Locale locale) {

		_ddmDataProviderInstanceService = ddmDataProviderInstanceService;
		_ddmDataProviderTracker = ddmDataProviderTracker;
		_ddmExpressionFactory = ddmExpressionFactory;
		_ddmForm = ddmForm;
		_ddmFormRuleEvaluatorGraph = ddmFormRuleEvaluatorGraph;
		_ddmFormValues = ddmFormValues;
		_ddmFormValuesJSONDeserializer = ddmFormValuesJSONDeserializer;
		_locale = locale;
	}

	public List<DDMFormFieldEvaluationResult> evaluate()
		throws DDMFormEvaluationException {

		addDDMFormFieldRuleEvaluationResults();

		List<DDMFormRuleEvaluatorNode> nodes =
			_ddmFormRuleEvaluatorGraph.sort();

		for (DDMFormRuleEvaluatorNode node : nodes) {
			evaluateDDMFormRuleEvaluatorNode(node);
		}

		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults =
			new ArrayList<>();

		for (Map.Entry<String, Map<String, DDMFormFieldEvaluationResult>>
				entry: _ddmFormFieldEvaluationResults.entrySet()) {

			ddmFormFieldEvaluationResults.addAll(
				ListUtil.fromCollection(entry.getValue().values()));
		}

		return ddmFormFieldEvaluationResults;
	}

	protected void addDDMFormFieldRuleEvaluationResults() {
		Map<String, DDMFormField> ddmFormFieldsMap =
			_ddmForm.getDDMFormFieldsMap(true);

		for (DDMFormField ddmFormField : ddmFormFieldsMap.values()) {
			createDDMFormFieldRuleEvaluationResult(ddmFormField);
		}
	}

	protected void createDDMFormFieldRuleEvaluationResult(
		DDMFormField ddmFormField) {

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			_ddmFormValues.getDDMFormFieldValuesMap();

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			ddmFormField.getName());

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultInstanceMap = new HashMap<>();

		_ddmFormFieldEvaluationResults.put(
			ddmFormField.getName(), ddmFormFieldEvaluationResultInstanceMap);

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
				new DDMFormFieldEvaluationResult(
					ddmFormField.getName(), ddmFormFieldValue.getInstanceId());

			ddmFormFieldEvaluationResult.setErrorMessage(StringPool.BLANK);
			ddmFormFieldEvaluationResult.setReadOnly(ddmFormField.isReadOnly());
			ddmFormFieldEvaluationResult.setValid(true);
			ddmFormFieldEvaluationResult.setVisible(true);

			Value value = ddmFormFieldValue.getValue();

			String valueString = StringPool.BLANK;

			if (value != null) {
				valueString = GetterUtil.getString(value.getString(_locale));
			}

			ddmFormFieldEvaluationResult.setValue(valueString);

			ddmFormFieldEvaluationResultInstanceMap.put(
				ddmFormFieldValue.getInstanceId(),
				ddmFormFieldEvaluationResult);
		}
	}

	protected void evaluateDDMFormRuleEvaluatorNode(
			DDMFormRuleEvaluatorNode node)
		throws DDMFormEvaluationException {

		String expression = node.getExpression();

		DDMFormFieldRuleType ddmFormFieldRuleType =
			node.getDDMFormFieldRuleType();

		String ddmFormFieldName = node.getDDMFormFieldName();

		Map<String, DDMFormField> ddmFormFieldsMap =
			_ddmForm.getDDMFormFieldsMap(true);

		DDMFormField ddmFormField = ddmFormFieldsMap.get(ddmFormFieldName);

		String instanceId = node.getInstanceId();

		Rule rule = RuleFactory.createDDMFormFieldRule(
			expression, _ddmExpressionFactory, _ddmDataProviderInstanceService,
			_ddmDataProviderTracker, _ddmFormFieldEvaluationResults,
			ddmFormField, ddmFormFieldRuleType, _ddmFormValuesJSONDeserializer,
			instanceId, _locale);

		rule.evaluate();
	}

	private final DDMDataProviderInstanceService
		_ddmDataProviderInstanceService;
	private final DDMDataProviderTracker _ddmDataProviderTracker;
	private final DDMExpressionFactory _ddmExpressionFactory;
	private final DDMForm _ddmForm;
	private final Map<String, Map<String, DDMFormFieldEvaluationResult>>
		_ddmFormFieldEvaluationResults = new HashMap<>();
	private final DDMFormRuleEvaluatorGraph _ddmFormRuleEvaluatorGraph;
	private final DDMFormValues _ddmFormValues;
	private final DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;
	private final Locale _locale;

}
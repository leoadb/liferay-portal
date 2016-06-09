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

import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.expression.VariableDependencies;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.rules.function.Function;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.rules.function.FunctionFactory;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.rules.type.Rule;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.rules.type.RuleFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldRuleType;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Leonardo Barros
 */
public class DDMFormRuleEvaluatorGraphHelper {

	public DDMFormRuleEvaluatorGraphHelper(
		DDMFormRuleEvaluatorContext ddmFormRuleEvaluatorContext) {

		_ddmFormRuleEvaluatorContext = ddmFormRuleEvaluatorContext;
	}

	public void addDDMFormFieldRuleEvaluationResults() {
		Map<String, DDMFormField> ddmFormFieldsMap = getDDMFormFieldsMap();

		for (DDMFormField ddmFormField : ddmFormFieldsMap.values()) {
			createDDMFormFieldRuleEvaluationResult(ddmFormField);
		}
	}

	public Set<DDMFormRuleEvaluatorNode>
		createDDMFormRuleEvaluatorNodeEdges(String expression) {

		Set<DDMFormRuleEvaluatorNode> edges = new HashSet<>();

		edges.addAll(createDataProviderEdges(expression));
		edges.addAll(createVisibilityEdges(expression));
		edges.addAll(createReadOnlyEdges(expression));
		edges.addAll(createValidationEdges(expression));
		edges.addAll(createValueEdges(expression));

		return edges;
	}

	public void executeDDMFormRuleEvaluatorNode(DDMFormRuleEvaluatorNode node)
		throws Exception {

		String expression = node.getExpression();

		DDMFormFieldRuleType ddmFormFieldRuleType =
			node.getDDMFormFieldRuleType();

		String ddmFormFieldName = node.getDDMFormFieldName();

		String instanceId = node.getInstanceId();

		Rule ddmFormFieldRule = RuleFactory.createDDMFormFieldRule(
			ddmFormFieldName, instanceId, expression, ddmFormFieldRuleType,
			_ddmFormRuleEvaluatorContext);

		ddmFormFieldRule.execute();
	}

	public Map<String, DDMFormFieldEvaluationResult>
		getDDMFormFieldEvaluationResults() {

		return _ddmFormRuleEvaluatorContext.getDDMFormFieldEvaluationResults();
	}

	public boolean hasDependencies(String expression) throws Exception {
		if (Validator.isNull(expression)) {
			return false;
		}

		DDMExpression<Boolean> ddmExpression =
			getDDMExpressionFactory().createBooleanDDMExpression(expression);

		Map<String, VariableDependencies> dependenciesMap =
			ddmExpression.getVariableDependenciesMap();

		Map<String, DDMFormField> ddmFormFieldsMap = getDDMFormFieldsMap();

		for (String ddmFormFieldName : ddmFormFieldsMap.keySet()) {
			if (dependenciesMap.containsKey(ddmFormFieldName)) {
				return true;
			}
		}

		return false;
	}

	protected Set<DDMFormRuleEvaluatorNode> createDataProviderEdges(
		String expression) {

		Set<DDMFormRuleEvaluatorNode> edges = new HashSet<>();

		for (String patternStr :
				FunctionFactory.getDataProviderFunctionPatterns()) {

			edges.addAll(
				createEdges(
					expression, patternStr,
					DDMFormFieldRuleType.DATA_PROVIDER));
		}

		return edges;
	}

	protected void createDDMFormFieldRuleEvaluationResult(
		DDMFormField ddmFormField) {

		DDMFormValues ddmFormValues = getDDMFormValues();

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			ddmFormField.getName());

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResults = getDDMFormFieldEvaluationResults();

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
				new DDMFormFieldEvaluationResult(
					ddmFormField.getName(), ddmFormFieldValue.getInstanceId());

			ddmFormFieldEvaluationResult.setErrorMessage(StringPool.BLANK);
			ddmFormFieldEvaluationResult.setReadOnly(false);
			ddmFormFieldEvaluationResult.setValid(true);
			ddmFormFieldEvaluationResult.setVisible(true);
			ddmFormFieldEvaluationResult.setValue(
				ddmFormFieldValue.getValue().getString(getLocale()));

			ddmFormFieldEvaluationResults.put(
				ddmFormField.getName(), ddmFormFieldEvaluationResult);
		}
	}

	protected Set<DDMFormRuleEvaluatorNode> createEdges(
		String expression, String patternStr,
		DDMFormFieldRuleType ddmFormFieldRuleType) {

		Set<DDMFormRuleEvaluatorNode> edges = new HashSet<>();

		Pattern pattern = Pattern.compile(patternStr);

		Matcher matcher = pattern.matcher(expression);

		if (matcher.find()) {
			Set<String> ddmFormFieldNames = extractDDMFormFieldName(
				matcher.group(1));

			for (String ddmFormFieldName : ddmFormFieldNames) {
				DDMFormRuleEvaluatorNode ddmFormRuleEvaluatorNode =
					new DDMFormRuleEvaluatorNode(
						ddmFormFieldName, StringPool.BLANK,
						ddmFormFieldRuleType, StringPool.BLANK);

				edges.add(ddmFormRuleEvaluatorNode);
			}
		}

		return edges;
	}

	protected Set<DDMFormRuleEvaluatorNode> createReadOnlyEdges(
		String expression) {

		String patternStr = FunctionFactory.getReadOnlyFunctionPattern();

		return createEdges(
			expression, patternStr, DDMFormFieldRuleType.READ_ONLY);
	}

	protected Set<DDMFormRuleEvaluatorNode> createValidationEdges(
		String expression) {

		Set<DDMFormRuleEvaluatorNode> edges = new HashSet<>();

		for (String patternStr :
				FunctionFactory.getValidationFunctionPatterns()) {

			edges.addAll(
				createEdges(
					expression, patternStr, DDMFormFieldRuleType.VALIDATION));
		}

		return edges;
	}

	protected Set<DDMFormRuleEvaluatorNode> createValueEdges(
		String expression) {

		Set<DDMFormRuleEvaluatorNode> edges = new HashSet<>();

		for (String patternStr : FunctionFactory.getValueFunctionPatterns()) {
			edges.addAll(
				createEdges(
					expression, patternStr, DDMFormFieldRuleType.VALUE));
		}

		return edges;
	}

	protected Set<DDMFormRuleEvaluatorNode> createVisibilityEdges(
		String expression) {

		String patternStr = FunctionFactory.getVisibilityFunctionPattern();

		return createEdges(
			expression, patternStr, DDMFormFieldRuleType.VISIBILITY);
	}

	protected Set<String> extractDDMFormFieldName(String innerExpression) {
		Set<String> ddmFormFieldNames = new HashSet<>();

		Map<String, DDMFormField> ddmFormFieldsMap = getDDMFormFieldsMap();

		Pattern pattern = Pattern.compile(Function.VARIABLE_PATTERN);

		Matcher matcher = pattern.matcher(innerExpression);

		while (matcher.find()) {
			String variableName = null;

			for (int i = 1; i <= matcher.groupCount(); i++) {
				variableName = matcher.group(i);

				if (ddmFormFieldsMap.containsKey(variableName)) {
					ddmFormFieldNames.add(variableName);
				}
			}
		}

		return ddmFormFieldNames;
	}

	protected DDMExpressionFactory getDDMExpressionFactory() {
		return _ddmFormRuleEvaluatorContext.getDDMExpressionFactory();
	}

	protected DDMForm getDDMForm() {
		return _ddmFormRuleEvaluatorContext.getDDMForm();
	}

	protected List<DDMFormField> getDDMFormFields() {
		return _ddmFormRuleEvaluatorContext.getDDMFormFields();
	}

	protected Map<String, DDMFormField> getDDMFormFieldsMap() {
		DDMForm ddmForm = getDDMForm();

		return ddmForm.getDDMFormFieldsMap(true);
	}

	protected DDMFormValues getDDMFormValues() {
		return _ddmFormRuleEvaluatorContext.getDDMFormValues();
	}

	protected Locale getLocale() {
		return _ddmFormRuleEvaluatorContext.getLocale();
	}

	private final DDMFormRuleEvaluatorContext _ddmFormRuleEvaluatorContext;

}
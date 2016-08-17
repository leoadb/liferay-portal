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
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationException;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.FieldConstants;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DDMFormRuleEvaluatorHelper {

	public DDMFormRuleEvaluatorHelper(
		DDMExpressionFactory ddmExpressionFactory, DDMForm ddmForm,
		DDMFormValues ddmFormValues, JSONFactory jsonFactory, Locale locale) {

		_ddmExpressionFactory = ddmExpressionFactory;
		_ddmForm = ddmForm;

		if (ddmFormValues == null) {
			ddmFormValues = createEmptyDDMFormValues(_ddmForm);
		}

		createDDMFormFieldValuesMap(ddmFormValues);

		_jsonFactory = jsonFactory;
		_locale = locale;
	}

	public List<DDMFormFieldEvaluationResult> evaluate()
		throws DDMFormEvaluationException {

		addDDMFormFieldRuleEvaluationResults();

		addDDMFormRuleForVisibilityAndValidation();

		List<DDMFormRule> ddmFormRules = _ddmForm.getDDMFormRules();

		if (Validator.isNull(ddmFormRules)) {
			return getDDMFormFieldEvaluationResults();
		}

		for (DDMFormRule ddmFormRule : ddmFormRules) {
			if (!ddmFormRule.isEnabled()) {
				continue;
			}

			if (evaluate(ddmFormRule)) {
				executeActions(ddmFormRule.getActions());
			}
		}

		return getDDMFormFieldEvaluationResults();
	}

	protected void addDDMFormFieldRuleEvaluationResults() {
		List<DDMFormField> ddmFormFields = _ddmForm.getDDMFormFields();

		for (DDMFormField ddmFormField : ddmFormFields) {
			createDDMFormFieldRuleEvaluationResult(ddmFormField);
		}
	}

	protected void addDDMFormRuleForVisibilityAndValidation() {
		Map<String, DDMFormField> ddmFormFieldMap =
			_ddmForm.getDDMFormFieldsMap(true);

		try {
			for (DDMFormField ddmFormField : ddmFormFieldMap.values()) {
				String visibilityExpressionProperty =
					ddmFormField.getVisibilityExpression();

				if (Validator.isNotNull(visibilityExpressionProperty) &&
					!visibilityExpressionProperty.equals("TRUE")) {

					String visibilityExpression = String.valueOf(
						visibilityExpressionProperty);

					createDDMFormRule(
						ddmFormField.getName(), visibilityExpression);
				}

				DDMFormFieldValidation validationProperty =
					ddmFormField.getDDMFormFieldValidation();

				if (validationProperty != null) {
					_createDDMFormRule(
						ddmFormField.getName(), validationProperty);
				}
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e);
			}
		}
	}

	protected DDMFormFieldEvaluationResult createDDMFormFieldEvaluationResult(
		DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue) {

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult(
				ddmFormField.getName(), ddmFormFieldValue.getInstanceId());

		ddmFormFieldEvaluationResult.setErrorMessage(StringPool.BLANK);
		ddmFormFieldEvaluationResult.setReadOnly(ddmFormField.isReadOnly());
		ddmFormFieldEvaluationResult.setValid(true);
		ddmFormFieldEvaluationResult.setVisible(true);

		Value value = ddmFormFieldValue.getValue();

		String valueString = getValueString(value, ddmFormField.getType());

		if (ddmFormField.getDataType().equals(FieldConstants.NUMBER)) {
			ddmFormFieldEvaluationResult.setValue(Double.valueOf(valueString));
		}
		else if (ddmFormField.getDataType().equals(FieldConstants.INTEGER)) {
			ddmFormFieldEvaluationResult.setValue(Integer.valueOf(valueString));
		}
		else if (ddmFormField.getDataType().equals(FieldConstants.BOOLEAN)) {
			ddmFormFieldEvaluationResult.setValue(Boolean.valueOf(valueString));
		}
		else {
			ddmFormFieldEvaluationResult.setValue(valueString);
		}

		return ddmFormFieldEvaluationResult;
	}

	protected void createDDMFormFieldRuleEvaluationResult(
		DDMFormField ddmFormField) {

		List<DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultInstances = null;

		if (!_ddmFormFieldEvaluationResults.containsKey(
				ddmFormField.getName())) {

			ddmFormFieldEvaluationResultInstances = new ArrayList<>();

			_ddmFormFieldEvaluationResults.put(
				ddmFormField.getName(), ddmFormFieldEvaluationResultInstances);
		}
		else {
			ddmFormFieldEvaluationResultInstances =
				_ddmFormFieldEvaluationResults.get(ddmFormField.getName());
		}

		List<DDMFormFieldValue> ddmFormFieldValues = _ddmFormFieldValuesMap.get(
			ddmFormField.getName());

		if (ddmFormFieldValues == null) {
			return;
		}

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
				createDDMFormFieldEvaluationResult(
					ddmFormField, ddmFormFieldValue);

			if (!ddmFormFieldEvaluationResultInstances.contains(
					ddmFormFieldEvaluationResult)) {

				ddmFormFieldEvaluationResultInstances.add(
					ddmFormFieldEvaluationResult);
			}

			for (DDMFormFieldValue nestedDDMFormFieldValue :
					ddmFormFieldValue.getNestedDDMFormFieldValues()) {

				DDMFormField nestedDDMFormField =
					nestedDDMFormFieldValue.getDDMFormField();

				createDDMFormFieldRuleEvaluationResult(nestedDDMFormField);
			}
		}
	}

	protected void createDDMFormFieldValuesMap(DDMFormValues ddmFormValues) {
		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		_ddmFormFieldValuesMap = new LinkedHashMap<>();

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			putDDMFormFieldValue(ddmFormFieldValue);
		}
	}

	protected void createDDMFormRule(
			String ddmFormFieldName, String visibilityExpression)
		throws Exception {

		String action = String.format(
			"set(fieldAt(\"%s\", 0), \"visible\", %s)", ddmFormFieldName,
			translateExpression(visibilityExpression));

		List<String> actions = new ArrayList<>();
		actions.add(action);

		DDMFormRule ddmFormRule = new DDMFormRule("TRUE", actions);
		_ddmForm.addDDMFormRule(ddmFormRule);
	}

	protected DDMFormFieldValue createDefaultDDMFormFieldValue(
		DDMFormField ddmFormField) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName(ddmFormField.getName());

		Value value = new UnlocalizedValue(StringPool.BLANK);

		if (ddmFormField.isLocalizable()) {
			value = new LocalizedValue(_locale);

			value.addString(_locale, StringPool.BLANK);
		}

		ddmFormFieldValue.setValue(value);
		return ddmFormFieldValue;
	}

	protected DDMFormValues createEmptyDDMFormValues(DDMForm ddmForm) {
		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			DDMFormFieldValue ddmFormFieldValue =
				createDefaultDDMFormFieldValue(ddmFormField);

			ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
		}

		return ddmFormValues;
	}

	protected boolean evaluate(DDMFormRule ddmFormRule)
		throws DDMFormEvaluationException {

		DDMFormRuleEvaluator ddmFormRuleEvaluator = new DDMFormRuleEvaluator(
			_ddmExpressionFactory, _ddmFormFieldEvaluationResults,
			ddmFormRule.getCondition());

		return ddmFormRuleEvaluator.evaluate();
	}

	protected void executeActions(List<String> actions)
		throws DDMFormEvaluationException {

		for (String action : actions) {
			DDMFormRuleEvaluator ddmFormRuleEvaluator =
				new DDMFormRuleEvaluator(
					_ddmExpressionFactory, _ddmFormFieldEvaluationResults,
					action);

			ddmFormRuleEvaluator.execute();
		}
	}

	protected List<DDMFormFieldEvaluationResult>
		getDDMFormFieldEvaluationResults() {

		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults =
			new ArrayList<>();

		for (List<DDMFormFieldEvaluationResult>
				ddmFormFieldEvaluationResultInstances :
					_ddmFormFieldEvaluationResults.values()) {

			ddmFormFieldEvaluationResults.addAll(
				ddmFormFieldEvaluationResultInstances);
		}

		return ddmFormFieldEvaluationResults;
	}

	protected String getJSONArrayValueString(String valueString) {
		try {
			JSONArray jsonArray = _jsonFactory.createJSONArray(valueString);

			return jsonArray.getString(0);
		}
		catch (JSONException jsone) {
			return valueString;
		}
	}

	protected String getValueString(Value value, String type) {
		if (value == null) {
			return null;
		}

		String valueString = GetterUtil.getString(value.getString(_locale));

		if (type.equals("select") || type.equals("radio")) {
			valueString = getJSONArrayValueString(valueString);
		}

		return valueString;
	}

	protected void putDDMFormFieldValue(DDMFormFieldValue ddmFormFieldValue) {
		List<DDMFormFieldValue> ddmFormFieldValues = _ddmFormFieldValuesMap.get(
			ddmFormFieldValue.getName());

		if (ddmFormFieldValues == null) {
			ddmFormFieldValues = new ArrayList<>();

			_ddmFormFieldValuesMap.put(
				ddmFormFieldValue.getName(), ddmFormFieldValues);
		}

		ddmFormFieldValues.add(ddmFormFieldValue);

		for (DDMFormFieldValue nestedDDMFormFieldValue :
				ddmFormFieldValue.getNestedDDMFormFieldValues()) {

			putDDMFormFieldValue(nestedDDMFormFieldValue);
		}
	}

	protected String translateExpression(String expressionStr)
		throws Exception {

		DDMExpression<Boolean> expression =
			_ddmExpressionFactory.createBooleanDDMExpression(expressionStr);

		Map<String, VariableDependencies> variableDependenciesMap =
			expression.getVariableDependenciesMap();

		for (String variable : variableDependenciesMap.keySet()) {
			if (_ddmFormFieldEvaluationResults.containsKey(variable)) {
				expressionStr = expressionStr.replaceAll(
					String.format(
						"([,\\s\\(]+|.*)(%s)([,\\s\\)]+|.*)", variable),
					String.format(
						"$1get(fieldAt(\"%s\", 0), \"value\")$3", variable));
			}
		}

		return expressionStr;
	}

	private void _createDDMFormRule(
			String ddmFormFieldName, DDMFormFieldValidation validation)
		throws Exception {

		if (Validator.isNull(validation.getExpression())) {
			return;
		}

		String errorMessage = validation.getErrorMessage();

		errorMessage = errorMessage.replace(
			StringPool.QUOTE, StringPool.DOUBLE_APOSTROPHE);

		String action = String.format(
			"set(fieldAt(\"%s\", 0), \"valid\", %s, \"%s\")", ddmFormFieldName,
			translateExpression(validation.getExpression()), errorMessage);

		List<String> actions = new ArrayList<>();
		actions.add(action);

		DDMFormRule ddmFormRule = new DDMFormRule("TRUE", actions);
		_ddmForm.addDDMFormRule(ddmFormRule);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormRuleEvaluatorHelper.class);

	private final DDMExpressionFactory _ddmExpressionFactory;
	private final DDMForm _ddmForm;
	private final Map<String, List<DDMFormFieldEvaluationResult>>
		_ddmFormFieldEvaluationResults = new HashMap<>();
	private Map<String, List<DDMFormFieldValue>> _ddmFormFieldValuesMap;
	private final JSONFactory _jsonFactory;
	private final Locale _locale;

}
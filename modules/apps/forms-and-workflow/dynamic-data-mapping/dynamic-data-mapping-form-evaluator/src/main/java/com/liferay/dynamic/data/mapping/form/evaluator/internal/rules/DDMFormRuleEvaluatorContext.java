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
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DDMFormRuleEvaluatorContext {

	public DDMFormRuleEvaluatorContext(
		DDMDataProviderInstanceService ddmDataProviderInstanceService,
		DDMDataProviderTracker ddmDataProviderTracker,
		DDMExpressionFactory ddmExpressionFactory, DDMForm ddmForm,
		DDMFormValues ddmFormValues,
		DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer,
		Locale locale) {

		_ddmDataProviderInstanceService = ddmDataProviderInstanceService;
		_ddmDataProviderTracker = ddmDataProviderTracker;
		_ddmExpressionFactory = ddmExpressionFactory;
		_ddmForm = ddmForm;
		_ddmFormValues = ddmFormValues;
		_ddmFormValuesJSONDeserializer = ddmFormValuesJSONDeserializer;
		_locale = locale;
	}

	public DDMDataProviderInstanceService getDDMDataProviderInstanceService() {
		return _ddmDataProviderInstanceService;
	}

	public DDMDataProviderTracker getDDMDataProviderTracker() {
		return _ddmDataProviderTracker;
	}

	public DDMExpressionFactory getDDMExpressionFactory() {
		return _ddmExpressionFactory;
	}

	public DDMForm getDDMForm() {
		return _ddmForm;
	}

	public Map<String, DDMFormFieldEvaluationResult>
		getDDMFormFieldEvaluationResults() {

		return _ddmFormFieldEvaluationResults;
	}

	public List<DDMFormField> getDDMFormFields() {
		return _ddmForm.getDDMFormFields();
	}

	public DDMFormValues getDDMFormValues() {
		return _ddmFormValues;
	}

	public DDMFormValuesJSONDeserializer getDDMFormValuesJSONDeserializer() {
		return _ddmFormValuesJSONDeserializer;
	}

	public Locale getLocale() {
		return _locale;
	}

	private final DDMDataProviderInstanceService
		_ddmDataProviderInstanceService;
	private final DDMDataProviderTracker _ddmDataProviderTracker;
	private final DDMExpressionFactory _ddmExpressionFactory;
	private final DDMForm _ddmForm;
	private final Map<String, DDMFormFieldEvaluationResult>
		_ddmFormFieldEvaluationResults = new HashMap<>();
	private final DDMFormValues _ddmFormValues;
	private final DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;
	private final Locale _locale;

}
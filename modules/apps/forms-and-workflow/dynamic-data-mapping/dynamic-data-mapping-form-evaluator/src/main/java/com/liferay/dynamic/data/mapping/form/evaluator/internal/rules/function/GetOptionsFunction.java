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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.rules.function;

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationException;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.rules.DDMFormRuleEvaluatorContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Leonardo Barros
 */
public class GetOptionsFunction extends CallFunction {

	@Override
	public String execute(
			DDMFormRuleEvaluatorContext ddmFormRuleEvaluatorContext,
			List<String> parameters)
		throws PortalException {

		if (parameters.size() < 5) {
			throw new DDMFormEvaluationException("Invalid function call");
		}

		long ddmDataProviderInstanceId = Long.parseLong(parameters.get(2));
		String paramsExpression = parameters.get(3);
		String resultMapExpression = parameters.get(4);

		try {
			JSONArray jsonArray = executeDataProvider(
				ddmFormRuleEvaluatorContext, ddmDataProviderInstanceId,
				paramsExpression);

			Map<String, String> resultMap = extractResultMap(
				resultMapExpression);

			DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
				getDDMFormFieldEvaluationResult(
					ddmFormRuleEvaluatorContext, resultMap);

			JSONObject resultMapJSONObject = getResultMapJSONObject(resultMap);

			setOptions(
				ddmFormFieldEvaluationResult, jsonArray, resultMapJSONObject);

			return StringPool.BLANK;
		}
		catch (Exception e) {
			throw new DDMFormEvaluationException(
				"An error occured while trying to call a data provider", e);
		}
	}

	@Override
	public String getPattern() {
		return _PATTERN;
	}

	protected DDMFormFieldEvaluationResult getDDMFormFieldEvaluationResult(
		DDMFormRuleEvaluatorContext ddmFormRuleEvaluatorContext,
		Map<String, String> resultMap) {

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultMap =
				ddmFormRuleEvaluatorContext.getDDMFormFieldEvaluationResults();

		String ddmFormFieldName = getDDMFormFieldName(resultMap);

		return ddmFormFieldEvaluationResultMap.get(ddmFormFieldName);
	}

	protected String getDDMFormFieldName(Map<String, String> resultMap) {
		Set<String> keySet = resultMap.keySet();
		return keySet.iterator().next();
	}

	protected JSONObject getResultMapJSONObject(Map<String, String> resultMap)
		throws Exception {

		String ddmFormFieldName = getDDMFormFieldName(resultMap);
		return JSONFactoryUtil.createJSONObject(
			resultMap.get(ddmFormFieldName));
	}

	protected void setOptions(
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult,
		JSONArray jsonArray, JSONObject resultMapJSONObject) {

		String key = resultMapJSONObject.getString("key");
		String value = resultMapJSONObject.getString("value");

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			ddmFormFieldEvaluationResult.addOption(
				jsonObject.getString(key), jsonObject.getString(value));
		}
	}

	private static final String _PATTERN =
		"((getOptions)\\((\\d+),\"(.+)\",\"(.+)\"\\))";

}
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

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderTracker;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationException;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;

import java.util.List;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public interface Function {

	public static final String VARIABLE_PATTERN =
		"\\b([a-zA-Z]+[\\w_]*)(?!\\()\\b";

	public String execute(
			DDMDataProviderInstanceService ddmDataProviderInstanceService,
			DDMDataProviderTracker ddmDataProviderTracker,
			Map<String, DDMFormFieldEvaluationResult>
				ddmFormFieldEvaluationResults,
			DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer,
			List<String> parameters)
		throws DDMFormEvaluationException;

	public String getPattern();

}
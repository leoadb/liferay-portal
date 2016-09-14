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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class GetValueFunctionTest {

	@Test
	public void testEvaluate() throws Exception {
		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResults = new HashMap<>();

		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResultList =
			new ArrayList<>();

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult1 =
			new DDMFormFieldEvaluationResult("field0", null);

		ddmFormFieldEvaluationResult1.setValue(3);

		ddmFormFieldEvaluationResultList.add(ddmFormFieldEvaluationResult1);

		ddmFormFieldEvaluationResults.put(
			"field0", ddmFormFieldEvaluationResultList);

		GetValueFunction getValueFunction = new GetValueFunction(
			ddmFormFieldEvaluationResults);

		getValueFunction.evaluate("field0");

		Assert.assertEquals(3, ddmFormFieldEvaluationResult1.getValue());
	}

	@Test
	public void testEvaluateWithNoResultForField() throws Exception {
		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResults = new HashMap<>();

		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResultList =
			new ArrayList<>();

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult1 =
			new DDMFormFieldEvaluationResult("field1", null);

		ddmFormFieldEvaluationResult1.setValue("EMPTY");

		ddmFormFieldEvaluationResultList.add(ddmFormFieldEvaluationResult1);

		ddmFormFieldEvaluationResults.put(
			"field1", ddmFormFieldEvaluationResultList);

		GetValueFunction getValueFunction = new GetValueFunction(
			ddmFormFieldEvaluationResults);

		getValueFunction.evaluate("not_available");

		Assert.assertNull(ddmFormFieldEvaluationResult1.getValue());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArgument1() throws Exception {
		GetValueFunction getValueFunction = new GetValueFunction(null);

		getValueFunction.evaluate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArgument2() throws Exception {
		GetValueFunction getValueFunction = new GetValueFunction(null);

		getValueFunction.evaluate("param1", "param2");
	}

}
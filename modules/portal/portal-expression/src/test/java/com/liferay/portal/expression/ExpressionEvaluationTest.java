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

package com.liferay.portal.expression;

import com.liferay.portal.expression.internal.ExpressionFactoryImpl;
import com.liferay.portal.kernel.util.MathUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class ExpressionEvaluationTest {

	@Test
	public void testEvaluateBasicBooleanEqualsExpression() throws Exception {
		Expression<Boolean> expression =
			_expressionFactory.createBooleanExpression("var1 == TRUE");

		expression.setBooleanVariableValue("var1", true);

		Assert.assertTrue(expression.evaluate());
	}

	@Test
	public void testEvaluateBasicBooleanNotEqualsExpression() throws Exception {
		Expression<Boolean> expression =
			_expressionFactory.createBooleanExpression("var1 != TRUE");

		expression.setBooleanVariableValue("var1", true);

		Assert.assertFalse(expression.evaluate());
	}

	@Test
	public void testEvaluateBetweenExpression() throws Exception {
		Expression<Boolean> expression1 =
			_expressionFactory.createBooleanExpression("between(22, 20, 25)");

		Assert.assertTrue(expression1.evaluate());

		Expression<Boolean> expression2 =
			_expressionFactory.createBooleanExpression("between(19, 20, 25)");

		Assert.assertFalse(expression2.evaluate());
	}

	@Test(expected = ExpressionEvaluationException.class)
	public void testEvaluateBlankExpression() throws Exception {
		Expression<Boolean> expression =
			_expressionFactory.createBooleanExpression("");

		expression.setIntegerVariableValue("var1", 5);
		expression.setIntegerVariableValue("var2", 6);

		Assert.assertFalse(expression.evaluate());
	}

	@Test
	public void testEvaluateConcatExpression1() throws Exception {
		Expression<String> expression =
			_expressionFactory.createStringExpression("concat(var1, var2)");

		expression.setStringVariableValue("var1", "Life");
		expression.setStringVariableValue("var2", "ray");

		Assert.assertEquals("Liferay", expression.evaluate());
	}

	@Test
	public void testEvaluateConcatExpression2() throws Exception {
		Expression<String> expression =
			_expressionFactory.createStringExpression(
				"concat(\"Hello \", \"World!\")");

		Assert.assertEquals("Hello World!", expression.evaluate());
	}

	@Test
	public void testEvaluateContainsExpression() throws Exception {
		Expression<Boolean> expression =
			_expressionFactory.createBooleanExpression("contains(var1, var2)");

		expression.setStringVariableValue("var1", "Liferay");
		expression.setStringVariableValue("var2", "ray");

		Assert.assertTrue(expression.evaluate());
	}

	@Test
	public void testEvaluateDoubleExpression() throws Exception {
		Expression<Double> expression =
			_expressionFactory.createDoubleExpression("var1 + var2 + var3");

		double var1 = 5.5;

		expression.setDoubleVariableValue("var1", var1);

		expression.setExpressionStringVariableValue("var2", "var1 + 3");
		expression.setExpressionStringVariableValue("var3", "var2 + var1");

		double var2 = var1 + 3;

		double var3 = var1 + var2;

		Assert.assertEquals(
			(Double)(var1 + var2 + var3), expression.evaluate());
	}

	@Test
	public void testEvaluateEqualsExpression1() throws Exception {
		Expression<Boolean> expression =
			_expressionFactory.createBooleanExpression("var1 == var2");

		expression.setBooleanVariableValue("var1", true);
		expression.setBooleanVariableValue("var2", false);

		Assert.assertFalse(expression.evaluate());
	}

	@Test
	public void testEvaluateEqualsExpression2() throws Exception {
		Expression<Boolean> expression =
			_expressionFactory.createBooleanExpression("equals(var1, var2)");

		expression.setStringVariableValue("var1", "Liferay");
		expression.setStringVariableValue("var2", "Liferay");

		Assert.assertTrue(expression.evaluate());
	}

	@Test
	public void testEvaluateEqualsExpression3() throws Exception {
		Expression<Boolean> expression =
			_expressionFactory.createBooleanExpression(
				"equals(var1, \"Liferay\")");

		expression.setStringVariableValue("var1", "Liferay");

		Assert.assertTrue(expression.evaluate());
	}

	@Test
	public void testEvaluateFalseConstantExpression() throws Exception {
		Expression<Boolean> expression =
			_expressionFactory.createBooleanExpression("FALSE");

		Assert.assertFalse(expression.evaluate());
	}

	@Test
	public void testEvaluateFloatExpression() throws Exception {
		Expression<Float> expression = _expressionFactory.createFloatExpression(
			"var1 + var2 + var3");

		float var1 = 5.5f;

		expression.setFloatVariableValue("var1", var1);

		expression.setExpressionStringVariableValue("var2", "var1 + 3");
		expression.setExpressionStringVariableValue("var3", "var2 + var1");

		float var2 = var1 + 3;

		float var3 = var1 + var2;

		Assert.assertEquals((Float)(var1 + var2 + var3), expression.evaluate());
	}

	@Test
	public void testEvaluateGreaterThanExpression() throws Exception {
		Expression<Boolean> expression =
			_expressionFactory.createBooleanExpression("var1 > var2");

		expression.setIntegerVariableValue("var1", 5);
		expression.setIntegerVariableValue("var2", 6);

		Assert.assertFalse(expression.evaluate());
	}

	@Test
	public void testEvaluateIntegerExpression() throws Exception {
		Expression<Integer> expression =
			_expressionFactory.createIntegerExpression("var1 + var2 + var3");

		int var1 = 5;

		expression.setIntegerVariableValue("var1", var1);

		expression.setExpressionStringVariableValue("var2", "var1 + 3");
		expression.setExpressionStringVariableValue("var3", "var2 + var1");

		int var2 = var1 + 3;

		int var3 = var1 + var2;

		Assert.assertEquals(var1 + var2 + var3, (int)expression.evaluate());
	}

	@Test(expected = ExpressionEvaluationException.class)
	public void testEvaluateInvalidExpression() throws Exception {
		Expression<Boolean> expression =
			_expressionFactory.createBooleanExpression("var1 >=+P var2");

		expression.setIntegerVariableValue("var1", 5);
		expression.setIntegerVariableValue("var2", 6);

		Assert.assertFalse(expression.evaluate());
	}

	@Test
	public void testEvaluateIsEmailAddressExpression() throws Exception {
		Expression<Boolean> expression =
			_expressionFactory.createBooleanExpression("isEmailAddress(var1)");

		expression.setStringVariableValue("var1", "invalid_email");

		Assert.assertFalse(expression.evaluate());

		expression.setStringVariableValue("var1", "test@liferay.com");

		Assert.assertTrue(expression.evaluate());
	}

	@Test
	public void testEvaluateIsURLExpression() throws Exception {
		Expression<Boolean> expression =
			_expressionFactory.createBooleanExpression("isURL(var1)");

		expression.setStringVariableValue("var1", "invalid_url");

		Assert.assertFalse(expression.evaluate());

		expression.setStringVariableValue("var1", "http://www.liferay.com");

		Assert.assertTrue(expression.evaluate());
	}

	@Test
	public void testEvaluateLongExpression() throws Exception {
		Expression<Long> expression = _expressionFactory.createLongExpression(
			"var1 + var2 + var3");

		long var1 = 5l;

		expression.setLongVariableValue("var1", var1);

		expression.setExpressionStringVariableValue("var2", "var1 + 3");
		expression.setExpressionStringVariableValue("var3", "var2 + var1");

		long var2 = var1 + 3;

		long var3 = var1 + var2;

		Assert.assertEquals(var1 + var2 + var3, (long)expression.evaluate());
	}

	@Test(expected = ExpressionEvaluationException.class)
	public void testEvaluateNullExpression() throws Exception {
		Expression<Boolean> expression =
			_expressionFactory.createBooleanExpression(null);

		expression.setIntegerVariableValue("var1", 5);
		expression.setIntegerVariableValue("var2", 6);

		Assert.assertFalse(expression.evaluate());
	}

	@Test
	public void testEvaluateTrueConstantExpression() throws Exception {
		Expression<Boolean> expression =
			_expressionFactory.createBooleanExpression("TRUE");

		Assert.assertTrue(expression.evaluate());
	}

	@Test
	public void testSumWithDoubleValues() throws Exception {
		Expression<Double> expression =
			_expressionFactory.createDoubleExpression("sum(var1, var2, var3)");

		double var1 = 5.5;

		expression.setDoubleVariableValue("var1", var1);

		expression.setExpressionStringVariableValue("var2", "var1 + 3.5");
		expression.setExpressionStringVariableValue("var3", "var2 + var1");

		double var2 = var1 + 3.5;

		double var3 = var1 + var2;

		Assert.assertEquals(
			(Double)MathUtil.sum(var1, var2, var3), expression.evaluate());
	}

	@Test
	public void testSumWithLongValues() throws Exception {
		Expression<Long> expression = _expressionFactory.createLongExpression(
			"sum(var1, var2, var3)");

		long var1 = 5;

		expression.setLongVariableValue("var1", var1);

		expression.setExpressionStringVariableValue("var2", "var1 + 3");
		expression.setExpressionStringVariableValue("var3", "var2 + var1");

		long var2 = var1 + 3;

		long var3 = var1 + var2;

		Assert.assertEquals(
			MathUtil.sum(var1, var2, var3), (long)expression.evaluate());
	}

	private final ExpressionFactory _expressionFactory =
		new ExpressionFactoryImpl();

}
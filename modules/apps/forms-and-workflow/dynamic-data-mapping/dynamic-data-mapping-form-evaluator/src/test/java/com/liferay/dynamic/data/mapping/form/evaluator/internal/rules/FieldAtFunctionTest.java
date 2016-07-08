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

import com.liferay.dynamic.data.mapping.form.evaluator.internal.rules.functions.FieldAtFunction;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class FieldAtFunctionTest {

	@Test
	public void testFieldName() throws Exception {
		FieldAtFunction fieldAtFunction = new FieldAtFunction();

		Assert.assertEquals(
			"fieldName#0", fieldAtFunction.evaluate("fieldName", 0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidNumberOfParameters() throws Exception {
		FieldAtFunction fieldAtFunction = new FieldAtFunction();

		fieldAtFunction.evaluate("param");
	}

}
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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.functions;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.portal.kernel.util.Validator;

import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "ddm.form.evaluator.function.name=isEmpty",
	service = DDMExpressionFunction.class
)
public class IsEmptyFunction
	implements DDMExpressionFunction.Function1<Object, Boolean> {

	@Override
	public Boolean apply(Object parameter) {
		if (parameter == null) {
			return true;
		}

		if (_isArray(parameter)) {
			Object[] values = (Object[])parameter;

			return Stream.of(
				values
			).allMatch(
				Validator::isNull
			);
		}

		return Validator.isNull(parameter);
	}

	private boolean _isArray(Object parameter) {
		Class<?> clazz = parameter.getClass();

		return clazz.isArray();
	}

}
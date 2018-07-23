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
import com.liferay.dynamic.data.mapping.expression.DDMExpressionObserver;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyRequest;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyResponse;
import com.liferay.petra.string.StringPool;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "ddm.form.evaluator.function.name=getValue",
	service = DDMExpressionFunction.class
)
public class GetValueFunction
	implements DDMExpressionFunction.
		Function2<DDMExpressionObserver, String, Object> {

	@Override
	public Object apply(
		DDMExpressionObserver ddmExpressionObserver, String field) {

		if (ddmExpressionObserver == null) {
			return StringPool.BLANK;
		}

		GetFieldPropertyRequest.Builder builder =
			GetFieldPropertyRequest.Builder.newBuilder(field, "value");

		GetFieldPropertyResponse getFieldPropertyResponse =
			ddmExpressionObserver.getFieldProperty(builder.build());

		return getFieldPropertyResponse.getValue();
	}

	@Override
	public boolean isObservable() {
		return true;
	}

}
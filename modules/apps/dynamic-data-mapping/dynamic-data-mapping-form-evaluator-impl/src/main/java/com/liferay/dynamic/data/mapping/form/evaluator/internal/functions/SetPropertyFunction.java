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
import com.liferay.dynamic.data.mapping.expression.UpdateFieldPropertyRequest;

/**
 * @author Leonardo Barros
 */
public abstract class SetPropertyFunction<V>
	implements DDMExpressionFunction.
		Function3<DDMExpressionObserver, String, V, Boolean> {

	@Override
	public Boolean apply(
		DDMExpressionObserver ddmExpressionObserver, String field, V value) {

		if (ddmExpressionObserver == null) {
			return false;
		}

		UpdateFieldPropertyRequest.Builder builder =
			UpdateFieldPropertyRequest.Builder.newBuilder(
				field, getPropertyName(), () -> value);

		ddmExpressionObserver.updateFieldProperty(builder.build());

		return true;
	}

	@Override
	public boolean isObservable() {
		return true;
	}

	protected abstract String getPropertyName();

}
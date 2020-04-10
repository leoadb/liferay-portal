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

import {Callable} from 'lfr-forms-evaluator';

import {PagesVisitor} from '../../util/visitors.es';

class GetValueFunction extends Callable {
	arity() {
		return 1;
	}

	doCall(interpreter, args) {
		const {environment} = interpreter;
		const {pages} = environment.values;
		const visitor = new PagesVisitor(pages);

		const fieldName = args[0];

		const field = visitor.findField(field => field.fieldName === fieldName);

		if (field) {
			return Promise.resolve(field.value);
		}

		return Promise.resolve(null);
	}
}

export default GetValueFunction;

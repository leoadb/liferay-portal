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

import {PagesVisitor} from '../../util/visitors.es';
import SetPropertyFunction from './setProperty.es';

class SetValidationFieldNameFunction extends SetPropertyFunction {
	arity() {
		return 2;
	}

	doCall(interpreter, args) {
		const {environment} = interpreter;
		const {pages} = environment.values;
		const visitor = new PagesVisitor(pages);

		const fieldName = args[0];

		const field = visitor.findField(field => field.fieldName === fieldName);

		if (field) {
			return super.doCall(interpreter, [
				fieldName,
				'validation',
				{
					...field.validation,
					fieldName: args[1],
				},
			]);
		}

		return Promise.resolve(pages);
	}
}

export default SetValidationFieldNameFunction;

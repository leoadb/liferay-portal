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

import SetPropertyFunction from './setProperty.es';

class SetValueFunction extends SetPropertyFunction {
	arity() {
		return 2;
	}

	doCall(interpreter, args) {
		return super.doCall(interpreter, [args[0], 'value', args[1]]);
	}
}

export default SetValueFunction;

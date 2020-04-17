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

package com.liferay.data.engine.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class DEDataDefinitionOptionsForFieldException
	extends DEDataDefinitionException {

	public DEDataDefinitionOptionsForFieldException() {
	}

	public DEDataDefinitionOptionsForFieldException(String msg) {
		super(msg);
	}

	public DEDataDefinitionOptionsForFieldException(
		String fieldName, Throwable cause) {

		super(
			String.format(
				"At least one option must be set for field %s", fieldName),
			cause);

		_fieldName = fieldName;
	}

	public DEDataDefinitionOptionsForFieldException(Throwable cause) {
		super(cause);
	}

	public String getFieldName() {
		return _fieldName;
	}

	private String _fieldName;

}
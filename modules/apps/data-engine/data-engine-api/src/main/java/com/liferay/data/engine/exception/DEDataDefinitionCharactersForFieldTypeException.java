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
public class DEDataDefinitionCharactersForFieldTypeException
	extends DEDataDefinitionException {

	public DEDataDefinitionCharactersForFieldTypeException() {
	}

	public DEDataDefinitionCharactersForFieldTypeException(String msg) {
		super(msg);
	}

	public DEDataDefinitionCharactersForFieldTypeException(
		String fieldType, Throwable cause) {

		super(
			String.format(
				"Invalid characters entered for field type %s", fieldType),
			cause);

		_fieldType = fieldType;
	}

	public DEDataDefinitionCharactersForFieldTypeException(Throwable cause) {
		super(cause);
	}

	public String getFieldType() {
		return _fieldType;
	}

	private String _fieldType;

}
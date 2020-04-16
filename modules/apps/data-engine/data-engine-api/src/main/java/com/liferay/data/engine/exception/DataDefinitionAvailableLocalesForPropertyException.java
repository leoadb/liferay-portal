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
public class DataDefinitionAvailableLocalesForPropertyException
	extends DataDefinitionException {

	public DataDefinitionAvailableLocalesForPropertyException() {
	}

	public DataDefinitionAvailableLocalesForPropertyException(String msg) {
		super(msg);
	}

	public DataDefinitionAvailableLocalesForPropertyException(
		String fieldName, String property, Throwable cause) {

		super(
			String.format(
				"Invalid available locales set for the property '%s' of " +
					"field name %s",
				property, fieldName));

		_fieldName = fieldName;
		_property = property;
	}

	public DataDefinitionAvailableLocalesForPropertyException(
		String msg, Throwable cause) {

		super(msg, cause);
	}

	public DataDefinitionAvailableLocalesForPropertyException(Throwable cause) {
		super(cause);
	}

	public String getFieldName() {
		return _fieldName;
	}

	public String getProperty() {
		return _property;
	}

	private String _fieldName;
	private String _property;

}
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

import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateDEDataLayoutFieldNameException
	extends DEDataLayoutException {

	public DuplicateDEDataLayoutFieldNameException() {
	}

	public DuplicateDEDataLayoutFieldNameException(
		Set<String> duplicatedFieldNames, Throwable cause) {

		super(
			String.format(
				"Field names %s were defined more than once",
				duplicatedFieldNames),
			cause);

		_duplicatedFieldNames = duplicatedFieldNames;
	}

	public DuplicateDEDataLayoutFieldNameException(String msg) {
		super(msg);
	}

	public DuplicateDEDataLayoutFieldNameException(
		String msg, Throwable cause) {

		super(msg, cause);
	}

	public DuplicateDEDataLayoutFieldNameException(Throwable cause) {
		super(cause);
	}

	public Set<String> getDuplicatedFieldNames() {
		return _duplicatedFieldNames;
	}

	private Set<String> _duplicatedFieldNames;

}
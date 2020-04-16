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

import com.liferay.portal.kernel.exception.PortalException;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateDataDefinitionFieldNameException extends PortalException {

	public DuplicateDataDefinitionFieldNameException() {
	}

	public DuplicateDataDefinitionFieldNameException(
		Set<String> duplicatedFieldNames, Throwable cause) {

		super(
			String.format(
				"Field names %s were defined more than once",
				duplicatedFieldNames),
			cause);

		_duplicatedFieldNames = duplicatedFieldNames;
	}

	public DuplicateDataDefinitionFieldNameException(String msg) {
		super(msg);
	}

	public DuplicateDataDefinitionFieldNameException(
		String msg, Throwable cause) {

		super(msg, cause);
	}

	public DuplicateDataDefinitionFieldNameException(Throwable cause) {
		super(cause);
	}

	public Set<String> getDuplicatedFieldNames() {
		return _duplicatedFieldNames;
	}

	private Set<String> _duplicatedFieldNames;

}
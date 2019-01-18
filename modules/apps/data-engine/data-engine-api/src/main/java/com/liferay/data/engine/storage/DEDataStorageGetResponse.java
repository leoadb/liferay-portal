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

package com.liferay.data.engine.storage;

import java.util.Collections;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public final class DEDataStorageGetResponse {

	public Map<String, Object> getValues() {
		return Collections.unmodifiableMap(_values);
	}

	public static class Builder {

		public Builder(Map<String, Object> values) {
			_deDataStorageGetResponse._values = values;
		}

		public DEDataStorageGetResponse build() {
			return _deDataStorageGetResponse;
		}

		private final DEDataStorageGetResponse _deDataStorageGetResponse =
			new DEDataStorageGetResponse();

	}

	private DEDataStorageGetResponse() {
	}

	private Map<String, Object> _values;

}
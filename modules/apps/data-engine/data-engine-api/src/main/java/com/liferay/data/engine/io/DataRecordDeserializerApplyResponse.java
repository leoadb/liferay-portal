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

package com.liferay.data.engine.io;

import java.util.Collections;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public final class DataRecordDeserializerApplyResponse {

	public Map<String, Object> getValues() {
		return Collections.unmodifiableMap(_values);
	}

	public static final class Builder {

		public static Builder newBuilder(Map<String, Object> values) {
			return new Builder(values);
		}

		public static DataRecordDeserializerApplyResponse of(
			Map<String, Object> values) {

			return newBuilder(
				values
			).build();
		}

		public DataRecordDeserializerApplyResponse build() {
			return _dataRecordDeserializerApplyResponse;
		}

		private Builder(Map<String, Object> values) {
			_dataRecordDeserializerApplyResponse._values = values;
		}

		private final DataRecordDeserializerApplyResponse
			_dataRecordDeserializerApplyResponse =
				new DataRecordDeserializerApplyResponse();

	}

	private DataRecordDeserializerApplyResponse() {
	}

	private Map<String, Object> _values;

}
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

import com.liferay.data.engine.model.DataDefinition;

import java.util.Map;

/**
 * @author Leonardo Barros
 */
public final class DataRecordSerializerApplyRequest {

	public DataDefinition getDataDefinition() {
		return _dataDefinition;
	}

	public Map<String, Object> getValues() {
		return _values;
	}

	public static final class Builder {

		public static Builder newBuilder(
			DataDefinition dataDefinition, Map<String, Object> values) {

			return new Builder(dataDefinition, values);
		}

		public static DataRecordSerializerApplyRequest of(
			DataDefinition dataDefinition, Map<String, Object> values) {

			return newBuilder(
				dataDefinition, values
			).build();
		}

		public DataRecordSerializerApplyRequest build() {
			return _dataRecordSerializerApplyRequest;
		}

		private Builder(
			DataDefinition dataDefinition, Map<String, Object> values) {

			_dataRecordSerializerApplyRequest._dataDefinition = dataDefinition;
			_dataRecordSerializerApplyRequest._values = values;
		}

		private final DataRecordSerializerApplyRequest
			_dataRecordSerializerApplyRequest =
				new DataRecordSerializerApplyRequest();

	}

	private DataRecordSerializerApplyRequest() {
	}

	private DataDefinition _dataDefinition;
	private Map<String, Object> _values;

}
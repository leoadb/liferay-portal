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

package com.liferay.data.engine.model;

import com.liferay.petra.lang.HashUtil;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * @author Leonardo Barros
 */
public final class DataRecord implements Serializable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DataRecord)) {
			return false;
		}

		DataRecord dataRecord = (DataRecord)obj;

		Map<String, Object> values = dataRecord._values;

		if (Objects.equals(_dataRecordId, dataRecord._dataRecordId) &&
			Objects.equals(_dataDefinition, dataRecord._dataDefinition) &&
			Objects.equals(_values.keySet(), values.keySet()) &&
			equals(values)) {

			return true;
		}

		return false;
	}

	public DataDefinition getDataDefinition() {
		return _dataDefinition;
	}

	public long getDataRecordId() {
		return _dataRecordId;
	}

	public Map<String, Object> getValues() {
		return Collections.unmodifiableMap(_values);
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _dataRecordId);

		hash = HashUtil.hash(hash, _dataDefinition.hashCode());

		return HashUtil.hash(hash, _values.hashCode());
	}

	public static final class Builder {

		public static Builder newBuilder(
			DataDefinition dataDefinition, Map<String, Object> values) {

			return new Builder(dataDefinition, values);
		}

		public static DataRecord of(
			DataDefinition dataDefinition, Map<String, Object> values) {

			return newBuilder(
				dataDefinition, values
			).build();
		}

		public DataRecord build() {
			return _dataRecord;
		}

		public Builder dataRecordId(long dataRecordId) {
			_dataRecord._dataRecordId = dataRecordId;

			return this;
		}

		private Builder(
			DataDefinition dataDefinition, Map<String, Object> values) {

			_dataRecord._dataDefinition = dataDefinition;
			_dataRecord._values = values;
		}

		private final DataRecord _dataRecord = new DataRecord();

	}

	protected boolean equals(Map<String, Object> values) {
		boolean result = true;

		for (Map.Entry<String, Object> entry : _values.entrySet()) {
			Object value = entry.getValue();
			Object otherValue = values.get(entry.getKey());

			Class<?> clazz = value.getClass();

			if (clazz.isArray()) {
				result = Arrays.equals((Object[])value, (Object[])otherValue);
			}
			else {
				result = Objects.equals(value, otherValue);
			}

			if (!result) {
				break;
			}
		}

		return result;
	}

	private DataRecord() {
	}

	private DataDefinition _dataDefinition;
	private long _dataRecordId;
	private Map<String, Object> _values;

}
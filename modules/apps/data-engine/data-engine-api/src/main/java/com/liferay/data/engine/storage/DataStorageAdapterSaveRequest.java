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

import com.liferay.data.engine.model.DataDefinition;

import java.util.Collections;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public final class DataStorageAdapterSaveRequest {

	public DataDefinition getDataDefinition() {
		return _dataDefinition;
	}

	public long getGroupId() {
		return _groupId;
	}

	public long getPrimaryKey() {
		return _primaryKey;
	}

	public long getUserId() {
		return _userId;
	}

	public Map<String, Object> getValues() {
		return Collections.unmodifiableMap(_values);
	}

	public static final class Builder {

		public static Builder newBuilder(
			long userId, long groupId, DataDefinition dataDefinition) {

			return new Builder(userId, groupId, dataDefinition);
		}

		public DataStorageAdapterSaveRequest build() {
			return _dataStorageAdapterSaveRequest;
		}

		public Builder primaryKey(long primaryKey) {
			_dataStorageAdapterSaveRequest._primaryKey = primaryKey;

			return this;
		}

		public Builder values(Map<String, Object> values) {
			_dataStorageAdapterSaveRequest._values = values;

			return this;
		}

		private Builder(
			long userId, long groupId, DataDefinition dataDefinition) {

			_dataStorageAdapterSaveRequest._userId = userId;
			_dataStorageAdapterSaveRequest._groupId = groupId;
			_dataStorageAdapterSaveRequest._dataDefinition = dataDefinition;
		}

		private final DataStorageAdapterSaveRequest
			_dataStorageAdapterSaveRequest =
				new DataStorageAdapterSaveRequest();

	}

	private DataStorageAdapterSaveRequest() {
	}

	private DataDefinition _dataDefinition;
	private long _groupId;
	private long _primaryKey;
	private long _userId;
	private Map<String, Object> _values;

}
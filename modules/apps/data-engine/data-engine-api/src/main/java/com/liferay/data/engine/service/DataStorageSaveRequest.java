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

package com.liferay.data.engine.service;

import com.liferay.data.engine.model.DataRecord;

/**
 * @author Leonardo Barros
 */
public final class DataStorageSaveRequest {

	public DataRecord getDataRecord() {
		return _dataRecord;
	}

	public long getGroupId() {
		return _groupId;
	}

	public long getUserId() {
		return _userId;
	}

	public static final class Builder {

		public static Builder newBuilder(
			long userId, long groupId, DataRecord dataRecord) {

			return new Builder(userId, groupId, dataRecord);
		}

		public static DataStorageSaveRequest of(
			long userId, long groupId, DataRecord dataRecord) {

			return newBuilder(
				userId, groupId, dataRecord
			).build();
		}

		public DataStorageSaveRequest build() {
			return _dataStorageSaveRequest;
		}

		private Builder(long userId, long groupId, DataRecord dataRecord) {
			_dataStorageSaveRequest._userId = userId;
			_dataStorageSaveRequest._groupId = groupId;
			_dataStorageSaveRequest._dataRecord = dataRecord;
		}

		private final DataStorageSaveRequest _dataStorageSaveRequest =
			new DataStorageSaveRequest();

	}

	private DataStorageSaveRequest() {
	}

	private DataRecord _dataRecord;
	private long _groupId;
	private long _userId;

}
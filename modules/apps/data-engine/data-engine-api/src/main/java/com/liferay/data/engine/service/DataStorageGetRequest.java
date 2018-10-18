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

/**
 * @author Leonardo Barros
 */
public final class DataStorageGetRequest {

	public long getDataRecordId() {
		return _dataRecordId;
	}

	public static final class Builder {

		public static Builder newBuilder(long dataRecordId) {
			return new Builder(dataRecordId);
		}

		public static DataStorageGetRequest of(long dataRecordId) {
			return newBuilder(
				dataRecordId
			).build();
		}

		public DataStorageGetRequest build() {
			return _dataStorageGetRequest;
		}

		private Builder(long dataRecordId) {
			_dataStorageGetRequest._dataRecordId = dataRecordId;
		}

		private final DataStorageGetRequest _dataStorageGetRequest =
			new DataStorageGetRequest();

	}

	private DataStorageGetRequest() {
	}

	private long _dataRecordId;

}
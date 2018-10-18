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
public final class DataStorageGetResponse {

	public DataRecord getDataRecord() {
		return _dataRecord;
	}

	public static final class Builder {

		public static Builder newBuilder(DataRecord dataRecord) {
			return new Builder(dataRecord);
		}

		public static DataStorageGetResponse of(DataRecord dataRecord) {
			return newBuilder(
				dataRecord
			).build();
		}

		public DataStorageGetResponse build() {
			return _dataStorageGetResponse;
		}

		private Builder(DataRecord dataRecord) {
			_dataStorageGetResponse._dataRecord = dataRecord;
		}

		private final DataStorageGetResponse _dataStorageGetResponse =
			new DataStorageGetResponse();

	}

	private DataStorageGetResponse() {
	}

	private DataRecord _dataRecord;

}
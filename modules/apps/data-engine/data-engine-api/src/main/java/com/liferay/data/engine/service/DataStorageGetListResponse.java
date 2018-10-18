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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Leonardo Barros
 */
public final class DataStorageGetListResponse {

	public List<DataRecord> getDataRecords() {
		return Collections.unmodifiableList(_dataRecords);
	}

	public static final class Builder {

		public static Builder newBuilder(List<DataRecord> dataRecords) {
			return new Builder(dataRecords);
		}

		public static DataStorageGetListResponse of(
			List<DataRecord> dataRecords) {

			return newBuilder(
				dataRecords
			).build();
		}

		public DataStorageGetListResponse build() {
			return _dataStorageGetListResponse;
		}

		private Builder(List<DataRecord> dataRecords) {
			_dataStorageGetListResponse._dataRecords.addAll(dataRecords);
		}

		private final DataStorageGetListResponse _dataStorageGetListResponse =
			new DataStorageGetListResponse();

	}

	private DataStorageGetListResponse() {
	}

	private final List<DataRecord> _dataRecords = new ArrayList<>();

}
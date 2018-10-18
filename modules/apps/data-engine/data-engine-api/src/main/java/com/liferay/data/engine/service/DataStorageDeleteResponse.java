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
public final class DataStorageDeleteResponse {

	public long getDataDefinitionId() {
		return _dataDefinitionId;
	}

	public long getDataRecordId() {
		return _dataRecordId;
	}

	public static final class Builder {

		public static Builder newBuilder() {
			return new Builder();
		}

		public DataStorageDeleteResponse build() {
			return _dataStorageDeleteResponse;
		}

		public Builder dataDefinitionId(long dataDefinitionId) {
			_dataStorageDeleteResponse._dataDefinitionId = dataDefinitionId;

			return this;
		}

		public Builder dataRecordId(long dataRecordId) {
			_dataStorageDeleteResponse._dataRecordId = dataRecordId;

			return this;
		}

		private Builder() {
		}

		private final DataStorageDeleteResponse _dataStorageDeleteResponse =
			new DataStorageDeleteResponse();

	}

	private DataStorageDeleteResponse() {
	}

	private long _dataDefinitionId;
	private long _dataRecordId;

}
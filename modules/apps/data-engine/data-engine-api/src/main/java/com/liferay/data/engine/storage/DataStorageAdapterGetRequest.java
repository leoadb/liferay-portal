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

/**
 * @author Leonardo Barros
 */
public final class DataStorageAdapterGetRequest {

	public DataDefinition getDataDefinition() {
		return _dataDefinition;
	}

	public long getPrimaryKey() {
		return _primaryKey;
	}

	public static final class Builder {

		public static Builder newBuilder(
			long primaryKey, DataDefinition dataDefinition) {

			return new Builder(primaryKey, dataDefinition);
		}

		public static DataStorageAdapterGetRequest of(
			long primaryKey, DataDefinition dataDefinition) {

			return newBuilder(
				primaryKey, dataDefinition
			).build();
		}

		public DataStorageAdapterGetRequest build() {
			return _dataStorageAdapterGetRequest;
		}

		private Builder(long primaryKey, DataDefinition dataDefinition) {
			_dataStorageAdapterGetRequest._primaryKey = primaryKey;
			_dataStorageAdapterGetRequest._dataDefinition = dataDefinition;
		}

		private final DataStorageAdapterGetRequest
			_dataStorageAdapterGetRequest = new DataStorageAdapterGetRequest();

	}

	private DataStorageAdapterGetRequest() {
	}

	private DataDefinition _dataDefinition;
	private long _primaryKey;

}
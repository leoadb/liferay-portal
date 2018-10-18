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

/**
 * @author Leonardo Barros
 */
public final class DataStorageAdapterSaveResponse {

	public long getPrimaryKey() {
		return _primaryKey;
	}

	public static class Builder {

		public static Builder newBuilder(long primaryKey) {
			return new Builder(primaryKey);
		}

		public static DataStorageAdapterSaveResponse of(long primaryKey) {
			return newBuilder(
				primaryKey
			).build();
		}

		public DataStorageAdapterSaveResponse build() {
			return _dataStorageAdapterSaveResponse;
		}

		private Builder(long primaryKey) {
			_dataStorageAdapterSaveResponse._primaryKey = primaryKey;
		}

		private final DataStorageAdapterSaveResponse
			_dataStorageAdapterSaveResponse =
				new DataStorageAdapterSaveResponse();

	}

	private DataStorageAdapterSaveResponse() {
	}

	private long _primaryKey;

}
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
public final class DataStorageSaveResponse {

	public long getPrimaryKey() {
		return _primaryKey;
	}

	public static class Builder {

		public static Builder newBuilder(long primaryKey) {
			return new Builder(primaryKey);
		}

		public static DataStorageSaveResponse of(long primaryKey) {
			return newBuilder(
				primaryKey
			).build();
		}

		public DataStorageSaveResponse build() {
			return _dataStorageSaveResponse;
		}

		private Builder(long primaryKey) {
			_dataStorageSaveResponse._primaryKey = primaryKey;
		}

		private final DataStorageSaveResponse _dataStorageSaveResponse =
			new DataStorageSaveResponse();

	}

	private DataStorageSaveResponse() {
	}

	private long _primaryKey;

}
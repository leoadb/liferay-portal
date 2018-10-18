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
public final class DataStorageGetCountRequest {

	public long getDataDefinitionId() {
		return _dataDefinitionId;
	}

	public static final class Builder {

		public static Builder newBuilder(long dataDefinitionId) {
			return new Builder(dataDefinitionId);
		}

		public static DataStorageGetCountRequest of(long dataDefinitionId) {
			return newBuilder(
				dataDefinitionId
			).build();
		}

		public DataStorageGetCountRequest build() {
			return _dataStorageGetCountRequest;
		}

		private Builder(long dataDefinitionId) {
			_dataStorageGetCountRequest._dataDefinitionId = dataDefinitionId;
		}

		private final DataStorageGetCountRequest _dataStorageGetCountRequest =
			new DataStorageGetCountRequest();

	}

	private DataStorageGetCountRequest() {
	}

	private long _dataDefinitionId;

}
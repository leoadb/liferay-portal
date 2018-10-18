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
public final class DataStorageGetListRequest {

	public long getDataDefinitionId() {
		return _dataDefinitionId;
	}

	public int getEnd() {
		return _end;
	}

	public int getStart() {
		return _start;
	}

	public static final class Builder {

		public static Builder newBuilder(long dataDefinitionId) {
			return new Builder(dataDefinitionId);
		}

		public static DataStorageGetListRequest of(long dataDefinitionId) {
			return newBuilder(
				dataDefinitionId
			).build();
		}

		public DataStorageGetListRequest build() {
			return _dataStorageGetListRequest;
		}

		public Builder end(int end) {
			_dataStorageGetListRequest._end = end;

			return this;
		}

		public Builder start(int start) {
			_dataStorageGetListRequest._start = start;

			return this;
		}

		private Builder(long dataDefinitionId) {
			_dataStorageGetListRequest._dataDefinitionId = dataDefinitionId;
		}

		private final DataStorageGetListRequest _dataStorageGetListRequest =
			new DataStorageGetListRequest();

	}

	private DataStorageGetListRequest() {
	}

	private long _dataDefinitionId;
	private int _end;
	private int _start;

}
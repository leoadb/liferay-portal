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
public final class DataStorageExportRequest {

	public long getDataDefinitionId() {
		return _dataDefinitionId;
	}

	public int getEnd() {
		return _end;
	}

	public int getStart() {
		return _start;
	}

	public String getType() {
		return _type;
	}

	public static final class Builder {

		public static Builder newBuilder(long dataDefinitionId) {
			return new Builder(dataDefinitionId);
		}

		public static DataStorageExportRequest of(long dataDefinitionId) {
			return newBuilder(
				dataDefinitionId
			).build();
		}

		public DataStorageExportRequest build() {
			return _dataStorageExportRequest;
		}

		public Builder end(int end) {
			_dataStorageExportRequest._end = end;

			return this;
		}

		public Builder start(int start) {
			_dataStorageExportRequest._start = start;

			return this;
		}

		public Builder type(String type) {
			_dataStorageExportRequest._type = type;

			return this;
		}

		private Builder(long dataDefinition) {
			_dataStorageExportRequest._dataDefinitionId = dataDefinition;
		}

		private final DataStorageExportRequest _dataStorageExportRequest =
			new DataStorageExportRequest();

	}

	private DataStorageExportRequest() {
	}

	private long _dataDefinitionId;
	private int _end;
	private int _start;
	private String _type = "json";

}
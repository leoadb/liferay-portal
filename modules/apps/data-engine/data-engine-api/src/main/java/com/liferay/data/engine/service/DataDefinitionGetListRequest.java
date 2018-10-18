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
public final class DataDefinitionGetListRequest {

	public int getEnd() {
		return _end;
	}

	public long getGroupId() {
		return _groupId;
	}

	public int getStart() {
		return _start;
	}

	public static final class Builder {

		public static Builder newBuilder(long groupId) {
			return new Builder(groupId);
		}

		public static DataDefinitionGetListRequest of(long groupId) {
			return newBuilder(
				groupId
			).build();
		}

		public DataDefinitionGetListRequest build() {
			return _dataDefinitionGetListRequest;
		}

		public Builder end(int end) {
			_dataDefinitionGetListRequest._end = end;

			return this;
		}

		public Builder start(int start) {
			_dataDefinitionGetListRequest._start = start;

			return this;
		}

		private Builder(long groupId) {
			_dataDefinitionGetListRequest._groupId = groupId;
		}

		private final DataDefinitionGetListRequest
			_dataDefinitionGetListRequest = new DataDefinitionGetListRequest();

	}

	private DataDefinitionGetListRequest() {
	}

	private int _end;
	private long _groupId;
	private int _start;

}
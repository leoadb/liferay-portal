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
public final class DataDefinitionGetCountRequest {

	public long getGroupId() {
		return _groupId;
	}

	public static final class Builder {

		public static Builder newBuilder(long groupId) {
			return new Builder(groupId);
		}

		public static DataDefinitionGetCountRequest of(long groupId) {
			return newBuilder(
				groupId
			).build();
		}

		public DataDefinitionGetCountRequest build() {
			return _dataDefinitionGetCountRequest;
		}

		private Builder(long groupId) {
			_dataDefinitionGetCountRequest._groupId = groupId;
		}

		private final DataDefinitionGetCountRequest
			_dataDefinitionGetCountRequest =
				new DataDefinitionGetCountRequest();

	}

	private DataDefinitionGetCountRequest() {
	}

	private long _groupId;

}
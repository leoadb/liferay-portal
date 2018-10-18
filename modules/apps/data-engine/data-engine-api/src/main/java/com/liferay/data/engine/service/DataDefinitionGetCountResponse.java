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
public final class DataDefinitionGetCountResponse {

	public int getCount() {
		return _count;
	}

	public static final class Builder {

		public static Builder newBuilder(int count) {
			return new Builder(count);
		}

		public static DataDefinitionGetCountResponse of(int count) {
			return newBuilder(
				count
			).build();
		}

		public DataDefinitionGetCountResponse build() {
			return _dataDefinitionGetCountResponse;
		}

		private Builder(int count) {
			_dataDefinitionGetCountResponse._count = count;
		}

		private final DataDefinitionGetCountResponse
			_dataDefinitionGetCountResponse =
				new DataDefinitionGetCountResponse();

	}

	private DataDefinitionGetCountResponse() {
	}

	private int _count;

}
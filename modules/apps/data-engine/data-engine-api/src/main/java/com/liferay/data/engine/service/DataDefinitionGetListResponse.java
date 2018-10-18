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

import com.liferay.data.engine.model.DataDefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Leonardo Barros
 */
public final class DataDefinitionGetListResponse {

	public List<DataDefinition> getDataDefinitions() {
		return Collections.unmodifiableList(_dataDefinitions);
	}

	public static final class Builder {

		public static Builder newBuilder(List<DataDefinition> dataDefinitions) {
			return new Builder(dataDefinitions);
		}

		public static DataDefinitionGetListResponse of(
			List<DataDefinition> dataDefinitions) {

			return newBuilder(
				dataDefinitions
			).build();
		}

		public DataDefinitionGetListResponse build() {
			return _dataDefinitionGetListResponse;
		}

		private Builder(List<DataDefinition> dataDefinitions) {
			_dataDefinitionGetListResponse._dataDefinitions.addAll(
				dataDefinitions);
		}

		private final DataDefinitionGetListResponse
			_dataDefinitionGetListResponse =
				new DataDefinitionGetListResponse();

	}

	private DataDefinitionGetListResponse() {
	}

	private final List<DataDefinition> _dataDefinitions = new ArrayList<>();

}
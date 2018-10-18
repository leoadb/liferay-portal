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

package com.liferay.data.engine.io;

import com.liferay.data.engine.model.DataDefinition;

/**
 * @author Leonardo Barros
 */
public final class DataRecordDeserializerApplyRequest {

	public String getContent() {
		return _content;
	}

	public DataDefinition getDataDefinition() {
		return _dataDefinition;
	}

	public static final class Builder {

		public static Builder newBuilder(
			DataDefinition dataDefinition, String content) {

			return new Builder(dataDefinition, content);
		}

		public static DataRecordDeserializerApplyRequest of(
			DataDefinition dataDefinition, String content) {

			return newBuilder(
				dataDefinition, content
			).build();
		}

		public DataRecordDeserializerApplyRequest build() {
			return _dataRecordDeserializerApplyRequest;
		}

		private Builder(DataDefinition dataDefinition, String content) {
			_dataRecordDeserializerApplyRequest._dataDefinition =
				dataDefinition;
			_dataRecordDeserializerApplyRequest._content = content;
		}

		private final DataRecordDeserializerApplyRequest
			_dataRecordDeserializerApplyRequest =
				new DataRecordDeserializerApplyRequest();

	}

	private DataRecordDeserializerApplyRequest() {
	}

	private String _content;
	private DataDefinition _dataDefinition;

}
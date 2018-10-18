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

import com.liferay.data.engine.model.DataRecord;

import java.util.Collections;
import java.util.List;

/**
 * @author Leonardo Barros
 */
public final class DataExporterApplyRequest {

	public List<DataRecord> getDataRecords() {
		return Collections.unmodifiableList(_dataRecords);
	}

	public static final class Builder {

		public static Builder newBuilder(List<DataRecord> dataRecords) {
			return new Builder(dataRecords);
		}

		public static DataExporterApplyRequest of(
			List<DataRecord> dataRecords) {

			return newBuilder(
				dataRecords
			).build();
		}

		public DataExporterApplyRequest build() {
			return _dataExporterApplyRequest;
		}

		private Builder(List<DataRecord> dataRecords) {
			_dataExporterApplyRequest._dataRecords = dataRecords;
		}

		private final DataExporterApplyRequest _dataExporterApplyRequest =
			new DataExporterApplyRequest();

	}

	private DataExporterApplyRequest() {
	}

	private List<DataRecord> _dataRecords;

}
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

import com.liferay.data.engine.model.DEDataRecord;

import java.util.Collections;
import java.util.List;

/**
 * @author Leonardo Barros
 */
public final class DEDataRecordCollectionListRecordsResponse {

	public List<DEDataRecord> getDEDataRecords() {
		return Collections.unmodifiableList(_deDataRecords);
	}

	public static class Builder {

		public static Builder newBuilder(List<DEDataRecord> deDataRecords) {
			return new Builder(deDataRecords);
		}

		public static DEDataRecordCollectionListRecordsResponse of(
			List<DEDataRecord> deDataRecords) {

			return newBuilder(
				deDataRecords
			).build();
		}

		public DEDataRecordCollectionListRecordsResponse build() {
			return _deDataRecordCollectionListRecordsResponse;
		}

		private Builder(List<DEDataRecord> deDataRecords) {
			_deDataRecordCollectionListRecordsResponse._deDataRecords =
				deDataRecords;
		}

		private final DEDataRecordCollectionListRecordsResponse
			_deDataRecordCollectionListRecordsResponse =
				new DEDataRecordCollectionListRecordsResponse();

	}

	private DEDataRecordCollectionListRecordsResponse() {
	}

	private List<DEDataRecord> _deDataRecords;

}
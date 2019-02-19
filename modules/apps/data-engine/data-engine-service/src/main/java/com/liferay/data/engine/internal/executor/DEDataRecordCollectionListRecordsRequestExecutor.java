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

package com.liferay.data.engine.internal.executor;

import com.liferay.data.engine.model.DEDataRecord;
import com.liferay.data.engine.model.DEDataRecordCollection;
import com.liferay.data.engine.service.DEDataRecordCollectionListRecordsRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionListRecordsResponse;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Leonardo Barros
 */
public class DEDataRecordCollectionListRecordsRequestExecutor {

	public DEDataRecordCollectionListRecordsRequestExecutor(
		DDLRecordSetLocalService ddlRecordSetLocalService,
		DEDataEngineRequestExecutor deDataEngineRequestExecutor) {

		_ddlRecordSetLocalService = ddlRecordSetLocalService;
		_deDataEngineRequestExecutor = deDataEngineRequestExecutor;
	}

	public DEDataRecordCollectionListRecordsResponse execute(
			DEDataRecordCollectionListRecordsRequest
				deDataRecordCollectionListRecordsRequest)
		throws Exception {

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			deDataRecordCollectionListRecordsRequest.
				getDEDataRecordCollectionId());

		DEDataRecordCollection deDataRecordCollection =
			_deDataEngineRequestExecutor.map(ddlRecordSet);

		List<DDLRecord> ddlRecords = ddlRecordSet.getRecords();

		List<DEDataRecord> deDataRecords = new ArrayList<>(ddlRecords.size());

		for (DDLRecord ddlRecord : ddlRecords) {
			DEDataRecord deDataRecord = _deDataEngineRequestExecutor.map(
				deDataRecordCollection, ddlRecord);

			deDataRecords.add(deDataRecord);
		}

		return DEDataRecordCollectionListRecordsResponse.Builder.of(
			deDataRecords);
	}

	private final DDLRecordSetLocalService _ddlRecordSetLocalService;
	private final DEDataEngineRequestExecutor _deDataEngineRequestExecutor;

}
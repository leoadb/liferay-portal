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

package com.liferay.data.engine.internal.io;

import com.liferay.data.engine.io.DataExporter;
import com.liferay.data.engine.io.DataExporterApplyRequest;
import com.liferay.data.engine.io.DataExporterApplyResponse;
import com.liferay.data.engine.io.DataRecordSerializer;
import com.liferay.data.engine.io.DataRecordSerializerApplyRequest;
import com.liferay.data.engine.io.DataRecordSerializerApplyResponse;
import com.liferay.data.engine.model.DataRecord;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "data.exporter.type=json",
	service = DataExporter.class
)
public class DataJSONExporter implements DataExporter {

	@Override
	public DataExporterApplyResponse apply(
		DataExporterApplyRequest dataExporterApplyRequest) {

		JSONArray jsonArray = jsonFactory.createJSONArray();

		List<DataRecord> dataRecords =
			dataExporterApplyRequest.getDataRecords();

		Stream<DataRecord> stream = dataRecords.stream();

		stream.map(
			this::map
		).forEach(
			jsonArray::put
		);

		return DataExporterApplyResponse.Builder.of(jsonArray.toJSONString());
	}

	protected JSONObject map(DataRecord dataRecord) {
		DataRecordSerializerApplyResponse dataRecordSerializerApplyResponse =
			dataRecordSerializer.apply(
				DataRecordSerializerApplyRequest.Builder.of(
					dataRecord.getDataDefinition(), dataRecord.getValues()));

		try {
			return jsonFactory.createJSONObject(
				dataRecordSerializerApplyResponse.getContent());
		}
		catch (JSONException jsone)
		{
			return jsonFactory.createJSONObject();
		}
	}

	@Reference(target = "(data.record.serializer.type=json)")
	protected DataRecordSerializer dataRecordSerializer;

	@Reference
	protected JSONFactory jsonFactory;

}
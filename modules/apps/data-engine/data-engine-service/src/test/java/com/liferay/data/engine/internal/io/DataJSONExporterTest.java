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

import com.liferay.data.engine.io.DataExporterApplyRequest;
import com.liferay.data.engine.io.DataExporterApplyResponse;
import com.liferay.data.engine.model.DataDefinition;
import com.liferay.data.engine.model.DataDefinitionColumn;
import com.liferay.data.engine.model.DataDefinitionColumnType;
import com.liferay.data.engine.model.DataRecord;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Leonardo Barros
 */
public class DataJSONExporterTest extends BaseTestCase {

	@Test
	public void testApply() throws Exception {
		DataJSONExporter dataJSONExporter = new DataJSONExporter();

		dataJSONExporter.jsonFactory = _jsonFactory;

		DataRecordJSONSerializer dataRecordJSONSerializer =
			new DataRecordJSONSerializer();

		dataRecordJSONSerializer.jsonFactory = _jsonFactory;

		dataJSONExporter.dataRecordSerializer = dataRecordJSONSerializer;

		DataDefinition dataDefinition = createDataDefinition();

		Map<String, Object> values1 = new HashMap() {
			{
				put("column1", 3);
				put("column2", new Object[] {5, 6});
			}
		};

		DataRecord dataRecord1 = DataRecord.Builder.of(dataDefinition, values1);

		Map<String, Object> values2 = new HashMap() {
			{
				put("column1", 2);
				put("column2", new Object[] {7, 8, 9});
			}
		};

		DataRecord dataRecord2 = DataRecord.Builder.of(dataDefinition, values2);

		Map<String, Object> values3 = new HashMap() {
			{
				put("column1", 1);
				put("column2", new Object[] {2, 1});
			}
		};

		DataRecord dataRecord3 = DataRecord.Builder.of(dataDefinition, values3);

		List<DataRecord> dataRecords = Arrays.asList(
			dataRecord1, dataRecord2, dataRecord3);

		DataExporterApplyRequest dataExporterApplyRequest =
			DataExporterApplyRequest.Builder.of(dataRecords);

		DataExporterApplyResponse dataExporterApplyResponse =
			dataJSONExporter.apply(dataExporterApplyRequest);

		String json = read("data-json-exporter.json");

		JSONAssert.assertEquals(
			json, dataExporterApplyResponse.getContent(), false);
	}

	protected DataDefinition createDataDefinition() throws Exception {
		DataDefinitionColumn dataDefinitionColumn1 =
			DataDefinitionColumn.Builder.newBuilder(
				"column1", DataDefinitionColumnType.NUMBER
			).build();

		DataDefinitionColumn dataDefinitionColumn2 =
			DataDefinitionColumn.Builder.newBuilder(
				"column2", DataDefinitionColumnType.NUMBER
			).repeatable(
				true
			).build();

		DataDefinition dataDefinition = DataDefinition.Builder.newBuilder(
			Arrays.asList(dataDefinitionColumn1, dataDefinitionColumn2)
		).name(
			LocaleUtil.US, "Definition 1"
		).build();

		return dataDefinition;
	}

	private final JSONFactory _jsonFactory = new JSONFactoryImpl();

}
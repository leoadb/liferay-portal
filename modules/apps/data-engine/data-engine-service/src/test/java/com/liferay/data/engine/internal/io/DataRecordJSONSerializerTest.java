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

import com.liferay.data.engine.io.DataRecordSerializerApplyRequest;
import com.liferay.data.engine.io.DataRecordSerializerApplyResponse;
import com.liferay.data.engine.model.DataDefinition;
import com.liferay.data.engine.model.DataDefinitionColumn;
import com.liferay.data.engine.model.DataDefinitionColumnType;
import com.liferay.portal.json.JSONFactoryImpl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Leonardo Barros
 */
public class DataRecordJSONSerializerTest extends BaseTestCase {

	@Test
	public void testApply() throws Exception {
		DataRecordJSONSerializer dataRecordJSONSerializer =
			new DataRecordJSONSerializer();

		dataRecordJSONSerializer.jsonFactory = new JSONFactoryImpl();

		DataDefinition dataDefinition = new DataDefinition();

		DataDefinitionColumn dataDefinitionColumn1 =
			DataDefinitionColumn.Builder.newBuilder(
				"column1", DataDefinitionColumnType.TEXT
			).build();

		dataDefinition.addColumn(dataDefinitionColumn1);

		DataDefinitionColumn dataDefinitionColumn2 =
			DataDefinitionColumn.Builder.newBuilder(
				"column2", DataDefinitionColumnType.TEXT
			).localizable(
				true
			).build();

		dataDefinition.addColumn(dataDefinitionColumn2);

		DataDefinitionColumn dataDefinitionColumn3 =
			DataDefinitionColumn.Builder.newBuilder(
				"column3", DataDefinitionColumnType.NUMBER
			).repeatable(
				true
			).build();

		dataDefinition.addColumn(dataDefinitionColumn3);

		DataDefinitionColumn dataDefinitionColumn4 =
			DataDefinitionColumn.Builder.newBuilder(
				"column4", DataDefinitionColumnType.NUMBER
			).build();

		dataDefinition.addColumn(dataDefinitionColumn4);

		Map<String, Object> localizedValues = new HashMap() {
			{
				put("en_US", "value 2");
				put("pt_BR", "valor 2");
			}
		};

		Map<String, Object> values = new HashMap() {
			{
				put("column1", "test");
				put("column2", localizedValues);
				put("column3", new Object[] {10, 11});
			}
		};

		DataRecordSerializerApplyRequest
			dataDefinitionRecordSerializerApplyRequest =
				DataRecordSerializerApplyRequest.Builder.newBuilder(
					dataDefinition, values
				).build();

		DataRecordSerializerApplyResponse
			dataDefinitionRecordSerializerApplyResponse =
				dataRecordJSONSerializer.apply(
					dataDefinitionRecordSerializerApplyRequest);

		String json = read("data-definition-record-serializer.json");

		JSONAssert.assertEquals(
			json, dataDefinitionRecordSerializerApplyResponse.getContent(),
			false);
	}

}
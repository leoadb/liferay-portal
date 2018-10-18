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

import com.liferay.data.engine.io.DataRecordDeserializerApplyRequest;
import com.liferay.data.engine.io.DataRecordDeserializerApplyResponse;
import com.liferay.data.engine.model.DataDefinition;
import com.liferay.data.engine.model.DataDefinitionColumn;
import com.liferay.data.engine.model.DataDefinitionColumnType;
import com.liferay.portal.json.JSONFactoryImpl;

import java.util.Arrays;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class DataRecordJSONDeserializerTest extends BaseTestCase {

	@Test
	public void testApply() throws Exception {
		DataDefinitionColumn dataDefinitionColumn1 =
			DataDefinitionColumn.Builder.newBuilder(
				"column1", DataDefinitionColumnType.STRING
			).build();

		DataDefinitionColumn dataDefinitionColumn2 =
			DataDefinitionColumn.Builder.newBuilder(
				"column2", DataDefinitionColumnType.STRING
			).localizable(
				true
			).build();

		DataDefinitionColumn dataDefinitionColumn3 =
			DataDefinitionColumn.Builder.newBuilder(
				"column3", DataDefinitionColumnType.NUMBER
			).repeatable(
				true
			).build();

		DataDefinitionColumn dataDefinitionColumn4 =
			DataDefinitionColumn.Builder.newBuilder(
				"column4", DataDefinitionColumnType.NUMBER
			).build();

		DataDefinition dataDefinition = DataDefinition.Builder.newBuilder(
			Arrays.asList(
				dataDefinitionColumn1, dataDefinitionColumn2,
				dataDefinitionColumn3, dataDefinitionColumn4)
		).build();

		String json = read("data-definition-record-serializer.json");

		DataRecordDeserializerApplyRequest
			dataDefinitionRecordDeserializerApplyRequest =
				DataRecordDeserializerApplyRequest.Builder.of(
					dataDefinition, json);

		DataRecordJSONDeserializer dataRecordJSONDeserializer =
			new DataRecordJSONDeserializer();

		dataRecordJSONDeserializer.jsonFactory = new JSONFactoryImpl();

		DataRecordDeserializerApplyResponse
			dataDefinitionRecordDeserializerApplyResponse =
				dataRecordJSONDeserializer.apply(
					dataDefinitionRecordDeserializerApplyRequest);

		Map<String, Object> values =
			dataDefinitionRecordDeserializerApplyResponse.getValues();

		Assert.assertEquals(false, values.containsKey("column4"));

		Assert.assertEquals("test", values.get("column1"));

		Map<String, Object> localizedValues = (Map<String, Object>)values.get(
			"column2");

		Assert.assertEquals("value 2", localizedValues.get("en_US"));
		Assert.assertEquals("valor 2", localizedValues.get("pt_BR"));

		Object[] repeatableValues = (Object[])values.get("column3");

		Assert.assertArrayEquals(new Object[] {10, 11}, repeatableValues);
	}

}
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

import com.liferay.data.engine.io.DataDefinitionSerializerApplyRequest;
import com.liferay.data.engine.io.DataDefinitionSerializerApplyResponse;
import com.liferay.data.engine.model.DataDefinitionColumn;
import com.liferay.data.engine.model.DataDefinitionColumnType;
import com.liferay.portal.json.JSONFactoryImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Leonardo Barros
 */
public class DataDefinitionJSONSerializerTest extends BaseTestCase {

	@Test
	public void testApply() throws Exception {
		Map<String, String> nameLabels = new HashMap() {
			{
				put("pt_BR", "Nome");
				put("en_US", "Name");
			}
		};

		DataDefinitionColumn dataDefinitionColumn1 =
			DataDefinitionColumn.Builder.newBuilder(
				"name", DataDefinitionColumnType.STRING
			).labels(
				nameLabels
			).build();

		Map<String, String> emailLabels = new HashMap() {
			{
				put("pt_BR", "Endereço de Email");
				put("en_US", "Email Address");
			}
		};

		DataDefinitionColumn dataDefinitionColumn2 =
			DataDefinitionColumn.Builder.newBuilder(
				"email", DataDefinitionColumnType.STRING
			).labels(
				emailLabels
			).build();

		DataDefinitionSerializerApplyRequest.Builder builder =
			DataDefinitionSerializerApplyRequest.Builder.newBuilder(
				Arrays.asList(dataDefinitionColumn1, dataDefinitionColumn2)
			);

		DataDefinitionJSONSerializer dataDefinitionJSONSerializer =
			new DataDefinitionJSONSerializer();

		dataDefinitionJSONSerializer.jsonFactory = new JSONFactoryImpl();

		DataDefinitionSerializerApplyResponse
			dataDefinitionSerializerApplyResponse =
				dataDefinitionJSONSerializer.apply(builder.build());

		String json = read("data-definition-serializer.json");

		JSONAssert.assertEquals(
			json, dataDefinitionSerializerApplyResponse.getContent(), false);
	}

}
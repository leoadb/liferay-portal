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

import com.liferay.data.engine.io.DataDefinitionDeserializerApplyRequest;
import com.liferay.data.engine.io.DataDefinitionDeserializerApplyResponse;
import com.liferay.data.engine.model.DataDefinitionColumn;
import com.liferay.data.engine.model.DataDefinitionColumnType;
import com.liferay.portal.json.JSONFactoryImpl;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class DataDefinitionJSONDeserializerTest extends BaseTestCase {

	@Test
	public void testApply() throws Exception {
		String json = read("data-definition-deserializer.json");

		DataDefinitionDeserializerApplyRequest
			dataDefinitionDeserializerApplyRequest =
				DataDefinitionDeserializerApplyRequest.Builder.of(json);

		DataDefinitionJSONDeserializer dataDefinitionJSONDeserializer =
			new DataDefinitionJSONDeserializer();

		dataDefinitionJSONDeserializer.jsonFactory = new JSONFactoryImpl();

		DataDefinitionDeserializerApplyResponse
			dataDefinitionDeserializerApplyResponse =
				dataDefinitionJSONDeserializer.apply(
					dataDefinitionDeserializerApplyRequest);

		List<DataDefinitionColumn> dataDefinitionColumns =
			dataDefinitionDeserializerApplyResponse.getDataDefinitionColumns();

		Assert.assertEquals(
			dataDefinitionColumns.toString(), 2, dataDefinitionColumns.size());

		DataDefinitionColumn dataDefinitionColumn = dataDefinitionColumns.get(
			0);

		Assert.assertEquals(false, dataDefinitionColumn.isIndexable());
		Assert.assertEquals(true, dataDefinitionColumn.isLocalizable());
		Assert.assertEquals(false, dataDefinitionColumn.isRepeatable());
		Assert.assertEquals("name", dataDefinitionColumn.getName());

		Assert.assertEquals(
			DataDefinitionColumnType.STRING, dataDefinitionColumn.getType());

		Map<String, String> labels = dataDefinitionColumn.getLabel();

		Assert.assertEquals("Name", labels.get("en_US"));
		Assert.assertEquals("Nome", labels.get("pt_BR"));

		dataDefinitionColumn = dataDefinitionColumns.get(1);

		Assert.assertEquals(true, dataDefinitionColumn.isIndexable());
		Assert.assertEquals(false, dataDefinitionColumn.isLocalizable());
		Assert.assertEquals(true, dataDefinitionColumn.isRepeatable());
		Assert.assertEquals("email", dataDefinitionColumn.getName());

		Assert.assertEquals(
			DataDefinitionColumnType.STRING, dataDefinitionColumn.getType());

		labels = dataDefinitionColumn.getLabel();

		Assert.assertEquals("Email Address", labels.get("en_US"));
		Assert.assertEquals("Endereço de Email", labels.get("pt_BR"));
	}

}
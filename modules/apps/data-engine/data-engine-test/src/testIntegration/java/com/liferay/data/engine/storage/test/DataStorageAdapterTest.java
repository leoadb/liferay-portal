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

package com.liferay.data.engine.storage.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.model.DataDefinition;
import com.liferay.data.engine.model.DataDefinitionColumn;
import com.liferay.data.engine.model.DataDefinitionColumnType;
import com.liferay.data.engine.storage.DataStorageAdapter;
import com.liferay.data.engine.storage.DataStorageAdapterDeleteRequest;
import com.liferay.data.engine.storage.DataStorageAdapterDeleteResponse;
import com.liferay.data.engine.storage.DataStorageAdapterGetRequest;
import com.liferay.data.engine.storage.DataStorageAdapterGetResponse;
import com.liferay.data.engine.storage.DataStorageAdapterSaveRequest;
import com.liferay.data.engine.storage.DataStorageAdapterSaveResponse;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Leonardo Barros
 */
@RunWith(Arquillian.class)
public class DataStorageAdapterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test(expected = PortalException.class)
	public void testDelete() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addGroupOwnerUser(group);

		DataDefinitionColumn dataDefinitionColumn1 =
			DataDefinitionColumn.Builder.newBuilder(
				"column1", DataDefinitionColumnType.STRING
			).build();

		DataDefinition dataDefinition = DataDefinition.Builder.newBuilder(
			Arrays.asList(dataDefinitionColumn1)
		).name(
			LocaleUtil.US, "Definition 1"
		).storageType(
			"json"
		).build();

		Map<String, Object> expectedValues = new HashMap() {
			{
				put("column1", "value 1");
			}
		};

		DataStorageAdapterSaveRequest dataStorageAdapterSaveRequest =
			DataStorageAdapterSaveRequest.Builder.newBuilder(
				user.getUserId(), group.getGroupId(), dataDefinition
			).values(
				expectedValues
			).build();

		DataStorageAdapterSaveResponse dataStorageAdapterSaveResponse =
			_dataStorageAdapter.save(dataStorageAdapterSaveRequest);

		long primaryKey = dataStorageAdapterSaveResponse.getPrimaryKey();

		DataStorageAdapterDeleteRequest dataStorageAdapterDeleteRequest =
			DataStorageAdapterDeleteRequest.Builder.of(primaryKey);

		DataStorageAdapterDeleteResponse dataStorageAdapterDeleteResponse =
			_dataStorageAdapter.delete(dataStorageAdapterDeleteRequest);

		Assert.assertEquals(
			primaryKey, dataStorageAdapterDeleteResponse.getPrimaryKey());

		DataStorageAdapterGetRequest dataStorageAdapterGetRequest =
			DataStorageAdapterGetRequest.Builder.of(primaryKey, dataDefinition);

		_dataStorageAdapter.get(dataStorageAdapterGetRequest);
	}

	@Test(expected = PortalException.class)
	public void testGetNotFound() throws Exception {
		DataDefinitionColumn dataDefinitionColumn1 =
			DataDefinitionColumn.Builder.newBuilder(
				"column1", DataDefinitionColumnType.STRING
			).build();

		DataDefinition dataDefinition = DataDefinition.Builder.newBuilder(
			Arrays.asList(dataDefinitionColumn1)
		).name(
			LocaleUtil.US, "Definition 1"
		).storageType(
			"json"
		).build();

		DataStorageAdapterGetRequest dataStorageAdapterGetRequest =
			DataStorageAdapterGetRequest.Builder.of(1, dataDefinition);

		_dataStorageAdapter.get(dataStorageAdapterGetRequest);
	}

	@Test
	public void testInsert() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addGroupOwnerUser(group);

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

		DataDefinition dataDefinition = DataDefinition.Builder.newBuilder(
			Arrays.asList(
				dataDefinitionColumn1, dataDefinitionColumn2,
				dataDefinitionColumn3)
		).name(
			LocaleUtil.US, "Definition 1"
		).storageType(
			"json"
		).build();

		Map<String, Object> column2Values = new HashMap() {
			{
				put("en_US", "value 2");
				put("pt_BR", "valor 2");
			}
		};

		Map<String, Object> expectedValues = new HashMap() {
			{
				put("column1", "value 1");
				put("column2", column2Values);
				put("column3", new Object[] {10, 11});
			}
		};

		DataStorageAdapterSaveRequest dataStorageAdapterSaveRequest =
			DataStorageAdapterSaveRequest.Builder.newBuilder(
				user.getUserId(), group.getGroupId(), dataDefinition
			).values(
				expectedValues
			).build();

		DataStorageAdapterSaveResponse dataStorageAdapterSaveResponse =
			_dataStorageAdapter.save(dataStorageAdapterSaveRequest);

		long primaryKey = dataStorageAdapterSaveResponse.getPrimaryKey();

		DataStorageAdapterGetRequest dataStorageAdapterGetRequest =
			DataStorageAdapterGetRequest.Builder.of(primaryKey, dataDefinition);

		DataStorageAdapterGetResponse dataStorageAdapterGetResponse =
			_dataStorageAdapter.get(dataStorageAdapterGetRequest);

		Map<String, Object> values = dataStorageAdapterGetResponse.getValues();

		Assert.assertEquals(
			expectedValues.get("column1"), values.get("column1"));
		Assert.assertEquals(
			expectedValues.get("column2"), values.get("column2"));
		Assert.assertArrayEquals(
			(Object[])expectedValues.get("column3"),
			(Object[])values.get("column3"));

		DataStorageAdapterDeleteRequest dataStorageAdapterDeleteRequest =
			DataStorageAdapterDeleteRequest.Builder.of(primaryKey);

		_dataStorageAdapter.delete(dataStorageAdapterDeleteRequest);
	}

	@Test
	public void testUpdate() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addGroupOwnerUser(group);

		DataDefinitionColumn dataDefinitionColumn1 =
			DataDefinitionColumn.Builder.newBuilder(
				"column1", DataDefinitionColumnType.STRING
			).build();

		Map<String, Object> expectedValues = new HashMap() {
			{
				put("column1", "value 1");
				put("column2", new Object[] {1, 2});
			}
		};

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
		).storageType(
			"json"
		).build();

		DataStorageAdapterSaveRequest dataStorageAdapterSaveRequest =
			DataStorageAdapterSaveRequest.Builder.newBuilder(
				user.getUserId(), group.getGroupId(), dataDefinition
			).values(
				expectedValues
			).build();

		DataStorageAdapterSaveResponse dataStorageAdapterSaveResponse =
			_dataStorageAdapter.save(dataStorageAdapterSaveRequest);

		long primaryKey = dataStorageAdapterSaveResponse.getPrimaryKey();

		expectedValues = new HashMap() {
			{
				put("column1", "value 1 updated");
				put("column2", new Object[] {1, 2, 3});
			}
		};

		dataStorageAdapterSaveRequest =
			DataStorageAdapterSaveRequest.Builder.newBuilder(
				user.getUserId(), group.getGroupId(), dataDefinition
			).values(
				expectedValues
			).primaryKey(
				primaryKey
			).build();

		_dataStorageAdapter.save(dataStorageAdapterSaveRequest);

		DataStorageAdapterGetRequest dataStorageAdapterGetRequest =
			DataStorageAdapterGetRequest.Builder.of(primaryKey, dataDefinition);

		DataStorageAdapterGetResponse dataStorageAdapterGetResponse =
			_dataStorageAdapter.get(dataStorageAdapterGetRequest);

		Map<String, Object> values = dataStorageAdapterGetResponse.getValues();

		Assert.assertEquals(
			expectedValues.get("column1"), values.get("column1"));
		Assert.assertArrayEquals(
			(Object[])expectedValues.get("column2"),
			(Object[])values.get("column2"));

		DataStorageAdapterDeleteRequest dataStorageAdapterDeleteRequest =
			DataStorageAdapterDeleteRequest.Builder.of(primaryKey);

		_dataStorageAdapter.delete(dataStorageAdapterDeleteRequest);
	}

	@Inject(filter = "data.storage.type=json", type = DataStorageAdapter.class)
	private DataStorageAdapter _dataStorageAdapter;

}
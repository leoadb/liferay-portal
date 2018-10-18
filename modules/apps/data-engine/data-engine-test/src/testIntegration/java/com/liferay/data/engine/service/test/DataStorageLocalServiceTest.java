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

package com.liferay.data.engine.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.model.DataDefinition;
import com.liferay.data.engine.model.DataDefinitionColumn;
import com.liferay.data.engine.model.DataDefinitionColumnType;
import com.liferay.data.engine.model.DataRecord;
import com.liferay.data.engine.service.DataDefinitionDeleteRequest;
import com.liferay.data.engine.service.DataDefinitionLocalService;
import com.liferay.data.engine.service.DataDefinitionSaveRequest;
import com.liferay.data.engine.service.DataDefinitionSaveResponse;
import com.liferay.data.engine.service.DataStorageDeleteRequest;
import com.liferay.data.engine.service.DataStorageExportRequest;
import com.liferay.data.engine.service.DataStorageExportResponse;
import com.liferay.data.engine.service.DataStorageGetCountRequest;
import com.liferay.data.engine.service.DataStorageGetCountResponse;
import com.liferay.data.engine.service.DataStorageGetListRequest;
import com.liferay.data.engine.service.DataStorageGetListResponse;
import com.liferay.data.engine.service.DataStorageGetRequest;
import com.liferay.data.engine.service.DataStorageGetResponse;
import com.liferay.data.engine.service.DataStorageLocalService;
import com.liferay.data.engine.service.DataStorageSaveRequest;
import com.liferay.data.engine.service.DataStorageSaveResponse;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;
import java.io.InputStream;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Leonardo Barros
 */
@RunWith(Arquillian.class)
public class DataStorageLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addGroupOwnerUser(_group);
	}

	@Test
	public void testDeleteByDefinitionId() throws Exception {
		ServiceContext serviceContext = createServiceContext(_group, _user);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		DataDefinition dataDefinition = insertDefinition();

		Map<String, Object> column2Values = new HashMap() {
			{
				put("en_US", "value 2");
				put("pt_BR", "valor 2");
			}
		};

		Map<String, Object> values = new HashMap() {
			{
				put("column1", "value 1");
				put("column2", column2Values);
				put("column3", new Object[] {1, 2, 3});
			}
		};

		DataRecord expectedDataRecord = DataRecord.Builder.of(
			dataDefinition, values);

		DataStorageSaveRequest dataStorageSaveRequest =
			DataStorageSaveRequest.Builder.of(
				_user.getUserId(), _group.getGroupId(), expectedDataRecord);

		_dataStorageLocalService.save(dataStorageSaveRequest);
		_dataStorageLocalService.save(dataStorageSaveRequest);
		_dataStorageLocalService.save(dataStorageSaveRequest);

		DataStorageDeleteRequest dataStorageDeleteRequest =
			DataStorageDeleteRequest.Builder.newBuilder(
			).dataDefinitionId(
				dataDefinition.getDataDefinitionId()
			).build();

		_dataStorageLocalService.delete(dataStorageDeleteRequest);

		DataStorageGetCountRequest dataStorageGetCountRequest =
			DataStorageGetCountRequest.Builder.of(
				dataDefinition.getDataDefinitionId());

		DataStorageGetCountResponse dataStorageGetCountResponse =
			_dataStorageLocalService.getCount(dataStorageGetCountRequest);

		Assert.assertEquals(0, dataStorageGetCountResponse.getCount());
	}

	@Test
	public void testDeleteById() throws Exception {
		ServiceContext serviceContext = createServiceContext(_group, _user);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		DataDefinition dataDefinition = insertDefinition();

		Map<String, Object> column2Values = new HashMap() {
			{
				put("en_US", "value 2");
				put("pt_BR", "valor 2");
			}
		};

		Map<String, Object> values = new HashMap() {
			{
				put("column1", "value 1");
				put("column2", column2Values);
				put("column3", new Object[] {1, 2, 3});
			}
		};

		DataRecord expectedDataRecord = DataRecord.Builder.of(
			dataDefinition, values);

		DataStorageSaveRequest dataStorageSaveRequest =
			DataStorageSaveRequest.Builder.of(
				_user.getUserId(), _group.getGroupId(), expectedDataRecord);

		DataStorageSaveResponse dataStorageSaveResponse =
			_dataStorageLocalService.save(dataStorageSaveRequest);

		long primaryKey = dataStorageSaveResponse.getPrimaryKey();

		DataStorageDeleteRequest dataStorageDeleteRequest =
			DataStorageDeleteRequest.Builder.newBuilder(
			).dataRecordId(
				primaryKey
			).build();

		_dataStorageLocalService.delete(dataStorageDeleteRequest);

		DataStorageGetCountRequest dataStorageGetCountRequest =
			DataStorageGetCountRequest.Builder.of(
				dataDefinition.getDataDefinitionId());

		DataStorageGetCountResponse dataStorageGetCountResponse =
			_dataStorageLocalService.getCount(dataStorageGetCountRequest);

		Assert.assertEquals(0, dataStorageGetCountResponse.getCount());
	}

	@Test
	public void testExport() throws Exception {
		DataDefinition dataDefinition = null;

		try {
			ServiceContext serviceContext = createServiceContext(_group, _user);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			dataDefinition = insertDefinition();

			Map<String, Object> values1 = new HashMap() {
				{
					put("column1", "1");
					put("column3", new Object[] {1, 2, 3});
				}
			};

			DataRecord dataRecord1 = DataRecord.Builder.of(
				dataDefinition, values1);

			DataStorageSaveRequest dataStorageSaveRequest1 =
				DataStorageSaveRequest.Builder.of(
					_user.getUserId(), _group.getGroupId(), dataRecord1);

			_dataStorageLocalService.save(dataStorageSaveRequest1);

			Map<String, Object> values2 = new HashMap() {
				{
					put("column1", "2");
					put("column3", new Object[] {4, 5});
				}
			};

			DataRecord dataRecord2 = DataRecord.Builder.of(
				dataDefinition, values2);

			DataStorageSaveRequest dataStorageSaveRequest2 =
				DataStorageSaveRequest.Builder.of(
					_user.getUserId(), _group.getGroupId(), dataRecord2);

			_dataStorageLocalService.save(dataStorageSaveRequest2);

			Map<String, Object> values3 = new HashMap() {
				{
					put("column1", "3");
					put("column3", new Object[] {6, 7, 8, 9});
				}
			};

			DataRecord dataRecord3 = DataRecord.Builder.of(
				dataDefinition, values3);

			DataStorageSaveRequest dataStorageSaveRequest3 =
				DataStorageSaveRequest.Builder.of(
					_user.getUserId(), _group.getGroupId(), dataRecord3);

			_dataStorageLocalService.save(dataStorageSaveRequest3);

			DataStorageExportRequest dataStorageExportRequest =
				DataStorageExportRequest.Builder.of(
					dataDefinition.getDataDefinitionId());

			DataStorageExportResponse dataStorageExportResponse =
				_dataStorageLocalService.export(dataStorageExportRequest);

			String json = read("data-storage-service-export.json");

			JSONAssert.assertEquals(
				json, dataStorageExportResponse.getContent(), false);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();

			if (dataDefinition != null) {
				deleteDataDefinition(dataDefinition.getDataDefinitionId());
			}
		}
	}

	@Test
	public void testGetCount() throws Exception {
		DataDefinition dataDefinition = null;

		try {
			ServiceContext serviceContext = createServiceContext(_group, _user);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			dataDefinition = insertDefinition();

			Map<String, Object> column2Values = new HashMap() {
				{
					put("en_US", "value 2");
					put("pt_BR", "valor 2");
				}
			};

			Map<String, Object> values = new HashMap() {
				{
					put("column1", "value 1");
					put("column2", column2Values);
					put("column3", new Object[] {1, 2, 3});
				}
			};

			DataRecord expectedDataRecord = DataRecord.Builder.of(
				dataDefinition, values);

			DataStorageSaveRequest dataStorageSaveRequest =
				DataStorageSaveRequest.Builder.of(
					_user.getUserId(), _group.getGroupId(), expectedDataRecord);

			_dataStorageLocalService.save(dataStorageSaveRequest);
			_dataStorageLocalService.save(dataStorageSaveRequest);
			_dataStorageLocalService.save(dataStorageSaveRequest);
			_dataStorageLocalService.save(dataStorageSaveRequest);
			_dataStorageLocalService.save(dataStorageSaveRequest);

			DataStorageGetCountRequest dataStorageGetCountRequest =
				DataStorageGetCountRequest.Builder.of(
					dataDefinition.getDataDefinitionId());

			DataStorageGetCountResponse dataStorageGetCountResponse =
				_dataStorageLocalService.getCount(dataStorageGetCountRequest);

			Assert.assertEquals(5, dataStorageGetCountResponse.getCount());
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();

			if (dataDefinition != null) {
				deleteDataDefinition(dataDefinition.getDataDefinitionId());
			}
		}
	}

	@Test
	public void testGetList() throws Exception {
		DataDefinition dataDefinition = null;

		try {
			ServiceContext serviceContext = createServiceContext(_group, _user);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			dataDefinition = insertDefinition();

			Map<String, Object> values1 = new HashMap() {
				{
					put("column1", "value 1");
				}
			};

			DataRecord expectedDataRecord1 = DataRecord.Builder.of(
				dataDefinition, values1);

			DataStorageSaveRequest dataStorageSaveRequest1 =
				DataStorageSaveRequest.Builder.of(
					_user.getUserId(), _group.getGroupId(),
					expectedDataRecord1);

			DataStorageSaveResponse dataStorageSaveResponse1 =
				_dataStorageLocalService.save(dataStorageSaveRequest1);

			expectedDataRecord1 = DataRecord.Builder.newBuilder(
				dataDefinition, values1
			).dataRecordId(
				dataStorageSaveResponse1.getPrimaryKey()
			).build();

			Map<String, Object> values2 = new HashMap() {
				{
					put("column1", "value 2");
				}
			};

			DataRecord expectedDataRecord2 = DataRecord.Builder.of(
				dataDefinition, values2);

			DataStorageSaveRequest dataStorageSaveRequest2 =
				DataStorageSaveRequest.Builder.of(
					_user.getUserId(), _group.getGroupId(),
					expectedDataRecord2);

			DataStorageSaveResponse dataStorageSaveResponse2 =
				_dataStorageLocalService.save(dataStorageSaveRequest2);

			expectedDataRecord2 = DataRecord.Builder.newBuilder(
				dataDefinition, values2
			).dataRecordId(
				dataStorageSaveResponse2.getPrimaryKey()
			).build();

			Map<String, Object> values3 = new HashMap() {
				{
					put("column1", "value 3");
				}
			};

			DataRecord expectedDataRecord3 = DataRecord.Builder.of(
				dataDefinition, values3);

			DataStorageSaveRequest dataStorageSaveRequest3 =
				DataStorageSaveRequest.Builder.of(
					_user.getUserId(), _group.getGroupId(),
					expectedDataRecord3);

			DataStorageSaveResponse dataStorageSaveResponse3 =
				_dataStorageLocalService.save(dataStorageSaveRequest3);

			expectedDataRecord3 = DataRecord.Builder.newBuilder(
				dataDefinition, values3
			).dataRecordId(
				dataStorageSaveResponse3.getPrimaryKey()
			).build();

			DataStorageGetListRequest dataStorageGetListRequest =
				DataStorageGetListRequest.Builder.newBuilder(
					dataDefinition.getDataDefinitionId()
				).start(
					0
				).end(
					2
				).build();

			DataStorageGetListResponse dataStorageGetListResponse =
				_dataStorageLocalService.getList(dataStorageGetListRequest);

			List<DataRecord> dataRecords =
				dataStorageGetListResponse.getDataRecords();

			Assert.assertEquals(
				dataRecords.toString(),
				Arrays.asList(expectedDataRecord3, expectedDataRecord2),
				dataRecords);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();

			if (dataDefinition != null) {
				deleteDataDefinition(dataDefinition.getDataDefinitionId());
			}
		}
	}

	@Test
	public void testInsert() throws Exception {
		DataDefinition dataDefinition = null;

		try {
			ServiceContext serviceContext = createServiceContext(_group, _user);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			dataDefinition = insertDefinition();

			Map<String, Object> column2Values = new HashMap() {
				{
					put("en_US", "value 2");
					put("pt_BR", "valor 2");
				}
			};

			Map<String, Object> values = new HashMap() {
				{
					put("column1", "value 1");
					put("column2", column2Values);
					put("column3", new Object[] {1, 2, 3});
				}
			};

			DataRecord expectedDataRecord = DataRecord.Builder.of(
				dataDefinition, values);

			DataStorageSaveRequest dataStorageSaveRequest =
				DataStorageSaveRequest.Builder.of(
					_user.getUserId(), _group.getGroupId(), expectedDataRecord);

			DataStorageSaveResponse dataStorageSaveResponse =
				_dataStorageLocalService.save(dataStorageSaveRequest);

			expectedDataRecord = DataRecord.Builder.newBuilder(
				dataDefinition, values
			).dataRecordId(
				dataStorageSaveResponse.getPrimaryKey()
			).build();

			DataStorageGetRequest dataStorageGetRequest =
				DataStorageGetRequest.Builder.of(
					dataStorageSaveResponse.getPrimaryKey());

			DataStorageGetResponse dataStorageGetResponse =
				_dataStorageLocalService.get(dataStorageGetRequest);

			DataRecord dataRecord = dataStorageGetResponse.getDataRecord();

			Assert.assertEquals(expectedDataRecord, dataRecord);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();

			if (dataDefinition != null) {
				deleteDataDefinition(dataDefinition.getDataDefinitionId());
			}
		}
	}

	@Test
	public void testUpdate() throws Exception {
		DataDefinition dataDefinition = null;

		try {
			ServiceContext serviceContext = createServiceContext(_group, _user);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			dataDefinition = insertDefinition();

			Map<String, Object> column2Values = new HashMap() {
				{
					put("en_US", "test 2");
					put("pt_BR", "test 2");
				}
			};

			Map<String, Object> values = new HashMap() {
				{
					put("column1", "test");
					put("column2", column2Values);
					put("column3", new Object[] {2, 3});
				}
			};

			DataRecord expectedDataRecord = DataRecord.Builder.of(
				dataDefinition, values);

			DataStorageSaveRequest dataStorageSaveRequest =
				DataStorageSaveRequest.Builder.of(
					_user.getUserId(), _group.getGroupId(), expectedDataRecord);

			DataStorageSaveResponse dataStorageSaveResponse =
				_dataStorageLocalService.save(dataStorageSaveRequest);

			values = new HashMap() {
				{
					put("column1", "test updated");
					put("column2", column2Values);
					put("column3", new Object[] {1, 2, 3, 4});
				}
			};

			expectedDataRecord = DataRecord.Builder.newBuilder(
				dataDefinition, values
			).dataRecordId(
				dataStorageSaveResponse.getPrimaryKey()
			).build();

			dataStorageSaveRequest = DataStorageSaveRequest.Builder.of(
				_user.getUserId(), _group.getGroupId(), expectedDataRecord);

			dataStorageSaveResponse = _dataStorageLocalService.save(
				dataStorageSaveRequest);

			DataStorageGetRequest dataStorageGetRequest =
				DataStorageGetRequest.Builder.of(
					dataStorageSaveResponse.getPrimaryKey());

			DataStorageGetResponse dataStorageGetResponse =
				_dataStorageLocalService.get(dataStorageGetRequest);

			DataRecord dataRecord = dataStorageGetResponse.getDataRecord();

			Assert.assertEquals(expectedDataRecord, dataRecord);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();

			if (dataDefinition != null) {
				deleteDataDefinition(dataDefinition.getDataDefinitionId());
			}
		}
	}

	protected ServiceContext createServiceContext(Group group, User user) {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(false);
		serviceContext.setAddGuestPermissions(false);
		serviceContext.setCompanyId(group.getCompanyId());
		serviceContext.setScopeGroupId(group.getGroupId());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	protected void deleteDataDefinition(long dataDefinitionId)
		throws Exception {

		DataDefinitionDeleteRequest dataDefinitionDeleteRequest =
			DataDefinitionDeleteRequest.Builder.of(dataDefinitionId);

		_dataDefinitionLocalService.delete(dataDefinitionDeleteRequest);
	}

	protected DataDefinition insertDefinition() throws Exception {
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

		DataDefinitionSaveRequest dataDefinitionSaveRequest =
			DataDefinitionSaveRequest.Builder.of(
				_user.getUserId(), _group.getGroupId(), dataDefinition
			);

		DataDefinitionSaveResponse dataDefinitionSaveResponse =
			_dataDefinitionLocalService.save(dataDefinitionSaveRequest);

		dataDefinition.setPrimaryKeyObj(
			dataDefinitionSaveResponse.getDataDefinitionId());

		return dataDefinition;
	}

	protected String read(String fileName) throws IOException {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(fileName);

		return StringUtil.read(inputStream);
	}

	@Inject(type = DataDefinitionLocalService.class)
	private DataDefinitionLocalService _dataDefinitionLocalService;

	@Inject(type = DataStorageLocalService.class)
	private DataStorageLocalService _dataStorageLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user;

}
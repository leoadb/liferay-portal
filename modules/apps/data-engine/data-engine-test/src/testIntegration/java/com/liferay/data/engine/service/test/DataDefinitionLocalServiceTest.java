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
import com.liferay.data.engine.exception.DataDefinitionException;
import com.liferay.data.engine.model.DataDefinition;
import com.liferay.data.engine.model.DataDefinitionColumn;
import com.liferay.data.engine.model.DataDefinitionColumnType;
import com.liferay.data.engine.service.DataDefinitionDeleteRequest;
import com.liferay.data.engine.service.DataDefinitionGetCountRequest;
import com.liferay.data.engine.service.DataDefinitionGetCountResponse;
import com.liferay.data.engine.service.DataDefinitionGetListRequest;
import com.liferay.data.engine.service.DataDefinitionGetListResponse;
import com.liferay.data.engine.service.DataDefinitionGetRequest;
import com.liferay.data.engine.service.DataDefinitionGetResponse;
import com.liferay.data.engine.service.DataDefinitionLocalService;
import com.liferay.data.engine.service.DataDefinitionSaveRequest;
import com.liferay.data.engine.service.DataDefinitionSaveResponse;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Leonardo Barros
 */
@RunWith(Arquillian.class)
public class DataDefinitionLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addGroupOwnerUser(_group);
	}

	@Test(expected = DataDefinitionException.class)
	public void testDelete() throws Exception {
		DataDefinition dataDefinition = new DataDefinition();

		dataDefinition.setStorageType("json");

		dataDefinition.addName("en_US", "Definition 1");

		Map<String, String> expectedNameLabels = new HashMap() {
			{
				put("pt_BR", "Nome");
				put("en_US", "Name");
			}
		};

		DataDefinitionColumn dataDefinitionColumn =
			DataDefinitionColumn.Builder.newBuilder(
				"name", DataDefinitionColumnType.TEXT
			).labels(
				expectedNameLabels
			).build();

		dataDefinition.addColumn(dataDefinitionColumn);

		DataDefinitionSaveRequest dataDefinitionSaveRequest =
			DataDefinitionSaveRequest.Builder.of(
				_user.getUserId(), _group.getGroupId(), dataDefinition
			);

		try {
			ServiceContext serviceContext = createServiceContext(
				_group, _user, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DataDefinitionSaveResponse dataDefinitionSaveResponse =
				_dataDefinitionLocalService.save(dataDefinitionSaveRequest);

			long dataDefinitionId =
				dataDefinitionSaveResponse.getDataDefinitionId();

			DataDefinitionDeleteRequest dataDefinitionDeleteRequest =
				DataDefinitionDeleteRequest.Builder.of(dataDefinitionId);

			_dataDefinitionLocalService.delete(dataDefinitionDeleteRequest);

			DataDefinitionGetRequest dataDefinitionGetRequest =
				DataDefinitionGetRequest.Builder.of(dataDefinitionId);

			_dataDefinitionLocalService.get(dataDefinitionGetRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testGet() throws Exception {
		DataDefinition expectedDataDefinition = new DataDefinition();

		expectedDataDefinition.setStorageType("json");

		expectedDataDefinition.addName("en_US", "Definition 2");

		Map<String, String> column1Labels = new HashMap() {
			{
				put("en_US", "Column 1");
			}
		};

		DataDefinitionColumn dataDefinitionColumn1 =
			DataDefinitionColumn.Builder.newBuilder(
				"column1", DataDefinitionColumnType.TEXT
			).labels(
				column1Labels
			).build();

		expectedDataDefinition.addColumn(dataDefinitionColumn1);

		Map<String, String> column2Labels = new HashMap() {
			{
				put("en_US", "Column 2");
			}
		};

		DataDefinitionColumn dataDefinitionColumn2 =
			DataDefinitionColumn.Builder.newBuilder(
				"column2", DataDefinitionColumnType.NUMBER
			).labels(
				column2Labels
			).build();

		expectedDataDefinition.addColumn(dataDefinitionColumn2);

		Map<String, String> column3Labels = new HashMap() {
			{
				put("en_US", "Column 3");
			}
		};

		DataDefinitionColumn dataDefinitionColumn3 =
			DataDefinitionColumn.Builder.newBuilder(
				"column3", DataDefinitionColumnType.DATE
			).labels(
				column3Labels
			).build();

		expectedDataDefinition.addColumn(dataDefinitionColumn3);

		DataDefinitionSaveRequest dataDefinitionSaveRequest =
			DataDefinitionSaveRequest.Builder.of(
				_user.getUserId(), _group.getGroupId(), expectedDataDefinition
			);

		try {
			ServiceContext serviceContext = createServiceContext(
				_group, _user, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DataDefinitionSaveResponse dataDefinitionSaveResponse =
				_dataDefinitionLocalService.save(dataDefinitionSaveRequest);

			long dataDefinitionId =
				dataDefinitionSaveResponse.getDataDefinitionId();

			expectedDataDefinition.setDataDefinitionId(dataDefinitionId);

			DataDefinitionGetRequest dataDefinitionGetRequest =
				DataDefinitionGetRequest.Builder.of(dataDefinitionId);

			DataDefinitionGetResponse dataDefinitionGetResponse =
				_dataDefinitionLocalService.get(dataDefinitionGetRequest);

			Assert.assertEquals(
				expectedDataDefinition,
				dataDefinitionGetResponse.getDataDefinition());

			DataDefinitionDeleteRequest dataDefinitionDeleteRequest =
				DataDefinitionDeleteRequest.Builder.of(dataDefinitionId);

			_dataDefinitionLocalService.delete(dataDefinitionDeleteRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testGetList() throws Exception {
		Group group2 = GroupTestUtil.addGroup();

		User user2 = UserTestUtil.addGroupOwnerUser(group2);

		DataDefinition dataDefinition1 = new DataDefinition();

		dataDefinition1.setStorageType("json");

		dataDefinition1.addName("en_US", "Definition 1");

		Map<String, String> expectedNameLabels = new HashMap() {
			{
				put("pt_BR", "Nome");
				put("en_US", "Name");
			}
		};

		DataDefinitionColumn dataDefinitionColumn =
			DataDefinitionColumn.Builder.newBuilder(
				"name", DataDefinitionColumnType.TEXT
			).labels(
				expectedNameLabels
			).build();

		dataDefinition1.addColumn(dataDefinitionColumn);

		DataDefinitionSaveRequest dataDefinitionSaveRequest1 =
			DataDefinitionSaveRequest.Builder.of(
				_user.getUserId(), _group.getGroupId(), dataDefinition1
			);

		DataDefinition dataDefinition2 = new DataDefinition();

		dataDefinition2.setStorageType("json");

		dataDefinition2.addName("en_US", "Definition 2");

		dataDefinition2.addColumn(dataDefinitionColumn);

		DataDefinitionSaveRequest dataDefinitionSaveRequest2 =
			DataDefinitionSaveRequest.Builder.of(
				user2.getUserId(), group2.getGroupId(), dataDefinition2
			);

		try {
			ServiceContext serviceContext1 = createServiceContext(
				_group, _user, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext1);

			DataDefinitionSaveResponse dataDefinitionSaveResponse1 =
				_dataDefinitionLocalService.save(dataDefinitionSaveRequest1);

			dataDefinition1.setDataDefinitionId(
				dataDefinitionSaveResponse1.getDataDefinitionId());

			ServiceContext serviceContext2 = createServiceContext(
				group2, user2, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext2);

			DataDefinitionSaveResponse dataDefinitionSaveResponse2 =
				_dataDefinitionLocalService.save(dataDefinitionSaveRequest2);

			dataDefinition2.setDataDefinitionId(
				dataDefinitionSaveResponse2.getDataDefinitionId());

			DataDefinitionGetCountRequest dataDefinitionGetCountRequest =
				DataDefinitionGetCountRequest.Builder.of(group2.getGroupId());

			DataDefinitionGetCountResponse dataDefinitionGetCountResponse =
				_dataDefinitionLocalService.getCount(
					dataDefinitionGetCountRequest);

			Assert.assertEquals(1, dataDefinitionGetCountResponse.getCount());

			DataDefinitionGetListRequest dataDefinitionGetListRequest =
				DataDefinitionGetListRequest.Builder.of(group2.getGroupId());

			DataDefinitionGetListResponse dataDefinitionGetListResponse =
				_dataDefinitionLocalService.getList(
					dataDefinitionGetListRequest);

			List<DataDefinition> dataDefinitions =
				dataDefinitionGetListResponse.getDataDefinitions();

			Assert.assertEquals(
				dataDefinitions.toString(), 1, dataDefinitions.size());

			Assert.assertEquals(dataDefinition2, dataDefinitions.get(0));

			DataDefinitionDeleteRequest dataDefinitionDeleteRequest1 =
				DataDefinitionDeleteRequest.Builder.of(
					dataDefinitionSaveResponse1.getDataDefinitionId());

			_dataDefinitionLocalService.delete(dataDefinitionDeleteRequest1);

			DataDefinitionDeleteRequest dataDefinitionDeleteRequest2 =
				DataDefinitionDeleteRequest.Builder.of(
					dataDefinitionSaveResponse2.getDataDefinitionId());

			_dataDefinitionLocalService.delete(dataDefinitionDeleteRequest2);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testInsert() throws Exception {
		DataDefinition expectedDataDefinition = new DataDefinition();

		expectedDataDefinition.setStorageType("json");

		expectedDataDefinition.addName("pt_BR", "Contato");
		expectedDataDefinition.addName("en_US", "Contact");

		expectedDataDefinition.addDescription("pt_BR", "Descrição do contato");
		expectedDataDefinition.addDescription("en_US", "Contact description");

		Map<String, String> expectedNameLabels = new HashMap() {
			{
				put("pt_BR", "Nome");
				put("en_US", "Name");
			}
		};

		DataDefinitionColumn expectedDataDefinitionColumn1 =
			DataDefinitionColumn.Builder.newBuilder(
				"name", DataDefinitionColumnType.TEXT
			).labels(
				expectedNameLabels
			).build();

		Map<String, String> expectedEmailLabels = new HashMap() {
			{
				put("pt_BR", "Endereço de Email");
				put("en_US", "Email Address");
			}
		};

		DataDefinitionColumn expectedDataDefinitionColumn2 =
			DataDefinitionColumn.Builder.newBuilder(
				"email", DataDefinitionColumnType.TEXT
			).labels(
				expectedEmailLabels
			).build();

		expectedDataDefinition.addColumn(expectedDataDefinitionColumn1);
		expectedDataDefinition.addColumn(expectedDataDefinitionColumn2);

		DataDefinitionSaveRequest dataDefinitionSaveRequest =
			DataDefinitionSaveRequest.Builder.of(
				_user.getUserId(), _group.getGroupId(), expectedDataDefinition
			);

		try {
			ServiceContext serviceContext = createServiceContext(
				_group, _user, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DataDefinitionSaveResponse dataDefinitionSaveResponse =
				_dataDefinitionLocalService.save(dataDefinitionSaveRequest);

			long dataDefinitionId =
				dataDefinitionSaveResponse.getDataDefinitionId();

			expectedDataDefinition.setDataDefinitionId(dataDefinitionId);

			DataDefinitionGetRequest dataDefinitionGetRequest =
				DataDefinitionGetRequest.Builder.of(dataDefinitionId);

			DataDefinitionGetResponse dataDefinitionGetResponse =
				_dataDefinitionLocalService.get(dataDefinitionGetRequest);

			DataDefinition dataDefinition =
				dataDefinitionGetResponse.getDataDefinition();

			Assert.assertEquals(expectedDataDefinition, dataDefinition);

			Role ownerRole = _roleLocalService.getRole(
				_group.getCompanyId(), RoleConstants.OWNER);

			ResourcePermission resourcePermission =
				_resourcePermissionLocalService.fetchResourcePermission(
					_group.getCompanyId(), DataDefinition.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(dataDefinitionId), ownerRole.getRoleId());

			Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.VIEW));

			DataDefinitionDeleteRequest dataDefinitionDeleteRequest =
				DataDefinitionDeleteRequest.Builder.of(dataDefinitionId);

			_dataDefinitionLocalService.delete(dataDefinitionDeleteRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testUpdate() throws Exception {
		DataDefinition expectedDataDefinition = new DataDefinition();

		expectedDataDefinition.setStorageType("json");

		expectedDataDefinition.addName("en_US", "Story");
		expectedDataDefinition.addName("pt_BR", "Estória");

		Map<String, String> expectedTitleLabels = new HashMap() {
			{
				put("pt_BR", "Título");
				put("en_US", "Title");
			}
		};

		DataDefinitionColumn dataDefinitionColumn1 =
			DataDefinitionColumn.Builder.newBuilder(
				"title", DataDefinitionColumnType.TEXT
			).labels(
				expectedTitleLabels
			).localizable(
				true
			).build();

		expectedDataDefinition.addColumn(dataDefinitionColumn1);

		DataDefinitionSaveRequest dataDefinitionSaveRequest =
			DataDefinitionSaveRequest.Builder.of(
				_user.getUserId(), _group.getGroupId(), expectedDataDefinition
			);

		try {
			ServiceContext serviceContext = createServiceContext(
				_group, _user, createModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DataDefinitionSaveResponse dataDefinitionSaveResponse =
				_dataDefinitionLocalService.save(dataDefinitionSaveRequest);

			long dataDefinitionId =
				dataDefinitionSaveResponse.getDataDefinitionId();

			expectedDataDefinition.setDataDefinitionId(dataDefinitionId);

			Map<String, String> expectedDescriptionLabels = new HashMap() {
				{
					put("pt_BR", "Descrição");
					put("en_US", "Description");
				}
			};

			DataDefinitionColumn dataDefinitionColumn2 =
				DataDefinitionColumn.Builder.newBuilder(
					"description", DataDefinitionColumnType.TEXT
				).labels(
					expectedDescriptionLabels
				).localizable(
					true
				).build();

			expectedDataDefinition.addColumn(dataDefinitionColumn2);

			dataDefinitionSaveRequest = DataDefinitionSaveRequest.Builder.of(
				_user.getUserId(), _group.getGroupId(), expectedDataDefinition
			);

			_dataDefinitionLocalService.save(dataDefinitionSaveRequest);

			DataDefinitionGetRequest dataDefinitionGetRequest =
				DataDefinitionGetRequest.Builder.of(dataDefinitionId);

			DataDefinitionGetResponse dataDefinitionGetResponse =
				_dataDefinitionLocalService.get(dataDefinitionGetRequest);

			DataDefinition dataDefinition =
				dataDefinitionGetResponse.getDataDefinition();

			Assert.assertEquals(expectedDataDefinition, dataDefinition);

			DataDefinitionDeleteRequest dataDefinitionDeleteRequest =
				DataDefinitionDeleteRequest.Builder.of(dataDefinitionId);

			_dataDefinitionLocalService.delete(dataDefinitionDeleteRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	protected ModelPermissions createModelPermissions() {
		ModelPermissions modelPermissions = new ModelPermissions();

		modelPermissions.addRolePermissions(
			RoleConstants.OWNER, ActionKeys.VIEW);

		return modelPermissions;
	}

	protected ServiceContext createServiceContext(
		Group group, User user, ModelPermissions modelPermissions) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(false);
		serviceContext.setAddGuestPermissions(false);
		serviceContext.setCompanyId(group.getCompanyId());
		serviceContext.setModelPermissions(modelPermissions);
		serviceContext.setScopeGroupId(group.getGroupId());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	@Inject(type = DataDefinitionLocalService.class)
	private DataDefinitionLocalService _dataDefinitionLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(type = ResourcePermissionLocalService.class)
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject(type = RoleLocalService.class)
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private User _user;

}
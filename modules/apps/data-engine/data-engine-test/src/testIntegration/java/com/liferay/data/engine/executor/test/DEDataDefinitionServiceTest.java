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

package com.liferay.data.engine.executor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.exception.DEDataDefinitionException;
import com.liferay.data.engine.executor.DEGetRequestExecutor;
import com.liferay.data.engine.executor.DESaveRequestExecutor;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.data.engine.service.DEDataDefinitionDeleteRequest;
import com.liferay.data.engine.service.DEDataDefinitionGetRequest;
import com.liferay.data.engine.service.DEDataDefinitionGetResponse;
import com.liferay.data.engine.service.DEDataDefinitionRequestBuilder;
import com.liferay.data.engine.service.DEDataDefinitionSaveRequest;
import com.liferay.data.engine.service.DEDataDefinitionSaveResponse;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
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
public class DEDataDefinitionServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		setUpPermissionThreadLocal();
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Test
	public void testDelete() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addGroupUser(group, RoleConstants.SITE_MEMBER);

		deleteDataDefinition(user, group);
	}

	@Test(expected = DEDataDefinitionException.NoSuchDataDefinition.class)
	public void testDeleteNoSuchDataDefinition() throws Exception {
		try {
			Group group = GroupTestUtil.addGroup();

			User user = UserTestUtil.addGroupUser(
				group, RoleConstants.SITE_MEMBER);

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			ServiceContext serviceContext = createServiceContext(
				user, group, new ModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequest =
				DEDataDefinitionRequestBuilder.deleteBuilder(
				).byId(
					1
				).build();

			_deDataDefinitionService.execute(deDataDefinitionDeleteRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testDeleteWithNoPermission() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		deleteDataDefinition(user, group);
	}

	@Test(expected = DEDataDefinitionException.NoSuchDataDefinition.class)
	public void testGetNoSuchDataDefinition() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addGroupUser(group, RoleConstants.SITE_MEMBER);

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			ServiceContext serviceContext = createServiceContext(
				user, group, new ModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DEDataDefinitionGetRequest deDataDefinitionGetRequest =
				DEDataDefinitionRequestBuilder.getBuilder(
				).byId(
					1
				).build();

			_deGetRequestExecutor.executeGetRequest(deDataDefinitionGetRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testInsert() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addGroupUser(group, RoleConstants.SITE_MEMBER);

		long dataDefinitionId = callServiceToInsertDataDefinition(user, group);

		Assert.assertTrue(dataDefinitionId > 0);
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testInsertWithNoPermission() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		callServiceToInsertDataDefinition(user, group);
	}

	@Test
	public void testUpdate() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addGroupUser(group, RoleConstants.SITE_MEMBER);

		long dataDefinitionId = callServiceToInsertDataDefinition(user, group);

		DEDataDefinitionGetRequest deDataDefinitionGetRequest =
			DEDataDefinitionRequestBuilder.getBuilder(
			).byId(
				dataDefinitionId
			).build();

		DEDataDefinitionGetResponse deDataDefinitionGetResponse =
			_deGetRequestExecutor.executeGetRequest(deDataDefinitionGetRequest);

		DEDataDefinition deDataDefinition =
			deDataDefinitionGetResponse.getDeDataDefinition();

		List<DEDataDefinitionField> deDataDefinitionFields =
			deDataDefinition.getDEDataDefinitionFields();

		DEDataDefinitionField deDataDefinitionField2 =
			new DEDataDefinitionField("field2", "string");

		deDataDefinitionFields.add(deDataDefinitionField2);

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			ServiceContext serviceContext = createServiceContext(
				user, group, new ModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
				DEDataDefinitionRequestBuilder.saveBuilder(
					deDataDefinition
				).onBehalfOf(
					user.getUserId()
				).inGroup(
					group.getGroupId()
				).build();

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponse =
				_deDataDefinitionService.execute(deDataDefinitionSaveRequest);

			long deDataDefinitionId =
				deDataDefinitionSaveResponse.getDEDataDefinitionId();

			Assert.assertTrue(deDataDefinitionId > 0);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataDefinitionException.NoSuchDataDefinition.class)
	public void testUpdateNoSuchDataDefinition() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addGroupUser(group, RoleConstants.SITE_MEMBER);

		long dataDefinitionId = callServiceToInsertDataDefinition(user, group);

		DEDataDefinitionGetRequest deDataDefinitionGetRequest =
			DEDataDefinitionRequestBuilder.getBuilder(
			).byId(
				dataDefinitionId
			).build();

		DEDataDefinitionGetResponse deDataDefinitionGetResponse =
			_deGetRequestExecutor.executeGetRequest(deDataDefinitionGetRequest);

		DEDataDefinition deDataDefinition =
			deDataDefinitionGetResponse.getDeDataDefinition();

		deDataDefinition.setDEDataDefinitionId(1);

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			ServiceContext serviceContext = createServiceContext(
				user, group, new ModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
				DEDataDefinitionRequestBuilder.saveBuilder(
					deDataDefinition
				).onBehalfOf(
					user.getUserId()
				).inGroup(
					group.getGroupId()
				).build();

			_deDataDefinitionService.execute(deDataDefinitionSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testUpdateWithNoPermission() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addGroupUser(group, RoleConstants.SITE_MEMBER);

		long dataDefinitionId = callServiceToInsertDataDefinition(user, group);

		DEDataDefinitionGetRequest deDataDefinitionGetRequest =
			DEDataDefinitionRequestBuilder.getBuilder(
			).byId(
				dataDefinitionId
			).build();

		DEDataDefinitionGetResponse deDataDefinitionGetResponse =
			_deGetRequestExecutor.executeGetRequest(deDataDefinitionGetRequest);

		DEDataDefinition deDataDefinition =
			deDataDefinitionGetResponse.getDeDataDefinition();

		try {
			user = _userLocalService.getDefaultUser(
				TestPropsValues.getCompanyId());

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			ServiceContext serviceContext = createServiceContext(
				user, group, new ModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
				DEDataDefinitionRequestBuilder.saveBuilder(
					deDataDefinition
				).onBehalfOf(
					user.getUserId()
				).inGroup(
					group.getGroupId()
				).build();

			_deDataDefinitionService.execute(deDataDefinitionSaveRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	protected long callExecutorToInsertDataDefinition(User user, Group group)
		throws Exception {

		Map<String, String> nameLabels = new HashMap() {
			{
				put("pt_BR", "Nome");
				put("en_US", "Name");
			}
		};

		DEDataDefinitionField deDataDefinitionField1 =
			new DEDataDefinitionField("name", "string");

		deDataDefinitionField1.addLabels(nameLabels);

		Map<String, String> emailLabels = new HashMap() {
			{
				put("pt_BR", "Endereço de Email");
				put("en_US", "Email Address");
			}
		};

		DEDataDefinitionField deDataDefinitionField2 =
			new DEDataDefinitionField("email", "string");

		deDataDefinitionField1.addLabels(emailLabels);

		DEDataDefinition deDataDefinition = new DEDataDefinition(
			Arrays.asList(deDataDefinitionField1, deDataDefinitionField2));

		deDataDefinition.addDescription(LocaleUtil.US, "Contact description");
		deDataDefinition.addDescription(
			LocaleUtil.BRAZIL, "Descrição do contato");
		deDataDefinition.addName(LocaleUtil.US, "Contact");
		deDataDefinition.addName(LocaleUtil.BRAZIL, "Contato");
		deDataDefinition.setStorageType("json");

		DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
			DEDataDefinitionRequestBuilder.saveBuilder(
				deDataDefinition
			).onBehalfOf(
				user.getUserId()
			).inGroup(
				group.getGroupId()
			).build();

		DEDataDefinitionSaveResponse deDataDefinitionSaveResponse =
			_deSaveRequestExecutor.executeSaveRequest(
				deDataDefinitionSaveRequest);

		return deDataDefinitionSaveResponse.getDEDataDefinitionId();
	}

	protected long callServiceToInsertDataDefinition(User user, Group group)
		throws Exception {

		long deDataDefinitionId = 0;

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			ServiceContext serviceContext = createServiceContext(
				user, group, new ModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DEDataDefinitionField deDataDefinitionField1 =
				new DEDataDefinitionField("field", "string");

			DEDataDefinition deDataDefinition = new DEDataDefinition(
				Arrays.asList(deDataDefinitionField1));

			deDataDefinition.addDescription(
				LocaleUtil.US, "Definition description");
			deDataDefinition.addDescription(
				LocaleUtil.BRAZIL, "Descrição da definição");
			deDataDefinition.addName(LocaleUtil.US, "Definition");
			deDataDefinition.addName(LocaleUtil.BRAZIL, "Definição");
			deDataDefinition.setStorageType("json");

			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
				DEDataDefinitionRequestBuilder.saveBuilder(
					deDataDefinition
				).onBehalfOf(
					user.getUserId()
				).inGroup(
					group.getGroupId()
				).build();

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponse =
				_deDataDefinitionService.execute(deDataDefinitionSaveRequest);

			deDataDefinitionId =
				deDataDefinitionSaveResponse.getDEDataDefinitionId();
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		return deDataDefinitionId;
	}

	protected ServiceContext createServiceContext(
		User user, Group group, ModelPermissions modelPermissions) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(false);
		serviceContext.setAddGuestPermissions(false);
		serviceContext.setCompanyId(group.getCompanyId());
		serviceContext.setModelPermissions(modelPermissions);
		serviceContext.setScopeGroupId(group.getGroupId());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	protected void deleteDataDefinition(User user, Group group)
		throws Exception {

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			ServiceContext serviceContext = createServiceContext(
				user, group, new ModelPermissions());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			long dataDefinitionId = callExecutorToInsertDataDefinition(
				user, group);

			DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequest =
				DEDataDefinitionRequestBuilder.deleteBuilder(
				).byId(
					dataDefinitionId
				).build();

			_deDataDefinitionService.execute(deDataDefinitionDeleteRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	protected void setUpPermissionThreadLocal() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
	}

	@Inject(type = DEDataDefinitionService.class)
	private DEDataDefinitionService _deDataDefinitionService;

	@Inject(type = DEGetRequestExecutor.class)
	private DEGetRequestExecutor _deGetRequestExecutor;

	@Inject(type = DESaveRequestExecutor.class)
	private DESaveRequestExecutor _deSaveRequestExecutor;

	private PermissionChecker _originalPermissionChecker;

	@Inject(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}
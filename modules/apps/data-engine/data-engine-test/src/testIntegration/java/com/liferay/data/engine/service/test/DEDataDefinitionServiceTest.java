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
import com.liferay.data.engine.exception.DEDataDefinitionException;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.data.engine.service.DEDataDefinitionDeleteRequest;
import com.liferay.data.engine.service.DEDataDefinitionGetRequest;
import com.liferay.data.engine.service.DEDataDefinitionGetResponse;
import com.liferay.data.engine.service.DEDataDefinitionRequestBuilder;
import com.liferay.data.engine.service.DEDataDefinitionSaveModelPermissionsRequest;
import com.liferay.data.engine.service.DEDataDefinitionSaveRequest;
import com.liferay.data.engine.service.DEDataDefinitionSaveResponse;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.HashMap;
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

		_group = GroupTestUtil.addGroup();

		_siteMember = UserTestUtil.addGroupUser(
			_group, RoleConstants.SITE_MEMBER);

		_adminUser = UserTestUtil.addOmniAdminUser();
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Test
	public void testDelete() throws Exception {
		long deDataDefinitionId = insertDEDataDefinition(_adminUser, _group);

		deleteDataDefinition(_siteMember, deDataDefinitionId);
	}

	@Test(expected = DEDataDefinitionException.NoSuchDataDefinition.class)
	public void testDeleteNoSuchDataDefinition() throws Exception {
		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(_siteMember));

			ServiceContext serviceContext = createServiceContext(
				_siteMember, _group);

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
		long deDataDefinitionId = insertDEDataDefinition(_adminUser, _group);

		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		deleteDataDefinition(user, deDataDefinitionId);
	}

	@Test
	public void testGet() throws Exception {
		long deDataDefinitionId = insertDEDataDefinition(_adminUser, _group);

		DEDataDefinition deDataDefinition = getDataDefinition(
			_siteMember, deDataDefinitionId);

		Assert.assertEquals(
			deDataDefinitionId, deDataDefinition.getDEDataDefinitionId());
	}

	@Test(expected = DEDataDefinitionException.NoSuchDataDefinition.class)
	public void testGetNoSuchDataDefinition() throws Exception {
		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(_siteMember));

			ServiceContext serviceContext = createServiceContext(
				_siteMember, _group);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DEDataDefinitionGetRequest deDataDefinitionGetRequest =
				DEDataDefinitionRequestBuilder.getBuilder(
				).byId(
					1
				).build();

			_deDataDefinitionService.execute(deDataDefinitionGetRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testGetWithNoPermission() throws Exception {
		long deDataDefinitionId = insertDEDataDefinition(_adminUser, _group);

		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		getDataDefinition(user, deDataDefinitionId);
	}

	@Test
	public void testGrantPermissionDelete() throws Exception {
		long deDataDefinitionId = insertDEDataDefinition(_adminUser, _group);

		User organizationUser = UserTestUtil.addGroupUser(
			_group, RoleConstants.GUEST);

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(_adminUser));

			ServiceContext serviceContext = createServiceContext(
				organizationUser, _group);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DEDataDefinitionSaveModelPermissionsRequest
				deDataDefinitionSaveModelPermissionsRequest =
					DEDataDefinitionRequestBuilder.saveModelPermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						deDataDefinitionId
					).grantTo(
						organizationUser.getUserId()
					).inGroup(
						_group.getGroupId()
					).allowDelete(
					).build();

			_deDataDefinitionService.execute(
				deDataDefinitionSaveModelPermissionsRequest);

			deleteDataDefinition(organizationUser, deDataDefinitionId);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testInsert() throws Exception {
		insertDEDataDefinition(_siteMember, _group);
	}

	//@Test(expected = DEDataDefinitionException.MustHavePermission.class)
	public void testInsertWithNoPermission() throws Exception {
	}

	@Test
	public void testUpdate() throws Exception {
		Map<String, String> field1Labels = new HashMap() {
			{
				put("en_US", "Field 1");
			}
		};

		DEDataDefinitionField deDataDefinitionField = new DEDataDefinitionField(
			"field1", "string");

		deDataDefinitionField.addLabels(field1Labels);

		DEDataDefinition expectedDEDataDefinition = new DEDataDefinition(
			Arrays.asList(deDataDefinitionField));

		expectedDEDataDefinition.addName(LocaleUtil.US, "Definition 1");
		expectedDEDataDefinition.setStorageType("json");

		DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
			DEDataDefinitionRequestBuilder.saveBuilder(
				expectedDEDataDefinition
			).onBehalfOf(
				_siteMember.getUserId()
			).inGroup(
				_group.getGroupId()
			).build();

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(_adminUser));

			ServiceContext serviceContext = createServiceContext(
				_siteMember, _group);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponse =
				_deDataDefinitionService.execute(deDataDefinitionSaveRequest);

			long deDataDefinitionId =
				deDataDefinitionSaveResponse.getDEDataDefinitionId();

			expectedDEDataDefinition.setPrimaryKeyObj(deDataDefinitionId);

			DEDataDefinition deDataDefinition = getDataDefinition(
				_siteMember, deDataDefinitionId);

			Assert.assertEquals(expectedDEDataDefinition, deDataDefinition);

			expectedDEDataDefinition.addName(
				LocaleUtil.BRAZIL, "Definition BR");

			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest2 =
				DEDataDefinitionRequestBuilder.saveBuilder(
					expectedDEDataDefinition
				).onBehalfOf(
					_siteMember.getUserId()
				).inGroup(
					_group.getGroupId()
				).build();

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponse2 =
				_deDataDefinitionService.execute(deDataDefinitionSaveRequest2);

			DEDataDefinition deDataDefinitionAfterUpdate = getDataDefinition(
				_siteMember,
				deDataDefinitionSaveResponse2.getDEDataDefinitionId());

			Assert.assertEquals(
				expectedDEDataDefinition, deDataDefinitionAfterUpdate);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	protected ServiceContext createServiceContext(User user, Group group) {
		ServiceContext serviceContext = new ServiceContext();

		ModelPermissions modelPermissions = new ModelPermissions();

		modelPermissions.addRolePermissions(
			RoleConstants.SITE_MEMBER,
			new String[] {
				ActionKeys.DELETE, ActionKeys.UPDATE, ActionKeys.VIEW
			});

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(false);
		serviceContext.setCompanyId(group.getCompanyId());
		serviceContext.setModelPermissions(modelPermissions);
		serviceContext.setScopeGroupId(group.getGroupId());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	protected void deleteDataDefinition(User user, long deDataDefinitionId)
		throws Exception {

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			ServiceContext serviceContext = createServiceContext(user, _group);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequest =
				DEDataDefinitionRequestBuilder.deleteBuilder(
				).byId(
					deDataDefinitionId
				).build();

			_deDataDefinitionService.execute(deDataDefinitionDeleteRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	protected DEDataDefinition getDataDefinition(
			User user, long deDataDefinitionId)
		throws Exception {

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			ServiceContext serviceContext = createServiceContext(user, _group);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			DEDataDefinitionGetRequest deDataDefinitionGetRequest =
				DEDataDefinitionRequestBuilder.getBuilder(
				).byId(
					deDataDefinitionId
				).build();

			DEDataDefinitionGetResponse deDataDefinitionGetResponse =
				_deDataDefinitionService.execute(deDataDefinitionGetRequest);

			return deDataDefinitionGetResponse.getDeDataDefinition();
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	protected long insertDEDataDefinition(User user, Group group)
		throws Exception {

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(_adminUser));

			ServiceContext serviceContext = createServiceContext(user, group);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

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

			deDataDefinition.addDescription(
				LocaleUtil.US, "Contact description");
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
				_deDataDefinitionService.execute(deDataDefinitionSaveRequest);

			return deDataDefinitionSaveResponse.getDEDataDefinitionId();
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	protected void setUpPermissionThreadLocal() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
	}

	@DeleteAfterTestRun
	private User _adminUser;

	@Inject(type = DEDataDefinitionService.class)
	private DEDataDefinitionService _deDataDefinitionService;

	@DeleteAfterTestRun
	private Group _group;

	private PermissionChecker _originalPermissionChecker;

	@DeleteAfterTestRun
	private User _siteMember;

	@Inject(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}
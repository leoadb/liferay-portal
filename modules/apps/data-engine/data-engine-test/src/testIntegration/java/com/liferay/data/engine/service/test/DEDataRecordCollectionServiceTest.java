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
import com.liferay.data.engine.exception.DEDataRecordCollectionException;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataRecordCollection;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.data.engine.service.DEDataRecordCollectionRequestBuilder;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveModelPermissionsRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSavePermissionsRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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
public class DEDataRecordCollectionServiceTest {

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

		setUpServiceContext();
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();

		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Test
	public void testAddDataRecordCollectionPermission() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		RoleLocalServiceUtil.addGroupRole(_group.getGroupId(), role);

		User user = UserTestUtil.addGroupUser(_group, role.getName());

//		PermissionThreadLocal.setPermissionChecker(
//			PermissionCheckerFactoryUtil.create(_adminUser));

//		DEDataRecordCollectionSavePermissionsRequest
//			deDataRecordCollectionSavePermissionsRequest =
//				DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
//					TestPropsValues.getCompanyId(), _adminUser.getGroupId(),
//					new String[] {role.getName()}
//				).allowAddDataRecordCollection(//				).build();
//
//		_deDataRecordCollectionService.execute(
//			deDataRecordCollectionSavePermissionsRequest);

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				user, _group, deDataDefinition, _deDataRecordCollectionService);

		Assert.assertTrue(
			deDataRecordCollection.getDEDataRecordCollectionId() > 0);
	}

	@Test(expected = DEDataRecordCollectionException.PrincipalException.class)
	public void testAddDataRecordCollectionPermissionToGuestUser()
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		DEDataRecordCollectionSavePermissionsRequest
			deDataRecordCollectionSavePermissionsRequest =
				DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
					TestPropsValues.getCompanyId(), _adminUser.getGroupId(),
					new String[] {RoleConstants.GUEST}
				).allowAddDataRecordCollection(
				).build();

		_deDataRecordCollectionService.execute(
			deDataRecordCollectionSavePermissionsRequest);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testAddDataRecordCollectionPermissionWithNoPermission()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addGroupUser(group, RoleConstants.GUEST);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		DEDataRecordCollectionSavePermissionsRequest
			deDataRecordCollectionSavePermissionsRequest =
				DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
					TestPropsValues.getCompanyId(), user.getGroupId(),
					new String[] {RoleConstants.ORGANIZATION_USER}
				).allowAddDataRecordCollection(
				).build();

		try {
			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSavePermissionsRequest);
		}
		finally {
			DEDataEngineTestUtil.deleteDEDataRecordCollectionPermissions(
				TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
				RoleConstants.ORGANIZATION_USER,
				_deDataRecordCollectionService);
		}
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testDefinePermissionsByGuestUser() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addGroupUser(group, RoleConstants.GUEST);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		DEDataRecordCollectionSavePermissionsRequest
			deDataRecordCollectionSavePermissionsRequest =
				DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
					TestPropsValues.getCompanyId(), user.getGroupId(),
					new String[] {RoleConstants.ORGANIZATION_USER}
				).allowDefinePermissions(
				).build();

		_deDataRecordCollectionService.execute(
			deDataRecordCollectionSavePermissionsRequest);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testDefinePermissionsBySiteMember() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_siteMember));

		DEDataRecordCollectionSavePermissionsRequest
			deDataRecordCollectionSavePermissionsRequest =
				DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
					TestPropsValues.getCompanyId(), _siteMember.getGroupId(),
					new String[] {RoleConstants.ORGANIZATION_USER}
				).allowDefinePermissions(
				).build();

		_deDataRecordCollectionService.execute(
			deDataRecordCollectionSavePermissionsRequest);
	}

	@Test(expected = DEDataRecordCollectionException.class)
	public void testDefinePermissionsToGuestUser() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		DEDataRecordCollectionSavePermissionsRequest
			deDataRecordCollectionSavePermissionsRequest =
				DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
					TestPropsValues.getCompanyId(), _adminUser.getGroupId(),
					new String[] {RoleConstants.GUEST}
				).allowDefinePermissions(
				).build();

		_deDataRecordCollectionService.execute(
			deDataRecordCollectionSavePermissionsRequest);
	}

	@Test
	public void testDefinePermissionToAllowAddDataRecordCollection()
		throws Exception {

		Role role1 = RoleTestUtil.addRole(
			"Test Role 1", RoleConstants.TYPE_REGULAR);

		Group group1 = GroupTestUtil.addGroup();

		User user1 = UserTestUtil.addGroupUser(group1, "Test Role 1");

		Role role2 = RoleTestUtil.addRole(
			"Test Role 2", RoleConstants.TYPE_REGULAR);

		Group group2 = GroupTestUtil.addGroup();

		User user2 = UserTestUtil.addGroupUser(group2, "Test Role 2");

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(_adminUser));

			DEDataRecordCollectionSavePermissionsRequest
				deDataRecordCollectionSavePermissionsRequest =
					DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role1.getName()}
					).allowDefinePermissions(
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSavePermissionsRequest);

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user1));

			DEDataRecordCollectionSavePermissionsRequest
				deDataRecordCollectionSavePermissionsRequest2 =
					DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), group1.getGroupId(),
						new String[] {role2.getName()}
					).allowAddDataRecordCollection(
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSavePermissionsRequest2);

			DEDataDefinition deDataDefinition =
				DEDataEngineTestUtil.insertDEDataDefinition(
					_adminUser, _group, _deDataDefinitionService);

			DEDataRecordCollection deDataRecordCollection =
				DEDataEngineTestUtil.insertDEDataRecordCollection(
					user2, group2, deDataDefinition,
					_deDataRecordCollectionService);

			Assert.assertTrue(
				deDataRecordCollection.getDEDataRecordCollectionId() > 0);
		}
		finally {
			DEDataEngineTestUtil.deleteDEDataRecordCollectionPermissions(
				TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
				role1.getName(), _deDataRecordCollectionService);

			DEDataEngineTestUtil.deleteDEDataRecordCollectionPermissions(
				TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
				role2.getName(), _deDataRecordCollectionService);
		}
	}

	@Test
	public void testDefinePermissionToAllowUpdateDataRecordCollection()
		throws Exception {

		Role role1 = RoleTestUtil.addRole(
			"Test Role 1", RoleConstants.TYPE_REGULAR);

		Group group1 = GroupTestUtil.addGroup();

		User user1 = UserTestUtil.addGroupUser(group1, "Test Role 1");

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(_adminUser));

			DEDataRecordCollectionSavePermissionsRequest
				deDataRecordCollectionSavePermissionsRequest =
					DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role1.getName()}
					).allowDefinePermissions(
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSavePermissionsRequest);

			DEDataRecordCollection deDataRecordCollection =
				DEDataEngineTestUtil.insertDEDataRecordCollection(
					_adminUser, _group, _deDataDefinitionService,
					_deDataRecordCollectionService);

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user1));

			Group group2 = GroupTestUtil.addGroup();

			User user2 = UserTestUtil.addGroupUser(
				group2, RoleConstants.ORGANIZATION_USER);

			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest2 =
					DEDataRecordCollectionRequestBuilder.
					saveModelPermissionsBuilder(
						TestPropsValues.getCompanyId(), group2.getGroupId(),
						user1.getUserId(), group1.getGroupId(),
						deDataRecordCollection.getDEDataRecordCollectionId(),
						new String[] {role1.getName()}
					).allowUpdate(
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest2);

			DEDataRecordCollection deDataRecordCollectionAfterUpdate =
				DEDataEngineTestUtil.updateDEDataRecordCollection(
					user2, user2.getGroup(), deDataRecordCollection,
					_deDataRecordCollectionService);

			Assert.assertEquals(
				deDataRecordCollection.getDEDataRecordCollectionId(),
				deDataRecordCollectionAfterUpdate.
					getDEDataRecordCollectionId());
		}
		finally {
			DEDataEngineTestUtil.deleteDEDataRecordCollectionPermissions(
				TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
				role1.getName(), _deDataRecordCollectionService);

			DEDataEngineTestUtil.deleteDEDataRecordCollectionPermissions(
				TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
				RoleConstants.ORGANIZATION_USER,
				_deDataRecordCollectionService);
		}
	}

	@Test(expected = DEDataRecordCollectionException.PrincipalException.class)
	public void testGrantUpdatePermissionToGuestUser() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addGroupUser(group, RoleConstants.GUEST);

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		DEDataRecordCollectionSaveModelPermissionsRequest
			deDataRecordCollectionSaveModelPermissionsRequest =
				DEDataRecordCollectionRequestBuilder.
					saveModelPermissionsBuilder(
						TestPropsValues.getCompanyId(), user.getGroupId(),
						_adminUser.getUserId(), _group.getGroupId(),
						deDataRecordCollection.getDEDataRecordCollectionId(),
						new String[] {RoleConstants.GUEST}
					).allowUpdate(
					).build();

		_deDataRecordCollectionService.execute(
			deDataRecordCollectionSaveModelPermissionsRequest);
	}

	@Test
	public void testInsert() throws Exception {
		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_siteMember, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		Assert.assertTrue(
			deDataRecordCollection.getDEDataRecordCollectionId() > 0);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testInsertByGuest() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		DEDataEngineTestUtil.insertDEDataRecordCollection(
			user, group, deDataDefinition, _deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.class)
	public void testInsertWithBadRequest() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_siteMember));

		DEDataRecordCollection deDataRecordCollection =
			new DEDataRecordCollection();

		deDataRecordCollection.addName(LocaleUtil.US, "Data record list");
		deDataRecordCollection.addDescription(
			LocaleUtil.BRAZIL, "Lista de registro de dados");

		DEDataRecordCollectionSaveRequest deDataRecordCollectionSaveRequest =
				DEDataRecordCollectionRequestBuilder.saveBuilder(
					deDataRecordCollection
				).onBehalfOf(
					_siteMember.getUserId()
				).build();

		_deDataRecordCollectionService.execute(
			deDataRecordCollectionSaveRequest);
	}

	@Test(expected = DEDataRecordCollectionException.class)
	public void testInsertWithBadRequest2() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_siteMember));

		DEDataRecordCollection deDataRecordCollection =
			new DEDataRecordCollection();

		deDataRecordCollection.addName(LocaleUtil.US, "Data record list");
		deDataRecordCollection.addDescription(
			LocaleUtil.BRAZIL, "Lista de registro de dados");

		DEDataRecordCollectionSaveRequest deDataRecordCollectionSaveRequest =
				DEDataRecordCollectionRequestBuilder.saveBuilder(
					deDataRecordCollection
				).inGroup(
					_group.getGroupId()
				).build();

		_deDataRecordCollectionService.execute(
			deDataRecordCollectionSaveRequest);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testInsertWithNoPermission() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addUser();

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		DEDataEngineTestUtil.insertDEDataRecordCollection(
			user, group, deDataDefinition, _deDataRecordCollectionService);
	}

	@Test
	public void testRevokeAddDataRecordCollectionPermission() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		RoleLocalServiceUtil.addGroupRole(group.getGroupId(), role);

		User user = UserTestUtil.addGroupUser(group, role.getName());

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		/*PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		DEDataRecordCollectionSavePermissionsRequest
			deDataRecordCollectionSavePermissionsRequest =
				DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
					TestPropsValues.getCompanyId(), _group.getGroupId(),
					new String[] {role.getName()}
				).allowAddDataRecordCollection(
				).build();

		_deDataRecordCollectionService.execute(
			deDataRecordCollectionSavePermissionsRequest);*/

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				user, group, deDataDefinition, _deDataRecordCollectionService);

		Assert.assertTrue(
			deDataRecordCollection.getDEDataRecordCollectionId() > 0);

		DEDataEngineTestUtil.deleteDEDataRecordCollectionPermissions(
			TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
			role.getName(), _deDataRecordCollectionService);

		deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				user, group, deDataDefinition, _deDataRecordCollectionService);
	}

	@Test
	public void testRevokeDefinePermissions() throws Exception {
		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		Group group = GroupTestUtil.addGroup();

		Role role1 = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		RoleLocalServiceUtil.addGroupRole(
			group.getGroupId(), role1.getRoleId());

		User user1 = UserTestUtil.addGroupUser(group, role1.getName());

		Role role2 = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		RoleLocalServiceUtil.addGroupRole(
			group.getGroupId(), role2.getRoleId());

		User user2 = UserTestUtil.addGroupUser(group, role2.getName());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

			DEDataRecordCollectionSavePermissionsRequest
				deDataRecordCollectionSavePermissionsRequest =
					DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
						TestPropsValues.getCompanyId(), _group.getGroupId(),
						new String[] {role1.getName()}
					).allowDefinePermissions(
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSavePermissionsRequest);

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user1));

			DEDataRecordCollectionSaveModelPermissionsRequest
			deDataRecordCollectionSaveModelPermissionsRequest =
				DEDataRecordCollectionRequestBuilder.
				saveModelPermissionsBuilder(
					TestPropsValues.getCompanyId(), user2.getGroupId(),
					user1.getUserId(), group.getGroupId(),
					deDataRecordCollection.getDEDataRecordCollectionId(),
					new String[] {role2.getName()}
				).allowUpdate(
				).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest);

			DEDataRecordCollection deDataRecordCollectionAfterUpdate =
				DEDataEngineTestUtil.updateDEDataRecordCollection(
					user2, group, deDataRecordCollection,
					_deDataRecordCollectionService);

			Assert.assertEquals(
				deDataRecordCollection.getDEDataRecordCollectionId(),
				deDataRecordCollectionAfterUpdate.
					getDEDataRecordCollectionId());

			DEDataEngineTestUtil.deleteDEDataRecordCollectionPermissions(
				TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
				role1.getName(), _deDataRecordCollectionService);

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user1));

			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest2 =
					DEDataRecordCollectionRequestBuilder.
					saveModelPermissionsBuilder(
						TestPropsValues.getCompanyId(), group.getGroupId(),
						user1.getUserId(), group.getGroupId(),
						deDataRecordCollection.getDEDataRecordCollectionId(),
						new String[] {role2.getName()}
					).allowUpdate(
					).build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest2);
	}

	@Test
	public void testRevokeUpdateDataRecordCollectionPermission()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		RoleLocalServiceUtil.addGroupRole(group.getGroupId(), role.getRoleId());

		User user = UserTestUtil.addUser(group.getGroupId());

		//User user = UserTestUtil.addGroupUser(
			//group, RoleConstants.ORGANIZATION_USER);

		String[] roleNames = {RoleConstants.ORGANIZATION_USER};

		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		DEDataRecordCollectionSaveModelPermissionsRequest
		deDataRecordCollectionSaveModelPermissionsRequest2 =
			DEDataRecordCollectionRequestBuilder.
			saveModelPermissionsBuilder(
				TestPropsValues.getCompanyId(), group.getGroupId(),
				_adminUser.getUserId(), _group.getGroupId(),
				deDataRecordCollection.getDEDataRecordCollectionId(),
				new String[] {role.getName()}
			).allowUpdate(
			).build();

		_deDataRecordCollectionService.execute(
			deDataRecordCollectionSaveModelPermissionsRequest2);

		DEDataEngineTestUtil.updateDEDataRecordCollection(
			user, group, deDataRecordCollection,
			_deDataRecordCollectionService);

		DEDataEngineTestUtil.deleteDEDataRecordCollectionModelPermissions(
			TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
			deDataRecordCollection.getDEDataRecordCollectionId(), roleNames,
			_deDataRecordCollectionService);

		DEDataEngineTestUtil.updateDEDataRecordCollection(
			user, group, deDataRecordCollection,
			_deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.class)
	public void testSaveWithBadRequest2() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_siteMember));

		DEDataRecordCollection deDataRecordCollection =
			new DEDataRecordCollection();

		deDataRecordCollection.addName(LocaleUtil.US, "Data record list");
		deDataRecordCollection.addDescription(
			LocaleUtil.BRAZIL, "Lista de registro de dados");

		DEDataRecordCollectionSaveRequest deDataRecordCollectionSaveRequest =
				DEDataRecordCollectionRequestBuilder.saveBuilder(
					deDataRecordCollection
				).onBehalfOf(
					_siteMember.getUserId()
				).build();

		_deDataRecordCollectionService.execute(
			deDataRecordCollectionSaveRequest);
	}

	@Test(expected = DEDataRecordCollectionException.class)
	public void testSaveWithNoDataDefinition() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_siteMember));

		DEDataRecordCollection deDataRecordCollection =
			new DEDataRecordCollection();

		deDataRecordCollection.addName(LocaleUtil.US, "Data record list");
		deDataRecordCollection.addDescription(
			LocaleUtil.BRAZIL, "Lista de registro de dados");

		DEDataRecordCollectionSaveRequest deDataRecordCollectionSaveRequest =
				DEDataRecordCollectionRequestBuilder.saveBuilder(
					deDataRecordCollection
				).onBehalfOf(
					_siteMember.getUserId()
				).inGroup(
					_group.getGroupId()
				).build();

		_deDataRecordCollectionService.execute(
			deDataRecordCollectionSaveRequest);
	}

	@Test(expected = DEDataRecordCollectionException.class)
	public void testSaveWithNoSuchGroup() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_siteMember));

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		DEDataRecordCollection deDataRecordCollection =
			new DEDataRecordCollection();

		deDataRecordCollection.addName(LocaleUtil.US, "Data record list");
		deDataRecordCollection.addDescription(
			LocaleUtil.BRAZIL, "Lista de registro de dados");
		deDataRecordCollection.setDEDataDefinition(deDataDefinition);

		DEDataRecordCollectionSaveRequest deDataRecordCollectionSaveRequest =
				DEDataRecordCollectionRequestBuilder.saveBuilder(
					deDataRecordCollection
				).onBehalfOf(
					_siteMember.getUserId()
				).inGroup(
					1
				).build();

		_deDataRecordCollectionService.execute(
			deDataRecordCollectionSaveRequest);
	}

	@Test(expected = DEDataRecordCollectionException.class)
	public void testSaveWithNoSuchUser() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_siteMember));

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		DEDataRecordCollection deDataRecordCollection =
			new DEDataRecordCollection();

		deDataRecordCollection.addName(LocaleUtil.US, "Data record list");
		deDataRecordCollection.addDescription(
			LocaleUtil.BRAZIL, "Lista de registro de dados");
		deDataRecordCollection.setDEDataDefinition(deDataDefinition);

		DEDataRecordCollectionSaveRequest deDataRecordCollectionSaveRequest =
				DEDataRecordCollectionRequestBuilder.saveBuilder(
					deDataRecordCollection
				).onBehalfOf(
					1
				).inGroup(
					_group.getGroupId()
				).build();

		_deDataRecordCollectionService.execute(
			deDataRecordCollectionSaveRequest);
	}

	@Test
	public void testUpdate() throws Exception {
		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_siteMember, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		deDataRecordCollection.addDescription(
			LocaleUtil.US, "Data record list 2");

		DEDataRecordCollection deDataRecordCollectionAfterUpdate =
			DEDataEngineTestUtil.updateDEDataRecordCollection(
				_siteMember, _group, deDataRecordCollection,
				_deDataRecordCollectionService);

		Assert.assertEquals(
			deDataRecordCollection.getDEDataRecordCollectionId(),
			deDataRecordCollectionAfterUpdate.getDEDataRecordCollectionId());
	}

	@Test
	public void testUpdateAfterUpdate() throws Exception {
		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_siteMember, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		deDataRecordCollection.addDescription(
			LocaleUtil.US, "Data record list 2");

		DEDataEngineTestUtil.updateDEDataRecordCollection(
			_siteMember, _group, deDataRecordCollection,
			_deDataRecordCollectionService);

		deDataRecordCollection.addDescription(
			LocaleUtil.US, "Data record list 3");

		DEDataRecordCollection deDataRecordCollectionAfterUpdate =
			DEDataEngineTestUtil.updateDEDataRecordCollection(
				_siteMember, _group, deDataRecordCollection,
				_deDataRecordCollectionService);

		Assert.assertEquals(
			deDataRecordCollection.getDEDataRecordCollectionId(),
			deDataRecordCollectionAfterUpdate.getDEDataRecordCollectionId());
	}

	protected void setUpPermissionThreadLocal() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
	}

	protected void setUpServiceContext() throws PortalException {
//		ModelPermissions modelPermissions = new ModelPermissions();
//
//		ServiceContext serviceContext = new ServiceContext();
//
//		String[] siteMemberDefaultPermissions =
//			{ActionKeys.VIEW, ActionKeys.UPDATE, ActionKeys.DELETE};
//
//		modelPermissions.addRolePermissions(
//			RoleConstants.GUEST, ActionKeys.VIEW);
//		modelPermissions.addRolePermissions(
//			RoleConstants.SITE_MEMBER, siteMemberDefaultPermissions);
//
//		serviceContext.setAddGroupPermissions(true);
//		serviceContext.setAddGuestPermissions(true);
//		serviceContext.setModelPermissions(modelPermissions);
//		serviceContext.setCompanyId(_group.getCompanyId());
//		serviceContext.setScopeGroupId(_group.getGroupId());
//		serviceContext.setUserId(_adminUser.getUserId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);
	}

	@DeleteAfterTestRun
	private User _adminUser;

	@Inject(type = DEDataDefinitionService.class)
	private DEDataDefinitionService _deDataDefinitionService;

	@Inject(type = DEDataRecordCollectionService.class)
	private DEDataRecordCollectionService _deDataRecordCollectionService;

	@DeleteAfterTestRun
	private Group _group;

	private PermissionChecker _originalPermissionChecker;

	@DeleteAfterTestRun
	private User _siteMember;

	@Inject(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}
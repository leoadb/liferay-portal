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
import com.liferay.data.engine.model.DEDataRecord;
import com.liferay.data.engine.model.DEDataRecordCollection;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.data.engine.service.DEDataRecordCollectionGetRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionRequestBuilder;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveModelPermissionsRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSavePermissionsRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
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
	}

	@Test
	public void testAddDataRecordCollectionPermission() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));

		Role role = RoleTestUtil.addRole("Teste", RoleConstants.TYPE_REGULAR);

		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addGroupUser(group, "Teste");

		DEDataRecordCollectionSavePermissionsRequest
			deDataRecordCollectionSavePermissionsRequest =
				DEDataRecordCollectionRequestBuilder.savePermissionsBuilder(
					TestPropsValues.getCompanyId(), _group.getGroupId(),
					role.getName()
				).allowAddDataRecordCollection(
				).build();

		try {
			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSavePermissionsRequest);

			DEDataDefinition deDataDefinition =
				DEDataEngineTestUtil.insertDEDataDefinition(
					_adminUser, group, _deDataDefinitionService);

			DEDataRecordCollection deDataRecordCollection =
				DEDataEngineTestUtil.insertDEDataRecordCollection(
					user, group, deDataDefinition,
					_deDataRecordCollectionService);

			Assert.assertTrue(
				deDataRecordCollection.getDEDataRecordCollectionId() > 0);
		}
		finally {
			DEDataEngineTestUtil.deleteDEDataRecordCollectionPermissions(
				TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
				role.getName(), _deDataRecordCollectionService);
		}
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
					RoleConstants.ORGANIZATION_USER
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

	@Test
	public void testDelete() throws Exception {
		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		DEDataRecordCollectionSaveModelPermissionsRequest.Builder builder1 =
			DEDataRecordCollectionRequestBuilder.saveModelPermissionsBuilder(
				TestPropsValues.getCompanyId(), _group.getGroupId(),
				deDataRecordCollection.getDEDataRecordCollectionId()
			).grantTo(
				_siteMember.getUserId()
			).inGroup(
				_group.getGroupId()
			).allowDelete();

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest =
					builder1.build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest);

			DEDataEngineTestUtil.deleteDEDataRecordCollection(
				_siteMember,
				deDataRecordCollection.getDEDataRecordCollectionId(),
				_deDataRecordCollectionService);
		}
		finally {
			DEDataEngineTestUtil.deleteDEDataRecordCollectionModelPermissions(
				TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
				deDataRecordCollection.getDEDataRecordCollectionId(),
				new String[] {RoleConstants.OWNER, RoleConstants.SITE_MEMBER},
				_deDataRecordCollectionService);
		}
	}

	@Test(
		expected =
			DEDataRecordCollectionException.NoSuchDataRecordCollection.class
	)
	public void testDeleteNoSuchDataRecordCollection() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_siteMember));

		DEDataEngineTestUtil.deleteDEDataRecordCollection(
			_siteMember, 1, _deDataRecordCollectionService);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testDeleteWithNoPermission() throws Exception {
		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		DEDataEngineTestUtil.deleteDEDataRecordCollection(
			user, deDataRecordCollection.getDEDataRecordCollectionId(),
			_deDataRecordCollectionService);
	}

	@Test
	public void testGetByGuestUser() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addGroupUser(group, RoleConstants.GUEST);

		DEDataRecordCollection expectedDEDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		DEDataRecordCollectionSaveModelPermissionsRequest.Builder builder =
			DEDataRecordCollectionRequestBuilder.saveModelPermissionsBuilder(
				TestPropsValues.getCompanyId(), _group.getGroupId(),
				expectedDEDataRecordCollection.getDEDataRecordCollectionId()
			).grantTo(
				user.getUserId()
			).inGroup(
				user.getGroupId()
			).allowView();

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest =
					builder.build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest);

			DEDataRecordCollection deDataRecordCollection =
				DEDataEngineTestUtil.getDEDataRecordCollection(
					user,
					expectedDEDataRecordCollection.
						getDEDataRecordCollectionId(),
					_deDataRecordCollectionService);

			Assert.assertEquals(
				expectedDEDataRecordCollection, deDataRecordCollection);
		}
		finally {
			DEDataEngineTestUtil.deleteDEDataRecordCollectionModelPermissions(
				TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
				expectedDEDataRecordCollection.getDEDataRecordCollectionId(),
				new String[] {RoleConstants.OWNER, RoleConstants.GUEST},
				_deDataRecordCollectionService);
		}
	}

	@Test
	public void testGetBySiteMember() throws Exception {
		DEDataRecordCollection expectedDEDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		DEDataRecordCollectionSaveModelPermissionsRequest.Builder builder =
			DEDataRecordCollectionRequestBuilder.saveModelPermissionsBuilder(
				TestPropsValues.getCompanyId(), _group.getGroupId(),
				expectedDEDataRecordCollection.getDEDataRecordCollectionId()
			).grantTo(
				_siteMember.getUserId()
			).inGroup(
				_group.getGroupId()
			).allowView();

		try {
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest =
					builder.build();

			_deDataRecordCollectionService.execute(
				deDataRecordCollectionSaveModelPermissionsRequest);

			DEDataRecordCollection deDataRecordCollection =
				DEDataEngineTestUtil.getDEDataRecordCollection(
					_siteMember,
					expectedDEDataRecordCollection.
						getDEDataRecordCollectionId(),
					_deDataRecordCollectionService);

			Assert.assertEquals(
				expectedDEDataRecordCollection, deDataRecordCollection);
		}
		finally {
			DEDataEngineTestUtil.deleteDEDataRecordCollectionModelPermissions(
				TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
				expectedDEDataRecordCollection.getDEDataRecordCollectionId(),
				new String[] {RoleConstants.OWNER, RoleConstants.SITE_MEMBER},
				_deDataRecordCollectionService);
		}
	}

	@Test(
		expected =
			DEDataRecordCollectionException.NoSuchDataRecordCollection.class
	)
	public void testGetNoSuchDataRecordCollection() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_siteMember));

		DEDataRecordCollectionGetRequest deDataRecordCollectionGetRequest =
			DEDataRecordCollectionRequestBuilder.getBuilder(
			).byId(
				1
			).build();

		_deDataRecordCollectionService.execute(
			deDataRecordCollectionGetRequest);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testGetWithNoPermission() throws Exception {
		DEDataRecordCollection deDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		DEDataEngineTestUtil.getDEDataRecordCollection(
			user, deDataRecordCollection.getDEDataRecordCollectionId(),
			_deDataRecordCollectionService);
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

	@Test
	public void testInsertRecord() throws Exception {
		DEDataRecord deDataRecord = DEDataEngineTestUtil.insertDEDataRecord(
			_siteMember, _group, _deDataDefinitionService,
			_deDataRecordCollectionService);

		Assert.assertTrue(deDataRecord.getDEDataRecordId() > 0);
	}

	@Test(expected = DEDataRecordCollectionException.MustHavePermission.class)
	public void testInsertWithNoPermission() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		DEDataDefinition deDataDefinition =
			DEDataEngineTestUtil.insertDEDataDefinition(
				_adminUser, _group, _deDataDefinitionService);

		DEDataEngineTestUtil.insertDEDataRecordCollection(
			user, group, deDataDefinition, _deDataRecordCollectionService);
	}

	@Test
	public void testUpdate() throws Exception {
		DEDataRecordCollection expectedDEDataRecordCollection =
			DEDataEngineTestUtil.insertDEDataRecordCollection(
				_adminUser, _group, _deDataDefinitionService,
				_deDataRecordCollectionService);

		DEDataRecordCollectionSaveModelPermissionsRequest.Builder builder =
			DEDataRecordCollectionRequestBuilder.saveModelPermissionsBuilder(
				TestPropsValues.getCompanyId(), _group.getGroupId(),
				expectedDEDataRecordCollection.getDEDataRecordCollectionId()
			).grantTo(
				_siteMember.getUserId()
			).inGroup(
				_group.getGroupId()
			).allowUpdate();

		DEDataRecordCollectionSaveModelPermissionsRequest
			deDataRecordCollectionSaveModelPermissionsRequest = builder.build();

		_deDataRecordCollectionService.execute(
			deDataRecordCollectionSaveModelPermissionsRequest);

		try {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group, _siteMember.getUserId());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(_siteMember));

			expectedDEDataRecordCollection.addName(
				LocaleUtil.BRAZIL, "Coleção de Dados BR");

			DEDataRecordCollectionSaveRequest
				deDataRecordCollectionSaveRequest =
					DEDataRecordCollectionRequestBuilder.saveBuilder(
						expectedDEDataRecordCollection
					).onBehalfOf(
						_siteMember.getUserId()
					).inGroup(
						_group.getGroupId()
					).build();

			DEDataRecordCollectionSaveResponse
				deDataRecordCollectionSaveResponse =
					_deDataRecordCollectionService.execute(
						deDataRecordCollectionSaveRequest);

			Assert.assertEquals(
				expectedDEDataRecordCollection,
				deDataRecordCollectionSaveResponse.getDEDataRecordCollection());
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();

			DEDataEngineTestUtil.deleteDEDataRecordCollectionModelPermissions(
				TestPropsValues.getCompanyId(), _adminUser, _group.getGroupId(),
				expectedDEDataRecordCollection.getDEDataRecordCollectionId(),
				new String[] {RoleConstants.OWNER, RoleConstants.SITE_MEMBER},
				_deDataRecordCollectionService);
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
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

package com.liferay.data.engine.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.rest.client.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.client.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.client.dto.v1_0.DataDefinitionPermission;
import com.liferay.data.engine.rest.resource.v1_0.test.util.DataDefinitionTestUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jeyvison Nascimento
 */
@RunWith(Arquillian.class)
public class DataDefinitionResourceTest
	extends BaseDataDefinitionResourceTestCase {

	@Override
	public void testPostDataDefinitionDataDefinitionPermission()
		throws Exception {

		super.testPostDataDefinitionDataDefinitionPermission();

		DDMStructure ddmStructure = DataDefinitionTestUtil.addDDMStructure(
			testGroup);

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		invokePostDataDefinitionDataDefinitionPermission(
			ddmStructure.getStructureId(), _OPERATION_SAVE_PERMISSION,
			new DataDefinitionPermission() {
				{
					view = true;
					roleNames = new String[] {role.getName()};
				}
			});
	}

	@Override
	public void testPostSiteDataDefinitionPermission() throws Exception {
		super.testPostSiteDataDefinitionPermission();

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		invokePostSiteDataDefinitionPermission(
			testGroup.getGroupId(), _OPERATION_SAVE_PERMISSION,
			new DataDefinitionPermission() {
				{
					addDataDefinition = true;
					roleNames = new String[] {role.getName()};
				}
			});
	}

	@Test
	public void testPostSiteDataDefinitionWithDataDefinitionField()
		throws Exception {

		DataDefinition randomDataDefinition = randomDataDefinitionWithField();

		assertValidDataDefinitionField(randomDataDefinition);

		DataDefinition postDataDefinition =
			testPostSiteDataDefinition_addDataDefinition(randomDataDefinition);

		assertEquals(randomDataDefinition, postDataDefinition);
		assertValid(postDataDefinition);
	}

	protected boolean assertValidDataDefinitionField(
		DataDefinition dataDefinition) {

		boolean valid = true;

		if ((dataDefinition != null) &&
			(dataDefinition.getDataDefinitionFields() != null)) {

			for (DataDefinitionField dataDefinitionField :
					dataDefinition.getDataDefinitionFields()) {

				if (dataDefinitionField.getFieldType() == null) {
					valid = false;
				}

				if (dataDefinitionField.getLabel() == null) {
					valid = false;
				}

				if (dataDefinitionField.getName() == null) {
					valid = false;
				}

				if (dataDefinitionField.getTip() == null) {
					valid = false;
				}
			}
		}
		else {
			valid = false;
		}

		return valid;
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name", "userId"};
	}

	@Override
	protected DataDefinition randomDataDefinition() throws Exception {
		return new DataDefinition() {
			{
				name = new HashMap<String, Object>() {
					{
						put("en_US", RandomTestUtil.randomString());
					}
				};
				siteId = testGroup.getGroupId();
				userId = TestPropsValues.getUserId();
			}
		};
	}

	protected DataDefinition randomDataDefinitionWithField() throws Exception {
		return new DataDefinition() {
			{
				dataDefinitionFields = new DataDefinitionField[] {
					new DataDefinitionField() {
						{
							fieldType = "fieldType";
							label = new HashMap<String, Object>() {
								{
									put("label", RandomTestUtil.randomString());
								}
							};
							name = RandomTestUtil.randomString();
							tip = new HashMap<String, Object>() {
								{
									put("tip", RandomTestUtil.randomString());
								}
							};
						}
					}
				};
				name = new HashMap<String, Object>() {
					{
						put("en_US", RandomTestUtil.randomString());
					}
				};
				siteId = testGroup.getGroupId();
				userId = TestPropsValues.getUserId();
			}
		};
	}

	private static final String _OPERATION_SAVE_PERMISSION = "save";

}
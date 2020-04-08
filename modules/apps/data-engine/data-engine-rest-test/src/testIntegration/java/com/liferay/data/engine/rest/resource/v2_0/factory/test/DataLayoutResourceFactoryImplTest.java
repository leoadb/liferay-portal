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

package com.liferay.data.engine.rest.resource.v2_0.factory.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.exception.DataLayoutValidationException;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutColumn;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutPage;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutRow;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.data.engine.rest.resource.v2_0.DataLayoutResource;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
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
public class DataLayoutResourceFactoryImplTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();
		_group = GroupTestUtil.addGroup();
	}

	@Test(
		expected = DataLayoutValidationException.MustNotDuplicateFieldName.class
	)
	public void testDataLayoutWithDuplicatedFieldNames() throws Exception {
		DataDefinition dataDefinition = new DataDefinition() {
			{
				availableLanguageIds = new String[] {"en_US", "pt_BR"};
				dataDefinitionFields = new DataDefinitionField[] {
					new DataDefinitionField() {
						{
							fieldType = "text";
							label = HashMapBuilder.<String, Object>put(
								"en_US", RandomTestUtil.randomString()
							).put(
								"pt_BR", RandomTestUtil.randomString()
							).build();
							name = "text1";
						}
					},
					new DataDefinitionField() {
						{
							fieldType = "text";
							label = HashMapBuilder.<String, Object>put(
								"en_US", RandomTestUtil.randomString()
							).put(
								"pt_BR", RandomTestUtil.randomString()
							).build();
							name = "text2";
						}
					}
				};
				dataDefinitionKey = RandomTestUtil.randomString();
				defaultLanguageId = "en_US";
				name = HashMapBuilder.<String, Object>put(
					"en_US", RandomTestUtil.randomString()
				).build();
			}
		};

		DataDefinitionResource dataDefinitionResource =
			DataDefinitionResource.builder(
			).checkPermissions(
				false
			).user(
				_user
			).build();

		dataDefinition =
			dataDefinitionResource.postSiteDataDefinitionByContentType(
				_group.getGroupId(), "app-builder", dataDefinition);

		DataLayout dataLayout = new DataLayout() {
			{
				dataLayoutKey = RandomTestUtil.randomString();
				paginationMode = "wizard";
			}
		};

		dataLayout.setDataDefinitionId(dataDefinition.getId());
		dataLayout.setDataLayoutPages(
			new DataLayoutPage[] {
				new DataLayoutPage() {
					{
						dataLayoutRows = new DataLayoutRow[] {
							new DataLayoutRow() {
								{
									dataLayoutColumns = new DataLayoutColumn[] {
										new DataLayoutColumn() {
											{
												columnSize = 12;
												fieldNames = new String[] {
													"text1", "text2", "text1"
												};
											}
										}
									};
								}
							}
						};
						description = HashMapBuilder.<String, Object>put(
							"en_US", "Page Description"
						).build();
						title = HashMapBuilder.<String, Object>put(
							"en_US", "Page Title"
						).build();
					}
				}
			});
		dataLayout.setName(
			HashMapBuilder.<String, Object>put(
				"en_US", RandomTestUtil.randomString()
			).build());

		DataLayoutResource dataLayoutResource = DataLayoutResource.builder(
		).checkPermissions(
			false
		).user(
			_user
		).build();

		try {
			dataLayoutResource.postDataDefinitionDataLayout(
				dataDefinition.getId(), dataLayout);

			Assert.fail("An exception must be thrown");
		}
		finally {
			dataDefinitionResource.deleteDataDefinition(dataDefinition.getId());
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user;

}
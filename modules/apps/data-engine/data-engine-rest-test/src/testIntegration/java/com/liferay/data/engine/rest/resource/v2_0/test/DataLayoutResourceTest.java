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

package com.liferay.data.engine.rest.resource.v2_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.rest.client.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.client.dto.v2_0.DataDefinitionField;
import com.liferay.data.engine.rest.client.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.client.dto.v2_0.DataLayoutColumn;
import com.liferay.data.engine.rest.client.dto.v2_0.DataLayoutPage;
import com.liferay.data.engine.rest.client.dto.v2_0.DataLayoutRow;
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.pagination.Pagination;
import com.liferay.data.engine.rest.client.problem.Problem;
import com.liferay.data.engine.rest.client.resource.v2_0.DataDefinitionResource;
import com.liferay.data.engine.rest.resource.v2_0.test.util.DataDefinitionTestUtil;
import com.liferay.data.engine.rest.resource.v2_0.test.util.DataLayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcelo Mello
 */
@RunWith(Arquillian.class)
public class DataLayoutResourceTest extends BaseDataLayoutResourceTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_dataDefinition = DataDefinitionTestUtil.addDataDefinition(
			testGroup.getGroupId());
		_irrelevantDataDefinition = DataDefinitionTestUtil.addDataDefinition(
			irrelevantGroup.getGroupId());
	}

	@Override
	@Test
	public void testGetDataDefinitionDataLayoutsPage() throws Exception {
		super.testGetDataDefinitionDataLayoutsPage();

		Page<DataLayout> page =
			dataLayoutResource.getDataDefinitionDataLayoutsPage(
				testGetDataDefinitionDataLayoutsPage_getDataDefinitionId(),
				"layout", Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		_testGetDataDefinitionDataLayoutsPage("FORM", "FoRmSLaYoUt");
		_testGetDataDefinitionDataLayoutsPage(
			"abcdefghijklmnopqrstuvwxyz0123456789",
			"abcdefghijklmnopqrstuvwxyz0123456789");
		_testGetDataDefinitionDataLayoutsPage("form layout", "form layout");
		_testGetDataDefinitionDataLayoutsPage("layo", "form layout");
	}

	@Override
	@Test
	public void testGraphQLGetDataLayout() throws Exception {
		DataLayout dataLayout = testGraphQLDataLayout_addDataLayout();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"dataLayout",
				HashMapBuilder.<String, Object>put(
					"dataLayoutId", dataLayout.getId()
				).build(),
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		Assert.assertEquals(
			GetterUtil.getLong(dataLayout.getDataDefinitionId()),
			GetterUtil.getLong(
				JSONUtil.getValue(
					jsonObject, "JSONObject/data", "JSONObject/dataLayout",
					"Object/dataDefinitionId")));
		Assert.assertEquals(
			MapUtil.getString(dataLayout.getName(), "en_US"),
			JSONUtil.getValue(
				jsonObject, "JSONObject/data", "JSONObject/dataLayout",
				"JSONObject/name", "Object/en_US"));
	}

	@Override
	@Test
	public void testGraphQLGetSiteDataLayoutByContentTypeByDataLayoutKey()
		throws Exception {

		DataLayout dataLayout = testGraphQLDataLayout_addDataLayout();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"dataLayoutByContentTypeByDataLayoutKey",
				HashMapBuilder.<String, Object>put(
					"contentType",
					StringBundler.concat(
						StringPool.QUOTE, "app-builder", StringPool.QUOTE)
				).put(
					"dataLayoutKey",
					StringBundler.concat(
						StringPool.QUOTE, dataLayout.getDataLayoutKey(),
						StringPool.QUOTE)
				).put(
					"siteKey",
					StringBundler.concat(
						StringPool.QUOTE, dataLayout.getSiteId(),
						StringPool.QUOTE)
				).build(),
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		Assert.assertEquals(
			GetterUtil.getLong(dataLayout.getDataDefinitionId()),
			GetterUtil.getLong(
				JSONUtil.getValue(
					jsonObject, "JSONObject/data",
					"JSONObject/dataLayoutByContentTypeByDataLayoutKey",
					"Object/dataDefinitionId")));
		Assert.assertEquals(
			MapUtil.getString(dataLayout.getName(), "en_US"),
			JSONUtil.getValue(
				jsonObject, "JSONObject/data",
				"JSONObject/dataLayoutByContentTypeByDataLayoutKey",
				"JSONObject/name", "Object/en_US"));
	}

	@Override
	@Test
	public void testPostDataDefinitionDataLayout() throws Exception {
		super.testPostDataDefinitionDataLayout();

		// Multiple data layouts with the same data definition

		for (int i = 0; i < 3; i++) {
			DataLayout randomDataLayout = randomDataLayout();

			DataLayout postDataLayout =
				testPostDataDefinitionDataLayout_addDataLayout(
					randomDataLayout);

			assertEquals(randomDataLayout, postDataLayout);
			assertValid(postDataLayout);
		}

		_testPostDataDefinitionDataLayoutDuplicatedFieldName();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"dataDefinitionId", "name", "paginationMode"};
	}

	@Override
	protected DataLayout randomDataLayout() {
		return DataLayoutTestUtil.createDataLayout(
			_dataDefinition.getId(), RandomTestUtil.randomString(),
			testGroup.getGroupId());
	}

	@Override
	protected DataLayout randomIrrelevantDataLayout() throws Exception {
		DataLayout randomIrrelevantDataLayout =
			super.randomIrrelevantDataLayout();

		randomIrrelevantDataLayout.setDataDefinitionId(
			_irrelevantDataDefinition.getId());

		return randomIrrelevantDataLayout;
	}

	@Override
	protected DataLayout testDeleteDataLayout_addDataLayout() throws Exception {
		return dataLayoutResource.postDataDefinitionDataLayout(
			_dataDefinition.getId(), randomDataLayout());
	}

	@Override
	protected DataLayout testDeleteDataLayoutsDataDefinition_addDataLayout()
		throws Exception {

		return dataLayoutResource.postDataDefinitionDataLayout(
			_dataDefinition.getId(), randomDataLayout());
	}

	@Override
	protected Long testGetDataDefinitionDataLayoutsPage_getDataDefinitionId()
		throws Exception {

		return _dataDefinition.getId();
	}

	@Override
	protected DataLayout testGetDataLayout_addDataLayout() throws Exception {
		return dataLayoutResource.postDataDefinitionDataLayout(
			_dataDefinition.getId(), randomDataLayout());
	}

	@Override
	protected DataLayout
			testGetSiteDataLayoutByContentTypeByDataLayoutKey_addDataLayout()
		throws Exception {

		DataLayout dataLayout = dataLayoutResource.postDataDefinitionDataLayout(
			_dataDefinition.getId(), randomDataLayout());

		dataLayout.setContentType("app-builder");

		return dataLayout;
	}

	@Override
	protected DataLayout testGraphQLDataLayout_addDataLayout()
		throws Exception {

		return dataLayoutResource.postDataDefinitionDataLayout(
			_dataDefinition.getId(), randomDataLayout());
	}

	@Override
	protected DataLayout testPutDataLayout_addDataLayout() throws Exception {
		return dataLayoutResource.postDataDefinitionDataLayout(
			_dataDefinition.getId(), randomDataLayout());
	}

	private void _testGetDataDefinitionDataLayoutsPage(
			String keywords, String name)
		throws Exception {

		Long dataDefinitionId =
			testGetDataDefinitionDataLayoutsPage_getDataDefinitionId();

		DataLayout dataLayout =
			testGetDataDefinitionDataLayoutsPage_addDataLayout(
				dataDefinitionId,
				DataLayoutTestUtil.createDataLayout(
					_dataDefinition.getId(), name, testGroup.getGroupId()));

		Page<DataLayout> page =
			dataLayoutResource.getDataDefinitionDataLayoutsPage(
				dataDefinitionId, keywords, Pagination.of(1, 2), null);

		Assert.assertEquals(1, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataLayout), (List<DataLayout>)page.getItems());
		assertValid(page);

		dataLayoutResource.deleteDataLayout(dataLayout.getId());
	}

	private void _testPostDataDefinitionDataLayoutDuplicatedFieldName()
		throws Exception {

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
			).build();

		dataDefinition =
			dataDefinitionResource.postSiteDataDefinitionByContentType(
				testGroup.getGroupId(), "app-builder", dataDefinition);

		try {
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
										dataLayoutColumns =
											new DataLayoutColumn[] {
												new DataLayoutColumn() {
													{
														columnSize = 12;
														fieldNames =
															new String[] {
																"text1",
																"text2", "text1"
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

			dataLayoutResource.postDataDefinitionDataLayout(
				dataDefinition.getId(), dataLayout);

			Assert.fail("An exception must be thrown");
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("text1", problem.getDetail());
			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertEquals("MustNotDuplicateFieldName", problem.getType());
		}
		finally {
			dataDefinitionResource.deleteDataDefinition(dataDefinition.getId());
		}
	}

	private DataDefinition _dataDefinition;
	private DataDefinition _irrelevantDataDefinition;

}
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
import com.liferay.data.engine.rest.client.dto.v2_0.DataRecordCollection;
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.pagination.Pagination;
import com.liferay.data.engine.rest.resource.v2_0.test.util.DataDefinitionTestUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gabriel Albuquerque
 */
@RunWith(Arquillian.class)
public class DataRecordCollectionResourceTest
	extends BaseDataRecordCollectionResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_ddmStructure = DataDefinitionTestUtil.addDDMStructure(testGroup);
		_irrelevantDDMStructure = DataDefinitionTestUtil.addDDMStructure(
			irrelevantGroup);
	}

	@Override
	@Test
	public void testGetDataDefinitionDataRecordCollectionsPage()
		throws Exception {

		super.testGetDataDefinitionDataRecordCollectionsPage();

		_testGetDataDefinitionDataRecordCollectionsPage(
			"CoLLeCTion dEsCrIpTiOn", "COLLECTION", "name");
		_testGetDataDefinitionDataRecordCollectionsPage(
			"definition", "abcdefghijklmnopqrstuvwxyz0123456789",
			"abcdefghijklmnopqrstuvwxyz0123456789");
	}

	@Override
	@Test
	public void testGetDataRecordCollectionPermissionByCurrentUser()
		throws Exception {

		DataRecordCollection dataRecordCollection =
			testGetDataRecordCollectionPermission_addDataRecordCollection();

		assertHttpResponseStatusCode(
			200,
			dataRecordCollectionResource.
				getDataRecordCollectionPermissionByCurrentUserHttpResponse(
					dataRecordCollection.getId()));
	}

	@Override
	@Test
	public void testGraphQLGetDataDefinitionDataRecordCollection()
		throws Exception {

		DataRecordCollection dataRecordCollection =
			testGraphQLGetDataDefinitionDataRecordCollection_addDataRecordCollection();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"dataDefinitionDataRecordCollection",
				HashMapBuilder.<String, Object>put(
					"dataDefinitionId",
					dataRecordCollection.getDataDefinitionId()
				).build(),
				graphQLFields.toArray(new GraphQLField[0])));

		Assert.assertEquals(
			MapUtil.getString(dataRecordCollection.getName(), "en_US"),
			JSONUtil.getValue(
				JSONFactoryUtil.createJSONObject(
					invoke(graphQLField.toString())),
				"JSONObject/data",
				"JSONObject/dataDefinitionDataRecordCollection",
				"JSONObject/name", "Object/en_US"));
	}

	@Override
	@Test
	public void testGraphQLGetDataRecordCollection() throws Exception {
		DataRecordCollection dataRecordCollection =
			testGraphQLDataRecordCollection_addDataRecordCollection();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"dataRecordCollection",
				HashMapBuilder.<String, Object>put(
					"dataRecordCollectionId", dataRecordCollection.getId()
				).build(),
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		Assert.assertEquals(
			GetterUtil.getLong(dataRecordCollection.getDataDefinitionId()),
			GetterUtil.getLong(
				JSONUtil.getValue(
					jsonObject, "JSONObject/data",
					"JSONObject/dataRecordCollection",
					"Object/dataDefinitionId")));
		Assert.assertEquals(
			MapUtil.getString(dataRecordCollection.getName(), "en_US"),
			JSONUtil.getValue(
				jsonObject, "JSONObject/data",
				"JSONObject/dataRecordCollection", "JSONObject/name",
				"Object/en_US"));
	}

	@Override
	@Test
	public void testGraphQLGetSiteDataRecordCollectionByDataRecordCollectionKey()
		throws Exception {

		DataRecordCollection dataRecordCollection =
			testGraphQLDataRecordCollection_addDataRecordCollection();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"dataRecordCollectionByDataRecordCollectionKey",
				HashMapBuilder.<String, Object>put(
					"dataRecordCollectionKey",
					StringBundler.concat(
						StringPool.QUOTE,
						dataRecordCollection.getDataRecordCollectionKey(),
						StringPool.QUOTE)
				).put(
					"siteKey",
					StringBundler.concat(
						StringPool.QUOTE, dataRecordCollection.getSiteId(),
						StringPool.QUOTE)
				).build(),
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		Assert.assertEquals(
			GetterUtil.getLong(dataRecordCollection.getDataDefinitionId()),
			GetterUtil.getLong(
				JSONUtil.getValue(
					jsonObject, "JSONObject/data",
					"JSONObject/dataRecordCollectionByDataRecordCollectionKey",
					"Object/dataDefinitionId")));
		Assert.assertEquals(
			MapUtil.getString(dataRecordCollection.getName(), "en_US"),
			JSONUtil.getValue(
				jsonObject, "JSONObject/data",
				"JSONObject/dataRecordCollectionByDataRecordCollectionKey",
				"JSONObject/name", "Object/en_US"));
	}

	@Override
	@Test
	public void testPostDataDefinitionDataRecordCollection() throws Exception {
		super.testPostDataDefinitionDataRecordCollection();

		assertHttpResponseStatusCode(
			404,
			dataRecordCollectionResource.
				postDataDefinitionDataRecordCollectionHttpResponse(
					0L, randomDataRecordCollection()));
	}

	@Ignore
	@Override
	@Test
	public void testPutDataRecordCollectionPermission() throws Exception {
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"dataDefinitionId", "name"};
	}

	@Override
	protected DataRecordCollection randomDataRecordCollection() {
		return _createDataRecordCollection(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());
	}

	@Override
	protected DataRecordCollection randomIrrelevantDataRecordCollection()
		throws Exception {

		DataRecordCollection randomIrrelevantDataRecordCollection =
			super.randomIrrelevantDataRecordCollection();

		randomIrrelevantDataRecordCollection.setDataDefinitionId(
			_irrelevantDDMStructure.getStructureId());

		return randomIrrelevantDataRecordCollection;
	}

	@Override
	protected DataRecordCollection
			testDeleteDataRecordCollection_addDataRecordCollection()
		throws Exception {

		return dataRecordCollectionResource.
			postDataDefinitionDataRecordCollection(
				_ddmStructure.getStructureId(), randomDataRecordCollection());
	}

	@Override
	protected DataRecordCollection
			testGetDataDefinitionDataRecordCollection_addDataRecordCollection()
		throws Exception {

		return dataRecordCollectionResource.
			postDataDefinitionDataRecordCollection(
				_ddmStructure.getStructureId(),
				_randomDataRecordCollection(_ddmStructure.getStructureKey()));
	}

	@Override
	protected Long
			testGetDataDefinitionDataRecordCollectionsPage_getDataDefinitionId()
		throws Exception {

		return _ddmStructure.getStructureId();
	}

	@Override
	protected DataRecordCollection
			testGetDataRecordCollection_addDataRecordCollection()
		throws Exception {

		return dataRecordCollectionResource.
			postDataDefinitionDataRecordCollection(
				_ddmStructure.getStructureId(), randomDataRecordCollection());
	}

	protected DataRecordCollection
			testGetDataRecordCollectionPermission_addDataRecordCollection()
		throws Exception {

		return dataRecordCollectionResource.
			postDataDefinitionDataRecordCollection(
				_ddmStructure.getStructureId(), randomDataRecordCollection());
	}

	@Override
	protected DataRecordCollection
			testGetSiteDataRecordCollectionByDataRecordCollectionKey_addDataRecordCollection()
		throws Exception {

		return dataRecordCollectionResource.
			postDataDefinitionDataRecordCollection(
				_ddmStructure.getStructureId(), randomDataRecordCollection());
	}

	@Override
	protected DataRecordCollection
			testGraphQLDataRecordCollection_addDataRecordCollection()
		throws Exception {

		return dataRecordCollectionResource.
			postDataDefinitionDataRecordCollection(
				_ddmStructure.getStructureId(), randomDataRecordCollection());
	}

	protected DataRecordCollection
			testGraphQLGetDataDefinitionDataRecordCollection_addDataRecordCollection()
		throws Exception {

		return dataRecordCollectionResource.
			postDataDefinitionDataRecordCollection(
				_ddmStructure.getStructureId(),
				_randomDataRecordCollection(_ddmStructure.getStructureKey()));
	}

	@Override
	protected DataRecordCollection
			testPostDataDefinitionDataRecordCollection_addDataRecordCollection(
				DataRecordCollection dataRecordCollection)
		throws Exception {

		return dataRecordCollectionResource.
			postDataDefinitionDataRecordCollection(
				dataRecordCollection.getDataDefinitionId(),
				dataRecordCollection);
	}

	@Override
	protected DataRecordCollection
			testPutDataRecordCollection_addDataRecordCollection()
		throws Exception {

		return dataRecordCollectionResource.
			postDataDefinitionDataRecordCollection(
				_ddmStructure.getStructureId(), randomDataRecordCollection());
	}

	private DataRecordCollection _createDataRecordCollection(
		String description, String name) {

		return _createDataRecordCollection(
			RandomTestUtil.randomString(), description, name);
	}

	private DataRecordCollection _createDataRecordCollection(
		String dataRecordCollectionKey, String description, String name) {

		DataRecordCollection dataRecordCollection = new DataRecordCollection() {
			{
				dataDefinitionId = _ddmStructure.getStructureId();
				siteId = testGroup.getGroupId();
			}
		};

		dataRecordCollection.setDataRecordCollectionKey(
			dataRecordCollectionKey);
		dataRecordCollection.setDescription(
			HashMapBuilder.<String, Object>put(
				"en_US", description
			).build());
		dataRecordCollection.setName(
			HashMapBuilder.<String, Object>put(
				"en_US", name
			).build());

		return dataRecordCollection;
	}

	private DataRecordCollection _randomDataRecordCollection(
		String dataRecordCollectionKey) {

		return _createDataRecordCollection(
			dataRecordCollectionKey, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
	}

	private void _testGetDataDefinitionDataRecordCollectionsPage(
			String description, String keywords, String name)
		throws Exception {

		Long dataDefinitionId =
			testGetDataDefinitionDataRecordCollectionsPage_getDataDefinitionId();

		DataRecordCollection dataRecordCollection =
			testGetDataDefinitionDataRecordCollectionsPage_addDataRecordCollection(
				dataDefinitionId,
				_createDataRecordCollection(description, name));

		Page<DataRecordCollection> page =
			dataRecordCollectionResource.
				getDataDefinitionDataRecordCollectionsPage(
					dataDefinitionId, keywords, Pagination.of(1, 2));

		Assert.assertEquals(1, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataRecordCollection),
			(List<DataRecordCollection>)page.getItems());
		assertValid(page);

		dataRecordCollectionResource.deleteDataRecordCollection(
			dataRecordCollection.getId());
	}

	private DDMStructure _ddmStructure;
	private DDMStructure _irrelevantDDMStructure;

}
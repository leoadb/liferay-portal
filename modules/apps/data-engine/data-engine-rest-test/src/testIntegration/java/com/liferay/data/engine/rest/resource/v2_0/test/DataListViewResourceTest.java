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
import com.liferay.data.engine.rest.client.dto.v2_0.DataListView;
import com.liferay.data.engine.rest.resource.v2_0.test.util.DataDefinitionTestUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Level;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jeyvison Nascimento
 */
@RunWith(Arquillian.class)
public class DataListViewResourceTest extends BaseDataListViewResourceTestCase {

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
	public void testGraphQLDeleteDataListView() throws Exception {
		DataListView dataListView = testGraphQLDataListView_addDataListView();

		GraphQLField graphQLField = new GraphQLField(
			"mutation",
			new GraphQLField(
				"deleteDataListView",
				new HashMap<String, Object>() {
					{
						put("dataListViewId", dataListView.getId());
					}
				}));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(dataJSONObject.getBoolean("deleteDataListView"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			graphQLField = new GraphQLField(
				"query",
				new GraphQLField(
					"dataListView",
					new HashMap<String, Object>() {
						{
							put("dataListViewId", dataListView.getId());
						}
					},
					new GraphQLField("id")));

			jsonObject = JSONFactoryUtil.createJSONObject(
				invoke(graphQLField.toString()));

			JSONArray errorsJSONArray = jsonObject.getJSONArray("errors");

			Assert.assertTrue(errorsJSONArray.length() > 0);
		}
	}

	@Override
	@Test
	public void testGraphQLGetDataListView() throws Exception {
		DataListView dataListView = testGraphQLDataListView_addDataListView();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"dataListView",
				new HashMap<String, Object>() {
					{
						put("dataListViewId", dataListView.getId());
					}
				},
				new GraphQLField("id")));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				dataListView, dataJSONObject.getJSONObject("dataListView")));
	}

	@Override
	protected DataListView randomDataListView() throws Exception {
		DataListView dataListView = super.randomDataListView();

		dataListView.setFieldNames(
			new String[] {RandomTestUtil.randomString()});

		return dataListView;
	}

	@Override
	protected DataListView testDeleteDataListView_addDataListView()
		throws Exception {

		return dataListViewResource.postDataDefinitionDataListView(
			_dataDefinition.getId(), randomDataListView());
	}

	@Override
	protected Long testGetDataDefinitionDataListViewsPage_getDataDefinitionId()
		throws Exception {

		return _dataDefinition.getId();
	}

	@Override
	protected Long
			testGetDataDefinitionDataListViewsPage_getIrrelevantDataDefinitionId()
		throws Exception {

		return _irrelevantDataDefinition.getId();
	}

	@Override
	protected DataListView testGetDataListView_addDataListView()
		throws Exception {

		return dataListViewResource.postDataDefinitionDataListView(
			_dataDefinition.getId(), randomDataListView());
	}

	@Override
	protected DataListView testGraphQLDataListView_addDataListView()
		throws Exception {

		return dataListViewResource.postDataDefinitionDataListView(
			_dataDefinition.getId(), randomDataListView());
	}

	@Override
	protected DataListView testPutDataListView_addDataListView()
		throws Exception {

		return dataListViewResource.postDataDefinitionDataListView(
			_dataDefinition.getId(), randomDataListView());
	}

	private DataDefinition _dataDefinition;
	private DataDefinition _irrelevantDataDefinition;

}
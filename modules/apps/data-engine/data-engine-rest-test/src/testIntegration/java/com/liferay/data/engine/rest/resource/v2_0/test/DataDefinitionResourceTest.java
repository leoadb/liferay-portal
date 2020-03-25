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
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.pagination.Pagination;
import com.liferay.data.engine.rest.resource.v2_0.test.util.DataLayoutTestUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jeyvison Nascimento
 */
@RunWith(Arquillian.class)
public class DataDefinitionResourceTest
	extends BaseDataDefinitionResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_dataDefinitions = new ArrayList<>();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		for (DataDefinition dataDefinition : _dataDefinitions) {
			try {
				dataDefinitionResource.deleteDataDefinition(
					dataDefinition.getId());
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}
			}
		}

		super.tearDown();
	}

	@Override
	public void testGetDataDefinitionDataDefinitionFieldFieldTypes()
		throws Exception {

		String fieldTypes =
			dataDefinitionResource.
				getDataDefinitionDataDefinitionFieldFieldTypes();

		Assert.assertTrue(Validator.isNotNull(fieldTypes));
	}

	@Override
	public void testGetDataDefinitionDataDefinitionFieldLinks()
		throws Exception {

		DataDefinition postDataDefinition =
			testGetDataDefinition_addDataDefinition();

		String fieldLinks =
			dataDefinitionResource.getDataDefinitionDataDefinitionFieldLinks(
				postDataDefinition.getId(), "");

		Assert.assertTrue(Validator.isNotNull(fieldLinks));
	}

	@Override
	@Test
	public void testGetSiteDataDefinitionByContentTypeContentTypePage()
		throws Exception {

		super.testGetSiteDataDefinitionByContentTypeContentTypePage();

		Page<DataDefinition> page =
			dataDefinitionResource.
				getSiteDataDefinitionByContentTypeContentTypePage(
					testGetSiteDataDefinitionByContentTypeContentTypePage_getSiteId(),
					testGetSiteDataDefinitionByContentTypeContentTypePage_getContentType(),
					"definition", Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		_testGetSiteDataDefinitionsPage(
			"DeFiNiTiON dEsCrIpTiOn", "DEFINITION", "name");
		_testGetSiteDataDefinitionsPage(
			"abcdefghijklmnopqrstuvwxyz0123456789",
			"abcdefghijklmnopqrstuvwxyz0123456789", "definition");
		_testGetSiteDataDefinitionsPage(
			"description name", "description name", "definition");
		_testGetSiteDataDefinitionsPage(
			"description", "DEFINITION", "DeFiNiTiON NaMe");
		_testGetSiteDataDefinitionsPage(
			"description", "definition name", "definition name");
		_testGetSiteDataDefinitionsPage(
			"description", "nam", "definition name");

		DataDefinition dataDefinition =
			dataDefinitionResource.postSiteDataDefinitionByContentType(
				testGroup.getGroupId(),
				testGetSiteDataDefinitionByContentTypeContentTypePage_getContentType(),
				randomDataDefinition());

		_dataDefinitions.add(dataDefinition);

		page =
			dataDefinitionResource.
				getSiteDataDefinitionByContentTypeContentTypePage(
					testGetSiteDataDefinitionByContentTypeContentTypePage_getSiteId(),
					testGetSiteDataDefinitionByContentTypeContentTypePage_getContentType(),
					null, Pagination.of(1, 2), null);

		Assert.assertEquals(1, page.getTotalCount());
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteDataDefinition() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetDataDefinition() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetSiteDataDefinitionByContentTypeByDataDefinitionKey() {
	}

	@Override
	@Test
	public void testPutDataDefinition() throws Exception {
		DataDefinition postDataDefinition =
			testPutDataDefinition_addDataDefinition();

		DataLayout dataLayout = postDataDefinition.getDefaultDataLayout();

		DataDefinition randomDataDefinition = randomDataDefinition();

		DataLayout newDataLayout = DataLayoutTestUtil.createDataLayout(
			postDataDefinition.getId(), "Data Layout Updated",
			postDataDefinition.getSiteId());

		newDataLayout.setId(dataLayout.getId());

		randomDataDefinition.setDefaultDataLayout(newDataLayout);

		DataDefinition putDataDefinition =
			dataDefinitionResource.putDataDefinition(
				postDataDefinition.getId(), randomDataDefinition);

		assertEquals(randomDataDefinition, putDataDefinition);
		assertValid(putDataDefinition);

		DataDefinition getDataDefinition =
			dataDefinitionResource.getDataDefinition(putDataDefinition.getId());

		assertEquals(randomDataDefinition, getDataDefinition);
		assertValid(getDataDefinition);
	}

	@Ignore
	@Override
	@Test
	public void testPutDataDefinitionPermission() throws Exception {
	}

	@Override
	protected void assertValid(DataDefinition dataDefinition) {
		super.assertValid(dataDefinition);

		boolean valid = true;

		if (dataDefinition.getDataDefinitionFields() != null) {
			for (DataDefinitionField dataDefinitionField :
					dataDefinition.getDataDefinitionFields()) {

				if (Validator.isNull(dataDefinitionField.getFieldType()) ||
					Validator.isNull(dataDefinitionField.getLabel()) ||
					Validator.isNull(dataDefinitionField.getName()) ||
					Validator.isNull(dataDefinitionField.getTip())) {

					valid = false;
				}
			}
		}
		else {
			valid = false;
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"availableLanguageIds", "defaultLanguageId", "name", "userId"
		};
	}

	@Override
	protected DataDefinition randomDataDefinition() throws Exception {
		return _createDataDefinition(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());
	}

	@Override
	protected DataDefinition testDeleteDataDefinition_addDataDefinition()
		throws Exception {

		DataDefinition dataDefinition =
			dataDefinitionResource.postSiteDataDefinitionByContentType(
				testGroup.getGroupId(), _CONTENT_TYPE, randomDataDefinition());

		_dataDefinitions.add(dataDefinition);

		return dataDefinition;
	}

	@Override
	protected DataDefinition testGetDataDefinition_addDataDefinition()
		throws Exception {

		DataDefinition dataDefinition =
			dataDefinitionResource.postSiteDataDefinitionByContentType(
				testGroup.getGroupId(), _CONTENT_TYPE, randomDataDefinition());

		_dataDefinitions.add(dataDefinition);

		return dataDefinition;
	}

	@Override
	protected DataDefinition
			testGetDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				String contentType, DataDefinition dataDefinition)
		throws Exception {

		DataDefinition postDataDefinition =
			dataDefinitionResource.postDataDefinitionByContentType(
				contentType, dataDefinition);

		_dataDefinitions.add(postDataDefinition);

		return postDataDefinition;
	}

	@Override
	protected String
			testGetDataDefinitionByContentTypeContentTypePage_getContentType()
		throws Exception {

		return _CONTENT_TYPE;
	}

	@Override
	protected DataDefinition
			testGetSiteDataDefinitionByContentTypeByDataDefinitionKey_addDataDefinition()
		throws Exception {

		DataDefinition dataDefinition =
			dataDefinitionResource.postSiteDataDefinitionByContentType(
				testGroup.getGroupId(), _CONTENT_TYPE, randomDataDefinition());

		_dataDefinitions.add(dataDefinition);

		dataDefinition.setContentType(_CONTENT_TYPE);

		return dataDefinition;
	}

	@Override
	protected DataDefinition
			testGetSiteDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				Long siteId, String contentType, DataDefinition dataDefinition)
		throws Exception {

		DataDefinition postDataDefinition =
			dataDefinitionResource.postSiteDataDefinitionByContentType(
				siteId, contentType, dataDefinition);

		_dataDefinitions.add(postDataDefinition);

		return postDataDefinition;
	}

	@Override
	protected String
			testGetSiteDataDefinitionByContentTypeContentTypePage_getContentType()
		throws Exception {

		return _CONTENT_TYPE;
	}

	@Override
	protected Long
			testGetSiteDataDefinitionByContentTypeContentTypePage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	@Override
	protected DataDefinition
			testPostDataDefinitionByContentType_addDataDefinition(
				DataDefinition dataDefinition)
		throws Exception {

		DataDefinition postDataDefinition =
			dataDefinitionResource.postSiteDataDefinitionByContentType(
				testGroup.getGroupId(), _CONTENT_TYPE, dataDefinition);

		_dataDefinitions.add(postDataDefinition);

		return postDataDefinition;
	}

	@Override
	protected DataDefinition
			testPostSiteDataDefinitionByContentType_addDataDefinition(
				DataDefinition dataDefinition)
		throws Exception {

		DataDefinition postDataDefinition =
			dataDefinitionResource.postSiteDataDefinitionByContentType(
				testGroup.getGroupId(), _CONTENT_TYPE, dataDefinition);

		_dataDefinitions.add(postDataDefinition);

		return postDataDefinition;
	}

	@Override
	protected DataDefinition testPutDataDefinition_addDataDefinition()
		throws Exception {

		DataDefinition dataDefinition =
			dataDefinitionResource.postSiteDataDefinitionByContentType(
				testGroup.getGroupId(), _CONTENT_TYPE, randomDataDefinition());

		_dataDefinitions.add(dataDefinition);

		return dataDefinition;
	}

	private DataDefinition _createDataDefinition(
			String description, String name)
		throws Exception {

		DataDefinition dataDefinition = new DataDefinition() {
			{
				availableLanguageIds = new String[] {"en_US", "pt_BR"};
				dataDefinitionFields = new DataDefinitionField[] {
					new DataDefinitionField() {
						{
							description = HashMapBuilder.<String, Object>put(
								"en_US", RandomTestUtil.randomString()
							).build();
							fieldType = "text";
							label = HashMapBuilder.<String, Object>put(
								"label", RandomTestUtil.randomString()
							).build();
							name = RandomTestUtil.randomString();
							tip = HashMapBuilder.<String, Object>put(
								"tip", RandomTestUtil.randomString()
							).build();
						}
					}
				};
				dataDefinitionKey = RandomTestUtil.randomString();
				defaultDataLayout = DataLayoutTestUtil.createDataLayout(
					0L, "Data Layout Name", testGroup.getGroupId());
				defaultLanguageId = "en_US";
				siteId = testGroup.getGroupId();
				userId = TestPropsValues.getUserId();
			}
		};

		dataDefinition.setDescription(
			HashMapBuilder.<String, Object>put(
				"en_US", description
			).build());
		dataDefinition.setName(
			HashMapBuilder.<String, Object>put(
				"en_US", name
			).build());

		return dataDefinition;
	}

	private void _testGetSiteDataDefinitionsPage(
			String description, String keywords, String name)
		throws Exception {

		Long siteId =
			testGetSiteDataDefinitionByContentTypeContentTypePage_getSiteId();

		DataDefinition dataDefinition =
			testGetSiteDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				siteId,
				testGetSiteDataDefinitionByContentTypeContentTypePage_getContentType(),
				_createDataDefinition(description, name));

		Page<DataDefinition> page =
			dataDefinitionResource.
				getSiteDataDefinitionByContentTypeContentTypePage(
					siteId,
					testGetSiteDataDefinitionByContentTypeContentTypePage_getContentType(),
					keywords, Pagination.of(1, 2), null);

		Assert.assertEquals(1, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataDefinition),
			(List<DataDefinition>)page.getItems());
		assertValid(page);

		dataDefinitionResource.deleteDataDefinition(dataDefinition.getId());
	}

	private static final String _CONTENT_TYPE = "app-builder";

	private static final Log _log = LogFactoryUtil.getLog(
		DataDefinitionResourceTest.class);

	private List<DataDefinition> _dataDefinitions;

	@Inject(type = Portal.class)
	private Portal _portal;

}
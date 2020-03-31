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
import com.liferay.data.engine.rest.client.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.pagination.Pagination;
import com.liferay.data.engine.rest.resource.v2_0.test.util.DataDefinitionTestUtil;
import com.liferay.data.engine.rest.resource.v2_0.test.util.DataLayoutTestUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcelo Mello
 */
@RunWith(Arquillian.class)
public class DataLayoutResourceTest extends BaseDataLayoutResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_ddmStructure = DataDefinitionTestUtil.addDDMStructure(testGroup);
		_irrelevantDDMStructure = DataDefinitionTestUtil.addDDMStructure(
			irrelevantGroup);
	}

	@Test
	public void testGetDataDefinitionDataLayoutsPage1() throws Exception {
		super.testGetDataDefinitionDataLayoutsPage();

		_testGetDataDefinitionDataLayoutsPage("FORM", "FoRmSLaYoUt");
	}

	@Test
	public void testGetDataDefinitionDataLayoutsPage2() throws Exception {
		super.testGetDataDefinitionDataLayoutsPage();

		_testGetDataDefinitionDataLayoutsPage(
			"abcdefghijklmnopqrstuvwxyz0123456789",
			"abcdefghijklmnopqrstuvwxyz0123456789");
	}

	@Test
	public void testGetDataDefinitionDataLayoutsPage3() throws Exception {
		super.testGetDataDefinitionDataLayoutsPage();

		_testGetDataDefinitionDataLayoutsPage("form layout", "form layout");
	}

	@Test
	public void testGetDataDefinitionDataLayoutsPage4() throws Exception {
		super.testGetDataDefinitionDataLayoutsPage();

		_testGetDataDefinitionDataLayoutsPage("layo", "form layout");
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteDataLayout() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetDataLayout() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetSiteDataLayoutByContentTypeByDataLayoutKey() {
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
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"dataDefinitionId", "name", "paginationMode"};
	}

	@Override
	protected DataLayout randomDataLayout() {
		return DataLayoutTestUtil.createDataLayout(
			_ddmStructure.getStructureId(), RandomTestUtil.randomString(),
			testGroup.getGroupId());
	}

	@Override
	protected DataLayout randomIrrelevantDataLayout() throws Exception {
		DataLayout randomIrrelevantDataLayout =
			super.randomIrrelevantDataLayout();

		randomIrrelevantDataLayout.setDataDefinitionId(
			_irrelevantDDMStructure.getStructureId());

		return randomIrrelevantDataLayout;
	}

	@Override
	protected DataLayout testDeleteDataLayout_addDataLayout() throws Exception {
		return dataLayoutResource.postDataDefinitionDataLayout(
			_ddmStructure.getStructureId(), randomDataLayout());
	}

	@Override
	protected DataLayout testDeleteDataLayoutsDataDefinition_addDataLayout()
		throws Exception {

		return dataLayoutResource.postDataDefinitionDataLayout(
			_ddmStructure.getStructureId(), randomDataLayout());
	}

	@Override
	protected Long testGetDataDefinitionDataLayoutsPage_getDataDefinitionId()
		throws Exception {

		return _ddmStructure.getStructureId();
	}

	@Override
	protected DataLayout testGetDataLayout_addDataLayout() throws Exception {
		return dataLayoutResource.postDataDefinitionDataLayout(
			_ddmStructure.getStructureId(), randomDataLayout());
	}

	@Override
	protected DataLayout
			testGetSiteDataLayoutByContentTypeByDataLayoutKey_addDataLayout()
		throws Exception {

		DataLayout dataLayout = dataLayoutResource.postDataDefinitionDataLayout(
			_ddmStructure.getStructureId(), randomDataLayout());

		dataLayout.setContentType("app-builder");

		return dataLayout;
	}

	@Override
	protected DataLayout testPutDataLayout_addDataLayout() throws Exception {
		return dataLayoutResource.postDataDefinitionDataLayout(
			_ddmStructure.getStructureId(), randomDataLayout());
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
					_ddmStructure.getStructureId(), name,
					testGroup.getGroupId()));

		dataLayoutList.add(dataLayout);

		Page<DataLayout> page =
			dataLayoutResource.getDataDefinitionDataLayoutsPage(
				dataDefinitionId, keywords, Pagination.of(1, 2), null);

		Assert.assertEquals(1, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataLayout), (List<DataLayout>)page.getItems());
		assertValid(page);
	}

	@DeleteAfterTestRun
	private DDMStructure _ddmStructure;

	@DeleteAfterTestRun
	private DDMStructure _irrelevantDDMStructure;

}
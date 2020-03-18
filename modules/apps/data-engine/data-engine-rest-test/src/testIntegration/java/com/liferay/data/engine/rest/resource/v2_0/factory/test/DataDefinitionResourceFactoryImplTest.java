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
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class DataDefinitionResourceFactoryImplTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();
	}

	@Test
	public void testAcceptLanguage1() throws Exception {
		_user.setLanguageId("en_US");

		DataDefinitionResource dataDefinitionResource =
			DataDefinitionResource.builder(
			).httpServletRequest(
				new MockHttpServletRequest() {

					@Override
					public Locale getLocale() {
						return LocaleUtil.BRAZIL;
					}

				}
			).user(
				_user
			).build();

		String fieldTypes =
			dataDefinitionResource.
				getDataDefinitionDataDefinitionFieldFieldTypes();

		Assert.assertTrue(
			StringUtil.contains(fieldTypes, "\"locale\":\"pt_BR\""));
		Assert.assertFalse(
			StringUtil.contains(fieldTypes, "\"locale\":\"en_US\""));
	}

	@Test
	public void testAcceptLanguage2() throws Exception {
		_user.setLanguageId("en_US");

		DataDefinitionResource dataDefinitionResource =
			DataDefinitionResource.builder(
			).httpServletRequest(
				new MockHttpServletRequest() {

					@Override
					public Locale getLocale() {
						return null;
					}

				}
			).user(
				_user
			).build();

		String fieldTypes =
			dataDefinitionResource.
				getDataDefinitionDataDefinitionFieldFieldTypes();

		Assert.assertTrue(
			StringUtil.contains(fieldTypes, "\"locale\":\"en_US\""));
		Assert.assertFalse(
			StringUtil.contains(fieldTypes, "\"locale\":\"pt_BR\""));
	}

	@Test(expected = NullPointerException.class)
	public void testHttpServletRequest() throws Exception {
		DataDefinitionResource dataDefinitionResource =
			DataDefinitionResource.builder(
			).user(
				_user
			).build();

		dataDefinitionResource.getDataDefinitionDataDefinitionFieldFieldTypes();
	}

	@DeleteAfterTestRun
	private User _user;

}
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

package com.liferay.data.engine.field.type.util;

import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.runners.MockitoJUnitRunner;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Mateus Santana
 */
@RunWith(MockitoJUnitRunner.class)
public class LocalizedValueUtilTest extends PowerMockito {

	@Test
	public void testToLocaleStringMapEmptyKeyValue() {
		Map<String, Object> toLocaleStringMap =
			HashMapBuilder.<String, Object>put(
				"", ""
			).build();

		Assert.assertTrue(
			LocalizedValueUtil.toLocaleStringMap(toLocaleStringMap) instanceof
				Map);
	}

	@Test
	public void testToLocaleStringMapEmptyMap() {
		Assert.assertEquals(
			Collections.emptyMap(),
			LocalizedValueUtil.toLocaleStringMap(new HashMap<>()));
	}

	@Test
	public void testToLocaleStringMapValidMap() {
		Map<String, Object> toLocaleStringMap =
			HashMapBuilder.<String, Object>put(
				"en_US", "English"
			).build();

		Assert.assertTrue(
			LocalizedValueUtil.toLocaleStringMap(toLocaleStringMap) instanceof
				Map);
	}

	@Test
	public void testToLocalizedValueEmptyMap() {
		Map<String, Object> toLocalizedValue = new HashMap<>();

		Assert.assertTrue(
			LocalizedValueUtil.toLocalizedValue(toLocalizedValue) instanceof
				LocalizedValue);
	}

	@Test
	public void testToLocalizedValueNullValue() {
		Assert.assertNull(LocalizedValueUtil.toLocalizedValue(null));
	}

	@Test
	public void testToLocalizedValuesMapNullLocalizedValue() {
		Assert.assertEquals(
			Collections.emptyMap(),
			LocalizedValueUtil.toLocalizedValuesMap(null));
	}

	@Test
	public void testToLocalizedValuesMapValidLocalizedValue() {
		LocalizedValue toLocalizedValuesMap = new LocalizedValue(
			LocaleUtil.BRAZIL);

		Assert.assertTrue(
			LocalizedValueUtil.toLocalizedValuesMap(
				toLocalizedValuesMap) instanceof Map);
	}

	@Test
	public void testToLocalizedValueValidMap() {
		Map<String, Object> toLocalizedValue =
			HashMapBuilder.<String, Object>put(
				"en_US", "English"
			).build();

		Assert.assertTrue(
			LocalizedValueUtil.toLocalizedValue(toLocalizedValue) instanceof
				LocalizedValue);
	}

}
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
import java.util.Locale;
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

		Map<Locale, String> localeStringMap =
			LocalizedValueUtil.toLocaleStringMap(toLocaleStringMap);

		Assert.assertEquals("", localeStringMap.get(LocaleUtil.US));
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
				"en_US", "en_US"
			).build();

		Map<Locale, String> localeStringMap =
			LocalizedValueUtil.toLocaleStringMap(toLocaleStringMap);

		Assert.assertEquals("en_US", localeStringMap.get(LocaleUtil.US));
	}

	@Test
	public void testToLocalizedValueEmptyMap() {
		Map<String, Object> toLocalizedValue = new HashMap<>();

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		Assert.assertEquals(
			localizedValue,
			LocalizedValueUtil.toLocalizedValue(toLocalizedValue));
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
		LocalizedValue toLocalizedValuesMap = new LocalizedValue();

		toLocalizedValuesMap.addString(LocaleUtil.US, "en_US");

		Map<String, Object> localizedValuesMap =
			LocalizedValueUtil.toLocalizedValuesMap(toLocalizedValuesMap);

		Assert.assertEquals("en_US", localizedValuesMap.get(LocaleUtil.US));
	}

	@Test
	public void testToLocalizedValueValidMap() {
		Map<String, Object> toLocalizedValue =
			HashMapBuilder.<String, Object>put(
				"en_US", "en_US"
			).build();

		LocalizedValue localizedValue = LocalizedValueUtil.toLocalizedValue(
			toLocalizedValue);

		Assert.assertEquals("en_US", localizedValue.getString(LocaleUtil.US));
	}

	@Test
	public void testToStringObjectMap() {
		LocalizedValue toLocalizedValuesMap = new LocalizedValue(LocaleUtil.US);

		toLocalizedValuesMap.addString(LocaleUtil.US, "en_US");

		Map<Locale, String> toStringObjectMap =
			toLocalizedValuesMap.getValues();

		Map<String, Object> stringObjectMap =
			HashMapBuilder.<String, Object>put(
				"en_US", "en_US"
			).build();

		Assert.assertEquals(
			stringObjectMap,
			LocalizedValueUtil.toStringObjectMap(toStringObjectMap));
	}

}
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

package com.liferay.dynamic.data.mapping.validator;

import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidationException.MustNotDuplicateFieldName;
import com.liferay.dynamic.data.mapping.validator.internal.DDMFormLayoutValidatorImpl;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;

import org.junit.Test;

/**
 * @author Pablo Carvalho
 */
public class DDMFormLayoutValidatorTest {

	@Test(expected = MustNotDuplicateFieldName.class)
	public void testDuplicateFieldNames() throws Exception {
		DDMFormLayoutColumn ddmFormLayoutColumn1 = createDDMFormLayoutColumn(
			6, "field1", "field2", "field3");

		DDMFormLayoutColumn ddmFormLayoutColumn2 = createDDMFormLayoutColumn(
			6, "field1", "field3");

		DDMFormLayoutRow ddmFormLayoutRow = createDDMFormLayoutRow(
			ddmFormLayoutColumn1);

		ddmFormLayoutRow.addDDMFormLayoutColumn(ddmFormLayoutColumn2);

		LocalizedValue title = createLocalizedValue("Page1", LocaleUtil.US);

		DDMFormLayoutPage ddmFormLayoutPage = createDDMFormLayoutPage(
			ddmFormLayoutRow, title);

		DDMFormLayout ddmFormLayout = createDDMFormLayout(
			ddmFormLayoutPage, LocaleUtil.US);

		_ddmFormLayoutValidator.validate(ddmFormLayout);
	}

	@Test(expected = DDMFormLayoutValidationException.class)
	public void testInvalidRowSize() throws Exception {
		DDMFormLayoutColumn ddmFormLayoutColumn1 = createDDMFormLayoutColumn(
			6, "field1");

		DDMFormLayoutColumn ddmFormLayoutColumn2 = createDDMFormLayoutColumn(
			7, "field2");

		DDMFormLayoutRow ddmFormLayoutRow = createDDMFormLayoutRow(
			ddmFormLayoutColumn1);

		ddmFormLayoutRow.addDDMFormLayoutColumn(ddmFormLayoutColumn2);

		LocalizedValue title = createLocalizedValue("Page1", LocaleUtil.US);

		DDMFormLayoutPage ddmFormLayoutPage = createDDMFormLayoutPage(
			ddmFormLayoutRow, title);

		DDMFormLayout ddmFormLayout = createDDMFormLayout(
			ddmFormLayoutPage, LocaleUtil.US);

		_ddmFormLayoutValidator.validate(ddmFormLayout);
	}

	@Test(expected = DDMFormLayoutValidationException.class)
	public void testNullDefaultLocale() throws Exception {
		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout.setDefaultLocale(null);

		_ddmFormLayoutValidator.validate(ddmFormLayout);
	}

	@Test
	public void testValidDDMFormLayout() throws Exception {
		DDMFormLayoutColumn ddmFormLayoutColumn = createDDMFormLayoutColumn(
			12, "field");

		DDMFormLayoutRow ddmFormLayoutRow = createDDMFormLayoutRow(
			ddmFormLayoutColumn);

		LocalizedValue title = createLocalizedValue("Page1", LocaleUtil.US);

		DDMFormLayoutPage ddmFormLayoutPage = createDDMFormLayoutPage(
			ddmFormLayoutRow, title);

		DDMFormLayout ddmFormLayout = createDDMFormLayout(
			ddmFormLayoutPage, LocaleUtil.US);

		_ddmFormLayoutValidator.validate(ddmFormLayout);
	}

	@Test(expected = DDMFormLayoutValidationException.class)
	public void testWrongDefaultLocaleSetForPageTitle() throws Exception {
		DDMFormLayoutColumn ddmFormLayoutColumn = createDDMFormLayoutColumn(
			12, "field");

		DDMFormLayoutRow ddmFormLayoutRow = createDDMFormLayoutRow(
			ddmFormLayoutColumn);

		LocalizedValue title = createLocalizedValue("Page1", LocaleUtil.US);

		DDMFormLayoutPage ddmFormLayoutPage = createDDMFormLayoutPage(
			ddmFormLayoutRow, title);

		DDMFormLayout ddmFormLayout = createDDMFormLayout(
			ddmFormLayoutPage, LocaleUtil.BRAZIL);

		_ddmFormLayoutValidator.validate(ddmFormLayout);
	}

	private DDMFormLayout createDDMFormLayout(
		DDMFormLayoutPage ddmFormLayoutPage, Locale defaultLocale) {

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout.addDDMFormLayoutPage(ddmFormLayoutPage);

		ddmFormLayout.setDefaultLocale(defaultLocale);

		return ddmFormLayout;
	}

	private DDMFormLayoutColumn createDDMFormLayoutColumn(
		int size, String... fieldNames) {

		DDMFormLayoutColumn ddmFormLayoutColumn = new DDMFormLayoutColumn(
			size, fieldNames);

		return ddmFormLayoutColumn;
	}

	private DDMFormLayoutPage createDDMFormLayoutPage(
		DDMFormLayoutRow ddmFormLayoutRow, LocalizedValue title) {

		DDMFormLayoutPage ddmFormLayoutPage = new DDMFormLayoutPage();

		ddmFormLayoutPage.addDDMFormLayoutRow(ddmFormLayoutRow);

		ddmFormLayoutPage.setTitle(title);

		return ddmFormLayoutPage;
	}

	private DDMFormLayoutRow createDDMFormLayoutRow(
		DDMFormLayoutColumn ddmFormLayoutColumn) {

		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		ddmFormLayoutRow.addDDMFormLayoutColumn(ddmFormLayoutColumn);

		return ddmFormLayoutRow;
	}

	private LocalizedValue createLocalizedValue(
		String value, Locale defaultLocale) {

		LocalizedValue localizedValue = new LocalizedValue(defaultLocale);

		localizedValue.addString(defaultLocale, value);

		return localizedValue;
	}

	private final DDMFormLayoutValidator _ddmFormLayoutValidator =
		new DDMFormLayoutValidatorImpl();

}
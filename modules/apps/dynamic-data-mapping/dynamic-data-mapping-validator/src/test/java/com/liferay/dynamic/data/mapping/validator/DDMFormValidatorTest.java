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

import static org.mockito.Mockito.when;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTrackerUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.AvailableLocalesNotSet;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.DefaultLocaleNotSet;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.DefaultLocaleNotSetAsAvailableLocale;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.DuplicateFieldName;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.EmptyOptionSet;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.FieldTypeNotSet;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.InvalidAvailableLocalesSetForProperty;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.InvalidCharactersSetForFieldName;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.InvalidCharactersSetForFieldType;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.InvalidDefaultLocaleSetForProperty;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.InvalidIndexTypeSet;
import com.liferay.dynamic.data.mapping.validator.internal.DDMFormValidatorImpl;
import com.liferay.portal.bean.BeanPropertiesImpl;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Marcellus Tavares
 */
@PrepareForTest(DDMFormFieldTypeServicesTrackerUtil.class)
@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor(
	"com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTrackerUtil"
)
public class DDMFormValidatorTest {

	@Before
	public void setUp() {
		setUpBeanPropertiesUtil();
		setUpDDMFormFieldTypeServicesTrackerUtil();
	}

	@Test(expected = InvalidCharactersSetForFieldName.class)
	public void testDashInFieldName() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		ddmForm.addDDMFormField(
			new DDMFormField("text-dash", DDMFormFieldType.TEXT));

		_ddmFormValidator.validate(ddmForm);
	}

	@Test(expected = DefaultLocaleNotSetAsAvailableLocale.class)
	public void testDefaultLocaleMissingAsAvailableLocale() throws Exception {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(createAvailableLocales(LocaleUtil.BRAZIL));
		ddmForm.setDefaultLocale(LocaleUtil.US);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test(expected = DuplicateFieldName.class)
	public void testDuplicateCaseInsensitiveFieldName() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		ddmForm.addDDMFormField(
			new DDMFormField("Name1", DDMFormFieldType.TEXT));

		DDMFormField name2DDMFormField = new DDMFormField(
			"Name2", DDMFormFieldType.TEXT);

		name2DDMFormField.addNestedDDMFormField(
			new DDMFormField("name1", DDMFormFieldType.TEXT));

		ddmForm.addDDMFormField(name2DDMFormField);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test(expected = DuplicateFieldName.class)
	public void testDuplicateFieldName() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		ddmForm.addDDMFormField(
			new DDMFormField("Name1", DDMFormFieldType.TEXT));

		DDMFormField name2DDMFormField = new DDMFormField(
			"Name2", DDMFormFieldType.TEXT);

		name2DDMFormField.addNestedDDMFormField(
			new DDMFormField("Name1", DDMFormFieldType.TEXT));

		ddmForm.addDDMFormField(name2DDMFormField);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test(expected = FieldTypeNotSet.class)
	public void testEmptyFieldType() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField("Name", "");

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test(expected = InvalidIndexTypeSet.class)
	public void testInvalidFieldIndexType() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Text", DDMFormFieldType.TEXT);

		ddmFormField.setIndexType("Invalid");

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test(expected = InvalidCharactersSetForFieldName.class)
	public void testInvalidFieldName() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"*", DDMFormFieldType.TEXT);

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test(expected = EmptyOptionSet.class)
	public void testNoOptionsSetForFieldOptions() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Select", DDMFormFieldType.SELECT);

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test(expected = AvailableLocalesNotSet.class)
	public void testNullAvailableLocales() throws Exception {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(null);
		ddmForm.setDefaultLocale(LocaleUtil.US);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test(expected = DefaultLocaleNotSet.class)
	public void testNullDefaultLocale() throws Exception {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setDefaultLocale(null);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test
	public void testRegisteredTextFieldType() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Text", DDMFormFieldType.TEXT);

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test(expected = InvalidCharactersSetForFieldType.class)
	public void testUnregisteredFieldType() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Name", "Unregistered Type");

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test
	public void testValidFieldName() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"valid_name", DDMFormFieldType.TEXT);

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test(expected = InvalidAvailableLocalesSetForProperty.class)
	public void testWrongAvailableLocalesSetForFieldOptions() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Select", DDMFormFieldType.SELECT);

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel(
			"Value", LocaleUtil.BRAZIL, "Portuguese Label");

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test(expected = InvalidAvailableLocalesSetForProperty.class)
	public void testWrongAvailableLocalesSetForLabel() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Text", DDMFormFieldType.TEXT);

		LocalizedValue label = ddmFormField.getLabel();

		label.addString(LocaleUtil.BRAZIL, "Portuguese Label");

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test(expected = InvalidDefaultLocaleSetForProperty.class)
	public void testWrongDefaultLocaleSetForFieldOptions() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Select", DDMFormFieldType.SELECT);

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel(
			"Value", LocaleUtil.US, "Value Label");

		ddmFormFieldOptions.setDefaultLocale(LocaleUtil.BRAZIL);

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidator.validate(ddmForm);
	}

	@Test(expected = InvalidDefaultLocaleSetForProperty.class)
	public void testWrongDefaultLocaleSetForLabel() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Text", DDMFormFieldType.TEXT);

		LocalizedValue label = ddmFormField.getLabel();

		label.addString(LocaleUtil.US, "Label");

		label.setDefaultLocale(LocaleUtil.BRAZIL);

		ddmForm.addDDMFormField(ddmFormField);

		_ddmFormValidator.validate(ddmForm);
	}

	protected Set<Locale> createAvailableLocales(Locale... locales) {
		return DDMFormTestUtil.createAvailableLocales(locales);
	}

	protected void setUpBeanPropertiesUtil() {
		BeanPropertiesUtil beanPropertiesUtil = new BeanPropertiesUtil();

		beanPropertiesUtil.setBeanProperties(new BeanPropertiesImpl());
	}

	protected void setUpDDMFormFieldTypeServicesTrackerUtil() {
		PowerMockito.mockStatic(DDMFormFieldTypeServicesTrackerUtil.class);

		when(
			DDMFormFieldTypeServicesTrackerUtil.getDDMFormFieldTypeNames()
		).thenReturn(
			new HashSet<String>(
				Arrays.asList(
					new String[] {
						DDMFormFieldType.TEXT, DDMFormFieldType.SELECT
					}))
		);
	}

	private final DDMFormValidator _ddmFormValidator =
		new DDMFormValidatorImpl();

}
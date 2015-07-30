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

package com.liferay.dynamic.data.mapping.type.select;

import com.liferay.dynamic.data.mapping.bridge.DDMForm;
import com.liferay.dynamic.data.mapping.bridge.DDMFormField;
import com.liferay.dynamic.data.mapping.bridge.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.bridge.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.bridge.DDMFormValues;
import com.liferay.dynamic.data.mapping.bridge.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Renato Rego
 */
public class SelectDDMFormFieldValueRendererAccessorTest {

	@Before
	public void setUp() {
		setUpJSONFactoryUtil();
	}

	@Test
	public void testGetSelectMultipleRenderedValues() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"Select", "Select", "select", "string", false, false, false);

		int numberOfOptions = 2;

		DDMFormFieldOptions ddmFormFieldOptions = createDDMFormFieldOptions(
			numberOfOptions);

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		JSONArray optionsValues = createOptionsValuesJSONArray(numberOfOptions);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Select", new UnlocalizedValue(optionsValues.toString()));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		SelectDDMFormFieldValueRendererAccessor
			selectDDMFormFieldValueRendererAccessor =
				createSelectDDMFormFieldValueRendererAccessor(LocaleUtil.US);

		Assert.assertEquals(
			"option 1, option 2",
			selectDDMFormFieldValueRendererAccessor.get(ddmFormFieldValue));
	}

	@Test
	public void testGetSelectSingleRenderedValue() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"Select", "Select", "select", "string", false, false, false);

		int numberOfOptions = 1;

		DDMFormFieldOptions ddmFormFieldOptions = createDDMFormFieldOptions(
			numberOfOptions);

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		JSONArray optionsValues = createOptionsValuesJSONArray(numberOfOptions);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Select", new UnlocalizedValue(optionsValues.toString()));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		SelectDDMFormFieldValueRendererAccessor
			selectDDMFormFieldValueRendererAccessor =
				createSelectDDMFormFieldValueRendererAccessor(LocaleUtil.US);

		Assert.assertEquals(
			"option 1",
			selectDDMFormFieldValueRendererAccessor.get(ddmFormFieldValue));
	}

	protected DDMFormFieldOptions createDDMFormFieldOptions(
		int numberOfOptions) {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		for (int i = 1; i <= numberOfOptions; i++) {
			ddmFormFieldOptions.addOptionLabel(
				"value " + i, LocaleUtil.US, "option " + i);
		}

		return ddmFormFieldOptions;
	}

	protected JSONArray createOptionsValuesJSONArray(int numberOfOptions) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (int i = 1; i <= numberOfOptions; i++) {
			jsonArray.put("value " + i);
		}

		return jsonArray;
	}

	protected SelectDDMFormFieldValueRendererAccessor
		createSelectDDMFormFieldValueRendererAccessor(Locale locale) {

		SelectDDMFormFieldValueAccessor selectDDMFormFieldValueAccessor =
			new SelectDDMFormFieldValueAccessor(locale);

		return new SelectDDMFormFieldValueRendererAccessor(
			selectDDMFormFieldValueAccessor);
	}

	protected void setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

}
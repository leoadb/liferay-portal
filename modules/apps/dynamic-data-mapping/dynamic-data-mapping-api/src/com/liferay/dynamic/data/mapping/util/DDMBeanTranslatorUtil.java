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

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;

import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DDMBeanTranslatorUtil {

	public static DDMForm translate(
		com.liferay.portlet.dynamicdatamapping.model.DDMForm ddmForm) {

		if (ddmForm == null) {
			return null;
		}

		DDMForm copyDDMForm = new DDMForm();

		copyDDMForm.setAvailableLocales(ddmForm.getAvailableLocales());
		copyDDMForm.setDefaultLocale(ddmForm.getDefaultLocale());

		for (com.liferay.portlet.dynamicdatamapping.model.DDMFormField
				ddmFormField :
					ddmForm.getDDMFormFields()) {

			DDMFormField copyDDMFormField = translate(ddmFormField);

			copyDDMFormField.setDDMForm(copyDDMForm);

			copyDDMForm.addDDMFormField(copyDDMFormField);
		}

		return copyDDMForm;
	}

	public static DDMFormField translate(
		com.liferay.portlet.dynamicdatamapping.model.DDMFormField ddmFormField) {

		if (ddmFormField == null) {
			return null;
		}

		DDMFormField copyDDMFormField = new DDMFormField(
			ddmFormField.getName(), ddmFormField.getType());

		copyDDMFormField.setDataType(ddmFormField.getDataType());
		copyDDMFormField.setFieldNamespace(ddmFormField.getFieldNamespace());
		copyDDMFormField.setIndexType(ddmFormField.getIndexType());
		copyDDMFormField.setDDMFormFieldOptions(
			translate(ddmFormField.getDDMFormFieldOptions()));
		copyDDMFormField.setLabel(translateLocalizedValue(ddmFormField.getLabel()));
		copyDDMFormField.setLocalizable(ddmFormField.isLocalizable());
		copyDDMFormField.setMultiple(ddmFormField.isMultiple());
		copyDDMFormField.setPredefinedValue(
			translateLocalizedValue(ddmFormField.getPredefinedValue()));
		copyDDMFormField.setReadOnly(ddmFormField.isReadOnly());
		copyDDMFormField.setRepeatable(ddmFormField.isRepeatable());
		copyDDMFormField.setRequired(ddmFormField.isRequired());
		copyDDMFormField.setShowLabel(ddmFormField.isShowLabel());
		copyDDMFormField.setStyle(translateLocalizedValue(ddmFormField.getStyle()));
		copyDDMFormField.setTip(translateLocalizedValue(ddmFormField.getTip()));
		copyDDMFormField.setVisibilityExpression(
			ddmFormField.getVisibilityExpression());

		for (com.liferay.portlet.dynamicdatamapping.model.DDMFormField
			nestedDDMFormField : ddmFormField.getNestedDDMFormFields()) {

			copyDDMFormField.addNestedDDMFormField(
				translate(nestedDDMFormField));
		}

		return copyDDMFormField;
	}

	public static DDMFormValues translate(
		com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues ddmFormValues) {

		if (ddmFormValues == null) {
			return null;
		}

		DDMFormValues copyDDMFormValues = new DDMFormValues(
			translate(ddmFormValues.getDDMForm()));

		copyDDMFormValues.setAvailableLocales(
			ddmFormValues.getAvailableLocales());
		copyDDMFormValues.setDefaultLocale(ddmFormValues.getDefaultLocale());

		for (com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue
			ddmFormFieldValue : ddmFormValues.getDDMFormFieldValues()) {

			copyDDMFormValues.addDDMFormFieldValue(
				translate(ddmFormFieldValue));
		}

		return copyDDMFormValues;
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMForm translate(
		DDMForm ddmForm) {

		if (ddmForm == null) {
			return null;
		}

		com.liferay.portlet.dynamicdatamapping.model.DDMForm copyDDMForm =
			new com.liferay.portlet.dynamicdatamapping.model.DDMForm();

		copyDDMForm.setAvailableLocales(ddmForm.getAvailableLocales());
		copyDDMForm.setDefaultLocale(ddmForm.getDefaultLocale());

		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			com.liferay.portlet.dynamicdatamapping.model.DDMFormField
				copyDDMFormField = translate(ddmFormField);

			copyDDMFormField.setDDMForm(copyDDMForm);

			copyDDMForm.addDDMFormField(copyDDMFormField);
		}

		return copyDDMForm;
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMFormField
		translate(DDMFormField ddmFormField) {

		if (ddmFormField == null) {
			return null;
		}

		com.liferay.portlet.dynamicdatamapping.model.DDMFormField copyDDMFormField =
			new com.liferay.portlet.dynamicdatamapping.model.DDMFormField(
				ddmFormField.getName(), ddmFormField.getType());

		copyDDMFormField.setDataType(ddmFormField.getDataType());
		copyDDMFormField.setFieldNamespace(ddmFormField.getFieldNamespace());
		copyDDMFormField.setIndexType(ddmFormField.getIndexType());
		copyDDMFormField.setDDMFormFieldOptions(
			translate(ddmFormField.getDDMFormFieldOptions()));
		copyDDMFormField.setLabel(translateLocalizedValue(ddmFormField.getLabel()));
		copyDDMFormField.setLocalizable(ddmFormField.isLocalizable());
		copyDDMFormField.setMultiple(ddmFormField.isMultiple());
		copyDDMFormField.setPredefinedValue(
			translateLocalizedValue(ddmFormField.getPredefinedValue()));
		copyDDMFormField.setReadOnly(ddmFormField.isReadOnly());
		copyDDMFormField.setRepeatable(ddmFormField.isRepeatable());
		copyDDMFormField.setRequired(ddmFormField.isRequired());
		copyDDMFormField.setShowLabel(ddmFormField.isShowLabel());
		copyDDMFormField.setStyle(translateLocalizedValue(ddmFormField.getStyle()));
		copyDDMFormField.setTip(translateLocalizedValue(ddmFormField.getTip()));
		copyDDMFormField.setVisibilityExpression(
			ddmFormField.getVisibilityExpression());

		for (DDMFormField nestedDDMFormField :
				ddmFormField.getNestedDDMFormFields()) {

			copyDDMFormField.addNestedDDMFormField(
				translate(nestedDDMFormField));
		}

		return copyDDMFormField;
	}

	public static com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues translate(
		DDMFormValues ddmFormValues) {

		if (ddmFormValues == null) {
			return null;
		}

		com.liferay.portlet.dynamicdatamapping.model.DDMForm copyDDMForm =
			translate(ddmFormValues.getDDMForm());

		com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues copyDDMFormValues =
			new com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues(
				copyDDMForm);

		copyDDMFormValues.setAvailableLocales(
			ddmFormValues.getAvailableLocales());
		copyDDMFormValues.setDefaultLocale(ddmFormValues.getDefaultLocale());

		for (DDMFormFieldValue ddmFormFieldValue :
				ddmFormValues.getDDMFormFieldValues()) {

			copyDDMFormValues.addDDMFormFieldValue(
				translate(ddmFormFieldValue));
		}

		return copyDDMFormValues;
	}

	protected static DDMFormFieldOptions translate(
		com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions ddmFormFieldOptions) {

		if (ddmFormFieldOptions == null) {
			return null;
		}

		DDMFormFieldOptions copyDDMFormFieldOptions = new DDMFormFieldOptions();

		copyDDMFormFieldOptions.setDefaultLocale(
			ddmFormFieldOptions.getDefaultLocale());

		Map<String, com.liferay.portlet.dynamicdatamapping.model.LocalizedValue>
			options = ddmFormFieldOptions.getOptions();

		for (String optionValue : options.keySet()) {
			com.liferay.portlet.dynamicdatamapping.model.LocalizedValue
				localizedValue = options.get(optionValue);

			for (Locale locale : localizedValue.getAvailableLocales()) {
				copyDDMFormFieldOptions.addOptionLabel(
					optionValue, locale, localizedValue.getString(locale));
			}
		}

		return copyDDMFormFieldOptions;
	}

	protected static Value translate(
		com.liferay.portlet.dynamicdatamapping.model.Value value) {

		if (value == null) {
			return null;
		}

		if (value.isLocalized()) {
			return translateLocalizedValue(value);
		}
		else {
			return translateUnlocalizedValue(value);
		}
	}

	protected static DDMFormFieldValue translate(
		com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue
			ddmFormFieldValue) {

		if (ddmFormFieldValue == null) {
			return null;
		}

		DDMFormFieldValue copyDDMFormFieldValue = new DDMFormFieldValue();

		copyDDMFormFieldValue.setInstanceId(ddmFormFieldValue.getInstanceId());
		copyDDMFormFieldValue.setName(ddmFormFieldValue.getName());
		copyDDMFormFieldValue.setValue(translate(ddmFormFieldValue.getValue()));

		for (com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue
			nestedFormFieldValue :
				ddmFormFieldValue.getNestedDDMFormFieldValues()) {

			copyDDMFormFieldValue.addNestedDDMFormFieldValue(
				translate(nestedFormFieldValue));
		}

		return copyDDMFormFieldValue;
	}

	protected static com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions translate(
		DDMFormFieldOptions ddmFormFieldOptions) {

		if (ddmFormFieldOptions == null) {
			return null;
		}

		com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions copyDDMFormFieldOptions =
			new com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions();

		copyDDMFormFieldOptions.setDefaultLocale(
			ddmFormFieldOptions.getDefaultLocale());

		Map<String, LocalizedValue> options = ddmFormFieldOptions.getOptions();

		for (String optionValue : options.keySet()) {
			LocalizedValue localizedValue = options.get(optionValue);

			for (Locale locale : localizedValue.getAvailableLocales()) {
				copyDDMFormFieldOptions.addOptionLabel(
					optionValue, locale, localizedValue.getString(locale));
			}
		}

		return copyDDMFormFieldOptions;
	}

	protected static com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue translate(
		DDMFormFieldValue ddmFormFieldValue) {

		if (ddmFormFieldValue == null) {
			return null;
		}

		com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue copyDDMFormFieldValue =
			new com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue();

		copyDDMFormFieldValue.setInstanceId(ddmFormFieldValue.getInstanceId());
		copyDDMFormFieldValue.setName(ddmFormFieldValue.getName());
		copyDDMFormFieldValue.setValue(translate(ddmFormFieldValue.getValue()));

		for (DDMFormFieldValue nestedFormFieldValue :
				ddmFormFieldValue.getNestedDDMFormFieldValues()) {

			copyDDMFormFieldValue.addNestedDDMFormFieldValue(
				translate(nestedFormFieldValue));
		}

		return copyDDMFormFieldValue;
	}

	protected static com.liferay.portlet.dynamicdatamapping.model.Value translate(Value value) {
		if (value == null) {
			return null;
		}

		if (value.isLocalized()) {
			return translateLocalizedValue(value);
		}
		else {
			return translateUnlocalizedValue(value);
		}
	}

	protected static LocalizedValue translateLocalizedValue(
		com.liferay.portlet.dynamicdatamapping.model.Value value) {

		if (value == null) {
			return null;
		}

		LocalizedValue localizedValue = new LocalizedValue();

		localizedValue.setDefaultLocale(value.getDefaultLocale());

		Map<Locale, String> values = value.getValues();

		for (Locale locale : values.keySet()) {
			localizedValue.addString(locale, values.get(locale));
		}

		return localizedValue;
	}

	protected static com.liferay.portlet.dynamicdatamapping.model.LocalizedValue translateLocalizedValue(Value value) {
		if (value == null) {
			return null;
		}

		com.liferay.portlet.dynamicdatamapping.model.LocalizedValue localizedValue =
			new com.liferay.portlet.dynamicdatamapping.model.LocalizedValue();

		localizedValue.setDefaultLocale(value.getDefaultLocale());

		Map<Locale, String> values = value.getValues();

		for (Locale locale : values.keySet()) {
			localizedValue.addString(locale, values.get(locale));
		}

		return localizedValue;
	}

	protected static UnlocalizedValue translateUnlocalizedValue(
		com.liferay.portlet.dynamicdatamapping.model.Value value) {

		if (value == null) {
			return null;
		}

		return new UnlocalizedValue(value.getString(value.getDefaultLocale()));
	}

	protected static com.liferay.portlet.dynamicdatamapping.model.UnlocalizedValue translateUnlocalizedValue(Value value) {
		if (value == null) {
			return null;
		}

		return new com.liferay.portlet.dynamicdatamapping.model.UnlocalizedValue(
			value.getString(value.getDefaultLocale()));
	}

}
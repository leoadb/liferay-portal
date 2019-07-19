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

package com.liferay.data.engine.rest.internal.dto.v1_0.util;

import com.liferay.data.engine.rest.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.spi.dto.SPIDataDefinitionField;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Method;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Leonardo Barros
 */
public class DataDefinitionFieldUtil {

	public static Object getPropertyValue(Locale locale, Object value) {
		if (Validator.isNull(value)) {
			return null;
		}

		if (_isLocalizableValue(value)) {
			return LanguageUtil.get(
				locale,
				StringUtil.extractLast(value.toString(), StringPool.PERCENT));
		}

		return value;
	}

	public static DataDefinitionField toDataDefinitionField(
		Locale locale, Method method) {

		com.liferay.data.engine.annotation.DataDefinitionField
			dataDefinitionField = method.getAnnotation(
				com.liferay.data.engine.annotation.DataDefinitionField.class);

		Map<String, Object> customProperties =
			_getDataDefinitionFieldProperties(dataDefinitionField);

		if (Objects.equals(
				_getDataDefinitionFieldType(dataDefinitionField, method),
				"fieldset")) {

			DataDefinition dataDefinitionFieldset =
				DataDefinitionUtil.toDataDefinition(
					_getReturnType(method), locale);

			customProperties.put(
				"nestedFields",
				dataDefinitionFieldset.getDataDefinitionFields());
		}

		return new DataDefinitionField() {
			{
				setCustomProperties(customProperties);
				setFieldType(
					_getDataDefinitionFieldType(dataDefinitionField, method));
				setLabel(
					_getDataDefinitionFieldLabel(dataDefinitionField, locale));
				setName(
					_getDataDefinitionFieldName(dataDefinitionField, method));
			}
		};
	}

	public static SPIDataDefinitionField toSPIDataDefinitionField(
		DataDefinitionField dataDefinitionField) {

		SPIDataDefinitionField spiDataDefinitionField =
			new SPIDataDefinitionField();

		spiDataDefinitionField.setCustomProperties(
			dataDefinitionField.getCustomProperties());
		spiDataDefinitionField.setDefaultValue(
			dataDefinitionField.getDefaultValue());
		spiDataDefinitionField.setFieldType(dataDefinitionField.getFieldType());
		spiDataDefinitionField.setId(dataDefinitionField.getId());
		spiDataDefinitionField.setIndexable(dataDefinitionField.getIndexable());
		spiDataDefinitionField.setLabel(dataDefinitionField.getLabel());
		spiDataDefinitionField.setLocalizable(
			dataDefinitionField.getLocalizable());
		spiDataDefinitionField.setName(dataDefinitionField.getName());
		spiDataDefinitionField.setRepeatable(
			dataDefinitionField.getRepeatable());
		spiDataDefinitionField.setTip(dataDefinitionField.getTip());

		return spiDataDefinitionField;
	}

	private static Map<String, Object> _getDataDefinitionFieldLabel(
		com.liferay.data.engine.annotation.DataDefinitionField
			dataDefinitionField,
		Locale locale) {

		if (Validator.isNotNull(dataDefinitionField.label())) {
			return Collections.emptyMap();
		}

		return new HashMap() {
			{
				put(
					LanguageUtil.getLanguageId(locale),
					LanguageUtil.get(locale, dataDefinitionField.label()));
			}
		};
	}

	private static String _getDataDefinitionFieldName(
		com.liferay.data.engine.annotation.DataDefinitionField
			dataDefinitionField,
		Method method) {

		if (Validator.isNotNull(dataDefinitionField.name())) {
			return dataDefinitionField.name();
		}

		return method.getName();
	}

	private static Map<String, Object> _getDataDefinitionFieldProperties(
		com.liferay.data.engine.annotation.DataDefinitionField
			dataDefinitionField) {

		return Stream.of(
			dataDefinitionField.properties()
		).collect(
			Collectors.toMap(
				property -> StringUtil.extractFirst(property, StringPool.EQUAL),
				property -> StringUtil.extractLast(property, StringPool.EQUAL))
		);
	}

	private static String _getDataDefinitionFieldType(
		com.liferay.data.engine.annotation.DataDefinitionField
			dataDefinitionField,
		Method method) {

		if (Validator.isNotNull(dataDefinitionField.fieldType())) {
			return dataDefinitionField.fieldType();
		}

		Class<?> clazz = _getReturnType(method);

		if (clazz.isAnnotationPresent(
				com.liferay.data.engine.annotation.DataDefinition.class)) {

			return "fieldset";
		}

		return "text";
	}

	private static Class<?> _getReturnType(Method method) {
		Class<?> clazz = method.getReturnType();

		if (clazz.isArray()) {
			clazz = clazz.getComponentType();
		}

		return clazz;
	}

	private static boolean _isLocalizableValue(Object value) {
		if (StringUtil.startsWith(
				GetterUtil.getString(value), StringPool.PERCENT)) {

			return true;
		}

		return false;
	}

}
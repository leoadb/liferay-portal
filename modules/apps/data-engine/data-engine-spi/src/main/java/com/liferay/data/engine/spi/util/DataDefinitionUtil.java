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

package com.liferay.data.engine.spi.util;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.spi.model.SPIDataDefinition;
import com.liferay.data.engine.spi.model.SPIDataDefinitionField;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidationExpression;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jeyvison Nascimento
 */
public class DataDefinitionUtil {

	public static DDMForm toDDMForm(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		SPIDataDefinition spiDataDefinition) {

		return new DDMForm() {
			{
				setAvailableLocales(
					_toLocales(spiDataDefinition.getAvailableLanguageIds()));
				setDDMFormFields(
					_toDDMFormFields(
						ddmFormFieldTypeServicesTracker,
						spiDataDefinition.getSPIDataDefinitionFields()));
				setDefaultLocale(
					LocaleUtil.fromLanguageId(
						spiDataDefinition.getDefaultLanguageId()));
			}
		};
	}

	public static SPIDataDefinition toSPIDataDefinition(
			DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
			DDMStructure ddmStructure)
		throws Exception {

		DDMForm ddmForm = ddmStructure.getDDMForm();

		return new SPIDataDefinition() {
			{
				setAvailableLanguageIds(
					_toLanguageIds(ddmForm.getAvailableLocales()));
				setDateCreated(ddmStructure.getCreateDate());
				setDataDefinitionKey(ddmStructure.getStructureKey());
				setDateModified(ddmStructure.getModifiedDate());
				setDefaultLanguageId(
					LanguageUtil.getLanguageId(ddmForm.getDefaultLocale()));
				setDescription(
					LocalizedValueUtil.toStringObjectMap(
						ddmStructure.getDescriptionMap()));
				setId(ddmStructure.getStructureId());
				setName(
					LocalizedValueUtil.toStringObjectMap(
						ddmStructure.getNameMap()));
				setSiteId(ddmStructure.getGroupId());
				setSPIDataDefinitionFields(
					_toSPIDataDefinitionFields(
						ddmForm.getDDMFormFields(),
						ddmFormFieldTypeServicesTracker));
				setSPIDefaultDataLayout(
					DataLayoutUtil.toSPIDataLayout(
						ddmStructure.fetchDDMStructureLayout()));
				setStorageType(ddmStructure.getStorageType());
				setUserId(ddmStructure.getUserId());
			}
		};
	}

	private static Map<String, Object> _getCustomProperties(
		DDMFormField ddmFormField,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		Map<String, DDMFormField> settingsDDMFormFieldsMap =
			_getSettingsDDMFormFields(
				ddmFormFieldTypeServicesTracker, ddmFormField.getType());

		Map<String, Object> properties = ddmFormField.getProperties();

		Map<String, Object> customProperties = new HashMap<>();

		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			if (ArrayUtil.contains(_PREDEFINED_PROPERTIES, entry.getKey())) {
				continue;
			}

			DDMFormField settingsDDMFormField = settingsDDMFormFieldsMap.get(
				entry.getKey());

			if (settingsDDMFormField == null) {
				continue;
			}

			if (settingsDDMFormField.isLocalizable()) {
				customProperties.put(
					entry.getKey(),
					LocalizedValueUtil.toLocalizedValuesMap(
						(LocalizedValue)entry.getValue()));
			}
			else if (Objects.equals(
						settingsDDMFormField.getDataType(), "boolean")) {

				customProperties.put(
					entry.getKey(), GetterUtil.getBoolean(entry.getValue()));
			}
			else if (Objects.equals(
						settingsDDMFormField.getDataType(), "ddm-options")) {

				customProperties.put(
					entry.getKey(),
					_toMap((DDMFormFieldOptions)entry.getValue()));
			}
			else if (Objects.equals(
						settingsDDMFormField.getType(), "validation")) {

				customProperties.put(
					entry.getKey(),
					_toMap((DDMFormFieldValidation)entry.getValue()));
			}
			else {
				customProperties.put(entry.getKey(), entry.getValue());
			}
		}

		return customProperties;
	}

	private static DDMFormFieldOptions _getDDMFormFieldOptions(
		Map<String, List<Map<String, String>>> options) {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		if (MapUtil.isEmpty(options)) {
			return ddmFormFieldOptions;
		}

		for (Map.Entry<String, List<Map<String, String>>> entry :
				options.entrySet()) {

			for (Map<String, String> option : entry.getValue()) {
				ddmFormFieldOptions.addOptionLabel(
					MapUtil.getString(option, "value"),
					LocaleUtil.fromLanguageId(entry.getKey()),
					MapUtil.getString(option, "label"));
			}
		}

		return ddmFormFieldOptions;
	}

	private static DDMFormFieldValidation _getDDMFormFieldValidation(
		Map<String, Object> value) {

		if (MapUtil.isEmpty(value)) {
			return null;
		}

		Map<String, String> expression = (Map<String, String>)value.get(
			"expression");

		if (Validator.isNull(MapUtil.getString(expression, "value"))) {
			return null;
		}

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		ddmFormFieldValidation.setDDMFormFieldValidationExpression(
			new DDMFormFieldValidationExpression() {
				{
					setName(MapUtil.getString(expression, "name"));
					setValue(MapUtil.getString(expression, "value"));
				}
			});
		ddmFormFieldValidation.setErrorMessageLocalizedValue(
			LocalizedValueUtil.toLocalizedValue(
				(Map<String, Object>)value.get("errorMessage")));
		ddmFormFieldValidation.setParameterLocalizedValue(
			LocalizedValueUtil.toLocalizedValue(
				(Map<String, Object>)value.get("parameter")));

		return ddmFormFieldValidation;
	}

	private static Map<String, DDMFormField> _getSettingsDDMFormFields(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		String type) {

		DDMFormFieldType ddmFormFieldType =
			ddmFormFieldTypeServicesTracker.getDDMFormFieldType(type);

		Class<? extends DDMFormFieldTypeSettings> ddmFormFieldTypeSettings =
			DefaultDDMFormFieldTypeSettings.class;

		if (ddmFormFieldType != null) {
			ddmFormFieldTypeSettings =
				ddmFormFieldType.getDDMFormFieldTypeSettings();
		}

		DDMForm ddmForm = DDMFormFactory.create(ddmFormFieldTypeSettings);

		return ddmForm.getDDMFormFieldsMap(true);
	}

	private static SPIDataDefinitionField _toDataDefinitionField(
		DDMFormField ddmFormField,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		return new SPIDataDefinitionField() {
			{
				setCustomProperties(
					_getCustomProperties(
						ddmFormField, ddmFormFieldTypeServicesTracker));
				setDefaultValue(
					LocalizedValueUtil.toLocalizedValuesMap(
						ddmFormField.getPredefinedValue()));
				setFieldType(ddmFormField.getType());
				setIndexable(Validator.isNotNull(ddmFormField.getIndexType()));
				setIndexType(
					SPIDataDefinitionField.IndexType.create(
						ddmFormField.getIndexType()));
				setLabel(
					LocalizedValueUtil.toLocalizedValuesMap(
						ddmFormField.getLabel()));
				setLocalizable(ddmFormField.isLocalizable());
				setName(ddmFormField.getName());
				setReadOnly(ddmFormField.isReadOnly());
				setRepeatable(ddmFormField.isRepeatable());
				setRequired(ddmFormField.isRequired());
				setShowLabel(ddmFormField.isShowLabel());
				setSPINestedDataDefinitionFields(
					_toSPIDataDefinitionFields(
						ddmFormField.getNestedDDMFormFields(),
						ddmFormFieldTypeServicesTracker));
				setTip(
					LocalizedValueUtil.toLocalizedValuesMap(
						ddmFormField.getTip()));
			}
		};
	}

	private static DDMFormField _toDDMFormField(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		SPIDataDefinitionField spiDataDefinitionField) {

		DDMFormField ddmFormField = new DDMFormField();

		ddmFormField.setIndexType(
			spiDataDefinitionField.getIndexTypeAsString());
		ddmFormField.setLabel(
			LocalizedValueUtil.toLocalizedValue(
				spiDataDefinitionField.getLabel()));
		ddmFormField.setLocalizable(
			GetterUtil.getBoolean(spiDataDefinitionField.getLocalizable()));
		ddmFormField.setName(spiDataDefinitionField.getName());
		ddmFormField.setNestedDDMFormFields(
			_toDDMFormFields(
				ddmFormFieldTypeServicesTracker,
				spiDataDefinitionField.getSPINestedDataDefinitionFields()));
		ddmFormField.setPredefinedValue(
			LocalizedValueUtil.toLocalizedValue(
				spiDataDefinitionField.getDefaultValue()));
		ddmFormField.setReadOnly(
			GetterUtil.getBoolean(spiDataDefinitionField.getReadOnly()));
		ddmFormField.setRepeatable(
			GetterUtil.getBoolean(spiDataDefinitionField.getRepeatable()));
		ddmFormField.setRequired(
			GetterUtil.getBoolean(spiDataDefinitionField.getRequired()));
		ddmFormField.setShowLabel(
			GetterUtil.getBoolean(spiDataDefinitionField.getShowLabel()));
		ddmFormField.setTip(
			LocalizedValueUtil.toLocalizedValue(
				spiDataDefinitionField.getTip()));
		ddmFormField.setType(spiDataDefinitionField.getFieldType());

		Map<String, Object> customProperties =
			spiDataDefinitionField.getCustomProperties();

		if (MapUtil.isNotEmpty(customProperties)) {
			Map<String, DDMFormField> settingsDDMFormFieldsMap =
				_getSettingsDDMFormFields(
					ddmFormFieldTypeServicesTracker,
					spiDataDefinitionField.getFieldType());

			for (Map.Entry<String, Object> entry :
					customProperties.entrySet()) {

				if (ArrayUtil.contains(
						_PREDEFINED_PROPERTIES, entry.getKey())) {

					continue;
				}

				DDMFormField settingsDDMFormField =
					settingsDDMFormFieldsMap.get(entry.getKey());

				if (settingsDDMFormField == null) {
					continue;
				}

				if (settingsDDMFormField.isLocalizable()) {
					ddmFormField.setProperty(
						entry.getKey(),
						LocalizedValueUtil.toLocalizedValue(
							(Map<String, Object>)entry.getValue()));
				}
				else if (Objects.equals(
							settingsDDMFormField.getDataType(), "boolean")) {

					ddmFormField.setProperty(
						entry.getKey(),
						GetterUtil.getBoolean(entry.getValue()));
				}
				else if (Objects.equals(
							settingsDDMFormField.getDataType(),
							"ddm-options")) {

					ddmFormField.setProperty(
						entry.getKey(),
						_getDDMFormFieldOptions(
							(Map<String, List<Map<String, String>>>)
								entry.getValue()));
				}
				else if (Objects.equals(
							settingsDDMFormField.getType(), "validation")) {

					ddmFormField.setProperty(
						entry.getKey(),
						_getDDMFormFieldValidation(
							(Map<String, Object>)entry.getValue()));
				}
				else {
					ddmFormField.setProperty(entry.getKey(), entry.getValue());
				}
			}
		}

		return ddmFormField;
	}

	private static List<DDMFormField> _toDDMFormFields(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		SPIDataDefinitionField[] spiDataDefinitionFields) {

		if (ArrayUtil.isEmpty(spiDataDefinitionFields)) {
			return Collections.emptyList();
		}

		return Stream.of(
			spiDataDefinitionFields
		).map(
			spiDataDefinitionField -> _toDDMFormField(
				ddmFormFieldTypeServicesTracker, spiDataDefinitionField)
		).collect(
			Collectors.toList()
		);
	}

	private static String[] _toLanguageIds(Set<Locale> locales) {
		Stream<Locale> stream = locales.stream();

		return stream.map(
			LanguageUtil::getLanguageId
		).collect(
			Collectors.toList()
		).toArray(
			new String[0]
		);
	}

	private static Set<Locale> _toLocales(String[] languageIds) {
		if (ArrayUtil.isEmpty(languageIds)) {
			return Collections.emptySet();
		}

		return Stream.of(
			languageIds
		).map(
			LocaleUtil::fromLanguageId
		).collect(
			Collectors.toSet()
		);
	}

	private static Map<String, List<Map<String, String>>> _toMap(
		DDMFormFieldOptions ddmFormFieldOptions) {

		Set<String> optionsValues = ddmFormFieldOptions.getOptionsValues();

		if (optionsValues.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<String, List<Map<String, String>>> options = new HashMap<>();

		for (String optionValue : optionsValues) {
			LocalizedValue localizedValue = ddmFormFieldOptions.getOptionLabels(
				optionValue);

			for (Locale locale : localizedValue.getAvailableLocales()) {
				String languageId = LanguageUtil.getLanguageId(locale);

				if (options.containsKey(languageId)) {
					List<Map<String, String>> values = options.get(languageId);

					values.add(
						HashMapBuilder.put(
							"label", localizedValue.getString(locale)
						).put(
							"value", optionValue
						).build());
				}
				else {
					options.put(
						languageId,
						ListUtil.toList(
							HashMapBuilder.put(
								"label", localizedValue.getString(locale)
							).put(
								"value", optionValue
							).build()));
				}
			}
		}

		return options;
	}

	private static Map<String, Object> _toMap(
		DDMFormFieldValidation ddmFormFieldValidation) {

		if (ddmFormFieldValidation == null) {
			return Collections.emptyMap();
		}

		return HashMapBuilder.<String, Object>put(
			"errorMessage",
			LocalizedValueUtil.toLocalizedValuesMap(
				ddmFormFieldValidation.getErrorMessageLocalizedValue())
		).put(
			"expression",
			_toMap(ddmFormFieldValidation.getDDMFormFieldValidationExpression())
		).put(
			"parameter",
			LocalizedValueUtil.toLocalizedValuesMap(
				ddmFormFieldValidation.getParameterLocalizedValue())
		).build();
	}

	private static Map<String, Object> _toMap(
		DDMFormFieldValidationExpression ddmFormFieldValidationExpression) {

		if (ddmFormFieldValidationExpression == null) {
			return Collections.emptyMap();
		}

		return HashMapBuilder.<String, Object>put(
			"name", ddmFormFieldValidationExpression.getName()
		).put(
			"value", ddmFormFieldValidationExpression.getValue()
		).build();
	}

	private static SPIDataDefinitionField[] _toSPIDataDefinitionFields(
		List<DDMFormField> ddmFormFields,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		if (ListUtil.isEmpty(ddmFormFields)) {
			return new SPIDataDefinitionField[0];
		}

		Stream<DDMFormField> stream = ddmFormFields.stream();

		return stream.map(
			ddmFormField -> _toDataDefinitionField(
				ddmFormField, ddmFormFieldTypeServicesTracker)
		).collect(
			Collectors.toList()
		).toArray(
			new SPIDataDefinitionField[0]
		);
	}

	private static final String[] _PREDEFINED_PROPERTIES = {
		"indexType", "label", "localizable", "name", "predefinedValue",
		"readOnly", "repeatable", "required", "showLabel", "tip", "type"
	};

}
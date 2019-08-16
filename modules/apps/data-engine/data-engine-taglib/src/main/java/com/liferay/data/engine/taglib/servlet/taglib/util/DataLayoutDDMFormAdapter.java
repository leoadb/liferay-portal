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

package com.liferay.data.engine.taglib.servlet.taglib.util;

import com.liferay.data.engine.rest.client.dto.v1_0.DataLayout;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerTracker;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Leonardo Barros
 */
public class DataLayoutDDMFormAdapter {

	public DataLayoutDDMFormAdapter(
		Set<Locale> availableLocales, DataLayout dataLayout,
		DDMFormDeserializerTracker ddmFormDeserializerTracker,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		DDMFormLayoutDeserializerTracker ddmFormLayoutDeserializerTracker,
		DDMFormTemplateContextFactory ddmFormTemplateContextFactory,
		DDMStructureLayoutLocalService ddmStructureLayoutLocalService,
		DDMStructureLocalService ddmStructureLocalService,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, JSONFactory jsonFactory) {

		_availableLocales = availableLocales;
		_dataLayout = dataLayout;
		_ddmFormDeserializerTracker = ddmFormDeserializerTracker;
		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
		_ddmFormLayoutDeserializerTracker = ddmFormLayoutDeserializerTracker;
		_ddmFormTemplateContextFactory = ddmFormTemplateContextFactory;
		_ddmStructureLayoutLocalService = ddmStructureLayoutLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;
		_jsonFactory = jsonFactory;
	}

	public JSONObject toJSONObject() throws Exception {
		if (_dataLayout.getId() == null) {
			return _jsonFactory.createJSONObject();
		}

		Map<String, Object> ddmFormContext = _createDDMFormContext(
			_getDDMForm(), _getDDMFormLayout());

		return _jsonFactory.createJSONObject(
			_jsonFactory.looseSerializeDeep(ddmFormContext));
	}

	private Map<String, Object> _createDDMFormContext(
			DDMForm ddmForm, DDMFormLayout ddmFormLayout)
		throws Exception {

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setHttpServletRequest(_httpServletRequest);
		ddmFormRenderingContext.setHttpServletResponse(_httpServletResponse);
		ddmFormRenderingContext.setLocale(_httpServletRequest.getLocale());
		ddmFormRenderingContext.setPortletNamespace(StringPool.BLANK);

		Map<String, Object> ddmFormTemplateContext =
			_ddmFormTemplateContextFactory.create(
				ddmForm, ddmFormLayout, ddmFormRenderingContext);

		_populateDDMFormFieldSettingsContext(
			ddmForm.getDDMFormFieldsMap(true), ddmFormTemplateContext);

		return ddmFormTemplateContext;
	}

	private Map<String, Object> _createDDMFormFieldSettingContext(
			DDMFormField ddmFormField)
		throws PortalException {

		DDMFormFieldType ddmFormFieldType =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldType(
				ddmFormField.getType());

		DDMForm ddmForm = DDMFormFactory.create(
			ddmFormFieldType.getDDMFormFieldTypeSettings());

		DDMFormLayout ddmFormLayout = DDMFormLayoutFactory.create(
			ddmFormFieldType.getDDMFormFieldTypeSettings());

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setContainerId("settings");
		ddmFormRenderingContext.setHttpServletRequest(_httpServletRequest);
		ddmFormRenderingContext.setHttpServletResponse(_httpServletResponse);
		ddmFormRenderingContext.setLocale(_httpServletRequest.getLocale());
		ddmFormRenderingContext.setPortletNamespace(StringPool.BLANK);

		DDMFormValues ddmFormValues =
			_createDDMFormFieldSettingContextDDMFormValues(
				ddmFormField, ddmForm);

		ddmFormRenderingContext.setDDMFormValues(ddmFormValues);

		return _ddmFormTemplateContextFactory.create(
			ddmForm, ddmFormLayout, ddmFormRenderingContext);
	}

	private DDMFormValues _createDDMFormFieldSettingContextDDMFormValues(
		DDMFormField ddmFormField, DDMForm ddmFormFieldTypeSettingsDDMForm) {

		Map<String, Object> ddmFormFieldProperties =
			ddmFormField.getProperties();

		DDMFormValues ddmFormValues = new DDMFormValues(
			ddmFormFieldTypeSettingsDDMForm);

		for (DDMFormField ddmFormFieldTypeSetting :
				ddmFormFieldTypeSettingsDDMForm.getDDMFormFields()) {

			DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

			String propertyName = ddmFormFieldTypeSetting.getName();

			ddmFormFieldValue.setName(propertyName);

			DDMForm ddmForm = ddmFormField.getDDMForm();

			Value value = _createDDMFormFieldValue(
				ddmForm.getAvailableLocales(), ddmFormFieldTypeSetting,
				ddmFormFieldProperties.get(propertyName));

			ddmFormFieldValue.setValue(value);

			ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
		}

		return ddmFormValues;
	}

	private Value _createDDMFormFieldValue(
		DDMFormFieldValidation ddmFormFieldValidation) {

		if (ddmFormFieldValidation == null) {
			return new UnlocalizedValue(StringPool.BLANK);
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put(
			"errorMessage", ddmFormFieldValidation.getErrorMessage()
		).put(
			"expression", ddmFormFieldValidation.getExpression()
		);

		return new UnlocalizedValue(jsonObject.toString());
	}

	private Value _createDDMFormFieldValue(
		Set<Locale> availableLocales, DDMFormField ddmFormFieldTypeSetting,
		Object propertyValue) {

		if (ddmFormFieldTypeSetting.isLocalizable()) {
			return (LocalizedValue)propertyValue;
		}

		if (Objects.equals(
				ddmFormFieldTypeSetting.getDataType(), "ddm-options")) {

			return _createDDMFormFieldValue(
				availableLocales, (DDMFormFieldOptions)propertyValue);
		}
		else if (Objects.equals(
					ddmFormFieldTypeSetting.getType(), "validation")) {

			return _createDDMFormFieldValue(
				(DDMFormFieldValidation)propertyValue);
		}

		return new UnlocalizedValue(String.valueOf(propertyValue));
	}

	private Value _createDDMFormFieldValue(
		Set<Locale> availableLocales, DDMFormFieldOptions ddmFormFieldOptions) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		for (Locale availableLocale : availableLocales) {
			jsonObject.put(
				LocaleUtil.toLanguageId(availableLocale),
				_createOptions(ddmFormFieldOptions, availableLocale));
		}

		return new UnlocalizedValue(jsonObject.toString());
	}

	private JSONArray _createOptions(
		DDMFormFieldOptions ddmFormFieldOptions, Locale locale) {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (String optionValue : ddmFormFieldOptions.getOptionsValues()) {
			JSONObject jsonObject = _jsonFactory.createJSONObject();

			LocalizedValue label = ddmFormFieldOptions.getOptionLabels(
				optionValue);

			jsonObject.put(
				"label", label.getString(locale)
			).put(
				"value", optionValue
			);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private JSONArray _createOptionsJSONArray(Map<String, JSONObject> options) {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (Map.Entry<String, JSONObject> entry : options.entrySet()) {
			JSONObject jsonObject = _jsonFactory.createJSONObject();

			jsonObject.put(
				"label", entry.getValue()
			).put(
				"value", entry.getKey()
			);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private DDMForm _deserializeDDMForm(String content) {
		DDMFormDeserializer ddmFormDeserializer =
			_ddmFormDeserializerTracker.getDDMFormDeserializer("json");

		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(content);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				ddmFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	private DDMFormLayout _deserializeDDMFormLayout(String content) {
		DDMFormLayoutDeserializer ddmFormLayoutDeserializer =
			_ddmFormLayoutDeserializerTracker.getDDMFormLayoutDeserializer(
				"json");

		DDMFormLayoutDeserializerDeserializeRequest.Builder builder =
			DDMFormLayoutDeserializerDeserializeRequest.Builder.newBuilder(
				content);

		DDMFormLayoutDeserializerDeserializeResponse
			ddmFormLayoutDeserializerDeserializeResponse =
				ddmFormLayoutDeserializer.deserialize(builder.build());

		return ddmFormLayoutDeserializerDeserializeResponse.getDDMFormLayout();
	}

	private DDMForm _getDDMForm() throws Exception {
		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			_dataLayout.getDataDefinitionId());

		DDMStructureVersion ddmStructureVersion =
			ddmStructure.getLatestStructureVersion();

		String dataDefinitionJSON = ddmStructureVersion.getDefinition();

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			dataDefinitionJSON.replace("defaultValue", "predefinedValue"));

		jsonObject = jsonObject.put(
			"availableLanguageIds", _getLanguageIdJSONArray(_availableLocales)
		).put(
			"defaultLanguageId", ddmStructure.getDefaultLanguageId()
		);

		_transformOptions(jsonObject, "columns");
		_transformOptions(jsonObject, "options");
		_transformOptions(jsonObject, "rows");

		return _deserializeDDMForm(jsonObject.toJSONString());
	}

	private DDMFormLayout _getDDMFormLayout() throws Exception {
		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.getStructureLayout(
				_dataLayout.getId());

		String dataLayoutJSON = ddmStructureLayout.getDefinition();

		dataLayoutJSON = dataLayoutJSON.replace("columnSize", "size");
		dataLayoutJSON = dataLayoutJSON.replace("dataLayoutColumns", "columns");
		dataLayoutJSON = dataLayoutJSON.replace("dataLayoutPages", "pages");
		dataLayoutJSON = dataLayoutJSON.replace("dataLayoutRows", "rows");

		JSONObject jsonObject = _jsonFactory.createJSONObject(dataLayoutJSON);

		return _deserializeDDMFormLayout(jsonObject.toJSONString());
	}

	private JSONArray _getLanguageIdJSONArray(Set<Locale> availableLocales) {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		Stream<Locale> stream = availableLocales.stream();

		stream.map(
			LanguageUtil::getLanguageId
		).forEach(
			jsonArray::put
		);

		return jsonArray;
	}

	private void _populateDDMFormFieldSettingsContext(
		Map<String, DDMFormField> ddmFormFieldsMap,
		Map<String, Object> ddmFormTemplateContext) {

		DDMFormBuilderContextFieldVisitor ddmFormBuilderContextFieldVisitor =
			new DDMFormBuilderContextFieldVisitor(
				ddmFormTemplateContext,
				fieldContext -> {
					String fieldName = MapUtil.getString(
						fieldContext, "fieldName");

					try {
						fieldContext.put(
							"settingsContext",
							_createDDMFormFieldSettingContext(
								ddmFormFieldsMap.get(fieldName)));
					}
					catch (PortalException pe) {
						_log.error(
							"Unable to create field settings context", pe);
					}
				});

		ddmFormBuilderContextFieldVisitor.visit();
	}

	private void _transformOptions(JSONObject jsonObject, String key) {
		JSONArray jsonArray = jsonObject.getJSONArray("fields");

		if (jsonArray == null) {
			return;
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject fieldJSONObject = jsonArray.getJSONObject(i);

			if (fieldJSONObject.has(key)) {
				JSONObject optionsJSONObject = fieldJSONObject.getJSONObject(
					key);

				Iterator<String> keys = optionsJSONObject.keys();

				Map<String, JSONObject> options = new TreeMap<>();

				while (keys.hasNext()) {
					String languageId = keys.next();

					JSONArray localizedOptionsJSONArray =
						optionsJSONObject.getJSONArray(languageId);

					for (int j = 0; j < localizedOptionsJSONArray.length();
						 j++) {

						JSONObject localizedOptionJSONObject =
							localizedOptionsJSONArray.getJSONObject(j);

						JSONObject optionLabelsJSONObject =
							options.getOrDefault(
								localizedOptionJSONObject.getString("value"),
								_jsonFactory.createJSONObject());

						optionLabelsJSONObject.put(
							languageId,
							localizedOptionJSONObject.getString("label"));

						options.putIfAbsent(
							localizedOptionJSONObject.getString("value"),
							optionLabelsJSONObject);
					}
				}

				fieldJSONObject.put(key, _createOptionsJSONArray(options));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataLayoutDDMFormAdapter.class);

	private final Set<Locale> _availableLocales;
	private final DataLayout _dataLayout;
	private final DDMFormDeserializerTracker _ddmFormDeserializerTracker;
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;
	private final DDMFormLayoutDeserializerTracker
		_ddmFormLayoutDeserializerTracker;
	private final DDMFormTemplateContextFactory _ddmFormTemplateContextFactory;
	private final DDMStructureLayoutLocalService
		_ddmStructureLayoutLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private final JSONFactory _jsonFactory;

	private static class DDMFormBuilderContextFieldVisitor {

		public DDMFormBuilderContextFieldVisitor(
			Map<String, Object> ddmFormBuilderContext,
			Consumer<Map<String, Object>> fieldConsumer) {

			_ddmFormBuilderContext = ddmFormBuilderContext;
			_fieldConsumer = fieldConsumer;
		}

		public void visit() {
			_traversePages(
				(List<Map<String, Object>>)_ddmFormBuilderContext.get("pages"));
		}

		private void _traverseColumns(List<Map<String, Object>> columns) {
			for (Map<String, Object> column : columns) {
				_traverseFields(
					(List<Map<String, Object>>)column.get("fields"));
			}
		}

		private void _traverseFields(List<Map<String, Object>> fields) {
			for (Map<String, Object> field : fields) {
				_fieldConsumer.accept(field);
			}
		}

		private void _traversePages(List<Map<String, Object>> pages) {
			for (Map<String, Object> page : pages) {
				_traverseRows((List<Map<String, Object>>)page.get("rows"));
			}
		}

		private void _traverseRows(List<Map<String, Object>> rows) {
			for (Map<String, Object> row : rows) {
				_traverseColumns((List<Map<String, Object>>)row.get("columns"));
			}
		}

		private final Map<String, Object> _ddmFormBuilderContext;
		private final Consumer<Map<String, Object>> _fieldConsumer;

	}

}
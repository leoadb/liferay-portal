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

import com.liferay.data.engine.field.type.FieldType;
import com.liferay.data.engine.field.type.FieldTypeTracker;
import com.liferay.data.engine.renderer.DataLayoutRenderer;
import com.liferay.data.engine.renderer.DataLayoutRendererContext;
import com.liferay.data.engine.rest.client.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.client.dto.v1_0.DataLayout;
import com.liferay.data.engine.rest.client.dto.v1_0.DataRecord;
import com.liferay.data.engine.rest.client.resource.v1_0.DataDefinitionResource;
import com.liferay.data.engine.rest.client.resource.v1_0.DataLayoutResource;
import com.liferay.data.engine.rest.client.resource.v1_0.DataRecordResource;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormBuilderContextFactory;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerTracker;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutFactory;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DataLayoutTaglibUtil.class)
public class DataLayoutTaglibUtil {

	public static Set<Locale> getAvailableLocales(
		long dataLayoutId, HttpServletRequest httpServletRequest) {

		return _instance._getAvailableLocales(dataLayoutId, httpServletRequest);
	}

	public static JSONObject getDataLayoutJSONObject(
		Set<Locale> availableLocales, Long dataLayoutId,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		return _instance._getDataLayoutJSONObject(
			availableLocales, dataLayoutId, httpServletRequest,
			httpServletResponse);
	}

	public static Map<String, Object> getDataRecordValues(
			long dataRecordId, HttpServletRequest httpServletRequest)
		throws Exception {

		return _instance._getDataRecordValues(dataRecordId, httpServletRequest);
	}

	public static JSONArray getFieldTypesJSONArray(
		HttpServletRequest httpServletRequest) {

		return _instance._getFieldTypesJSONArray(httpServletRequest);
	}

	public static String renderDataLayout(
			long dataLayoutId,
			DataLayoutRendererContext dataLayoutRendererContext)
		throws Exception {

		return _instance._dataLayoutRenderer.render(
			dataLayoutId, dataLayoutRendererContext);
	}

	public static String resolveFieldTypesModules() {
		return _instance._resolveFieldTypesModules();
	}

	public static String resolveModule(String moduleName) {
		return _instance._npmResolver.resolveModuleName(moduleName);
	}

	@Activate
	protected void activate() {
		_instance = this;
	}

	@Deactivate
	protected void deactivate() {
		_instance = null;
	}

	private JSONObject _createFieldContext(
		HttpServletRequest httpServletRequest, Locale locale, String type) {

		try {
			Class<?> ddmFormFieldTypeSettings = _getDDMFormFieldTypeSettings(
				type);

			DDMForm ddmFormFieldTypeSettingsDDMForm = DDMFormFactory.create(
				ddmFormFieldTypeSettings);

			DDMFormRenderingContext ddmFormRenderingContext =
				new DDMFormRenderingContext();

			ddmFormRenderingContext.setContainerId("settings");

			DDMFormValues ddmFormValues = _ddmFormValuesFactory.create(
				httpServletRequest, ddmFormFieldTypeSettingsDDMForm);

			_setTypeDDMFormFieldValue(ddmFormValues, type);

			ddmFormRenderingContext.setDDMFormValues(ddmFormValues);

			ddmFormRenderingContext.setHttpServletRequest(httpServletRequest);
			ddmFormRenderingContext.setLocale(locale);
			ddmFormRenderingContext.setPortletNamespace(
				ParamUtil.getString(httpServletRequest, "portletNamespace"));
			ddmFormRenderingContext.setReturnFullContext(true);

			return _jsonFactory.createJSONObject(
				_jsonFactory.looseSerializeDeep(
					_ddmFormTemplateContextFactory.create(
						ddmFormFieldTypeSettingsDDMForm,
						DDMFormLayoutFactory.create(ddmFormFieldTypeSettings),
						ddmFormRenderingContext)));
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return null;
	}

	private Set<Locale> _getAvailableLocales(
		long dataLayoutId, HttpServletRequest httpServletRequest) {

		if (dataLayoutId == 0) {
			return new HashSet() {
				{
					add(LocaleThreadLocal.getDefaultLocale());
				}
			};
		}

		try {
			DataLayout dataLayout = _getDataLayout(
				dataLayoutId, httpServletRequest);

			DataDefinition dataDefinition = _getDataDefinition(
				dataLayout.getDataDefinitionId(), httpServletRequest);

			return Stream.of(
				dataDefinition.getAvailableLanguageIds()
			).map(
				LocaleUtil::fromLanguageId
			).collect(
				Collectors.toSet()
			);
		}
		catch (Exception e) {
			return new HashSet() {
				{
					add(LocaleThreadLocal.getDefaultLocale());
				}
			};
		}
	}

	private DataDefinition _getDataDefinition(
			long dataDefinitionId, HttpServletRequest httpServletRequest)
		throws Exception {

		String cookie = CookieKeys.getCookie(
			httpServletRequest, CookieKeys.JSESSIONID);

		DataDefinitionResource dataDefinitionResource =
			DataDefinitionResource.builder(
			).header(
				"Cookie", "JSESSIONID=" + cookie
			).parameter(
				"p_auth", AuthTokenUtil.getToken(httpServletRequest)
			).endpoint(
				_portal.getHost(httpServletRequest),
				httpServletRequest.getServerPort(),
				httpServletRequest.getScheme()
			).build();

		return dataDefinitionResource.getDataDefinition(dataDefinitionId);
	}

	private DataLayout _getDataLayout(
			long dataLayoutId, HttpServletRequest httpServletRequest)
		throws Exception {

		DataLayoutResource dataLayoutResource = DataLayoutResource.builder(
		).header(
			"Cookie",
			"JSESSIONID=" +
				CookieKeys.getCookie(httpServletRequest, CookieKeys.JSESSIONID)
		).parameter(
			"p_auth", AuthTokenUtil.getToken(httpServletRequest)
		).endpoint(
			_portal.getHost(httpServletRequest),
			httpServletRequest.getServerPort(), httpServletRequest.getScheme()
		).build();

		return dataLayoutResource.getDataLayout(dataLayoutId);
	}

	private JSONObject _getDataLayoutJSONObject(
		Set<Locale> availableLocales, Long dataLayoutId,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		if (dataLayoutId == null) {
			return _jsonFactory.createJSONObject();
		}

		try {
			DataLayout dataLayout = _getDataLayout(
				dataLayoutId, httpServletRequest);

			DataLayoutDDMFormAdapter dataLayoutDDMFormAdapter =
				new DataLayoutDDMFormAdapter(
					availableLocales, dataLayout, _ddmFormDeserializerTracker,
					_ddmFormFieldTypeServicesTracker,
					_ddmFormLayoutDeserializerTracker,
					_ddmFormTemplateContextFactory,
					_ddmStructureLayoutLocalService, _ddmStructureLocalService,
					httpServletRequest, httpServletResponse, _jsonFactory);

			return dataLayoutDDMFormAdapter.toJSONObject();
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			return _jsonFactory.createJSONObject();
		}
	}

	private Map<String, Object> _getDataRecordValues(
			long dataRecordId, HttpServletRequest httpServletRequest)
		throws Exception {

		if (dataRecordId == 0) {
			return Collections.emptyMap();
		}

		DataRecordResource dataRecordResource = DataRecordResource.builder(
		).header(
			"Cookie",
			"JSESSIONID=" +
				CookieKeys.getCookie(httpServletRequest, CookieKeys.JSESSIONID)
		).parameter(
			"p_auth", AuthTokenUtil.getToken(httpServletRequest)
		).endpoint(
			_portal.getHost(httpServletRequest),
			httpServletRequest.getServerPort(), httpServletRequest.getScheme()
		).build();

		DataRecord dataRecord = dataRecordResource.getDataRecord(dataRecordId);

		return dataRecord.getDataRecordValues();
	}

	private Class<?> _getDDMFormFieldTypeSettings(String type) {
		DDMFormFieldType ddmFormFieldType =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldType(type);

		return ddmFormFieldType.getDDMFormFieldTypeSettings();
	}

	private JSONObject _getFieldTypeMetadataJSONObject(
		FieldType fieldType, HttpServletRequest httpServletRequest) {

		Map<String, Object> fieldTypeProperties =
			_fieldTypeTracker.getFieldTypeProperties(fieldType.getName());

		return JSONUtil.put(
			"description",
			_getLanguageTerm(
				MapUtil.getString(
					fieldTypeProperties, "data.engine.field.type.description"),
				LocaleThreadLocal.getThemeDisplayLocale())
		).put(
			"group",
			MapUtil.getString(
				fieldTypeProperties, "data.engine.field.type.group")
		).put(
			"icon",
			MapUtil.getString(
				fieldTypeProperties, "data.engine.field.type.icon")
		).put(
			"javaScriptModule",
			_getJavaScriptModule(
				MapUtil.getString(
					fieldTypeProperties, "data.engine.field.type.js.module"))
		).put(
			"label",
			_getLanguageTerm(
				MapUtil.getString(
					fieldTypeProperties, "data.engine.field.type.label"),
				LocaleThreadLocal.getThemeDisplayLocale())
		).put(
			"name", fieldType.getName()
		).put(
			"settingsContext",
			_createFieldContext(
				httpServletRequest, LocaleThreadLocal.getThemeDisplayLocale(),
				fieldType.getName())
		);
	}

	private JSONArray _getFieldTypesJSONArray(
		HttpServletRequest httpServletRequest) {

		Collection<FieldType> fieldTypes = _fieldTypeTracker.getFieldTypes();

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		Stream<FieldType> stream = fieldTypes.stream();

		stream.map(
			fieldType -> _instance._getFieldTypeMetadataJSONObject(
				fieldType, httpServletRequest)
		).forEach(
			jsonArray::put
		);

		return jsonArray;
	}

	private String _getJavaScriptModule(String moduleName) {
		if (Validator.isNull(moduleName)) {
			return StringPool.BLANK;
		}

		return _npmResolver.resolveModuleName(moduleName);
	}

	private String _getLanguageTerm(String key, Locale locale) {
		if (Validator.isNull(key)) {
			return StringPool.BLANK;
		}

		return GetterUtil.getString(
			ResourceBundleUtil.getString(_getResourceBundle(locale), key), key);
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return new AggregateResourceBundle(
			ResourceBundleUtil.getBundle(
				"content.Language", locale, getClass()),
			_portal.getResourceBundle(locale));
	}

	private boolean _hasJavascriptModule(FieldType fieldType) {
		Map<String, Object> fieldTypeProperties =
			_fieldTypeTracker.getFieldTypeProperties(fieldType.getName());

		return fieldTypeProperties.containsKey(
			"data.engine.field.type.js.module");
	}

	private String _resolveFieldTypeModule(FieldType fieldType) {
		return _getJavaScriptModule(
			MapUtil.getString(
				_fieldTypeTracker.getFieldTypeProperties(fieldType.getName()),
				"data.engine.field.type.js.module"));
	}

	private String _resolveFieldTypesModules() {
		Collection<FieldType> fieldTypes = _fieldTypeTracker.getFieldTypes();

		Stream<FieldType> stream = fieldTypes.stream();

		return stream.filter(
			_instance::_hasJavascriptModule
		).map(
			_instance::_resolveFieldTypeModule
		).collect(
			Collectors.joining(StringPool.COMMA)
		);
	}

	private void _setTypeDDMFormFieldValue(
		DDMFormValues ddmFormValues, String type) {

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			"type");

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		ddmFormFieldValue.setValue(new UnlocalizedValue(type));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataLayoutTaglibUtil.class);

	private static DataLayoutTaglibUtil _instance;

	@Reference
	private DataLayoutRenderer _dataLayoutRenderer;

	@Reference
	private DDMFormBuilderContextFactory _ddmFormBuilderContextFactory;

	@Reference
	private DDMFormDeserializerTracker _ddmFormDeserializerTracker;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private DDMFormLayoutDeserializerTracker _ddmFormLayoutDeserializerTracker;

	@Reference
	private DDMFormTemplateContextFactory _ddmFormTemplateContextFactory;

	@Reference
	private DDMFormValuesFactory _ddmFormValuesFactory;

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private FieldTypeTracker _fieldTypeTracker;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private NPMResolver _npmResolver;

	@Reference
	private Portal _portal;

}
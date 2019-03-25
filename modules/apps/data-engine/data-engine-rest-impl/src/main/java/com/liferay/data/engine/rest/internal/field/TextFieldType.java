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

package com.liferay.data.engine.rest.internal.field;

import com.liferay.data.engine.exception.DEDataDefinitionDeserializerException;
import com.liferay.data.engine.exception.DEDataDefinitionSerializerException;
import com.liferay.data.engine.field.FieldType;
import com.liferay.data.engine.field.FieldTypeContextContributor;
import com.liferay.data.engine.field.FieldTypeIO;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.util.DataEngineUtil;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "de.data.definition.field.type=text",
	service = {
		FieldType.class, FieldTypeContextContributor.class, FieldTypeIO.class
	}
)
public class TextFieldType
	implements FieldType, FieldTypeContextContributor, FieldTypeIO {

	@Override
	public DataDefinitionField deserialize(JSONObject jsonObject)
		throws DEDataDefinitionDeserializerException {

		DataDefinitionField dataDefinitionField = FieldTypeIO.super.deserialize(
			jsonObject);

		Map<String, Object> customProperties = new HashMap<>();

		dataDefinitionField.setCustomProperties(customProperties);

		if (jsonObject.has("displayStyle")) {
			customProperties.put(
				"displayStyle", jsonObject.getString("displayStyle"));
		}

		if (jsonObject.has("placeholder")) {
			customProperties.put(
				"placeholder",
				DataEngineUtil.getLocalizedValues("placeholder", jsonObject));
		}

		if (jsonObject.has("predefinedValue")) {
			customProperties.put(
				"predefinedValue", jsonObject.getString("predefinedValue"));
		}

		if (jsonObject.has("tooltip")) {
			customProperties.put(
				"tooltip",
				DataEngineUtil.getLocalizedValues("tooltip", jsonObject));
		}

		return dataDefinitionField;
	}

	@Override
	public String getDescription(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "text-field-type-description");
	}

	@Override
	public String getGroup() {
		return "basic";
	}

	@Override
	public String getIcon() {
		return "text";
	}

	@Override
	public String getJavaScriptModule() {
		return npmResolver.resolveModuleName(
			"dynamic-data-mapping-form-field-type/metal/Text/Text.es");
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "text-field-type-label");
	}

	@Override
	public String getName() {
		return "text";
	}

	@Override
	public void includeContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Map<String, Object> context,
		DataDefinitionField dataDefinitionField, boolean readOnly) {

		FieldTypeContextContributor.super.includeContext(
			httpServletRequest, httpServletResponse, context,
			dataDefinitionField, readOnly);

		String languageId = LanguageUtil.getLanguageId(httpServletRequest);

		Map<String, ?> customProperties =
			dataDefinitionField.getCustomProperties();

		if (customProperties.containsKey("displayStyle")) {
			context.put("displayStyle", customProperties.get("displayStyle"));
		}

		if (customProperties.containsKey("placeholder")) {
			context.put(
				"placeholder",
				DataEngineUtil.getLocalizedValue(
					dataDefinitionField, "placeholder", languageId));
		}

		if (customProperties.containsKey("predefinedValue")) {
			context.put(
				"predefinedValue", customProperties.get("predefinedValue"));
		}

		if (customProperties.containsKey("tooltip")) {
			context.put(
				"tooltip",
				DataEngineUtil.getLocalizedValue(
					dataDefinitionField, "tooltip", languageId));
		}

		if (customProperties.containsKey("value")) {
			context.put("value", customProperties.get("value"));
		}
	}

	@Override
	public JSONObject serialize(
			DataDefinitionField dataDefinitionField, JSONFactory jsonFactory)
		throws DEDataDefinitionSerializerException {

		JSONObject jsonObject = FieldTypeIO.super.serialize(
			dataDefinitionField, jsonFactory);

		Map<String, ?> customProperties =
			dataDefinitionField.getCustomProperties();

		if (customProperties.containsKey("displayStyle")) {
			jsonObject.put(
				"displayStyle", customProperties.get("displayStyle"));
		}

		if (customProperties.containsKey("placeholder")) {
			Map<String, String> placeholder =
				(Map<String, String>)customProperties.get("placeholder");

			DataEngineUtil.setLocalizedProperty(
				"placeholder", jsonFactory, jsonObject, placeholder);
		}

		if (customProperties.containsKey("predefinedValue")) {
			jsonObject.put(
				"predefinedValue", customProperties.get("predefinedValue"));
		}

		if (customProperties.containsKey("tooltip")) {
			Map<String, String> tooltip =
				(Map<String, String>)customProperties.get("tooltip");

			DataEngineUtil.setLocalizedProperty(
				"tooltip", jsonFactory, jsonObject, tooltip);
		}

		return jsonObject;
	}


	@Reference
	protected NPMResolver npmResolver;
}
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

package com.liferay.data.engine.internal.field;

import com.liferay.data.engine.exception.DEDataDefinitionDeserializerException;
import com.liferay.data.engine.exception.DEDataDefinitionSerializerException;
import com.liferay.data.engine.field.DEFieldType;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "de.data.definition.field.type=checkbox",
	service = DEFieldType.class
)
public class DECheckboxFieldType implements DEFieldType {

	@Override
	public DEDataDefinitionField deserialize(JSONObject jsonObject)
		throws DEDataDefinitionDeserializerException {

		DEDataDefinitionField deDataDefinitionField =
			DEFieldType.super.deserialize(jsonObject);

		if (jsonObject.has("predefinedValue")) {
			deDataDefinitionField.setCustomProperty(
				"predefinedValue", jsonObject.getBoolean("predefinedValue"));
		}

		if (jsonObject.has("showAsSwitcher")) {
			deDataDefinitionField.setCustomProperty(
				"showAsSwitcher", jsonObject.getBoolean("showAsSwitcher"));
		}

		return deDataDefinitionField;
	}

	@Override
	public void includeContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Map<String, Object> context,
		DEDataDefinitionField deDataDefinitionField, boolean readOnly) {

		DEFieldType.super.includeContext(
			httpServletRequest, httpServletResponse, context,
			deDataDefinitionField, readOnly);

		if (deDataDefinitionField.hasCustomProperty("predefinedValue")) {
			context.put(
				"predefinedValue",
				GetterUtil.getBoolean(
					deDataDefinitionField.getCustomProperty(
						"predefinedValue")));
		}

		if (deDataDefinitionField.hasCustomProperty("showAsSwitcher")) {
			context.put(
				"showAsSwitcher",
				GetterUtil.getBoolean(
					deDataDefinitionField.getCustomProperty("showAsSwitcher")));
		}

		if (deDataDefinitionField.hasCustomProperty("value")) {
			context.put(
				"value",
				GetterUtil.getBoolean(
					deDataDefinitionField.getCustomProperty("value")));
		}
	}

	@Override
	public JSONObject serialize(
			DEDataDefinitionField deDataDefinitionField,
			JSONFactory jsonFactory)
		throws DEDataDefinitionSerializerException {

		JSONObject jsonObject = DEFieldType.super.serialize(
			deDataDefinitionField, jsonFactory);

		if (deDataDefinitionField.hasCustomProperty("showAsSwitcher")) {
			jsonObject.put(
				"showAsSwitcher",
				GetterUtil.getBoolean(
					deDataDefinitionField.getCustomProperty("showAsSwitcher")));
		}

		if (deDataDefinitionField.hasCustomProperty("predefinedValue")) {
			jsonObject.put(
				"predefinedValue",
				GetterUtil.getBoolean(
					deDataDefinitionField.getCustomProperty(
						"predefinedValue")));
		}

		return jsonObject;
	}

}
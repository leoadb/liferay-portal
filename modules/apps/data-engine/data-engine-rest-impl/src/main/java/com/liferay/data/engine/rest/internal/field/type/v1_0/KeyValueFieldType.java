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

package com.liferay.data.engine.rest.internal.field.type.v1_0;

import com.liferay.data.engine.rest.internal.field.type.v1_0.util.CustomPropertyUtil;
import com.liferay.data.engine.spi.field.type.BaseFieldType;
import com.liferay.data.engine.spi.field.type.FieldType;
import com.liferay.data.engine.spi.field.type.SPIDataDefinitionField;
import com.liferay.data.engine.spi.field.type.util.LocalizedValueUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	immediate = true,
	property = {
		"data.engine.field.type.icon=icon-font",
		"data.engine.field.type.js.module=dynamic-data-mapping-form-field-type/metal/KeyValue/KeyValue.es",
		"data.engine.field.type.system=true"
	},
	service = FieldType.class
)
public class KeyValueFieldType extends BaseFieldType {

	@Override
	public SPIDataDefinitionField deserialize(JSONObject jsonObject)
		throws Exception {

		SPIDataDefinitionField spiDataDefinitionField = super.deserialize(
			jsonObject);

		Map<String, Object> customProperties =
			spiDataDefinitionField.getCustomProperties();

		customProperties.put("autoFocus", jsonObject.getBoolean("autoFocus"));
		customProperties.put(
			"placeholder",
			LocalizedValueUtil.toLocalizationMap(
				jsonObject.getJSONObject("placeholder")));
		customProperties.put(
			"tooltip",
			LocalizedValueUtil.toLocalizationMap(
				jsonObject.getJSONObject("tooltip")));

		return spiDataDefinitionField;
	}

	@Override
	public String getName() {
		return "key_value";
	}

	@Override
	public JSONObject toJSONObject(
			SPIDataDefinitionField spiDataDefinitionField)
		throws Exception {

		JSONObject jsonObject = super.toJSONObject(spiDataDefinitionField);

		return jsonObject.put(
			"autoFocus",
			MapUtil.getBoolean(
				spiDataDefinitionField.getCustomProperties(), "autoFocus",
				false)
		).put(
			"placeholder",
			LocalizedValueUtil.toJSONObject(
				CustomPropertyUtil.getMap(
					spiDataDefinitionField.getCustomProperties(),
					"placeholder"))
		).put(
			"tooltip",
			LocalizedValueUtil.toJSONObject(
				CustomPropertyUtil.getMap(
					spiDataDefinitionField.getCustomProperties(), "tooltip"))
		);
	}

	@Override
	protected void includeContext(
		Map<String, Object> context,
		SPIDataDefinitionField spiDataDefinitionField,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		context.put(
			"autoFocus",
			MapUtil.getBoolean(
				spiDataDefinitionField.getCustomProperties(), "autoFocus",
				false));
		context.put(
			"placeholder",
			MapUtil.getString(
				CustomPropertyUtil.getMap(
					spiDataDefinitionField.getCustomProperties(),
					"placeholder"),
				LanguageUtil.getLanguageId(httpServletRequest)));
		context.put("strings", _getStrings(httpServletRequest));
		context.put(
			"tooltip",
			MapUtil.getString(
				CustomPropertyUtil.getMap(
					spiDataDefinitionField.getCustomProperties(), "tooltip"),
				LanguageUtil.getLanguageId(httpServletRequest)));
	}

	private Map<String, String> _getStrings(
		HttpServletRequest httpServletRequest) {

		Map<String, String> values = new HashMap<>();

		values.put(
			"keyLabel", LanguageUtil.get(httpServletRequest, "field-name"));

		return values;
	}

}
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

import com.liferay.data.engine.field.FieldType;
import com.liferay.data.engine.field.FieldTypeTracker;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleThreadLocal;

import java.util.Collection;
import java.util.Locale;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DataEngineTaglibUtil.class)
public class DataEngineTaglibUtil {

	public static JSONArray getFieldTypesJSONArray() {
		Collection<FieldType> fieldTypes = fieldTypeTracker.getFieldTypes();

		JSONArray jsonArray = jsonFactory.createJSONArray();

		Stream<FieldType> stream = fieldTypes.stream();

		stream.map(
			DataEngineTaglibUtil::getFieldTypeMetadataJSONObject
		).forEach(
			jsonArray::put
		);

		return jsonArray;
	}

	public static String getJavaScriptPackage() {
		JSPackage jsPackage = npmResolver.getJSPackage();

		return jsPackage.getResolvedId();
	}

	protected static JSONObject getFieldTypeMetadataJSONObject(
		FieldType fieldType) {

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		JSONObject jsonObject = jsonFactory.createJSONObject();

		jsonObject.put("description", fieldType.getDescription(locale));
		jsonObject.put("group", fieldType.getGroup());
		jsonObject.put("icon", fieldType.getIcon());
		jsonObject.put("javaScriptModule", fieldType.getJavaScriptModule());
		jsonObject.put("label", fieldType.getLabel(locale));
		jsonObject.put("name", fieldType.getName());

		return jsonObject;
	}

	@Reference
	protected static FieldTypeTracker fieldTypeTracker;

	@Reference
	protected static JSONFactory jsonFactory;

	@Reference
	protected static NPMResolver npmResolver;

}
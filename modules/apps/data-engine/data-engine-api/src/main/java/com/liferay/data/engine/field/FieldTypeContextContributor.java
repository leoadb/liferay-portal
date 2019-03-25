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

package com.liferay.data.engine.field;

import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.portal.kernel.language.LanguageConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Leonardo Barros
 */
public interface FieldTypeContextContributor {

	public default void includeContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Map<String, Object> context,
		DataDefinitionField dataDefinitionField, boolean readOnly) {

		String languageId = LanguageUtil.getLanguageId(httpServletRequest);

		context.put(
			"dir",
			LanguageUtil.get(httpServletRequest, LanguageConstants.KEY_DIR));
		context.put(
			"label",
			MapUtil.getString(dataDefinitionField.getLabel(), languageId));
		context.put("name", dataDefinitionField.getName());
		context.put(
			"tip", MapUtil.getString(dataDefinitionField.getTip(), languageId));
		context.put("type", dataDefinitionField.getFieldType());

		Map<String, ?> customProperties =
			dataDefinitionField.getCustomProperties();

		context.put(
			"readOnly", MapUtil.getBoolean(customProperties, "readOnly"));
		context.put(
			"required", MapUtil.getBoolean(customProperties, "required"));
		context.put(
			"showLabel",
			MapUtil.getBoolean(customProperties, "showLabel", true));
		context.put(
			"visible", MapUtil.getBoolean(customProperties, "visible", true));
	}

}
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

package com.liferay.dynamic.data.mapping.test.util;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRendererRenderRequest;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRendererRenderResponse;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;

import java.util.Locale;

/**
 * @author Leonardo Barros
 */
public class DDMFormFieldValueRendererTestUtil {

	public static String render(
		DDMFormFieldValue ddmFormFieldValue,
		DDMFormFieldValueRenderer ddmFormFieldValueRenderer, Locale locale) {

		DDMFormFieldValueRendererRenderRequest.Builder builder =
			DDMFormFieldValueRendererRenderRequest.Builder.newBuilder();

		DDMFormFieldValueRendererRenderRequest
			ddmFormFieldValueRendererRenderRequest =
				builder.withDDMFormFieldValue(
					ddmFormFieldValue
				).withLocale(
					locale
				).build();

		DDMFormFieldValueRendererRenderResponse
			ddmFormFieldValueRendererRenderResponse =
				ddmFormFieldValueRenderer.render(
					ddmFormFieldValueRendererRenderRequest);

		return ddmFormFieldValueRendererRenderResponse.getContent();
	}

}
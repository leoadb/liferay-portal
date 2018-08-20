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

package com.liferay.dynamic.data.mapping.form.field.type;

import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;

import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public interface DDMFormFieldValueRenderer {

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {
	 * @link DDMFormFieldValueRenderer#render(
	 * DDMFormFieldValueRendererRenderRequest)}
	 */
	@Deprecated
	public default String render(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

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
			ddmFormFieldValueRendererRenderResponse = render(
				ddmFormFieldValueRendererRenderRequest);

		return ddmFormFieldValueRendererRenderResponse.getContent();
	}

	public DDMFormFieldValueRendererRenderResponse render(
		DDMFormFieldValueRendererRenderRequest
			ddmFormFieldValueRendererRenderRequest);

}
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

package com.liferay.dynamic.data.mapping.form.field.type.checkbox.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRendererRenderRequest;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRendererRenderResponse;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Renato Rego
 */
@Component(immediate = true, property = "ddm.form.field.type.name=checkbox")
public class CheckboxDDMFormFieldValueRenderer
	implements DDMFormFieldValueRenderer {

	@Override
	public DDMFormFieldValueRendererRenderResponse render(
		DDMFormFieldValueRendererRenderRequest
			ddmFormFieldValueRendererRenderRequest) {

		DDMFormFieldValue ddmFormFieldValue =
			ddmFormFieldValueRendererRenderRequest.getDDMFormFieldValue();
		Locale locale = ddmFormFieldValueRendererRenderRequest.getLocale();

		Boolean valueBoolean = checkboxDDMFormFieldValueAccessor.getValue(
			ddmFormFieldValue, locale);

		String content = LanguageUtil.get(locale, "no");

		if (valueBoolean == Boolean.TRUE) {
			content = LanguageUtil.get(locale, "yes");
		}

		return DDMFormFieldValueRendererRenderResponse.Builder.of(content);
	}

	@Reference
	protected CheckboxDDMFormFieldValueAccessor
		checkboxDDMFormFieldValueAccessor;

}
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
 * @author Leonardo Barros
 */
public final class DDMFormFieldValueRendererRenderRequest {

	public DDMFormFieldValue getDDMFormFieldValue() {
		return _ddmFormFieldValue;
	}

	public Locale getLocale() {
		return _locale;
	}

	public static class Builder {

		public static Builder newBuilder() {
			return new Builder();
		}

		public DDMFormFieldValueRendererRenderRequest build() {
			return _ddmFormFieldValueRendererRenderRequest;
		}

		public Builder withDDMFormFieldValue(
			DDMFormFieldValue ddmFormFieldValue) {

			_ddmFormFieldValueRendererRenderRequest._ddmFormFieldValue =
				ddmFormFieldValue;

			return this;
		}

		public Builder withLocale(Locale locale) {
			_ddmFormFieldValueRendererRenderRequest._locale = locale;

			return this;
		}

		private Builder() {
		}

		private final DDMFormFieldValueRendererRenderRequest
			_ddmFormFieldValueRendererRenderRequest =
				new DDMFormFieldValueRendererRenderRequest();

	}

	private DDMFormFieldValueRendererRenderRequest() {
	}

	private DDMFormFieldValue _ddmFormFieldValue;
	private Locale _locale;

}
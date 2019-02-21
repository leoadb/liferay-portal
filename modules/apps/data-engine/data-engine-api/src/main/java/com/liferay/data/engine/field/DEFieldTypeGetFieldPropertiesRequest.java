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

import com.liferay.data.engine.model.DEDataDefinitionField;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Leonardo Barros
 */
public final class DEFieldTypeGetFieldPropertiesRequest {

	public DEDataDefinitionField getDEDataDefinitionField() {
		return _deDataDefinitionField;
	}

	public HttpServletRequest getHttpServletRequest() {
		return _httpServletRequest;
	}

	public HttpServletResponse getHttpServletResponse() {
		return _httpServletResponse;
	}

	public Locale getLocale() {
		return _locale;
	}

	public boolean isViewMode() {
		return _viewMode;
	}

	public static class Builder {

		public Builder(
			DEDataDefinitionField deDataDefinitionField, Locale locale) {

			_deFieldTypeGetCustomPropertiesRequest._deDataDefinitionField =
				deDataDefinitionField;
			_deFieldTypeGetCustomPropertiesRequest._locale = locale;
		}

		public DEFieldTypeGetFieldPropertiesRequest build() {
			return _deFieldTypeGetCustomPropertiesRequest;
		}

		public Builder request(HttpServletRequest httpServletRequest) {
			_deFieldTypeGetCustomPropertiesRequest._httpServletRequest =
				httpServletRequest;

			return this;
		}

		public Builder response(HttpServletResponse httpServletResponse) {
			_deFieldTypeGetCustomPropertiesRequest._httpServletResponse =
				httpServletResponse;

			return this;
		}

		public Builder viewMode(boolean viewMode) {
			_deFieldTypeGetCustomPropertiesRequest._viewMode = viewMode;

			return this;
		}

		private final DEFieldTypeGetFieldPropertiesRequest
			_deFieldTypeGetCustomPropertiesRequest =
				new DEFieldTypeGetFieldPropertiesRequest();

	}

	private DEFieldTypeGetFieldPropertiesRequest() {
	}

	private DEDataDefinitionField _deDataDefinitionField;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private Locale _locale;
	private boolean _viewMode = true;

}
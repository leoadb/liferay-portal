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

package com.liferay.dynamic.data.mapping.form.evaluator;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leonardo Barros
 */
public class DDMFormEvaluatorContext {

	public DDMForm getDDMForm() {
		return _ddmForm;
	}

	public DDMFormValues getDDMFormValues() {
		return _ddmFormValues;
	}

	public HttpServletRequest getHttpServletRequest() {
		return _request;
	}

	public Locale getLocale() {
		return _locale;
	}

	public void setDDMForm(DDMForm ddmForm) {
		_ddmForm = ddmForm;
	}

	public void setDDMFormValues(DDMFormValues ddmFormValues) {
		_ddmFormValues = ddmFormValues;
	}

	public void setHttpServletRequest(HttpServletRequest request) {
		_request = request;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	private DDMForm _ddmForm;
	private DDMFormValues _ddmFormValues;
	private Locale _locale;
	private HttpServletRequest _request;

}
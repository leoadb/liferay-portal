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

package com.liferay.data.engine.exception;

import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class DEDataDefinitionDefaultLocaleAsAvailableLocaleException
	extends DEDataDefinitionException {

	public DEDataDefinitionDefaultLocaleAsAvailableLocaleException() {
	}

	public DEDataDefinitionDefaultLocaleAsAvailableLocaleException(
		Locale defaultLocale, Throwable cause) {

		super(
			String.format(
				"The default locale %s must be set to a valid available locale",
				defaultLocale),
			cause);

		_defaultLocale = defaultLocale;
	}

	public DEDataDefinitionDefaultLocaleAsAvailableLocaleException(String msg) {
		super(msg);
	}

	public DEDataDefinitionDefaultLocaleAsAvailableLocaleException(
		String msg, Throwable cause) {

		super(msg, cause);
	}

	public DEDataDefinitionDefaultLocaleAsAvailableLocaleException(
		Throwable cause) {

		super(cause);
	}

	public Locale getDefaultLocale() {
		return _defaultLocale;
	}

	private Locale _defaultLocale;

}
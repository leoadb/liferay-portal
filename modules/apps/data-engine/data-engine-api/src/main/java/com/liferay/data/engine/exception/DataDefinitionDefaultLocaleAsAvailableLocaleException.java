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

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class DataDefinitionDefaultLocaleAsAvailableLocaleException
	extends PortalException {

	public DataDefinitionDefaultLocaleAsAvailableLocaleException() {
	}

	public DataDefinitionDefaultLocaleAsAvailableLocaleException(
		Locale defaultLocale, Throwable cause) {

		super(
			String.format(
				"The default locale %s must be set to a valid available locale",
				defaultLocale),
			cause);

		_defaultLocale = defaultLocale;
	}

	public DataDefinitionDefaultLocaleAsAvailableLocaleException(String msg) {
		super(msg);
	}

	public DataDefinitionDefaultLocaleAsAvailableLocaleException(
		String msg, Throwable cause) {

		super(msg, cause);
	}

	public DataDefinitionDefaultLocaleAsAvailableLocaleException(
		Throwable cause) {

		super(cause);
	}

	public Locale getDefaultLocale() {
		return _defaultLocale;
	}

	private Locale _defaultLocale;

}
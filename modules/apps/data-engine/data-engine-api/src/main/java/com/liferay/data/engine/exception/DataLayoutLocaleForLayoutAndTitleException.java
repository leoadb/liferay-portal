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

/**
 * @author Brian Wing Shun Chan
 */
public class DataLayoutLocaleForLayoutAndTitleException
	extends PortalException {

	public DataLayoutLocaleForLayoutAndTitleException() {
	}

	public DataLayoutLocaleForLayoutAndTitleException(String msg) {
		super(msg);
	}

	public DataLayoutLocaleForLayoutAndTitleException(
		String msg, Throwable cause) {

		super(msg, cause);
	}

	public DataLayoutLocaleForLayoutAndTitleException(Throwable cause) {
		super(
			"The default locale for the data layout's page title is not the " +
				"same as the data layout's default locale",
			cause);
	}

}
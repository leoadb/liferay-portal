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

package com.liferay.data.engine.service;

import com.liferay.data.engine.model.DEDataLayout;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jeyvison Nascimento
 */
public class DEDataLayoutRequestBuilder {

	public static DEDataLayoutGetRequest.Builder getBuilder() {
		return new DEDataLayoutGetRequest.Builder();
	}

	public static DEDataLayoutRenderRequest.Builder renderBuilder(
		DEDataLayout deDataLayout, HttpServletRequest httpServletRequest) {

		return new DEDataLayoutRenderRequest.Builder(
			deDataLayout, httpServletRequest);
	}

	public static DEDataLayoutSaveRequest.Builder saveBuilder(
		DEDataLayout deDataLayout) {

		return new DEDataLayoutSaveRequest.Builder(deDataLayout);
	}

}
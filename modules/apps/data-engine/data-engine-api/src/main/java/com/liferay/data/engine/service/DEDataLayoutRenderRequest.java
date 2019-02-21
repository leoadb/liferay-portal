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
 * @author Leonardo Barros
 */
public final class DEDataLayoutRenderRequest {

	public DEDataLayout getDEDataLayout() {
		return _deDataLayout;
	}

	public HttpServletRequest getHttpServletRequest() {
		return _httpServletRequest;
	}

	public boolean isReadOnly() {
		return _readOnly;
	}

	public static class Builder {

		public Builder(
			DEDataLayout deDataLayout, HttpServletRequest httpServletRequest) {

			_deDataLayoutRenderRequest._deDataLayout = deDataLayout;
			_deDataLayoutRenderRequest._httpServletRequest = httpServletRequest;
		}

		public DEDataLayoutRenderRequest build() {
			return _deDataLayoutRenderRequest;
		}

		public Builder readOnly(boolean readOnly) {
			_deDataLayoutRenderRequest._readOnly = readOnly;

			return this;
		}

		private final DEDataLayoutRenderRequest _deDataLayoutRenderRequest =
			new DEDataLayoutRenderRequest();

	}

	private DEDataLayoutRenderRequest() {
	}

	private DEDataLayout _deDataLayout;
	private HttpServletRequest _httpServletRequest;
	private boolean _readOnly;

}
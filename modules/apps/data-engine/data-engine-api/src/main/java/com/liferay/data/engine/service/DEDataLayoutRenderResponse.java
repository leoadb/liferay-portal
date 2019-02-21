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

/**
 * @author Leonardo Barros
 */
public final class DEDataLayoutRenderResponse {

	public String getHtml() {
		return _html;
	}

	public static class Builder {

		public static Builder newBuilder(String html) {
			return new Builder(html);
		}

		public static DEDataLayoutRenderResponse of(String html) {
			return newBuilder(
				html
			).build();
		}

		public DEDataLayoutRenderResponse build() {
			return _deDataLayoutRenderResponse;
		}

		private Builder(String html) {
			_deDataLayoutRenderResponse._html = html;
		}

		private final DEDataLayoutRenderResponse _deDataLayoutRenderResponse =
			new DEDataLayoutRenderResponse();

	}

	private DEDataLayoutRenderResponse() {
	}

	private String _html;

}
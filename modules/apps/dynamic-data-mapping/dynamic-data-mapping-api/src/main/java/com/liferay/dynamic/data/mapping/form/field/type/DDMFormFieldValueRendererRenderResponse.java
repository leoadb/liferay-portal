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

/**
 * @author Leonardo Barros
 */
public final class DDMFormFieldValueRendererRenderResponse {

	public String getContent() {
		return _content;
	}

	public static class Builder {

		public static Builder newBuilder(String content) {
			return new Builder(content);
		}

		public static DDMFormFieldValueRendererRenderResponse of(
			String content) {

			Builder builder = newBuilder(content);

			return builder.build();
		}

		public DDMFormFieldValueRendererRenderResponse build() {
			return _ddmFormFieldValueRendererRenderResponse;
		}

		private Builder(String content) {
			_ddmFormFieldValueRendererRenderResponse._content = content;
		}

		private final DDMFormFieldValueRendererRenderResponse
			_ddmFormFieldValueRendererRenderResponse =
				new DDMFormFieldValueRendererRenderResponse();

	}

	private DDMFormFieldValueRendererRenderResponse() {
	}

	private String _content;

}
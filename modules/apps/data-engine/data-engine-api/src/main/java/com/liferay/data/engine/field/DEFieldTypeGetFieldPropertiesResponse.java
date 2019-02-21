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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public final class DEFieldTypeGetFieldPropertiesResponse {

	public Map<String, Object> getFieldProperties() {
		return _fieldProperties;
	}

	public static class Builder {

		public Builder(Map<String, Object> fieldProperties) {
			if (fieldProperties != null) {
				_deFieldTypeGetCustomPropertiesResponse._fieldProperties.putAll(
					fieldProperties);
			}
		}

		public DEFieldTypeGetFieldPropertiesResponse build() {
			return _deFieldTypeGetCustomPropertiesResponse;
		}

		private final DEFieldTypeGetFieldPropertiesResponse
			_deFieldTypeGetCustomPropertiesResponse =
				new DEFieldTypeGetFieldPropertiesResponse();

	}

	private DEFieldTypeGetFieldPropertiesResponse() {
	}

	private final Map<String, Object> _fieldProperties = new HashMap<>();

}
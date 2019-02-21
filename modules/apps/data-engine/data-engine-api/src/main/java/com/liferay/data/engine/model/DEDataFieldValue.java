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

package com.liferay.data.engine.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DEDataFieldValue {

	public DEDataFieldValue(Object value, boolean localized) {
		if (localized) {
			if (!(value instanceof Map)) {
				throw new IllegalArgumentException(
					"A Map must be passed as argument for localized values");
			}

			_values.putAll((Map<String, Object>)value);
		}
		else {
			_values.put(_DEFAULT, value);
		}
	}

	public Object getValue() {
		return _values.get(_DEFAULT);
	}

	public Object getValue(String languageId) {
		return _values.get(languageId);
	}

	public Map<String, Object> getValues() {
		return Collections.unmodifiableMap(_values);
	}

	public boolean isLocalized() {
		return !_values.containsKey(_DEFAULT);
	}

	private static final String _DEFAULT = "DEFAULT";

	private Map<String, Object> _values = new HashMap<>();

}
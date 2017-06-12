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

package com.liferay.dynamic.data.mapping.type.checkbox.multiple.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=checkbox_multiple",
	service = {
		CheckboxMultipleDDMFormFieldValueAccessor.class,
		DDMFormFieldValueAccessor.class
	}
)
public class CheckboxMultipleDDMFormFieldValueAccessor
	implements DDMFormFieldValueAccessor<JSONArray> {

	@Override
	public JSONArray getValue(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		Value value = ddmFormFieldValue.getValue();

		String valueString = value.getString(locale);

		try {
			return jsonFactory.createJSONArray(valueString);
		}
		catch (JSONException jsone) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse JSON array", jsone);
			}

			return jsonFactory.createJSONArray();
		}
	}

	@Override
	public Object map(Object value) {
		if (Validator.isNull(value)) {
			return value;
		}

		try {
			JSONArray jsonArray = jsonFactory.createJSONArray(value.toString());

			StringBundler sb = new StringBundler(jsonArray.length() * 2 - 1);

			for (int i = 0; i < jsonArray.length(); i++) {
				sb.append(jsonArray.get(i));

				if (i < (jsonArray.length() - 1)) {
					sb.append(CharPool.COMMA);
				}
			}

			return sb.toString();
		}
		catch (JSONException jsone) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse JSON array", jsone);
			}

			return StringPool.BLANK;
		}
	}

	@Reference
	protected JSONFactory jsonFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		CheckboxMultipleDDMFormFieldValueAccessor.class);

}
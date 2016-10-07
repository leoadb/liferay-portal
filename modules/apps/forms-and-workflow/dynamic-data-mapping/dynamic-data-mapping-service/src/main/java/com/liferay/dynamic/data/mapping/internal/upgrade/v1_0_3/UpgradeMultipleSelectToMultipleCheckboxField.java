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

package com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_3;

import com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_2.UpgradeCheckboxFieldToCheckboxMultipleField;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONSerializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Leonardo Barros
 */
public class UpgradeMultipleSelectToMultipleCheckboxField
	extends UpgradeCheckboxFieldToCheckboxMultipleField {

	public UpgradeMultipleSelectToMultipleCheckboxField(
		DDMFormJSONDeserializer ddmFormJSONDeserializer,
		DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer,
		DDMFormValuesJSONSerializer ddmFormValuesJSONSerializer,
		JSONFactory jsonFactory) {

		super(
			ddmFormJSONDeserializer, ddmFormValuesJSONDeserializer,
			ddmFormValuesJSONSerializer, jsonFactory);
	}

	@Override
	protected String getFieldTypeFilter() {
		return "%select%";
	}

	@Override
	protected void transformDDMFormField(JSONObject jsonObject) {
		jsonObject.put("type", "checkbox_multiple");
	}

	@Override
	protected void updateRecords(DDMForm ddmForm, long recordSetId)
		throws Exception {
	}

	@Override
	protected void upgradeRecordSetStructureFields(JSONArray fieldsJSONArray) {
		for (int i = 0; i < fieldsJSONArray.length(); i++) {
			JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(i);

			String type = fieldJSONObject.getString("type");
			boolean multiple = fieldJSONObject.getBoolean("multiple");

			if (type.equals("select") && multiple) {
				transformDDMFormField(fieldJSONObject);
			}

			JSONArray nestedFieldsJSONArray = fieldJSONObject.getJSONArray(
				"nestedFields");

			if (nestedFieldsJSONArray != null) {
				upgradeRecordSetStructureFields(nestedFieldsJSONArray);
			}
		}
	}

}
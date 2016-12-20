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

package com.liferay.dynamic.data.mapping.type.fieldset.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=fieldset",
	service = {
		FieldSetDDMFormFieldTemplateContextContributor.class,
		DDMFormFieldTemplateContextContributor.class
	}
)
public class FieldSetDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Map<String, Object> parameters = new HashMap<>();

		Map<String, Object> properties = ddmFormField.getProperties();

		Map<String, Object> renderingContextProperties =
			ddmFormFieldRenderingContext.getProperties();

		Map<String, List<Object>> nestedDDMFormFieldTemplateContexts =
			(Map<String, List<Object>>)renderingContextProperties.get(
				"nestedFields");

		LocalizedValue label = ddmFormField.getLabel();

		if (label != null) {
			parameters.put("label", label.getString(
				ddmFormFieldRenderingContext.getLocale()));
			parameters.put("showLabel", true);
		}

		if (nestedDDMFormFieldTemplateContexts == null) {
			return parameters;
		}

		if (properties.containsKey("rows")) {
			List<DDMFormLayoutRow> ddmFormLayoutRows =
				(List<DDMFormLayoutRow>)properties.get("rows");

			parameters.put(
				"rows",
				createRowsContext(
					ddmFormLayoutRows, nestedDDMFormFieldTemplateContexts));
		}

		return parameters;
	}

	protected Map<String, Object> createColumnContext(
		DDMFormLayoutColumn ddmFormLayoutColumn,
		Map<String, List<Object>> nestedDDMFormFieldTemplateContexts) {

		Map<String, Object> columnMap = new HashMap<>();
		List<Object> fieldsContext = new ArrayList<>();

		for (String ddmFormFieldName :
				ddmFormLayoutColumn.getDDMFormFieldNames()) {

			if (nestedDDMFormFieldTemplateContexts.containsKey(
					ddmFormFieldName)) {

				fieldsContext.addAll(
					nestedDDMFormFieldTemplateContexts.get(ddmFormFieldName));
			}
		}

		columnMap.put("fields", fieldsContext);
		columnMap.put("size", ddmFormLayoutColumn.getSize());

		return columnMap;
	}

	protected List<Map<String, Object>> createColumnsContext(
		List<DDMFormLayoutColumn> ddmFormLayoutColumns,
		Map<String, List<Object>> nestedDDMFormFieldTemplateContexts) {

		List<Map<String, Object>> columns = new ArrayList<>();

		for (DDMFormLayoutColumn ddmFormLayoutColumn : ddmFormLayoutColumns) {
			columns.add(
				createColumnContext(
					ddmFormLayoutColumn, nestedDDMFormFieldTemplateContexts));
		}

		return columns;
	}

	protected Map<String, Object> createRowContext(
		DDMFormLayoutRow ddmFormLayoutRow,
		Map<String, List<Object>> nestedDDMFormFieldTemplateContexts) {

		Map<String, Object> rowMap = new HashMap<>();

		rowMap.put(
			"columns",
			createColumnsContext(
				ddmFormLayoutRow.getDDMFormLayoutColumns(),
				nestedDDMFormFieldTemplateContexts));

		return rowMap;
	}

	protected List<Map<String, Object>> createRowsContext(
		List<DDMFormLayoutRow> ddmFormLayoutRows,
		Map<String, List<Object>> nestedDDMFormFieldTemplateContexts) {

		List<Map<String, Object>> rows = new ArrayList<>();

		for (DDMFormLayoutRow ddmFormLayoutRow : ddmFormLayoutRows) {
			rows.add(
				createRowContext(
					ddmFormLayoutRow, nestedDDMFormFieldTemplateContexts));
		}

		return rows;
	}

	protected int getColumnSize(int size) {
		if (size == 0) {
			return 0;
		}

		return 12 / size;
	}

}
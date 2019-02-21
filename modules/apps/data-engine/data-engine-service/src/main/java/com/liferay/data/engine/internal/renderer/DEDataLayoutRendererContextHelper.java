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

package com.liferay.data.engine.internal.renderer;

import com.liferay.data.engine.field.DEFieldType;
import com.liferay.data.engine.field.DEFieldTypeGetFieldPropertiesRequest;
import com.liferay.data.engine.field.DEFieldTypeGetFieldPropertiesResponse;
import com.liferay.data.engine.field.DEFieldTypeRequestBuilder;
import com.liferay.data.engine.internal.field.DEFieldTypeTracker;
import com.liferay.data.engine.internal.util.DEDataEngineUtil;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.data.engine.model.DEDataLayout;
import com.liferay.data.engine.model.DEDataLayoutColumn;
import com.liferay.data.engine.model.DEDataLayoutPage;
import com.liferay.data.engine.model.DEDataLayoutRow;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Leonardo Barros
 */
public class DEDataLayoutRendererContextHelper {

	public DEDataLayoutRendererContextHelper(
		DEDataLayout deDataLayout, DEFieldTypeTracker deFieldTypeTracker,
		Locale locale) {

		_deDataLayout = deDataLayout;
		_deFieldTypeTracker = deFieldTypeTracker;

		_deDataDefinitionFields = DEDataEngineUtil.getDEDataDefinitionFieldsMap(
			_deDataLayout.getDEDataDefinition());
		_locale = locale;
	}

	public Map<String, Object> getContext() {
		Map<String, Object> context = new HashMap<>();

		context.put(
			"pages",
			createPagesTemplateContext(_deDataLayout.getDEDataLayoutPages()));

		return context;
	}

	protected List<Object> createColumnsTemplateContext(
		Queue<DEDataLayoutColumn> deDataLayoutColumns) {

		Stream<DEDataLayoutColumn> stream = deDataLayoutColumns.stream();

		return stream.map(
			this::createColumnTemplateContext
		).collect(
			Collectors.toList()
		);
	}

	protected Map<String, Object> createColumnTemplateContext(
		DEDataLayoutColumn deDataLayoutColumn) {

		Map<String, Object> columnTemplateContext = new HashMap<>();

		columnTemplateContext.put(
			"fields",
			createFieldsTemplateContext(deDataLayoutColumn.getFieldsName()));
		columnTemplateContext.put("size", deDataLayoutColumn.getColumnSize());

		return columnTemplateContext;
	}

	protected List<Object> createFieldsTemplateContext(
		List<String> ddmFormFieldNames) {

		Stream<String> stream = ddmFormFieldNames.stream();

		return stream.map(
			this::createFieldTemplateContext
		).collect(
			Collectors.toList()
		);
	}

	protected Map<String, Object> createFieldTemplateContext(
		String ddmFormFieldName) {

		DEDataDefinitionField deDataDefinitionField =
			_deDataDefinitionFields.get(ddmFormFieldName);

		DEFieldType deFieldType = _deFieldTypeTracker.getDEFieldType(
			deDataDefinitionField.getType());

		DEFieldTypeGetFieldPropertiesRequest
			deFieldTypeGetFieldPropertiesRequest =
				DEFieldTypeRequestBuilder.getFieldPropertiesBuilder(
					deDataDefinitionField, _locale
				).build();

		DEFieldTypeGetFieldPropertiesResponse
			deFieldTypeGetFieldPropertiesResponse =
				deFieldType.getFieldProperties(
					deFieldTypeGetFieldPropertiesRequest);

		return deFieldTypeGetFieldPropertiesResponse.getFieldProperties();
	}

	protected List<Object> createPagesTemplateContext(
		Queue<DEDataLayoutPage> deDataLayoutPages) {

		Stream<DEDataLayoutPage> stream = deDataLayoutPages.stream();

		return stream.map(
			this::createPageTemplateContext
		).collect(
			Collectors.toList()
		);
	}

	protected Map<String, Object> createPageTemplateContext(
		DEDataLayoutPage deDataLayoutPage) {

		Map<String, Object> pageTemplateContext = new HashMap<>();

		pageTemplateContext.put(
			"rows",
			createRowsTemplateContext(deDataLayoutPage.getDEDataLayoutRows()));

		return pageTemplateContext;
	}

	protected List<Object> createRowsTemplateContext(
		Queue<DEDataLayoutRow> deDataLayoutRows) {

		Stream<DEDataLayoutRow> stream = deDataLayoutRows.stream();

		return stream.map(
			this::createRowTemplateContext
		).collect(
			Collectors.toList()
		);
	}

	protected Map<String, Object> createRowTemplateContext(
		DEDataLayoutRow deDataLayoutRow) {

		Map<String, Object> rowTemplateContext = new HashMap<>();

		rowTemplateContext.put(
			"columns",
			createColumnsTemplateContext(
				deDataLayoutRow.getDEDataLayoutColumns()));

		return rowTemplateContext;
	}

	private final Map<String, DEDataDefinitionField> _deDataDefinitionFields;
	private final DEDataLayout _deDataLayout;
	private final DEFieldTypeTracker _deFieldTypeTracker;
	private final Locale _locale;

}
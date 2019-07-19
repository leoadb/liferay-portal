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

package com.liferay.data.engine.rest.internal.renderer.v1_0;

import com.liferay.data.engine.field.type.FieldType;
import com.liferay.data.engine.field.type.FieldTypeTracker;
import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.renderer.DataLayoutRenderer;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v1_0.DataLayout;
import com.liferay.data.engine.rest.dto.v1_0.DataLayoutColumn;
import com.liferay.data.engine.rest.dto.v1_0.DataLayoutPage;
import com.liferay.data.engine.rest.dto.v1_0.DataLayoutRow;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataDefinitionFieldUtil;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataDefinitionUtil;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataLayoutUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.soy.renderer.ComponentDescriptor;
import com.liferay.portal.template.soy.renderer.SoyComponentRenderer;

import java.io.Writer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(immediate = true, service = DataLayoutRenderer.class)
public class DataLayoutRendererImpl implements DataLayoutRenderer {

	@Override
	public String render(
			Class<?> clazz, Map<String, Object> dataRecordValues,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		return _render(
			DataDefinitionUtil.toDataDefinition(
				clazz, httpServletRequest.getLocale()),
			DataLayoutUtil.toDataLayout(clazz, httpServletRequest.getLocale()),
			dataRecordValues, httpServletRequest, httpServletResponse);
	}

	@Override
	public String render(
			Long dataLayoutId, Map<String, Object> dataRecordValues,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.getStructureLayout(dataLayoutId);

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getDDMStructureVersion(
				ddmStructureLayout.getStructureVersionId());

		return _render(
			DataDefinitionUtil.toDataDefinition(
				ddmStructureVersion.getStructure()),
			DataLayoutUtil.toDataLayout(ddmStructureLayout.getDefinition()),
			dataRecordValues, httpServletRequest, httpServletResponse);
	}

	private List<Object> _createDataLayoutColumnContexts(
		Map<String, DataDefinitionField> dataDefinitionFields,
		DataLayoutColumn[] dataLayoutColumns, FieldTypeTracker fieldTypeTracker,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		return Stream.of(
			dataLayoutColumns
		).map(
			dataLayoutColumn -> new HashMap() {
				{
					put(
						"fields",
						_createFieldTypeContexts(
							dataDefinitionFields,
							dataLayoutColumn.getFieldNames(), fieldTypeTracker,
							httpServletRequest, httpServletResponse));
					put("size", dataLayoutColumn.getColumnSize());
				}
			}
		).collect(
			Collectors.toList()
		);
	}

	private List<Object> _createDataLayoutPageContexts(
		Map<String, DataDefinitionField> dataDefinitionFields,
		DataLayoutPage[] dataLayoutPages, FieldTypeTracker fieldTypeTracker,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		return Stream.of(
			dataLayoutPages
		).map(
			dataLayoutPage -> new HashMap() {
				{
					put(
						"description",
						GetterUtil.getString(
							LocalizedValueUtil.getLocalizedValue(
								httpServletRequest.getLocale(),
								dataLayoutPage.getDescription())));
					put(
						"rows",
						_createDataLayoutRowContexts(
							dataDefinitionFields,
							dataLayoutPage.getDataLayoutRows(),
							fieldTypeTracker, httpServletRequest,
							httpServletResponse));
					put(
						"title",
						GetterUtil.getString(
							LocalizedValueUtil.getLocalizedValue(
								httpServletRequest.getLocale(),
								dataLayoutPage.getTitle())));
				}
			}
		).collect(
			Collectors.toList()
		);
	}

	private List<Object> _createDataLayoutRowContexts(
		Map<String, DataDefinitionField> dataDefinitionFields,
		DataLayoutRow[] dataLayoutRows, FieldTypeTracker fieldTypeTracker,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		return Stream.of(
			dataLayoutRows
		).map(
			dataLayoutRow -> new HashMap() {
				{
					put(
						"columns",
						_createDataLayoutColumnContexts(
							dataDefinitionFields,
							dataLayoutRow.getDataLayoutColums(),
							fieldTypeTracker, httpServletRequest,
							httpServletResponse));
				}
			}
		).collect(
			Collectors.toList()
		);
	}

	private List<Object> _createFieldTypeContexts(
		Map<String, DataDefinitionField> dataDefinitionFields,
		String[] fieldNames, FieldTypeTracker fieldTypeTracker,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		List<Object> fieldTypeContexts = new ArrayList<>();

		Stream.of(
			fieldNames
		).map(
			dataDefinitionFields::get
		).forEach(
			dataDefinitionField -> {
				FieldType fieldType = fieldTypeTracker.getFieldType(
					dataDefinitionField.getFieldType());

				fieldTypeContexts.add(
					fieldType.includeContext(
						httpServletRequest, httpServletResponse,
						DataDefinitionFieldUtil.toSPIDataDefinitionField(
							dataDefinitionField)));
			}
		);

		return fieldTypeContexts;
	}

	private Map<String, DataDefinitionField> _getDataDefinitionFieldsMap(
		DataDefinition dataDefinition, Map<String, Object> dataRecordValues) {

		List<DataDefinitionField> dataDefinitionFields = Arrays.asList(
			dataDefinition.getDataDefinitionFields());

		Stream<DataDefinitionField> stream = dataDefinitionFields.stream();

		return stream.collect(
			Collectors.toMap(
				dataDefinitionField -> dataDefinitionField.getName(),
				definitionField -> {
					if (MapUtil.isEmpty(dataRecordValues)) {
						return definitionField;
					}

					Map<String, Object> customProperties =
						definitionField.getCustomProperties();

					customProperties.put(
						"value",
						dataRecordValues.get(definitionField.getName()));

					return definitionField;
				}));
	}

	private String _getSpriteMap(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String pathThemeImages = themeDisplay.getPathThemeImages();

		return pathThemeImages.concat("/clay/icons.svg");
	}

	private String _render(
			DataDefinition dataDefinition, DataLayout dataLayout,
			Map<String, Object> dataRecordValues,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		Writer writer = new UnsyncStringWriter();

		_soyComponentRenderer.renderSoyComponent(
			httpServletRequest, writer,
			new ComponentDescriptor(
				_TEMPLATE_NAMESPACE,
				_npmResolver.resolveModuleName(_MODULE_NAME)),
			new HashMap() {
				{
					put(
						"pages",
						_createDataLayoutPageContexts(
							_getDataDefinitionFieldsMap(
								dataDefinition, dataRecordValues),
							dataLayout.getDataLayoutPages(), _fieldTypeTracker,
							httpServletRequest, httpServletResponse));
					put("paginationMode", dataLayout.getPaginationMode());
					put("spritemap", _getSpriteMap(httpServletRequest));
				}
			});

		return writer.toString();
	}

	private static final String _MODULE_NAME =
		"dynamic-data-mapping-form-renderer/js/metal/containers/Form/Form.es";

	private static final String _TEMPLATE_NAMESPACE = "FormRenderer.render";

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	@Reference
	private FieldTypeTracker _fieldTypeTracker;

	@Reference
	private NPMResolver _npmResolver;

	@Reference
	private SoyComponentRenderer _soyComponentRenderer;

}
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

package com.liferay.dynamic.data.mapping.type.select.internal;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.annotations.DDMFormRule;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;

/**
 * @author Marcellus Tavares
 */
@DDMForm(
	rules = {
		@DDMFormRule(
			condition = "TRUE",
			actions = {
				"setVisible('ddmDataProviderInstanceId', equals(getValue('dataSourceType'), 'data-provider'))",
				"setRequired('ddmDataProviderInstanceId', equals(getValue('dataSourceType'), 'data-provider'))",
				"setVisible('ddmDataProviderOutput', equals(getValue('dataSourceType'), 'data-provider'))",
				"setRequired('ddmDataProviderOutput', equals(getValue('dataSourceType'), 'data-provider'))",
				"setVisible('options', equals(getValue('dataSourceType'), 'manual'))",
				"setRequired('options', equals(getValue('dataSourceType'), 'manual'))",
				"setVisible('validation', FALSE)"
			}
		)
	}
)
@DDMFormLayout(
	paginationMode = com.liferay.dynamic.data.mapping.model.DDMFormLayout.TABBED_MODE,
	value = {
		@DDMFormLayoutPage(
			title = "basic",
			value = {
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 12,
							value = {
								"label", "tip", "required", "dataSourceType",
								"options", "ddmDataProviderInstanceId",
								"ddmDataProviderOutput"
							}
						)
					}
				)
			}
		),
		@DDMFormLayoutPage(
			title = "properties",
			value = {
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 12,
							value = {
								"predefinedValue", "visibilityExpression",
								"validation", "fieldNamespace", "indexType",
								"localizable", "readOnly", "dataType", "type",
								"name", "showLabel", "repeatable", "multiple"
							}
						)
					}
				)
			}
		)
	}
)
public interface SelectDDMFormFieldTypeSettings
	extends DefaultDDMFormFieldTypeSettings {

	@DDMFormField(
		label = "%create-list",
		optionLabels = {"%manually", "%from-data-provider"},
		optionValues = {"manual", "data-provider"}, predefinedValue = "manual",
		type = "radio"
	)
	public String dataSourceType();

	@DDMFormField(
		label = "%choose-an-output-parameter-from-data-provider", type = "select"
	)
	public String ddmDataProviderOutput();

	@DDMFormField(
		label = "%choose-a-data-provider", type = "select"
	)
	public long ddmDataProviderInstanceId();

	@DDMFormField(label = "%multiple", properties = {"showAsSwitcher=true"})
	public boolean multiple();

	@DDMFormField(
		dataType = "ddm-options", label = "%options",
		properties = {"showLabel=false"}, type = "options"
	)
	public DDMFormFieldOptions options();

	@DDMFormField
	@Override
	public DDMFormFieldValidation validation();

}
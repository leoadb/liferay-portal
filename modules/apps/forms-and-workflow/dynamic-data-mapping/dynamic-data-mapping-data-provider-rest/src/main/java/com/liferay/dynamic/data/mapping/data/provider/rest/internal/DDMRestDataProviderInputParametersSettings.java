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

package com.liferay.dynamic.data.mapping.data.provider.rest.internal;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;

/**
 * @author Leonardo Barros
 */
@DDMForm
@DDMFormLayout(
	{
		@DDMFormLayoutPage(
			{
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 4,
							value = {
								"inputParameterName", "inputParameterType",
								"inputParameterRequired"
							}
						)
					}
				)
			}
		)
	}
)
public interface DDMRestDataProviderInputParametersSettings {

	@DDMFormField(
		label = "%name", properties = {"placeholder=%enter-a-name"},
		required = true
	)
	public String inputParameterName();

	@DDMFormField(label = "%required", type = "checkbox")
	public boolean inputParameterRequired();

	@DDMFormField(
		label = "%type", optionLabels = {"%text", "%number"},
		optionValues = {"text", "number"}, type = "select"
	)
	public String inputParameterType();

}
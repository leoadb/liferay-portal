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

package com.liferay.report.definitions.web.internal.portlet.action.forms;

import com.liferay.data.engine.annotation.DataDefinition;
import com.liferay.data.engine.annotation.DataDefinitionField;
import com.liferay.data.engine.annotation.DataLayout;
import com.liferay.data.engine.annotation.DataLayoutColumn;
import com.liferay.data.engine.annotation.DataLayoutPage;
import com.liferay.data.engine.annotation.DataLayoutRow;

/**
 * @author Bruno Basto
 * @author Leonardo Barros
 */
@DataDefinition
@DataLayout(
	{
		@DataLayoutPage(
			{
				@DataLayoutRow(
					{@DataLayoutColumn(size = 12, value = "availableColumns")}
				),
				@DataLayoutRow(
					{@DataLayoutColumn(size = 12, value = "sortColumns")}
				)
			}
		)
	}
)
public interface AvailableColumnsForm {

	@DataDefinitionField(
		fieldType = "fieldset", label = "%available-columns", repeatable = true
	)
	public ColumnsFormFields[] availableColumns();

	@DataDefinitionField(
		fieldType = "fieldset", label = "%sort-columns", repeatable = true
	)
	public ColumnsFormFields[] sortColumns();

}
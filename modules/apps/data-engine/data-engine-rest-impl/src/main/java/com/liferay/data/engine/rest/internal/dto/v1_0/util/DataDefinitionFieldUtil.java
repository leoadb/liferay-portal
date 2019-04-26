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

package com.liferay.data.engine.rest.internal.dto.v1_0.util;

import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.internal.field.type.v1_0.util.CustomPropertyUtil;
import com.liferay.data.engine.spi.field.type.SPIDataDefinitionField;

/**
 * @author Leonardo Barros
 */
public class DataDefinitionFieldUtil {

	public static SPIDataDefinitionField toSPIDataDefinitionField(
		DataDefinitionField dataDefinitionField) {

		SPIDataDefinitionField spiDataDefinitionField =
			new SPIDataDefinitionField();

		spiDataDefinitionField.setCustomProperties(
			CustomPropertyUtil.toMap(
				dataDefinitionField.getCustomProperties()));
		spiDataDefinitionField.setDefaultValue(
			LocalizedValueUtil.toMap(dataDefinitionField.getDefaultValue()));
		spiDataDefinitionField.setFieldType(dataDefinitionField.getFieldType());
		spiDataDefinitionField.setId(dataDefinitionField.getId());
		spiDataDefinitionField.setIndexable(dataDefinitionField.getIndexable());
		spiDataDefinitionField.setLocalizable(
			dataDefinitionField.getLocalizable());
		spiDataDefinitionField.setName(dataDefinitionField.getName());
		spiDataDefinitionField.setRepeatable(
			dataDefinitionField.getRepeatable());
		spiDataDefinitionField.setTip(
			LocalizedValueUtil.toMap(dataDefinitionField.getTip()));

		return spiDataDefinitionField;
	}

}
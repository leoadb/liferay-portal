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

package com.liferay.data.engine.spi.util;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.spi.model.SPIDataRecordCollection;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;

/**
 * @author Jeyvison Nascimento
 */
public class DataRecordCollectionUtil {

	public static SPIDataRecordCollection toSPIDataRecordCollection(
		DDLRecordSet ddlRecordSet) {

		return new SPIDataRecordCollection() {
			{
				setDataDefinitionId(ddlRecordSet.getDDMStructureId());
				setDataRecordCollectionKey(ddlRecordSet.getRecordSetKey());
				setDescription(
					LocalizedValueUtil.toStringObjectMap(
						ddlRecordSet.getDescriptionMap()));
				setId(ddlRecordSet.getRecordSetId());
				setName(
					LocalizedValueUtil.toStringObjectMap(
						ddlRecordSet.getNameMap()));
				setSiteId(ddlRecordSet.getGroupId());
			}
		};
	}

}
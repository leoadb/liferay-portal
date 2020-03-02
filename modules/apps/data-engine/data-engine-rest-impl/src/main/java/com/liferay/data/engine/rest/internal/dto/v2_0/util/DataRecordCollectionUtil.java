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

package com.liferay.data.engine.rest.internal.dto.v2_0.util;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.dto.v2_0.DataRecordCollection;
import com.liferay.data.engine.spi.model.SPIDataRecordCollection;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;

/**
 * @author Jeyvison Nascimento
 */
public class DataRecordCollectionUtil {

	public static DataRecordCollection toDataRecordCollection(
		DDLRecordSet ddlRecordSet) {

		return new DataRecordCollection() {
			{
				dataDefinitionId = ddlRecordSet.getDDMStructureId();
				dataRecordCollectionKey = ddlRecordSet.getRecordSetKey();
				description = LocalizedValueUtil.toStringObjectMap(
					ddlRecordSet.getDescriptionMap());
				id = ddlRecordSet.getRecordSetId();
				name = LocalizedValueUtil.toStringObjectMap(
					ddlRecordSet.getNameMap());
				siteId = ddlRecordSet.getGroupId();
			}
		};
	}

	public static DataRecordCollection toDataRecordCollection(
		SPIDataRecordCollection spiDataRecordCollection) {

		return new DataRecordCollection() {
			{
				dataDefinitionId =
					spiDataRecordCollection.getDataDefinitionId();
				dataRecordCollectionKey =
					spiDataRecordCollection.getDataRecordCollectionKey();
				description = spiDataRecordCollection.getDescription();
				id = spiDataRecordCollection.getId();
				name = spiDataRecordCollection.getName();
				siteId = spiDataRecordCollection.getSiteId();
			}
		};
	}

	public static SPIDataRecordCollection toSPIDataRecordCollection(
		DataRecordCollection dataRecordCollection) {

		SPIDataRecordCollection spiDataRecordCollection =
			new SPIDataRecordCollection();

		spiDataRecordCollection.setDataDefinitionId(
			dataRecordCollection.getDataDefinitionId());
		spiDataRecordCollection.setDataRecordCollectionKey(
			dataRecordCollection.getDataRecordCollectionKey());
		spiDataRecordCollection.setDescription(
			dataRecordCollection.getDescription());
		spiDataRecordCollection.setId(dataRecordCollection.getId());
		spiDataRecordCollection.setName(dataRecordCollection.getName());
		spiDataRecordCollection.setSiteId(dataRecordCollection.getSiteId());

		return spiDataRecordCollection;
	}

}
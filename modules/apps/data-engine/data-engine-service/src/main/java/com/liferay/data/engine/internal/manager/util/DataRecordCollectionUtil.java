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

package com.liferay.data.engine.internal.manager.util;

import com.liferay.data.engine.DataRecordCollection;
import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Leonardo Barros
 */
public class DataRecordCollectionUtil {

	public static DataRecordCollection toDataRecordCollection(
		DDLRecordSet ddlRecordSet) {

		return new DataRecordCollection() {
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

	public static List<DataRecordCollection> toDataRecordCollections(
		List<DDLRecordSet> ddlRecordSets) {

		List<DataRecordCollection> dataRecordCollections = new ArrayList<>();

		for (DDLRecordSet ddlRecordSet : ddlRecordSets) {
			dataRecordCollections.add(toDataRecordCollection(ddlRecordSet));
		}

		return dataRecordCollections;
	}

}
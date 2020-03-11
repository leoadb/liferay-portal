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

import com.liferay.data.engine.rest.dto.v2_0.DataRecordCollection;

/**
 * @author Jeyvison Nascimento
 */
public class DataRecordCollectionUtil {

	public static DataRecordCollection toDataRecordCollection(
		com.liferay.data.engine.DataRecordCollection dataRecordCollection) {

		return new DataRecordCollection() {
			{
				dataDefinitionId = dataRecordCollection.getDataDefinitionId();
				dataRecordCollectionKey =
					dataRecordCollection.getDataRecordCollectionKey();
				description = dataRecordCollection.getDescription();
				id = dataRecordCollection.getId();
				name = dataRecordCollection.getName();
				siteId = dataRecordCollection.getSiteId();
			}
		};
	}

	public static com.liferay.data.engine.DataRecordCollection
		toDataRecordCollection(DataRecordCollection dataRecordCollection) {

		return new com.liferay.data.engine.DataRecordCollection() {
			{
				setDataDefinitionId(dataRecordCollection.getDataDefinitionId());
				setDataRecordCollectionKey(
					dataRecordCollection.getDataRecordCollectionKey());
				setDescription(dataRecordCollection.getDescription());
				setId(dataRecordCollection.getId());
				setName(dataRecordCollection.getName());
				setSiteId(dataRecordCollection.getSiteId());
			}
		};
	}

}
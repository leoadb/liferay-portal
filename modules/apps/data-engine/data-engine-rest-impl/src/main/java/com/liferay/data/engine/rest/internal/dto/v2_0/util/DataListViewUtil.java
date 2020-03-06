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

import com.liferay.data.engine.rest.dto.v2_0.DataListView;
import com.liferay.data.engine.spi.model.SPIDataListView;

/**
 * @author Bruno Farache
 */
public class DataListViewUtil {

	public static DataListView toDataListView(SPIDataListView spiDataListView) {
		if (spiDataListView == null) {
			return null;
		}

		return new DataListView() {
			{
				appliedFilters = spiDataListView.getAppliedFilters();
				dataDefinitionId = spiDataListView.getDataDefinitionId();
				dateCreated = spiDataListView.getDateCreated();
				dateModified = spiDataListView.getDateModified();
				fieldNames = spiDataListView.getFieldNames();
				id = spiDataListView.getId();
				name = spiDataListView.getName();
				siteId = spiDataListView.getSiteId();
				sortField = spiDataListView.getSortField();
				userId = spiDataListView.getUserId();
			}
		};
	}

	public static SPIDataListView toSPIDataListView(DataListView dataListView) {
		SPIDataListView spiDataListView = new SPIDataListView();

		spiDataListView.setAppliedFilters(dataListView.getAppliedFilters());
		spiDataListView.setDataDefinitionId(dataListView.getDataDefinitionId());
		spiDataListView.setDateCreated(dataListView.getDateCreated());
		spiDataListView.setDateModified(dataListView.getDateModified());
		spiDataListView.setFieldNames(dataListView.getFieldNames());
		spiDataListView.setId(dataListView.getId());
		spiDataListView.setName(dataListView.getName());
		spiDataListView.setSiteId(dataListView.getSiteId());
		spiDataListView.setSortField(dataListView.getSortField());
		spiDataListView.setUserId(dataListView.getUserId());

		return spiDataListView;
	}

}
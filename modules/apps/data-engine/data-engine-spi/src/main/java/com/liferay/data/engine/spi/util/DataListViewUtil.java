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
import com.liferay.data.engine.model.DEDataListView;
import com.liferay.data.engine.spi.model.SPIDataListView;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Jeyvison Nascimento
 */
public class DataListViewUtil {

	public static SPIDataListView toSPIDataListView(
			DEDataListView deDataListView)
		throws Exception {

		return new SPIDataListView() {
			{
				setAppliedFilters(_toMap(deDataListView.getAppliedFilters()));
				setDataDefinitionId(deDataListView.getDdmStructureId());
				setDateCreated(deDataListView.getCreateDate());
				setDateModified(deDataListView.getModifiedDate());
				setFieldNames(
					JSONUtil.toStringArray(
						JSONFactoryUtil.createJSONArray(
							deDataListView.getFieldNames())));
				setId(deDataListView.getDeDataListViewId());
				setName(
					LocalizedValueUtil.toStringObjectMap(
						deDataListView.getNameMap()));
				setSiteId(deDataListView.getGroupId());
				setSortField(deDataListView.getSortField());
				setUserId(deDataListView.getUserId());
			}
		};
	}

	private static Map<String, Object> _toMap(String json) throws Exception {
		Map<String, Object> map = new HashMap<>();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

		Set<String> keySet = jsonObject.keySet();

		Iterator<String> iterator = keySet.iterator();

		while (iterator.hasNext()) {
			String key = iterator.next();

			if (jsonObject.get(key) instanceof JSONObject) {
				map.put(
					key,
					_toMap(
						jsonObject.get(
							key
						).toString()));
			}
			else {
				map.put(key, jsonObject.get(key));
			}
		}

		return map;
	}

}
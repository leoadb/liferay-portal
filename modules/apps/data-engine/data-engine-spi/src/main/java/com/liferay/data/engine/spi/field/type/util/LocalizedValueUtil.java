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

package com.liferay.data.engine.spi.field.type.util;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class LocalizedValueUtil {

	public static <V> JSONObject toJSONObject(Map<String, V> map) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (map == null) {
			return jsonObject;
		}

		for (Map.Entry<String, V> entry : map.entrySet()) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}

		return jsonObject;
	}

	public static <V> Map<String, V> toLocalizationMap(JSONObject jsonObject) {
		Map<String, V> localizationMap = new HashMap<>();

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String key = keys.next();

			localizationMap.put(key, (V)jsonObject.get(key));
		}

		return localizationMap;
	}

}
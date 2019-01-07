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

package com.liferay.dynamic.data.mapping.form.renderer.internal.util;

import com.liferay.dynamic.data.mapping.form.renderer.internal.DDMFormRendererImpl;
import com.liferay.portal.kernel.cache.thread.local.Lifecycle;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCache;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;

/**
 * @author Bruno Basto
 */
public class DDMFormFieldTypesThreadLocal {

	public static boolean isFieldTypesProvided() {
		if (_threadLocalCache.get("fieldTypesProvided") == Boolean.TRUE) {
			return true;
		}

		return false;
	}

	public static boolean isFieldTypesRequested() {
		if (_threadLocalCache.get("fieldTypesRequested") == Boolean.TRUE) {
			return true;
		}

		return false;
	}

	public static void removeAll() {
		_threadLocalCache.removeAll();
	}

	public static void setFieldTypesProvided(boolean fieldTypesProvided) {
		_threadLocalCache.put("fieldTypesProvided", fieldTypesProvided);
	}

	public static void setFieldTypesRequested(boolean fieldTypesRequested) {
		_threadLocalCache.put("fieldTypesRequested", fieldTypesRequested);
	}

	private static final ThreadLocalCache<Boolean> _threadLocalCache =
		ThreadLocalCacheManager.getThreadLocalCache(
			Lifecycle.REQUEST, DDMFormRendererImpl.class.getName());

}
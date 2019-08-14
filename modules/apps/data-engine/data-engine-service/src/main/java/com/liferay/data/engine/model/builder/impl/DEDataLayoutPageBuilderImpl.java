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

package com.liferay.data.engine.model.builder.impl;

import com.liferay.data.engine.model.DEDataLayoutPage;
import com.liferay.data.engine.model.DEDataLayoutRow;
import com.liferay.data.engine.model.builder.DEDataLayoutPageBuilder;
import com.liferay.data.engine.model.impl.DEDataLayoutPageImpl;

import java.util.List;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DEDataLayoutPageBuilderImpl implements DEDataLayoutPageBuilder {

	@Override
	public DEDataLayoutPage build() {
		return _instance;
	}

	@Override
	public DEDataLayoutPageBuilder description(
		Map<String, Object> description) {

		_instance.setDescription(description);

		return this;
	}

	@Override
	public DEDataLayoutPageBuilder rows(
		List<DEDataLayoutRow> deDataLayoutRows) {

		_instance.setDEDataLayoutRows(deDataLayoutRows);

		return this;
	}

	@Override
	public DEDataLayoutPageBuilder title(Map<String, Object> title) {
		_instance.setTitle(title);

		return this;
	}

	private final DEDataLayoutPage _instance = new DEDataLayoutPageImpl();

}
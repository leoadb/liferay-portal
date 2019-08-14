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

import com.liferay.data.engine.model.DEDataLayoutColumn;
import com.liferay.data.engine.model.builder.DEDataLayoutColumnBuilder;
import com.liferay.data.engine.model.impl.DEDataLayoutColumnImpl;

/**
 * @author Leonardo Barros
 */
public class DEDataLayoutColumnBuilderImpl
	implements DEDataLayoutColumnBuilder {

	@Override
	public DEDataLayoutColumn build() {
		return _instance;
	}

	@Override
	public DEDataLayoutColumnBuilder columnSize(int columnSize) {
		_instance.setColumnSize(columnSize);

		return this;
	}

	@Override
	public DEDataLayoutColumnBuilder fieldNames(String[] fieldNames) {
		_instance.setFieldNames(fieldNames);

		return this;
	}

	private final DEDataLayoutColumn _instance = new DEDataLayoutColumnImpl();

}
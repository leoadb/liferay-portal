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

import com.liferay.data.engine.model.builder.DEDataLayoutBuilder;
import com.liferay.data.engine.model.builder.DEDataLayoutColumnBuilder;
import com.liferay.data.engine.model.builder.DEDataLayoutPageBuilder;
import com.liferay.data.engine.model.builder.DEDataLayoutRowBuilder;
import com.liferay.data.engine.model.builder.DEModelBuilderFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DEModelBuilderFactory.class)
public class DEModelBuilderFactoryImpl implements DEModelBuilderFactory {

	@Override
	public DEDataLayoutBuilder newDEDataLayoutBuilder() {
		return new DEDataLayoutBuilderImpl();
	}

	@Override
	public DEDataLayoutColumnBuilder newDEDataLayoutColumnBuilder() {
		return new DEDataLayoutColumnBuilderImpl();
	}

	@Override
	public DEDataLayoutPageBuilder newDEDataLayoutPageBuilder() {
		return new DEDataLayoutPageBuilderImpl();
	}

	@Override
	public DEDataLayoutRowBuilder newDEDataLayoutRowBuilder() {
		return new DEDataLayoutRowBuilderImpl();
	}

}
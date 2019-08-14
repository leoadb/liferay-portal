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

import com.liferay.data.engine.model.DEDataLayout;
import com.liferay.data.engine.model.DEDataLayoutPage;
import com.liferay.data.engine.model.builder.DEDataLayoutBuilder;
import com.liferay.data.engine.model.impl.DEDataLayoutImpl;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DEDataLayoutBuilderImpl implements DEDataLayoutBuilder {

	@Override
	public DEDataLayout build() {
		return _instance;
	}

	@Override
	public DEDataLayoutBuilder companyId(long companyId) {
		_instance.setCompanyId(companyId);

		return this;
	}

	@Override
	public DEDataLayoutBuilder createDate(Date date) {
		_instance.setCreateDate(date);

		return this;
	}

	@Override
	public DEDataLayoutBuilder dataDefinitionId(long dataDefinitionId) {
		_instance.setDeDataDefinitionId(dataDefinitionId);

		return this;
	}

	@Override
	public DEDataLayoutBuilder dataLayoutKey(String dataLayoutKey) {
		_instance.setDataLayoutKey(dataLayoutKey);

		return this;
	}

	@Override
	public DEDataLayoutBuilder description(Map<Locale, String> description) {
		_instance.setDescriptionMap(description);

		return this;
	}

	@Override
	public DEDataLayoutBuilder groupId(long groupId) {
		_instance.setGroupId(groupId);

		return this;
	}

	@Override
	public DEDataLayoutBuilder id(long id) {
		_instance.setDeDataLayoutId(id);
		_instance.setPrimaryKey(id);

		return this;
	}

	@Override
	public DEDataLayoutBuilder modifiedDate(Date date) {
		_instance.setModifiedDate(date);

		return this;
	}

	@Override
	public DEDataLayoutBuilder name(Map<Locale, String> name) {
		_instance.setNameMap(name);

		return this;
	}

	@Override
	public DEDataLayoutBuilder pages(List<DEDataLayoutPage> deDataLayoutPages) {
		_instance.setDEDataLayoutPages(deDataLayoutPages);

		return this;
	}

	@Override
	public DEDataLayoutBuilder paginationMode(String paginationMode) {
		_instance.setPaginationMode(paginationMode);

		return this;
	}

	@Override
	public DEDataLayoutBuilder userId(long userId) {
		_instance.setUserId(userId);

		return this;
	}

	private final DEDataLayout _instance = new DEDataLayoutImpl();

}
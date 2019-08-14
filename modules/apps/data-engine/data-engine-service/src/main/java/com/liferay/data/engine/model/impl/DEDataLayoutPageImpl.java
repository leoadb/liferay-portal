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

package com.liferay.data.engine.model.impl;

import com.liferay.data.engine.model.DEDataLayoutPage;
import com.liferay.data.engine.model.DEDataLayoutRow;

import java.util.List;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DEDataLayoutPageImpl implements DEDataLayoutPage {

	@Override
	public List<DEDataLayoutRow> getDEDataLayoutRows() {
		return _deDataLayoutRows;
	}

	@Override
	public Map<String, Object> getDescription() {
		return _description;
	}

	@Override
	public Map<String, Object> getTitle() {
		return _title;
	}

	@Override
	public void setDEDataLayoutRows(List<DEDataLayoutRow> deDataLayoutRows) {
		_deDataLayoutRows = deDataLayoutRows;
	}

	@Override
	public void setDescription(Map<String, Object> description) {
		_description = description;
	}

	@Override
	public void setTitle(Map<String, Object> title) {
		_title = title;
	}

	private List<DEDataLayoutRow> _deDataLayoutRows;
	private Map<String, Object> _description;
	private Map<String, Object> _title;

}
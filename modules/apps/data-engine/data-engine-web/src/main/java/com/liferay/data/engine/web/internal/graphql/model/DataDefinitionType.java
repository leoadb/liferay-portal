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

package com.liferay.data.engine.web.internal.graphql.model;

import java.util.List;

/**
 * @author Leonardo Barros
 */
public class DataDefinitionType {

	public String getDataDefinitionId() {
		return _dataDefinitionId;
	}

	public List<LocalizedValueType> getDescriptions() {
		return _descriptions;
	}

	public List<DataDefinitionFieldType> getFields() {
		return _fields;
	}

	public List<LocalizedValueType> getNames() {
		return _names;
	}

	public String getStorageType() {
		return _storageType;
	}

	public void setDataDefinitionId(String dataDefinitionId) {
		_dataDefinitionId = dataDefinitionId;
	}

	public void setDescriptions(List<LocalizedValueType> descriptions) {
		_descriptions = descriptions;
	}

	public void setFields(List<DataDefinitionFieldType> fields) {
		_fields = fields;
	}

	public void setNames(List<LocalizedValueType> names) {
		_names = names;
	}

	public void setStorageType(String storageType) {
		_storageType = storageType;
	}

	private String _dataDefinitionId;
	private List<LocalizedValueType> _descriptions;
	private List<DataDefinitionFieldType> _fields;
	private List<LocalizedValueType> _names;
	private String _storageType = "json";

}
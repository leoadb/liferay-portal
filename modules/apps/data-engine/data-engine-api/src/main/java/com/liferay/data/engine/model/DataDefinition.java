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

package com.liferay.data.engine.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.util.HashUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Leonardo Barros
 */
public final class DataDefinition implements ClassedModel, Serializable {

	public DataDefinition() {
	}

	public void addColumn(DataDefinitionColumn dataDefinitionColumn) {
		_columns.add(dataDefinitionColumn);
	}

	public void addDescription(String languageId, String description) {
		_description.put(languageId, description);
	}

	public void addName(String languageId, String name) {
		_name.put(languageId, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DataDefinition)) {
			return false;
		}

		DataDefinition dataDefinition = (DataDefinition)obj;

		if (Objects.equals(_name, dataDefinition._name) &&
			Objects.equals(_description, dataDefinition._description) &&
			Objects.equals(
				_dataDefinitionId, dataDefinition._dataDefinitionId) &&
			Objects.equals(_storageType, dataDefinition._storageType) &&
			Objects.equals(_columns, dataDefinition._columns)) {

			return true;
		}

		return false;
	}

	public List<DataDefinitionColumn> getColumns() {
		return Collections.unmodifiableList(_columns);
	}

	public long getDataDefinitionId() {
		return _dataDefinitionId;
	}

	public Map<String, String> getDescription() {
		return Collections.unmodifiableMap(_description);
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Class<?> getModelClass() {
		return DataDefinition.class;
	}

	@Override
	public String getModelClassName() {
		return DataDefinition.class.getName();
	}

	public Map<String, String> getName() {
		return Collections.unmodifiableMap(_name);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _dataDefinitionId;
	}

	public String getStorageType() {
		return _storageType;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _name.hashCode());

		hash = HashUtil.hash(hash, _description.hashCode());

		hash = HashUtil.hash(hash, _dataDefinitionId);

		hash = HashUtil.hash(hash, _storageType.hashCode());

		return HashUtil.hash(hash, _columns.hashCode());
	}

	public void setDataDefinitionId(long dataDefinitionId) {
		_dataDefinitionId = dataDefinitionId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_dataDefinitionId = ((Long)primaryKeyObj).longValue();
	}

	public void setStorageType(String storageType) {
		_storageType = storageType;
	}

	private final List<DataDefinitionColumn> _columns = new ArrayList<>();
	private long _dataDefinitionId;
	private final Map<String, String> _description = new HashMap<>();
	private final Map<String, String> _name = new HashMap<>();
	private String _storageType = "json";

}
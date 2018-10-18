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

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public final class DataCollection implements ClassedModel, Serializable {

	public long getDataCollectionId() {
		return _dataCollectionId;
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
		return DataCollection.class;
	}

	@Override
	public String getModelClassName() {
		return DataCollection.class.getName();
	}

	public Map<String, String> getName() {
		return Collections.unmodifiableMap(_name);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _dataCollectionId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_dataCollectionId = ((Long)primaryKeyObj).longValue();
	}

	private long _dataCollectionId;
	private long _dataDefinitionId;
	private final Map<String, String> _description = new HashMap<>();
	private final Map<String, String> _name = new HashMap<>();

}
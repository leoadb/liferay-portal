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

package com.liferay.data.engine;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DataRecordCollection implements Serializable {

	public Long getDataDefinitionId() {
		return _dataDefinitionId;
	}

	public String getDataRecordCollectionKey() {
		return _dataRecordCollectionKey;
	}

	public Map<String, Object> getDescription() {
		return _description;
	}

	public Long getId() {
		return _id;
	}

	public Map<String, Object> getName() {
		return _name;
	}

	public Long getSiteId() {
		return _siteId;
	}

	public void setDataDefinitionId(Long dataDefinitionId) {
		_dataDefinitionId = dataDefinitionId;
	}

	public void setDataRecordCollectionKey(String dataRecordCollectionKey) {
		_dataRecordCollectionKey = dataRecordCollectionKey;
	}

	public void setDescription(Map<String, Object> description) {
		_description = description;
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setName(Map<String, Object> name) {
		_name = name;
	}

	public void setSiteId(Long siteId) {
		_siteId = siteId;
	}

	private Long _dataDefinitionId;
	private String _dataRecordCollectionKey;
	private Map<String, Object> _description;
	private Long _id;
	private Map<String, Object> _name;
	private Long _siteId;

}
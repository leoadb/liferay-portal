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

package com.liferay.data.engine.spi.model;

import java.util.Map;

/**
 * @author Jeyvison Nascimento
 */
public class SPIDataRecordCollection {

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

	public Long getSPIDataDefinitionId() {
		return _spiDataDefinitionId;
	}

	public String getSPIDataRecordCollectionKey() {
		return _spiDataRecordCollectionKey;
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

	public void setSPIDataDefinitionId(Long spiDataDefinitionId) {
		_spiDataDefinitionId = spiDataDefinitionId;
	}

	public void setSPIDataRecordCollectionKey(String dataRecordCollectionKey) {
		_spiDataRecordCollectionKey = dataRecordCollectionKey;
	}

	private Map<String, Object> _description;
	private Long _id;
	private Map<String, Object> _name;
	private Long _siteId;
	private Long _spiDataDefinitionId;
	private String _spiDataRecordCollectionKey;

}
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

import java.util.Date;
import java.util.Map;

/**
 * @author Jeyvison Nascimento
 */
public class SPIDataListView {

	public Map<String, Object> getAppliedFilters() {
		return _appliedFilters;
	}

	public Long getDataDefinitionId() {
		return _dataDefinitionId;
	}

	public Date getDateCreated() {
		return _dateCreated;
	}

	public Date getDateModified() {
		return _dateModified;
	}

	public String[] getFieldNames() {
		return _fieldNames;
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

	public String getSortField() {
		return _sortField;
	}

	public Long getUserId() {
		return _userId;
	}

	public void setAppliedFilters(Map<String, Object> appliedFilters) {
		_appliedFilters = appliedFilters;
	}

	public void setDataDefinitionId(Long dataDefinitionId) {
		_dataDefinitionId = dataDefinitionId;
	}

	public void setDateCreated(Date dateCreated) {
		_dateCreated = dateCreated;
	}

	public void setDateModified(Date dateModified) {
		_dateModified = dateModified;
	}

	public void setFieldNames(String[] fieldNames) {
		_fieldNames = fieldNames;
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

	public void setSortField(String sortField) {
		_sortField = sortField;
	}

	public void setUserId(Long userId) {
		_userId = userId;
	}

	private Map<String, Object> _appliedFilters;
	private Long _dataDefinitionId;
	private Date _dateCreated;
	private Date _dateModified;
	private String[] _fieldNames;
	private Long _id;
	private Map<String, Object> _name;
	private Long _siteId;
	private String _sortField;
	private Long _userId;

}
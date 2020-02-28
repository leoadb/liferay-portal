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
public class SPIDataDefinition {

	public String[] getAvailableLanguageIds() {
		return _availableLanguageIds;
	}

	public String getContentType() {
		return _contentType;
	}

	public String getDataDefinitionKey() {
		return _dataDefinitionKey;
	}

	public Date getDateCreated() {
		return _dateCreated;
	}

	public Date getDateModified() {
		return _dateModified;
	}

	public String getDefaultLanguageId() {
		return _defaultLanguageId;
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

	public SPIDataDefinitionField[] getSPIDataDefinitionFields() {
		return _spiDataDefinitionFields;
	}

	public SPIDataLayout getSPIDefaultDataLayout() {
		return _spiDefaultDataLayout;
	}

	public String getStorageType() {
		return _storageType;
	}

	public Long getUserId() {
		return _userId;
	}

	public void setAvailableLanguageIds(String[] availableLanguageIds) {
		_availableLanguageIds = availableLanguageIds;
	}

	public void setContentType(String contentType) {
		_contentType = contentType;
	}

	public void setDataDefinitionKey(String dataDefinitionKey) {
		_dataDefinitionKey = dataDefinitionKey;
	}

	public void setDateCreated(Date dateCreated) {
		_dateCreated = dateCreated;
	}

	public void setDateModified(Date dateModified) {
		_dateModified = dateModified;
	}

	public void setDefaultLanguageId(String defaultLanguageId) {
		_defaultLanguageId = defaultLanguageId;
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

	public void setSPIDataDefinitionFields(
		SPIDataDefinitionField[] spiDataDefinitionFields) {

		_spiDataDefinitionFields = spiDataDefinitionFields;
	}

	public void setSPIDefaultDataLayout(SPIDataLayout spiDataLayout) {
		_spiDefaultDataLayout = spiDataLayout;
	}

	public void setStorageType(String storageType) {
		_storageType = storageType;
	}

	public void setUserId(Long userId) {
		_userId = userId;
	}

	private String[] _availableLanguageIds;
	private String _contentType;
	private String _dataDefinitionKey;
	private Date _dateCreated;
	private Date _dateModified;
	private String _defaultLanguageId;
	private Map<String, Object> _description;
	private Long _id;
	private Map<String, Object> _name;
	private Long _siteId;
	private SPIDataDefinitionField[] _spiDataDefinitionFields;
	private SPIDataLayout _spiDefaultDataLayout;
	private String _storageType;
	private Long _userId;

}
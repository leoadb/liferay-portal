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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.data.engine.service.http.DEDataLayoutServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class DEDataLayoutSoap implements Serializable {

	public static DEDataLayoutSoap toSoapModel(DEDataLayout model) {
		DEDataLayoutSoap soapModel = new DEDataLayoutSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setDeDataLayoutId(model.getDeDataLayoutId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setDeDataDefinitionId(model.getDeDataDefinitionId());
		soapModel.setDataLayoutKey(model.getDataLayoutKey());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());

		return soapModel;
	}

	public static DEDataLayoutSoap[] toSoapModels(DEDataLayout[] models) {
		DEDataLayoutSoap[] soapModels = new DEDataLayoutSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DEDataLayoutSoap[][] toSoapModels(DEDataLayout[][] models) {
		DEDataLayoutSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DEDataLayoutSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DEDataLayoutSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DEDataLayoutSoap[] toSoapModels(List<DEDataLayout> models) {
		List<DEDataLayoutSoap> soapModels = new ArrayList<DEDataLayoutSoap>(
			models.size());

		for (DEDataLayout model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DEDataLayoutSoap[soapModels.size()]);
	}

	public DEDataLayoutSoap() {
	}

	public long getPrimaryKey() {
		return _deDataLayoutId;
	}

	public void setPrimaryKey(long pk) {
		setDeDataLayoutId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getDeDataLayoutId() {
		return _deDataLayoutId;
	}

	public void setDeDataLayoutId(long deDataLayoutId) {
		_deDataLayoutId = deDataLayoutId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getDeDataDefinitionId() {
		return _deDataDefinitionId;
	}

	public void setDeDataDefinitionId(long deDataDefinitionId) {
		_deDataDefinitionId = deDataDefinitionId;
	}

	public String getDataLayoutKey() {
		return _dataLayoutKey;
	}

	public void setDataLayoutKey(String dataLayoutKey) {
		_dataLayoutKey = dataLayoutKey;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	private String _uuid;
	private long _deDataLayoutId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _deDataDefinitionId;
	private String _dataLayoutKey;
	private String _name;
	private String _description;

}
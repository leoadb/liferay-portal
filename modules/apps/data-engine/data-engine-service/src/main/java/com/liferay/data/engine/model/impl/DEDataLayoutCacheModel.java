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

import com.liferay.data.engine.model.DEDataLayout;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The cache model class for representing DEDataLayout in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class DEDataLayoutCacheModel
	implements CacheModel<DEDataLayout>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DEDataLayoutCacheModel)) {
			return false;
		}

		DEDataLayoutCacheModel deDataLayoutCacheModel =
			(DEDataLayoutCacheModel)obj;

		if (deDataLayoutId == deDataLayoutCacheModel.deDataLayoutId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, deDataLayoutId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", deDataLayoutId=");
		sb.append(deDataLayoutId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", deDataDefinitionId=");
		sb.append(deDataDefinitionId);
		sb.append(", dataLayoutKey=");
		sb.append(dataLayoutKey);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DEDataLayout toEntityModel() {
		DEDataLayoutImpl deDataLayoutImpl = new DEDataLayoutImpl();

		if (uuid == null) {
			deDataLayoutImpl.setUuid("");
		}
		else {
			deDataLayoutImpl.setUuid(uuid);
		}

		deDataLayoutImpl.setDeDataLayoutId(deDataLayoutId);
		deDataLayoutImpl.setGroupId(groupId);
		deDataLayoutImpl.setCompanyId(companyId);
		deDataLayoutImpl.setUserId(userId);

		if (userName == null) {
			deDataLayoutImpl.setUserName("");
		}
		else {
			deDataLayoutImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			deDataLayoutImpl.setCreateDate(null);
		}
		else {
			deDataLayoutImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			deDataLayoutImpl.setModifiedDate(null);
		}
		else {
			deDataLayoutImpl.setModifiedDate(new Date(modifiedDate));
		}

		deDataLayoutImpl.setDeDataDefinitionId(deDataDefinitionId);

		if (dataLayoutKey == null) {
			deDataLayoutImpl.setDataLayoutKey("");
		}
		else {
			deDataLayoutImpl.setDataLayoutKey(dataLayoutKey);
		}

		if (name == null) {
			deDataLayoutImpl.setName("");
		}
		else {
			deDataLayoutImpl.setName(name);
		}

		if (description == null) {
			deDataLayoutImpl.setDescription("");
		}
		else {
			deDataLayoutImpl.setDescription(description);
		}

		deDataLayoutImpl.resetOriginalValues();

		return deDataLayoutImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		deDataLayoutId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		deDataDefinitionId = objectInput.readLong();
		dataLayoutKey = objectInput.readUTF();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(deDataLayoutId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(deDataDefinitionId);

		if (dataLayoutKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(dataLayoutKey);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}
	}

	public String uuid;
	public long deDataLayoutId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long deDataDefinitionId;
	public String dataLayoutKey;
	public String name;
	public String description;

}
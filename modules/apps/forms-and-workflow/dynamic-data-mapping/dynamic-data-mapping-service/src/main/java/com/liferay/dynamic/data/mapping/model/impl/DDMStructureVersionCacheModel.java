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

package com.liferay.dynamic.data.mapping.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing DDMStructureVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureVersion
 * @generated
 */
@ProviderType
public class DDMStructureVersionCacheModel implements CacheModel<DDMStructureVersion>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMStructureVersionCacheModel)) {
			return false;
		}

		DDMStructureVersionCacheModel ddmStructureVersionCacheModel = (DDMStructureVersionCacheModel)obj;

		if (structureVersionId == ddmStructureVersionCacheModel.structureVersionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, structureVersionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(43);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", structureVersionId=");
		sb.append(structureVersionId);
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
		sb.append(", structureId=");
		sb.append(structureId);
		sb.append(", version=");
		sb.append(version);
		sb.append(", parentStructureId=");
		sb.append(parentStructureId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", definition=");
		sb.append(definition);
		sb.append(", storageType=");
		sb.append(storageType);
		sb.append(", type=");
		sb.append(type);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DDMStructureVersion toEntityModel() {
		DDMStructureVersionImpl ddmStructureVersionImpl = new DDMStructureVersionImpl();

		if (uuid == null) {
			ddmStructureVersionImpl.setUuid(StringPool.BLANK);
		}
		else {
			ddmStructureVersionImpl.setUuid(uuid);
		}

		ddmStructureVersionImpl.setStructureVersionId(structureVersionId);
		ddmStructureVersionImpl.setGroupId(groupId);
		ddmStructureVersionImpl.setCompanyId(companyId);
		ddmStructureVersionImpl.setUserId(userId);

		if (userName == null) {
			ddmStructureVersionImpl.setUserName(StringPool.BLANK);
		}
		else {
			ddmStructureVersionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			ddmStructureVersionImpl.setCreateDate(null);
		}
		else {
			ddmStructureVersionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			ddmStructureVersionImpl.setModifiedDate(null);
		}
		else {
			ddmStructureVersionImpl.setModifiedDate(new Date(modifiedDate));
		}

		ddmStructureVersionImpl.setStructureId(structureId);

		if (version == null) {
			ddmStructureVersionImpl.setVersion(StringPool.BLANK);
		}
		else {
			ddmStructureVersionImpl.setVersion(version);
		}

		ddmStructureVersionImpl.setParentStructureId(parentStructureId);

		if (name == null) {
			ddmStructureVersionImpl.setName(StringPool.BLANK);
		}
		else {
			ddmStructureVersionImpl.setName(name);
		}

		if (description == null) {
			ddmStructureVersionImpl.setDescription(StringPool.BLANK);
		}
		else {
			ddmStructureVersionImpl.setDescription(description);
		}

		if (definition == null) {
			ddmStructureVersionImpl.setDefinition(StringPool.BLANK);
		}
		else {
			ddmStructureVersionImpl.setDefinition(definition);
		}

		if (storageType == null) {
			ddmStructureVersionImpl.setStorageType(StringPool.BLANK);
		}
		else {
			ddmStructureVersionImpl.setStorageType(storageType);
		}

		ddmStructureVersionImpl.setType(type);
		ddmStructureVersionImpl.setStatus(status);
		ddmStructureVersionImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			ddmStructureVersionImpl.setStatusByUserName(StringPool.BLANK);
		}
		else {
			ddmStructureVersionImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			ddmStructureVersionImpl.setStatusDate(null);
		}
		else {
			ddmStructureVersionImpl.setStatusDate(new Date(statusDate));
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			ddmStructureVersionImpl.setLastPublishDate(null);
		}
		else {
			ddmStructureVersionImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		ddmStructureVersionImpl.resetOriginalValues();

		ddmStructureVersionImpl.setDDMForm(_ddmForm);

		return ddmStructureVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {
		uuid = objectInput.readUTF();

		structureVersionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		structureId = objectInput.readLong();
		version = objectInput.readUTF();

		parentStructureId = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		definition = objectInput.readUTF();
		storageType = objectInput.readUTF();

		type = objectInput.readInt();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
		lastPublishDate = objectInput.readLong();

		_ddmForm = (com.liferay.dynamic.data.mapping.model.DDMForm)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(structureVersionId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(structureId);

		if (version == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(version);
		}

		objectOutput.writeLong(parentStructureId);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (definition == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(definition);
		}

		if (storageType == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(storageType);
		}

		objectOutput.writeInt(type);

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
		objectOutput.writeLong(lastPublishDate);

		objectOutput.writeObject(_ddmForm);
	}

	public String uuid;
	public long structureVersionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long structureId;
	public String version;
	public long parentStructureId;
	public String name;
	public String description;
	public String definition;
	public String storageType;
	public int type;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
	public long lastPublishDate;
	public com.liferay.dynamic.data.mapping.model.DDMForm _ddmForm;
}
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

package com.liferay.data.engine.internal.executor;

import com.liferay.data.engine.exception.DEDataDefinitionDeserializerException;
import com.liferay.data.engine.exception.DEDataRecordCollectionException;
import com.liferay.data.engine.internal.io.DEDataDefinitionDeserializerTracker;
import com.liferay.data.engine.internal.io.DEDataLayoutDeserializerTracker;
import com.liferay.data.engine.internal.storage.DEDataStorageTracker;
import com.liferay.data.engine.io.DEDataDefinitionDeserializer;
import com.liferay.data.engine.io.DEDataDefinitionDeserializerApplyRequest;
import com.liferay.data.engine.io.DEDataDefinitionDeserializerApplyResponse;
import com.liferay.data.engine.io.DEDataLayoutDeserializer;
import com.liferay.data.engine.io.DEDataLayoutDeserializerApplyRequest;
import com.liferay.data.engine.io.DEDataLayoutDeserializerApplyResponse;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataLayout;
import com.liferay.data.engine.model.DEDataRecord;
import com.liferay.data.engine.model.DEDataRecordCollection;
import com.liferay.data.engine.storage.DEDataStorage;
import com.liferay.data.engine.storage.DEDataStorageGetRequest;
import com.liferay.data.engine.storage.DEDataStorageGetResponse;
import com.liferay.data.engine.storage.DEDataStorageRequestBuilder;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Leonardo Barros
 */
public class DEDataEngineRequestExecutor {

	public DEDataEngineRequestExecutor(
		DDMStructureVersionLocalService ddmStructureVersionLocalService,
		DEDataDefinitionDeserializerTracker deDataDefinitionDeserializerTracker,
		DEDataLayoutDeserializerTracker deDataLayoutDeserializerTracker,
		DEDataStorageTracker deDataStorageTracker) {

		_ddmStructureVersionLocalService = ddmStructureVersionLocalService;
		_deDataDefinitionDeserializerTracker =
			deDataDefinitionDeserializerTracker;
		_deDataLayoutDeserializerTracker = deDataLayoutDeserializerTracker;
		_deDataStorageTracker = deDataStorageTracker;
	}

	public DEDataRecord map(DDLRecord ddlRecord) throws PortalException {
		DEDataRecordCollection deDataRecordCollection = map(
			ddlRecord.getRecordSet());

		DEDataDefinition deDataDefinition =
			deDataRecordCollection.getDEDataDefinition();

		DEDataStorage deDataStorage = _deDataStorageTracker.getDEDataStorage(
			deDataDefinition.getStorageType());

		if (deDataStorage == null) {
			throw new DEDataRecordCollectionException.NoSuchDataStorage(
				deDataDefinition.getStorageType());
		}

		DEDataStorageGetRequest deDataStorageGetRequest =
			DEDataStorageRequestBuilder.getBuilder(
				ddlRecord.getDDMStorageId(), deDataDefinition
			).build();

		DEDataStorageGetResponse deDataStorageGetResponse = deDataStorage.get(
			deDataStorageGetRequest);

		DEDataRecord deDataRecord = new DEDataRecord();

		deDataRecord.setDEDataRecordCollection(deDataRecordCollection);
		deDataRecord.setDEDataRecordId(ddlRecord.getRecordId());
		deDataRecord.setValues(deDataStorageGetResponse.getValues());

		return deDataRecord;
	}

	public DEDataRecordCollection map(DDLRecordSet ddlRecordSet)
		throws PortalException {

		DEDataRecordCollection deDataRecordCollection =
			new DEDataRecordCollection();

		deDataRecordCollection.setDEDataDefinition(
			map(ddlRecordSet.getDDMStructure()));
		deDataRecordCollection.setDEDataRecordCollectionId(
			ddlRecordSet.getRecordSetId());
		deDataRecordCollection.addDescriptions(
			ddlRecordSet.getDescriptionMap());
		deDataRecordCollection.addNames(ddlRecordSet.getNameMap());

		return deDataRecordCollection;
	}

	public DEDataDefinition map(DDMStructure ddmStructure)
		throws PortalException {

		DEDataDefinition deDataDefinition = deserialize(
			ddmStructure.getDefinition());

		deDataDefinition.addDescriptions(ddmStructure.getDescriptionMap());
		deDataDefinition.addNames(ddmStructure.getNameMap());
		deDataDefinition.setCreateDate(ddmStructure.getCreateDate());
		deDataDefinition.setDEDataDefinitionId(ddmStructure.getStructureId());
		deDataDefinition.setModifiedDate(ddmStructure.getModifiedDate());
		deDataDefinition.setStorageType(ddmStructure.getStorageType());
		deDataDefinition.setUserId(ddmStructure.getUserId());

		return deDataDefinition;
	}

	public DEDataLayout map(DDMStructureLayout ddmStructureLayout)
		throws PortalException {

		DEDataLayoutDeserializer deDataLayoutDeserializer =
			_deDataLayoutDeserializerTracker.getDEDataLayoutSerializer("json");

		DEDataLayout deDataLayout = null;

		DEDataLayoutDeserializerApplyResponse
			deDataLayoutDeserializerApplyResponse =
				deDataLayoutDeserializer.apply(
					DEDataLayoutDeserializerApplyRequest.Builder.of(
						ddmStructureLayout.getDefinition()));

		deDataLayout = deDataLayoutDeserializerApplyResponse.getDEDataLayout();

		deDataLayout.setCreateDate(ddmStructureLayout.getCreateDate());

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getDDMStructureVersion(
				ddmStructureLayout.getStructureVersionId());

		DDMStructure ddmStructure = ddmStructureVersion.getStructure();

		deDataLayout.setDEDataDefinition(map(ddmStructure));

		deDataLayout.setDEDataLayoutId(
			ddmStructureLayout.getStructureLayoutId());
		deDataLayout.setDescription(ddmStructureLayout.getDescriptionMap());
		deDataLayout.setModifiedDate(ddmStructureLayout.getModifiedDate());
		deDataLayout.setName(ddmStructureLayout.getNameMap());
		deDataLayout.setUserId(ddmStructureLayout.getUserId());

		return deDataLayout;
	}

	protected DEDataDefinition deserialize(String content)
		throws DEDataDefinitionDeserializerException {

		DEDataDefinitionDeserializer deDataDefinitionDeserializer =
			_deDataDefinitionDeserializerTracker.
				getDEDataDefinitionDeserializer("json");

		DEDataDefinitionDeserializerApplyRequest
			deDataDefinitionDeserializerApplyRequest =
				DEDataDefinitionDeserializerApplyRequest.Builder.newBuilder(
					content
				).build();

		DEDataDefinitionDeserializerApplyResponse
			deDataDefinitionDeserializerApplyResponse =
				deDataDefinitionDeserializer.apply(
					deDataDefinitionDeserializerApplyRequest);

		return deDataDefinitionDeserializerApplyResponse.getDEDataDefinition();
	}

	private final DDMStructureVersionLocalService
		_ddmStructureVersionLocalService;
	private final DEDataDefinitionDeserializerTracker
		_deDataDefinitionDeserializerTracker;
	private final DEDataLayoutDeserializerTracker
		_deDataLayoutDeserializerTracker;
	private final DEDataStorageTracker _deDataStorageTracker;

}
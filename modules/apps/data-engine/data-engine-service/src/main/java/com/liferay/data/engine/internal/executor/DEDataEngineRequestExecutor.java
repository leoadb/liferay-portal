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

import com.liferay.data.engine.exception.DEDataDefinitionFieldsDeserializerException;
import com.liferay.data.engine.internal.io.DEDataDefinitionFieldsDeserializerTracker;
import com.liferay.data.engine.io.DEDataDefinitionFieldsDeserializer;
import com.liferay.data.engine.io.DEDataDefinitionFieldsDeserializerApplyRequest;
import com.liferay.data.engine.io.DEDataDefinitionFieldsDeserializerApplyResponse;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.data.engine.model.DEDataRecordCollection;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Leonardo Barros
 */
public class DEDataEngineRequestExecutor {

	public DEDataEngineRequestExecutor(
		DEDataDefinitionFieldsDeserializerTracker
			deDataDefinitionFieldsDeserializerTracker) {

		_deDataDefinitionFieldsDeserializerTracker =
			deDataDefinitionFieldsDeserializerTracker;
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

		List<DEDataDefinitionField> deDataDefinitionFields = deserialize(
			ddmStructure.getDefinition());

		DEDataDefinition deDataDefinition = new DEDataDefinition();

		deDataDefinition.setDEDataDefinitionFields(deDataDefinitionFields);
		deDataDefinition.setDEDataDefinitionId(ddmStructure.getStructureId());
		deDataDefinition.addDescriptions(ddmStructure.getDescriptionMap());
		deDataDefinition.addNames(ddmStructure.getNameMap());
		deDataDefinition.setCreateDate(ddmStructure.getCreateDate());
		deDataDefinition.setDEDataDefinitionId(ddmStructure.getStructureId());
		deDataDefinition.setModifiedDate(ddmStructure.getModifiedDate());
		deDataDefinition.setStorageType(ddmStructure.getStorageType());
		deDataDefinition.setUserId(ddmStructure.getUserId());

		return deDataDefinition;
	}

	protected List<DEDataDefinitionField> deserialize(String content)
		throws DEDataDefinitionFieldsDeserializerException {

		DEDataDefinitionFieldsDeserializer deDataDefinitionFieldsDeserializer =
			_deDataDefinitionFieldsDeserializerTracker.
				getDEDataDefinitionFieldsDeserializer("json");

		DEDataDefinitionFieldsDeserializerApplyRequest
			deDataDefinitionFieldsDeserializerApplyRequest =
				DEDataDefinitionFieldsDeserializerApplyRequest.Builder.
					newBuilder(
						content
					).build();

		DEDataDefinitionFieldsDeserializerApplyResponse
			deDataDefinitionFieldsDeserializerApplyResponse =
				deDataDefinitionFieldsDeserializer.apply(
					deDataDefinitionFieldsDeserializerApplyRequest);

		return deDataDefinitionFieldsDeserializerApplyResponse.
			getDeDataDefinitionFields();
	}

	private final DEDataDefinitionFieldsDeserializerTracker
		_deDataDefinitionFieldsDeserializerTracker;

}
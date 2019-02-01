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
import com.liferay.data.engine.internal.io.DEDataDefinitionDeserializerTracker;
import com.liferay.data.engine.io.DEDataDefinitionDeserializer;
import com.liferay.data.engine.io.DEDataDefinitionDeserializerApplyRequest;
import com.liferay.data.engine.io.DEDataDefinitionDeserializerApplyResponse;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.service.DEDataDefinitionListRequest;
import com.liferay.data.engine.service.DEDataDefinitionListResponse;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jeyvison Nascimento
 */
public class DEDataDefinitionListRequestExecutor {

	public DEDataDefinitionListRequestExecutor(
		DDMStructureService ddmStructureService,
		DEDataDefinitionDeserializerTracker
			deDataDefinitionFieldsDeserializerTracker,
		Portal portal) {

		this.ddmStructureService = ddmStructureService;
		this.deDataDefinitionFieldsDeserializerTracker =
			deDataDefinitionFieldsDeserializerTracker;
		this.portal = portal;
	}

	public DEDataDefinitionListResponse execute(
			DEDataDefinitionListRequest deDataDefinitionListRequest)
		throws Exception {

		List<DEDataDefinition> deDataDefinitions = new ArrayList<>();

		List<DDMStructure> ddmStructures = ddmStructureService.getStructures(
			deDataDefinitionListRequest.getCompanyId(),
			new long[] {deDataDefinitionListRequest.getGroupId()},
			portal.getClassNameId(DEDataDefinition.class),
			deDataDefinitionListRequest.getStart(),
			deDataDefinitionListRequest.getEnd(), null);

		for (DDMStructure ddmStructure : ddmStructures) {
			deDataDefinitions.add(map(ddmStructure));
		}

		return DEDataDefinitionListResponse.Builder.of(deDataDefinitions);
	}

	protected DEDataDefinition deserialize(String content)
		throws DEDataDefinitionDeserializerException {

		DEDataDefinitionDeserializer deDataDefinitionFieldsDeserializer =
			deDataDefinitionFieldsDeserializerTracker.
				getDEDataDefinitionDeserializer("json");

		DEDataDefinitionDeserializerApplyRequest
			deDataDefinitionFieldsDeserializerApplyRequest =
				DEDataDefinitionDeserializerApplyRequest.Builder.newBuilder(
					content
				).build();

		DEDataDefinitionDeserializerApplyResponse
			deDataDefinitionFieldsDeserializerApplyResponse =
				deDataDefinitionFieldsDeserializer.apply(
					deDataDefinitionFieldsDeserializerApplyRequest);

		return deDataDefinitionFieldsDeserializerApplyResponse.
			getDEDataDefinition();
	}

	protected DEDataDefinition map(DDMStructure ddmStructure)
		throws DEDataDefinitionDeserializerException {

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

	protected DDMStructureService ddmStructureService;
	protected DEDataDefinitionDeserializerTracker
		deDataDefinitionFieldsDeserializerTracker;
	protected Portal portal;

}
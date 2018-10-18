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

package com.liferay.data.engine.internal.service;

import com.liferay.data.engine.exception.DataDefinitionException;
import com.liferay.data.engine.internal.io.DataDefinitionDeserializerTracker;
import com.liferay.data.engine.internal.io.DataDefinitionSerializerTracker;
import com.liferay.data.engine.io.DataDefinitionDeserializer;
import com.liferay.data.engine.io.DataDefinitionDeserializerApplyRequest;
import com.liferay.data.engine.io.DataDefinitionDeserializerApplyResponse;
import com.liferay.data.engine.io.DataDefinitionSerializer;
import com.liferay.data.engine.io.DataDefinitionSerializerApplyRequest;
import com.liferay.data.engine.io.DataDefinitionSerializerApplyResponse;
import com.liferay.data.engine.model.DataDefinition;
import com.liferay.data.engine.model.DataDefinitionColumn;
import com.liferay.data.engine.service.DataDefinitionDeleteRequest;
import com.liferay.data.engine.service.DataDefinitionDeleteResponse;
import com.liferay.data.engine.service.DataDefinitionGetCountRequest;
import com.liferay.data.engine.service.DataDefinitionGetCountResponse;
import com.liferay.data.engine.service.DataDefinitionGetListRequest;
import com.liferay.data.engine.service.DataDefinitionGetListResponse;
import com.liferay.data.engine.service.DataDefinitionGetRequest;
import com.liferay.data.engine.service.DataDefinitionGetResponse;
import com.liferay.data.engine.service.DataDefinitionLocalService;
import com.liferay.data.engine.service.DataDefinitionSaveRequest;
import com.liferay.data.engine.service.DataDefinitionSaveResponse;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.comparator.StructureNameComparator;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DataDefinitionLocalService.class)
public class DataDefinitionLocalServiceImpl
	implements DataDefinitionLocalService {

	@Override
	public DataDefinitionDeleteResponse delete(
			DataDefinitionDeleteRequest dataDefinitionDeleteRequest)
		throws DataDefinitionException {

		try {
			long dataDefinitionId =
				dataDefinitionDeleteRequest.getDataDefinitionId();

			deleteDDLRecordSet(dataDefinitionId);

			ddmStructureLocalService.deleteDDMStructure(dataDefinitionId);

			return DataDefinitionDeleteResponse.Builder.of(dataDefinitionId);
		}
		catch (Exception e)
		{
			throw new DataDefinitionException(e);
		}
	}

	@Override
	public DataDefinitionGetResponse get(
			DataDefinitionGetRequest dataDefinitionGetRequest)
		throws DataDefinitionException {

		try {
			long dataDefinitionId =
				dataDefinitionGetRequest.getDataDefinitionId();

			DDMStructure ddmStructure = ddmStructureLocalService.getStructure(
				dataDefinitionId);

			return DataDefinitionGetResponse.Builder.of(map(ddmStructure));
		}
		catch (Exception e)
		{
			throw new DataDefinitionException(e);
		}
	}

	@Override
	public DataDefinitionGetCountResponse getCount(
			DataDefinitionGetCountRequest dataDefinitionGetCountRequest)
		throws DataDefinitionException {

		long groupId = dataDefinitionGetCountRequest.getGroupId();
		long classNameId = portal.getClassNameId(DataDefinition.class);

		try {
			int count = ddmStructureLocalService.getStructuresCount(
				groupId, classNameId);

			return DataDefinitionGetCountResponse.Builder.of(count);
		}
		catch (Exception e)
		{
			throw new DataDefinitionException(e);
		}
	}

	@Override
	public DataDefinitionGetListResponse getList(
			DataDefinitionGetListRequest dataDefinitionGetListRequest)
		throws DataDefinitionException {

		long groupId = dataDefinitionGetListRequest.getGroupId();
		long classNameId = portal.getClassNameId(DataDefinition.class);
		int start = dataDefinitionGetListRequest.getStart();
		int end = dataDefinitionGetListRequest.getEnd();

		if (start == 0) {
			start = QueryUtil.ALL_POS;
		}

		if (end == 0) {
			end = QueryUtil.ALL_POS;
		}

		try {
			List<DDMStructure> ddmStructures =
				ddmStructureLocalService.getStructures(
					groupId, classNameId, start, end,
					new StructureNameComparator());

			List<DataDefinition> dataDefinitions = mapToDataDefinitions(
				ddmStructures);

			return DataDefinitionGetListResponse.Builder.of(dataDefinitions);
		}
		catch (Exception e)
		{
			throw new DataDefinitionException(e);
		}
	}

	@Override
	public DataDefinitionSaveResponse save(
			DataDefinitionSaveRequest dataDefinitionSaveRequest)
		throws DataDefinitionException {

		try {
			long userId = dataDefinitionSaveRequest.getUserId();

			long groupId = dataDefinitionSaveRequest.getGroupId();

			long classNameId = portal.getClassNameId(DataDefinition.class);

			DataDefinition dataDefinition =
				dataDefinitionSaveRequest.getDataDefinition();

			long dataDefinitionId = dataDefinition.getDataDefinitionId();

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			if (dataDefinitionId == 0) {
				DDMStructure ddmStructure = createDDMStructure(
					userId, groupId, classNameId, dataDefinition,
					serviceContext);

				dataDefinitionId = ddmStructure.getStructureId();

				resourceLocalService.addModelResources(
					ddmStructure.getCompanyId(), groupId, userId,
					DataDefinition.class.getName(), dataDefinitionId,
					serviceContext.getModelPermissions());

				ddlRecordSetLocalService.addRecordSet(
					userId, groupId, dataDefinitionId,
					String.valueOf(dataDefinitionId), ddmStructure.getNameMap(),
					ddmStructure.getDescriptionMap(), 0,
					DDLRecordSetConstants.SCOPE_DATA_ENGINE, serviceContext);
			}
			else {
				updateDDMStructure(userId, dataDefinition, serviceContext);
			}

			return DataDefinitionSaveResponse.Builder.of(dataDefinitionId);
		}
		catch (Exception e)
		{
			throw new DataDefinitionException(e);
		}
	}

	protected DDMStructure createDDMStructure(
			long userId, long groupId, long classNameId,
			DataDefinition dataDefinition, ServiceContext serviceContext)
		throws PortalException {

		Map<Locale, String> nameMap = createLocalizedMap(
			dataDefinition.getName());

		Map<Locale, String> descriptionMap = createLocalizedMap(
			dataDefinition.getDescription());

		return ddmStructureLocalService.addStructure(
			userId, groupId, classNameId,
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID, null, nameMap,
			descriptionMap, serialize(dataDefinition),
			dataDefinition.getStorageType(), serviceContext);
	}

	protected Map<Locale, String> createLocalizedMap(Map<String, String> map) {
		Set<Map.Entry<String, String>> entrySet = map.entrySet();

		Stream<Map.Entry<String, String>> entryStream = entrySet.stream();

		return entryStream.collect(
			Collectors.toMap(
				entry -> LocaleUtil.fromLanguageId(entry.getKey()),
				entry -> entry.getValue()));
	}

	protected void deleteDDLRecordSet(long dataDefinitionId) {
		ActionableDynamicQuery actionableDynamicQuery =
			ddlRecordSetLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property ddmStructureIdProperty = PropertyFactoryUtil.forName(
					"DDMStructureId");

				dynamicQuery.add(ddmStructureIdProperty.eq(dataDefinitionId));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(DDLRecordSet ddlRecordSet) -> {
				ddlRecordSetLocalService.deleteDDLRecordSet(ddlRecordSet);
			});
	}

	protected List<DataDefinitionColumn> deserialize(String content) {
		DataDefinitionDeserializer dataDefinitionDeserializer =
			dataDefinitionDeserializerTracker.getDataDefinitionDeserializer(
				"json");

		DataDefinitionDeserializerApplyRequest
			dataDefinitionDeserializerApplyRequest =
				DataDefinitionDeserializerApplyRequest.Builder.newBuilder(
					content
				).build();

		DataDefinitionDeserializerApplyResponse
			dataDefinitionDeserializerApplyResponse =
				dataDefinitionDeserializer.apply(
					dataDefinitionDeserializerApplyRequest);

		return dataDefinitionDeserializerApplyResponse.
			getDataDefinitionColumns();
	}

	protected DataDefinition map(DDMStructure ddmStructure) {
		DataDefinition dataDefinition = new DataDefinition();

		List<DataDefinitionColumn> dataDefinitionColumns = deserialize(
			ddmStructure.getDefinition());

		for (DataDefinitionColumn dataDefinitionColumn :
				dataDefinitionColumns) {

			dataDefinition.addColumn(dataDefinitionColumn);
		}

		dataDefinition.setDataDefinitionId(ddmStructure.getStructureId());
		dataDefinition.setStorageType(ddmStructure.getStorageType());

		Map<Locale, String> nameMap = ddmStructure.getNameMap();

		for (Map.Entry<Locale, String> entry : nameMap.entrySet()) {
			dataDefinition.addName(
				LocaleUtil.toLanguageId(entry.getKey()), entry.getValue());
		}

		Map<Locale, String> descriptionMap = ddmStructure.getDescriptionMap();

		for (Map.Entry<Locale, String> entry : descriptionMap.entrySet()) {
			dataDefinition.addDescription(
				LocaleUtil.toLanguageId(entry.getKey()), entry.getValue());
		}

		return dataDefinition;
	}

	protected List<DataDefinition> mapToDataDefinitions(
		List<DDMStructure> ddmStructures) {

		Stream<DDMStructure> ddmStructureStream = ddmStructures.stream();

		return ddmStructureStream.map(
			this::map
		).collect(
			Collectors.toList()
		);
	}

	protected String serialize(DataDefinition dataDefinition) {
		DataDefinitionSerializer dataDefinitionSerializer =
			dataDefinitionSerializerTracker.getDataDefinitionSerializer("json");

		DataDefinitionSerializerApplyRequest
			dataDefinitionSerializerApplyRequest =
				DataDefinitionSerializerApplyRequest.Builder.of(
					dataDefinition.getColumns());

		DataDefinitionSerializerApplyResponse
			dataDefinitionSerializerApplyResponse =
				dataDefinitionSerializer.apply(
					dataDefinitionSerializerApplyRequest);

		return dataDefinitionSerializerApplyResponse.getContent();
	}

	protected void updateDDMStructure(
			long userId, DataDefinition dataDefinition,
			ServiceContext serviceContext)
		throws PortalException {

		Map<Locale, String> nameMap = createLocalizedMap(
			dataDefinition.getName());

		Map<Locale, String> descriptionMap = createLocalizedMap(
			dataDefinition.getDescription());

		ddmStructureLocalService.updateStructure(
			userId, dataDefinition.getDataDefinitionId(),
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID, nameMap,
			descriptionMap, serialize(dataDefinition), serviceContext);
	}

	@Reference
	protected DataDefinitionDeserializerTracker
		dataDefinitionDeserializerTracker;

	@Reference
	protected DataDefinitionSerializerTracker dataDefinitionSerializerTracker;

	@Reference
	protected DDLRecordSetLocalService ddlRecordSetLocalService;

	@Reference
	protected DDMStructureLocalService ddmStructureLocalService;

	@Reference
	protected Portal portal;

	@Reference
	protected ResourceLocalService resourceLocalService;

}
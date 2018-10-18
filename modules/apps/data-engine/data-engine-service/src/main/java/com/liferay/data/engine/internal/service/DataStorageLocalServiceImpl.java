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

import com.liferay.data.engine.exception.DataStorageException;
import com.liferay.data.engine.internal.io.DataExporterTracker;
import com.liferay.data.engine.internal.storage.DataStorageAdapterTracker;
import com.liferay.data.engine.io.DataExporter;
import com.liferay.data.engine.io.DataExporterApplyRequest;
import com.liferay.data.engine.io.DataExporterApplyResponse;
import com.liferay.data.engine.model.DataDefinition;
import com.liferay.data.engine.model.DataRecord;
import com.liferay.data.engine.service.DataDefinitionGetRequest;
import com.liferay.data.engine.service.DataDefinitionGetResponse;
import com.liferay.data.engine.service.DataDefinitionLocalService;
import com.liferay.data.engine.service.DataStorageDeleteRequest;
import com.liferay.data.engine.service.DataStorageDeleteResponse;
import com.liferay.data.engine.service.DataStorageExportRequest;
import com.liferay.data.engine.service.DataStorageExportResponse;
import com.liferay.data.engine.service.DataStorageGetCountRequest;
import com.liferay.data.engine.service.DataStorageGetCountResponse;
import com.liferay.data.engine.service.DataStorageGetListRequest;
import com.liferay.data.engine.service.DataStorageGetListResponse;
import com.liferay.data.engine.service.DataStorageGetRequest;
import com.liferay.data.engine.service.DataStorageGetResponse;
import com.liferay.data.engine.service.DataStorageLocalService;
import com.liferay.data.engine.service.DataStorageSaveRequest;
import com.liferay.data.engine.service.DataStorageSaveResponse;
import com.liferay.data.engine.storage.DataStorageAdapter;
import com.liferay.data.engine.storage.DataStorageAdapterGetRequest;
import com.liferay.data.engine.storage.DataStorageAdapterGetResponse;
import com.liferay.data.engine.storage.DataStorageAdapterSaveRequest;
import com.liferay.data.engine.storage.DataStorageAdapterSaveResponse;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordModifiedDateComparator;
import com.liferay.dynamic.data.mapping.model.DDMContent;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DataStorageLocalService.class)
public class DataStorageLocalServiceImpl implements DataStorageLocalService {

	@Override
	public DataStorageDeleteResponse delete(
			DataStorageDeleteRequest dataStorageDeleteRequest)
		throws DataStorageException {

		try {
			long dataRecordId = dataStorageDeleteRequest.getDataRecordId();
			long dataDefinitionId =
				dataStorageDeleteRequest.getDataDefinitionId();

			if (dataRecordId > 0) {
				return deleteByDataRecordId(dataRecordId);
			}
			else if (dataDefinitionId > 0) {
				return deleteByDataDefinitionId(dataDefinitionId);
			}

			DataStorageDeleteResponse.Builder builder =
				DataStorageDeleteResponse.Builder.newBuilder();

			return builder.build();
		}
		catch (Exception e)
		{
			throw new DataStorageException(e);
		}
	}

	@Override
	public DataStorageExportResponse export(
			DataStorageExportRequest dataStorageExportRequest)
		throws DataStorageException {

		try {
			DataStorageGetListRequest dataStorageGetListRequest =
				DataStorageGetListRequest.Builder.newBuilder(
					dataStorageExportRequest.getDataDefinitionId()
				).start(
					dataStorageExportRequest.getStart()
				).end(
					dataStorageExportRequest.getEnd()
				).build();

			DataStorageGetListResponse dataStorageGetListResponse = getList(
				dataStorageGetListRequest);

			List<DataRecord> dataRecords =
				dataStorageGetListResponse.getDataRecords();

			DataExporter dataExporter = dataExporterTracker.getDataExporter(
				dataStorageExportRequest.getType());

			DataExporterApplyResponse dataExporterApplyResponse =
				dataExporter.apply(
					DataExporterApplyRequest.Builder.of(dataRecords));

			return DataStorageExportResponse.Builder.of(
				dataExporterApplyResponse.getContent());
		}
		catch (DataStorageException dse) {
			throw dse;
		}
		catch (Exception e)
		{
			throw new DataStorageException(e);
		}
	}

	@Override
	public DataStorageGetResponse get(
			DataStorageGetRequest dataStorageGetRequest)
		throws DataStorageException {

		try {
			DDLRecord ddlRecord = ddlRecordLocalService.getDDLRecord(
				dataStorageGetRequest.getDataRecordId());

			return DataStorageGetResponse.Builder.of(map(ddlRecord));
		}
		catch (Exception e)
		{
			throw new DataStorageException(e);
		}
	}

	@Override
	public DataStorageGetCountResponse getCount(
			DataStorageGetCountRequest dataStorageGetCountRequest)
		throws DataStorageException {

		try {
			DDLRecordSet ddlRecordSet = getDDLRecordSet(
				dataStorageGetCountRequest.getDataDefinitionId());

			int count = ddlRecordLocalService.getRecordsCount(
				ddlRecordSet.getRecordSetId());

			return DataStorageGetCountResponse.Builder.of(count);
		}
		catch (Exception e)
		{
			throw new DataStorageException(e);
		}
	}

	@Override
	public DataStorageGetListResponse getList(
			DataStorageGetListRequest dataStorageGetListRequest)
		throws DataStorageException {

		long dataDefinitionId = dataStorageGetListRequest.getDataDefinitionId();
		int start = dataStorageGetListRequest.getStart();
		int end = dataStorageGetListRequest.getEnd();

		if (start == 0) {
			start = QueryUtil.ALL_POS;
		}

		if (end == 0) {
			end = QueryUtil.ALL_POS;
		}

		try {
			DDLRecordSet ddlRecordSet = getDDLRecordSet(dataDefinitionId);

			List<DDLRecord> ddlRecords = ddlRecordLocalService.getRecords(
				ddlRecordSet.getRecordSetId(),
				WorkflowConstants.STATUS_APPROVED, start, end,
				new DDLRecordModifiedDateComparator(false));

			Stream<DDLRecord> stream = ddlRecords.parallelStream();

			List<DataRecord> dataRecords = stream.map(
				this::map
			).collect(
				Collectors.toList()
			);

			return DataStorageGetListResponse.Builder.of(dataRecords);
		}
		catch (Exception e)
		{
			throw new DataStorageException(e);
		}
	}

	@Override
	public DataStorageSaveResponse save(
			DataStorageSaveRequest dataStorageSaveRequest)
		throws DataStorageException {

		try {
			DataRecord dataRecord = dataStorageSaveRequest.getDataRecord();

			//TODO call validation

			long userId = dataStorageSaveRequest.getUserId();

			long groupId = dataStorageSaveRequest.getGroupId();

			long ddlRecordId = dataRecord.getDataRecordId();

			DataDefinition dataDefinition = dataRecord.getDataDefinition();

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			if (ddlRecordId == 0) {
				ddlRecordId = createDDLRecord(
					userId, groupId,
					getDDLRecordSet(dataDefinition.getDataDefinitionId()),
					dataRecord, serviceContext);
			}
			else {
				updateDDLRecord(userId, groupId, dataRecord, serviceContext);
			}

			return DataStorageSaveResponse.Builder.of(ddlRecordId);
		}
		catch (Exception e) {
			throw new DataStorageException(e);
		}
	}

	protected long createDDLRecord(
			long userId, long groupId, DDLRecordSet ddlRecordSet,
			DataRecord dataRecord, ServiceContext serviceContext)
		throws Exception {

		long ddmStorageId = storeDataRecord(
			userId, groupId, dataRecord, serviceContext);

		DDLRecord ddlRecord = ddlRecordLocalService.addRecord(
			userId, groupId, ddlRecordSet.getRecordSetId(), ddmStorageId,
			serviceContext);

		return ddlRecord.getRecordId();
	}

	protected DataStorageDeleteResponse deleteByDataDefinitionId(
			long dataDefinitionId)
		throws Exception {

		DDLRecordSet ddlRecordSet = getDDLRecordSet(dataDefinitionId);

		ddlRecordLocalService.deleteRecords(ddlRecordSet.getRecordSetId());

		return DataStorageDeleteResponse.Builder.newBuilder(
		).dataDefinitionId(
			dataDefinitionId
		).build();
	}

	protected DataStorageDeleteResponse deleteByDataRecordId(long dataRecordId)
		throws Exception {

		ddlRecordLocalService.deleteRecord(dataRecordId);

		return DataStorageDeleteResponse.Builder.newBuilder(
		).dataRecordId(
			dataRecordId
		).build();
	}

	protected DataDefinition getDataDefinition(DDLRecord ddlRecord)
		throws Exception {

		DDLRecordSet ddlRecordSet = ddlRecord.getRecordSet();

		DataDefinitionGetRequest dataDefinitionGetRequest =
			DataDefinitionGetRequest.Builder.of(
				ddlRecordSet.getDDMStructureId());

		DataDefinitionGetResponse dataDefinitionGetResponse =
			dataDefinitionLocalService.get(dataDefinitionGetRequest);

		return dataDefinitionGetResponse.getDataDefinition();
	}

	protected DDLRecordSet getDDLRecordSet(long dataDefinitionId)
		throws PortalException {

		DDMStructure ddmStructure = ddmStructureLocalService.getStructure(
			dataDefinitionId);

		return ddlRecordSetLocalService.getRecordSet(
			ddmStructure.getGroupId(),
			String.valueOf(ddmStructure.getStructureId()));
	}

	protected Map<String, Object> getValues(
			DataDefinition dataDefinition, DDLRecord ddlRecord)
		throws Exception {

		DDLRecordVersion latestRecordVersion =
			ddlRecord.getLatestRecordVersion();

		long ddmStorageId = latestRecordVersion.getDDMStorageId();

		DataStorageAdapter dataStorageAdapter =
			dataStorageAdapterTracker.getDataStorageAdapter(
				dataDefinition.getStorageType());

		DataStorageAdapterGetRequest dataStorageAdapterGetRequest =
			DataStorageAdapterGetRequest.Builder.of(
				ddmStorageId, dataDefinition);

		DataStorageAdapterGetResponse dataStorageAdapterGetResponse =
			dataStorageAdapter.get(dataStorageAdapterGetRequest);

		return dataStorageAdapterGetResponse.getValues();
	}

	protected DataRecord map(DDLRecord ddlRecord) {
		try {
			DataDefinition dataDefinition = getDataDefinition(ddlRecord);

			Map<String, Object> values = getValues(dataDefinition, ddlRecord);

			return DataRecord.Builder.newBuilder(
				dataDefinition, values
			).dataRecordId(
				ddlRecord.getRecordId()
			).build();
		}
		catch (Exception e)
		{
			return DataRecord.Builder.of(null, null);
		}
	}

	protected long storeDataRecord(
			long userId, long groupId, DataRecord dataRecord,
			ServiceContext serviceContext)
		throws Exception {

		DataDefinition dataDefinition = dataRecord.getDataDefinition();

		DataStorageAdapter dataStorageAdapter =
			dataStorageAdapterTracker.getDataStorageAdapter(
				dataDefinition.getStorageType());

		DataStorageAdapterSaveRequest dataStorageAdapterSaveRequest =
			DataStorageAdapterSaveRequest.Builder.newBuilder(
				userId, groupId, dataDefinition
			).values(
				dataRecord.getValues()
			).build();

		DataStorageAdapterSaveResponse dataStorageAdapterSaveResponse =
			dataStorageAdapter.save(dataStorageAdapterSaveRequest);

		long ddmStorageId = dataStorageAdapterSaveResponse.getPrimaryKey();

		DDMStructure ddmStructure = ddmStructureLocalService.getStructure(
			dataDefinition.getDataDefinitionId());

		DDMStructureVersion ddmStructureVersion =
			ddmStructure.getLatestStructureVersion();

		ddmStorageLinkLocalService.addStorageLink(
			portal.getClassNameId(DDMContent.class.getName()), ddmStorageId,
			ddmStructureVersion.getStructureVersionId(), serviceContext);

		return ddmStorageId;
	}

	protected void updateDDLRecord(
			long userId, long groupId, DataRecord dataRecord,
			ServiceContext serviceContext)
		throws Exception {

		long ddmStorageId = storeDataRecord(
			userId, groupId, dataRecord, serviceContext);

		ddlRecordLocalService.updateRecord(
			userId, dataRecord.getDataRecordId(), ddmStorageId, serviceContext);
	}

	@Reference
	protected DataDefinitionLocalService dataDefinitionLocalService;

	@Reference
	protected DataExporterTracker dataExporterTracker;

	@Reference
	protected DataStorageAdapterTracker dataStorageAdapterTracker;

	@Reference
	protected DDLRecordLocalService ddlRecordLocalService;

	@Reference
	protected DDLRecordSetLocalService ddlRecordSetLocalService;

	@Reference
	protected DDMStorageLinkLocalService ddmStorageLinkLocalService;

	@Reference
	protected DDMStructureLocalService ddmStructureLocalService;

	@Reference
	protected Portal portal;

}
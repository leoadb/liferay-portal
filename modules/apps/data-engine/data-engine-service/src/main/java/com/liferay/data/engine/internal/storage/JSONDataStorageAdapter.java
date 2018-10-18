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

package com.liferay.data.engine.internal.storage;

import com.liferay.data.engine.internal.io.DataRecordDeserializerTracker;
import com.liferay.data.engine.internal.io.DataRecordSerializerTracker;
import com.liferay.data.engine.io.DataRecordDeserializer;
import com.liferay.data.engine.io.DataRecordDeserializerApplyRequest;
import com.liferay.data.engine.io.DataRecordDeserializerApplyResponse;
import com.liferay.data.engine.io.DataRecordSerializer;
import com.liferay.data.engine.io.DataRecordSerializerApplyRequest;
import com.liferay.data.engine.io.DataRecordSerializerApplyResponse;
import com.liferay.data.engine.model.DataDefinition;
import com.liferay.data.engine.storage.DataStorageAdapter;
import com.liferay.data.engine.storage.DataStorageAdapterDeleteRequest;
import com.liferay.data.engine.storage.DataStorageAdapterDeleteResponse;
import com.liferay.data.engine.storage.DataStorageAdapterGetRequest;
import com.liferay.data.engine.storage.DataStorageAdapterGetResponse;
import com.liferay.data.engine.storage.DataStorageAdapterSaveRequest;
import com.liferay.data.engine.storage.DataStorageAdapterSaveResponse;
import com.liferay.dynamic.data.mapping.model.DDMContent;
import com.liferay.dynamic.data.mapping.service.DDMContentLocalService;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "data.storage.type=json",
	service = DataStorageAdapter.class
)
public class JSONDataStorageAdapter implements DataStorageAdapter {

	@Override
	public DataStorageAdapterDeleteResponse delete(
			DataStorageAdapterDeleteRequest dataStorageAdapterDeleteRequest)
		throws Exception {

		long primaryKey = dataStorageAdapterDeleteRequest.getPrimaryKey();

		ddmContentLocalService.deleteDDMContent(primaryKey);

		return DataStorageAdapterDeleteResponse.Builder.of(primaryKey);
	}

	@Override
	public DataStorageAdapterGetResponse get(
			DataStorageAdapterGetRequest dataStorageAdapterGetRequest)
		throws Exception {

		DDMContent ddmContent = ddmContentLocalService.getContent(
			dataStorageAdapterGetRequest.getPrimaryKey());

		DataRecordDeserializerApplyRequest dataRecordDeserializerApplyRequest =
			DataRecordDeserializerApplyRequest.Builder.of(
				dataStorageAdapterGetRequest.getDataDefinition(),
				ddmContent.getData()
			);

		DataRecordDeserializer dataRecordDeserializer =
			dataDefinitionRecordDeserializerTracker.getDataRecordDeserializer(
				"json");

		DataRecordDeserializerApplyResponse
			dataRecordDeserializerApplyResponse = dataRecordDeserializer.apply(
				dataRecordDeserializerApplyRequest);

		return DataStorageAdapterGetResponse.Builder.of(
			dataRecordDeserializerApplyResponse.getValues());
	}

	@Override
	public DataStorageAdapterSaveResponse save(
			DataStorageAdapterSaveRequest dataStorageAdapterSaveRequest)
		throws Exception {

		DataRecordSerializerApplyRequest dataRecordSerializerApplyRequest =
			DataRecordSerializerApplyRequest.Builder.of(
				dataStorageAdapterSaveRequest.getDataDefinition(),
				dataStorageAdapterSaveRequest.getValues()
			);

		DataRecordSerializer dataRecordSerializer =
			dataDefinitionRecordSerializerTracker.getDataRecordSerializer(
				"json");

		DataRecordSerializerApplyResponse dataRecordSerializerApplyResponse =
			dataRecordSerializer.apply(dataRecordSerializerApplyRequest);

		String content = dataRecordSerializerApplyResponse.getContent();

		long primaryKey = dataStorageAdapterSaveRequest.getPrimaryKey();

		if (primaryKey == 0) {
			primaryKey = insert(
				dataStorageAdapterSaveRequest.getUserId(),
				dataStorageAdapterSaveRequest.getGroupId(), content);
		}
		else {
			update(primaryKey, content);
		}

		return DataStorageAdapterSaveResponse.Builder.of(primaryKey);
	}

	protected long insert(long userId, long groupId, String content)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(userId);

		DDMContent ddmContent = ddmContentLocalService.addContent(
			userId, groupId, DataDefinition.class.getName(), null, content,
			serviceContext);

		return ddmContent.getPrimaryKey();
	}

	protected void update(long primaryKey, String content) throws Exception {
		DDMContent ddmContent = ddmContentLocalService.getContent(primaryKey);

		ddmContent.setModifiedDate(new Date());
		ddmContent.setData(content);

		ddmContentLocalService.updateContent(
			ddmContent.getPrimaryKey(), ddmContent.getName(),
			ddmContent.getDescription(), ddmContent.getData(),
			new ServiceContext());
	}

	@Reference
	protected DataRecordDeserializerTracker
		dataDefinitionRecordDeserializerTracker;

	@Reference
	protected DataRecordSerializerTracker dataDefinitionRecordSerializerTracker;

	@Reference
	protected DDMContentLocalService ddmContentLocalService;

}
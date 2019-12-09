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

package com.liferay.data.engine.internal.adapter.executors;

import com.liferay.data.engine.adapter.DataEngineError;
import com.liferay.data.engine.adapter.DataEngineErrorCode;
import com.liferay.data.engine.adapter.data.record.collection.DataRecordCollectionExecutor;
import com.liferay.data.engine.adapter.data.record.collection.SaveDataRecordCollectionRequest;
import com.liferay.data.engine.adapter.data.record.collection.SaveDataRecordCollectionResponse;
import com.liferay.data.engine.exception.DataEngineException;
import com.liferay.data.engine.internal.security.permission.DataEnginePermissionUtil;
import com.liferay.data.engine.model.DEDataRecordCollection;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Arrays;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DataRecordCollectionExecutor.class)
public class DataRecordCollectionRequestExecutorImpl
	implements DataRecordCollectionExecutor {

	@Override
	public SaveDataRecordCollectionResponse execute(
		SaveDataRecordCollectionRequest saveDataRecordCollectionRequest) {

		DEDataRecordCollection deDataRecordCollection =
			saveDataRecordCollectionRequest.getDEDataRecordCollection();

		try {
			DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
				deDataRecordCollection.getDEDataDefinitionId());

			if (saveDataRecordCollectionRequest.isPermissionAware()) {
				DataEnginePermissionUtil.checkPermission(
					ActionKeys.ACCESS, _groupLocalService,
					ddmStructure.getGroupId());
			}

			ServiceContext serviceContext = new ServiceContext();

			DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.addRecordSet(
				PrincipalThreadLocal.getUserId(), ddmStructure.getGroupId(),
				deDataRecordCollection.getDEDataDefinitionId(),
				deDataRecordCollection.getDEDataRecordCollectionKey(),
				deDataRecordCollection.getName(),
				deDataRecordCollection.getDescription(), 0,
				DDLRecordSetConstants.SCOPE_DATA_ENGINE, serviceContext);

			SaveDataRecordCollectionResponse saveDataRecordCollectionResponse =
				new SaveDataRecordCollectionResponse();

			saveDataRecordCollectionResponse.setDEDataRecordCollection(
				toDEDataRecordCollection(ddlRecordSet));

			return saveDataRecordCollectionResponse;
		}
		catch (NoSuchStructureException nsse) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsse, nsse);
			}

			String errorMessage =
				"No such data definition: " +
					deDataRecordCollection.getDEDataDefinitionId();

			throw new DataEngineException(
				Arrays.asList(
					new DataEngineError(
						DataEngineErrorCode.NO_SUCH_DATA_DEFINITION,
						errorMessage)),
				nsse);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			throw new DataEngineException(
				Arrays.asList(
					new DataEngineError(
						DataEngineErrorCode.
							UNABLE_TO_SAVE_DATA_RECORD_COLLECTION,
						"Unable to save data record collection")),
				e);
		}
	}

	protected DEDataRecordCollection toDEDataRecordCollection(
		DDLRecordSet ddlRecordSet) {

		return DEDataRecordCollection.newBuilder(
			ddlRecordSet.getDDMStructureId(), ddlRecordSet.getRecordSetId()
		).deDataRecordCollectionKey(
			ddlRecordSet.getRecordSetKey()
		).description(
			ddlRecordSet.getDescriptionMap()
		).name(
			ddlRecordSet.getNameMap()
		).build();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataRecordCollectionRequestExecutorImpl.class);

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}
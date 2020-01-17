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

package com.liferay.data.engine.rest.internal.resource.common;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.internal.constants.DataActionKeys;
import com.liferay.data.engine.rest.internal.dto.v2_0.util.DataLayoutUtil;
import com.liferay.data.engine.rest.internal.model.InternalDataDefinition;
import com.liferay.data.engine.rest.internal.resource.util.DataEnginePermissionUtil;
import com.liferay.data.engine.service.DEDataDefinitionFieldLinkLocalService;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.List;

/**
 * @author Leonardo Barros
 */
public class CommonDataLayoutResource<T> {

	public CommonDataLayoutResource(
		DEDataDefinitionFieldLinkLocalService
			deDataDefinitionFieldLinkLocalService,
		DDMFormLayoutSerializer ddmFormLayoutSerializer,
		DDMStructureLayoutLocalService ddmStructureLayoutLocalService,
		DDMStructureLocalService ddmStructureLocalService,
		DDMStructureVersionLocalService ddmStructureVersionLocalService,
		GroupLocalService groupLocalService,
		ModelResourcePermission<InternalDataDefinition> modelResourcePermission,
		UnsafeFunction<DDMStructureLayout, T, Exception>
			transformUnsafeFunction) {

		_deDataDefinitionFieldLinkLocalService =
			deDataDefinitionFieldLinkLocalService;
		_ddmFormLayoutSerializer = ddmFormLayoutSerializer;
		_ddmStructureLayoutLocalService = ddmStructureLayoutLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_ddmStructureVersionLocalService = ddmStructureVersionLocalService;
		_groupLocalService = groupLocalService;
		_modelResourcePermission = modelResourcePermission;
		_transformUnsafeFunction = transformUnsafeFunction;
	}

	public T postDataDefinitionDataLayout(
			Long dataDefinitionId, DataLayout dataLayout)
		throws Exception {

		if (MapUtil.isEmpty(dataLayout.getName())) {
			throw new Exception("Name is required");
		}

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		DataEnginePermissionUtil.checkPermission(
			DataActionKeys.ADD_DATA_DEFINITION, _groupLocalService,
			ddmStructure.getGroupId());

		DDMFormLayout ddmFormLayout = DataLayoutUtil.toDDMFormLayout(
			dataLayout);

		DDMFormLayoutSerializerSerializeRequest.Builder builder =
			DDMFormLayoutSerializerSerializeRequest.Builder.newBuilder(
				ddmFormLayout);

		DDMFormLayoutSerializerSerializeResponse
			ddmFormLayoutSerializerSerializeResponse =
				_ddmFormLayoutSerializer.serialize(builder.build());

		ServiceContext serviceContext = new ServiceContext();

		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.addStructureLayout(
				PrincipalThreadLocal.getUserId(), ddmStructure.getGroupId(),
				ddmStructure.getClassNameId(), dataLayout.getDataLayoutKey(),
				_getDDMStructureVersionId(dataDefinitionId),
				LocalizedValueUtil.toLocaleStringMap(dataLayout.getName()),
				LocalizedValueUtil.toLocaleStringMap(
					dataLayout.getDescription()),
				ddmFormLayoutSerializerSerializeResponse.getContent(),
				serviceContext);

		_addDataDefinitionFieldLinks(
			ddmStructure.getClassNameId(), dataDefinitionId,
			ddmStructureLayout.getStructureLayoutId(),
			ddmFormLayoutSerializerSerializeResponse.getContent(),
			ddmStructureLayout.getGroupId());

		return _transformUnsafeFunction.apply(ddmStructureLayout);
	}

	public T putDataLayout(Long dataLayoutId, DataLayout dataLayout)
		throws Exception {

		if (MapUtil.isEmpty(dataLayout.getName())) {
			throw new Exception("Name is required");
		}

		_modelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			_getDDMStructureId(
				_ddmStructureLayoutLocalService.getStructureLayout(
					dataLayoutId)),
			ActionKeys.UPDATE);

		DDMFormLayout ddmFormLayout = DataLayoutUtil.toDDMFormLayout(
			dataLayout);

		DDMFormLayoutSerializerSerializeRequest.Builder builder =
			DDMFormLayoutSerializerSerializeRequest.Builder.newBuilder(
				ddmFormLayout);

		DDMFormLayoutSerializerSerializeResponse
			ddmFormLayoutSerializerSerializeResponse =
				_ddmFormLayoutSerializer.serialize(builder.build());

		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.updateStructureLayout(
				dataLayoutId,
				_getDDMStructureVersionId(dataLayout.getDataDefinitionId()),
				LocalizedValueUtil.toLocaleStringMap(dataLayout.getName()),
				LocalizedValueUtil.toLocaleStringMap(
					dataLayout.getDescription()),
				ddmFormLayoutSerializerSerializeResponse.getContent(),
				new ServiceContext());

		_deDataDefinitionFieldLinkLocalService.deleteDEDataDefinitionFieldLinks(
			ddmStructureLayout.getClassNameId(),
			ddmStructureLayout.getStructureLayoutId());

		_addDataDefinitionFieldLinks(
			ddmStructureLayout.getClassNameId(),
			dataLayout.getDataDefinitionId(),
			ddmStructureLayout.getStructureLayoutId(),
			ddmFormLayoutSerializerSerializeResponse.getContent(),
			ddmStructureLayout.getGroupId());

		return _transformUnsafeFunction.apply(ddmStructureLayout);
	}

	private void _addDataDefinitionFieldLinks(
		long classNameId, long dataDefinitionId, long dataLayoutId,
		String dataLayoutJSON, long groupId) {

		DocumentContext documentContext = JsonPath.parse(dataLayoutJSON);

		List<String> fieldNames = documentContext.read(
			"$[\"pages\"][*][\"rows\"][*][\"columns\"][*][\"fieldNames\"][*]");

		for (String fieldName : fieldNames) {
			_deDataDefinitionFieldLinkLocalService.addDEDataDefinitionFieldLink(
				groupId, classNameId, dataLayoutId, dataDefinitionId,
				fieldName);
		}
	}

	private long _getDDMStructureId(DDMStructureLayout ddmStructureLayout)
		throws Exception {

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getDDMStructureVersion(
				ddmStructureLayout.getStructureVersionId());

		DDMStructure ddmStructure = ddmStructureVersion.getStructure();

		return ddmStructure.getStructureId();
	}

	private long _getDDMStructureVersionId(Long deDataDefinitionId)
		throws Exception {

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getLatestStructureVersion(
				deDataDefinitionId);

		return ddmStructureVersion.getStructureVersionId();
	}

	private final DDMFormLayoutSerializer _ddmFormLayoutSerializer;
	private final DDMStructureLayoutLocalService
		_ddmStructureLayoutLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DDMStructureVersionLocalService
		_ddmStructureVersionLocalService;
	private final DEDataDefinitionFieldLinkLocalService
		_deDataDefinitionFieldLinkLocalService;
	private final GroupLocalService _groupLocalService;
	private final ModelResourcePermission<InternalDataDefinition>
		_modelResourcePermission;
	private final UnsafeFunction<DDMStructureLayout, T, Exception>
		_transformUnsafeFunction;

}
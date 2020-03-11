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

package com.liferay.data.engine.rest.internal.resource.v2_0;

import com.liferay.data.engine.manager.DataRecordCollectionManager;
import com.liferay.data.engine.rest.dto.v2_0.DataRecordCollection;
import com.liferay.data.engine.rest.internal.constants.DataActionKeys;
import com.liferay.data.engine.rest.internal.dto.v2_0.util.DataRecordCollectionUtil;
import com.liferay.data.engine.rest.internal.security.permission.resource.DataDefinitionModelResourcePermission;
import com.liferay.data.engine.rest.internal.security.permission.resource.DataRecordCollectionModelResourcePermission;
import com.liferay.data.engine.rest.resource.v2_0.DataRecordCollectionResource;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.permission.ModelPermissionsUtil;
import com.liferay.portal.vulcan.permission.Permission;
import com.liferay.portal.vulcan.permission.PermissionUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ValidationException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v2_0/data-record-collection.properties",
	scope = ServiceScope.PROTOTYPE, service = DataRecordCollectionResource.class
)
public class DataRecordCollectionResourceImpl
	extends BaseDataRecordCollectionResourceImpl {

	@Override
	public void deleteDataRecordCollection(Long dataRecordCollectionId)
		throws Exception {

		_dataRecordCollectionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			dataRecordCollectionId, ActionKeys.DELETE);

		_dataRecordCollectionManager.deleteDataRecordCollection(
			dataRecordCollectionId);
	}

	@Override
	public DataRecordCollection getDataDefinitionDataRecordCollection(
			Long dataDefinitionId)
		throws Exception {

		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), dataDefinitionId,
			ActionKeys.VIEW);

		return DataRecordCollectionUtil.toDataRecordCollection(
			_dataRecordCollectionManager.
				getDefaultDataRecordCollectionDataDefinition(dataDefinitionId));
	}

	@Override
	public Page<DataRecordCollection>
			getDataDefinitionDataRecordCollectionsPage(
				Long dataDefinitionId, String keywords, Pagination pagination)
		throws Exception {

		if (pagination.getPageSize() > 250) {
			throw new ValidationException(
				LanguageUtil.format(
					contextAcceptLanguage.getPreferredLocale(),
					"page-size-is-greater-than-x", 250));
		}

		List<com.liferay.data.engine.DataRecordCollection>
			dataRecordCollections =
				_dataRecordCollectionManager.getDataRecordCollections(
					dataDefinitionId, pagination.getEndPosition(), keywords,
					pagination.getStartPosition());

		Stream<com.liferay.data.engine.DataRecordCollection>
			dataRecordCollectionStream = dataRecordCollections.stream();

		return Page.of(
			dataRecordCollectionStream.map(
				DataRecordCollectionUtil::toDataRecordCollection
			).collect(
				Collectors.toList()
			),
			pagination,
			_dataRecordCollectionManager.getDataRecordCollectionsCount(
				dataDefinitionId, keywords));
	}

	@Override
	public DataRecordCollection getDataRecordCollection(
			Long dataRecordCollectionId)
		throws Exception {

		_dataRecordCollectionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			dataRecordCollectionId, ActionKeys.VIEW);

		return DataRecordCollectionUtil.toDataRecordCollection(
			_dataRecordCollectionManager.getDataRecordCollection(
				dataRecordCollectionId));
	}

	@Override
	public String getDataRecordCollectionPermissionByCurrentUser(
			Long dataRecordCollectionId)
		throws Exception {

		JSONArray actionIdsJSONArray = JSONFactoryUtil.createJSONArray();

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			dataRecordCollectionId);

		String resourceName = _getResourceName(ddlRecordSet);

		List<ResourceAction> resourceActions =
			resourceActionLocalService.getResourceActions(resourceName);

		for (ResourceAction resourceAction : resourceActions) {
			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			if (permissionChecker.hasPermission(
					ddlRecordSet.getGroupId(), resourceName,
					dataRecordCollectionId, resourceAction.getActionId())) {

				actionIdsJSONArray.put(resourceAction.getActionId());
			}
		}

		return actionIdsJSONArray.toString();
	}

	@Override
	public Page<Permission> getDataRecordCollectionPermissionsPage(
			Long dataRecordCollectionId, String roleNames)
		throws Exception {

		com.liferay.data.engine.DataRecordCollection dataRecordCollection =
			_dataRecordCollectionManager.getDataRecordCollection(
				dataRecordCollectionId);

		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			dataRecordCollection.getDataDefinitionId(), ActionKeys.PERMISSIONS);

		String resourceName = getPermissionCheckerResourceName(
			dataRecordCollectionId);

		return Page.of(
			transform(
				PermissionUtil.getRoles(
					contextCompany, roleLocalService,
					StringUtil.split(roleNames)),
				role -> PermissionUtil.toPermission(
					contextCompany.getCompanyId(), dataRecordCollectionId,
					resourceActionLocalService.getResourceActions(resourceName),
					resourceName, resourcePermissionLocalService, role)));
	}

	@Override
	public DataRecordCollection
			getSiteDataRecordCollectionByDataRecordCollectionKey(
				Long siteId, String dataRecordCollectionKey)
		throws Exception {

		return DataRecordCollectionUtil.toDataRecordCollection(
			_dataRecordCollectionManager.getDataRecordCollection(
				dataRecordCollectionKey, siteId));
	}

	@Override
	public DataRecordCollection postDataDefinitionDataRecordCollection(
			Long dataDefinitionId, DataRecordCollection dataRecordCollection)
		throws Exception {

		_dataDefinitionModelResourcePermission.checkPortletPermission(
			PermissionThreadLocal.getPermissionChecker(),
			_ddmStructureLocalService.getDDMStructure(dataDefinitionId),
			DataActionKeys.ADD_DATA_RECORD_COLLECTION);

		dataRecordCollection.setDataDefinitionId(dataDefinitionId);

		return DataRecordCollectionUtil.toDataRecordCollection(
			_dataRecordCollectionManager.addDataRecordCollection(
				DataRecordCollectionUtil.toDataRecordCollection(
					dataRecordCollection)));
	}

	@Override
	public DataRecordCollection putDataRecordCollection(
			Long dataRecordCollectionId,
			DataRecordCollection dataRecordCollection)
		throws Exception {

		_dataRecordCollectionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			dataRecordCollectionId, ActionKeys.UPDATE);

		dataRecordCollection.setId(dataRecordCollectionId);

		return DataRecordCollectionUtil.toDataRecordCollection(
			_dataRecordCollectionManager.updateDataRecordCollection(
				DataRecordCollectionUtil.toDataRecordCollection(
					dataRecordCollection)));
	}

	@Override
	public void putDataRecordCollectionPermission(
			Long dataRecordCollectionId, Permission[] permissions)
		throws Exception {

		com.liferay.data.engine.DataRecordCollection dataRecordCollection =
			_dataRecordCollectionManager.getDataRecordCollection(
				dataRecordCollectionId);

		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			dataRecordCollection.getDataDefinitionId(), ActionKeys.PERMISSIONS);

		String resourceName = getPermissionCheckerResourceName(
			dataRecordCollectionId);

		resourcePermissionLocalService.updateResourcePermissions(
			contextCompany.getCompanyId(), 0, resourceName,
			String.valueOf(dataRecordCollectionId),
			ModelPermissionsUtil.toModelPermissions(
				contextCompany.getCompanyId(), permissions,
				dataRecordCollectionId, resourceName,
				resourceActionLocalService, resourcePermissionLocalService,
				roleLocalService));
	}

	@Override
	protected Long getPermissionCheckerGroupId(Object id) throws Exception {
		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			(long)id);

		return ddlRecordSet.getGroupId();
	}

	@Override
	protected String getPermissionCheckerResourceName(Object id)
		throws Exception {

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			(long)id);

		return _getResourceName(ddlRecordSet);
	}

	private String _getResourceName(DDLRecordSet ddlRecordSet)
		throws PortalException {

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		return ResourceActionsUtil.getCompositeModelName(
			_portal.getClassName(ddmStructure.getClassNameId()),
			DDLRecordSet.class.getName());
	}

	@Reference
	private DataDefinitionModelResourcePermission
		_dataDefinitionModelResourcePermission;

	@Reference
	private DataRecordCollectionManager _dataRecordCollectionManager;

	@Reference
	private DataRecordCollectionModelResourcePermission
		_dataRecordCollectionModelResourcePermission;

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private Portal _portal;

}
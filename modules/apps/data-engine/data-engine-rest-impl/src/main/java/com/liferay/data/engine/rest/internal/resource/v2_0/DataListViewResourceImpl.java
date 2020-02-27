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

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.model.DEDataListView;
import com.liferay.data.engine.rest.dto.v2_0.DataListView;
import com.liferay.data.engine.rest.internal.odata.entity.v2_0.DataDefinitionEntityModel;
import com.liferay.data.engine.rest.internal.security.permission.resource.DataDefinitionModelResourcePermission;
import com.liferay.data.engine.rest.resource.v2_0.DataListViewResource;
import com.liferay.data.engine.service.DEDataDefinitionFieldLinkLocalService;
import com.liferay.data.engine.service.DEDataListViewLocalService;
import com.liferay.data.engine.spi.resource.SPIDataListViewResource;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v2_0/data-list-view.properties",
	scope = ServiceScope.PROTOTYPE, service = DataListViewResource.class
)
public class DataListViewResourceImpl
	extends BaseDataListViewResourceImpl implements EntityModelResource {

	@Override
	public void deleteDataListView(Long dataListViewId) throws Exception {
		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			_getDDMStructureId(
				_deDataListViewLocalService.getDEDataListView(dataListViewId)),
			ActionKeys.DELETE);

		SPIDataListViewResource<DataListView> spiDataListViewResource =
			_getSPIDataListViewResource();

		spiDataListViewResource.deleteDataListView(dataListViewId);
	}

	@Override
	public Page<DataListView> getDataDefinitionDataListViewsPage(
			Long dataDefinitionId, String keywords, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		SPIDataListViewResource<DataListView> spiDataListViewResource =
			_getSPIDataListViewResource();

		return spiDataListViewResource.getDataDefinitionDataListViews(
			dataDefinitionId, keywords,
			contextAcceptLanguage.getPreferredLocale(), pagination, sorts);
	}

	@Override
	public DataListView getDataListView(Long dataListViewId) throws Exception {
		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			_getDDMStructureId(
				_deDataListViewLocalService.getDEDataListView(dataListViewId)),
			ActionKeys.VIEW);

		SPIDataListViewResource<DataListView> spiDataListViewResource =
			_getSPIDataListViewResource();

		return spiDataListViewResource.getDataListView(dataListViewId);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public DataListView postDataDefinitionDataListView(
			Long dataDefinitionId, DataListView dataListView)
		throws Exception {

		SPIDataListViewResource<DataListView> spiDataListViewResource =
			_getSPIDataListViewResource();

		return spiDataListViewResource.addDataDefinitionDataListView(
			dataListView.getAppliedFilters(), dataDefinitionId,
			dataListView.getId(), dataListView.getFieldNames(),
			dataListView.getName(), dataListView.getSortField());
	}

	@Override
	public DataListView putDataListView(
			Long dataListViewId, DataListView dataListView)
		throws Exception {

		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			_getDDMStructureId(
				_deDataListViewLocalService.getDEDataListView(dataListViewId)),
			ActionKeys.UPDATE);

		SPIDataListViewResource<DataListView> spiDataListViewResource =
			_getSPIDataListViewResource();

		return spiDataListViewResource.updateDataListView(
			dataListView.getAppliedFilters(),
			dataListView.getDataDefinitionId(), dataListViewId,
			dataListView.getFieldNames(), dataListView.getName(),
			dataListView.getSortField());
	}

	private long _getDDMStructureId(DEDataListView deDataListView) {
		return deDataListView.getDdmStructureId();
	}

	private SPIDataListViewResource<DataListView>
		_getSPIDataListViewResource() {

		return new SPIDataListViewResource<>(
			_ddmStructureLocalService, _deDataDefinitionFieldLinkLocalService,
			_deDataListViewLocalService, this::_toDataListView);
	}

	private DataListView _toDataListView(DEDataListView deDataListView)
		throws Exception {

		return new DataListView() {
			{
				appliedFilters = _toMap(deDataListView.getAppliedFilters());
				dataDefinitionId = deDataListView.getDdmStructureId();
				dateCreated = deDataListView.getCreateDate();
				dateModified = deDataListView.getModifiedDate();
				fieldNames = JSONUtil.toStringArray(
					_jsonFactory.createJSONArray(
						deDataListView.getFieldNames()));
				id = deDataListView.getPrimaryKey();
				name = LocalizedValueUtil.toStringObjectMap(
					deDataListView.getNameMap());
				siteId = deDataListView.getGroupId();
				sortField = deDataListView.getSortField();
				userId = deDataListView.getUserId();
			}
		};
	}

	private Map<String, Object> _toMap(String json) throws Exception {
		Map<String, Object> map = new HashMap<>();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

		Set<String> keySet = jsonObject.keySet();

		Iterator<String> iterator = keySet.iterator();

		while (iterator.hasNext()) {
			String key = iterator.next();

			if (jsonObject.get(key) instanceof JSONObject) {
				map.put(
					key,
					_toMap(
						jsonObject.get(
							key
						).toString()));
			}
			else {
				map.put(key, jsonObject.get(key));
			}
		}

		return map;
	}

	private static final EntityModel _entityModel =
		new DataDefinitionEntityModel();

	@Reference
	private DataDefinitionModelResourcePermission
		_dataDefinitionModelResourcePermission;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DEDataDefinitionFieldLinkLocalService
		_deDataDefinitionFieldLinkLocalService;

	@Reference
	private DEDataListViewLocalService _deDataListViewLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}
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

import com.liferay.data.engine.model.DEDataListView;
import com.liferay.data.engine.rest.dto.v2_0.DataListView;
import com.liferay.data.engine.rest.internal.odata.entity.v2_0.DataDefinitionEntityModel;
import com.liferay.data.engine.rest.internal.security.permission.resource.DataDefinitionModelResourcePermission;
import com.liferay.data.engine.rest.resource.v2_0.DataListViewResource;
import com.liferay.data.engine.service.DEDataDefinitionFieldLinkLocalService;
import com.liferay.data.engine.service.DEDataListViewLocalService;
import com.liferay.data.engine.spi.model.SPIDataListView;
import com.liferay.data.engine.spi.resource.SPIDataListViewResource;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

		SPIDataListViewResource spiDataListViewResource =
			_getSPIDataListViewResource();

		spiDataListViewResource.deleteDataListView(dataListViewId);
	}

	@Override
	public Page<DataListView> getDataDefinitionDataListViewsPage(
			Long dataDefinitionId, String keywords, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		SPIDataListViewResource spiDataListViewResource =
			_getSPIDataListViewResource();

		Page<SPIDataListView> page =
			spiDataListViewResource.getDataDefinitionDataListViewsPage(
				dataDefinitionId, keywords,
				contextAcceptLanguage.getPreferredLocale(), pagination, sorts);

		Collection<SPIDataListView> items = page.getItems();

		Stream<SPIDataListView> stream = items.stream();

		return Page.of(
			stream.map(
				this::_toDataListView
			).collect(
				Collectors.toList()
			),
			pagination, page.getTotalCount());
	}

	@Override
	public DataListView getDataListView(Long dataListViewId) throws Exception {
		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			_getDDMStructureId(
				_deDataListViewLocalService.getDEDataListView(dataListViewId)),
			ActionKeys.VIEW);

		SPIDataListViewResource spiDataListViewResource =
			_getSPIDataListViewResource();

		return _toDataListView(
			spiDataListViewResource.getDataListView(dataListViewId));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public DataListView postDataDefinitionDataListView(
			Long dataDefinitionId, DataListView dataListView)
		throws Exception {

		SPIDataListViewResource spiDataListViewResource =
			_getSPIDataListViewResource();

		return _toDataListView(
			spiDataListViewResource.addDataDefinitionDataListView(
				dataListView.getAppliedFilters(), dataDefinitionId,
				dataListView.getId(), dataListView.getFieldNames(),
				dataListView.getName(), dataListView.getSortField()));
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

		SPIDataListViewResource spiDataListViewResource =
			_getSPIDataListViewResource();

		return _toDataListView(
			spiDataListViewResource.updateDataListView(
				dataListView.getAppliedFilters(),
				dataListView.getDataDefinitionId(), dataListViewId,
				dataListView.getFieldNames(), dataListView.getName(),
				dataListView.getSortField()));
	}

	private long _getDDMStructureId(DEDataListView deDataListView) {
		return deDataListView.getDdmStructureId();
	}

	private SPIDataListViewResource _getSPIDataListViewResource() {
		return new SPIDataListViewResource(
			_ddmStructureLocalService, _deDataDefinitionFieldLinkLocalService,
			_deDataListViewLocalService);
	}

	private DataListView _toDataListView(SPIDataListView spiDataListView) {
		return new DataListView() {
			{
				appliedFilters = spiDataListView.getAppliedFilters();
				dataDefinitionId = spiDataListView.getDataDefinitionId();
				dateCreated = spiDataListView.getDateCreated();
				dateModified = spiDataListView.getDateModified();
				fieldNames = spiDataListView.getFieldNames();
				id = spiDataListView.getId();
				name = spiDataListView.getName();
				siteId = spiDataListView.getSiteId();
				sortField = spiDataListView.getSortField();
				userId = spiDataListView.getUserId();
			}
		};
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
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

package com.liferay.data.engine.internal.manager;

import com.liferay.data.engine.DataRecordCollection;
import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.internal.manager.util.DataRecordCollectionUtil;
import com.liferay.data.engine.manager.DataRecordCollectionManager;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DataRecordCollectionManager.class)
public class DataRecordCollectionManagerImpl
	implements DataRecordCollectionManager {

	@Override
	public DataRecordCollection addDataRecordCollection(
			DataRecordCollection dataRecordCollection)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getDDMStructure(
			dataRecordCollection.getDataDefinitionId());

		ServiceContext serviceContext = new ServiceContext();

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.addRecordSet(
			PrincipalThreadLocal.getUserId(), ddmStructure.getGroupId(),
			ddmStructure.getStructureId(),
			Optional.ofNullable(
				dataRecordCollection.getDataRecordCollectionKey()
			).orElse(
				ddmStructure.getStructureKey()
			),
			LocalizedValueUtil.toLocaleStringMap(
				dataRecordCollection.getName()),
			LocalizedValueUtil.toLocaleStringMap(
				dataRecordCollection.getDescription()),
			0, DDLRecordSetConstants.SCOPE_DATA_ENGINE, serviceContext);

		_resourceLocalService.addModelResources(
			ddmStructure.getCompanyId(), ddmStructure.getGroupId(),
			PrincipalThreadLocal.getUserId(), _getResourceName(ddlRecordSet),
			ddlRecordSet.getPrimaryKey(), serviceContext.getModelPermissions());

		return DataRecordCollectionUtil.toDataRecordCollection(ddlRecordSet);
	}

	@Override
	public void deleteDataRecordCollection(long dataRecordCollectionId)
		throws Exception {

		_ddlRecordSetLocalService.deleteRecordSet(dataRecordCollectionId);
	}

	@Override
	public DataRecordCollection fetchDataRecordCollection(
		long dataRecordCollectionId) {

		try {
			return DataRecordCollectionUtil.toDataRecordCollection(
				_ddlRecordSetLocalService.getRecordSet(dataRecordCollectionId));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}

			return null;
		}
	}

	@Override
	public DataRecordCollection fetchDataRecordCollection(
		String dataRecordCollectionKey, long siteId) {

		try {
			return DataRecordCollectionUtil.toDataRecordCollection(
				_ddlRecordSetLocalService.getRecordSet(
					siteId, dataRecordCollectionKey));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}

			return null;
		}
	}

	@Override
	public DataRecordCollection getDataRecordCollection(
			long dataRecordCollectionId)
		throws Exception {

		return DataRecordCollectionUtil.toDataRecordCollection(
			_ddlRecordSetLocalService.getRecordSet(dataRecordCollectionId));
	}

	@Override
	public DataRecordCollection getDataRecordCollection(
			String dataRecordCollectionKey, long siteId)
		throws Exception {

		return DataRecordCollectionUtil.toDataRecordCollection(
			_ddlRecordSetLocalService.getRecordSet(
				siteId, dataRecordCollectionKey));
	}

	@Override
	public List<DataRecordCollection> getDataRecordCollections(
			long dataDefinitionId, int end, String keywords, int start)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		if (Validator.isNull(keywords)) {
			return DataRecordCollectionUtil.toDataRecordCollections(
				_ddlRecordSetLocalService.search(
					ddmStructure.getCompanyId(), ddmStructure.getGroupId(),
					keywords, DDLRecordSetConstants.SCOPE_DATA_ENGINE, start,
					end, null));
		}

		return DataRecordCollectionUtil.toDataRecordCollections(
			_search(
				ddmStructure.getCompanyId(), end, ddmStructure.getGroupId(),
				keywords, start, dataDefinitionId));
	}

	@Override
	public int getDataRecordCollectionsCount(
			long dataDefinitionId, String keywords)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		if (Validator.isNull(keywords)) {
			return _ddlRecordSetLocalService.searchCount(
				ddmStructure.getCompanyId(), ddmStructure.getGroupId(),
				keywords, DDLRecordSetConstants.SCOPE_DATA_ENGINE);
		}

		return _searchCount(
			ddmStructure.getCompanyId(), ddmStructure.getGroupId(), keywords,
			dataDefinitionId);
	}

	@Override
	public DataRecordCollection getDefaultDataRecordCollectionDataDefinition(
			long dataDefinitionId)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		return DataRecordCollectionUtil.toDataRecordCollection(
			_ddlRecordSetLocalService.getRecordSet(
				ddmStructure.getGroupId(), ddmStructure.getStructureKey()));
	}

	@Override
	public DataRecordCollection updateDataRecordCollection(
			DataRecordCollection dataRecordCollection)
		throws Exception {

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			dataRecordCollection.getId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUserId(PrincipalThreadLocal.getUserId());

		return DataRecordCollectionUtil.toDataRecordCollection(
			_ddlRecordSetLocalService.updateRecordSet(
				dataRecordCollection.getId(), ddlRecordSet.getDDMStructureId(),
				LocalizedValueUtil.toLocaleStringMap(
					dataRecordCollection.getName()),
				LocalizedValueUtil.toLocaleStringMap(
					dataRecordCollection.getDescription()),
				0, serviceContext));
	}

	private String _getResourceName(DDLRecordSet ddlRecordSet)
		throws PortalException {

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		return ResourceActionsUtil.getCompositeModelName(
			_portal.getClassName(ddmStructure.getClassNameId()),
			DDLRecordSet.class.getName());
	}

	private List<DDLRecordSet> _search(
		long companyId, int end, long groupId, String keywords, int start,
		long structureId) {

		try {
			List<DDLRecordSet> ddlRecordSets = new ArrayList<>();

			Indexer indexer = IndexerRegistryUtil.getIndexer(
				DDLRecordSet.class);

			SearchContext searchContext = new SearchContext();

			searchContext.setAttribute(Field.DESCRIPTION, keywords);
			searchContext.setAttribute(Field.NAME, keywords);
			searchContext.setAttribute("DDMStructureId", structureId);
			searchContext.setAttribute(
				"scope", DDLRecordSetConstants.SCOPE_DATA_ENGINE);
			searchContext.setCompanyId(companyId);
			searchContext.setGroupIds(new long[] {groupId});
			searchContext.setEnd(end);
			searchContext.setStart(start);

			Hits hits = indexer.search(searchContext);

			for (Document document : hits.getDocs()) {
				ddlRecordSets.add(
					_ddlRecordSetLocalService.getRecordSet(
						GetterUtil.getLong(
							document.get(Field.ENTRY_CLASS_PK))));
			}

			return ddlRecordSets;
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}
		}

		return Collections.emptyList();
	}

	private int _searchCount(
		long companyId, long groupId, String keywords, long structureId) {

		try {
			Indexer indexer = IndexerRegistryUtil.getIndexer(
				DDLRecordSet.class);

			SearchContext searchContext = new SearchContext();

			searchContext.setAttribute(Field.DESCRIPTION, keywords);
			searchContext.setAttribute(Field.NAME, keywords);
			searchContext.setAttribute("DDMStructureId", structureId);
			searchContext.setAttribute(
				"scope", DDLRecordSetConstants.SCOPE_DATA_ENGINE);
			searchContext.setCompanyId(companyId);
			searchContext.setGroupIds(new long[] {groupId});

			return (int)indexer.searchCount(searchContext);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}
		}

		return 0;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataRecordCollectionManagerImpl.class);

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private ResourceLocalService _resourceLocalService;

}
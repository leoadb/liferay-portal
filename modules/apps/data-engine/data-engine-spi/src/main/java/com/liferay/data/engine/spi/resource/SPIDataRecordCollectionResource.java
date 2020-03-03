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

package com.liferay.data.engine.spi.resource;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.spi.model.SPIDataRecordCollection;
import com.liferay.data.engine.spi.util.DataRecordCollectionUtil;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Locale;
import java.util.Optional;

import javax.validation.ValidationException;

/**
 * @author Jeyvison Nascimento
 */
public class SPIDataRecordCollectionResource {

	public SPIDataRecordCollectionResource(
		DDLRecordSetLocalService ddlRecordSetLocalService,
		DDMStructureLocalService ddmStructureLocalService, Portal portal,
		ResourceLocalService resourceLocalService) {

		_ddlRecordSetLocalService = ddlRecordSetLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_portal = portal;
		_resourceLocalService = resourceLocalService;
	}

	public SPIDataRecordCollection addDataRecordCollection(
			long dataDefinitionId,
			SPIDataRecordCollection spiDataRecordCollection)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		ServiceContext serviceContext = new ServiceContext();

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.addRecordSet(
			PrincipalThreadLocal.getUserId(), ddmStructure.getGroupId(),
			dataDefinitionId,
			Optional.ofNullable(
				spiDataRecordCollection.getDataRecordCollectionKey()
			).orElse(
				ddmStructure.getStructureKey()
			),
			LocalizedValueUtil.toLocaleStringMap(
				spiDataRecordCollection.getName()),
			LocalizedValueUtil.toLocaleStringMap(
				spiDataRecordCollection.getDescription()),
			0, DDLRecordSetConstants.SCOPE_DATA_ENGINE, serviceContext);

		_resourceLocalService.addModelResources(
			ddmStructure.getCompanyId(), ddmStructure.getGroupId(),
			PrincipalThreadLocal.getUserId(), _getResourceName(ddlRecordSet),
			ddlRecordSet.getPrimaryKey(), serviceContext.getModelPermissions());

		return DataRecordCollectionUtil.toSPIDataRecordCollection(ddlRecordSet);
	}

	public void deleteDataRecordCollection(Long dataRecordCollectionId)
		throws Exception {

		_ddlRecordSetLocalService.deleteRecordSet(dataRecordCollectionId);
	}

	public SPIDataRecordCollection getDataRecordCollection(
			long dataRecordCollectionId)
		throws Exception {

		return DataRecordCollectionUtil.toSPIDataRecordCollection(
			_ddlRecordSetLocalService.getRecordSet(dataRecordCollectionId));
	}

	public Page<SPIDataRecordCollection> getDataRecordCollections(
			long dataDefinitionId, String keywords, Locale locale,
			Pagination pagination)
		throws Exception {

		if (pagination.getPageSize() > 250) {
			throw new ValidationException(
				LanguageUtil.format(
					locale, "page-size-is-greater-than-x", 250));
		}

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		if (Validator.isNull(keywords)) {
			return Page.of(
				TransformUtil.transform(
					_ddlRecordSetLocalService.search(
						ddmStructure.getCompanyId(), ddmStructure.getGroupId(),
						keywords, DDLRecordSetConstants.SCOPE_DATA_ENGINE,
						pagination.getStartPosition(),
						pagination.getEndPosition(), null),
					DataRecordCollectionUtil::toSPIDataRecordCollection),
				pagination,
				_ddlRecordSetLocalService.searchCount(
					ddmStructure.getCompanyId(), ddmStructure.getGroupId(),
					keywords, DDLRecordSetConstants.SCOPE_DATA_ENGINE));
		}

		return SearchUtil.search(
			booleanQuery -> {
			},
			null, DDLRecordSet.class, keywords, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(Field.DESCRIPTION, keywords);
				searchContext.setAttribute(Field.NAME, keywords);
				searchContext.setAttribute(
					"DDMStructureId", ddmStructure.getStructureId());
				searchContext.setAttribute(
					"scope", DDLRecordSetConstants.SCOPE_DATA_ENGINE);
				searchContext.setCompanyId(ddmStructure.getCompanyId());
				searchContext.setGroupIds(
					new long[] {ddmStructure.getGroupId()});
			},
			document -> DataRecordCollectionUtil.toSPIDataRecordCollection(
				_ddlRecordSetLocalService.getRecordSet(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			null);
	}

	public SPIDataRecordCollection getDefaultDataRecordCollection(
			long dataDefinitionId)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		return DataRecordCollectionUtil.toSPIDataRecordCollection(
			_ddlRecordSetLocalService.getRecordSet(
				ddmStructure.getGroupId(), ddmStructure.getStructureKey()));
	}

	public SPIDataRecordCollection getSiteDataRecordCollection(
			String dataRecordCollectionKey, long siteId)
		throws Exception {

		return DataRecordCollectionUtil.toSPIDataRecordCollection(
			_ddlRecordSetLocalService.getRecordSet(
				siteId, dataRecordCollectionKey));
	}

	public SPIDataRecordCollection updateDataRecordCollection(
			long dataRecordCollectionId,
			SPIDataRecordCollection spiDataRecordCollection)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUserId(PrincipalThreadLocal.getUserId());

		return DataRecordCollectionUtil.toSPIDataRecordCollection(
			_ddlRecordSetLocalService.updateRecordSet(
				dataRecordCollectionId,
				spiDataRecordCollection.getDataDefinitionId(),
				LocalizedValueUtil.toLocaleStringMap(
					spiDataRecordCollection.getName()),
				LocalizedValueUtil.toLocaleStringMap(
					spiDataRecordCollection.getDescription()),
				0, serviceContext));
	}

	private String _getResourceName(DDLRecordSet ddlRecordSet)
		throws PortalException {

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		return ResourceActionsUtil.getCompositeModelName(
			_portal.getClassName(ddmStructure.getClassNameId()),
			DDLRecordSet.class.getName());
	}

	private final DDLRecordSetLocalService _ddlRecordSetLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final Portal _portal;
	private final ResourceLocalService _resourceLocalService;

}
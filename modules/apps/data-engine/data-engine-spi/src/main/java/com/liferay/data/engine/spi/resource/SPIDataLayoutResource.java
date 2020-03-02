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

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.service.DEDataDefinitionFieldLinkLocalService;
import com.liferay.data.engine.spi.model.SPIDataLayout;
import com.liferay.data.engine.spi.util.DataLayoutUtil;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.util.comparator.StructureLayoutCreateDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureLayoutModifiedDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureLayoutNameComparator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.ValidationException;

/**
 * @author Leonardo Barros
 */
public class SPIDataLayoutResource {

	public SPIDataLayoutResource(
		DDMFormLayoutSerializer ddmFormLayoutSerializer,
		DDMStructureLayoutLocalService ddmStructureLayoutLocalService,
		DDMStructureLocalService ddmStructureLocalService,
		DDMStructureVersionLocalService ddmStructureVersionLocalService,
		DEDataDefinitionFieldLinkLocalService
			deDataDefinitionFieldLinkLocalService) {

		_ddmFormLayoutSerializer = ddmFormLayoutSerializer;
		_ddmStructureLayoutLocalService = ddmStructureLayoutLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_ddmStructureVersionLocalService = ddmStructureVersionLocalService;
		_deDataDefinitionFieldLinkLocalService =
			deDataDefinitionFieldLinkLocalService;
	}

	public SPIDataLayout addDataLayout(SPIDataLayout spiDataLayout)
		throws Exception {

		String content = DataLayoutUtil.serialize(
			_ddmFormLayoutSerializer, spiDataLayout);

		_validate(content, spiDataLayout.getName());

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			spiDataLayout.getDataDefinitionId());

		ServiceContext serviceContext = new ServiceContext();

		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.addStructureLayout(
				PrincipalThreadLocal.getUserId(), ddmStructure.getGroupId(),
				ddmStructure.getClassNameId(), spiDataLayout.getDataLayoutKey(),
				_getDDMStructureVersionId(spiDataLayout.getDataDefinitionId()),
				LocalizedValueUtil.toLocaleStringMap(spiDataLayout.getName()),
				LocalizedValueUtil.toLocaleStringMap(
					spiDataLayout.getDescription()),
				content, serviceContext);

		_addDataDefinitionFieldLinks(
			ddmStructure.getClassNameId(), spiDataLayout.getDataDefinitionId(),
			ddmStructureLayout.getStructureLayoutId(), _getFieldNames(content),
			ddmStructureLayout.getGroupId());

		return DataLayoutUtil.toSPIDataLayout(ddmStructureLayout);
	}

	public void deleteDataLayout(long dataLayoutId) throws Exception {
		_deleteDataLayout(
			_ddmStructureLayoutLocalService.getStructureLayout(dataLayoutId));
	}

	public void deleteDataLayoutDataDefinition(long dataDefinitionId)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getDDMStructure(
			dataDefinitionId);

		List<DDMStructureVersion> ddmStructureVersions =
			_ddmStructureVersionLocalService.getStructureVersions(
				dataDefinitionId);

		for (DDMStructureVersion ddmStructureVersion : ddmStructureVersions) {
			List<DDMStructureLayout> ddmStructureLayouts =
				_ddmStructureLayoutLocalService.getStructureLayouts(
					ddmStructure.getGroupId(), ddmStructure.getClassNameId(),
					ddmStructureVersion.getStructureVersionId());

			for (DDMStructureLayout ddmStructureLayout : ddmStructureLayouts) {
				_deleteDataLayout(ddmStructureLayout);
			}
		}
	}

	public SPIDataLayout getDataLayout(long dataLayoutId) throws Exception {
		return DataLayoutUtil.toSPIDataLayout(
			_ddmStructureLayoutLocalService.getDDMStructureLayout(
				dataLayoutId));
	}

	public SPIDataLayout getDataLayout(
			long classNameId, String dataLayoutKey, long siteId)
		throws Exception {

		return DataLayoutUtil.toSPIDataLayout(
			_ddmStructureLayoutLocalService.getStructureLayout(
				siteId, classNameId, dataLayoutKey));
	}

	public Page<SPIDataLayout> getDataLayouts(
			long dataDefinitionId, String keywords, Locale locale,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		if (pagination.getPageSize() > 250) {
			throw new ValidationException(
				LanguageUtil.format(
					locale, "page-size-is-greater-than-x", 250));
		}

		if (ArrayUtil.isEmpty(sorts)) {
			sorts = new Sort[] {
				new Sort(
					Field.getSortableFieldName(Field.MODIFIED_DATE),
					Sort.STRING_TYPE, true)
			};
		}

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		if (Validator.isNull(keywords)) {
			return Page.of(
				TransformUtil.transform(
					_ddmStructureLayoutLocalService.getStructureLayouts(
						ddmStructure.getGroupId(),
						ddmStructure.getClassNameId(),
						_getDDMStructureVersionId(dataDefinitionId),
						pagination.getStartPosition(),
						pagination.getEndPosition(),
						_toOrderByComparator(
							(Sort)ArrayUtil.getValue(sorts, 0))),
					DataLayoutUtil::toSPIDataLayout),
				pagination,
				_ddmStructureLayoutLocalService.getStructureLayoutsCount(
					ddmStructure.getGroupId(), ddmStructure.getClassNameId(),
					_getDDMStructureVersionId(dataDefinitionId)));
		}

		return SearchUtil.search(
			booleanQuery -> {
			},
			null, DDMStructureLayout.class, keywords, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(
					Field.CLASS_NAME_ID, ddmStructure.getClassNameId());
				searchContext.setAttribute(Field.DESCRIPTION, keywords);
				searchContext.setAttribute(Field.NAME, keywords);
				searchContext.setAttribute(
					"structureVersionId",
					_getDDMStructureVersionId(dataDefinitionId));
				searchContext.setCompanyId(ddmStructure.getCompanyId());
				searchContext.setGroupIds(
					new long[] {ddmStructure.getGroupId()});
			},
			document -> DataLayoutUtil.toSPIDataLayout(
				_ddmStructureLayoutLocalService.getStructureLayout(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
	}

	public SPIDataLayout updateDataLayout(SPIDataLayout spiDataLayout)
		throws Exception {

		String content = DataLayoutUtil.serialize(
			_ddmFormLayoutSerializer, spiDataLayout);

		_validate(content, spiDataLayout.getName());

		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.getStructureLayout(
				spiDataLayout.getId());

		DDMStructure ddmStructure = ddmStructureLayout.getDDMStructure();

		ddmStructureLayout =
			_ddmStructureLayoutLocalService.updateStructureLayout(
				spiDataLayout.getId(),
				_getDDMStructureVersionId(ddmStructure.getStructureId()),
				LocalizedValueUtil.toLocaleStringMap(spiDataLayout.getName()),
				LocalizedValueUtil.toLocaleStringMap(
					spiDataLayout.getDescription()),
				content, new ServiceContext());

		_deDataDefinitionFieldLinkLocalService.deleteDEDataDefinitionFieldLinks(
			ddmStructure.getClassNameId(), spiDataLayout.getId());

		_addDataDefinitionFieldLinks(
			ddmStructure.getClassNameId(), ddmStructure.getStructureId(),
			spiDataLayout.getId(), _getFieldNames(content),
			ddmStructure.getGroupId());

		return DataLayoutUtil.toSPIDataLayout(ddmStructureLayout);
	}

	private void _addDataDefinitionFieldLinks(
			long classNameId, long dataDefinitionId, long dataLayoutId,
			List<String> fieldNames, long siteId)
		throws PortalException {

		for (String fieldName : fieldNames) {
			_deDataDefinitionFieldLinkLocalService.addDEDataDefinitionFieldLink(
				siteId, classNameId, dataLayoutId, dataDefinitionId, fieldName);
		}
	}

	private void _deleteDataLayout(DDMStructureLayout ddmStructureLayout)
		throws Exception {

		DDMStructure ddmStructure = ddmStructureLayout.getDDMStructure();

		_ddmStructureLayoutLocalService.deleteDDMStructureLayout(
			ddmStructureLayout);

		_deDataDefinitionFieldLinkLocalService.deleteDEDataDefinitionFieldLinks(
			ddmStructure.getClassNameId(),
			ddmStructureLayout.getStructureLayoutId());
	}

	private long _getDDMStructureVersionId(long deDataDefinitionId)
		throws PortalException {

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getLatestStructureVersion(
				deDataDefinitionId);

		return ddmStructureVersion.getStructureVersionId();
	}

	private List<String> _getFieldNames(String content) {
		DocumentContext documentContext = JsonPath.parse(content);

		return documentContext.read(
			"$[\"pages\"][*][\"rows\"][*][\"columns\"][*][\"fieldNames\"][*]");
	}

	private OrderByComparator<DDMStructureLayout> _toOrderByComparator(
		Sort sort) {

		boolean ascending = !sort.isReverse();

		String sortFieldName = sort.getFieldName();

		if (StringUtil.startsWith(sortFieldName, "createDate")) {
			return new StructureLayoutCreateDateComparator(ascending);
		}
		else if (StringUtil.startsWith(sortFieldName, "localized_name")) {
			return new StructureLayoutNameComparator(ascending);
		}

		return new StructureLayoutModifiedDateComparator(ascending);
	}

	private void _validate(String content, Map<String, Object> name) {
		if (MapUtil.isEmpty(name)) {
			throw new ValidationException("Name is required");
		}

		name.forEach(
			(locale, localizedName) -> {
				if (Validator.isNull(localizedName)) {
					throw new ValidationException("Name is required");
				}
			});

		List<String> fieldNames = _getFieldNames(content);

		if (ListUtil.isEmpty(fieldNames)) {
			throw new ValidationException("Layout is empty");
		}
	}

	private final DDMFormLayoutSerializer _ddmFormLayoutSerializer;
	private final DDMStructureLayoutLocalService
		_ddmStructureLayoutLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DDMStructureVersionLocalService
		_ddmStructureVersionLocalService;
	private final DEDataDefinitionFieldLinkLocalService
		_deDataDefinitionFieldLinkLocalService;

}
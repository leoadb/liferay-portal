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
import com.liferay.data.engine.model.DEDataListView;
import com.liferay.data.engine.service.DEDataDefinitionFieldLinkLocalService;
import com.liferay.data.engine.service.DEDataListViewLocalService;
import com.liferay.data.engine.spi.model.SPIDataListView;
import com.liferay.data.engine.spi.util.DataListViewUtil;
import com.liferay.data.engine.util.comparator.DEDataListViewCreateDateComparator;
import com.liferay.data.engine.util.comparator.DEDataListViewModifiedDateComparator;
import com.liferay.data.engine.util.comparator.DEDataListViewNameComparator;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import javax.validation.ValidationException;

/**
 * @author Eudaldo Alonso
 */
public class SPIDataListViewResource {

	public SPIDataListViewResource(
		DDMStructureLocalService ddmStructureLocalService,
		DEDataDefinitionFieldLinkLocalService
			deDataDefinitionFieldLinkLocalService,
		DEDataListViewLocalService deDataListViewLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
		_deDataDefinitionFieldLinkLocalService =
			deDataDefinitionFieldLinkLocalService;
		_deDataListViewLocalService = deDataListViewLocalService;
	}

	public SPIDataListView addDataDefinitionDataListView(
			SPIDataListView spiDataListView)
		throws Exception {

		_validate(spiDataListView.getFieldNames());

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			spiDataListView.getDataDefinitionId());

		DEDataListView deDataListView =
			_deDataListViewLocalService.addDEDataListView(
				ddmStructure.getGroupId(), ddmStructure.getCompanyId(),
				PrincipalThreadLocal.getUserId(),
				_toJSON(spiDataListView.getAppliedFilters()),
				spiDataListView.getDataDefinitionId(),
				Arrays.toString(spiDataListView.getFieldNames()),
				LocalizedValueUtil.toLocaleStringMap(spiDataListView.getName()),
				spiDataListView.getSortField());

		_addDataDefinitionFieldLinks(
			spiDataListView.getDataDefinitionId(),
			deDataListView.getDeDataListViewId(),
			spiDataListView.getFieldNames(), ddmStructure.getGroupId());

		return DataListViewUtil.toSPIDataListView(deDataListView);
	}

	public void deleteDataListView(long dataListViewId) throws Exception {
		_deDataDefinitionFieldLinkLocalService.deleteDEDataDefinitionFieldLinks(
			_getClassNameId(), dataListViewId);

		_deDataListViewLocalService.deleteDEDataListView(dataListViewId);
	}

	public Page<SPIDataListView> getDataDefinitionDataListViewsPage(
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
					_deDataListViewLocalService.getDEDataListViews(
						ddmStructure.getGroupId(), ddmStructure.getCompanyId(),
						ddmStructure.getStructureId(),
						pagination.getStartPosition(),
						pagination.getEndPosition(),
						_toOrderByComparator(
							(Sort)ArrayUtil.getValue(sorts, 0))),
					DataListViewUtil::toSPIDataListView),
				pagination,
				_deDataListViewLocalService.getDEDataListViewsCount(
					ddmStructure.getGroupId(), ddmStructure.getCompanyId(),
					ddmStructure.getStructureId()));
		}

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> {
			},
			null, DEDataListView.class, keywords, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(Field.DESCRIPTION, keywords);
				searchContext.setAttribute(Field.NAME, keywords);
				searchContext.setAttribute("ddmStructureId", dataDefinitionId);
				searchContext.setCompanyId(ddmStructure.getCompanyId());
				searchContext.setGroupIds(
					new long[] {ddmStructure.getGroupId()});
			},
			sorts,
			document -> DataListViewUtil.toSPIDataListView(
				_deDataListViewLocalService.getDEDataListView(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	public SPIDataListView getDataListView(long dataListViewId)
		throws Exception {

		return DataListViewUtil.toSPIDataListView(
			_deDataListViewLocalService.getDEDataListView(dataListViewId));
	}

	public SPIDataListView updateDataListView(SPIDataListView spiDataListView)
		throws Exception {

		_validate(spiDataListView.getFieldNames());

		DEDataListView deDataListView =
			_deDataListViewLocalService.updateDEDataListView(
				spiDataListView.getId(),
				_toJSON(spiDataListView.getAppliedFilters()),
				Arrays.toString(spiDataListView.getFieldNames()),
				LocalizedValueUtil.toLocaleStringMap(spiDataListView.getName()),
				spiDataListView.getSortField());

		_deDataDefinitionFieldLinkLocalService.deleteDEDataDefinitionFieldLinks(
			_getClassNameId(), spiDataListView.getId());

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			spiDataListView.getDataDefinitionId());

		_addDataDefinitionFieldLinks(
			spiDataListView.getDataDefinitionId(), spiDataListView.getId(),
			spiDataListView.getFieldNames(), ddmStructure.getGroupId());

		return DataListViewUtil.toSPIDataListView(deDataListView);
	}

	private void _addDataDefinitionFieldLinks(
			long dataDefinitionId, long dataListViewId, String[] fieldNames,
			long groupId)
		throws Exception {

		for (String fieldName : fieldNames) {
			_deDataDefinitionFieldLinkLocalService.addDEDataDefinitionFieldLink(
				groupId, _getClassNameId(), dataListViewId, dataDefinitionId,
				fieldName);
		}
	}

	private long _getClassNameId() {
		return PortalUtil.getClassNameId(DEDataListView.class);
	}

	private String _toJSON(Map<String, Object> appliedFilters) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (MapUtil.isEmpty(appliedFilters)) {
			return jsonObject.toString();
		}

		for (Map.Entry<String, Object> entry : appliedFilters.entrySet()) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}

		return jsonObject.toString();
	}

	private OrderByComparator<DEDataListView> _toOrderByComparator(Sort sort) {
		boolean ascending = !sort.isReverse();

		String sortFieldName = sort.getFieldName();

		if (StringUtil.startsWith(sortFieldName, "createDate")) {
			return new DEDataListViewCreateDateComparator(ascending);
		}
		else if (StringUtil.startsWith(sortFieldName, "localized_name")) {
			return new DEDataListViewNameComparator(ascending);
		}

		return new DEDataListViewModifiedDateComparator(ascending);
	}

	private void _validate(String[] fieldNames) {
		if (ArrayUtil.isEmpty(fieldNames)) {
			throw new ValidationException("View is empty");
		}
	}

	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DEDataDefinitionFieldLinkLocalService
		_deDataDefinitionFieldLinkLocalService;
	private final DEDataListViewLocalService _deDataListViewLocalService;

}
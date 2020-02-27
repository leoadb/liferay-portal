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
import com.liferay.data.engine.util.comparator.DEDataListViewCreateDateComparator;
import com.liferay.data.engine.util.comparator.DEDataListViewModifiedDateComparator;
import com.liferay.data.engine.util.comparator.DEDataListViewNameComparator;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.petra.function.UnsafeFunction;
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
public class SPIDataListViewResource<T> {

	public SPIDataListViewResource(
		DDMStructureLocalService ddmStructureLocalService,
		DEDataDefinitionFieldLinkLocalService
			deDataDefinitionFieldLinkLocalService,
		DEDataListViewLocalService deDataListViewLocalService,
		UnsafeFunction<DEDataListView, T, Exception> toDataListViewFunction) {

		_ddmStructureLocalService = ddmStructureLocalService;
		_deDataDefinitionFieldLinkLocalService =
			deDataDefinitionFieldLinkLocalService;
		_deDataListViewLocalService = deDataListViewLocalService;
		_toDataListViewFunction = toDataListViewFunction;
	}

	public T addDataDefinitionDataListView(
			Map<String, Object> appliedFilters, long dataDefinitionId,
			long dataListViewId, String[] fieldNames, Map<String, Object> name,
			String sortField)
		throws Exception {

		_validate(fieldNames);

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		DEDataListView deDataListView =
			_deDataListViewLocalService.addDEDataListView(
				ddmStructure.getGroupId(), ddmStructure.getCompanyId(),
				PrincipalThreadLocal.getUserId(), _toJSON(appliedFilters),
				dataDefinitionId, Arrays.toString(fieldNames),
				LocalizedValueUtil.toLocaleStringMap(name), sortField);

		_addDataDefinitionFieldLinks(
			dataDefinitionId, dataListViewId, fieldNames,
			ddmStructure.getGroupId());

		return _toDataListViewFunction.apply(deDataListView);
	}

	public void deleteDataListView(long dataListViewId) throws Exception {
		_deDataDefinitionFieldLinkLocalService.deleteDEDataDefinitionFieldLinks(
			_getClassNameId(), dataListViewId);

		_deDataListViewLocalService.deleteDEDataListView(dataListViewId);
	}

	public Page<T> getDataDefinitionDataListViews(
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
					_toDataListViewFunction),
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
			document -> _toDataListViewFunction.apply(
				_deDataListViewLocalService.getDEDataListView(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	public T getDataListView(long dataListViewId) throws Exception {
		return _toDataListViewFunction.apply(
			_deDataListViewLocalService.getDEDataListView(dataListViewId));
	}

	public T updateDataListView(
			Map<String, Object> appliedFilters, long dataDefinitionId,
			long dataListViewId, String[] fieldNames, Map<String, Object> name,
			String sortField)
		throws Exception {

		_validate(fieldNames);

		DEDataListView deDataListView =
			_deDataListViewLocalService.updateDEDataListView(
				dataListViewId, _toJSON(appliedFilters),
				Arrays.toString(fieldNames),
				LocalizedValueUtil.toLocaleStringMap(name), sortField);

		_deDataDefinitionFieldLinkLocalService.deleteDEDataDefinitionFieldLinks(
			_getClassNameId(), dataListViewId);

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		_addDataDefinitionFieldLinks(
			dataDefinitionId, dataListViewId, fieldNames,
			ddmStructure.getGroupId());

		return _toDataListViewFunction.apply(deDataListView);
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
	private final UnsafeFunction<DEDataListView, T, Exception>
		_toDataListViewFunction;

}
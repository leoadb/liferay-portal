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

import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.data.engine.content.type.DataDefinitionContentTypeTracker;
import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.service.DEDataDefinitionFieldLinkLocalService;
import com.liferay.data.engine.service.DEDataListViewLocalService;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.util.comparator.StructureCreateDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureModifiedDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureNameComparator;
import com.liferay.petra.function.UnsafeBiFunction;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.ValidationException;

/**
 * @author Jeyvison Nascimento
 */
public class SPIDataDefinitionResource<T, U, V> {

	public SPIDataDefinitionResource(
		DataDefinitionContentTypeTracker dataDefinitionContentTypeTracker,
		DDLRecordSetLocalService ddlRecordSetLocalService,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		DDMStructureLayoutLocalService ddmStructureLayoutLocalService,
		DDMStructureLocalService ddmStructureLocalService,
		DDMStructureVersionLocalService ddmStructureVersionLocalService,
		DEDataDefinitionFieldLinkLocalService
			deDataDefinitionFieldLinkLocalService,
		DEDataListViewLocalService deDataListViewLocalService, Portal portal,
		ResourceLocalService resourceLocalService,
		UnsafeBiFunction
			<DDMFormFieldTypeServicesTracker, DDMStructure, T, Exception>
				toDataDefinitionFunction,
		UnsafeFunction<DDMStructureLayout, U, Exception> toDataLayoutFunction,
		UnsafeFunction<DDLRecordSet, V, Exception>
			toDataRecordCollectionFunction) {

		_dataDefinitionContentTypeTracker = dataDefinitionContentTypeTracker;
		_ddlRecordSetLocalService = ddlRecordSetLocalService;
		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
		_ddmStructureLayoutLocalService = ddmStructureLayoutLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_ddmStructureVersionLocalService = ddmStructureVersionLocalService;
		_deDataDefinitionFieldLinkLocalService =
			deDataDefinitionFieldLinkLocalService;
		_deDataListViewLocalService = deDataListViewLocalService;
		_portal = portal;
		_resourceLocalService = resourceLocalService;
		_toDataDefinitionFunction = toDataDefinitionFunction;
		_toDataLayoutFunction = toDataLayoutFunction;
		_toDataRecordCollectionFunction = toDataRecordCollectionFunction;
	}

	public T addDataDefinitionByContentType(
			long companyId, String content, String contentType,
			String dataDefinitionKey, Map<String, Object> description,
			Map<String, Object> name, long siteId, String storageType)
		throws Exception {

		DataDefinitionContentType dataDefinitionContentType =
			_dataDefinitionContentTypeTracker.getDataDefinitionContentType(
				contentType);

		DDMStructure ddmStructure = _ddmStructureLocalService.addStructure(
			PrincipalThreadLocal.getUserId(), siteId,
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			dataDefinitionContentType.getClassNameId(), dataDefinitionKey,
			LocalizedValueUtil.toLocaleStringMap(name),
			LocalizedValueUtil.toLocaleStringMap(description), content,
			GetterUtil.getString(storageType, "json"), new ServiceContext());

		_resourceLocalService.addResources(
			companyId, siteId, PrincipalThreadLocal.getUserId(),
			ResourceActionsUtil.getCompositeModelName(
				_portal.getClassName(
					dataDefinitionContentType.getClassNameId()),
				DDMStructure.class.getName()),
			ddmStructure.getStructureId(), false, false, false);

		SPIDataRecordCollectionResource<V> spiDataRecordCollectionResource =
			new SPIDataRecordCollectionResource<>(
				_ddlRecordSetLocalService, _ddmStructureLocalService, _portal,
				_resourceLocalService, _toDataRecordCollectionFunction);

		spiDataRecordCollectionResource.addDataRecordCollection(
			ddmStructure.getStructureId(), ddmStructure.getStructureKey(),
			LocalizedValueUtil.toStringObjectMap(
				ddmStructure.getDescriptionMap()),
			LocalizedValueUtil.toStringObjectMap(ddmStructure.getNameMap()));

		return _toDataDefinitionFunction.apply(
			_ddmFormFieldTypeServicesTracker, ddmStructure);
	}

	public void deleteDataDefinition(long dataDefinitionId) throws Exception {
		_ddlRecordSetLocalService.deleteDDMStructureRecordSets(
			dataDefinitionId);

		SPIDataLayoutResource<U> spiDataLayoutResource =
			_getSPIDataLayoutResource();

		spiDataLayoutResource.deleteDataLayoutDataDefinition(dataDefinitionId);

		_ddmStructureLocalService.deleteDDMStructure(dataDefinitionId);

		List<DDMStructureVersion> ddmStructureVersions =
			_ddmStructureVersionLocalService.getStructureVersions(
				dataDefinitionId);

		for (DDMStructureVersion ddmStructureVersion : ddmStructureVersions) {
			_ddmStructureVersionLocalService.deleteDDMStructureVersion(
				ddmStructureVersion);
		}

		_deDataDefinitionFieldLinkLocalService.deleteDEDataDefinitionFieldLinks(
			dataDefinitionId);

		_deDataListViewLocalService.deleteDEDataListViews(dataDefinitionId);
	}

	public T getDataDefinition(long dataDefinitionId) throws Exception {
		return _toDataDefinitionFunction.apply(
			_ddmFormFieldTypeServicesTracker,
			_ddmStructureLocalService.getStructure(dataDefinitionId));
	}

	public Page<T> getDataDefinitionsByContentType(
			long companyId, String contentType, String keywords, long groupId,
			Locale locale, Pagination pagination, Sort[] sorts)
		throws Exception {

		return getSiteDataDefinitionsByContentType(
			companyId, contentType, keywords, locale, pagination,
			_portal.getSiteGroupId(groupId), sorts);
	}

	public T getSiteDataDefinitionByContentTypeByDataDefinitionKey(
			String contentType, String dataDefinitionKey, long siteId)
		throws Exception {

		DataDefinitionContentType dataDefinitionContentType =
			_dataDefinitionContentTypeTracker.getDataDefinitionContentType(
				contentType);

		return _toDataDefinitionFunction.apply(
			_ddmFormFieldTypeServicesTracker,
			_ddmStructureLocalService.getStructure(
				siteId, dataDefinitionContentType.getClassNameId(),
				dataDefinitionKey));
	}

	public Page<T> getSiteDataDefinitionsByContentType(
			long companyId, String contentType, String keywords, Locale locale,
			Pagination pagination, long siteId, Sort[] sorts)
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

		DataDefinitionContentType dataDefinitionContentType =
			_dataDefinitionContentTypeTracker.getDataDefinitionContentType(
				contentType);

		if (Validator.isNull(keywords)) {
			return Page.of(
				TransformUtil.transform(
					_ddmStructureLocalService.getStructures(
						siteId, dataDefinitionContentType.getClassNameId(),
						pagination.getStartPosition(),
						pagination.getEndPosition(),
						_toOrderByComparator(
							(Sort)ArrayUtil.getValue(sorts, 0))),
					ddmStructure -> _toDataDefinitionFunction.apply(
						_ddmFormFieldTypeServicesTracker, ddmStructure)),
				pagination,
				_ddmStructureLocalService.getStructuresCount(
					siteId, dataDefinitionContentType.getClassNameId()));
		}

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> {
			},
			null, DDMStructure.class, keywords, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.CLASS_NAME_ID, Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(
					Field.CLASS_NAME_ID,
					dataDefinitionContentType.getClassNameId());
				searchContext.setAttribute(Field.DESCRIPTION, keywords);
				searchContext.setAttribute(Field.NAME, keywords);
				searchContext.setCompanyId(companyId);
				searchContext.setGroupIds(new long[] {siteId});
			},
			sorts,
			document -> _toDataDefinitionFunction.apply(
				_ddmFormFieldTypeServicesTracker,
				_ddmStructureLocalService.getStructure(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	public T updateDataDefinition(
			String content, long dataDefinitionId,
			Map<String, Object> description, Map<String, Object> name)
		throws Exception {

		return _toDataDefinitionFunction.apply(
			_ddmFormFieldTypeServicesTracker,
			_ddmStructureLocalService.updateStructure(
				PrincipalThreadLocal.getUserId(), dataDefinitionId,
				DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
				LocalizedValueUtil.toLocaleStringMap(name),
				LocalizedValueUtil.toLocaleStringMap(description), content,
				new ServiceContext()));
	}

	private SPIDataLayoutResource _getSPIDataLayoutResource() {
		return new SPIDataLayoutResource<>(
			_ddmStructureLayoutLocalService, _ddmStructureLocalService,
			_ddmStructureVersionLocalService,
			_deDataDefinitionFieldLinkLocalService, _toDataLayoutFunction);
	}

	private OrderByComparator<DDMStructure> _toOrderByComparator(Sort sort) {
		boolean ascending = !sort.isReverse();

		String sortFieldName = sort.getFieldName();

		if (StringUtil.startsWith(sortFieldName, "createDate")) {
			return new StructureCreateDateComparator(ascending);
		}
		else if (StringUtil.startsWith(sortFieldName, "localized_name")) {
			return new StructureNameComparator(ascending);
		}

		return new StructureModifiedDateComparator(ascending);
	}

	private final DataDefinitionContentTypeTracker
		_dataDefinitionContentTypeTracker;
	private final DDLRecordSetLocalService _ddlRecordSetLocalService;
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;
	private final DDMStructureLayoutLocalService
		_ddmStructureLayoutLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DDMStructureVersionLocalService
		_ddmStructureVersionLocalService;
	private final DEDataDefinitionFieldLinkLocalService
		_deDataDefinitionFieldLinkLocalService;
	private final DEDataListViewLocalService _deDataListViewLocalService;
	private final Portal _portal;
	private final ResourceLocalService _resourceLocalService;
	private final UnsafeBiFunction
		<DDMFormFieldTypeServicesTracker, DDMStructure, T, Exception>
			_toDataDefinitionFunction;
	private final UnsafeFunction<DDMStructureLayout, U, Exception>
		_toDataLayoutFunction;
	private final UnsafeFunction<DDLRecordSet, V, Exception>
		_toDataRecordCollectionFunction;

}
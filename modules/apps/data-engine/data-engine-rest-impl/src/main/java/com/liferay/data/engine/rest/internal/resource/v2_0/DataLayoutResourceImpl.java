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

import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.internal.dto.v2_0.util.DataLayoutUtil;
import com.liferay.data.engine.rest.internal.model.InternalDataDefinition;
import com.liferay.data.engine.rest.internal.model.InternalDataLayout;
import com.liferay.data.engine.rest.internal.odata.entity.v2_0.DataLayoutEntityModel;
import com.liferay.data.engine.rest.internal.resource.common.CommonDataLayoutResource;
import com.liferay.data.engine.rest.resource.v2_0.DataLayoutResource;
import com.liferay.data.engine.service.DEDataDefinitionFieldLinkLocalService;
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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import javax.validation.ValidationException;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v2_0/data-layout.properties",
	scope = ServiceScope.PROTOTYPE, service = DataLayoutResource.class
)
public class DataLayoutResourceImpl
	extends BaseDataLayoutResourceImpl implements EntityModelResource {

	@Override
	public void deleteDataLayout(Long dataLayoutId) throws Exception {
		CommonDataLayoutResource<DataLayout> commonDataLayoutResource =
			_getCommonDataLayoutResource();

		commonDataLayoutResource.deleteDataLayout(dataLayoutId);
	}

	@Override
	public Page<DataLayout> getDataDefinitionDataLayoutsPage(
			Long dataDefinitionId, String keywords, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		if (pagination.getPageSize() > 250) {
			throw new ValidationException(
				LanguageUtil.format(
					contextAcceptLanguage.getPreferredLocale(),
					"page-size-is-greater-than-x", 250));
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
				transform(
					_ddmStructureLayoutLocalService.getStructureLayouts(
						ddmStructure.getGroupId(),
						ddmStructure.getClassNameId(),
						_getDDMStructureVersionId(dataDefinitionId),
						pagination.getStartPosition(),
						pagination.getEndPosition(),
						_toOrderByComparator(
							(Sort)ArrayUtil.getValue(sorts, 0))),
					DataLayoutUtil::toDataLayout),
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
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(
					new long[] {ddmStructure.getGroupId()});
			},
			document -> DataLayoutUtil.toDataLayout(
				_ddmStructureLayoutLocalService.getStructureLayout(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
	}

	@Override
	public DataLayout getDataLayout(Long dataLayoutId) throws Exception {
		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.getStructureLayout(dataLayoutId);

		_modelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			ddmStructureLayout.getDDMStructureId(), ActionKeys.VIEW);

		return DataLayoutUtil.toDataLayout(
			_ddmStructureLayoutLocalService.getDDMStructureLayout(
				dataLayoutId));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public DataLayout getSiteDataLayoutByDataLayoutKey(
			Long siteId, String dataLayoutKey)
		throws Exception {

		return DataLayoutUtil.toDataLayout(
			_ddmStructureLayoutLocalService.getStructureLayout(
				siteId, _getClassNameId(), dataLayoutKey));
	}

	@Override
	public Page<DataLayout> getSiteDataLayoutsPage(
			Long siteId, String keywords, Pagination pagination, Sort[] sorts)
		throws Exception {

		if (pagination.getPageSize() > 250) {
			throw new ValidationException(
				LanguageUtil.format(
					contextAcceptLanguage.getPreferredLocale(),
					"page-size-is-greater-than-x", 250));
		}

		if (ArrayUtil.isEmpty(sorts)) {
			sorts = new Sort[] {
				new Sort(
					Field.getSortableFieldName(Field.MODIFIED_DATE),
					Sort.STRING_TYPE, true)
			};
		}

		if (Validator.isNull(keywords)) {
			return Page.of(
				transform(
					_ddmStructureLayoutLocalService.getStructureLayouts(
						siteId, _getClassNameId(),
						pagination.getStartPosition(),
						pagination.getEndPosition(),
						_toOrderByComparator(
							(Sort)ArrayUtil.getValue(sorts, 0))),
					DataLayoutUtil::toDataLayout),
				pagination,
				_ddmStructureLayoutLocalService.getStructureLayoutsCount(
					siteId, _getClassNameId()));
		}

		return SearchUtil.search(
			booleanQuery -> {
			},
			null, DDMStructureLayout.class, keywords, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(
					Field.CLASS_NAME_ID, _getClassNameId());
				searchContext.setAttribute(Field.DESCRIPTION, keywords);
				searchContext.setAttribute(Field.NAME, keywords);
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(new long[] {siteId});
			},
			document -> DataLayoutUtil.toDataLayout(
				_ddmStructureLayoutLocalService.getStructureLayout(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
	}

	@Override
	public DataLayout postDataDefinitionDataLayout(
			Long dataDefinitionId, DataLayout dataLayout)
		throws Exception {

		CommonDataLayoutResource<DataLayout> commonDataLayoutResource =
			_getCommonDataLayoutResource();

		return commonDataLayoutResource.postDataDefinitionDataLayout(
			dataDefinitionId, dataLayout);
	}

	@Override
	public DataLayout putDataLayout(Long dataLayoutId, DataLayout dataLayout)
		throws Exception {

		CommonDataLayoutResource<DataLayout> commonDataLayoutResource =
			_getCommonDataLayoutResource();

		return commonDataLayoutResource.putDataLayout(dataLayoutId, dataLayout);
	}

	@Reference(
		target = "(model.class.name=com.liferay.data.engine.rest.internal.model.InternalDataDefinition)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<InternalDataDefinition>
			modelResourcePermission) {

		_modelResourcePermission = modelResourcePermission;
	}

	private long _getClassNameId() {
		return _portal.getClassNameId(InternalDataLayout.class);
	}

	private CommonDataLayoutResource<DataLayout>
		_getCommonDataLayoutResource() {

		return new CommonDataLayoutResource<>(
			_deDataDefinitionFieldLinkLocalService, _ddmFormLayoutSerializer,
			_ddmStructureLayoutLocalService, _ddmStructureLocalService,
			_ddmStructureVersionLocalService, _groupLocalService,
			_modelResourcePermission, DataLayoutUtil::toDataLayout);
	}

	private long _getDDMStructureVersionId(Long deDataDefinitionId)
		throws Exception {

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getLatestStructureVersion(
				deDataDefinitionId);

		return ddmStructureVersion.getStructureVersionId();
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

	private static final EntityModel _entityModel = new DataLayoutEntityModel();

	@Reference(target = "(ddm.form.layout.serializer.type=json)")
	private DDMFormLayoutSerializer _ddmFormLayoutSerializer;

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	@Reference
	private DEDataDefinitionFieldLinkLocalService
		_deDataDefinitionFieldLinkLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	private ModelResourcePermission<InternalDataDefinition>
		_modelResourcePermission;

	@Reference
	private Portal _portal;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}
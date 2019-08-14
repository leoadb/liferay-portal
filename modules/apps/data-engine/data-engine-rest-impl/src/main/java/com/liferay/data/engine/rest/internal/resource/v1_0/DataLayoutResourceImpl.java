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

package com.liferay.data.engine.rest.internal.resource.v1_0;

import com.liferay.data.engine.constants.DataActionKeys;
import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.model.DEDataLayout;
import com.liferay.data.engine.model.DEDataLayoutColumn;
import com.liferay.data.engine.model.DEDataLayoutPage;
import com.liferay.data.engine.model.DEDataLayoutRow;
import com.liferay.data.engine.model.builder.DEDataLayoutBuilder;
import com.liferay.data.engine.model.builder.DEDataLayoutColumnBuilder;
import com.liferay.data.engine.model.builder.DEDataLayoutPageBuilder;
import com.liferay.data.engine.model.builder.DEDataLayoutRowBuilder;
import com.liferay.data.engine.model.builder.DEModelBuilderFactory;
import com.liferay.data.engine.rest.dto.v1_0.DataLayout;
import com.liferay.data.engine.rest.dto.v1_0.DataLayoutColumn;
import com.liferay.data.engine.rest.dto.v1_0.DataLayoutPage;
import com.liferay.data.engine.rest.dto.v1_0.DataLayoutPermission;
import com.liferay.data.engine.rest.dto.v1_0.DataLayoutRow;
import com.liferay.data.engine.rest.internal.constants.DataLayoutConstants;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataLayoutUtil;
import com.liferay.data.engine.rest.internal.model.InternalDataLayout;
import com.liferay.data.engine.rest.internal.model.InternalDataRecordCollection;
import com.liferay.data.engine.rest.internal.odata.entity.v1_0.DataLayoutEntityModel;
import com.liferay.data.engine.rest.internal.resource.v1_0.util.DataEnginePermissionUtil;
import com.liferay.data.engine.rest.resource.v1_0.DataLayoutResource;
import com.liferay.data.engine.service.DEDataLayoutService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ValidationException;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/data-layout.properties",
	scope = ServiceScope.PROTOTYPE, service = DataLayoutResource.class
)
public class DataLayoutResourceImpl
	extends BaseDataLayoutResourceImpl implements EntityModelResource {

	@Override
	public void deleteDataLayout(Long dataLayoutId) throws Exception {
		_modelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), dataLayoutId,
			ActionKeys.DELETE);

		_ddmStructureLayoutLocalService.deleteDDMStructureLayout(dataLayoutId);
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

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		if (ArrayUtil.isEmpty(sorts)) {
			sorts = new Sort[] {
				new Sort(
					Field.getSortableFieldName(Field.MODIFIED_DATE),
					Sort.STRING_TYPE, true)
			};
		}

		return SearchUtil.search(
			booleanQuery -> {
			},
			null, DDMStructureLayout.class, keywords, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(
					Field.CLASS_NAME_ID,
					_portal.getClassNameId(InternalDataLayout.class));
				searchContext.setAttribute(Field.DESCRIPTION, keywords);
				searchContext.setAttribute(Field.NAME, keywords);
				searchContext.setAttribute(
					"structureVersionId",
					_getDDMStructureVersionId(dataDefinitionId));
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(
					new long[] {ddmStructure.getGroupId()});
			},
			document -> _toDataLayout(
				_ddmStructureLayoutLocalService.getStructureLayout(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
	}

	@Override
	public DataLayout getDataLayout(Long dataLayoutId) throws Exception {
		return _toDataLayout(_deDataLayoutService.getDataLayout(dataLayoutId));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public DataLayout getSiteDataLayout(Long siteId, String dataLayoutKey)
		throws Exception {

		return _toDataLayout(
			_deDataLayoutService.getDataLayout(siteId, dataLayoutKey));
	}

	@Override
	public Page<DataLayout> getSiteDataLayoutPage(
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

		return SearchUtil.search(
			booleanQuery -> {
			},
			null, DDMStructureLayout.class, keywords, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(
					Field.CLASS_NAME_ID,
					_portal.getClassNameId(InternalDataLayout.class));
				searchContext.setAttribute(Field.DESCRIPTION, keywords);
				searchContext.setAttribute(Field.NAME, keywords);
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(new long[] {siteId});
			},
			document -> _toDataLayout(
				_ddmStructureLayoutLocalService.getStructureLayout(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
	}

	@Override
	public DataLayout postDataDefinitionDataLayout(
			Long dataDefinitionId, DataLayout dataLayout)
		throws Exception {

		DEDataLayout deDataLayout = _toDEDataLayout(dataLayout);

		deDataLayout.setUserId(PrincipalThreadLocal.getUserId());
		deDataLayout.setDeDataDefinitionId(dataDefinitionId);

		return _toDataLayout(
			_deDataLayoutService.addDataLayout(
				deDataLayout, new ServiceContext()));
	}

	public void postDataLayoutDataLayoutPermission(
			Long dataLayoutId, String operation,
			DataLayoutPermission dataLayoutPermission)
		throws Exception {

		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.getStructureLayout(dataLayoutId);

		DataEnginePermissionUtil.checkOperationPermission(
			_groupLocalService, operation, ddmStructureLayout.getGroupId());

		List<String> actionIds = new ArrayList<>();

		if (GetterUtil.getBoolean(dataLayoutPermission.getDelete())) {
			actionIds.add(ActionKeys.DELETE);
		}

		if (GetterUtil.getBoolean(dataLayoutPermission.getUpdate())) {
			actionIds.add(ActionKeys.UPDATE);
		}

		if (GetterUtil.getBoolean(dataLayoutPermission.getView())) {
			actionIds.add(ActionKeys.VIEW);
		}

		if (actionIds.isEmpty()) {
			return;
		}

		DataEnginePermissionUtil.persistModelPermission(
			actionIds, contextCompany, dataLayoutId, operation,
			DataLayoutConstants.RESOURCE_NAME, _resourcePermissionLocalService,
			_roleLocalService, dataLayoutPermission.getRoleNames(),
			ddmStructureLayout.getGroupId());
	}

	@Override
	public void postSiteDataLayoutPermission(
			Long siteId, String operation,
			DataLayoutPermission dataLayoutPermission)
		throws Exception {

		DataEnginePermissionUtil.checkOperationPermission(
			_groupLocalService, operation, siteId);

		List<String> actionIds = new ArrayList<>();

		if (GetterUtil.getBoolean(dataLayoutPermission.getAddDataLayout())) {
			actionIds.add(DataActionKeys.ADD_DATA_LAYOUT);
		}

		if (GetterUtil.getBoolean(
				dataLayoutPermission.getDefinePermissions())) {

			actionIds.add(DataActionKeys.DEFINE_PERMISSIONS);
		}

		if (actionIds.isEmpty()) {
			return;
		}

		DataEnginePermissionUtil.persistPermission(
			actionIds, contextCompany, operation,
			_resourcePermissionLocalService, _roleLocalService,
			dataLayoutPermission.getRoleNames());
	}

	@Override
	public DataLayout putDataLayout(Long dataLayoutId, DataLayout dataLayout)
		throws Exception {

		DEDataLayout deDataLayout = _toDEDataLayout(dataLayout);

		deDataLayout.setDeDataLayoutId(dataLayoutId);
		deDataLayout.setUserId(PrincipalThreadLocal.getUserId());

		return _toDataLayout(
			_deDataLayoutService.updateDataLayout(
				deDataLayout, new ServiceContext()));
	}

	@Reference(
		target = "(model.class.name=com.liferay.data.engine.rest.internal.model.InternalDataLayout)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<InternalDataRecordCollection>
			modelResourcePermission) {

		_modelResourcePermission = modelResourcePermission;
	}

	private long _getDDMStructureId(DDMStructureLayout ddmStructureLayout)
		throws Exception {

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getDDMStructureVersion(
				ddmStructureLayout.getStructureVersionId());

		DDMStructure ddmStructure = ddmStructureVersion.getStructure();

		return ddmStructure.getStructureId();
	}

	private long _getDDMStructureVersionId(Long deDataDefinitionId)
		throws Exception {

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getLatestStructureVersion(
				deDataDefinitionId);

		return ddmStructureVersion.getStructureVersionId();
	}

	private DataLayout _toDataLayout(DDMStructureLayout ddmStructureLayout)
		throws Exception {

		DataLayout dataLayout = DataLayoutUtil.toDataLayout(
			ddmStructureLayout.getDefinition());

		dataLayout.setDateCreated(ddmStructureLayout.getCreateDate());
		dataLayout.setDataDefinitionId(_getDDMStructureId(ddmStructureLayout));
		dataLayout.setDataLayoutKey(ddmStructureLayout.getStructureLayoutKey());
		dataLayout.setDateModified(ddmStructureLayout.getModifiedDate());
		dataLayout.setDescription(
			LocalizedValueUtil.toStringObjectMap(
				ddmStructureLayout.getDescriptionMap()));
		dataLayout.setId(ddmStructureLayout.getStructureLayoutId());
		dataLayout.setName(
			LocalizedValueUtil.toStringObjectMap(
				ddmStructureLayout.getNameMap()));
		dataLayout.setSiteId(ddmStructureLayout.getGroupId());
		dataLayout.setUserId(ddmStructureLayout.getUserId());

		return dataLayout;
	}

	private DataLayout _toDataLayout(DEDataLayout deDataLayout) {
		return new DataLayout() {
			{
				setDateCreated(deDataLayout.getCreateDate());
				setDataDefinitionId(deDataLayout.getDeDataDefinitionId());
				setDataLayoutPages(
					_toDataLayoutPages(deDataLayout.getDEDataLayoutPages()));
				setDataLayoutKey(deDataLayout.getDataLayoutKey());
				setDateModified(deDataLayout.getModifiedDate());
				setDescription(
					_toLanguageIdMap(deDataLayout.getDescriptionMap()));
				setId(deDataLayout.getDeDataLayoutId());
				setName(_toLanguageIdMap(deDataLayout.getNameMap()));
				setSiteId(deDataLayout.getGroupId());
				setPaginationMode(deDataLayout.getPaginationMode());
				setUserId(deDataLayout.getUserId());
			}
		};
	}

	private DataLayoutColumn _toDataLayoutColumn(
		DEDataLayoutColumn deDataLayoutColumn) {

		return new DataLayoutColumn() {
			{
				setColumnSize(deDataLayoutColumn.getColumnSize());
				setFieldNames(deDataLayoutColumn.getFieldNames());
			}
		};
	}

	private DataLayoutColumn[] _toDataLayoutColumns(
		List<DEDataLayoutColumn> deDataLayoutColumns) {

		Stream<DEDataLayoutColumn> stream = deDataLayoutColumns.stream();

		return stream.map(
			this::_toDataLayoutColumn
		).collect(
			Collectors.toList()
		).toArray(
			new DataLayoutColumn[0]
		);
	}

	private DataLayoutPage _toDataLayoutPage(
		DEDataLayoutPage deDataLayoutPage) {

		return new DataLayoutPage() {
			{
				setDataLayoutRows(
					_toDataLayoutRows(deDataLayoutPage.getDEDataLayoutRows()));
				setDescription(deDataLayoutPage.getDescription());
				setTitle(deDataLayoutPage.getTitle());
			}
		};
	}

	private DataLayoutPage[] _toDataLayoutPages(
		List<DEDataLayoutPage> deDataLayoutPages) {

		Stream<DEDataLayoutPage> stream = deDataLayoutPages.stream();

		return stream.map(
			this::_toDataLayoutPage
		).collect(
			Collectors.toList()
		).toArray(
			new DataLayoutPage[0]
		);
	}

	private DataLayoutRow _toDataLayoutRow(DEDataLayoutRow deDataLayoutRow) {
		return new DataLayoutRow() {
			{
				setDataLayoutColumns(
					_toDataLayoutColumns(
						deDataLayoutRow.getDEDataLayoutColumns()));
			}
		};
	}

	private DataLayoutRow[] _toDataLayoutRows(
		List<DEDataLayoutRow> deDataLayoutRows) {

		Stream<DEDataLayoutRow> stream = deDataLayoutRows.stream();

		return stream.map(
			this::_toDataLayoutRow
		).collect(
			Collectors.toList()
		).toArray(
			new DataLayoutRow[0]
		);
	}

	private DEDataLayout _toDEDataLayout(DataLayout dataLayout) {
		DEDataLayoutBuilder deDataLayoutBuilder =
			_deModelBuilderFactory.newDEDataLayoutBuilder();

		deDataLayoutBuilder = deDataLayoutBuilder.companyId(
			contextCompany.getCompanyId()
		).createDate(
			dataLayout.getDateCreated()
		).dataDefinitionId(
			GetterUtil.getLong(dataLayout.getDataDefinitionId())
		).dataLayoutKey(
			dataLayout.getDataLayoutKey()
		).description(
			_toLocaleMap(dataLayout.getDescription())
		).groupId(
			GetterUtil.getLong(dataLayout.getSiteId())
		).id(
			GetterUtil.getLong(dataLayout.getId())
		).modifiedDate(
			dataLayout.getDateModified()
		).name(
			_toLocaleMap(dataLayout.getName())
		).pages(
			_toDEDataLayoutPages(dataLayout.getDataLayoutPages())
		).paginationMode(
			dataLayout.getPaginationMode()
		);

		return deDataLayoutBuilder.build();
	}

	private DEDataLayoutColumn _toDEDataLayoutColumn(
		DataLayoutColumn dataLayoutColumn) {

		DEDataLayoutColumnBuilder deDataLayoutColumnBuilder =
			_deModelBuilderFactory.newDEDataLayoutColumnBuilder();

		deDataLayoutColumnBuilder = deDataLayoutColumnBuilder.columnSize(
			dataLayoutColumn.getColumnSize()
		).fieldNames(
			dataLayoutColumn.getFieldNames()
		);

		return deDataLayoutColumnBuilder.build();
	}

	private List<DEDataLayoutColumn> _toDEDataLayoutColumns(
		DataLayoutColumn[] dataLayoutColumns) {

		if (dataLayoutColumns == null) {
			return Collections.emptyList();
		}

		return Stream.of(
			dataLayoutColumns
		).map(
			this::_toDEDataLayoutColumn
		).collect(
			Collectors.toList()
		);
	}

	private DEDataLayoutPage _toDEDataLayoutPage(
		DataLayoutPage dataLayoutPage) {

		DEDataLayoutPageBuilder deDataLayoutPageBuilder =
			_deModelBuilderFactory.newDEDataLayoutPageBuilder();

		deDataLayoutPageBuilder = deDataLayoutPageBuilder.description(
			dataLayoutPage.getDescription()
		).rows(
			_toDEDataLayoutRows(dataLayoutPage.getDataLayoutRows())
		).title(
			dataLayoutPage.getTitle()
		);

		return deDataLayoutPageBuilder.build();
	}

	private List<DEDataLayoutPage> _toDEDataLayoutPages(
		DataLayoutPage[] dataLayoutPages) {

		if (dataLayoutPages == null) {
			return Collections.emptyList();
		}

		return Stream.of(
			dataLayoutPages
		).map(
			this::_toDEDataLayoutPage
		).collect(
			Collectors.toList()
		);
	}

	private DEDataLayoutRow _toDEDataLayoutRow(DataLayoutRow dataLayoutRow) {
		DEDataLayoutRowBuilder deDataLayoutRowBuilder =
			_deModelBuilderFactory.newDEDataLayoutRowBuilder();

		deDataLayoutRowBuilder = deDataLayoutRowBuilder.columns(
			_toDEDataLayoutColumns(dataLayoutRow.getDataLayoutColumns()));

		return deDataLayoutRowBuilder.build();
	}

	private List<DEDataLayoutRow> _toDEDataLayoutRows(
		DataLayoutRow[] dataLayoutRows) {

		if (dataLayoutRows == null) {
			return Collections.emptyList();
		}

		return Stream.of(
			dataLayoutRows
		).map(
			this::_toDEDataLayoutRow
		).collect(
			Collectors.toList()
		);
	}

	private Map<String, Object> _toLanguageIdMap(
		Map<Locale, String> description) {

		if (MapUtil.isEmpty(description)) {
			return Collections.emptyMap();
		}

		Map<String, Object> localeMap = new HashMap<>();

		for (Map.Entry<Locale, String> entry : description.entrySet()) {
			localeMap.put(
				LanguageUtil.getLanguageId(entry.getKey()), entry.getValue());
		}

		return localeMap;
	}

	private Map<Locale, String> _toLocaleMap(Map<String, Object> description) {
		if (MapUtil.isEmpty(description)) {
			return Collections.emptyMap();
		}

		Map<Locale, String> localeMap = new HashMap<>();

		for (Map.Entry<String, Object> entry : description.entrySet()) {
			localeMap.put(
				LocaleUtil.fromLanguageId(entry.getKey()),
				GetterUtil.getString(entry.getValue()));
		}

		return localeMap;
	}

	private static final EntityModel _entityModel = new DataLayoutEntityModel();

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	@Reference
	private DEDataLayoutService _deDataLayoutService;

	@Reference
	private DEModelBuilderFactory _deModelBuilderFactory;

	@Reference
	private GroupLocalService _groupLocalService;

	private ModelResourcePermission<InternalDataRecordCollection>
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
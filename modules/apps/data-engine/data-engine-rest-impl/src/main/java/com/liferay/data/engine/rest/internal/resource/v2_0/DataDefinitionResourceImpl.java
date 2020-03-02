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

import com.liferay.data.engine.content.type.DataDefinitionContentTypeTracker;
import com.liferay.data.engine.model.DEDataListView;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.internal.constants.DataActionKeys;
import com.liferay.data.engine.rest.internal.dto.v2_0.util.DataDefinitionUtil;
import com.liferay.data.engine.rest.internal.odata.entity.v2_0.DataDefinitionEntityModel;
import com.liferay.data.engine.rest.internal.security.permission.resource.DataDefinitionModelResourcePermission;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.data.engine.service.DEDataDefinitionFieldLinkLocalService;
import com.liferay.data.engine.service.DEDataListViewLocalService;
import com.liferay.data.engine.spi.model.SPIDataDefinition;
import com.liferay.data.engine.spi.resource.SPIDataDefinitionResource;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutFactory;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.permission.ModelPermissionsUtil;
import com.liferay.portal.vulcan.permission.Permission;
import com.liferay.portal.vulcan.permission.PermissionUtil;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v2_0/data-definition.properties",
	scope = ServiceScope.PROTOTYPE, service = DataDefinitionResource.class
)
public class DataDefinitionResourceImpl
	extends BaseDataDefinitionResourceImpl implements EntityModelResource {

	@Override
	public void deleteDataDefinition(Long dataDefinitionId) throws Exception {
		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), dataDefinitionId,
			ActionKeys.DELETE);

		SPIDataDefinitionResource spiDataDefinitionResource =
			_getSPIDataDefinitionResource();

		spiDataDefinitionResource.deleteDataDefinition(dataDefinitionId);
	}

	@Override
	public DataDefinition getDataDefinition(Long dataDefinitionId)
		throws Exception {

		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), dataDefinitionId,
			ActionKeys.VIEW);

		SPIDataDefinitionResource spiDataDefinitionResource =
			_getSPIDataDefinitionResource();

		return DataDefinitionUtil.toDataDefinition(
			spiDataDefinitionResource.getDataDefinition(dataDefinitionId));
	}

	@Override
	public Page<DataDefinition> getDataDefinitionByContentTypeContentTypePage(
			String contentType, String keywords, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		SPIDataDefinitionResource spiDataDefinitionResource =
			_getSPIDataDefinitionResource();

		Page<SPIDataDefinition> page =
			spiDataDefinitionResource.getDataDefinitionsByContentTypePage(
				contextCompany.getCompanyId(), contentType, keywords,
				_portal.getSiteGroupId(contextCompany.getGroupId()),
				contextAcceptLanguage.getPreferredLocale(), pagination, sorts);

		Collection<SPIDataDefinition> items = page.getItems();

		Stream<SPIDataDefinition> stream = items.stream();

		return Page.of(
			stream.map(
				DataDefinitionUtil::toDataDefinition
			).collect(
				Collectors.toList()
			),
			pagination, page.getTotalCount());
	}

	@Override
	public String getDataDefinitionDataDefinitionFieldFieldTypes()
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		Set<String> ddmFormFieldTypeNames =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeNames();

		Stream<String> stream = ddmFormFieldTypeNames.stream();

		stream.map(
			ddmFormFieldTypeName -> _getFieldTypeMetadataJSONObject(
				ddmFormFieldTypeName, contextHttpServletRequest,
				contextAcceptLanguage.getPreferredLocale(),
				_getResourceBundle(
					ddmFormFieldTypeName,
					contextAcceptLanguage.getPreferredLocale()))
		).filter(
			jsonObject -> !jsonObject.getBoolean("system")
		).forEach(
			jsonArray::put
		);

		return jsonArray.toJSONString();
	}

	@Override
	public String getDataDefinitionDataDefinitionFieldLinks(
			Long dataDefinitionId, String fieldName)
		throws Exception {

		return JSONUtil.put(
			"dataLayouts",
			TransformUtil.transformToArray(
				_deDataDefinitionFieldLinkLocalService.
					getDEDataDefinitionFieldLinks(
						_getClassNameId(dataDefinitionId), dataDefinitionId,
						fieldName),
				deDataDefinitionFieldLink -> {
					DDMStructureLayout ddmStructureLayout =
						_ddmStructureLayoutLocalService.getDDMStructureLayout(
							deDataDefinitionFieldLink.getClassPK());

					return ddmStructureLayout.getName(
						ddmStructureLayout.getDefaultLanguageId());
				},
				String.class)
		).put(
			"dataListViews",
			TransformUtil.transformToArray(
				_deDataDefinitionFieldLinkLocalService.
					getDEDataDefinitionFieldLinks(
						_portal.getClassNameId(DEDataListView.class),
						dataDefinitionId, fieldName),
				deDataDefinitionFieldLink -> {
					DEDataListView deDataListView =
						_deDataListViewLocalService.getDEDataListView(
							deDataDefinitionFieldLink.getClassPK());

					return deDataListView.getName(
						deDataListView.getDefaultLanguageId());
				},
				String.class)
		).toString();
	}

	@Override
	public Page<Permission> getDataDefinitionPermissionsPage(
			Long dataDefinitionId, String roleNames)
		throws Exception {

		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), dataDefinitionId,
			ActionKeys.PERMISSIONS);

		String resourceName = getPermissionCheckerResourceName(
			dataDefinitionId);

		return Page.of(
			transform(
				PermissionUtil.getRoles(
					contextCompany, roleLocalService,
					StringUtil.split(roleNames)),
				role -> PermissionUtil.toPermission(
					contextCompany.getCompanyId(), dataDefinitionId,
					resourceActionLocalService.getResourceActions(resourceName),
					resourceName, resourcePermissionLocalService, role)));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public DataDefinition getSiteDataDefinitionByContentTypeByDataDefinitionKey(
			Long siteId, String contentType, String dataDefinitionKey)
		throws Exception {

		SPIDataDefinitionResource spiDataDefinitionResource =
			_getSPIDataDefinitionResource();

		return DataDefinitionUtil.toDataDefinition(
			spiDataDefinitionResource.
				getSiteDataDefinitionByContentTypeByDataDefinitionKey(
					contentType, dataDefinitionKey, siteId));
	}

	@Override
	public Page<DataDefinition>
			getSiteDataDefinitionByContentTypeContentTypePage(
				Long siteId, String contentType, String keywords,
				Pagination pagination, Sort[] sorts)
		throws Exception {

		SPIDataDefinitionResource spiDataDefinitionResource =
			_getSPIDataDefinitionResource();

		Page<SPIDataDefinition> page =
			spiDataDefinitionResource.getSiteDataDefinitionsByContentTypePage(
				contextCompany.getCompanyId(), contentType, keywords,
				contextAcceptLanguage.getPreferredLocale(), pagination, siteId,
				sorts);

		Collection<SPIDataDefinition> items = page.getItems();

		Stream<SPIDataDefinition> stream = items.stream();

		return Page.of(
			stream.map(
				DataDefinitionUtil::toDataDefinition
			).collect(
				Collectors.toList()
			),
			pagination, page.getTotalCount());
	}

	@Override
	public DataDefinition postDataDefinitionByContentType(
			String contentType, DataDefinition dataDefinition)
		throws Exception {

		return postSiteDataDefinitionByContentType(
			_portal.getSiteGroupId(contextCompany.getGroupId()), contentType,
			dataDefinition);
	}

	@Override
	public DataDefinition postSiteDataDefinitionByContentType(
			Long siteId, String contentType, DataDefinition dataDefinition)
		throws Exception {

		_dataDefinitionModelResourcePermission.checkPortletPermission(
			PermissionThreadLocal.getPermissionChecker(), contentType, siteId,
			DataActionKeys.ADD_DATA_DEFINITION);

		SPIDataDefinitionResource spiDataDefinitionResource =
			_getSPIDataDefinitionResource();

		return DataDefinitionUtil.toDataDefinition(
			spiDataDefinitionResource.addDataDefinitionByContentType(
				contextCompany.getCompanyId(), contentType, siteId,
				DataDefinitionUtil.toSPIDataDefinition(dataDefinition)));
	}

	@Override
	public DataDefinition putDataDefinition(
			Long dataDefinitionId, DataDefinition dataDefinition)
		throws Exception {

		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), dataDefinitionId,
			ActionKeys.UPDATE);

		SPIDataDefinitionResource spiDataDefinitionResource =
			_getSPIDataDefinitionResource();

		return DataDefinitionUtil.toDataDefinition(
			spiDataDefinitionResource.updateDataDefinition(
				dataDefinitionId,
				DataDefinitionUtil.toSPIDataDefinition(dataDefinition)));
	}

	@Override
	public void putDataDefinitionPermission(
			Long dataDefinitionId, Permission[] permissions)
		throws Exception {

		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), dataDefinitionId,
			ActionKeys.PERMISSIONS);

		String resourceName = getPermissionCheckerResourceName(
			dataDefinitionId);

		resourcePermissionLocalService.updateResourcePermissions(
			contextCompany.getCompanyId(), 0, resourceName,
			String.valueOf(dataDefinitionId),
			ModelPermissionsUtil.toModelPermissions(
				contextCompany.getCompanyId(), permissions, dataDefinitionId,
				resourceName, resourceActionLocalService,
				resourcePermissionLocalService, roleLocalService));
	}

	@Override
	protected Long getPermissionCheckerGroupId(Object id) throws Exception {
		DDMStructure ddmStructure = _ddmStructureLocalService.getDDMStructure(
			(long)id);

		return ddmStructure.getGroupId();
	}

	@Override
	protected String getPermissionCheckerResourceName(Object id)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getDDMStructure(
			(long)id);

		return ResourceActionsUtil.getCompositeModelName(
			_portal.getClassName(ddmStructure.getClassNameId()),
			DDMStructure.class.getName());
	}

	private JSONObject _createFieldContextJSONObject(
		DDMFormFieldType ddmFormFieldType,
		HttpServletRequest httpServletRequest, Locale locale, String type) {

		Locale originalThemeDisplayLocale =
			LocaleThreadLocal.getThemeDisplayLocale();

		LocaleThreadLocal.setThemeDisplayLocale(locale);

		try {
			DDMForm ddmFormFieldTypeSettingsDDMForm = DDMFormFactory.create(
				ddmFormFieldType.getDDMFormFieldTypeSettings());

			DDMFormRenderingContext ddmFormRenderingContext =
				new DDMFormRenderingContext();

			ddmFormRenderingContext.setContainerId("settings");

			DDMFormValues ddmFormValues = _ddmFormValuesFactory.create(
				httpServletRequest, ddmFormFieldTypeSettingsDDMForm);

			_setTypeDDMFormFieldValue(ddmFormValues, type);

			ddmFormRenderingContext.setDDMFormValues(ddmFormValues);

			ddmFormRenderingContext.setHttpServletRequest(httpServletRequest);
			ddmFormRenderingContext.setLocale(locale);
			ddmFormRenderingContext.setPortletNamespace(
				ParamUtil.getString(httpServletRequest, "portletNamespace"));
			ddmFormRenderingContext.setReturnFullContext(true);

			return JSONFactoryUtil.createJSONObject(
				JSONFactoryUtil.looseSerializeDeep(
					_ddmFormTemplateContextFactory.create(
						ddmFormFieldTypeSettingsDDMForm,
						DDMFormLayoutFactory.create(
							ddmFormFieldType.getDDMFormFieldTypeSettings()),
						ddmFormRenderingContext)));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}
		finally {
			LocaleThreadLocal.setThemeDisplayLocale(originalThemeDisplayLocale);
		}

		return null;
	}

	private long _getClassNameId(long dataDefinitionId) throws PortalException {
		DDMStructure ddmStructure = _ddmStructureLocalService.getDDMStructure(
			dataDefinitionId);

		return ddmStructure.getClassNameId();
	}

	private JSONObject _getFieldTypeMetadataJSONObject(
		String ddmFormFieldName, HttpServletRequest httpServletRequest,
		Locale locale, ResourceBundle resourceBundle) {

		Map<String, Object> ddmFormFieldTypeProperties =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeProperties(
				ddmFormFieldName);

		DDMFormFieldType ddmFormFieldType =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldType(
				ddmFormFieldName);

		return JSONUtil.put(
			"description",
			_translate(
				MapUtil.getString(
					ddmFormFieldTypeProperties,
					"ddm.form.field.type._description"),
				resourceBundle)
		).put(
			"displayOrder",
			MapUtil.getInteger(
				ddmFormFieldTypeProperties, "ddm.form.field.type.display.order",
				Integer.MAX_VALUE)
		).put(
			"group",
			MapUtil.getString(
				ddmFormFieldTypeProperties, "ddm.form.field.type.group")
		).put(
			"icon",
			MapUtil.getString(
				ddmFormFieldTypeProperties, "ddm.form.field.type.icon")
		).put(
			"javaScriptModule", _resolveModuleName(ddmFormFieldType)
		).put(
			"label",
			_translate(
				MapUtil.getString(
					ddmFormFieldTypeProperties, "ddm.form.field.type.label"),
				resourceBundle)
		).put(
			"name", ddmFormFieldName
		).put(
			"scope",
			MapUtil.getString(
				ddmFormFieldTypeProperties, "ddm.form.field.type.scope")
		).put(
			"settingsContext",
			_createFieldContextJSONObject(
				ddmFormFieldType, httpServletRequest, locale, ddmFormFieldName)
		).put(
			"system",
			MapUtil.getBoolean(
				ddmFormFieldTypeProperties, "ddm.form.field.type.system")
		);
	}

	private ResourceBundle _getResourceBundle(
		String ddmFormFieldTypeName, Locale locale) {

		DDMFormFieldType ddmFormFieldType =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldType(
				ddmFormFieldTypeName);

		return new AggregateResourceBundle(
			ResourceBundleUtil.getBundle(
				"content.Language", locale, getClass()),
			ResourceBundleUtil.getBundle(
				"content.Language", locale, ddmFormFieldType.getClass()),
			_portal.getResourceBundle(locale));
	}

	private SPIDataDefinitionResource _getSPIDataDefinitionResource() {
		return new SPIDataDefinitionResource(
			_dataDefinitionContentTypeTracker, _ddlRecordSetLocalService,
			_ddmFormFieldTypeServicesTracker, _ddmFormLayoutSerializer,
			_ddmFormSerializer, _ddmStructureLayoutLocalService,
			_ddmStructureLocalService, _ddmStructureVersionLocalService,
			_deDataDefinitionFieldLinkLocalService, _deDataListViewLocalService,
			_jsonFactory, _portal, _resourceLocalService);
	}

	private String _resolveModuleName(DDMFormFieldType ddmFormFieldType) {
		if (Validator.isNull(ddmFormFieldType.getModuleName())) {
			return StringPool.BLANK;
		}

		if (ddmFormFieldType.isCustomDDMFormFieldType()) {
			return ddmFormFieldType.getModuleName();
		}

		return _npmResolver.resolveModuleName(ddmFormFieldType.getModuleName());
	}

	private void _setTypeDDMFormFieldValue(
		DDMFormValues ddmFormValues, String type) {

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			"type");

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		ddmFormFieldValue.setValue(new UnlocalizedValue(type));
	}

	private String _translate(String key, ResourceBundle resourceBundle) {
		if (Validator.isNull(key)) {
			return StringPool.BLANK;
		}

		return GetterUtil.getString(
			ResourceBundleUtil.getString(resourceBundle, key), key);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataDefinitionResourceImpl.class);

	private static final EntityModel _entityModel =
		new DataDefinitionEntityModel();

	@Reference
	private DataDefinitionContentTypeTracker _dataDefinitionContentTypeTracker;

	@Reference
	private DataDefinitionModelResourcePermission
		_dataDefinitionModelResourcePermission;

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference(target = "(ddm.form.layout.serializer.type=json)")
	private DDMFormLayoutSerializer _ddmFormLayoutSerializer;

	@Reference(target = "(ddm.form.serializer.type=json)")
	private DDMFormSerializer _ddmFormSerializer;

	@Reference
	private DDMFormTemplateContextFactory _ddmFormTemplateContextFactory;

	@Reference
	private DDMFormValuesFactory _ddmFormValuesFactory;

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
	private DEDataListViewLocalService _deDataListViewLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private NPMResolver _npmResolver;

	@Reference
	private Portal _portal;

	@Reference
	private ResourceLocalService _resourceLocalService;

}
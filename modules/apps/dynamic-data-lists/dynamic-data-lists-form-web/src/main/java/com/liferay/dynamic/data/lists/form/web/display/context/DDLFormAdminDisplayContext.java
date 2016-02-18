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

package com.liferay.dynamic.data.lists.form.web.display.context;

import com.liferay.dynamic.data.lists.constants.DDLActionKeys;
import com.liferay.dynamic.data.lists.constants.DDLWebKeys;
import com.liferay.dynamic.data.lists.form.web.configuration.DDLFormWebConfiguration;
import com.liferay.dynamic.data.lists.form.web.display.context.util.DDLFormAdminRequestHelper;
import com.liferay.dynamic.data.lists.form.web.search.RecordSetSearchTerms;
import com.liferay.dynamic.data.lists.form.web.util.DDLFormAdminPortletUtil;
import com.liferay.dynamic.data.lists.model.DDLFormRecord;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSetSettings;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetService;
import com.liferay.dynamic.data.lists.service.permission.DDLPermission;
import com.liferay.dynamic.data.lists.service.permission.DDLRecordSetPermission;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesJSONSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutJSONSerializer;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowEngineManager;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Bruno Basto
 */
public class DDLFormAdminDisplayContext {

	public DDLFormAdminDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		DDLFormWebConfiguration ddlFormWebConfiguration,
		DDLRecordLocalService ddlRecordLocalService,
		DDLRecordSetService ddlRecordSetService,
		DDMDataProviderInstanceLocalService ddmDataProviderInstanceLocalService,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		DDMFormFieldTypesJSONSerializer ddmFormFieldTypesJSONSerializer,
		DDMFormJSONSerializer ddmFormJSONSerializer,
		DDMFormLayoutJSONSerializer ddmFormLayoutJSONSerializer,
		DDMFormRenderer ddmFormRenderer,
		DDMStructureLocalService ddmStructureLocalService,
		JSONFactory jsonFactory, StorageEngine storageEngine,
		WorkflowEngineManager workflowEngineManager) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_ddlFormWebConfiguration = ddlFormWebConfiguration;
		_ddlRecordLocalService = ddlRecordLocalService;
		_ddlRecordSetService = ddlRecordSetService;
		_ddmDataProviderInstanceLocalService =
			ddmDataProviderInstanceLocalService;
		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
		_ddmFormFieldTypesJSONSerializer = ddmFormFieldTypesJSONSerializer;
		_ddmFormJSONSerializer = ddmFormJSONSerializer;
		_ddmFormLayoutJSONSerializer = ddmFormLayoutJSONSerializer;
		_ddmFormRenderer = ddmFormRenderer;
		_ddmStructureLocalService = ddmStructureLocalService;
		_jsonFactory = jsonFactory;
		_storageEngine = storageEngine;
		_workflowEngineManager = workflowEngineManager;

		_ddlFormAdminRequestHelper = new DDLFormAdminRequestHelper(
			renderRequest);
	}

	public DDLFormViewRecordsDisplayContext getDDLFormViewDisplayContext()
		throws PortalException {

		return new DDLFormViewRecordsDisplayContext(
			_renderRequest, _renderResponse, getRecordSet(),
			_ddlRecordLocalService, _ddmFormFieldTypeServicesTracker,
			_storageEngine);
	}

	public JSONArray getDDMFormFieldTypesJSONArray() throws PortalException {
		List<DDMFormFieldType> ddmFormFieldTypes =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypes();

		String serializedDDMFormFieldTypes =
			_ddmFormFieldTypesJSONSerializer.serialize(ddmFormFieldTypes);

		return _jsonFactory.createJSONArray(serializedDDMFormFieldTypes);
	}

	public String getDDMFormHTML() throws PortalException {
		DDLRecord record = getRecord();

		DDMFormRenderingContext ddmFormRenderingContext =
			createDDMFormRenderingContext();

		ddmFormRenderingContext.setDDMFormValues(record.getDDMFormValues());

		DDMStructure ddmStructure = getDDMStructure();

		DDMForm ddmForm = ddmStructure.getDDMForm();

		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			setDDMFormFieldReadOnly(ddmFormField);
		}

		DDMFormLayout ddmFormLayout = ddmStructure.getDDMFormLayout();

		return getDDMFormRenderer().render(
			ddmForm, ddmFormLayout, ddmFormRenderingContext);
	}

	public DDMStructure getDDMStructure() throws PortalException {
		if (_ddmStucture != null) {
			return _ddmStucture;
		}

		DDLRecordSet recordSet = getRecordSet();

		if (recordSet == null) {
			return null;
		}

		_ddmStucture = _ddmStructureLocalService.getStructure(
			recordSet.getDDMStructureId());

		return _ddmStucture;
	}

	public String getDisplayStyle() {
		if (_displayStyle == null) {
			_displayStyle = DDLFormAdminPortletUtil.getDisplayStyle(
				_renderRequest, _ddlFormWebConfiguration, getDisplayViews());
		}

		return _displayStyle;
	}

	public String[] getDisplayViews() {
		return _DISPLAY_VIEWS;
	}

	public String getOrderByCol() {
		String orderByCol = ParamUtil.getString(
			_renderRequest, "orderByCol", "modified-date");

		return orderByCol;
	}

	public String getOrderByType() {
		String orderByType = ParamUtil.getString(
			_renderRequest, "orderByType", "asc");

		return orderByType;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/admin/view.jsp");
		portletURL.setParameter(
			"groupId",
			String.valueOf(_ddlFormAdminRequestHelper.getScopeGroupId()));

		return portletURL;
	}

	public String getPreviewFormURL() throws PortalException {
		String publishedFormURL = getPublishedFormURL();

		if (Validator.isNull(publishedFormURL)) {
			return publishedFormURL;
		}

		return publishedFormURL.concat("/preview");
	}

	public String getPublishedFormURL() throws PortalException {
		if (_recordSet == null) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(4);

		ThemeDisplay themeDisplay =
			_ddlFormAdminRequestHelper.getThemeDisplay();

		Group group = themeDisplay.getSiteGroup();

		sb.append(themeDisplay.getPortalURL());
		sb.append(group.getPathFriendlyURL(false, themeDisplay));
		sb.append("/forms/shared/-/form/");
		sb.append(_recordSet.getRecordSetId());

		return sb.toString();
	}

	public DDLRecordSet getRecordSet() throws PortalException {
		if (_recordSet != null) {
			return _recordSet;
		}

		long recordSetId = ParamUtil.getLong(_renderRequest, "recordSetId");

		if (recordSetId > 0) {
			_recordSet = _ddlRecordSetService.fetchRecordSet(recordSetId);
		}
		else {
			DDLRecord ddlRecord = getRecord();

			if (ddlRecord != null) {
				_recordSet = ddlRecord.getRecordSet();
			}
		}

		return _recordSet;
	}

	public List<DDLRecordSet> getSearchContainerResults(
			SearchContainer<DDLRecordSet> searchContainer)
		throws PortalException {

		RecordSetSearchTerms searchTerms =
			(RecordSetSearchTerms)searchContainer.getSearchTerms();

		if (searchTerms.isAdvancedSearch()) {
			return _ddlRecordSetService.search(
				_ddlFormAdminRequestHelper.getCompanyId(),
				_ddlFormAdminRequestHelper.getScopeGroupId(),
				searchTerms.getName(), searchTerms.getDescription(),
				DDLRecordSetConstants.SCOPE_FORMS, searchTerms.isAndOperator(),
				searchContainer.getStart(), searchContainer.getEnd(),
				searchContainer.getOrderByComparator());
		}
		else {
			return _ddlRecordSetService.search(
				_ddlFormAdminRequestHelper.getCompanyId(),
				_ddlFormAdminRequestHelper.getScopeGroupId(),
				searchTerms.getKeywords(), DDLRecordSetConstants.SCOPE_FORMS,
				searchContainer.getStart(), searchContainer.getEnd(),
				searchContainer.getOrderByComparator());
		}
	}

	public int getSearchContainerTotal(
			SearchContainer<DDLRecordSet> searchContainer)
		throws PortalException {

		RecordSetSearchTerms searchTerms =
			(RecordSetSearchTerms)searchContainer.getSearchTerms();

		if (searchTerms.isAdvancedSearch()) {
			return _ddlRecordSetService.searchCount(
				_ddlFormAdminRequestHelper.getCompanyId(),
				_ddlFormAdminRequestHelper.getScopeGroupId(),
				searchTerms.getName(), searchTerms.getDescription(),
				DDLRecordSetConstants.SCOPE_FORMS, searchTerms.isAndOperator());
		}
		else {
			return _ddlRecordSetService.searchCount(
				_ddlFormAdminRequestHelper.getCompanyId(),
				_ddlFormAdminRequestHelper.getScopeGroupId(),
				searchTerms.getKeywords(), DDLRecordSetConstants.SCOPE_FORMS);
		}
	}

	public String getSerializedDDMDataProviders() throws PortalException {
		ThemeDisplay themeDisplay =
			_ddlFormAdminRequestHelper.getThemeDisplay();

		List<DDMDataProviderInstance> ddmDataProviderInstances =
			_ddmDataProviderInstanceLocalService.getDataProviderInstances(
				PortalUtil.getCurrentAndAncestorSiteGroupIds(
					themeDisplay.getScopeGroupId()));

		return serialize(ddmDataProviderInstances, themeDisplay.getLocale());
	}

	public String getSerializedDDMForm() throws PortalException {
		String definition = ParamUtil.getString(_renderRequest, "definition");

		if (Validator.isNotNull(definition)) {
			return definition;
		}

		DDMStructure ddmStructure = getDDMStructure();

		DDMForm ddmForm = new DDMForm();

		if (ddmStructure != null) {
			ddmForm = ddmStructure.getDDMForm();
		}

		return _ddmFormJSONSerializer.serialize(ddmForm);
	}

	public String getSerializedDDMFormLayout() throws PortalException {
		String layout = ParamUtil.getString(_renderRequest, "layout");

		if (Validator.isNotNull(layout)) {
			return layout;
		}

		DDMStructure ddmStructure = getDDMStructure();

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		if (ddmStructure != null) {
			ddmFormLayout = ddmStructure.getDDMFormLayout();
		}

		return _ddmFormLayoutJSONSerializer.serialize(ddmFormLayout);
	}

	public boolean isDDLRecordWorkflowHandlerDeployed() {
		if (!_workflowEngineManager.isDeployed()) {
			return false;
		}

		WorkflowHandler<DDLRecord> ddlRecordWorkflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(
				DDLRecord.class.getName());

		if (ddlRecordWorkflowHandler != null) {
			return true;
		}

		return false;
	}

	public boolean isFormPublished() throws PortalException {
		DDLRecordSet recordSet = getRecordSet();

		if (recordSet == null) {
			return false;
		}

		DDLRecordSetSettings recordSetSettings = recordSet.getSettingsModel();

		return recordSetSettings.published();
	}

	public boolean isShowAddRecordSetButton() {
		return DDLPermission.contains(
			_ddlFormAdminRequestHelper.getPermissionChecker(),
			_ddlFormAdminRequestHelper.getScopeGroupId(),
			DDLActionKeys.ADD_RECORD_SET);
	}

	public boolean isShowDeleteRecordSetIcon(DDLRecordSet recordSet) {
		return DDLRecordSetPermission.contains(
			_ddlFormAdminRequestHelper.getPermissionChecker(), recordSet,
			ActionKeys.DELETE);
	}

	public boolean isShowEditRecordSetIcon(DDLRecordSet recordSet) {
		return DDLRecordSetPermission.contains(
			_ddlFormAdminRequestHelper.getPermissionChecker(), recordSet,
			ActionKeys.UPDATE);
	}

	public boolean isShowExportRecordSetIcon(DDLRecordSet recordSet) {
		return DDLRecordSetPermission.contains(
			_ddlFormAdminRequestHelper.getPermissionChecker(), recordSet,
			ActionKeys.VIEW);
	}

	public boolean isShowPermissionsIcon(DDLRecordSet recordSet) {
		return DDLRecordSetPermission.contains(
			_ddlFormAdminRequestHelper.getPermissionChecker(), recordSet,
			ActionKeys.PERMISSIONS);
	}

	public boolean isShowViewEntriesRecordSetIcon(DDLRecordSet recordSet) {
		return DDLRecordSetPermission.contains(
			_ddlFormAdminRequestHelper.getPermissionChecker(), recordSet,
			ActionKeys.VIEW);
	}

	protected DDMFormRenderingContext createDDMFormRenderingContext() {
		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setHttpServletRequest(
			PortalUtil.getHttpServletRequest(_renderRequest));
		ddmFormRenderingContext.setHttpServletResponse(
			PortalUtil.getHttpServletResponse(_renderResponse));
		ddmFormRenderingContext.setLocale(
			_ddlFormAdminRequestHelper.getLocale());
		ddmFormRenderingContext.setPortletNamespace(
			_renderResponse.getNamespace());
		ddmFormRenderingContext.setReadOnly(true);

		return ddmFormRenderingContext;
	}

	protected DDMFormRenderer getDDMFormRenderer() {
		return _ddmFormRenderer;
	}

	protected DDLRecord getRecord() throws PortalException {
		long recordId = ParamUtil.getLong(_renderRequest, "recordId");

		if (recordId > 0) {
			return _ddlRecordLocalService.fetchRecord(recordId);
		}

		HttpServletRequest httpServletRequest =
			_ddlFormAdminRequestHelper.getRequest();

		Object record = httpServletRequest.getAttribute(
			DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD);

		if (record instanceof DDLFormRecord) {
			DDLFormRecord formRecord = (DDLFormRecord)record;

			return formRecord.getDDLRecord();
		}
		else {
			return (DDLRecord)record;
		}
	}

	protected String serialize(
		List<DDMDataProviderInstance> ddmDataProviderInstances, Locale locale) {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (DDMDataProviderInstance ddmDataProviderInstance :
				ddmDataProviderInstances) {

			JSONObject jsonObject = toJSONObject(
				ddmDataProviderInstance, locale);

			jsonArray.put(jsonObject);
		}

		return jsonArray.toString();
	}

	protected void setDDMFormFieldReadOnly(DDMFormField ddmFormField) {
		ddmFormField.setReadOnly(true);

		for (DDMFormField nestedDDMFormField :
				ddmFormField.getNestedDDMFormFields()) {

			setDDMFormFieldReadOnly(nestedDDMFormField);
		}
	}

	protected JSONObject toJSONObject(
		DDMDataProviderInstance ddmDataProviderInstance, Locale locale) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put(
			"id", ddmDataProviderInstance.getDataProviderInstanceId());
		jsonObject.put("name", ddmDataProviderInstance.getName(locale));

		return jsonObject;
	}

	private static final String[] _DISPLAY_VIEWS = {"descriptive", "list"};

	private final DDLFormAdminRequestHelper _ddlFormAdminRequestHelper;
	private final DDLFormWebConfiguration _ddlFormWebConfiguration;
	private final DDLRecordLocalService _ddlRecordLocalService;
	private final DDLRecordSetService _ddlRecordSetService;
	private final DDMDataProviderInstanceLocalService
		_ddmDataProviderInstanceLocalService;
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;
	private final DDMFormFieldTypesJSONSerializer
		_ddmFormFieldTypesJSONSerializer;
	private final DDMFormJSONSerializer _ddmFormJSONSerializer;
	private final DDMFormLayoutJSONSerializer _ddmFormLayoutJSONSerializer;
	private final DDMFormRenderer _ddmFormRenderer;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private DDMStructure _ddmStucture;
	private String _displayStyle;
	private final JSONFactory _jsonFactory;
	private DDLRecordSet _recordSet;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final StorageEngine _storageEngine;
	private final WorkflowEngineManager _workflowEngineManager;

}
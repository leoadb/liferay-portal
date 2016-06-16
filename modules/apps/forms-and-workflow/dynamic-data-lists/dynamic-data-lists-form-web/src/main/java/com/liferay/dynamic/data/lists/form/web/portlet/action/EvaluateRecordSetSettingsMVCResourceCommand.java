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

package com.liferay.dynamic.data.lists.form.web.portlet.action;

import com.liferay.dynamic.data.lists.form.web.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.lists.model.DDLRecordSetSettings;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageAdapter;
import com.liferay.dynamic.data.mapping.storage.StorageAdapterRegistry;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutFactory;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowEngineManager;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
		"mvc.command.name=evaluateRecordSetSettings"
	},
	service = MVCResourceCommand.class
)
public class EvaluateRecordSetSettingsMVCResourceCommand
	extends BaseMVCResourceCommand {

	protected void addWorkflowDefinitionDDMFormFieldOptionLabels(
			DDMFormFieldOptions ddmFormFieldOptions, ThemeDisplay themeDisplay)
		throws PortalException {

		if (!_workflowEngineManager.isDeployed()) {
			return;
		}

		List<WorkflowDefinition> workflowDefinitions =
			_workflowDefinitionManager.getActiveWorkflowDefinitions(
				themeDisplay.getCompanyId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
			String value =
				workflowDefinition.getName() + StringPool.AT +
					workflowDefinition.getVersion();

			String version = LanguageUtil.format(
				themeDisplay.getLocale(), "version-x",
				workflowDefinition.getVersion(), false);

			String label = workflowDefinition.getName() + " (" + version + ")";

			ddmFormFieldOptions.addOptionLabel(
				value, themeDisplay.getLocale(), label);
		}
	}

	protected DDMFormRenderingContext createDDMFormRenderingContext(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setHttpServletRequest(
			PortalUtil.getHttpServletRequest(resourceRequest));
		ddmFormRenderingContext.setHttpServletResponse(
			PortalUtil.getHttpServletResponse(resourceResponse));
		ddmFormRenderingContext.setLocale(resourceRequest.getLocale());
		ddmFormRenderingContext.setPortletNamespace(
			resourceResponse.getNamespace());

		return ddmFormRenderingContext;
	}

	protected DDMFormFieldOptions createStorageTypeDDMFormFieldOptions(
			ThemeDisplay themeDisplay)
		throws PortalException {

		Locale locale = themeDisplay.getLocale();

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.setDefaultLocale(locale);

		StorageAdapter storageAdapter =
			_storageAdapterRegistry.getDefaultStorageAdapter();

		String storageTypeDefault = storageAdapter.getStorageType();

		ddmFormFieldOptions.addOptionLabel(
			storageTypeDefault, locale, storageTypeDefault);

		Set<String> storageTypes = _storageAdapterRegistry.getStorageTypes();

		for (String storageType : storageTypes) {
			if (storageType.equals(storageTypeDefault)) {
				continue;
			}

			ddmFormFieldOptions.addOptionLabel(
				storageType, locale, storageType);
		}

		return ddmFormFieldOptions;
	}

	protected DDMFormFieldOptions createWorkflowDefinitionDDMFormFieldOptions(
			ThemeDisplay themeDisplay)
		throws PortalException {

		Locale locale = themeDisplay.getLocale();

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.setDefaultLocale(locale);

		ddmFormFieldOptions.addOptionLabel(
			StringPool.BLANK, locale, LanguageUtil.get(locale, "no-workflow"));

		addWorkflowDefinitionDDMFormFieldOptionLabels(
			ddmFormFieldOptions, themeDisplay);

		return ddmFormFieldOptions;
	}

	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long recordSetId = ParamUtil.getLong(resourceRequest, "recordSetId");

		DDMForm ddmForm = DDMFormFactory.create(DDLRecordSetSettings.class);
		DDMFormLayout ddmFormLayout = DDMFormLayoutFactory.create(
			DDLRecordSetSettings.class);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		// Workflow definition

		DDMFormField ddmFormField = ddmFormFieldsMap.get("workflowDefinition");

		DDMFormFieldOptions ddmFormFieldOptions =
			createWorkflowDefinitionDDMFormFieldOptions(themeDisplay);

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		// Storage type

		ddmFormField = ddmFormFieldsMap.get("storageType");

		if (recordSetId > 0) {
			ddmFormField.setReadOnly(true);
		}

		ddmFormFieldOptions = createStorageTypeDDMFormFieldOptions(
			themeDisplay);

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		String serializedDDMFormValues = ParamUtil.getString(
			resourceRequest, "serializedDDMFormValues");

		DDMFormValues ddmFormValues =
			_ddmFormValuesJSONDeserializer.deserialize(
				ddmForm, serializedDDMFormValues);

		DDMFormRenderingContext ddmFormRenderingContext =
			createDDMFormRenderingContext(resourceRequest, resourceResponse);

		ddmFormRenderingContext.setDDMFormValues(ddmFormValues);

		Map<String, Object> templateContext =
			_ddmFormTemplateContextFactory.create(
				ddmForm, ddmFormLayout, ddmFormRenderingContext);

		Object pages = templateContext.get("pages");

		resourceResponse.setContentType(ContentTypes.APPLICATION_JSON);

		JSONSerializer jsonSerializer = _jsonFactory.createJSONSerializer();

		PortletResponseUtil.write(
			resourceResponse, jsonSerializer.serializeDeep(pages));

		resourceResponse.flushBuffer();
	}

	@Reference(unbind = "-")
	protected void setStorageAdapterRegistry(
		StorageAdapterRegistry storageAdapterRegistry) {

		_storageAdapterRegistry = storageAdapterRegistry;
	}

	@Reference(unbind = "-")
	protected void setWorkflowDefinitionManager(
		WorkflowDefinitionManager workflowDefinitionManager) {

		_workflowDefinitionManager = workflowDefinitionManager;
	}

	@Reference(unbind = "-")
	protected void setWorkflowEngineManager(
		WorkflowEngineManager workflowEngineManager) {

		_workflowEngineManager = workflowEngineManager;
	}

	@Reference
	private DDMFormTemplateContextFactory _ddmFormTemplateContextFactory;

	@Reference
	private DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;

	@Reference
	private JSONFactory _jsonFactory;

	private StorageAdapterRegistry _storageAdapterRegistry;
	private WorkflowDefinitionManager _workflowDefinitionManager;
	private WorkflowEngineManager _workflowEngineManager;

}
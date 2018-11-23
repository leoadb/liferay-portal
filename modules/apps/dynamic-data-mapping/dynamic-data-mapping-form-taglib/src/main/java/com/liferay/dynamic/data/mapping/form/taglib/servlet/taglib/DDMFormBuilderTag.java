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

package com.liferay.dynamic.data.mapping.form.taglib.servlet.taglib;

import com.liferay.data.engine.model.DataDefinitionColumn;
import com.liferay.dynamic.data.mapping.form.builder.settings.DDMFormBuilderSettingsRequest;
import com.liferay.dynamic.data.mapping.form.builder.settings.DDMFormBuilderSettingsResponse;
import com.liferay.dynamic.data.mapping.form.taglib.servlet.taglib.base.BaseDDMFormBuilderTag;
import com.liferay.dynamic.data.mapping.form.taglib.servlet.taglib.util.DDMFormTaglibUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rafael Praxedes
 */
public class DDMFormBuilderTag extends BaseDDMFormBuilderTag {

	public String getDDMFormBuilderContext(HttpServletRequest request) {
		return DDMFormTaglibUtil.getFormBuilderContext(
			GetterUtil.getLong(getDdmStructureId()),
			GetterUtil.getLong(getDdmStructureVersionId()), request);
	}

	protected DDMForm getDDMForm() {
		return DDMFormTaglibUtil.getDDMForm(
			GetterUtil.getLong(getDdmStructureId()),
			GetterUtil.getLong(getDdmStructureVersionId()));
	}

	protected DDMFormBuilderSettingsResponse getDDMFormBuilderSettings(
		HttpServletRequest request) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return DDMFormTaglibUtil.getDDMFormBuilderSettings(
			DDMFormBuilderSettingsRequest.with(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
				getFieldSetClassNameId(), getDDMForm(),
				themeDisplay.getLocale()));
	}

	@Override
	protected String getEndPage() {
		if (getUseExperimentalInterface()) {
			return "/ddm_form_builder/experimental/end.jsp";
		}

		return super.getEndPage();
	}

	@Override
	protected String getStartPage() {
		if (getUseExperimentalInterface()) {
			return "/ddm_form_builder/experimental/start.jsp";
		}

		return super.getStartPage();
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		super.setAttributes(request);

		DDMFormBuilderSettingsResponse ddmFormBuilderSettingsResponse =
			getDDMFormBuilderSettings(request);

		List<DataDefinitionColumn> dataDefinitionColumns =
			(List<DataDefinitionColumn>)getDataDefinitionColumns();

		setNamespacedAttribute(
			request, "dataDefinitionColumnsJSON",
			DDMFormTaglibUtil.getDataDefinitionColumnsJSON(
				dataDefinitionColumns));

		setNamespacedAttribute(
			request, "dataProviderInstancesURL",
			ddmFormBuilderSettingsResponse.getDataProviderInstancesURL());
		setNamespacedAttribute(
			request, "dataProviderInstanceParameterSettingsURL",
			ddmFormBuilderSettingsResponse.
				getDataProviderInstanceParameterSettingsURL());
		setNamespacedAttribute(
			request, "evaluatorURL",
			ddmFormBuilderSettingsResponse.getFormContextProviderURL());
		setNamespacedAttribute(
			request, "fieldSets",
			ddmFormBuilderSettingsResponse.getFieldSets());
		setNamespacedAttribute(
			request, "fieldSetDefinitionURL",
			ddmFormBuilderSettingsResponse.getFieldSetDefinitionURL());
		setNamespacedAttribute(
			request, "fieldSettingsDDMFormContextURL",
			ddmFormBuilderSettingsResponse.getFieldSettingsDDMFormContextURL());

		setNamespacedAttribute(
			request, "fieldTypes",
			DDMFormTaglibUtil.getDDMFormFieldTypesJSONArray(request));
		setNamespacedAttribute(
			request, "formBuilderContext", getDDMFormBuilderContext(request));
		setNamespacedAttribute(
			request, "functionsMetadata",
			ddmFormBuilderSettingsResponse.getFunctionsMetadata());
		setNamespacedAttribute(
			request, "functionsURL",
			ddmFormBuilderSettingsResponse.getFunctionsURL());
		setNamespacedAttribute(
			request, "javaScriptPackage",
			DDMFormTaglibUtil.getJavaScriptPackage());
		setNamespacedAttribute(
			request, "rolesURL", ddmFormBuilderSettingsResponse.getRolesURL());
		setNamespacedAttribute(
			request, "serializedDDMFormRules",
			ddmFormBuilderSettingsResponse.getSerializedDDMFormRules());
	}

}
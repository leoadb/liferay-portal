<%--
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
--%>

<%@ include file="/admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

DDMStructure structure = ddlFormAdminDisplayContext.getDDMStructure();

long groupId = BeanParamUtil.getLong(structure, request, "groupId", scopeGroupId);
long structureId = ParamUtil.getLong(request, "structureId");

if (structure != null) {
	structureId = structure.getStructureId();
}

String structureKey = BeanParamUtil.getString(structure, request, "structureKey");

String defaultLanguageId = LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault());

Locale[] availableLocales = ddlFormAdminDisplayContext.getAvailableLocales();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((structure == null) ? LanguageUtil.get(request, "new-field-set") : LanguageUtil.get(request, "edit-field-set"));
%>

<div class="loading-animation" id="<portlet:namespace />loader"></div>

<portlet:actionURL name="saveStructure" var="saveStructureURL">
	<portlet:param name="mvcPath" value="/admin/edit_field_set.jsp" />
</portlet:actionURL>

<div class="hide portlet-forms" id="<portlet:namespace />formContainer">
	<div class="container-fluid-1280">
		<aui:translation-manager
			availableLocales="<%= availableLocales %>"
			changeableDefaultLanguage="<%= false %>"
			defaultLanguageId="<%= defaultLanguageId %>"
			id="translationManager"
		/>
	</div>

	<aui:form action="<%= saveStructureURL %>" cssClass="ddl-form-builder-form" method="post" name="editForm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
		<aui:input name="structureId" type="hidden" value="<%= structureId %>" />
		<aui:input name="structureKey" type="hidden" value="<%= structureKey %>" />

		<%@ include file="/admin/exceptions.jspf" %>

		<aui:fieldset cssClass="ddl-form-basic-info">
			<div class="container-fluid-1280">
				<h1>
					<liferay-ui:input-editor
						autoCreate="<%= false %>"
						contents="<%= HtmlUtil.escape(ddlFormAdminDisplayContext.getFormName()) %>"
						cssClass="ddl-form-name"
						editorName="alloyeditor"
						name="nameEditor"
						placeholder="untitled-field-set"
						showSource="<%= false %>"
					/>
				</h1>

				<aui:input name="name" type="hidden" />

				<h5>
					<liferay-ui:input-editor
						autoCreate="<%= false %>"
						contents="<%= HtmlUtil.escape(ddlFormAdminDisplayContext.getFormDescription()) %>"
						cssClass="ddl-form-description h5"
						editorName="alloyeditor"
						name="descriptionEditor"
						placeholder="add-a-short-description-for-this-field-set"
						showSource="<%= false %>"
					/>
				</h5>

				<aui:input name="description" type="hidden" />
			</div>
		</aui:fieldset>

		<aui:fieldset cssClass="container-fluid-1280 ddl-form-builder-app">
			<aui:input name="serializedFormBuilderContext" type="hidden" />

			<div id="<portlet:namespace />formBuilder"></div>
		</aui:fieldset>

		<div class="container-fluid-1280">
			<aui:button-row cssClass="ddl-form-builder-buttons">
				<aui:button cssClass="btn-lg" id="save" type="submit" value="save" />
				<aui:button cssClass="btn-lg" href="<%= redirect %>" name="cancelButton" type="cancel" />
			</aui:button-row>
		</div>

		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="getDataProviderInstances" var="getDataProviderInstancesURL" />

		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="getDataProviderParametersSettings" var="getDataProviderParametersSettings" />

		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="getFieldSettingsDDMFormContext" var="getFieldSettingsDDMFormContext" />

		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="getFunctions" var="getFunctions" />

		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="getRoles" var="getRoles" />

		<aui:script>
			Liferay.namespace('DDL').Settings = {
				evaluatorURL: '<%= ddlFormAdminDisplayContext.getDDMFormContextProviderServletURL() %>',
				functionsMetadata: <%= ddlFormAdminDisplayContext.getSerializedDDMExpressionFunctionsMetadata() %>,
				getDataProviderInstancesURL: '<%= getDataProviderInstancesURL.toString() %>',
				getDataProviderParametersSettingsURL: '<%= getDataProviderParametersSettings.toString() %>',
				getFieldTypeSettingFormContextURL: '<%= getFieldSettingsDDMFormContext.toString() %>',
				getFunctionsURL: '<%= getFunctions.toString() %>',
				getRolesURL: '<%= getRoles.toString() %>',
				portletNamespace: '<portlet:namespace />',
				showPagination: false
			};

			var initHandler = Liferay.after(
				'form:registered',
				function(event) {
					if (event.formName === '<portlet:namespace />editForm') {
						initHandler.detach();

						var fieldTypes = <%= ddlFormAdminDisplayContext.getDDMFormFieldTypesJSONArray() %>;

						var systemFieldModules = fieldTypes.filter(
							function(item) {
								return item.system;
							}
						).map(
							function(item) {
								return item.javaScriptModule;
							}
						);

						Liferay.provide(
							window,
							'<portlet:namespace />init',
							function() {
								Liferay.DDM.Renderer.FieldTypes.register(fieldTypes);

								if (window.ddm) {
									<portlet:namespace />registerFormPortlet(event.form);
								}
								else {
									Liferay.after(
										'DDMFormLoaded',
										function() {
											<portlet:namespace />registerFormPortlet(event.form);
										}
									);
								}
							},
							['liferay-ddl-portlet'].concat(systemFieldModules)
						);

						<portlet:namespace />init();
					}
				}
			);

			function <portlet:namespace />registerFormPortlet(form) {
				Liferay.component(
					'formPortlet',
					new Liferay.DDL.Portlet(
						{
							context: <%= ddlFormAdminDisplayContext.getFormBuilderContext() %>,
							localizedDescription: <%= ddlFormAdminDisplayContext.getFormLocalizedDescription() %>,
							localizedName: <%= ddlFormAdminDisplayContext.getFormLocalizedName() %>,
							defaultLanguageId: '<%= ddlFormAdminDisplayContext.getDefaultLanguageId() %>',
							editingLanguageId: '<%= ddlFormAdminDisplayContext.getDefaultLanguageId() %>',
							editForm: form,
							namespace: '<portlet:namespace />',
							translationManager: Liferay.component('<portlet:namespace />translationManager'),
							view: 'fieldSets'
						}
					)
				);
			}

			var clearPortletHandlers = function(event) {
				if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
					Liferay.namespace('DDL').destroySettings();

					Liferay.detach('destroyPortlet', clearPortletHandlers);
				}
			};

			Liferay.on('destroyPortlet', clearPortletHandlers);
		</aui:script>
	</aui:form>
</div>
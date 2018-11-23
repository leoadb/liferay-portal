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

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

ReportDefinition reportDefinition = displayContext.getReportDefinition();

long reportDefinitionId = BeanParamUtil.getLong(reportDefinition, request, "reportDefinitionId");
%>

<portlet:actionURL name="abc" var="saveReportDefinitionURL">
	<portlet:param name="mvcRenderCommandName" value="/view_report_definitions" />
</portlet:actionURL>

<aui:form action="<%= saveReportDefinitionURL %>" method="post" name="fm">
	<aui:input name="definition" type="hidden" value="<%= displayContext.getReportDefinitionData() %>" />
	<aui:input name="reportDefinitionId" type="hidden" value="<%= reportDefinitionId %>" />

	<div class="container-fluid-1280 report-definitions-form">
		<div class="lfr-form-content">
			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset collapsible="<%= true %>" label="name-and-description">
					<aui:input label="name" name="name" type="text" value="<%= HtmlUtil.escape(displayContext.getReportDefinitionName()) %>" />

					<aui:input label="description" name="description" type="textarea" value="<%= HtmlUtil.escape(displayContext.getReportDefinitionDescription()) %>" />
				</aui:fieldset>

				<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="database-columns">
					<%= displayContext.getColumnsFormHTML() %>
				</aui:fieldset>

				<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="parameters">
					<aui:button id="formBuilderButton" value='<%= (reportDefinitionId > 0) ? "edit-parameters" : "add-parameters" %>' />
				</aui:fieldset>

				<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="permissions">
					<liferay-ui:input-permissions
						modelName="<%= ReportDefinition.class.getName() %>"
					/>
				</aui:fieldset>
			</aui:fieldset-group>
		</div>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</div>
</aui:form>

<div class="hide" id="<portlet:namespace />formBuilderApp">
	<liferay-form:ddm-form-builder
		dataDefinitionColumns="<%= displayContext.getDataDefinitionColumns() %>"
		dataDefinitionInputId='<%= liferayPortletResponse.getNamespace() + "definition" %>'
		ddmStructureVersionId="<%= 0L %>"
		defaultLanguageId="<%= themeDisplay.getLanguageId() %>"
		editingLanguageId="<%= themeDisplay.getLanguageId() %>"
		fieldSetClassNameId="<%= PortalUtil.getClassNameId(DDMFormInstance.class) %>"
		refererPortletNamespace="<%= liferayPortletResponse.getNamespace() %>"
		useExperimentalInterface="<%= true %>"
	/>
</div>

<aui:script require='<%= bootstrapRequire + "/js/ReportDefinitions.es as ReportDefinitions" %>'>
const reportDefinitions = new ReportDefinitions.default(
	{
		formBuilderAppNode: '#<portlet:namespace />formBuilderApp',
		formBuilderButtonNode: '#<portlet:namespace />formBuilderButton',
		spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
	}
);

var destroyInstance = function(event) {
	if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
		reportDefinitions.dispose();

		Liferay.detach('destroyPortlet', destroyInstance);
	}
};

Liferay.on('destroyPortlet', destroyInstance);
</aui:script>
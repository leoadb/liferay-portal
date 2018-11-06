<%@ include file="/init.jsp" %>

<div class="hide" id="<portlet:namespace />formBuilderApp">
	<liferay-form:ddm-form-builder
		ddmStructureVersionId="<%= 0L %>"
		defaultLanguageId="<%= themeDisplay.getLanguageId() %>"
		editingLanguageId="<%= themeDisplay.getLanguageId() %>"
		fieldSetClassNameId="<%= PortalUtil.getClassNameId(DDMFormInstance.class) %>"
		refererPortletNamespace="<%= liferayPortletResponse.getNamespace() %>"
		useExperimentalInterface="<%= true %>"
	/>
</div>

<aui:button id="formBuilderButton" value="Add Fields" />

<aui:script require='<%= bootstrapRequire + "/js/main.es as ReportDefinitions" %>'>
new ReportDefinitions.default(
	{
		formBuilderAppNode: '#<portlet:namespace />formBuilderApp',
		formBuilderButtonNode: '#<portlet:namespace />formBuilderButton'
	}
);
</aui:script>
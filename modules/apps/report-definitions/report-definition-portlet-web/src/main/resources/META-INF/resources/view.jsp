<%@ include file="/init.jsp" %>

<liferay-form:ddm-form-builder
	ddmStructureVersionId="<%= 0L %>"
	defaultLanguageId="<%= themeDisplay.getLanguageId() %>"
	editingLanguageId="<%= themeDisplay.getLanguageId() %>"
	fieldSetClassNameId="<%= PortalUtil.getClassNameId(DDMFormInstance.class) %>"
	refererPortletNamespace="<%= liferayPortletResponse.getNamespace() %>"
	useExperimentalInterface="<%= true %>"
/>

<aui:button id="openFormBuilder" value="Add Fields" />

<div id="renderForm"></div>

<aui:script require='<%= bootstrapRequire + "/js/main.es as reportDefinitions" %>'>
console.log(reportDefinitions);
</aui:script>
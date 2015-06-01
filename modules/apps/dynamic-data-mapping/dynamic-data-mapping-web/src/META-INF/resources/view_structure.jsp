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

long structureId = ParamUtil.getLong(request, "structureId");
		 
long structureVersionId = ParamUtil.getLong(request, "structureVersionId");
		 
DDMStructure structure = DDMStructureServiceUtil.getStructure(structureId);

long classPK = BeanParamUtil.getLong(structure, request, "structureId");

DDMStructureVersion structureVersion = DDMStructureVersionServiceUtil.getStructureVersion(structureVersionId);

long parentStructureId = BeanParamUtil.getLong(structure, request, "parentStructureId", DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID);

String portletResourceNamespace = ParamUtil.getString(request, "portletResourceNamespace", renderResponse.getNamespace());

String parentStructureName = StringPool.BLANK;

try {
	DDMStructure parentStructure = DDMStructureServiceUtil.getStructure(parentStructureId);

	parentStructureName = parentStructure.getName(locale);
}
catch (NoSuchStructureException nsee) {
}

String script = BeanParamUtil.getString(structureVersion, request, "definition");

JSONArray fieldsJSONArray = DDMUtil.getDDMFormFieldsJSONArray(structureVersion, script);

String fieldsJSONArrayString = StringPool.BLANK;

if (fieldsJSONArray != null) {
	fieldsJSONArrayString = fieldsJSONArray.toString();
}
%>


<liferay-ui:header
	backURL="<%= redirect %>"
	localizeTitle="<%= false %>"
	showBackURL="<%= true %>"
	title="<%= structure.getName(locale) %>"
/>

<aui:input disabled="<%= true %>" name="name" value="<%= structure.getName(locale) %>" />

<aui:input disabled="<%= true %>" name="description" value="<%= structure.getDescription(locale) %>"  />

<aui:input disabled="<%= true %>" label="<%= LanguageUtil.format(request, "parent-x", ddmDisplay.getStructureName(locale), false) %>" name="parentStructureName" type="text" value="<%= parentStructureName %>" />

<portlet:actionURL name="ddmGetStructure" var="getStructureURL">
	<portlet:param name="structureId" value="<%= String.valueOf(classPK) %>" />
</portlet:actionURL>

<aui:input name="url" type="resource" value="<%= getStructureURL.toString() %>" />

<%@ include file="/form_builder.jspf" %>

<aui:button-row>
	<aui:button href="<%= redirect %>" type="cancel" />
</aui:button-row>


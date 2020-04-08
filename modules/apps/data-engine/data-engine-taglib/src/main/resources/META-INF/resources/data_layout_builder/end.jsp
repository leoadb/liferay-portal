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

<%@ include file="/data_layout_builder/init.jsp" %>

<portlet:renderURL var="basePortletURL" />

<%
Map<String, Object> data = HashMapBuilder.<String, Object>put(
	"availableLanguageIds", availableLanguageIds
).put(
	"config", configJSONObject
).put(
	"contentType", contentType
).put(
	"context", dataLayoutJSONObject
).put(
	"dataDefinitionId", dataDefinitionId
).put(
	"dataLayoutBuilderElementId", renderResponse.getNamespace() + "-data-layout-builder"
).put(
	"dataLayoutBuilderId", componentId
).put(
	"dataLayoutId", dataLayoutId
).put(
	"fieldTypes", fieldTypesJSONArray
).put(
	"fieldTypesModules", fieldTypesModules
).put(
	"groupId", groupId
).put(
	"localizable", localizable
).put(
	"sidebarPanels", sidebarPanels
).put(
	"spritemap", themeDisplay.getPathThemeImages() + "/lexicon/icons.svg"
).build();
%>

<liferay-ui:error exception="<%= DEDataDefinitionCharactersForFieldNameException.class %>">

	<%
	DEDataDefinitionCharactersForFieldNameException deddcffne = (DEDataDefinitionCharactersForFieldNameException)errorException;
	%>

	<liferay-ui:message arguments="<%= HtmlUtil.escape(deddcffne.getFieldName()) %>" key="invalid-characters-were-defined-for-field-name-x" translateArguments="<%= false %>" />
</liferay-ui:error>

<liferay-ui:error exception="<%= DEDataDefinitionException.class %>" message="please-enter-a-valid-form-definition" />

<liferay-ui:error exception="<%= DEDataDefinitionNameException.class %>" message="please-enter-a-valid-name" />

<liferay-ui:error exception="<%= DEDataDefinitionOptionsForFieldException.class %>">

	<%
	DEDataDefinitionOptionsForFieldException deddoffe = (DEDataDefinitionOptionsForFieldException)errorException;
	%>

	<liferay-ui:message arguments="<%= HtmlUtil.escape(deddoffe.getFieldName()) %>" key="at-least-one-option-should-be-set-for-field-x" translateArguments="<%= false %>" />
</liferay-ui:error>

<liferay-ui:error exception="<%= DEDataLayoutException.class %>" message="please-enter-a-valid-form-layout" />

<liferay-ui:error exception="<%= DuplicateDEDataDefinitionFieldNameException.class %>">

	<%
	DuplicateDEDataDefinitionFieldNameException dedddfne = (DuplicateDEDataDefinitionFieldNameException)errorException;
	%>

	<liferay-ui:message arguments="<%= HtmlUtil.escape(StringUtil.merge(dedddfne.getDuplicatedFieldNames(), StringPool.COMMA_AND_SPACE)) %>" key="the-definition-field-name-x-was-defined-more-than-once" translateArguments="<%= false %>" />
</liferay-ui:error>

<liferay-ui:error exception="<%= DuplicateDEDataLayoutFieldNameException.class %>">

	<%
	DuplicateDEDataLayoutFieldNameException deddlfne = (DuplicateDEDataLayoutFieldNameException)errorException;
	%>

	<liferay-ui:message arguments="<%= HtmlUtil.escape(StringUtil.merge(deddlfne.getDuplicatedFieldNames(), StringPool.COMMA_AND_SPACE)) %>" key="the-definition-field-name-x-was-defined-more-than-once" translateArguments="<%= false %>" />
</liferay-ui:error>

<div id="<%= componentId + "container" %>">
	<react:component
		data="<%= data %>"
		module="data_layout_builder/js/App.es"
	/>
</div>
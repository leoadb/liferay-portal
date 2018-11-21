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

<%@ include file="/ddm_form_builder/init.jsp" %>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, "/o/dynamic-data-mapping-form-builder/metal/css/main.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<div id="<%= refererPortletNamespace %>formBuilder"></div>

<aui:script require='<%= javaScriptPackage + "/ddm_form_builder/experimental/js/main.es as FormBuilderTagLib" %>'>
FormBuilderTagLib.default(
	{
		context: {
			pages: [],
			successPageSettings: {}
		},
		dataDefinitionColumns: <%= dataDefinitionColumnsJSON %>,
		dataDefinitionInputId: '<%= HtmlUtil.escapeJS(dataDefinitionInputId) %>',
		dependencies: ['dynamic-data-mapping-form-field-type/metal'],
		fieldTypes: <%= fieldTypes %>,
		modules: Liferay.MODULES,
		namespace: '<%= HtmlUtil.escapeJS(refererPortletNamespace) %>',
		spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
	},
	'#<%= HtmlUtil.escapeJS(refererPortletNamespace) %>formBuilder',
	function() {
	}
);
</aui:script>
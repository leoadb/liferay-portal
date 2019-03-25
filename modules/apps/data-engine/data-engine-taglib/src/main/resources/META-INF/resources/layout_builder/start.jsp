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

<%@ include file="/layout_builder/init.jsp" %>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, "/o/dynamic-data-mapping-form-builder/metal/css/main.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<div id="<%= renderResponse.getNamespace() %>layoutBuilder"></div>

<aui:script>
	var modules = ['<%= javaScriptPackage + "/layout_builder/js/main.es" %>'];

	modules.concat(
		(<%= fieldTypes %>).map(
			function(fieldType) {
				return fieldType.javaScriptModule;
			}
		)
	);

	var loadTagLib = function(LayoutBuilderTagLib) {
		LayoutBuilderTagLib.default(
			{
				context: {
					pages: [],
				},
				dataLayoutInputId: '<%= HtmlUtil.escapeJS(dataLayoutInputId) %>',
				defaultLanguageId: '<%= defaultLanguageId %>',
				dependencies: ['dynamic-data-mapping-form-field-type/metal'],
				editingLanguageId: '<%= editingLanguageId %>',
				fieldTypes: <%= fieldTypes %>,
				namespace: '<%= HtmlUtil.escapeJS(renderResponse.getNamespace()) %>',
				spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
			},
			'#<%= HtmlUtil.escapeJS(renderResponse.getNamespace()) %>layoutBuilder',
			function() {
			}
		);
	};

	Liferay.Loader.require.apply(Liferay.Loader, modules.concat(loadTagLib));

</aui:script>
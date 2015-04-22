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
String tabs2 = ParamUtil.getString(request, "tabs2", "pending");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("tabs1", "submissions");
portletURL.setParameter("tabs2", tabs2);
%>

<liferay-ui:tabs
	names="pending,completed"
	param="tabs2"
	portletURL="<%= portletURL %>"
/>

<%
try {
	boolean completedInstances = true;

	if (tabs2.equals("pending")) {
		completedInstances = false;
	}
%>

	<%@ include file="/workflow_instances.jspf" %>

<%
}
catch (Exception e) {
	if (_log.isWarnEnabled()) {
		_log.warn("Error retrieving workflow instances", e);
	}
%>

	<div class="alert alert-danger">
		<liferay-ui:message key="an-error-occurred-while-retrieving-the-list-of-instances" />
	</div>

<%
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, tabs2), currentURL);
%>

<%!
private static Log _log = LogFactoryUtil.getLog("modules.apps.workflow.workflow-instance-web.view_jsp");
%>
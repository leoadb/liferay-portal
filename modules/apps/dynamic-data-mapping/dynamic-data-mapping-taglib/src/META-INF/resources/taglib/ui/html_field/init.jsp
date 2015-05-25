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

@generated
--%>

<%@ include file="/taglib/common/taglib-init.jsp" %>

<%
long classNameId = GetterUtil.getLong(String.valueOf(request.getAttribute("ui:html-field:classNameId")));
long classPK = GetterUtil.getLong(String.valueOf(request.getAttribute("ui:html-field:classPK")));
com.liferay.portlet.dynamicdatamapping.storage.Field field = (com.liferay.portlet.dynamicdatamapping.storage.Field)request.getAttribute("ui:html-field:field");
java.lang.String fieldsNamespace = GetterUtil.getString((java.lang.String)request.getAttribute("ui:html-field:fieldsNamespace"));
boolean readOnly = GetterUtil.getBoolean(String.valueOf(request.getAttribute("ui:html-field:readOnly")));
boolean repeatable = GetterUtil.getBoolean(String.valueOf(request.getAttribute("ui:html-field:repeatable")), true);
java.util.Locale requestedLocale = (java.util.Locale)request.getAttribute("ui:html-field:requestedLocale");
boolean showEmptyFieldLabel = GetterUtil.getBoolean(String.valueOf(request.getAttribute("ui:html-field:showEmptyFieldLabel")), true);
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("ui:html-field:dynamicAttributes");
Map<String, Object> scopedAttributes = (Map<String, Object>)request.getAttribute("ui:html-field:scopedAttributes");
%>

<%@ include file="/taglib/ui/html_field/init-ext.jspf" %>
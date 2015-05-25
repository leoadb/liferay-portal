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
boolean checkRequired = GetterUtil.getBoolean(String.valueOf(request.getAttribute("ui:html:checkRequired")), true);
long classNameId = GetterUtil.getLong(String.valueOf(request.getAttribute("ui:html:classNameId")));
long classPK = GetterUtil.getLong(String.valueOf(request.getAttribute("ui:html:classPK")));
com.liferay.portlet.dynamicdatamapping.storage.Fields fields = (com.liferay.portlet.dynamicdatamapping.storage.Fields)request.getAttribute("ui:html:fields");
java.lang.String fieldsNamespace = GetterUtil.getString((java.lang.String)request.getAttribute("ui:html:fieldsNamespace"));
boolean readOnly = GetterUtil.getBoolean(String.valueOf(request.getAttribute("ui:html:readOnly")));
boolean repeatable = GetterUtil.getBoolean(String.valueOf(request.getAttribute("ui:html:repeatable")), true);
java.util.Locale requestedLocale = (java.util.Locale)request.getAttribute("ui:html:requestedLocale");
boolean showEmptyFieldLabel = GetterUtil.getBoolean(String.valueOf(request.getAttribute("ui:html:showEmptyFieldLabel")), true);
java.lang.Object servletContext = (java.lang.Object)request.getAttribute("ui:html:servletContext");
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("ui:html:dynamicAttributes");
Map<String, Object> scopedAttributes = (Map<String, Object>)request.getAttribute("ui:html:scopedAttributes");
%>

<%@ include file="/taglib/ui/html/init-ext.jspf" %>
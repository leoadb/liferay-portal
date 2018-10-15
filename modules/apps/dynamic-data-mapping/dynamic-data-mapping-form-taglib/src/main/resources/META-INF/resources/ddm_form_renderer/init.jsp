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

<%@ include file="/init.jsp" %>

<%
java.lang.Long formInstanceId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-form:ddm-form-renderer:formInstanceId")));
java.lang.Long formInstanceRecordId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-form:ddm-form-renderer:formInstanceRecordId")));
java.lang.Long formInstanceRecordVersionId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-form:ddm-form-renderer:formInstanceRecordVersionId")));
java.lang.Long formInstanceVersionId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-form:ddm-form-renderer:formInstanceVersionId")));
java.lang.String namespace = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-form:ddm-form-renderer:namespace"));
boolean showFormBasicInfo = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-form:ddm-form-renderer:showFormBasicInfo")));
boolean showSubmitButton = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-form:ddm-form-renderer:showSubmitButton")));
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("liferay-form:ddm-form-renderer:dynamicAttributes");
%>

<%@ include file="/ddm_form_renderer/init-ext.jspf" %>
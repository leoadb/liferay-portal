<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/form" prefix="liferay-form" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.dynamic.data.mapping.model.DDMFormInstance" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.report.definitions.portlet.web.constants.ReportDefinitionNPMKeys" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String bootstrapRequire = (String)request.getAttribute(ReportDefinitionNPMKeys.BOOTSTRAP_REQUIRE);
%>
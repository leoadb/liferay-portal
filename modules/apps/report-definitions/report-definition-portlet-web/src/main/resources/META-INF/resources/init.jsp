<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/form" prefix="liferay-form" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.dynamic.data.mapping.model.DDMFormInstance" %><%@
page import="report.definition.portlet.web.constants.ReportDefinitionNPMKeys" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String bootstrapRequire = (String)request.getAttribute(ReportDefinitionNPMKeys.BOOTSTRAP_REQUIRE);
%>

<style type="text/css">
.form-builder-sidebar {
	margin-top: 0 !important;
}
</style>
<%@ include file="/init.jsp" %>

<liferay-form:ddm-form-builder
	ddmStructureVersionId="<%= 0L %>"
	defaultLanguageId="<%= themeDisplay.getLanguageId() %>"
	editingLanguageId="<%= themeDisplay.getLanguageId() %>"
	fieldSetClassNameId="<%= PortalUtil.getClassNameId(DDMFormInstance.class) %>"
	refererPortletNamespace="<%= liferayPortletResponse.getNamespace() %>"
/>

<div class="portlet-forms-admin" id="<%= liferayPortletResponse.getNamespace() %>dialogContainer">
	<div class="portlet-forms" id="<%= liferayPortletResponse.getNamespace() %>formContainer">
		<div class="container-fluid-1280 ddm-form-builder-app">
			<div id="<%= liferayPortletResponse.getNamespace() %>formBuilder"></div>
			<div id="<%= liferayPortletResponse.getNamespace() %>ruleBuilder"></div>
		</div>
	</div>
</div>

<aui:script use="liferay-ddm-form-builder-layout-serializer, liferay-util-window">
	Liferay.on(
		'formBuilderReady',
		function(event) {
			if (event.refererPortletNamespace == '<%= liferayPortletResponse.getNamespace() %>') {
				var formBuilder = Liferay.component('<%= liferayPortletResponse.getNamespace() %>formBuilder');

				formBuilder.render('#<%= liferayPortletResponse.getNamespace() %>formBuilder');

				window.getFormBuilderJSON = function() {
					var serializer = new Liferay.DDM.LayoutSerializer(
						{
							builder: formBuilder,
							defaultLanguageId: themeDisplay.getLanguageId(),
							pages: formBuilder.get('layouts')
						}
					);

					return serializer.getPages();
				}
			}
		}
	);
</aui:script>
Liferay.Loader.require(
	'frontend-js-metal-web/metal-soy-bundle/build/bundle',
	'dynamic-data-lists-form-web/admin/templates/sidebar.es',
	function(MetalSoyBundle, SideBar) {
		if (!window.DDLSidebar) {
			window.DDLSidebar = {};
		}

		window.DDLSidebar.render = SideBar.default;

		AUI.add('liferay-ddl-form-builder-sidebar-template');
	}
);
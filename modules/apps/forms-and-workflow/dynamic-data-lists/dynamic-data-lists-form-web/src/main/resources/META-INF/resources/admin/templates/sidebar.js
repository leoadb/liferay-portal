Liferay.Loader.require(
	'frontend-js-metal-web/metal-soy-bundle/build/bundle',
	'dynamic-data-lists-form-web/sidebar.es',
	function(MetalSoyBundle, SideBar) {
		if (!window.ddl) {
			window.ddl = {};
		}

		window.ddl['sidebar'] = SideBar.default;

		AUI.add(
			'liferay-ddl-sidebar-soy',
			function(A) {
				debugger;
			}
		);
	}
);
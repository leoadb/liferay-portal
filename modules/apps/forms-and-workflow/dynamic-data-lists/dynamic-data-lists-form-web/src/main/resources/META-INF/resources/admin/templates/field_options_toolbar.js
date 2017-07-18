Liferay.Loader.require(
	'frontend-js-metal-web/metal-soy-bundle/build/bundle',
	'dynamic-data-lists-form-web/field_options_toolbar.es',
	function(MetalSoyBundle, FieldOptionsToolbar) {
		if (!window.ddl) {
			window.ddl = {};
		}

		window.ddl['field_options_toolbar'] = FieldOptionsToolbar.default;

		AUI.add(
			'liferay-ddl-field-options-toolbar-soy',
			function(A) {
				debugger;
			}
		);
	}
);
Liferay.Loader.require(
	'frontend-js-metal-web/metal-soy-bundle/build/bundle',
	'dynamic-data-lists-form-web/admin/templates/field_options_toolbar.es',
	function(MetalSoyBundle, FieldOptionsToolbar) {
		if (!window.DDLFieldSettingsToolbar) {
			window.DDLFieldSettingsToolbar = {};
		}

		window.DDLFieldSettingsToolbar.render = FieldOptionsToolbar.default;

		AUI.add('liferay-ddl-form-builder-field-options-toolbar-template');
	}
);
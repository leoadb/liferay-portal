Liferay.Loader.require(
	'frontend-js-metal-web/metal-soy-bundle/build/bundle',
	'dynamic-data-lists-form-web/admin/templates/autocomplete.es',
	function(MetalSoyBundle, AutoComplete) {
		if (!window.DDLAutoComplete) {
			window.DDLAutoComplete = {};
		}

		window.DDLAutoComplete.actionPanel = AutoComplete.AutoCompleteActionPanel;
		window.DDLAutoComplete.container = AutoComplete.AutoCompleteContainer;

		AUI.add('liferay-ddl-form-builder-autocomplete-template');
	}
);
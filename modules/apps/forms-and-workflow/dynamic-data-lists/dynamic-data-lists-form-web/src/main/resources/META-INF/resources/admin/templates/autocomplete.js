Liferay.Loader.require(
	'frontend-js-metal-web/metal-soy-bundle/build/bundle',
	'dynamic-data-lists-form-web/autocomplete.es',
	function(MetalSoyBundle, AutoComplete) {
		if (!window.ddl) {
			window.ddl = {};
		}

		window.ddl['autocomplete'] = AutoComplete.default;

		AUI.add(
			'liferay-ddl-autocomplete-soy',
			function(A) {
				debugger;
			}
		);
	}
);
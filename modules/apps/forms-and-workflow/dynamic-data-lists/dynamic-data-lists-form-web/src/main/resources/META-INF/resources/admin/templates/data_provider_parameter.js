Liferay.Loader.require(
	'frontend-js-metal-web/metal-soy-bundle/build/bundle',
	'dynamic-data-lists-form-web/data_provider_parameter.es',
	function(MetalSoyBundle, DataProviderParameter) {
		if (!window.ddl) {
			window.ddl = {};
		}

		window.ddl['data_provider_parameter'] = DataProviderParameter.default;

		AUI.add(
			'liferay-ddl-data-provider-parameter-soy',
			function(A) {
				debugger;
			}
		);
	}
);
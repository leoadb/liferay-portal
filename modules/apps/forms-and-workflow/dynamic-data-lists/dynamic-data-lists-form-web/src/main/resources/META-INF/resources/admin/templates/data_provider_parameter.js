Liferay.Loader.require(
	'frontend-js-metal-web/metal-soy-bundle/build/bundle',
	'dynamic-data-lists-form-web/admin/templates/data_provider_parameter.es',
	function(MetalSoyBundle, DataProviderParameter) {
		if (!window.DDLDataProviderParameter) {
			window.DDLDataProviderParameter = {};
		}

		window.DDLDataProviderParameter.render = DataProviderParameter.default;

		AUI.add('liferay-ddl-form-builder-data-provider-parameter-template');
	}
);
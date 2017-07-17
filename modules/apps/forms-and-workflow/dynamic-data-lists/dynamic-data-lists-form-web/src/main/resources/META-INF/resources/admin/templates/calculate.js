Liferay.Loader.require(
	'frontend-js-metal-web/metal-soy-bundle/build/bundle',
	'dynamic-data-lists-form-web/admin/templates/calculate.es',
	function(MetalSoyBundle, Calculate) {
		if (!window.DDLCalculate) {
			window.DDLCalculate = {};
		}

		window.DDLCalculate.render = Calculate.default;

		AUI.add('liferay-ddl-form-builder-calculate-template');
	}
);
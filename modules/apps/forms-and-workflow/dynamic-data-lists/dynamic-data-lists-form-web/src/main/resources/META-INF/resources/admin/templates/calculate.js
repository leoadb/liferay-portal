Liferay.Loader.require(
	'frontend-js-metal-web/metal-soy-bundle/build/bundle',
	'dynamic-data-lists-form-web/calculate.es',
	function(MetalSoyBundle, Calculate) {
		if (!window.ddl) {
			window.ddl = {};
		}

		window.ddl['calculate'] = Calculate.default;

		AUI.add(
			'liferay-ddl-calculate-soy',
			function(A) {
				debugger;
			}
		);
	}
);
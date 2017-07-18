Liferay.Loader.require(
	'frontend-js-metal-web/metal-soy-bundle/build/bundle',
	'dynamic-data-lists-form-web/calculator.es',
	function(MetalSoyBundle, Calculator) {
		if (!window.ddl) {
			window.ddl = {};
		}

		window.ddl['calculator'] = Calculator.default;

		AUI.add(
			'liferay-ddl-calculator-soy',
			function(A) {
				debugger;
			}
		);
	}
);
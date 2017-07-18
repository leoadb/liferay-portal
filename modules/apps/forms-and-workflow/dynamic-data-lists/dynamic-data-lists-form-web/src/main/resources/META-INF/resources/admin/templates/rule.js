Liferay.Loader.require(
	'frontend-js-metal-web/metal-soy-bundle/build/bundle',
	'dynamic-data-lists-form-web/rule.es',
	function(MetalSoyBundle, Rule) {
		if (!window.ddl) {
			window.ddl = {};
		}

		window.ddl['rule'] = Rule.default;

		AUI.add(
			'liferay-ddl-rule-soy',
			function(A) {
				debugger;
			}
		);
	}
);
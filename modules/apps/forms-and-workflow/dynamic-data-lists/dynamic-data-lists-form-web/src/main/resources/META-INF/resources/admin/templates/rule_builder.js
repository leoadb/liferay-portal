Liferay.Loader.require(
	'frontend-js-metal-web/metal-soy-bundle/build/bundle',
	'dynamic-data-lists-form-web/rule_builder.es',
	function(MetalSoyBundle, RuleBuilder) {
		if (!window.ddl) {
			window.ddl = {};
		}

		window.ddl['rule_builder'] = RuleBuilder.default;

		AUI.add(
			'liferay-ddl-rule-builder-soy',
			function(A) {
				debugger;
			}
		);
	}
);
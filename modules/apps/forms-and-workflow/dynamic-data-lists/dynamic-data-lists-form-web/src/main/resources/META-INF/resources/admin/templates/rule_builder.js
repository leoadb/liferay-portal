Liferay.Loader.require(
	'frontend-js-metal-web/metal-soy-bundle/build/bundle',
	'dynamic-data-lists-form-web/admin/templates/rule_builder.es',
	function(MetalSoyBundle, RuleBuilder) {
		if (!window.DDLRuleBuilder) {
			window.DDLRuleBuilder = {};
		}

		window.DDLRuleBuilder.render = RuleBuilder.default;

		AUI.add('liferay-ddl-form-builder-rule-builder-template');
	}
);
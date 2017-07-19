Liferay.Loader.require(
	'frontend-js-metal-web/metal-soy-bundle/build/bundle',
	'dynamic-data-lists-form-web/admin/templates/rule.es',
	function(MetalSoyBundle, Rule) {
		if (!window.DDLRule) {
			window.DDLRule = {};
		}

		window.DDLRule.render = Rule.default;

		AUI.add('liferay-ddl-form-builder-rule-template');
	}
);
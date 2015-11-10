AUI.add(
	'liferay-ddm-form-field-select',
	function(A) {
		var Lang = A.Lang;

		var TPL_OPTION = '<option>{label}</option>';

		var SelectField = A.Component.create(
			{
				ATTRS: {
					dataSourceType: {
						value: 'manual'
					},

					ddmDataProviderId: {
						value: 0
					},

					options: {
						validator: Array.isArray,
						value: []
					},

					strings: {
						value: {
							dynamicallyLoadedData: Liferay.Language.get('dynamically-loaded-data')
						}
					},

					type: {
						value: 'select'
					},

					value: {
						setter: '_setValue',
						value: []
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-select',

				prototype: {
					getOptions: function() {
						var instance = this;

						return A.map(
							instance.get('options'),
							function(item) {
								var label = item.label;

								if (Lang.isObject(label)) {
									label = label[instance.get('locale')];
								}

								return {
									label: label,
									status: instance._getOptionStatus(item),
									value: item.value
								};
							}
						);
					},

					getTemplateContext: function() {
						var instance = this;

						return A.merge(
							SelectField.superclass.getTemplateContext.apply(instance, arguments),
							{
								options: instance.getOptions()
							}
						);
					},

					getTemplateRenderer: function() {
						var instance = this;

						var renderer = SelectField.superclass.getTemplateRenderer.apply(instance, arguments);

						if (instance.get('dataSourceType') !== 'manual') {
							renderer = A.bind('renderTemplate', instance, renderer);
						}

						return renderer;
					},

					render: function() {
						var instance = this;

						var dataSourceType = instance.get('dataSourceType');

						SelectField.superclass.render.apply(instance, arguments);

						if (dataSourceType !== 'manual' && instance.get('builder')) {
							var inputNode = instance.getInputNode();

							var strings = instance.get('strings');

							inputNode.attr('disabled', true);

							inputNode.html(
								Lang.sub(
									TPL_OPTION,
									{
										label: strings.dynamicallyLoadedData
									}
								)
							);
						}

						return instance;
					},

					renderTemplate: function(renderer, context) {
						var instance = this;

						var container = instance.fetchContainer();

						var template;

						if (container) {
							template = container.html();
						}
						else {
							template = renderer(context);
						}

						return template;
					},

					_getOptionStatus: function(option) {
						var instance = this;

						var status = '';

						var value = instance.get('value');

						if (instance.get('localizable')) {
							value = value[instance.get('locale')] || [];
						}

						if (value.indexOf(option.value) > -1) {
							status = 'selected';
						}

						return status;
					},

					_setValue: function(val) {
						return val || [];
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').Select = SelectField;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-field']
	}
);
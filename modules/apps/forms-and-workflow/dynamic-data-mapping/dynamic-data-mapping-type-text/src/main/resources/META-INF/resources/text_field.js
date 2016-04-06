AUI.add(
	'liferay-ddm-form-field-text',
	function(A) {
		var Lang = A.Lang;

		new A.TooltipDelegate(
			{
				position: 'left',
				trigger: '.liferay-ddm-form-field-text .help-icon',
				visible: false
			}
		);

		var TextField = A.Component.create(
			{
				ATTRS: {
					dataProviderSelectedValue: {
						value: ''
					},

					dataProviderURL: {
						valueFn: '_valueDataProviderURL'
					},

					dataSourceOptions: {
						value: []
					},

					ddmDataProviderInstanceId: {
						value: 0
					},

					displayStyle: {
						value: 'singleline'
					},

					placeholder: {
						value: ''
					},

					tooltip: {
						value: ''
					},

					type: {
						value: 'text'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-text',

				prototype: {
					getTemplateContext: function() {
						var instance = this;

						return A.merge(
							TextField.superclass.getTemplateContext.apply(instance, arguments),
							{
								displayStyle: instance.get('displayStyle'),
								placeholder: instance.getLocalizedValue(instance.get('placeholder')),
								tooltip: instance.getLocalizedValue(instance.get('tooltip'))
							}
						);
					},

					render: function() {
						var instance = this;

						TextField.superclass.render.apply(instance, arguments);

						if (instance.get('displayStyle') === 'multiline') {
							var textAreaNode = instance.getInputNode();

							if (!textAreaNode.autosize) {
								textAreaNode.plug(A.Plugin.Autosize);
								textAreaNode.height(textAreaNode.get('scrollHeight'));
							}

							textAreaNode.autosize._uiAutoSize();
						}

						if (instance.get('displayStyle') === 'singleline' && instance._hasDataProvider() && !instance.get('builder') && !instance.get('readOnly')) {
							instance._getDataSourceData(
								function(options) {
									instance.set('dataSourceOptions', options);
									instance._instantiateAutoComplete();
								}
							);
						}

						return instance;
					},

					toJSON: function() {
						var instance = this;

						var json = TextField.superclass.toJSON.apply(instance, arguments);

						if (!instance._hasDataProvider()) {
							return json;
						}

						json.value[instance.get('locale')] = instance.get('dataProviderSelectedValue');

						return json;
					},

					_getAutoCompateData: function() {
						var instance = this;

						return A.map(
							instance.get('dataSourceOptions'),
							function(item) {
								var label = item.label;

								if (Lang.isObject(label)) {
									label = label[instance.get('locale')];
								}

								return [label, item.value];
							}
						);
					},

					_getDataSourceData: function(callback) {
						var instance = this;

						var url = instance.get('dataProviderURL');

						A.io.request(
							url,
							{
								data: {
									ddmDataProviderInstanceId: instance.get('ddmDataProviderInstanceId')
								},
								dataType: 'JSON',
								method: 'GET',
								on: {
									failure: function() {
										callback.call(instance, null);
									},
									success: function() {
										var result = this.get('responseData');

										callback.call(instance, result);
									}
								}
							}
						);
					},

					_hasDataProvider: function() {
						var instance = this;

						return instance.get('ddmDataProviderInstanceId') > 0;
					},

					_instantiateAutoComplete: function(options) {
						var instance = this;

						this.autoComplete = new A.AutoCompleteDeprecated(
							{
								contentBox: instance.get('container').one('.input-group-container'),
								dataSource: instance._getAutoCompateData(),
								input: instance.get('container').one('.form-control'),
								matchKey: 'label',
								schema: {
									resultFields: ['label', 'value']
								}
							}
						).render();

						this.autoComplete.on(
							'itemSelect',
							function(event, data) {
								instance.set('dataProviderSelectedValue', data.value);

								instance.fire(
									'valueChanged',
									{
										domEvent: event,
										field: instance,
										value: instance.getValue()
									}
								);
							}
						);
					},

					_renderErrorMessage: function() {
						var instance = this;

						TextField.superclass._renderErrorMessage.apply(instance, arguments);

						var container = instance.get('container');

						var inputGroup = container.one('.input-group-container');

						inputGroup.insert(container.one('.help-block'), 'after');
					},

					_showFeedback: function() {
						var instance = this;

						TextField.superclass._showFeedback.apply(instance, arguments);

						var container = instance.get('container');

						var feedBack = container.one('.form-control-feedback');

						var inputGroupAddOn = container.one('.input-group-addon');

						if (inputGroupAddOn) {
							feedBack.appendTo(inputGroupAddOn);
						}
						else {
							var inputGroupContainer = container.one('.input-group-container');

							inputGroupContainer.placeAfter(feedBack);
						}
					},

					_valueDataProviderURL: function() {
						var instance = this;

						var dataProviderURL;

						var form = instance.getRoot();

						if (form) {
							dataProviderURL = form.get('dataProviderURL');
						}

						return dataProviderURL;
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').Text = TextField;
	},
	'',
	{
		requires: ['aui-autocomplete-deprecated', 'aui-autosize-deprecated', 'aui-tooltip', 'liferay-ddm-form-renderer-field']
	}
);
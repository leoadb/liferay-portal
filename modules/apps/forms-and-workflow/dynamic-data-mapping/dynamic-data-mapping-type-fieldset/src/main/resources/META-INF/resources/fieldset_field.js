AUI.add(
	'liferay-ddm-form-field-fieldset',
	function(A) {
		var FieldSetField = A.Component.create(
			{
				ATTRS: {
					fields: {
						setter: '_setFields',
						state: true,
						validator: Array.isArray,
						value: []
					},

					showLabel: {
						state: true,
						value: true
					},

					type: {
						value: 'fieldset'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-fieldset',

				prototype: {

					initializer: function() {
						var instance = this;

						if (instance.get('repeatable')) {
							instance._eventHandlers.push(
								instance.after('repeat', instance._afterRepeat)
							);
						}
					},

					getValue: function() {
						return '';
					},

					setValue: function() {
					},

					_afterRepeat: function() {
						var instance = this;

						instance.set('showLabel', false);
					},

					_createNestedField: function(config) {
						var instance = this;

						var fieldClass = instance.getFieldClass();

						return new fieldClass(config);
					},

					_setFields: function(fields) {
						var instance = this;

						return fields.map(A.bind(instance._createNestedField, instance));
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').FieldSet = FieldSetField;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-field']
	}
);
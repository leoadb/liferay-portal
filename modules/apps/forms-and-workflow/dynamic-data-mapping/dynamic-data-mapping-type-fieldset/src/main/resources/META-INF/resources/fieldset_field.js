AUI.add(
	'liferay-ddm-form-field-fieldset',
	function(A) {
		var AObject = A.Object;

		var Renderer = Liferay.DDM.Renderer;

		var FieldTypes = Renderer.FieldTypes;

		var Util = Renderer.Util;

		var FieldSetField = A.Component.create(
			{
				ATTRS: {
					fields: {
						setter: '_setFields',
						validator: Array.isArray,
						value: []
					},

					rows: {
						setter: '_setRows',
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

						config = A.merge(
								config,
							{
								context: A.clone(config),
								fieldName: Util.getFieldNameFromQualifiedName(config.name),
								parent: instance,
								portletNamespace: instance.get('portletNamespace'),
								repeatedIndex: instance.get('repeatedIndex')
							}
						);

						var fieldType = FieldTypes.get(config.type);

						var fieldClassName = fieldType.get('className');

						var fieldClass = AObject.getValue(window, fieldClassName.split('.'));

						var FieldSetNestedField = A.Component.create(
								{
									EXTENDS: fieldClass,

									NAME: 'liferay-ddm-form-field-fieldset-nestedfield',

									prototype: {
										getQualifiedName: function() {
											var instance = this;

											var parent = instance.get('parent');

											return [
												instance.get('portletNamespace'),
												'ddm$$',
												parent.get('fieldName'),
												'$',
												parent.get('instanceId'),
												'$',
												parent.get('repeatedIndex'),
												'#',
												instance.get('fieldName'),
												'$',
												instance.get('instanceId'),
												'$',
												instance.get('repeatedIndex'),
												'$$',
												instance.get('locale')
											].join('');
										},

										getInputNode: function() {
											var instance = this;

											var parent = instance.get('parent');

											return parent.get('container').one(instance.getInputSelector());
										},
									}
								}
							);

						return new FieldSetNestedField(config);
					},

					_setFields: function(fields) {
						var instance = this;

						return fields.map(A.bind(instance._createNestedField, instance));
					},

					_setRows: function(rows) {
						var instance = this;

						var fields = [];

						rows.forEach(function(row) {
							row.columns.forEach(function(column) {
								fields = fields.concat(column.fields);
							});
						});

						instance.set('fields', fields);

						return rows;
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
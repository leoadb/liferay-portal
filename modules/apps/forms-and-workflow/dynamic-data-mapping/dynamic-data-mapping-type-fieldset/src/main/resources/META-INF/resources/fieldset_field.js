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
					context: {
						value: ""
					},

					fields: {
						setter: '_setFields',
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

						instance.after('contextChange', instance._afterContextChange);

						var fields = [];

						var context = instance.get('context');

						var nestedFields = context.nestedFields;

						for (var nestedFieldName in nestedFields) {
							var nestedFieldContext = nestedFields[nestedFieldName][0];

							nestedFieldContext.fieldName = nestedFieldName;

							fields.push(nestedFieldContext);
						}

						context.rows.forEach(function(row) {
							row.columns.forEach(function(column) {
								column.fields.forEach(function(field) {
									field.fieldName = Util.getFieldNameFromQualifiedName(field.name);
								});
							});
						});

						if (instance.get('repeatedIndex') > 0) {
							instance.set('showLabel', false);
						}

						instance.set('fields', fields);
					},

					copyConfiguration: function() {
						var instance = this;

						var config = FieldSetField.superclass.copyConfiguration.apply(instance, arguments);

						var nestedFields = config.context.nestedFields;

						for (var nestedFieldName in nestedFields) {
							var nestedFieldContext = nestedFields[nestedFieldName][0];

							nestedFieldContext.fieldName = nestedFieldName;

							delete nestedFieldContext.instanceId;
							delete nestedFieldContext.name;
							delete nestedFieldContext.value;
						}

						var rows = config.context.rows;

						rows.forEach(function(row) {
							row.columns.forEach(function(column) {
								column.fields.forEach(function(field) {
									field.fieldName = Util.getFieldNameFromQualifiedName(field.name);

									delete field.name;
								});
							});
						});

						return config;
					},

					getValue: function() {
						return '';
					},

					render: function() {
						var instance = this;

						var context = instance.get('context');

						var rows = context.rows;

						rows.forEach(function(row) {
							row.columns.forEach(function(column) {
								column.fields.forEach(function(fieldContext) {
									var field = instance.getField(fieldContext.fieldName);

									fieldContext.name = field.getQualifiedName();
								});
							});
						});

						FieldSetField.superclass.render.apply(instance, arguments);

						instance.eachField(
							function(field) {
								field.render();
							}
						);

						return instance;
					},

					setValue: function() {
					},

					_afterContextChange: function(event) {
						var instance = this;

						var nestedFields = event.newVal.nestedFields;

						if (nestedFields) {
							for (var nestedFieldName in nestedFields) {
								var nestedFieldContext = nestedFields[nestedFieldName][0];

								if (nestedFieldContext.name) {
									var name = Util.getFieldNameFromQualifiedName(nestedFieldContext.name);

									var field = instance.getField(name);

									if (field) {
										field.set('context', nestedFieldContext);
									}
								}
							}
						}
					},

					_createNestedField: function(config) {
						var instance = this;

						var fieldType = FieldTypes.get(config.type);

						var fieldClassName = fieldType.get('className');

						var fieldClass = AObject.getValue(window, fieldClassName.split('.'));

						var FieldSetNestedField = A.Component.create(
								{
									EXTENDS: fieldClass,

									NAME: 'liferay-ddm-form-field-fieldset-nestedfield',

									prototype: {
										getQualifiedName: function() {
											var nestedFieldInstance = this;

											var parent = nestedFieldInstance.get('parent');

											console.log("parent: " + parent.get('repeatedIndex'));
											console.log("nestedFieldInstance: " + nestedFieldInstance.get('repeatedIndex'));

											return [
												nestedFieldInstance.get('portletNamespace'),
												'ddm$$',
												parent.get('fieldName'),
												'$',
												parent.get('instanceId'),
												'$',
												parent.get('repeatedIndex'),
												'#',
												nestedFieldInstance.get('fieldName'),
												'$',
												nestedFieldInstance.get('instanceId'),
												'$',
												nestedFieldInstance.get('repeatedIndex'),
												'$$',
												nestedFieldInstance.get('locale')
											].join('');
										}
									}
								}
							);

						var nestedFieldContext = A.merge(
							config,
							{
								context: A.clone(config),
								parent: instance,
								portletNamespace: instance.get('portletNamespace'),
								repeatedIndex: 0,
							}
						);

						return new FieldSetNestedField(nestedFieldContext);
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
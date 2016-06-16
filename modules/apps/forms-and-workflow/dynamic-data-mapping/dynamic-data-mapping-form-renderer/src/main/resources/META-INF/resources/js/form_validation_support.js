AUI.add(
	'liferay-ddm-form-renderer-validation',
	function(A) {
		var Lang = A.Lang;

		var Renderer = Liferay.DDM.Renderer;

		var FormValidationSupport = function() {
		};

		FormValidationSupport.ATTRS = {
		};

		FormValidationSupport.prototype = {
			hasErrors: function() {
				var instance = this;

				var hasErrors = false;

				instance.eachField(
					function(field) {
						hasErrors = field.hasErrors();

						if (hasErrors) {
							console.log(field.get('name'), field.getValue());
						}

						return hasErrors;
					}
				);

				return hasErrors;
			},

			pageHasErrors: function(pageNode) {
				var instance = this;

				var hasErrors = false;

				instance.eachField(
					function(field) {
						if (pageNode.contains(field.get('container'))) {
							hasErrors = field.hasErrors();
						}

						return hasErrors;
					}
				);

				return hasErrors;
			},

			processPageValidation: function(pageNode) {
				var instance = this;

				instance.eachField(
					function(field) {
						var container = field.get('container');

						if (field.hasErrors() && pageNode.contains(container)) {
							field.showErrorMessage();
						}
					}
				);
			},

			processValidation: function() {
				var instance = this;

				instance.eachField(
					function(field) {
						if (field.hasErrors()) {
							field.showErrorMessage();
						}
					}
				);
			},

			validate: function(callback) {
				var instance = this;

				var evaluator = instance.get('evaluator');

				evaluator.evaluate(
					instance,
					function(result) {
						var hasErrors = true;

						if (result && Lang.isObject(result)) {
							instance.processValidation();

							hasErrors = instance.hasErrors();
						}

						console.log('validate errors', hasErrors);

						if (callback) {
							callback.call(instance, hasErrors, result);
						}
					}
				);
			},

			validatePage: function(pageNode, callback) {
				var instance = this;

				var evaluator = instance.get('evaluator');

				evaluator.evaluate(
					instance,
					function(result) {
						var hasErrors = true;

						if (result && Lang.isObject(result)) {
							instance.processPageValidation(pageNode, result);

							hasErrors = instance.pageHasErrors(pageNode);
						}

						console.log('validatePage errors', hasErrors);

						if (callback) {
							callback.call(instance, hasErrors, result);
						}
					}
				);
			}
		};

		Liferay.namespace('DDM.Renderer').FormValidationSupport = FormValidationSupport;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-expressions-evaluator']
	}
);
AUI.add(
	'liferay-ddm-form-renderer-evaluation',
	function(A) {
		var Lang = A.Lang;

		var Renderer = Liferay.DDM.Renderer;

		var Util = Renderer.Util;

		var FormEvaluationSupport = function() {
		};

		FormEvaluationSupport.ATTRS = {
			evaluatorURL: {
				value: ''
			},

			evaluator: {
				valueFn: '_valueEvaluator'
			}
		};

		FormEvaluationSupport.prototype = {
			initializer: function() {
				var instance = this;

				var evaluator = instance.get('evaluator');

				instance._eventHandlers.push(
					evaluator.after('evaluationEnded', A.bind('_afterEvaluationEnded', instance)),
					evaluator.after('evaluationStarted', A.bind('_afterEvaluationStarted', instance))
				);
			},

			destructor: function() {
				var instance = this;

				var evaluator = instance.get('evaluator');

				evaluator.destroy();
			},

			processEvaluationResultEvent: function(event) {
				var instance = this;

				var trigger = event.trigger;

				var result = event.result;

				if (result && Lang.isObject(result)) {
					var visitor = instance.get('visitor');

					visitor.set('pages', result);

					visitor.set(
						'fieldHandler',
						function(fieldContext) {
							var fieldName = Util.getFieldNameFromQualifiedName(fieldContext.name);

							var instanceId = Util.getInstanceIdFromQualifiedName(fieldContext.name);

							var field = instance.getField(fieldName, instanceId);

							field.set('context', fieldContext);

							if (field !== trigger) {
								field.setValue(fieldContext.value);
							}

							if (fieldContext.valid) {
								field.hideErrorMessage();
								field.showValidationStatus();
							}
						}
					);

					visitor.visit();
				}
				else {
					var strings = instance.get('strings');

					instance.showAlert(strings.requestErrorMessage);
				}
			},

			_afterEvaluationEnded: function(event) {
				var instance = this;

				var trigger = event.trigger;

				trigger.hideFeedback();

				instance.processEvaluationResultEvent(event);
			},

			_afterEvaluationStarted: function(event) {
				var instance = this;

				var trigger = event.trigger;

				trigger.showLoadingFeedback();
			},

			_valueEvaluator: function() {
				var instance = this;

				return new Renderer.ExpressionsEvaluator(
					{
						form: instance
					}
				);
			}
		};

		Liferay.namespace('DDM.Renderer').FormEvaluationSupport = FormEvaluationSupport;
	},
	'',
	{
		requires: []
	}
);
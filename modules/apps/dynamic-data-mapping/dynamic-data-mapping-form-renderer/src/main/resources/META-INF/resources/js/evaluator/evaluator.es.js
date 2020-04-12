/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {Environment, Interpreter, Parser, Scanner} from 'lfr-forms-evaluator';

import {PagesVisitor} from '../util/visitors.es';
import CalculateFunction from './functions/calculate.es';
import CallFunction from './functions/call.es';
import ContainsFunction from './functions/contains.es';
import EqualsFunction from './functions/equals.es';
import GetValueFunction from './functions/getValue.es';
import IsEmailAddressFunction from './functions/isEmailAddress.es';
import IsEmptyFunction from './functions/isEmpty.es';
import IsURLFunction from './functions/isURL.es';
import SetDataTypeFunction from './functions/setDataType.es';
import SetMultipleFunction from './functions/setMultiple.es';
import SetOptionsFunction from './functions/setOptions.es';
import SetRequiredFunction from './functions/setRequired.es';
import SetValidationDataTypeFunction from './functions/setValidationDataType.es';
import SetValidationFieldNameFunction from './functions/setValidationFieldName.es';
import SetValueFunction from './functions/setValue.es';
import SetVisibleFunction from './functions/setVisible.es';

class AbortableInterpreter extends Interpreter {
	constructor(expression, environment, controller) {
		super(expression, environment);

		this.controller = controller;
	}

	getSignal() {
		if (!this.controller) {
			return null;
		}

		return this.controller.signal;
	}

	isAborted() {
		if (!this.controller) {
			return false;
		}

		return this.controller.signal.aborted;
	}
}

class Evaluator {
	constructor() {
		if (window.AbortController) {
			this.abortController = new AbortController();
		}

		this.environment = this.buildEnvironment();
	}

	buildEnvironment() {
		const environment = new Environment();

		environment.define('calculate', new CalculateFunction());
		environment.define('call', new CallFunction());
		environment.define('contains', new ContainsFunction());
		environment.define('equals', new EqualsFunction());
		environment.define('getValue', new GetValueFunction());
		environment.define('isEmailAddress', new IsEmailAddressFunction());
		environment.define('isEmpty', new IsEmptyFunction());
		environment.define('isURL', new IsURLFunction());
		environment.define('setDataType', new SetDataTypeFunction());
		environment.define('setMultiple', new SetMultipleFunction());
		environment.define('setOptions', new SetOptionsFunction());
		environment.define('setRequired', new SetRequiredFunction());
		environment.define(
			'setValidationDataType',
			new SetValidationDataTypeFunction()
		);
		environment.define(
			'setValidationFieldName',
			new SetValidationFieldNameFunction()
		);
		environment.define('setValue', new SetValueFunction());
		environment.define('setVisible', new SetVisibleFunction());

		return environment;
	}

	evaluate({pages, rules}) {
		if (this.ongoing) {
			this.controller.abort();
		}

		if (window.AbortController) {
			this.controller = new AbortController();
		}

		this.ongoing = this.evaluateRules(pages, rules).then(newPages => {
			return this.evaluateValidations(newPages).then(newPages => {
				this.ongoing = null;

				return newPages;
			});
		});

		return this.ongoing;
	}

	evaluateExpression(source, environment) {
		const scanner = new Scanner(source);
		const tokens = scanner.scanTokens();

		const parser = new Parser(tokens);
		const expression = parser.parse();

		const interpreter = new AbortableInterpreter(
			expression,
			environment,
			this.controller
		);

		return interpreter.interpret();
	}

	evaluateRules(pages, rules) {
		const {environment} = this;

		return rules.reduce((previousPromise, rule) => {
			return previousPromise.then(previousPages => {
				environment.define('pages', previousPages);

				return this.evaluateExpression(
					rule.condition,
					environment
				).then(conditionMatches => {
					let promise = Promise.resolve();

					if (conditionMatches) {
						rule.actions.forEach(action => {
							promise = promise.then(() =>
								this.evaluateExpression(action, environment)
							);
						});
					}

					return promise.then(() => environment.values.pages);
				});
			});
		}, Promise.resolve(pages));
	}

	evaluateValidations(pages) {
		const visitor = new PagesVisitor(pages);

		const promises = [];
		const results = {};

		visitor.visitFields(field => {
			if (field.validation && field.validation.expression) {
				const expression = field.validation.expression.value;
				const {errorMessage} = field.validation;

				const environment = this.buildEnvironment();

				environment.define(field.fieldName, field.value);
				environment.define('pages', pages);

				promises.push(
					this.evaluateExpression(expression, environment).then(
						valid => {
							results[field.name] = {
								errorMessage,
								valid,
							};
						}
					)
				);
			}
			else {
				results[field.name] = {
					errorMessage: '',
					valid: field.valid,
				};
			}
		});

		return Promise.all(promises).then(() => {
			return visitor.mapFields(field => ({
				...field,
				...results[field.name],
			}));
		});
	}
}

export default Evaluator;

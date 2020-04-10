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

import CalculateFunction from './functions/calculate.es';
import CallFunction from './functions/call.es';
import ContainsFunction from './functions/contains.es';
import EqualsFunction from './functions/equals.es';
import GetValueFunction from './functions/getValue.es';
import IsEmptyFunction from './functions/isEmpty.es';
import SetDataTypeFunction from './functions/setDataType.es';
import SetMultipleFunction from './functions/setMultiple.es';
import SetOptionsFunction from './functions/setOptions.es';
import SetRequiredFunction from './functions/setRequired.es';
import SetValidationDataTypeFunction from './functions/setValidationDataType.es';
import SetValidationFieldNameFunction from './functions/setValidationFieldName.es';
import SetValueFunction from './functions/setValue.es';
import SetVisibleFunction from './functions/setVisible.es';

export const buildEnvironment = () => {
	const environment = new Environment();

	environment.define('call', new CallFunction());
	environment.define('calculate', new CalculateFunction());
	environment.define('contains', new ContainsFunction());
	environment.define('equals', new EqualsFunction());
	environment.define('getValue', new GetValueFunction());
	environment.define('isEmpty', new IsEmptyFunction());
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
};

export const evaluateExpression = (source, environment) => {
	const scanner = new Scanner(source);
	const tokens = scanner.scanTokens();

	const parser = new Parser(tokens);
	const expression = parser.parse();

	const interpreter = new Interpreter(expression, environment);

	return interpreter.interpret();
};

export default (pages, rules) => {
	const environment = buildEnvironment();

	return rules.reduce((previousPromise, rule) => {
		return previousPromise.then(previousPages => {
			environment.define('pages', previousPages);

			return evaluateExpression(rule.condition, environment).then(
				conditionMatches => {
					let promise = Promise.resolve();

					if (conditionMatches) {
						rule.actions.forEach(action => {
							promise = promise.then(() =>
								evaluateExpression(action, environment)
							);
						});
					}

					return promise.then(() => environment.values.pages);
				}
			);
		});
	}, Promise.resolve(pages));
};

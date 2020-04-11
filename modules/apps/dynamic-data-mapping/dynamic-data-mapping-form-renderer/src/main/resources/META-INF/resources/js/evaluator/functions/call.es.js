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

import {Callable} from 'lfr-forms-evaluator';

import {makeFetch} from '../../util/fetch.es';
import {PagesVisitor} from '../../util/visitors.es';

class CallFunction extends Callable {
	arity() {
		return 3;
	}

	async doCall(interpreter, args) {
		const {environment} = interpreter;
		const {pages} = environment.values;

		const ddmDataProviderInstanceUUID = args[0];
		const paramsExpression = args[1];
		const resultMapExpression = args[2];

		const url = new URL(
			`${themeDisplay.getPortalURL()}/o/dynamic-data-mapping-form-data-provider`
		);
		const params = {
			ddmDataProviderInstanceUUID,
			p_auth: Liferay.authToken,
			paramsExpression: this.prepareParamsExpression(
				paramsExpression,
				pages
			),
			resultMapExpression,
		};

		Object.keys(params).forEach(key =>
			url.searchParams.append(key, params[key])
		);

		return makeFetch({
			method: 'GET',
			url,
		})
			.then(results => {
				const newPages = this.setResults(pages, results);

				environment.define('pages', newPages);

				return newPages;
			})
			.catch(() => pages);
	}

	getFieldValue(pages, fieldName) {
		const visitor = new PagesVisitor(pages);

		const field = visitor.findField(field => field.fieldName === fieldName);

		if (field) {
			return field.value;
		}

		return '';
	}

	prepareParamsExpression(paramsExpression, pages) {
		const parameters = paramsExpression.split(';');
		const newParameters = [];

		parameters.forEach(parameter => {
			const keyValue = parameter.split('=');
			const fieldValue = this.getFieldValue(pages, keyValue[1]);

			newParameters.push(`${keyValue[0]}=${fieldValue}`);
		});

		return newParameters.join(';');
	}

	setResults(pages, results) {
		return Object.keys(results).reduce((previousPages, fieldName) => {
			const value = results[fieldName];

			if (Array.isArray(value)) {
				return this.updateFieldProperty(
					previousPages,
					fieldName,
					'options',
					value.map(entry => ({label: entry.value, value: entry.key}))
				);
			}

			return this.updateFieldProperty(
				previousPages,
				fieldName,
				'value',
				value
			);
		}, pages);
	}

	updateFieldProperty(pages, fieldName, propertyName, propertyValue) {
		const visitor = new PagesVisitor(pages);

		return visitor.mapFields(field => {
			if (field.fieldName === fieldName) {
				return {
					...field,
					[propertyName]: propertyValue,
				};
			}

			return field;
		});
	}
}

export default CallFunction;

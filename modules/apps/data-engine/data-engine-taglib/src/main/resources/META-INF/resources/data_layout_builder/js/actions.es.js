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

export const DELETE_DATA_DEFINITION_FIELD = 'DELETE_DATA_DEFINITION_FIELD';
export const DELETE_DATA_LAYOUT_FIELD = 'DELETE_DATA_LAYOUT_FIELD';
export const EVALUATION_ERROR = 'EVALUATION_ERROR';
export const UPDATE_DATA_DEFINITION = 'UPDATE_DATA_DEFINITION';
export const UPDATE_DATA_LAYOUT = 'UPDATE_DATA_LAYOUT';
export const UPDATE_DATA_LAYOUT_NAME = 'UPDATE_DATA_LAYOUT_NAME';
export const UPDATE_FIELD_TYPES = 'UPDATE_FIELD_TYPES';
export const UPDATE_FOCUSED_FIELD = 'UPDATE_FOCUSED_FIELD';
export const UPDATE_IDS = 'UPDATE_IDS';
export const UPDATE_PAGES = 'UPDATE_PAGES';

export const dropLayoutBuilderField = ({
	dataLayoutBuilder,
	fieldTypeName,
	...payload
}) => {
	const fieldType = dataLayoutBuilder.getFieldTypes().find(({name}) => {
		return name === fieldTypeName;
	});

	return {
		...payload,
		fieldType: {
			...fieldType,
			editable: true
		}
	};
};

export const deleteDefinitionField = fieldName => ({
	payload: {fieldName},
	type: DELETE_DATA_DEFINITION_FIELD
});

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

import * as FormSupport from 'dynamic-data-mapping-form-renderer/js/components/FormRenderer/FormSupport.es';

import {updateFocusedField} from '../util/focusedField.es';

import handleFieldAdded from './fieldAddedHandler.es';
import handleFieldDeleted from './fieldDeletedHandler.es';

import {
	generateInstanceId,
	getFieldProperties,
	normalizeSettingsContextPages
} from '../../../util/fieldSupport.es';

const createField = (props, event) => {
    const {
        defaultLanguageId,
        editingLanguageId,
        fieldNameGenerator,
        spritemap
    } = props;
    const {fieldType, skipFieldNameGeneration = false} = event;

    let newFieldName;

    if (skipFieldNameGeneration) {
		const {settingsContext} = fieldType;
		const visitor = new PagesVisitor(settingsContext.pages);

		visitor.mapFields(({fieldName, value}) => {
			if (fieldName === 'name') {
				newFieldName = value;
			}
		});
	} else {
		newFieldName = fieldNameGenerator(fieldType.label);
    }

    const focusedField = {
		...fieldType,
		fieldName: newFieldName,
		settingsContext: {
			...fieldType.settingsContext,
			pages: normalizeSettingsContextPages(
				fieldType.settingsContext.pages,
				editingLanguageId,
				fieldType,
				newFieldName
			),
			type: fieldType.name
		}
    };
    
    const {fieldName, name, settingsContext} = focusedField;

	return {
		...getFieldProperties(
			settingsContext,
			defaultLanguageId,
			editingLanguageId
		),
		fieldName,
		instanceId: generateInstanceId(8),
		name,
		settingsContext,
		spritemap,
		type: fieldType.name
    };
}

const createSection = (props, event, fields) => {
    const {fieldTypes} = props;

    const fieldType = fieldTypes.find(fieldType => {
        return fieldType.name === 'section';
    });

    let sectionField = createField(props, {...event, fieldType});

    let sectionFieldRows = {rows:[]};

    fields.reverse().forEach(field => {
        sectionFieldRows = FormSupport.addFieldToColumn(
            [sectionFieldRows],
            0,
            0,
            0,
            field
        )[0]
    });

    return updateFocusedField(
		props,
		{focusedField: sectionField},
		'rows',
		sectionFieldRows.rows
    );
};

const getContext = (context, nestedIndexes) => {
    if (nestedIndexes.length > 0) {
        nestedIndexes.forEach(indexes => {
            context = [context[indexes.pageIndex].rows[indexes.rowIndex].columns[indexes.columnIndex].fields[indexes.fieldIndex]];
        });    
    }

    return context;
};

const getField = (context, {pageIndex, rowIndex, columnIndex, fieldIndex}) => {
    return FormSupport.getField(context, pageIndex, rowIndex, columnIndex, fieldIndex);
};

export default (props, state, event) => {
    const {pages} = state;

    const nestedIndexes = FormSupport.getNestedIndexes(event.data.target.parentElement);

    const targetIndexes = nestedIndexes[nestedIndexes.length-1];

    const context = getContext(pages, nestedIndexes.slice(0,-1));

    const targetField = getField(context, targetIndexes);

    const newField = createField(props, event);

    const sectionField = createSection(props, event, [targetField, newField]);

    let child = sectionField;

    let newContext;
    
    if (nestedIndexes.length > 1) {
        let nestedSection;

        while (nestedIndexes.length > 1) {
            let lastIndexes = nestedIndexes.pop();

            let {columnIndex, fieldIndex, rowIndex} = lastIndexes;

            nestedSection = getContext(pages, nestedIndexes)[0];

            nestedSection.rows[rowIndex].columns[columnIndex].fields[fieldIndex] = child;

            child = updateFocusedField(
                props,
                {focusedField: nestedSection},
                'rows',
                nestedSection.rows
            );
        }
    }

    let {columnIndex, fieldIndex, pageIndex, rowIndex} = nestedIndexes.pop();

    newContext = FormSupport.removeFields(
		pages,
		pageIndex,
		rowIndex,
		columnIndex
	);    

    return {
		focusedField: {
			...child,
			columnIndex,
            fieldIndex,
			pageIndex,
            rowIndex
		},
		pages: FormSupport.addFieldToColumn(
			newContext,
			pageIndex,
			rowIndex,
			columnIndex,
			child
		),
		previousFocusedField: child
	};
};
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

package com.liferay.data.engine.web.internal.servlet.data.fetcher;

import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.data.engine.web.internal.graphql.model.DataDefinitionFieldType;
import com.liferay.data.engine.web.internal.graphql.model.DataDefinitionType;
import com.liferay.data.engine.web.internal.graphql.model.LocalizedValueType;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Leonardo Barros
 */
public abstract class DEBaseDataDefinitionDataFetcher
	extends DEBaseDataFetcher {

	protected List<DataDefinitionFieldType> createDataDefinitionFieldTypes(
		List<DEDataDefinitionField> deDataDefinitionFields) {

		Stream<DEDataDefinitionField> stream = deDataDefinitionFields.stream();

		return stream.map(
			this::map
		).collect(
			Collectors.toList()
		);
	}

	protected DataDefinitionType createDataDefinitionType(
		long deDataDefinitionId, DEDataDefinition deDataDefinition) {

		DataDefinitionType dataDefinitionType = new DataDefinitionType();

		dataDefinitionType.setDataDefinitionId(
			String.valueOf(deDataDefinitionId));
		dataDefinitionType.setDescriptions(
			getLocalizedValuesType(deDataDefinition.getDescription()));
		dataDefinitionType.setFields(
			createDataDefinitionFieldTypes(
				deDataDefinition.getDEDataDefinitionFields()));
		dataDefinitionType.setNames(
			getLocalizedValuesType(deDataDefinition.getName()));
		dataDefinitionType.setStorageType(deDataDefinition.getStorageType());

		return dataDefinitionType;
	}

	protected List<LocalizedValueType> getLocalizedValuesType(
		Map<String, String> values) {

		if (values == null) {
			return null;
		}

		Set<Map.Entry<String, String>> set = values.entrySet();

		Stream<Map.Entry<String, String>> stream = set.stream();

		return stream.map(
			this::map
		).collect(
			Collectors.toList()
		);
	}

	protected DataDefinitionFieldType map(
		DEDataDefinitionField deDataDefinitionField) {

		DataDefinitionFieldType dataDefinitionFieldType =
			new DataDefinitionFieldType(
				deDataDefinitionField.getName(),
				deDataDefinitionField.getType());

		Object defaultValue = deDataDefinitionField.getDefaultValue();

		if (defaultValue != null) {
			dataDefinitionFieldType.setDefaultValue(defaultValue.toString());
		}

		dataDefinitionFieldType.setIndexable(
			deDataDefinitionField.isIndexable());
		dataDefinitionFieldType.setLabels(
			getLocalizedValuesType(deDataDefinitionField.getLabel()));
		dataDefinitionFieldType.setLocalizable(
			deDataDefinitionField.isLocalizable());
		dataDefinitionFieldType.setRepeatable(
			deDataDefinitionField.isRepeatable());
		dataDefinitionFieldType.setRequired(deDataDefinitionField.isRequired());
		dataDefinitionFieldType.setTips(
			getLocalizedValuesType(deDataDefinitionField.getTip()));

		return dataDefinitionFieldType;
	}

	protected LocalizedValueType map(Map.Entry<String, String> entry) {
		return new LocalizedValueType(entry.getKey(), entry.getValue());
	}

}
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

package com.liferay.data.engine.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * It represents a validation rule that may be applied to a data definition.
 *
 * @author Leonardo Barros
 * @see com.liferay.data.engine.rules.DEDataDefinitionValidationRuleFunction
 */
public final class DEDataDefinitionValidationRule implements Serializable {

	/**
	 * Default constructor.
	 */
	public DEDataDefinitionValidationRule() {
	}

	/**
	 * It creates a validation rule.
	 *
	 * @param name A name of validation rule function.
	 * The name must match the value of the property
	 * "data.definition.validation.rule.function.name" of a registered OSGI
	 * component which implements {@link
	 * com.liferay.data.engine.rules.DEDataDefinitionValidationRuleFunction}.
	 *
	 * @param deDataDefinitionFieldNames A list of data definition field's name.
	 * The validation rule must be applied to this list of fields.
	 */
	public DEDataDefinitionValidationRule(
		String name, List<String> deDataDefinitionFieldNames) {

		_name = name;

		setDEDataDefinitionFieldNames(deDataDefinitionFieldNames);
	}

	/**
	 * Add parameters to be used by the validator.
	 *
	 * @param name The parameter's name.
	 * @param value The parameter's value.
	 */
	public void addParameter(String name, Object value) {
		_parameters.put(name, value);
	}

	/**
	 * @return A list of data definition field's name.
	 */
	public List<String> getDEDataDefinitionFieldNames() {
		return _deDataDefinitionFieldNames;
	}

	/**
	 * @return A name of a validation rule function.
	 */
	public String getName() {
		return _name;
	}

	/**
	 * @return Additional parameters.
	 */
	public Map<String, Object> getParameters() {
		return Collections.unmodifiableMap(_parameters);
	}

	/**
	 * It sets a list of data definition field's name.
	 *
	 * @param deDataDefinitionFieldNames A list of data definition field's name.
	 */
	public void setDEDataDefinitionFieldNames(
		List<String> deDataDefinitionFieldNames) {

		_deDataDefinitionFieldNames = new ArrayList<>();

		if (deDataDefinitionFieldNames != null) {
			_deDataDefinitionFieldNames.addAll(deDataDefinitionFieldNames);
		}
	}

	/**
	 * It sets the name of the validation rule function to be used.
	 *
	 * @param name A name of a validation rule function.
	 */
	public void setName(String name) {
		_name = name;
	}

	private List<String> _deDataDefinitionFieldNames = new ArrayList<>();
	private String _name;
	private Map<String, Object> _parameters = new HashMap<>();

}
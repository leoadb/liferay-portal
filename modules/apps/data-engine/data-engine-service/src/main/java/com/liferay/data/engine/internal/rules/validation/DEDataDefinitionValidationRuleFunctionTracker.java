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

package com.liferay.data.engine.internal.rules.validation;

import com.liferay.data.engine.rules.DEDataDefinitionValidationRuleFunction;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;
import java.util.TreeMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * This is a tracker to manage all available validation functions.
 *
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	service = DEDataDefinitionValidationRuleFunctionTracker.class
)
public class DEDataDefinitionValidationRuleFunctionTracker {

	public DEDataDefinitionValidationRuleFunction
		getDEDataDefinitionValidationRuleFunction(String name) {

		return _deDataDefinitionValidationRuleFunctions.get(name);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDEDataDefinitionValidationRuleFunction(
		DEDataDefinitionValidationRuleFunction
			deDataDefinitionValidationRuleFunction,
		Map<String, Object> properties) {

		String type = MapUtil.getString(
			properties, "data.definition.validation.rule.function.name");

		_deDataDefinitionValidationRuleFunctions.put(
			type, deDataDefinitionValidationRuleFunction);
	}

	@Deactivate
	protected void deactivate() {
		_deDataDefinitionValidationRuleFunctions.clear();
	}

	protected void removeDEDataDefinitionValidationRuleFunction(
		DEDataDefinitionValidationRuleFunction
			deDataDefinitionValidationRuleFunction,
		Map<String, Object> properties) {

		String type = MapUtil.getString(
			properties, "data.definition.validation.rule.function.name");

		_deDataDefinitionValidationRuleFunctions.remove(type);
	}

	private final Map<String, DEDataDefinitionValidationRuleFunction>
		_deDataDefinitionValidationRuleFunctions = new TreeMap<>();

}
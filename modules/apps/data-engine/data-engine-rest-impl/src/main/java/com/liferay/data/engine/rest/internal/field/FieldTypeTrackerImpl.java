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

package com.liferay.data.engine.rest.internal.field;

import com.liferay.data.engine.field.FieldType;
import com.liferay.data.engine.field.FieldTypeContextContributor;
import com.liferay.data.engine.field.FieldTypeIO;
import com.liferay.data.engine.field.FieldTypeTracker;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = FieldTypeTracker.class)
public class FieldTypeTrackerImpl implements FieldTypeTracker {

	public FieldType getFieldType(String type) {
		return _fieldTypes.get(type);
	}

	@Override
	public FieldTypeContextContributor getFieldTypeContextContributor(
		String type) {

		return _fieldTypesContextContributor.get(type);
	}

	@Override
	public FieldTypeIO getFieldTypeIO(String type) {
		return _fieldTypesIO.get(type);
	}

	public Collection<FieldType> getFieldTypes() {
		return _fieldTypes.values();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addFieldType(
		FieldType fieldType, Map<String, Object> properties) {

		String type = MapUtil.getString(
			properties, "de.data.definition.field.type");

		_fieldTypes.put(type, fieldType);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addFieldTypeContextContributor(
		FieldTypeContextContributor fieldTypeContextContributor,
		Map<String, Object> properties) {

		String type = MapUtil.getString(
			properties, "de.data.definition.field.type");

		_fieldTypesContextContributor.put(type, fieldTypeContextContributor);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addFieldTypeIO(
		FieldTypeIO fieldTypeIO, Map<String, Object> properties) {

		String type = MapUtil.getString(
			properties, "de.data.definition.field.type");

		_fieldTypesIO.put(type, fieldTypeIO);
	}

	@Deactivate
	protected void deactivate() {
		_fieldTypes.clear();
		_fieldTypesIO.clear();
		_fieldTypesContextContributor.clear();
	}

	protected void removeFieldType(
		FieldType fieldType, Map<String, Object> properties) {

		String type = MapUtil.getString(
			properties, "de.data.definition.field.type");

		_fieldTypes.remove(type);
	}

	protected void removeFieldTypeContextContributor(
		FieldTypeContextContributor fieldTypeContextContributor,
		Map<String, Object> properties) {

		String type = MapUtil.getString(
			properties, "de.data.definition.field.type");

		_fieldTypesContextContributor.remove(type);
	}

	protected void removeFieldTypeIO(
		FieldTypeIO fieldTypeIO, Map<String, Object> properties) {

		String type = MapUtil.getString(
			properties, "de.data.definition.field.type");

		_fieldTypesIO.remove(type);
	}

	private final Map<String, FieldType> _fieldTypes = new TreeMap<>();
	private final Map<String, FieldTypeContextContributor>
		_fieldTypesContextContributor = new TreeMap<>();
	private final Map<String, FieldTypeIO> _fieldTypesIO = new TreeMap<>();

}
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

package com.liferay.dynamic.data.mapping.internal;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portlet.dynamicdatamapping.DDMForm;
import com.liferay.portlet.dynamicdatamapping.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.DDMStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DDMStructureImpl implements DDMStructure {

	public DDMStructureImpl(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure structure) {

		_structure = structure;
	}

	@Override
	public DDMForm getDDMForm() {
		try {
			return BeanPropertiesUtil.deepCopyProperties(
				_structure.getDDMForm(), DDMForm.class);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public List<DDMFormField> getDDMFormFields(boolean includeTransientFields) {
		try {
			List<DDMFormField> ddmFormFields = new ArrayList<>();

			for (com.liferay.portlet.dynamicdatamapping.model.DDMFormField
					ddmFormField :
						_structure.getDDMFormFields(includeTransientFields)) {

				ddmFormFields.add(
					BeanPropertiesUtil.deepCopyProperties(
						ddmFormField, DDMFormField.class));
			}

			return ddmFormFields;
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public DDMFormLayout getDDMFormLayout() throws PortalException {
		try {
			return BeanPropertiesUtil.deepCopyProperties(
				_structure.getDDMFormLayout(), DDMFormLayout.class);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public String getDefinition() {
		return _structure.getDescription();
	}

	@Override
	public Map<Locale, String> getDescriptionMap() {
		return _structure.getDescriptionMap();
	}

	@Override
	public String getFieldDataType(String fieldName) throws PortalException {
		return _structure.getFieldDataType(fieldName);
	}

	@Override
	public String getFieldProperty(String fieldName, String property)
		throws PortalException {

		return _structure.getFieldProperty(fieldName, property);
	}

	@Override
	public String getFieldType(String fieldName) throws PortalException {
		return _structure.getFieldType(fieldName);
	}

	@Override
	public String getName(Locale locale) {
		return _structure.getName(locale);
	}

	@Override
	public String getName(Locale locale, boolean useDefault) {
		return _structure.getName(locale, useDefault);
	}

	@Override
	public Map<Locale, String> getNameMap() {
		return _structure.getNameMap();
	}

	@Override
	public long getParentStructureId() {
		return _structure.getParentStructureId();
	}

	@Override
	public long getStructureId() {
		return _structure.getStructureId();
	}

	@Override
	public String getStructureKey() {
		return _structure.getStructureKey();
	}

	@Override
	public long getUserId() {
		return _structure.getUserId();
	}

	@Override
	public String getUuid() {
		return _structure.getUuid();
	}

	@Override
	public boolean hasField(String fieldName) {
		return _structure.hasField(fieldName);
	}

	@Override
	public boolean isFieldTransient(String fieldName) throws PortalException {
		return _structure.isFieldTransient(fieldName);
	}

	@Override
	public void setStructureKey(String structureKey) {
		_structure.setStructureKey(structureKey);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMStructureImpl.class);

	private final com.liferay.portlet.dynamicdatamapping.model.DDMStructure
		_structure;

}
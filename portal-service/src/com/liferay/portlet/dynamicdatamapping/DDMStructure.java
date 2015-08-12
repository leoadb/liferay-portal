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

package com.liferay.portlet.dynamicdatamapping;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.StagedGroupedModel;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Leonardo Barros
 */
public interface DDMStructure extends StagedGroupedModel {

	public long getClassNameId();

	public DDMForm getDDMForm();

	public List<DDMFormField> getDDMFormFields(boolean includeTransientFields);

	public String getDefinition();

	public Map<Locale, String> getDescriptionMap();

	public Set<String> getFieldNames();

	public String getFieldProperty(String fieldName, String property)
		throws PortalException;

	public String getFieldType(String fieldName) throws PortalException;

	@Override
	public long getGroupId();

	public String getName(Locale locale);

	public Map<Locale, String> getNameMap();

	public long getParentStructureId();

	public long getPrimaryKey();

	public long getStructureId();

	public String getStructureKey();

	public int getType();

	@Override
	public long getUserId();

}
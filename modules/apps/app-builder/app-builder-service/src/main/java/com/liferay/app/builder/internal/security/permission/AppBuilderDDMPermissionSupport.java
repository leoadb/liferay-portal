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

package com.liferay.app.builder.internal.security.permission;

import com.liferay.dynamic.data.mapping.util.DDMStructurePermissionSupport;
import com.liferay.dynamic.data.mapping.util.DDMTemplatePermissionSupport;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.app.builder.model.AppBuilderApp",
	service = {
		DDMStructurePermissionSupport.class, DDMTemplatePermissionSupport.class
	}
)
public class AppBuilderDDMPermissionSupport
	implements DDMStructurePermissionSupport, DDMTemplatePermissionSupport {

	@Override
	public String getResourceName() {
		return _RESOURCE_NAME;
	}

	@Override
	public String getResourceName(long classNameId) {
		return _RESOURCE_NAME;
	}

	private static final String _RESOURCE_NAME = "com.liferay.app.builder";

}
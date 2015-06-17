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

package com.liferay.portal.verify.model;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMTemplateVerifiableModel implements VerifiableResourcedModel {

	@Override
	public String getModelName() {
		return DDMTemplate.class.getName();
	}

	@Override
	public String getPrimaryKeyColumnName() {
		return "templateId";
	}

	@Override
	public String getTableName() {
		return "DDMTemplate";
	}

	@Override
	public String getUserIdColumnName() {
		return "userId";
	}

}
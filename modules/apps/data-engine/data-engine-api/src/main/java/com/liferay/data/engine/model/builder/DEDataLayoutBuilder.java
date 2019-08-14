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

package com.liferay.data.engine.model.builder;

import com.liferay.data.engine.model.DEDataLayout;
import com.liferay.data.engine.model.DEDataLayoutPage;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Leonardo Barros
 */
@ProviderType
public interface DEDataLayoutBuilder {

	public DEDataLayout build();

	public DEDataLayoutBuilder companyId(long companyId);

	public DEDataLayoutBuilder createDate(Date date);

	public DEDataLayoutBuilder dataDefinitionId(long dataDefinitionId);

	public DEDataLayoutBuilder dataLayoutKey(String dataLayoutKey);

	public DEDataLayoutBuilder description(Map<Locale, String> description);

	public DEDataLayoutBuilder groupId(long groupId);

	public DEDataLayoutBuilder id(long id);

	public DEDataLayoutBuilder modifiedDate(Date date);

	public DEDataLayoutBuilder name(Map<Locale, String> name);

	public DEDataLayoutBuilder pages(List<DEDataLayoutPage> deDataLayoutPages);

	public DEDataLayoutBuilder paginationMode(String paginationMode);

	public DEDataLayoutBuilder userId(long userId);

}
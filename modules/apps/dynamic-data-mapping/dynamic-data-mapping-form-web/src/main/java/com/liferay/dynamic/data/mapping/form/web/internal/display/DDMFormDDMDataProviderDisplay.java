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

package com.liferay.dynamic.data.mapping.form.web.internal.display;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderDisplay;
import com.liferay.dynamic.data.mapping.form.web.internal.tab.item.DDMFormAdminDataProviderTabItem;
import com.liferay.dynamic.data.mapping.form.web.internal.tab.item.DDMFormAdminFieldSetTabItem;
import com.liferay.dynamic.data.mapping.form.web.internal.tab.item.DDMFormAdminTabItem;
import com.liferay.dynamic.data.mapping.util.DDMDisplayTabItem;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lino Alves
 */
@Component(
	property = "javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN,
	service = DDMDataProviderDisplay.class
)
public class DDMFormDDMDataProviderDisplay implements DDMDataProviderDisplay {

	@Override
	public DDMDisplayTabItem getDefaultTabItem() {
		return _ddmFormAdminDataProviderTabItem;
	}

	@Override
	public String getPortletId() {
		return DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN;
	}

	@Override
	public List<DDMDisplayTabItem> getTabItems() {
		return Arrays.asList(
			_ddmFormAdminTabItem, _ddmFormAdminFieldSetTabItem,
			_ddmFormAdminDataProviderTabItem);
	}

	@Override
	public String getTitle(Locale locale) {
		return LanguageUtil.get(locale, "forms");
	}

	@Reference
	private DDMFormAdminDataProviderTabItem _ddmFormAdminDataProviderTabItem;

	@Reference
	private DDMFormAdminFieldSetTabItem _ddmFormAdminFieldSetTabItem;

	@Reference
	private DDMFormAdminTabItem _ddmFormAdminTabItem;

}
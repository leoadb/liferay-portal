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

package com.liferay.dynamic.data.lists.form.web.asset;

import com.liferay.dynamic.data.lists.form.web.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService;
import com.liferay.dynamic.data.lists.service.permission.DDLRecordPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM
	},
	service = AssetRendererFactory.class
)
public class DDLFormAssetRendererFactory
	extends BaseAssetRendererFactory<DDLRecord> {

	public static final String TYPE = "form";

	public DDLFormAssetRendererFactory() {
		setCategorizable(false);
		setClassName(DDLRecord.class.getName());
		setPortletId(DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM);
		setSearchable(true);
		setSelectable(true);
	}

	@Override
	public AssetRenderer<DDLRecord> getAssetRenderer(long classPK, int type)
		throws PortalException {

		DDLRecord record = _ddlRecordLocalService.fetchDDLRecord(classPK);

		DDLRecordVersion recordVersion = null;

		if (record == null) {
			recordVersion = _ddlRecordVersionLocalService.getRecordVersion(
				classPK);

			record = recordVersion.getRecord();
		}
		else {
			if (type == TYPE_LATEST) {
				recordVersion = record.getLatestRecordVersion();
			}
			else if (type == TYPE_LATEST_APPROVED) {
				recordVersion = record.getRecordVersion();
			}
			else {
				throw new IllegalArgumentException(
					"Unknown asset renderer type " + type);
			}
		}

		DDLRecordSet recordSet = recordVersion.getRecordSet();

		if (recordSet.getScope() != DDLRecordSetConstants.SCOPE_FORMS) {
			return null;
		}

		return createAssetRenderer(record, recordVersion, type);
	}

	@Override
	public String getClassName() {
		return DDLRecord.class.getName();
	}

	@Override
	public String getIconCssClass() {
		return "icon-file-2";
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		return DDLRecordPermission.contains(
			permissionChecker, classPK, actionId);
	}

	@Reference(
		target =
			"(osgi.web.symbolicname=com.liferay.dynamic.data.lists.form.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	protected AssetRenderer<DDLRecord> createAssetRenderer(
		DDLRecord record, DDLRecordVersion recordVersion, int type) {

		DDLFormAssetRenderer ddlFormAssetRenderer = new DDLFormAssetRenderer(
			record, recordVersion);

		ddlFormAssetRenderer.setAssetRendererType(type);
		ddlFormAssetRenderer.setServletContext(_servletContext);

		return ddlFormAssetRenderer;
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/history.png";
	}

	@Reference(unbind = "-")
	protected void setDDLRecordLocalService(
		DDLRecordLocalService ddlRecordLocalService) {

		_ddlRecordLocalService = ddlRecordLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDLRecordVersionLocalService(
		DDLRecordVersionLocalService ddlRecordVersionLocalService) {

		_ddlRecordVersionLocalService = ddlRecordVersionLocalService;
	}

	private volatile DDLRecordLocalService _ddlRecordLocalService;
	private volatile DDLRecordVersionLocalService _ddlRecordVersionLocalService;
	private volatile ServletContext _servletContext;

}
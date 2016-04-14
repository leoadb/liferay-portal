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

package com.liferay.dynamic.data.lists.form.web.display.context;

import com.liferay.dynamic.data.lists.form.web.display.context.util.DDLFormFieldLibraryRequestHelper;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Leonardo Barros
 */
public class DDLFormFieldLibraryDisplayContext {

	public DDLFormFieldLibraryDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_ddlFormFieldLibraryRequestHelper =
			new DDLFormFieldLibraryRequestHelper(_renderRequest);
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/admin/view_field_library.jsp");
		portletURL.setParameter(
			"groupId",
			String.valueOf(
				_ddlFormFieldLibraryRequestHelper.getScopeGroupId()));
		portletURL.setParameter("tab", "field-library");

		return portletURL;
	}

	private final DDLFormFieldLibraryRequestHelper
		_ddlFormFieldLibraryRequestHelper;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}
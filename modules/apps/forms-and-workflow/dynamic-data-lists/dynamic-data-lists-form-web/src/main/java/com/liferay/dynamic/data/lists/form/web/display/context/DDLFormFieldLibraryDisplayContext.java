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
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.search.StructureSearch;
import com.liferay.dynamic.data.mapping.search.StructureSearchTerms;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Leonardo Barros
 */
public class DDLFormFieldLibraryDisplayContext {

	public DDLFormFieldLibraryDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		DDMStructureService ddmStructureService, String displayStyle) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_ddmStructureService = ddmStructureService;
		_displayStyle = displayStyle;

		_classNameId = PortalUtil.getClassNameId(DDMStructure.class);

		_ddlFormFieldLibraryRequestHelper =
			new DDLFormFieldLibraryRequestHelper(_renderRequest);
	}

	public String getOrderByCol() {
		String orderByCol = ParamUtil.getString(
			_renderRequest, "orderByCol", "modified-date");

		return orderByCol;
	}

	public String getOrderByType() {
		String orderByType = ParamUtil.getString(
			_renderRequest, "orderByType", "asc");

		return orderByType;
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

	public StructureSearch getStructureSearch() throws PortalException {
		PortletURL portletURL = getPortletURL();

		portletURL.setParameter("displayStyle", _displayStyle);

		StructureSearch structureSearch = new StructureSearch(
			_renderRequest, portletURL);

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<DDMStructure> orderByComparator =
			DDMUtil.getStructureOrderByComparator(orderByCol, orderByType);

		structureSearch.setOrderByCol(orderByCol);
		structureSearch.setOrderByComparator(orderByComparator);
		structureSearch.setOrderByType(orderByType);

		if (structureSearch.isSearch()) {
			structureSearch.setEmptyResultsMessage("no-fields-were-found");
		}
		else {
			structureSearch.setEmptyResultsMessage("there-are-no-fields");
		}

		setStructureSearchResults(structureSearch);
		setStructureSearchTotal(structureSearch);

		return structureSearch;
	}

	protected void setStructureSearchResults(StructureSearch structureSearch) {
		long[] groupIds =
			new long[] {_ddlFormFieldLibraryRequestHelper.getScopeGroupId()};

		StructureSearchTerms structureSearchTerms =
			(StructureSearchTerms)structureSearch.getSearchTerms();

		List<DDMStructure> ddmStructures = _ddmStructureService.search(
			_ddlFormFieldLibraryRequestHelper.getCompanyId(), groupIds,
			_classNameId, structureSearchTerms.getName(),
			structureSearchTerms.getDescription(),
			structureSearchTerms.getStorageType(),
			DDMStructureConstants.TYPE_FORM_FIELD, WorkflowConstants.STATUS_ANY,
			structureSearchTerms.isAndOperator(), structureSearch.getStart(),
			structureSearch.getEnd(), structureSearch.getOrderByComparator());

		structureSearch.setResults(ddmStructures);
	}

	protected void setStructureSearchTotal(StructureSearch structureSearch) {
		long[] groupIds =
			new long[] {_ddlFormFieldLibraryRequestHelper.getScopeGroupId()};

		StructureSearchTerms structureSearchTerms =
			(StructureSearchTerms)structureSearch.getSearchTerms();

		int total = _ddmStructureService.searchCount(
			_ddlFormFieldLibraryRequestHelper.getCompanyId(), groupIds,
			_classNameId, structureSearchTerms.getName(),
			structureSearchTerms.getDescription(),
			structureSearchTerms.getStorageType(),
			DDMStructureConstants.TYPE_FORM_FIELD, WorkflowConstants.STATUS_ANY,
			structureSearchTerms.isAndOperator());

		structureSearch.setTotal(total);
	}

	private final long _classNameId;
	private final DDLFormFieldLibraryRequestHelper
		_ddlFormFieldLibraryRequestHelper;
	private final DDMStructureService _ddmStructureService;
	private final String _displayStyle;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}
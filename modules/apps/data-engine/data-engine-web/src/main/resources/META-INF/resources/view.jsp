<%--
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
--%>

<%@ include file="/init.jsp" %>

<nav class="management-bar management-bar-light navbar navbar-expand-md toolbar-group-field">
	<div class="autosave-bar container toolbar">
		<ul class="navbar-nav toolbar-group-field">
			<li class="nav-item">
				<button class="btn btn-primary lfr-ddm-add-field lfr-ddm-plus-button nav-btn nav-btn-monospaced" id="addFieldButton">
					<svg class="lexicon-icon">
						<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#plus" />
					</svg>
				</button>
			</li>
		</ul>
	</div>
</nav>

<form class="ddm-form-builder-form" enctype="multipart/form-data" method="post">
	<aui:input name="dataLayoutId" type="hidden" />
	<aui:input name="groupId" type="hidden" />
	<aui:input name="dataLayout" type="hidden" />

	<liferay-data-engine:layout-builder
		dataLayoutId="<%= 0L %>"
		dataLayoutInputId="dataLayout"
		namespace="<%= renderResponse.getNamespace() %>"
	>

	</liferay-data-engine:layout-builder>
</form>
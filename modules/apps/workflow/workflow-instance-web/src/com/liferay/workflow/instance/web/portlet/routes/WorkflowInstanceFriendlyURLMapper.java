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

package com.liferay.workflow.instance.web.portlet.routes;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.DefaultFriendlyURLMapper;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.util.PortletKeys;
import com.liferay.workflow.instance.web.constants.WorkflowInstancePortletKeys;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.friendly-url-routes=META-INF/friendly-url-routes/routes.xml",
		"javax.portlet.name=" + WorkflowInstancePortletKeys.WORKFLOW_INSTANCE,
		"javax.portlet.name=" + PortletKeys.MY_WORKFLOW_INSTANCE
	},
	service = FriendlyURLMapper.class
)
public class WorkflowInstanceFriendlyURLMapper extends DefaultFriendlyURLMapper {

}

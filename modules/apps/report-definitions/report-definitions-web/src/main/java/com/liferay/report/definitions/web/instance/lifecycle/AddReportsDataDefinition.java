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

package com.liferay.report.definitions.web.instance.lifecycle;

import com.liferay.data.engine.rest.client.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.client.dto.v1_0.DataLayout;
import com.liferay.data.engine.rest.client.resource.v1_0.DataDefinitionResource;
import com.liferay.data.engine.rest.client.resource.v1_0.DataLayoutResource;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;

import java.util.HashMap;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	service = {
		AddReportsDataDefinition.class, PortalInstanceLifecycleListener.class
	}
)
public class AddReportsDataDefinition
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {

		DataDefinition dataDefinition =
			DataDefinitionResource.getSiteDataDefinition(
				company.getGroupId(), "REPORTS_DEFINITION");


		if (dataDefinition != null) {
			return;
		}

		dataDefinition = new DataDefinition();

		dataDefinition.setDataDefinitionKey("REPORTS_DEFINITION");
		dataDefinition.setName(
			new HashMap() {
				{
					put("en_US", "Reports Definition");
				}
			});

		dataDefinition = DataDefinitionResource.postSiteDataDefinition(
			company.getGroupId(), dataDefinition);

		System.out.println(dataDefinition.toString());
	}

	public DataLayout getDataLayout(long groupId) throws Exception {
		return DataLayoutResource.getSiteDataLayout(
			groupId, "REPORTS_DEFINITION");
	}

}
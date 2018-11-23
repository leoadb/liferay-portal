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

package com.liferay.report.definitions.portlet.web.portlet;

import com.liferay.data.engine.io.DataDefinitionSerializer;
import com.liferay.data.engine.service.DataDefinitionLocalService;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterTracker;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.report.definitions.portlet.web.configuration.activator.ReportDefinitionConfigurationActivator;
import com.liferay.report.definitions.portlet.web.constants.ReportDefinitionNPMKeys;
import com.liferay.report.definitions.portlet.web.constants.ReportDefinitionPortletKeys;
import com.liferay.report.definitions.portlet.web.display.context.ReportDefinitionsDisplayContext;
import com.liferay.report.definitions.service.ReportDefinitionLocalService;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=false",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ReportDefinitionPortletKeys.PORTLET_NAME,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class ReportDefinitionPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			setRenderRequestAttributes(renderRequest, renderResponse);
		}
		catch (PortalException pe) {
			pe.printStackTrace();
		}

		super.render(renderRequest, renderResponse);
	}

	@Reference(unbind = "-")
	protected void setDDMFormRenderer(DDMFormRenderer ddmFormRenderer) {
		_ddmFormRenderer = ddmFormRenderer;
	}

	protected void setRenderRequestAttributes(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		JSPackage jsPackage = _npmResolver.getJSPackage();

		renderRequest.setAttribute(
			ReportDefinitionNPMKeys.BOOTSTRAP_REQUIRE,
			jsPackage.getResolvedId());

		ReportDefinitionsDisplayContext ddmDataProviderDisplayContext =
			new ReportDefinitionsDisplayContext(
				renderRequest, renderResponse, _ddmFormRenderer,
				_reportDefinitionConfigurationActivator.
					getReportDefinitionConfiguration(),
				_reportDefinitionLocalService, _dataDefinitionLocalService,
				_dataDefinitionSerializer, _ddmStorageAdapterTracker);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, ddmDataProviderDisplayContext);
	}

	@Reference
	private DataDefinitionLocalService _dataDefinitionLocalService;

	@Reference(target = "(data.definition.serializer.type=json)")
	private DataDefinitionSerializer _dataDefinitionSerializer;

	private DDMFormRenderer _ddmFormRenderer;

	@Reference
	private DDMStorageAdapterTracker _ddmStorageAdapterTracker;

	@Reference
	private NPMResolver _npmResolver;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "unsetReportDefinitionConfigurationActivator"
	)
	private volatile ReportDefinitionConfigurationActivator
		_reportDefinitionConfigurationActivator;

	@Reference
	private ReportDefinitionLocalService _reportDefinitionLocalService;

}
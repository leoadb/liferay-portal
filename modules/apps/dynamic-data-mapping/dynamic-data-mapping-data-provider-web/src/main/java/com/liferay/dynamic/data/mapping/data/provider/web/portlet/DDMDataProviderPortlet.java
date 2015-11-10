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

package com.liferay.dynamic.data.mapping.data.provider.web.portlet;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderSettings;
import com.liferay.dynamic.data.mapping.data.provider.web.constants.DDMDataProviderPortletKeys;
import com.liferay.dynamic.data.mapping.data.provider.web.display.context.DDMDataProviderDisplayContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderLocalService;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderService;
import com.liferay.osgi.service.tracker.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.UserLocalService;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-dynamic-data-mapping-data-provider",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.instanceable=false",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Dynamic Data Mapping Data Provider Web",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + DDMDataProviderPortletKeys.DYNAMIC_DATA_MAPPING_DATA_PROVIDER,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class DDMDataProviderPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		DDMDataProviderDisplayContext ddmDataProviderDisplayContext =
			new DDMDataProviderDisplayContext(
				renderRequest, renderResponse, _ddmDataProviderService,
				_ddmDataProviderLocalService, _ddmFormRenderer,
				_ddmFormValuesJSONDeserializer, _userLocalService,
				_ddmDataProvidersMap);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, ddmDataProviderDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	@Activate
	protected void activate(final BundleContext bundleContext)
		throws InvalidSyntaxException {

		_ddmDataProvidersMap = ServiceTrackerMapFactory.singleValueMap(
			bundleContext, DDMDataProviderSettings.class,
			"ddm.data.provider.name",
			ServiceTrackerCustomizerFactory.<DDMDataProviderSettings>
				serviceWrapper(bundleContext));
		_ddmDataProvidersMap.open();
	}

	@Deactivate
	protected void deactivate() {
		_ddmDataProvidersMap.close();
	}

	@Reference(unbind = "-")
	protected void setDDMDataProviderLocalService(
		DDMDataProviderLocalService ddmDataProviderLocalService) {

		_ddmDataProviderLocalService = ddmDataProviderLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMDataProviderService(
		DDMDataProviderService ddmDataProviderService) {

		_ddmDataProviderService = ddmDataProviderService;
	}

	@Reference(unbind = "-")
	protected void setDDMFormRenderer(DDMFormRenderer ddmFormRenderer) {
		_ddmFormRenderer = ddmFormRenderer;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesJSONDeserializer(
		DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer) {

		_ddmFormValuesJSONDeserializer = ddmFormValuesJSONDeserializer;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private DDMDataProviderLocalService _ddmDataProviderLocalService;
	private DDMDataProviderService _ddmDataProviderService;
	private ServiceTrackerMap<String, ServiceWrapper<DDMDataProviderSettings>>
		_ddmDataProvidersMap;
	private DDMFormRenderer _ddmFormRenderer;
	private DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;
	private UserLocalService _userLocalService;

}
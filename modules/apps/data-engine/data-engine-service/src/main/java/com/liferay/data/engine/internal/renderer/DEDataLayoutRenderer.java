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

package com.liferay.data.engine.internal.renderer;

import com.liferay.data.engine.internal.field.DEFieldTypeTracker;
import com.liferay.data.engine.service.DEDataLayoutRenderRequest;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.template.soy.renderer.SoyRenderer;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leonardo Barros
 */
public class DEDataLayoutRenderer {

	public DEDataLayoutRenderer(
		DEFieldTypeTracker deFieldTypeTracker, SoyRenderer soyRenderer) {

		_deFieldTypeTracker = deFieldTypeTracker;
		_soyRenderer = soyRenderer;
	}

	public String render(DEDataLayoutRenderRequest deDataLayoutRenderRequest)
		throws PortalException {

		HttpServletRequest httpServletRequest =
			deDataLayoutRenderRequest.getHttpServletRequest();

		DEDataLayoutRendererContextHelper deDataLayoutRendererContextHelper =
			new DEDataLayoutRendererContextHelper(
				deDataLayoutRenderRequest.getDEDataLayout(),
				_deFieldTypeTracker, httpServletRequest.getLocale());

		Writer writer = new UnsyncStringWriter();

		_soyRenderer.renderSoy(
			httpServletRequest, writer, _TEMPLATE_PATH,
			deDataLayoutRendererContextHelper.getContext());

		return writer.toString();
	}

	private static final String _TEMPLATE_PATH = "/metal/js/components/Form";

	private final DEFieldTypeTracker _deFieldTypeTracker;
	private final SoyRenderer _soyRenderer;

}
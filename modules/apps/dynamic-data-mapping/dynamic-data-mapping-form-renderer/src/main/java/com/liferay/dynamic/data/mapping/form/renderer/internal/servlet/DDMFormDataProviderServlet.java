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

package com.liferay.dynamic.data.mapping.form.renderer.internal.servlet;

import com.liferay.portal.kernel.util.Portal;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderInvoker;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.context.path=/dynamic-data-mapping-form-data-provider",
		"osgi.http.whiteboard.servlet.name=com.liferay.dynamic.data.mapping.form.renderer.internal.servlet.DDMFormDataProviderServlet",
		"osgi.http.whiteboard.servlet.pattern=/dynamic-data-mapping-form-data-provider/*"
	},
	service = Servlet.class
)
public class DDMFormDataProviderServlet extends HttpServlet {

	@Override
	public void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		createContext(httpServletRequest, httpServletResponse);

		super.service(httpServletRequest, httpServletResponse);
	}

	protected void createContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			EventsProcessorUtil.process(
				PropsKeys.SERVLET_SERVICE_EVENTS_PRE,
				PropsValues.SERVLET_SERVICE_EVENTS_PRE, httpServletRequest,
				httpServletResponse);
		}
		catch (ActionException actionException) {
			if (_log.isDebugEnabled()) {
				_log.debug(actionException, actionException);
			}
		}
	}

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		String ddmDataProviderInstanceUUID = ParamUtil.getString(
			httpServletRequest, "ddmDataProviderInstanceUUID");
		String paramsExpression = ParamUtil.getString(
			httpServletRequest, "paramsExpression");
		String resultMapExpression = ParamUtil.getString(
			httpServletRequest, "resultMapExpression");

		try {
			DDMDataProviderRequest.Builder builder =
				DDMDataProviderRequest.Builder.newBuilder();

			builder = builder.withDDMDataProviderId(
				ddmDataProviderInstanceUUID);

			Map<String, String> parameterMap = extractParameters(
				paramsExpression);

			for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
				builder = builder.withParameter(
					entry.getKey(), entry.getValue());
			}

			builder.withGroupId(_portal.getScopeGroupId(httpServletRequest));

			DDMDataProviderRequest ddmDataProviderRequest = builder.build();

			DDMDataProviderResponse ddmDataProviderResponse =
				_ddmDataProviderInvoker.invoke(ddmDataProviderRequest);

			Map<String, String> resultMap = extractResults(resultMapExpression);

			System.out.println(resultMap);	

			Map<String, Object> fieldsResultsMap = getFieldsResultsMap(
				ddmDataProviderResponse, resultMap);

			System.out.println(fieldsResultsMap);

			JSONSerializer jsonSerializer = _jsonFactory.createJSONSerializer();

			httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);

			ServletResponseUtil.write(
				httpServletResponse,
				jsonSerializer.serializeDeep(fieldsResultsMap));
		}
		catch (Exception exception) {
			exception.printStackTrace();;

			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	protected Map<String, String> extractParameters(String expression) {
		if (Validator.isNull(expression)) {
			return Collections.emptyMap();
		}

		Map<String, String> parameters = new HashMap<>();

		String[] innerExpressions = StringUtil.split(
			expression, CharPool.SEMICOLON);

		if (innerExpressions.length == 0) {
			extractDDMFormFieldValue(expression, parameters);
		}
		else {
			for (String innerExpression : innerExpressions) {
				extractDDMFormFieldValue(innerExpression, parameters);
			}
		}

		return parameters;
	}

	protected void extractDDMFormFieldValue(
		String expression, Map<String, String> parameters) {

		String[] tokens = StringUtil.split(expression, CharPool.EQUAL);

		String parameterName = tokens[0];

		String parameterValue = StringPool.BLANK;

		if (tokens.length == 2) {
			parameterValue = tokens[1];
		}

		parameters.put(parameterName, parameterValue);
	}

	protected Map<String, String> extractResults(String resultMapExpression) {
		if (Validator.isNull(resultMapExpression)) {
			return Collections.emptyMap();
		}

		Map<String, String> results = new HashMap<>();

		String[] innerExpressions = StringUtil.split(
			resultMapExpression, CharPool.SEMICOLON);

		for (String innerExpression : innerExpressions) {
			String[] tokens = StringUtil.split(innerExpression, CharPool.EQUAL);

			results.put(tokens[0], tokens[1]);
		}

		return results;
	}

	protected Map<String, Object> getFieldsResultsMap(
		DDMDataProviderResponse ddmDataProviderResponse,
		Map<String, String> resultMap) {

		Map<String, Object> fieldsResultsMap = new HashMap<>();

		for (Map.Entry<String, String> entry : resultMap.entrySet()) {
			String outputName = entry.getValue();

			if (!ddmDataProviderResponse.hasOutput(outputName)) {
				continue;
			}

			String ddmFormFieldName = entry.getKey();

			Optional<List<KeyValuePair>> optionsOptional =
				ddmDataProviderResponse.getOutputOptional(
					outputName, List.class);

			if (optionsOptional.isPresent()) {
				fieldsResultsMap.put(ddmFormFieldName, optionsOptional.get());
			}
			else {
				Optional<String> valueOptional =
					ddmDataProviderResponse.getOutputOptional(
						outputName, String.class);

				fieldsResultsMap.put(ddmFormFieldName, valueOptional.get());
			}
		}

		return fieldsResultsMap;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormDataProviderServlet.class);

	private static final long serialVersionUID = 1L;

	@Reference
	private Portal _portal;

	@Reference
	private DDMDataProviderInvoker _ddmDataProviderInvoker;

	@Reference
	private JSONFactory _jsonFactory;

}
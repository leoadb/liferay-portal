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

package com.liferay.portal.workflow.kaleo.runtime.action;

import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.workflow.WorkflowException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Michael C. Han
 * @author Leonardo Barros
 */
public class ActionExecutorFactory {

	public static final String SCRIPT = "script";
	public static final String HANDLER = "handler";

	public static ActionExecutor getActionExecutor(String type, String name)
		throws WorkflowException {

		if (type.equalsIgnoreCase(SCRIPT)) {
			return _instance._getScriptExecutor(name);
		}
		else {
			return _instance._getHandlerExecutor(name);
		}
	}

	private ActionExecutorFactory() {
		Bundle bundle = FrameworkUtil.getBundle(ActionExecutorFactory.class);

		_bundleContext = bundle.getBundleContext();

		Filter scriptFilter = null;

		try {
			scriptFilter = FrameworkUtil.createFilter(
				"(&(objectClass=" + ScriptExecutor.class.getName() + ")");
		}
		catch (InvalidSyntaxException ise) {
			ReflectionUtil.throwException(ise);
		}

		_scriptServiceTracker = new ServiceTracker<>(
			_bundleContext, scriptFilter,
			new ScriptExecutorServiceTrackerCustomizer());

		_scriptServiceTracker.open();

		Filter handlerFilter = null;

		try {
			handlerFilter = FrameworkUtil.createFilter(
				"(&(objectClass=" + HandlerExecutor.class.getName() + ")");
		}
		catch (InvalidSyntaxException ise) {
			ReflectionUtil.throwException(ise);
		}

		_handlerServiceTracker = new ServiceTracker<>(
			_bundleContext, handlerFilter,
			new HandlerExecutorServiceTrackerCustomizer());

		_handlerServiceTracker.open();
	}

	private ActionExecutor _getHandlerExecutor(String name)
		throws WorkflowException {

		ActionExecutor actionExecutor = _handlerExecutors.get(name);

		if (actionExecutor == null) {
			throw new WorkflowException("Invalid handler " + name);
		}

		return actionExecutor;
	}

	private ActionExecutor _getScriptExecutor(String scriptLanguage)
		throws WorkflowException {

		ActionExecutor actionExecutor = _scriptExecutors.get(scriptLanguage);

		if (actionExecutor == null) {
			throw new WorkflowException(
				"Invalid script language " + scriptLanguage);
		}

		return actionExecutor;
	}

	private static final ActionExecutorFactory _instance =
		new ActionExecutorFactory();

	private final BundleContext _bundleContext;
	private final Map<Object, HandlerExecutor> _handlerExecutors =
		new ConcurrentHashMap<>();
	private final ServiceTracker<HandlerExecutor, HandlerExecutor>
		_handlerServiceTracker;
	private final Map<Object, ScriptExecutor> _scriptExecutors =
		new ConcurrentHashMap<>();
	private final ServiceTracker<ScriptExecutor, ScriptExecutor>
		_scriptServiceTracker;

	private class HandlerExecutorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<HandlerExecutor, HandlerExecutor> {

		@Override
		public HandlerExecutor addingService(
			ServiceReference<HandlerExecutor> serviceReference) {

			HandlerExecutor handlerExecutor = _bundleContext.getService(
				serviceReference);

			String name = handlerExecutor.getClass().getName();

			_handlerExecutors.put(name, handlerExecutor);

			return handlerExecutor;
		}

		@Override
		public void modifiedService(
			ServiceReference<HandlerExecutor> serviceReference,
			HandlerExecutor service) {
		}

		@Override
		public void removedService(
			ServiceReference<HandlerExecutor> serviceReference,
			HandlerExecutor service) {

			HandlerExecutor handlerExecutor = _bundleContext.getService(
				serviceReference);

			_bundleContext.ungetService(serviceReference);

			String name = handlerExecutor.getClass().getName();

			_handlerExecutors.remove(name);
		}

	}

	private class ScriptExecutorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<ScriptExecutor, ScriptExecutor> {

		@Override
		public ScriptExecutor addingService(
			ServiceReference<ScriptExecutor> serviceReference) {

			ScriptExecutor scriptExecutor = _bundleContext.getService(
				serviceReference);

			Object scriptLanguage = serviceReference.getProperty(
				"com.liferay.portal.workflow.kaleo.action.script.language");

			_scriptExecutors.put(scriptLanguage, scriptExecutor);

			return scriptExecutor;
		}

		@Override
		public void modifiedService(
			ServiceReference<ScriptExecutor> serviceReference,
			ScriptExecutor service) {
		}

		@Override
		public void removedService(
			ServiceReference<ScriptExecutor> serviceReference,
			ScriptExecutor service) {

			_bundleContext.ungetService(serviceReference);

			Object scriptLanguage = serviceReference.getProperty(
				"com.liferay.portal.workflow.kaleo.action.script.language");

			_scriptExecutors.remove(scriptLanguage);
		}

	}

}
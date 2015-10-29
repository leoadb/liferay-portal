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

	public static ActionExecutor getScriptExecutor(String scriptLanguage)
		throws WorkflowException {

		return _instance._getScriptExecutor(scriptLanguage);
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

	private static final Map<Object, ScriptExecutor> _scriptExecutors =
		new ConcurrentHashMap<>();

	private final BundleContext _bundleContext;
	private final ServiceTracker<ScriptExecutor, ScriptExecutor>
		_scriptServiceTracker;

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
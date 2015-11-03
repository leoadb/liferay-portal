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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.workflow.kaleo.definition.ExecutionType;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Michael C. Han
 */
public class ActionExecutorUtil {

	public static void executeKaleoActions(
			String kaleoClassName, long kaleoClassPK,
			ExecutionType executionType, ExecutionContext executionContext)
		throws PortalException {

		getActionExecutorManager().executeKaleoActions(
			kaleoClassName, kaleoClassPK, executionType, executionContext);
	}

	protected static ActionExecutorManager getActionExecutorManager() {
		return _serviceTracker.getService();
	}

	private static final ServiceTracker
		<ActionExecutorManager, ActionExecutorManager> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ActionExecutorUtil.class);

		_serviceTracker = new ServiceTracker<>(
			bundle.getBundleContext(), ActionExecutorManager.class, null);

		_serviceTracker.open();
	}

}
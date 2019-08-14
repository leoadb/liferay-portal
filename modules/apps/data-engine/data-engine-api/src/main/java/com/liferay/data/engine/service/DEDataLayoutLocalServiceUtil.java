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

package com.liferay.data.engine.service;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for DEDataLayout. This utility wraps
 * <code>com.liferay.data.engine.service.impl.DEDataLayoutLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DEDataLayoutLocalService
 * @generated
 */
@ProviderType
public class DEDataLayoutLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.data.engine.service.impl.DEDataLayoutLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.data.engine.model.DEDataLayout addDataLayout(
			com.liferay.data.engine.model.DEDataLayout deDataLayout,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addDataLayout(deDataLayout, serviceContext);
	}

	public static com.liferay.data.engine.model.DEDataLayout getDataLayout(
			long deDataLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDataLayout(deDataLayoutId);
	}

	public static com.liferay.data.engine.model.DEDataLayout getDataLayout(
			long groupId, String deDataLayoutKey)
		throws Exception {

		return getService().getDataLayout(groupId, deDataLayoutKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.data.engine.model.DEDataLayout updateDataLayout(
			com.liferay.data.engine.model.DEDataLayout deDataLayout,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateDataLayout(deDataLayout, serviceContext);
	}

	public static DEDataLayoutLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DEDataLayoutLocalService, DEDataLayoutLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DEDataLayoutLocalService.class);

		ServiceTracker<DEDataLayoutLocalService, DEDataLayoutLocalService>
			serviceTracker =
				new ServiceTracker
					<DEDataLayoutLocalService, DEDataLayoutLocalService>(
						bundle.getBundleContext(),
						DEDataLayoutLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
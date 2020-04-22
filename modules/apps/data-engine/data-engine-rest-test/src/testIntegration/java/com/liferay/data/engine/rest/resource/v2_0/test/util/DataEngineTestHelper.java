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

package com.liferay.data.engine.rest.resource.v2_0.test.util;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Leonardo Barros
 */
public class DataEngineTestHelper {

	public DataEngineTestHelper(
		DDLRecordSetLocalService ddlRecordSetLocalService,
		DDMStructureLayoutLocalService ddmStructureLayoutLocalService,
		DDMStructureLocalService ddmStructureLocalService) {

		_ddlRecordSetLocalService = ddlRecordSetLocalService;
		_ddmStructureLayoutLocalService = ddmStructureLayoutLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_serviceRegistrations = _registerModelListeners();
	}

	public void deletePersistedModels() {
		for (DDLRecordSet ddlRecordSet : _ddlRecordSets) {
			try {
				_ddlRecordSetLocalService.deleteRecordSet(ddlRecordSet);
			}
			catch (Exception exception) {
			}
		}

		for (DDMStructure ddmStructure : _ddmStructures) {
			try {
				_ddmStructureLocalService.deleteStructure(ddmStructure);
			}
			catch (Exception exception) {
			}
		}

		for (DDMStructureLayout ddmStructureLayout : _ddmStructureLayouts) {
			try {
				_ddmStructureLayoutLocalService.deleteStructureLayout(
					ddmStructureLayout);
			}
			catch (Exception exception) {
			}
		}

		_ddlRecordSets.clear();
		_ddmStructures.clear();
		_ddmStructureLayouts.clear();
	}

	public void unregister() {
		for (ServiceRegistration serviceRegistration : _serviceRegistrations) {
			serviceRegistration.unregister();
		}
	}

	private List<ServiceRegistration> _registerModelListeners() {
		Bundle bundle = FrameworkUtil.getBundle(DataEngineTestHelper.class);

		BundleContext bundleContext = bundle.getBundleContext();

		return new ArrayList() {
			{
				add(
					bundleContext.registerService(
						ModelListener.class,
						new BaseModelListener<DDLRecordSet>() {

							@Override
							public void onAfterCreate(DDLRecordSet ddlRecordSet)
								throws ModelListenerException {

								_ddlRecordSets.add(ddlRecordSet);
							}

						},
						new HashMapDictionary<>()));
				add(
					bundleContext.registerService(
						ModelListener.class,
						new BaseModelListener<DDMStructure>() {

							@Override
							public void onAfterCreate(DDMStructure ddmStructure)
								throws ModelListenerException {

								_ddmStructures.add(ddmStructure);
							}

						},
						new HashMapDictionary<>()));
				add(
					bundleContext.registerService(
						ModelListener.class,
						new BaseModelListener<DDMStructureLayout>() {

							@Override
							public void onAfterCreate(
									DDMStructureLayout ddmStructureLayout)
								throws ModelListenerException {

								_ddmStructureLayouts.add(ddmStructureLayout);
							}

						},
						new HashMapDictionary<>()));
			}
		};
	}

	private final DDLRecordSetLocalService _ddlRecordSetLocalService;
	private final List<DDLRecordSet> _ddlRecordSets = new ArrayList<>();
	private final DDMStructureLayoutLocalService
		_ddmStructureLayoutLocalService;
	private final List<DDMStructureLayout> _ddmStructureLayouts =
		new ArrayList<>();
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final List<DDMStructure> _ddmStructures = new ArrayList<>();
	private final List<ServiceRegistration> _serviceRegistrations;

}
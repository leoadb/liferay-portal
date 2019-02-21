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

package com.liferay.data.engine.internal.service;

import com.liferay.data.engine.exception.DEDataLayoutException;
import com.liferay.data.engine.internal.executor.DEDataEngineRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataLayoutGetRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataLayoutSaveRequestExecutor;
import com.liferay.data.engine.internal.io.DEDataDefinitionDeserializerTracker;
import com.liferay.data.engine.internal.io.DEDataLayoutDeserializerTracker;
import com.liferay.data.engine.internal.io.DEDataLayoutSerializerTracker;
import com.liferay.data.engine.internal.storage.DEDataStorageTracker;
import com.liferay.data.engine.service.DEDataLayoutGetRequest;
import com.liferay.data.engine.service.DEDataLayoutGetResponse;
import com.liferay.data.engine.service.DEDataLayoutSaveRequest;
import com.liferay.data.engine.service.DEDataLayoutSaveResponse;
import com.liferay.data.engine.service.DEDataLayoutService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jeyvison Nascimento
 */
@Component(immediate = true, service = DEDataLayoutService.class)
public class DEDataLayoutServiceImpl implements DEDataLayoutService {

	@Activate
	public void activate() {
		_deDataEngineRequestExecutor = new DEDataEngineRequestExecutor(
			ddmStructureVersionLocalService,
			deDataDefinitionDeserializerTracker,
			deDataLayoutDeserializerTracker, deDataStorageTracker);

		_deDataLayoutGetRequestExecutor = new DEDataLayoutGetRequestExecutor(
			ddmStructureLayoutLocalService, _deDataEngineRequestExecutor);

		_deDataLayoutSaveRequestExecutor = new DEDataLayoutSaveRequestExecutor(
			ddmStructureLayoutLocalService, ddmStructureVersionLocalService,
			ddmStructureLocalService, deDataLayoutSerializerTracker);
	}

	@Override
	public DEDataLayoutGetResponse execute(
			DEDataLayoutGetRequest deDataLayoutGetRequest)
		throws DEDataLayoutException {

		return _deDataLayoutGetRequestExecutor.execute(deDataLayoutGetRequest);
	}

	@Override
	public DEDataLayoutSaveResponse execute(
			DEDataLayoutSaveRequest deDataLayoutSaveRequest)
		throws DEDataLayoutException {

		return _deDataLayoutSaveRequestExecutor.execute(
			deDataLayoutSaveRequest);
	}

	@Reference
	protected DDMStructureLayoutLocalService ddmStructureLayoutLocalService;

	@Reference
	protected DDMStructureLocalService ddmStructureLocalService;

	@Reference
	protected DDMStructureVersionLocalService ddmStructureVersionLocalService;

	@Reference
	protected DEDataDefinitionDeserializerTracker
		deDataDefinitionDeserializerTracker;

	@Reference
	protected DEDataLayoutDeserializerTracker deDataLayoutDeserializerTracker;

	@Reference
	protected DEDataLayoutSerializerTracker deDataLayoutSerializerTracker;

	@Reference
	protected DEDataStorageTracker deDataStorageTracker;

	private DEDataEngineRequestExecutor _deDataEngineRequestExecutor;
	private DEDataLayoutGetRequestExecutor _deDataLayoutGetRequestExecutor;
	private DEDataLayoutSaveRequestExecutor _deDataLayoutSaveRequestExecutor;

}
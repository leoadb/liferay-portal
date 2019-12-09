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

package com.liferay.data.engine.internal.adapter;

import com.liferay.data.engine.adapter.DataEngineAdapter;
import com.liferay.data.engine.adapter.data.definition.DataDefinitionRequest;
import com.liferay.data.engine.adapter.data.definition.DataDefinitionRequestExecutor;
import com.liferay.data.engine.adapter.data.definition.DataDefinitionResponse;
import com.liferay.data.engine.adapter.data.record.collection.DataRecordCollectionExecutor;
import com.liferay.data.engine.adapter.data.record.collection.DataRecordCollectionRequest;
import com.liferay.data.engine.adapter.data.record.collection.DataRecordCollectionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DataEngineAdapter.class)
public class DataEngineAdapterImpl implements DataEngineAdapter {

	@Override
	public <T extends DataDefinitionResponse> T execute(
		DataDefinitionRequest<T> dataDefinitionRequest) {

		return dataDefinitionRequest.accept(_dataDefinitionRequestExecutor);
	}

	@Override
	public <T extends DataRecordCollectionResponse> T execute(
		DataRecordCollectionRequest<T> dataRecordCollectionRequest) {

		return dataRecordCollectionRequest.accept(
			_dataRecordCollectionExecutor);
	}

	@Reference
	private DataDefinitionRequestExecutor _dataDefinitionRequestExecutor;

	@Reference
	private DataRecordCollectionExecutor _dataRecordCollectionExecutor;

}
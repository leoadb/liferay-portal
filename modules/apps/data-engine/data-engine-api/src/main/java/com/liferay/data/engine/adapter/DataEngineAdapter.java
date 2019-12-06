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

package com.liferay.data.engine.adapter;

import com.liferay.data.engine.adapter.data.definition.DataDefinitionRequest;
import com.liferay.data.engine.adapter.data.definition.DataDefinitionResponse;
import com.liferay.data.engine.adapter.data.record.collection.DataRecordCollectionRequest;
import com.liferay.data.engine.adapter.data.record.collection.DataRecordCollectionResponse;
import com.liferay.data.engine.exception.DataEngineException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;

/**
 * @author Leonardo Barros
 */
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {DataEngineException.class, SystemException.class}
)
public interface DataEngineAdapter {

	public <T extends DataDefinitionResponse> T execute(
		DataDefinitionRequest<T> dataDefinitionRequest);

	public <T extends DataRecordCollectionResponse> T execute(
		DataRecordCollectionRequest<T> dataRecordCollectionRequest);

}
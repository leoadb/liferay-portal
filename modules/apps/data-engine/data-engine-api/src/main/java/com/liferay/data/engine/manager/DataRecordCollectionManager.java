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

package com.liferay.data.engine.manager;

import com.liferay.data.engine.DataRecordCollection;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Leonardo Barros
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor = Exception.class)
public interface DataRecordCollectionManager {

	public DataRecordCollection addDataRecordCollection(
			DataRecordCollection dataRecordCollection)
		throws Exception;

	public void deleteDataRecordCollection(long dataRecordCollectionId)
		throws Exception;

	public DataRecordCollection fetchDataRecordCollection(
		long dataRecordCollectionId);

	public DataRecordCollection fetchDataRecordCollection(
		String dataRecordCollectionKey, long siteId);

	public DataRecordCollection getDataRecordCollection(
			long dataRecordCollectionId)
		throws Exception;

	public DataRecordCollection getDataRecordCollection(
			String dataRecordCollectionKey, long siteId)
		throws Exception;

	public List<DataRecordCollection> getDataRecordCollections(
			long dataDefinitionId, int end, String keywords, int start)
		throws Exception;

	public int getDataRecordCollectionsCount(
			long dataDefinitionId, String keywords)
		throws Exception;

	public DataRecordCollection getDefaultDataRecordCollectionDataDefinition(
			long dataDefinitionId)
		throws Exception;

	public DataRecordCollection updateDataRecordCollection(
			DataRecordCollection dataRecordCollection)
		throws Exception;

}
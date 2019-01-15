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

package com.liferay.data.engine.web.internal.servlet.data.fetcher;

import com.liferay.data.engine.exception.DEDataRecordCollectionException;
import com.liferay.data.engine.service.DEDataRecordCollectionRequestBuilder;
import com.liferay.data.engine.service.DEDataRecordCollectionService;
import com.liferay.data.engine.web.internal.graphql.model.DataRecordCollection;
import com.liferay.data.engine.web.internal.graphql.model.DataRecordCollectionType;
import com.liferay.data.engine.web.internal.graphql.model.DeleteDataRecordCollectionType;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, service = DEDeleteDataRecordCollectionDataFetcher.class
)
public class DEDeleteDataRecordCollectionDataFetcher
	extends DEBaseDataFetcher
	implements DataFetcher<DeleteDataRecordCollectionType> {

	@Override
	public DeleteDataRecordCollectionType get(
		DataFetchingEnvironment dataFetchingEnvironment) {

		DeleteDataRecordCollectionType deleteDataRecordCollectionType =
			new DeleteDataRecordCollectionType();

		String errorMessage = null;
		String languageId = dataFetchingEnvironment.getArgument("languageId");

		try {
			long dataRecordCollectionId = GetterUtil.getLong(
				dataFetchingEnvironment.getArgument("dataRecordCollectionId"));

			deDataRecordCollectionService.execute(
				DEDataRecordCollectionRequestBuilder.deleteBuilder(
				).byId(
					dataRecordCollectionId
				).build());

			DataRecordCollection dataRecordCollection =
				new DataRecordCollectionType();

			dataRecordCollection.setDataRecordCollectionId(
				String.valueOf(dataRecordCollectionId));

			deleteDataRecordCollectionType.setDataRecordCollection(
				dataRecordCollection);
		}
		catch (DEDataRecordCollectionException.MustHavePermission mhp) {
			errorMessage = getMessage(
				languageId, "the-user-must-have-permission",
				getActionMessage(languageId, mhp.getActionId()));
		}
		catch (
			DEDataRecordCollectionException.NoSuchDataRecordCollection nsdrc) {

			errorMessage = getMessage(
				languageId, "no-data-record-collection-exists-with-id-x",
				nsdrc.getDEDataRecordCollectionId());
		}
		catch (Exception e) {
			errorMessage = getMessage(
				languageId, "unable-to-delete-data-record-collection");
		}

		if (errorMessage != null) {
			handleErrorMessage(errorMessage);
		}

		return deleteDataRecordCollectionType;
	}

	@Override
	protected Portal getPortal() {
		return portal;
	}

	@Reference
	protected DEDataRecordCollectionService deDataRecordCollectionService;

	@Reference
	protected Portal portal;

}
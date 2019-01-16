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
import com.liferay.data.engine.service.DEDataRecordCollectionGetResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionRequestBuilder;
import com.liferay.data.engine.service.DEDataRecordCollectionService;
import com.liferay.data.engine.web.internal.graphql.model.DataRecordCollection;
import com.liferay.data.engine.web.internal.graphql.model.GetDataRecordCollectionType;
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
	immediate = true, service = DEGetDataRecordCollectionDataFetcher.class
)
public class DEGetDataRecordCollectionDataFetcher
	extends DEBaseDataRecordCollectionDataFetcher
	implements DataFetcher<GetDataRecordCollectionType> {

	@Override
	public GetDataRecordCollectionType get(
		DataFetchingEnvironment dataFetchingEnvironment) {

		GetDataRecordCollectionType getDataRecordCollectionType =
			new GetDataRecordCollectionType();

		String errorMessage = null;
		String languageId = dataFetchingEnvironment.getArgument("languageId");

		try {
			long dataRecordCollectionId = GetterUtil.getLong(
				dataFetchingEnvironment.getArgument("dataRecordCollectionId"));

			DEDataRecordCollectionGetResponse deDataRecordCollectionGetResponse =
				deDataRecordCollectionService.execute(
					DEDataRecordCollectionRequestBuilder.getBuilder(
					).byId(
						dataRecordCollectionId
					).build());

			DataRecordCollection dataRecordCollection =
				createDataRecordCollection(
					deDataRecordCollectionGetResponse.
						getDEDataRecordCollection(),
					deSaveDataDefinitionDataFetcher);

			getDataRecordCollectionType.setDataRecordCollection(
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
				languageId, "unable-to-retrive-data-record-collection");
		}

		if (errorMessage != null) {
			handleErrorMessage(errorMessage);
		}

		return getDataRecordCollectionType;
	}

	@Override
	public Portal getPortal() {
		return portal;
	}

	@Reference
	protected DEDataRecordCollectionService deDataRecordCollectionService;

	@Reference
	protected DESaveDataDefinitionDataFetcher deSaveDataDefinitionDataFetcher;

	@Reference
	protected Portal portal;

}
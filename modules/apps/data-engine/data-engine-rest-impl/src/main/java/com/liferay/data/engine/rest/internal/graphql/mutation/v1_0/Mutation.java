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

package com.liferay.data.engine.rest.internal.graphql.mutation.v1_0;

import com.liferay.data.engine.rest.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v1_0.DataLayout;
import com.liferay.data.engine.rest.dto.v1_0.DataRecord;
import com.liferay.data.engine.rest.dto.v1_0.DataRecordCollection;
import com.liferay.data.engine.rest.resource.v1_0.DataDefinitionResource;
import com.liferay.data.engine.rest.resource.v1_0.DataLayoutResource;
import com.liferay.data.engine.rest.resource.v1_0.DataRecordCollectionResource;
import com.liferay.data.engine.rest.resource.v1_0.DataRecordResource;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class Mutation {

	@GraphQLField
	@GraphQLInvokeDetached
	public DataDefinition postContentSpaceDataDefinition(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("DataDefinition") DataDefinition dataDefinition)
		throws Exception {

		DataDefinitionResource dataDefinitionResource =
			_createDataDefinitionResource();

		return dataDefinitionResource.postContentSpaceDataDefinition(
			contentSpaceId, dataDefinition);
	}

	@GraphQLInvokeDetached
	public boolean deleteDataDefinition(
			@GraphQLName("data-definition-id") Long dataDefinitionId)
		throws Exception {

		DataDefinitionResource dataDefinitionResource =
			_createDataDefinitionResource();

		return dataDefinitionResource.deleteDataDefinition(dataDefinitionId);
	}

	@GraphQLInvokeDetached
	public DataDefinition putDataDefinition(
			@GraphQLName("data-definition-id") Long dataDefinitionId,
			@GraphQLName("DataDefinition") DataDefinition dataDefinition)
		throws Exception {

		DataDefinitionResource dataDefinitionResource =
			_createDataDefinitionResource();

		return dataDefinitionResource.putDataDefinition(
			dataDefinitionId, dataDefinition);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DataLayout postDataDefinitionDataLayout(
			@GraphQLName("data-definition-id") Long dataDefinitionId,
			@GraphQLName("DataLayout") DataLayout dataLayout)
		throws Exception {

		DataLayoutResource dataLayoutResource = _createDataLayoutResource();

		return dataLayoutResource.postDataDefinitionDataLayout(
			dataDefinitionId, dataLayout);
	}

	@GraphQLInvokeDetached
	public boolean deleteDataLayout(
			@GraphQLName("data-layout-id") Long dataLayoutId)
		throws Exception {

		DataLayoutResource dataLayoutResource = _createDataLayoutResource();

		return dataLayoutResource.deleteDataLayout(dataLayoutId);
	}

	@GraphQLInvokeDetached
	public DataLayout putDataLayout(
			@GraphQLName("data-layout-id") Long dataLayoutId,
			@GraphQLName("DataLayout") DataLayout dataLayout)
		throws Exception {

		DataLayoutResource dataLayoutResource = _createDataLayoutResource();

		return dataLayoutResource.putDataLayout(dataLayoutId, dataLayout);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DataRecord postDataRecordCollectionDataRecord(
			@GraphQLName("data-record-collection-id") Long
				dataRecordCollectionId,
			@GraphQLName("DataRecord") DataRecord dataRecord)
		throws Exception {

		DataRecordResource dataRecordResource = _createDataRecordResource();

		return dataRecordResource.postDataRecordCollectionDataRecord(
			dataRecordCollectionId, dataRecord);
	}

	@GraphQLInvokeDetached
	public boolean deleteDataRecord(
			@GraphQLName("data-record-id") Long dataRecordId)
		throws Exception {

		DataRecordResource dataRecordResource = _createDataRecordResource();

		return dataRecordResource.deleteDataRecord(dataRecordId);
	}

	@GraphQLInvokeDetached
	public DataRecord putDataRecord(
			@GraphQLName("data-record-id") Long dataRecordId,
			@GraphQLName("DataRecord") DataRecord dataRecord)
		throws Exception {

		DataRecordResource dataRecordResource = _createDataRecordResource();

		return dataRecordResource.putDataRecord(dataRecordId, dataRecord);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DataRecordCollection postDataDefinitionDataRecordCollection(
			@GraphQLName("data-definition-id") Long dataDefinitionId,
			@GraphQLName("DataRecordCollection") DataRecordCollection
				dataRecordCollection)
		throws Exception {

		DataRecordCollectionResource dataRecordCollectionResource =
			_createDataRecordCollectionResource();

		return dataRecordCollectionResource.
			postDataDefinitionDataRecordCollection(
				dataDefinitionId, dataRecordCollection);
	}

	@GraphQLInvokeDetached
	public boolean deleteDataRecordCollection(
			@GraphQLName("data-record-collection-id") Long
				dataRecordCollectionId)
		throws Exception {

		DataRecordCollectionResource dataRecordCollectionResource =
			_createDataRecordCollectionResource();

		return dataRecordCollectionResource.deleteDataRecordCollection(
			dataRecordCollectionId);
	}

	@GraphQLInvokeDetached
	public DataRecordCollection putDataRecordCollection(
			@GraphQLName("data-record-collection-id") Long
				dataRecordCollectionId,
			@GraphQLName("DataRecordCollection") DataRecordCollection
				dataRecordCollection)
		throws Exception {

		DataRecordCollectionResource dataRecordCollectionResource =
			_createDataRecordCollectionResource();

		return dataRecordCollectionResource.putDataRecordCollection(
			dataRecordCollectionId, dataRecordCollection);
	}

	private static DataDefinitionResource _createDataDefinitionResource()
		throws Exception {

		DataDefinitionResource dataDefinitionResource =
			_dataDefinitionResourceServiceTracker.getService();

		dataDefinitionResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return dataDefinitionResource;
	}

	private static final ServiceTracker
		<DataDefinitionResource, DataDefinitionResource>
			_dataDefinitionResourceServiceTracker;

	private static DataLayoutResource _createDataLayoutResource()
		throws Exception {

		DataLayoutResource dataLayoutResource =
			_dataLayoutResourceServiceTracker.getService();

		dataLayoutResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return dataLayoutResource;
	}

	private static final ServiceTracker<DataLayoutResource, DataLayoutResource>
		_dataLayoutResourceServiceTracker;

	private static DataRecordResource _createDataRecordResource()
		throws Exception {

		DataRecordResource dataRecordResource =
			_dataRecordResourceServiceTracker.getService();

		dataRecordResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return dataRecordResource;
	}

	private static final ServiceTracker<DataRecordResource, DataRecordResource>
		_dataRecordResourceServiceTracker;

	private static DataRecordCollectionResource
			_createDataRecordCollectionResource()
		throws Exception {

		DataRecordCollectionResource dataRecordCollectionResource =
			_dataRecordCollectionResourceServiceTracker.getService();

		dataRecordCollectionResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return dataRecordCollectionResource;
	}

	private static final ServiceTracker
		<DataRecordCollectionResource, DataRecordCollectionResource>
			_dataRecordCollectionResourceServiceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(Mutation.class);

		ServiceTracker<DataDefinitionResource, DataDefinitionResource>
			dataDefinitionResourceServiceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), DataDefinitionResource.class, null);

		dataDefinitionResourceServiceTracker.open();

		_dataDefinitionResourceServiceTracker =
			dataDefinitionResourceServiceTracker;
		ServiceTracker<DataLayoutResource, DataLayoutResource>
			dataLayoutResourceServiceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), DataLayoutResource.class, null);

		dataLayoutResourceServiceTracker.open();

		_dataLayoutResourceServiceTracker = dataLayoutResourceServiceTracker;
		ServiceTracker<DataRecordResource, DataRecordResource>
			dataRecordResourceServiceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), DataRecordResource.class, null);

		dataRecordResourceServiceTracker.open();

		_dataRecordResourceServiceTracker = dataRecordResourceServiceTracker;
		ServiceTracker
			<DataRecordCollectionResource, DataRecordCollectionResource>
				dataRecordCollectionResourceServiceTracker =
					new ServiceTracker<>(
						bundle.getBundleContext(),
						DataRecordCollectionResource.class, null);

		dataRecordCollectionResourceServiceTracker.open();

		_dataRecordCollectionResourceServiceTracker =
			dataRecordCollectionResourceServiceTracker;
	}

}
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

import com.liferay.data.engine.exception.DEDataRecordCollectionException;

/**
 * @author Leonardo Barros
 */
public interface DEDataRecordCollectionService {

	public DEDataRecordCollectionDeleteModelPermissionsResponse execute(
			DEDataRecordCollectionDeleteModelPermissionsRequest
				deDataRecordCollectionDeleteModelPermissionsRequest)
		throws DEDataRecordCollectionException;

	public DEDataRecordCollectionDeletePermissionsResponse execute(
			DEDataRecordCollectionDeletePermissionsRequest
				deDataRecordCollectionDeletePermissionsRequest)
		throws DEDataRecordCollectionException;

	public DEDataRecordCollectionDeleteResponse execute(
			DEDataRecordCollectionDeleteRequest
				deDataRecordCollectionDeleteRequest)
		throws DEDataRecordCollectionException;

	public DEDataRecordCollectionGetResponse execute(
			DEDataRecordCollectionGetRequest deDataRecordCollectionGetRequest)
		throws DEDataRecordCollectionException;

	public DEDataRecordCollectionSaveModelPermissionsResponse execute(
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest)
		throws DEDataRecordCollectionException;

	public DEDataRecordCollectionSavePermissionsResponse execute(
			DEDataRecordCollectionSavePermissionsRequest
				deDataRecordCollectionSavePermissionsRequest)
		throws DEDataRecordCollectionException;

	public DEDataRecordCollectionSaveRecordResponse execute(
			DEDataRecordCollectionSaveRecordRequest
				deDataRecordCollectionSaveRecordRequest)
		throws DEDataRecordCollectionException;

	public DEDataRecordCollectionSaveResponse execute(
			DEDataRecordCollectionSaveRequest deDataRecordCollectionSaveRequest)
		throws DEDataRecordCollectionException;

}
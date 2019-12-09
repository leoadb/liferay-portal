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

package com.liferay.data.engine.exception;

import com.liferay.data.engine.adapter.DataEngineError;

import java.util.List;

/**
 * @author Leonardo Barros
 */
public class DataEngineException extends RuntimeException {

	public DataEngineException(
		List<DataEngineError> dataEngineErrors, Throwable cause) {

		super(cause);

		_dataEngineErrors = dataEngineErrors;
	}

	public List<DataEngineError> getDataEngineErrors() {
		return _dataEngineErrors;
	}

	private final List<DataEngineError> _dataEngineErrors;

}
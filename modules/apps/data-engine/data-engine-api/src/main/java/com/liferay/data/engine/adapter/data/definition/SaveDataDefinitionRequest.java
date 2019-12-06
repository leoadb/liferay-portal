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

package com.liferay.data.engine.adapter.data.definition;

import com.liferay.data.engine.adapter.BaseDataEngineRequest;
import com.liferay.data.engine.model.DEDataDefinition;

/**
 * @author Leonardo Barros
 */
public class SaveDataDefinitionRequest
	extends BaseDataEngineRequest
	implements DataDefinitionRequest<SaveDataDefinitionResponse> {

	@Override
	public SaveDataDefinitionResponse accept(
		DataDefinitionRequestExecutor dataDefinitionRequestExecutor) {

		return dataDefinitionRequestExecutor.execute(this);
	}

	public DEDataDefinition getDEDataDefinition() {
		return _deDataDefinition;
	}

	public void setDEDataDefinition(DEDataDefinition deDataDefinition) {
		_deDataDefinition = deDataDefinition;
	}

	private DEDataDefinition _deDataDefinition;

}
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

package com.liferay.dynamic.data.mapping.io.exporter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public interface DDMFormInstanceRecordWriter {

	public default List<String> getDistinctLabels(
		Map<String, String> ddmFormFieldsLabel) {

		List<String> labels = new ArrayList<>();

		for (String label : ddmFormFieldsLabel.values()) {
			if (!labels.contains(label)) {
				labels.add(label);
			}
		}

		return labels;
	}

	public DDMFormInstanceRecordWriterResponse write(
			DDMFormInstanceRecordWriterRequest
				ddmFormInstanceRecordWriterRequest)
		throws Exception;

}
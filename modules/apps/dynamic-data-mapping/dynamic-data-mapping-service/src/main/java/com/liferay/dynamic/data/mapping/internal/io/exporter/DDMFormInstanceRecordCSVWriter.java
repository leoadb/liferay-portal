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

package com.liferay.dynamic.data.mapping.internal.io.exporter;

import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriter;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterRequest;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterResponse;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.CSVUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "ddm.form.instance.record.writer.type=csv",
	service = DDMFormInstanceRecordWriter.class
)
public class DDMFormInstanceRecordCSVWriter
	implements DDMFormInstanceRecordWriter {

	@Override
	public DDMFormInstanceRecordWriterResponse write(
			DDMFormInstanceRecordWriterRequest
				ddmFormInstanceRecordWriterRequest)
		throws Exception {

		StringBundler sb = new StringBundler(3);

		List<String> labels = new ArrayList<>();

		Map<String, String> ddmFormFieldsLabel =
			ddmFormInstanceRecordWriterRequest.getDDMFormFieldsLabel();

		_writeLabels(ddmFormFieldsLabel, labels, sb);

		_writeRecords(
			ddmFormFieldsLabel,
			ddmFormInstanceRecordWriterRequest.getDDMFormFieldValues(), labels,
			sb);

		String csv = sb.toString();

		DDMFormInstanceRecordWriterResponse.Builder builder =
			DDMFormInstanceRecordWriterResponse.Builder.newBuilder(
				csv.getBytes());

		return builder.build();
	}

	private void _writeLabels(
		Map<String, String> ddmFormFieldsLabel, List<String> labels,
		StringBundler sb) {

		for (String label : ddmFormFieldsLabel.values()) {
			if (labels.contains(label)) {
				continue;
			}

			sb.append(CSVUtil.encode(label));

			sb.append(StringPool.COMMA);

			labels.add(label);
		}

		sb.append(StringPool.NEW_LINE);
	}

	private void _writeRecord(
		Map<String, String> ddmFormFieldsLabel,
		Map<String, String> ddmFormFieldValue, List<String> labels,
		StringBundler sb) {

		for (String label : labels) {
			for (Map.Entry<String, String> ddmFormFieldLabel :
					ddmFormFieldsLabel.entrySet()) {

				if (Objects.equals(ddmFormFieldLabel.getValue(), label) &&
					ddmFormFieldValue.containsKey(ddmFormFieldLabel.getKey())) {

					sb.append(
						CSVUtil.encode(
							ddmFormFieldValue.get(ddmFormFieldLabel.getKey())));
				}
			}

			sb.append(StringPool.COMMA);
		}
	}

	private void _writeRecords(
		Map<String, String> ddmFormFieldsLabel,
		List<Map<String, String>> ddmFormFieldValues, List<String> labels,
		StringBundler sb) {

		for (Map<String, String> ddmFormFieldValue : ddmFormFieldValues) {
			_writeRecord(ddmFormFieldsLabel, ddmFormFieldValue, labels, sb);

			sb.append(StringPool.NEW_LINE);
		}
	}

}
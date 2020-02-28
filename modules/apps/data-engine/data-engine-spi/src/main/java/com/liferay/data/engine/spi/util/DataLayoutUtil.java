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

package com.liferay.data.engine.spi.util;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.spi.model.SPIDataLayout;
import com.liferay.data.engine.spi.model.SPIDataLayoutColumn;
import com.liferay.data.engine.spi.model.SPIDataLayoutPage;
import com.liferay.data.engine.spi.model.SPIDataLayoutRow;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jeyvison Nascimento
 */
public class DataLayoutUtil {

	public static String serialize(
		DDMFormLayoutSerializer ddmFormLayoutSerializer,
		SPIDataLayout spiDataLayout) {

		DDMFormLayoutSerializerSerializeRequest.Builder builder =
			DDMFormLayoutSerializerSerializeRequest.Builder.newBuilder(
				toDDMFormLayout(spiDataLayout));

		DDMFormLayoutSerializerSerializeResponse
			ddmFormLayoutSerializerSerializeResponse =
				ddmFormLayoutSerializer.serialize(builder.build());

		return ddmFormLayoutSerializerSerializeResponse.getContent();
	}

	public static DDMFormLayout toDDMFormLayout(SPIDataLayout dataLayout) {
		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout.setDDMFormLayoutPages(
			_toDDMFormLayoutPages(dataLayout.getSPIDataLayoutPages()));
		ddmFormLayout.setPaginationMode(dataLayout.getPaginationMode());

		return ddmFormLayout;
	}

	public static SPIDataLayout toSPIDataLayout(DDMFormLayout ddmFormLayout) {
		return new SPIDataLayout() {
			{
				setPaginationMode(ddmFormLayout.getPaginationMode());
				setSPIDataLayoutPages(
					_toSPIDataLayoutPages(
						ddmFormLayout.getDDMFormLayoutPages()));
			}
		};
	}

	public static SPIDataLayout toSPIDataLayout(
			DDMStructureLayout ddmStructureLayout)
		throws Exception {

		if (ddmStructureLayout == null) {
			return null;
		}

		SPIDataLayout spiDataLayout = toSPIDataLayout(
			ddmStructureLayout.getDDMFormLayout());

		spiDataLayout.setDateCreated(ddmStructureLayout.getCreateDate());
		spiDataLayout.setDataDefinitionId(
			ddmStructureLayout.getDDMStructureId());
		spiDataLayout.setDataLayoutKey(
			ddmStructureLayout.getStructureLayoutKey());
		spiDataLayout.setDateModified(ddmStructureLayout.getModifiedDate());
		spiDataLayout.setDescription(
			LocalizedValueUtil.toStringObjectMap(
				ddmStructureLayout.getDescriptionMap()));
		spiDataLayout.setId(ddmStructureLayout.getStructureLayoutId());
		spiDataLayout.setName(
			LocalizedValueUtil.toStringObjectMap(
				ddmStructureLayout.getNameMap()));
		spiDataLayout.setSiteId(ddmStructureLayout.getGroupId());
		spiDataLayout.setUserId(ddmStructureLayout.getUserId());

		return spiDataLayout;
	}

	private static SPIDataLayoutColumn _toDataLayoutColumn(
		DDMFormLayoutColumn ddmFormLayoutColumn) {

		return new SPIDataLayoutColumn() {
			{
				setColumnSize(ddmFormLayoutColumn.getSize());
				setFieldNames(
					ArrayUtil.toStringArray(
						ddmFormLayoutColumn.getDDMFormFieldNames()));
			}
		};
	}

	private static SPIDataLayoutColumn[] _toDataLayoutColumns(
		List<DDMFormLayoutColumn> ddmFormLayoutColumns) {

		if (ListUtil.isEmpty(ddmFormLayoutColumns)) {
			return new SPIDataLayoutColumn[0];
		}

		Stream<DDMFormLayoutColumn> stream = ddmFormLayoutColumns.stream();

		return stream.map(
			DataLayoutUtil::_toDataLayoutColumn
		).collect(
			Collectors.toList()
		).toArray(
			new SPIDataLayoutColumn[0]
		);
	}

	private static SPIDataLayoutPage _toDataLayoutPage(
		DDMFormLayoutPage ddmFormLayoutPage) {

		return new SPIDataLayoutPage() {
			{
				setDescription(
					LocalizedValueUtil.toLocalizedValuesMap(
						ddmFormLayoutPage.getDescription()));
				setSPIDataLayoutRows(
					_toDataLayoutRows(
						ddmFormLayoutPage.getDDMFormLayoutRows()));
				setTitle(
					LocalizedValueUtil.toLocalizedValuesMap(
						ddmFormLayoutPage.getTitle()));
			}
		};
	}

	private static SPIDataLayoutRow _toDataLayoutRow(
		DDMFormLayoutRow ddmFormLayoutRow) {

		return new SPIDataLayoutRow() {
			{
				setSPIDataLayoutColumns(
					_toDataLayoutColumns(
						ddmFormLayoutRow.getDDMFormLayoutColumns()));
			}
		};
	}

	private static SPIDataLayoutRow[] _toDataLayoutRows(
		List<DDMFormLayoutRow> ddmFormLayoutRows) {

		Stream<DDMFormLayoutRow> stream = ddmFormLayoutRows.stream();

		return stream.map(
			DataLayoutUtil::_toDataLayoutRow
		).collect(
			Collectors.toList()
		).toArray(
			new SPIDataLayoutRow[0]
		);
	}

	private static DDMFormLayoutColumn _toDDMFormLayoutColumn(
		SPIDataLayoutColumn spiDataLayoutColumn) {

		DDMFormLayoutColumn ddmFormLayoutColumn = new DDMFormLayoutColumn();

		ddmFormLayoutColumn.setDDMFormFieldNames(
			Arrays.asList(spiDataLayoutColumn.getFieldNames()));
		ddmFormLayoutColumn.setSize(spiDataLayoutColumn.getColumnSize());

		return ddmFormLayoutColumn;
	}

	private static List<DDMFormLayoutColumn> _toDDMFormLayoutColumns(
		SPIDataLayoutColumn[] spiDataLayoutColumns) {

		if (ArrayUtil.isEmpty(spiDataLayoutColumns)) {
			return Collections.emptyList();
		}

		return Stream.of(
			spiDataLayoutColumns
		).map(
			DataLayoutUtil::_toDDMFormLayoutColumn
		).collect(
			Collectors.toList()
		);
	}

	private static DDMFormLayoutPage _toDDMFormLayoutPage(
		SPIDataLayoutPage spiDataLayoutPage) {

		DDMFormLayoutPage ddmFormLayoutPage = new DDMFormLayoutPage();

		ddmFormLayoutPage.setDDMFormLayoutRows(
			_toDDMFormLayoutRows(spiDataLayoutPage.getSPIDataLayoutRows()));
		ddmFormLayoutPage.setDescription(
			LocalizedValueUtil.toLocalizedValue(
				spiDataLayoutPage.getDescription()));
		ddmFormLayoutPage.setTitle(
			LocalizedValueUtil.toLocalizedValue(spiDataLayoutPage.getTitle()));

		return ddmFormLayoutPage;
	}

	private static List<DDMFormLayoutPage> _toDDMFormLayoutPages(
		SPIDataLayoutPage[] spiDataLayoutPages) {

		if (ArrayUtil.isEmpty(spiDataLayoutPages)) {
			return Collections.emptyList();
		}

		return Stream.of(
			spiDataLayoutPages
		).map(
			DataLayoutUtil::_toDDMFormLayoutPage
		).collect(
			Collectors.toList()
		);
	}

	private static DDMFormLayoutRow _toDDMFormLayoutRow(
		SPIDataLayoutRow spiDataLayoutRow) {

		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		ddmFormLayoutRow.setDDMFormLayoutColumns(
			_toDDMFormLayoutColumns(
				spiDataLayoutRow.getSPIDataLayoutColumns()));

		return ddmFormLayoutRow;
	}

	private static List<DDMFormLayoutRow> _toDDMFormLayoutRows(
		SPIDataLayoutRow[] spiDataLayoutRows) {

		if (ArrayUtil.isEmpty(spiDataLayoutRows)) {
			return Collections.emptyList();
		}

		return Stream.of(
			spiDataLayoutRows
		).map(
			DataLayoutUtil::_toDDMFormLayoutRow
		).collect(
			Collectors.toList()
		);
	}

	private static SPIDataLayoutPage[] _toSPIDataLayoutPages(
		List<DDMFormLayoutPage> ddmFormLayoutPages) {

		if (ListUtil.isEmpty(ddmFormLayoutPages)) {
			return new SPIDataLayoutPage[0];
		}

		Stream<DDMFormLayoutPage> stream = ddmFormLayoutPages.stream();

		return stream.map(
			DataLayoutUtil::_toDataLayoutPage
		).collect(
			Collectors.toList()
		).toArray(
			new SPIDataLayoutPage[0]
		);
	}

}
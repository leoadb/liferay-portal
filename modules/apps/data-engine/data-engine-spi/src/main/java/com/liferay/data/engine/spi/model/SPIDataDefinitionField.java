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

package com.liferay.data.engine.spi.model;

import java.util.Map;
import java.util.Objects;

/**
 * @author Jeyvison Nascimento
 */
public class SPIDataDefinitionField {

	public Map<String, Object> getCustomProperties() {
		return _customProperties;
	}

	public Map<String, Object> getDefaultValue() {
		return _defaultValue;
	}

	public String getFieldType() {
		return _fieldType;
	}

	public Long getId() {
		return _id;
	}

	public Boolean getIndexable() {
		return _indexable;
	}

	public IndexType getIndexType() {
		return _indexType;
	}

	public String getIndexTypeAsString() {
		if (_indexType == null) {
			return null;
		}

		return _indexType.toString();
	}

	public Map<String, Object> getLabel() {
		return _label;
	}

	public Boolean getLocalizable() {
		return _localizable;
	}

	public String getName() {
		return _name;
	}

	public Boolean getReadOnly() {
		return _readOnly;
	}

	public Boolean getRepeatable() {
		return _repeatable;
	}

	public Boolean getRequired() {
		return _required;
	}

	public Boolean getShowLabel() {
		return _showLabel;
	}

	public SPIDataDefinitionField[] getSPINestedDataDefinitionFields() {
		return _nestedSPIDataDefinitionFields;
	}

	public Map<String, Object> getTip() {
		return _tip;
	}

	public Boolean getVisible() {
		return _visible;
	}

	public void setCustomProperties(Map<String, Object> customProperties) {
		_customProperties = customProperties;
	}

	public void setDefaultValue(Map<String, Object> defaultValue) {
		_defaultValue = defaultValue;
	}

	public void setFieldType(String fieldType) {
		_fieldType = fieldType;
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setIndexable(Boolean indexable) {
		_indexable = indexable;
	}

	public void setIndexType(IndexType indexType) {
		_indexType = indexType;
	}

	public void setLabel(Map<String, Object> label) {
		_label = label;
	}

	public void setLocalizable(Boolean localizable) {
		_localizable = localizable;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setReadOnly(Boolean readOnly) {
		_readOnly = readOnly;
	}

	public void setRepeatable(Boolean repeatable) {
		_repeatable = repeatable;
	}

	public void setRequired(Boolean required) {
		_required = required;
	}

	public void setShowLabel(Boolean showLabel) {
		_showLabel = showLabel;
	}

	public void setSPINestedDataDefinitionFields(
		SPIDataDefinitionField[] nestedSPIDataDefinitionFields) {

		_nestedSPIDataDefinitionFields = nestedSPIDataDefinitionFields;
	}

	public void setTip(Map<String, Object> tip) {
		_tip = tip;
	}

	public void setVisible(Boolean visible) {
		_visible = visible;
	}

	public static enum IndexType {

		ALL("all"), KEYWORD("keyword"), NONE("none"), TEXT("text");

		public static IndexType create(String value) {
			for (IndexType indexType : values()) {
				if (Objects.equals(indexType.getValue(), value)) {
					return indexType;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private IndexType(String value) {
			_value = value;
		}

		private final String _value;

	}

	private Map<String, Object> _customProperties;
	private Map<String, Object> _defaultValue;
	private String _fieldType;
	private Long _id;
	private Boolean _indexable;
	private IndexType _indexType;
	private Map<String, Object> _label;
	private Boolean _localizable;
	private String _name;
	private SPIDataDefinitionField[] _nestedSPIDataDefinitionFields;
	private Boolean _readOnly;
	private Boolean _repeatable;
	private Boolean _required;
	private Boolean _showLabel;
	private Map<String, Object> _tip;
	private Boolean _visible;

}
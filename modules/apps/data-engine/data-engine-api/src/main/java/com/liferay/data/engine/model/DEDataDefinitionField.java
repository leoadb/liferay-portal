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

package com.liferay.data.engine.model;

import com.liferay.portal.kernel.util.ListUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Leonardo Barros
 */
public class DEDataDefinitionField implements Serializable {

	public static Builder newBuilder(String fieldType, String name) {
		return new Builder(fieldType, name);
	}

	public Map<String, Object> getCustomProperties() {
		return _customProperties;
	}

	public String getFieldType() {
		return _fieldType;
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

	public String getName() {
		return _name;
	}

	public List<DEDataDefinitionField> getNestedDEDataDefinitionFields() {
		return _nestedDEDataDefinitionFields;
	}

	public Map<String, Object> getPredefinedValue() {
		return _predefinedValue;
	}

	public Map<String, Object> getTip() {
		return _tip;
	}

	public boolean isIndexable() {
		return _indexable;
	}

	public boolean isLocalizable() {
		return _localizable;
	}

	public boolean isReadOnly() {
		return _readOnly;
	}

	public boolean isRepeatable() {
		return _repeatable;
	}

	public boolean isRequired() {
		return _required;
	}

	public boolean isShowLabel() {
		return _showLabel;
	}

	public static class Builder {

		public DEDataDefinitionField build() {
			return _deDataDefinitionField;
		}

		public Builder customProperty(String name, Object value) {
			_deDataDefinitionField._customProperties.put(name, value);

			return this;
		}

		public Builder indexable(boolean indexable) {
			_deDataDefinitionField._indexable = indexable;

			return this;
		}

		public Builder indexType(IndexType indexType) {
			_deDataDefinitionField._indexType = indexType;

			return this;
		}

		public Builder label(Map<String, Object> label) {
			_deDataDefinitionField._label = label;

			return this;
		}

		public Builder localizable(boolean localizable) {
			_deDataDefinitionField._localizable = localizable;

			return this;
		}

		public Builder nestedDEDataDefinitionFields(
			List<DEDataDefinitionField> deNestedDataDefinitionFields) {

			if (ListUtil.isNotEmpty(deNestedDataDefinitionFields)) {
				_deDataDefinitionField._nestedDEDataDefinitionFields.addAll(
					deNestedDataDefinitionFields);
			}

			return this;
		}

		public Builder predefinedValue(Map<String, Object> predefinedValue) {
			_deDataDefinitionField._predefinedValue = predefinedValue;

			return this;
		}

		public Builder readOnly(boolean readOnly) {
			_deDataDefinitionField._readOnly = readOnly;

			return this;
		}

		public Builder repeatable(boolean repeatable) {
			_deDataDefinitionField._repeatable = repeatable;

			return this;
		}

		public Builder required(boolean required) {
			_deDataDefinitionField._required = required;

			return this;
		}

		public Builder showLabel(boolean showLabel) {
			_deDataDefinitionField._showLabel = showLabel;

			return this;
		}

		public Builder tip(Map<String, Object> tip) {
			_deDataDefinitionField._tip = tip;

			return this;
		}

		private Builder(String fieldType, String name) {
			_deDataDefinitionField._fieldType = fieldType;
			_deDataDefinitionField._name = name;
		}

		private final DEDataDefinitionField _deDataDefinitionField =
			new DEDataDefinitionField();

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

	private DEDataDefinitionField() {
	}

	private Map<String, Object> _customProperties = new HashMap<>();
	private String _fieldType;
	private boolean _indexable;
	private IndexType _indexType;
	private Map<String, Object> _label;
	private boolean _localizable;
	private String _name;
	private List<DEDataDefinitionField> _nestedDEDataDefinitionFields =
		new ArrayList<>();
	private Map<String, Object> _predefinedValue;
	private boolean _readOnly;
	private boolean _repeatable;
	private boolean _required;
	private boolean _showLabel;
	private Map<String, Object> _tip;

}
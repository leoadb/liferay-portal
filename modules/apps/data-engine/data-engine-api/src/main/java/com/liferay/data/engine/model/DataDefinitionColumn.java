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

import com.liferay.portal.kernel.util.HashUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Leonardo Barros
 */
public final class DataDefinitionColumn implements Serializable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DataDefinitionColumn)) {
			return false;
		}

		DataDefinitionColumn dataDefinitionColumn = (DataDefinitionColumn)obj;

		if (Objects.equals(_name, dataDefinitionColumn._name) &&
			Objects.equals(_type, dataDefinitionColumn._type) &&
			Objects.equals(_indexable, dataDefinitionColumn._indexable) &&
			Objects.equals(_localizable, dataDefinitionColumn._localizable) &&
			Objects.equals(_repeatable, dataDefinitionColumn._repeatable) &&
			Objects.equals(_label, dataDefinitionColumn._label)) {

			return true;
		}

		return false;
	}

	public Map<String, String> getLabel() {
		return Collections.unmodifiableMap(_label);
	}

	public String getName() {
		return _name;
	}

	public DataDefinitionColumnType getType() {
		return _type;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _name.hashCode());

		hash = HashUtil.hash(hash, _type.hashCode());

		hash = HashUtil.hash(hash, _indexable);

		hash = HashUtil.hash(hash, _localizable);

		hash = HashUtil.hash(hash, _repeatable);

		return HashUtil.hash(hash, _label.hashCode());
	}

	public boolean isIndexable() {
		return _indexable;
	}

	public boolean isLocalizable() {
		return _localizable;
	}

	public boolean isRepeatable() {
		return _repeatable;
	}

	public static final class Builder {

		public static Builder newBuilder(
			String name, DataDefinitionColumnType type) {

			return new Builder(name, type);
		}

		public DataDefinitionColumn build() {
			return _dataDefinitionColumn;
		}

		public Builder indexable(boolean indexable) {
			_dataDefinitionColumn._indexable = indexable;

			return this;
		}

		public Builder labels(Map<String, String> label) {
			_dataDefinitionColumn._label.putAll(label);

			return this;
		}

		public Builder localizable(boolean localizable) {
			_dataDefinitionColumn._localizable = localizable;

			return this;
		}

		public Builder repeatable(boolean repeatable) {
			_dataDefinitionColumn._repeatable = repeatable;

			return this;
		}

		private Builder(String name, DataDefinitionColumnType type) {
			_dataDefinitionColumn._name = name;
			_dataDefinitionColumn._type = type;
		}

		private final DataDefinitionColumn _dataDefinitionColumn =
			new DataDefinitionColumn();

	}

	private DataDefinitionColumn() {
	}

	private boolean _indexable = true;
	private final Map<String, String> _label = new HashMap<>();
	private boolean _localizable;
	private String _name;
	private boolean _repeatable;
	private DataDefinitionColumnType _type;

}
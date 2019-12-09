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

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DEDataRecordCollection implements Serializable {

	public static Builder newBuilder(long deDataDefinitionId) {
		return new Builder(deDataDefinitionId);
	}

	public static Builder newBuilder(
		long deDataDefinitionId, long deDataRecordCollectionId) {

		return new Builder(deDataDefinitionId, deDataRecordCollectionId);
	}

	public long getDEDataDefinitionId() {
		return _deDataDefinitionId;
	}

	public long getDEDataRecordCollectionId() {
		return _deDataRecordCollectionId;
	}

	public String getDEDataRecordCollectionKey() {
		return _deDataRecordCollectionKey;
	}

	public Map<Locale, String> getDescription() {
		return _description;
	}

	public Map<Locale, String> getName() {
		return _name;
	}

	public static class Builder {

		public DEDataRecordCollection build() {
			return _deDataRecordCollection;
		}

		public Builder deDataRecordCollectionKey(
			String deDataRecordCollectionKey) {

			_deDataRecordCollection._deDataRecordCollectionKey =
				deDataRecordCollectionKey;

			return this;
		}

		public Builder description(Map<Locale, String> description) {
			_deDataRecordCollection._description = description;

			return this;
		}

		public Builder name(Map<Locale, String> name) {
			_deDataRecordCollection._name = name;

			return this;
		}

		private Builder(long deDataDefinitionId) {
			_deDataRecordCollection._deDataDefinitionId = deDataDefinitionId;
		}

		private Builder(
			long deDataDefinitionId, long deDataRecordCollectionId) {

			_deDataRecordCollection._deDataDefinitionId = deDataDefinitionId;
			_deDataRecordCollection._deDataRecordCollectionId =
				deDataRecordCollectionId;
		}

		private final DEDataRecordCollection _deDataRecordCollection =
			new DEDataRecordCollection();

	}

	private DEDataRecordCollection() {
	}

	private long _deDataDefinitionId;
	private long _deDataRecordCollectionId;
	private String _deDataRecordCollectionKey;
	private Map<Locale, String> _description;
	private Map<Locale, String> _name;

}
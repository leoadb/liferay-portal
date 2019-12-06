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

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Leonardo Barros
 */
public class DEDataDefinition implements ClassedModel, Serializable {

	public static Builder newBuilder(
		List<DEDataDefinitionField> deDataDefinitionFields,
		long deDataDefinitionId) {

		return new Builder(deDataDefinitionFields, deDataDefinitionId);
	}

	public static Builder newBuilder(
		long classNameId, long companyId,
		List<DEDataDefinitionField> deDataDefinitionFields) {

		return new Builder(classNameId, companyId, deDataDefinitionFields);
	}

	public static Builder newBuilder(
		long classNameId, long companyId,
		List<DEDataDefinitionField> deDataDefinitionFields, long groupId) {

		return new Builder(
			classNameId, companyId, deDataDefinitionFields, groupId);
	}

	public Set<Locale> getAvailableLocales() {
		return _availableLocales;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public Date getDateCreated() {
		return _dateCreated;
	}

	public Date getDateModified() {
		return _dateModified;
	}

	public List<DEDataDefinitionField> getDEDataDefinitionFields() {
		return _deDataDefinitionFields;
	}

	public long getDEDataDefinitionId() {
		return _deDataDefinitionId;
	}

	public String getDEDataDefinitionKey() {
		return _deDataDefinitionKey;
	}

	public Locale getDefaultLocale() {
		return _defaultLocale;
	}

	public Map<Locale, String> getDescription() {
		return _description;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		throw new UnsupportedOperationException();
	}

	public long getGroupId() {
		return _groupId;
	}

	@Override
	public Class<?> getModelClass() {
		return DEDataDefinition.class;
	}

	@Override
	public String getModelClassName() {
		return DEDataDefinition.class.getName();
	}

	public Map<Locale, String> getName() {
		return _name;
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _deDataDefinitionId;
	}

	public String getStorageType() {
		return _storageType;
	}

	public long getUserId() {
		return _userId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_deDataDefinitionId = (long)primaryKeyObj;
	}

	public static class Builder {

		public Builder availableLocales(Set<Locale> availableLocales) {
			if (SetUtil.isNotEmpty(availableLocales)) {
				_deDataDefinition._availableLocales.addAll(availableLocales);
			}

			return this;
		}

		public DEDataDefinition build() {
			return _deDataDefinition;
		}

		public Builder classNameId(long classNameId) {
			_deDataDefinition._classNameId = classNameId;

			return this;
		}

		public Builder companyId(long companyId) {
			_deDataDefinition._companyId = companyId;

			return this;
		}

		public Builder dateCreated(Date dateCreated) {
			_deDataDefinition._dateCreated = dateCreated;

			return this;
		}

		public Builder dateModified(Date dateModified) {
			_deDataDefinition._dateModified = dateModified;

			return this;
		}

		public Builder deDataDefinitionId(long deDataDefinitionId) {
			_deDataDefinition._deDataDefinitionId = deDataDefinitionId;

			return this;
		}

		public Builder deDataDefinitionKey(String deDataDefinitionKey) {
			_deDataDefinition._deDataDefinitionKey = deDataDefinitionKey;

			return this;
		}

		public Builder defaultLocale(Locale defaultLocale) {
			_deDataDefinition._defaultLocale = defaultLocale;

			return this;
		}

		public Builder description(Map<Locale, String> description) {
			_deDataDefinition._description = description;

			return this;
		}

		public Builder groupId(long groupId) {
			_deDataDefinition._groupId = groupId;

			return this;
		}

		public Builder name(Map<Locale, String> name) {
			_deDataDefinition._name = name;

			return this;
		}

		public Builder storageType(String storageType) {
			_deDataDefinition._storageType = storageType;

			return this;
		}

		public Builder userId(long userId) {
			_deDataDefinition._userId = userId;

			return this;
		}

		private Builder() {
		}

		private Builder(
			List<DEDataDefinitionField> deDataDefinitionFields,
			long deDataDefinitionId) {

			if (ListUtil.isNotEmpty(deDataDefinitionFields)) {
				_deDataDefinition._deDataDefinitionFields.addAll(
					deDataDefinitionFields);
			}

			_deDataDefinition._deDataDefinitionId = deDataDefinitionId;
		}

		private Builder(
			long classNameId, long companyId,
			List<DEDataDefinitionField> deDataDefinitionFields) {

			this(classNameId, companyId, deDataDefinitionFields, 0);
		}

		private Builder(
			long classNameId, long companyId,
			List<DEDataDefinitionField> deDataDefinitionFields, long groupId) {

			_deDataDefinition._classNameId = classNameId;
			_deDataDefinition._companyId = companyId;

			if (ListUtil.isNotEmpty(deDataDefinitionFields)) {
				_deDataDefinition._deDataDefinitionFields.addAll(
					deDataDefinitionFields);
			}

			_deDataDefinition._groupId = groupId;
		}

		private DEDataDefinition _deDataDefinition = new DEDataDefinition();

	}

	private DEDataDefinition() {
	}

	private final Set<Locale> _availableLocales = new HashSet<>();
	private long _classNameId;
	private long _companyId;
	private Date _dateCreated;
	private Date _dateModified;
	private final List<DEDataDefinitionField> _deDataDefinitionFields =
		new ArrayList<>();
	private long _deDataDefinitionId;
	private String _deDataDefinitionKey;
	private Locale _defaultLocale;
	private Map<Locale, String> _description;
	private long _groupId;
	private Map<Locale, String> _name;
	private String _storageType = "json";
	private long _userId;

}
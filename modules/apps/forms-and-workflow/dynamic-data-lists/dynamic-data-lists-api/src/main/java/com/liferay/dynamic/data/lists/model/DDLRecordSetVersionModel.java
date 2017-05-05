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

package com.liferay.dynamic.data.lists.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.LocalizedModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.model.StagedGroupedModel;
import com.liferay.portal.kernel.model.WorkflowedModel;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * The base model interface for the DDLRecordSetVersion service. Represents a row in the &quot;DDLRecordSetVersion&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordSetVersionModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordSetVersionImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordSetVersion
 * @see com.liferay.dynamic.data.lists.model.impl.DDLRecordSetVersionImpl
 * @see com.liferay.dynamic.data.lists.model.impl.DDLRecordSetVersionModelImpl
 * @generated
 */
@ProviderType
public interface DDLRecordSetVersionModel extends BaseModel<DDLRecordSetVersion>,
	LocalizedModel, ShardedModel, StagedGroupedModel, WorkflowedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a ddl record set version model instance should use the {@link DDLRecordSetVersion} interface instead.
	 */

	/**
	 * Returns the primary key of this ddl record set version.
	 *
	 * @return the primary key of this ddl record set version
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this ddl record set version.
	 *
	 * @param primaryKey the primary key of this ddl record set version
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the uuid of this ddl record set version.
	 *
	 * @return the uuid of this ddl record set version
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this ddl record set version.
	 *
	 * @param uuid the uuid of this ddl record set version
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the record set version ID of this ddl record set version.
	 *
	 * @return the record set version ID of this ddl record set version
	 */
	public long getRecordSetVersionId();

	/**
	 * Sets the record set version ID of this ddl record set version.
	 *
	 * @param recordSetVersionId the record set version ID of this ddl record set version
	 */
	public void setRecordSetVersionId(long recordSetVersionId);

	/**
	 * Returns the group ID of this ddl record set version.
	 *
	 * @return the group ID of this ddl record set version
	 */
	@Override
	public long getGroupId();

	/**
	 * Sets the group ID of this ddl record set version.
	 *
	 * @param groupId the group ID of this ddl record set version
	 */
	@Override
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this ddl record set version.
	 *
	 * @return the company ID of this ddl record set version
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this ddl record set version.
	 *
	 * @param companyId the company ID of this ddl record set version
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this ddl record set version.
	 *
	 * @return the user ID of this ddl record set version
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this ddl record set version.
	 *
	 * @param userId the user ID of this ddl record set version
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this ddl record set version.
	 *
	 * @return the user uuid of this ddl record set version
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this ddl record set version.
	 *
	 * @param userUuid the user uuid of this ddl record set version
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this ddl record set version.
	 *
	 * @return the user name of this ddl record set version
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this ddl record set version.
	 *
	 * @param userName the user name of this ddl record set version
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this ddl record set version.
	 *
	 * @return the create date of this ddl record set version
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this ddl record set version.
	 *
	 * @param createDate the create date of this ddl record set version
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this ddl record set version.
	 *
	 * @return the modified date of this ddl record set version
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this ddl record set version.
	 *
	 * @param modifiedDate the modified date of this ddl record set version
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the record set ID of this ddl record set version.
	 *
	 * @return the record set ID of this ddl record set version
	 */
	public long getRecordSetId();

	/**
	 * Sets the record set ID of this ddl record set version.
	 *
	 * @param recordSetId the record set ID of this ddl record set version
	 */
	public void setRecordSetId(long recordSetId);

	/**
	 * Returns the ddm structure version ID of this ddl record set version.
	 *
	 * @return the ddm structure version ID of this ddl record set version
	 */
	public long getDDMStructureVersionId();

	/**
	 * Sets the ddm structure version ID of this ddl record set version.
	 *
	 * @param DDMStructureVersionId the ddm structure version ID of this ddl record set version
	 */
	public void setDDMStructureVersionId(long DDMStructureVersionId);

	/**
	 * Returns the name of this ddl record set version.
	 *
	 * @return the name of this ddl record set version
	 */
	public String getName();

	/**
	 * Returns the localized name of this ddl record set version in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this ddl record set version
	 */
	@AutoEscape
	public String getName(Locale locale);

	/**
	 * Returns the localized name of this ddl record set version in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this ddl record set version. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getName(Locale locale, boolean useDefault);

	/**
	 * Returns the localized name of this ddl record set version in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this ddl record set version
	 */
	@AutoEscape
	public String getName(String languageId);

	/**
	 * Returns the localized name of this ddl record set version in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this ddl record set version
	 */
	@AutoEscape
	public String getName(String languageId, boolean useDefault);

	@AutoEscape
	public String getNameCurrentLanguageId();

	@AutoEscape
	public String getNameCurrentValue();

	/**
	 * Returns a map of the locales and localized names of this ddl record set version.
	 *
	 * @return the locales and localized names of this ddl record set version
	 */
	public Map<Locale, String> getNameMap();

	/**
	 * Sets the name of this ddl record set version.
	 *
	 * @param name the name of this ddl record set version
	 */
	public void setName(String name);

	/**
	 * Sets the localized name of this ddl record set version in the language.
	 *
	 * @param name the localized name of this ddl record set version
	 * @param locale the locale of the language
	 */
	public void setName(String name, Locale locale);

	/**
	 * Sets the localized name of this ddl record set version in the language, and sets the default locale.
	 *
	 * @param name the localized name of this ddl record set version
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setName(String name, Locale locale, Locale defaultLocale);

	public void setNameCurrentLanguageId(String languageId);

	/**
	 * Sets the localized names of this ddl record set version from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this ddl record set version
	 */
	public void setNameMap(Map<Locale, String> nameMap);

	/**
	 * Sets the localized names of this ddl record set version from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this ddl record set version
	 * @param defaultLocale the default locale
	 */
	public void setNameMap(Map<Locale, String> nameMap, Locale defaultLocale);

	/**
	 * Returns the description of this ddl record set version.
	 *
	 * @return the description of this ddl record set version
	 */
	public String getDescription();

	/**
	 * Returns the localized description of this ddl record set version in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized description of this ddl record set version
	 */
	@AutoEscape
	public String getDescription(Locale locale);

	/**
	 * Returns the localized description of this ddl record set version in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this ddl record set version. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getDescription(Locale locale, boolean useDefault);

	/**
	 * Returns the localized description of this ddl record set version in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized description of this ddl record set version
	 */
	@AutoEscape
	public String getDescription(String languageId);

	/**
	 * Returns the localized description of this ddl record set version in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this ddl record set version
	 */
	@AutoEscape
	public String getDescription(String languageId, boolean useDefault);

	@AutoEscape
	public String getDescriptionCurrentLanguageId();

	@AutoEscape
	public String getDescriptionCurrentValue();

	/**
	 * Returns a map of the locales and localized descriptions of this ddl record set version.
	 *
	 * @return the locales and localized descriptions of this ddl record set version
	 */
	public Map<Locale, String> getDescriptionMap();

	/**
	 * Sets the description of this ddl record set version.
	 *
	 * @param description the description of this ddl record set version
	 */
	public void setDescription(String description);

	/**
	 * Sets the localized description of this ddl record set version in the language.
	 *
	 * @param description the localized description of this ddl record set version
	 * @param locale the locale of the language
	 */
	public void setDescription(String description, Locale locale);

	/**
	 * Sets the localized description of this ddl record set version in the language, and sets the default locale.
	 *
	 * @param description the localized description of this ddl record set version
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setDescription(String description, Locale locale,
		Locale defaultLocale);

	public void setDescriptionCurrentLanguageId(String languageId);

	/**
	 * Sets the localized descriptions of this ddl record set version from the map of locales and localized descriptions.
	 *
	 * @param descriptionMap the locales and localized descriptions of this ddl record set version
	 */
	public void setDescriptionMap(Map<Locale, String> descriptionMap);

	/**
	 * Sets the localized descriptions of this ddl record set version from the map of locales and localized descriptions, and sets the default locale.
	 *
	 * @param descriptionMap the locales and localized descriptions of this ddl record set version
	 * @param defaultLocale the default locale
	 */
	public void setDescriptionMap(Map<Locale, String> descriptionMap,
		Locale defaultLocale);

	/**
	 * Returns the settings of this ddl record set version.
	 *
	 * @return the settings of this ddl record set version
	 */
	@AutoEscape
	public String getSettings();

	/**
	 * Sets the settings of this ddl record set version.
	 *
	 * @param settings the settings of this ddl record set version
	 */
	public void setSettings(String settings);

	/**
	 * Returns the version of this ddl record set version.
	 *
	 * @return the version of this ddl record set version
	 */
	@AutoEscape
	public String getVersion();

	/**
	 * Sets the version of this ddl record set version.
	 *
	 * @param version the version of this ddl record set version
	 */
	public void setVersion(String version);

	/**
	 * Returns the status of this ddl record set version.
	 *
	 * @return the status of this ddl record set version
	 */
	@Override
	public int getStatus();

	/**
	 * Sets the status of this ddl record set version.
	 *
	 * @param status the status of this ddl record set version
	 */
	@Override
	public void setStatus(int status);

	/**
	 * Returns the status by user ID of this ddl record set version.
	 *
	 * @return the status by user ID of this ddl record set version
	 */
	@Override
	public long getStatusByUserId();

	/**
	 * Sets the status by user ID of this ddl record set version.
	 *
	 * @param statusByUserId the status by user ID of this ddl record set version
	 */
	@Override
	public void setStatusByUserId(long statusByUserId);

	/**
	 * Returns the status by user uuid of this ddl record set version.
	 *
	 * @return the status by user uuid of this ddl record set version
	 */
	@Override
	public String getStatusByUserUuid();

	/**
	 * Sets the status by user uuid of this ddl record set version.
	 *
	 * @param statusByUserUuid the status by user uuid of this ddl record set version
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid);

	/**
	 * Returns the status by user name of this ddl record set version.
	 *
	 * @return the status by user name of this ddl record set version
	 */
	@AutoEscape
	@Override
	public String getStatusByUserName();

	/**
	 * Sets the status by user name of this ddl record set version.
	 *
	 * @param statusByUserName the status by user name of this ddl record set version
	 */
	@Override
	public void setStatusByUserName(String statusByUserName);

	/**
	 * Returns the status date of this ddl record set version.
	 *
	 * @return the status date of this ddl record set version
	 */
	@Override
	public Date getStatusDate();

	/**
	 * Sets the status date of this ddl record set version.
	 *
	 * @param statusDate the status date of this ddl record set version
	 */
	@Override
	public void setStatusDate(Date statusDate);

	/**
	 * Returns the last publish date of this ddl record set version.
	 *
	 * @return the last publish date of this ddl record set version
	 */
	@Override
	public Date getLastPublishDate();

	/**
	 * Sets the last publish date of this ddl record set version.
	 *
	 * @param lastPublishDate the last publish date of this ddl record set version
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate);

	/**
	 * Returns <code>true</code> if this ddl record set version is approved.
	 *
	 * @return <code>true</code> if this ddl record set version is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved();

	/**
	 * Returns <code>true</code> if this ddl record set version is denied.
	 *
	 * @return <code>true</code> if this ddl record set version is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied();

	/**
	 * Returns <code>true</code> if this ddl record set version is a draft.
	 *
	 * @return <code>true</code> if this ddl record set version is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft();

	/**
	 * Returns <code>true</code> if this ddl record set version is expired.
	 *
	 * @return <code>true</code> if this ddl record set version is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired();

	/**
	 * Returns <code>true</code> if this ddl record set version is inactive.
	 *
	 * @return <code>true</code> if this ddl record set version is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive();

	/**
	 * Returns <code>true</code> if this ddl record set version is incomplete.
	 *
	 * @return <code>true</code> if this ddl record set version is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete();

	/**
	 * Returns <code>true</code> if this ddl record set version is pending.
	 *
	 * @return <code>true</code> if this ddl record set version is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending();

	/**
	 * Returns <code>true</code> if this ddl record set version is scheduled.
	 *
	 * @return <code>true</code> if this ddl record set version is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled();

	@Override
	public boolean isNew();

	@Override
	public void setNew(boolean n);

	@Override
	public boolean isCachedModel();

	@Override
	public void setCachedModel(boolean cachedModel);

	@Override
	public boolean isEscapedModel();

	@Override
	public Serializable getPrimaryKeyObj();

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	@Override
	public ExpandoBridge getExpandoBridge();

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel);

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge);

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	@Override
	public String[] getAvailableLanguageIds();

	@Override
	public String getDefaultLanguageId();

	@Override
	public void prepareLocalizedFieldsForImport() throws LocaleException;

	@Override
	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException;

	@Override
	public Object clone();

	@Override
	public int compareTo(DDLRecordSetVersion ddlRecordSetVersion);

	@Override
	public int hashCode();

	@Override
	public CacheModel<DDLRecordSetVersion> toCacheModel();

	@Override
	public DDLRecordSetVersion toEscapedModel();

	@Override
	public DDLRecordSetVersion toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();
}
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

package com.liferay.report.definitions.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.model.StagedAuditedModel;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;

/**
 * The base model interface for the ReportDefinition service. Represents a row in the &quot;ReportDefinition&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.report.definitions.model.impl.ReportDefinitionModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.report.definitions.model.impl.ReportDefinitionImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ReportDefinition
 * @see com.liferay.report.definitions.model.impl.ReportDefinitionImpl
 * @see com.liferay.report.definitions.model.impl.ReportDefinitionModelImpl
 * @generated
 */
@ProviderType
public interface ReportDefinitionModel extends BaseModel<ReportDefinition>,
	GroupedModel, ShardedModel, StagedAuditedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a report definition model instance should use the {@link ReportDefinition} interface instead.
	 */

	/**
	 * Returns the primary key of this report definition.
	 *
	 * @return the primary key of this report definition
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this report definition.
	 *
	 * @param primaryKey the primary key of this report definition
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the uuid of this report definition.
	 *
	 * @return the uuid of this report definition
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this report definition.
	 *
	 * @param uuid the uuid of this report definition
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the report definition ID of this report definition.
	 *
	 * @return the report definition ID of this report definition
	 */
	public long getReportDefinitionId();

	/**
	 * Sets the report definition ID of this report definition.
	 *
	 * @param reportDefinitionId the report definition ID of this report definition
	 */
	public void setReportDefinitionId(long reportDefinitionId);

	/**
	 * Returns the group ID of this report definition.
	 *
	 * @return the group ID of this report definition
	 */
	@Override
	public long getGroupId();

	/**
	 * Sets the group ID of this report definition.
	 *
	 * @param groupId the group ID of this report definition
	 */
	@Override
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this report definition.
	 *
	 * @return the company ID of this report definition
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this report definition.
	 *
	 * @param companyId the company ID of this report definition
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this report definition.
	 *
	 * @return the user ID of this report definition
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this report definition.
	 *
	 * @param userId the user ID of this report definition
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this report definition.
	 *
	 * @return the user uuid of this report definition
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this report definition.
	 *
	 * @param userUuid the user uuid of this report definition
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this report definition.
	 *
	 * @return the user name of this report definition
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this report definition.
	 *
	 * @param userName the user name of this report definition
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this report definition.
	 *
	 * @return the create date of this report definition
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this report definition.
	 *
	 * @param createDate the create date of this report definition
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this report definition.
	 *
	 * @return the modified date of this report definition
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this report definition.
	 *
	 * @param modifiedDate the modified date of this report definition
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the data definition ID of this report definition.
	 *
	 * @return the data definition ID of this report definition
	 */
	public long getDataDefinitionId();

	/**
	 * Sets the data definition ID of this report definition.
	 *
	 * @param dataDefinitionId the data definition ID of this report definition
	 */
	public void setDataDefinitionId(long dataDefinitionId);

	/**
	 * Returns the description of this report definition.
	 *
	 * @return the description of this report definition
	 */
	@AutoEscape
	public String getDescription();

	/**
	 * Sets the description of this report definition.
	 *
	 * @param description the description of this report definition
	 */
	public void setDescription(String description);

	/**
	 * Returns the name of this report definition.
	 *
	 * @return the name of this report definition
	 */
	@AutoEscape
	public String getName();

	/**
	 * Sets the name of this report definition.
	 *
	 * @param name the name of this report definition
	 */
	public void setName(String name);

	/**
	 * Returns the parameters data schema ID of this report definition.
	 *
	 * @return the parameters data schema ID of this report definition
	 */
	public long getParametersDataSchemaId();

	/**
	 * Sets the parameters data schema ID of this report definition.
	 *
	 * @param parametersDataSchemaId the parameters data schema ID of this report definition
	 */
	public void setParametersDataSchemaId(long parametersDataSchemaId);

	/**
	 * Returns the sort columns of this report definition.
	 *
	 * @return the sort columns of this report definition
	 */
	@AutoEscape
	public String getSortColumns();

	/**
	 * Sets the sort columns of this report definition.
	 *
	 * @param sortColumns the sort columns of this report definition
	 */
	public void setSortColumns(String sortColumns);

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
	public Object clone();

	@Override
	public int compareTo(ReportDefinition reportDefinition);

	@Override
	public int hashCode();

	@Override
	public CacheModel<ReportDefinition> toCacheModel();

	@Override
	public ReportDefinition toEscapedModel();

	@Override
	public ReportDefinition toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();
}
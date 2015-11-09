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

package com.liferay.dynamic.data.mapping.service.base;

import com.liferay.dynamic.data.mapping.model.DDMDataProvider;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderService;
import com.liferay.dynamic.data.mapping.service.persistence.DDMDataProviderFinder;
import com.liferay.dynamic.data.mapping.service.persistence.DDMDataProviderPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.service.BaseServiceImpl;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.util.PortalUtil;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the d d m data provider remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.dynamic.data.mapping.service.impl.DDMDataProviderServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMDataProviderServiceImpl
 * @see com.liferay.dynamic.data.mapping.service.DDMDataProviderServiceUtil
 * @generated
 */
public abstract class DDMDataProviderServiceBaseImpl extends BaseServiceImpl
	implements DDMDataProviderService, IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.dynamic.data.mapping.service.DDMDataProviderServiceUtil} to access the d d m data provider remote service.
	 */

	/**
	 * Returns the d d m data provider local service.
	 *
	 * @return the d d m data provider local service
	 */
	public com.liferay.dynamic.data.mapping.service.DDMDataProviderLocalService getDDMDataProviderLocalService() {
		return ddmDataProviderLocalService;
	}

	/**
	 * Sets the d d m data provider local service.
	 *
	 * @param ddmDataProviderLocalService the d d m data provider local service
	 */
	public void setDDMDataProviderLocalService(
		com.liferay.dynamic.data.mapping.service.DDMDataProviderLocalService ddmDataProviderLocalService) {
		this.ddmDataProviderLocalService = ddmDataProviderLocalService;
	}

	/**
	 * Returns the d d m data provider remote service.
	 *
	 * @return the d d m data provider remote service
	 */
	public DDMDataProviderService getDDMDataProviderService() {
		return ddmDataProviderService;
	}

	/**
	 * Sets the d d m data provider remote service.
	 *
	 * @param ddmDataProviderService the d d m data provider remote service
	 */
	public void setDDMDataProviderService(
		DDMDataProviderService ddmDataProviderService) {
		this.ddmDataProviderService = ddmDataProviderService;
	}

	/**
	 * Returns the d d m data provider persistence.
	 *
	 * @return the d d m data provider persistence
	 */
	public DDMDataProviderPersistence getDDMDataProviderPersistence() {
		return ddmDataProviderPersistence;
	}

	/**
	 * Sets the d d m data provider persistence.
	 *
	 * @param ddmDataProviderPersistence the d d m data provider persistence
	 */
	public void setDDMDataProviderPersistence(
		DDMDataProviderPersistence ddmDataProviderPersistence) {
		this.ddmDataProviderPersistence = ddmDataProviderPersistence;
	}

	/**
	 * Returns the d d m data provider finder.
	 *
	 * @return the d d m data provider finder
	 */
	public DDMDataProviderFinder getDDMDataProviderFinder() {
		return ddmDataProviderFinder;
	}

	/**
	 * Sets the d d m data provider finder.
	 *
	 * @param ddmDataProviderFinder the d d m data provider finder
	 */
	public void setDDMDataProviderFinder(
		DDMDataProviderFinder ddmDataProviderFinder) {
		this.ddmDataProviderFinder = ddmDataProviderFinder;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the group local service.
	 *
	 * @return the group local service
	 */
	public com.liferay.portal.service.GroupLocalService getGroupLocalService() {
		return groupLocalService;
	}

	/**
	 * Sets the group local service.
	 *
	 * @param groupLocalService the group local service
	 */
	public void setGroupLocalService(
		com.liferay.portal.service.GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	/**
	 * Returns the group remote service.
	 *
	 * @return the group remote service
	 */
	public com.liferay.portal.service.GroupService getGroupService() {
		return groupService;
	}

	/**
	 * Sets the group remote service.
	 *
	 * @param groupService the group remote service
	 */
	public void setGroupService(
		com.liferay.portal.service.GroupService groupService) {
		this.groupService = groupService;
	}

	/**
	 * Returns the group persistence.
	 *
	 * @return the group persistence
	 */
	public GroupPersistence getGroupPersistence() {
		return groupPersistence;
	}

	/**
	 * Sets the group persistence.
	 *
	 * @param groupPersistence the group persistence
	 */
	public void setGroupPersistence(GroupPersistence groupPersistence) {
		this.groupPersistence = groupPersistence;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public com.liferay.portal.service.UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(
		com.liferay.portal.service.UserService userService) {
		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
	}

	public void destroy() {
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return DDMDataProviderService.class.getName();
	}

	protected Class<?> getModelClass() {
		return DDMDataProvider.class;
	}

	protected String getModelClassName() {
		return DDMDataProvider.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = ddmDataProviderPersistence.getDataSource();

			DB db = DBFactoryUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.dynamic.data.mapping.service.DDMDataProviderLocalService.class)
	protected com.liferay.dynamic.data.mapping.service.DDMDataProviderLocalService ddmDataProviderLocalService;
	@BeanReference(type = com.liferay.dynamic.data.mapping.service.DDMDataProviderService.class)
	protected DDMDataProviderService ddmDataProviderService;
	@BeanReference(type = DDMDataProviderPersistence.class)
	protected DDMDataProviderPersistence ddmDataProviderPersistence;
	@BeanReference(type = DDMDataProviderFinder.class)
	protected DDMDataProviderFinder ddmDataProviderFinder;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.service.GroupLocalService.class)
	protected com.liferay.portal.service.GroupLocalService groupLocalService;
	@BeanReference(type = com.liferay.portal.service.GroupService.class)
	protected com.liferay.portal.service.GroupService groupService;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = com.liferay.portal.service.UserLocalService.class)
	protected com.liferay.portal.service.UserLocalService userLocalService;
	@BeanReference(type = com.liferay.portal.service.UserService.class)
	protected com.liferay.portal.service.UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
}
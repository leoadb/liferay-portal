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

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.persistence.DDMStructureLayoutPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the d d m structure layout local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.dynamic.data.mapping.service.impl.DDMStructureLayoutLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMStructureLayoutLocalServiceImpl
 * @see com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class DDMStructureLayoutLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements DDMStructureLayoutLocalService,
		IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalServiceUtil} to access the d d m structure layout local service.
	 */

	/**
	 * Adds the d d m structure layout to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmStructureLayout the d d m structure layout
	 * @return the d d m structure layout that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDMStructureLayout addDDMStructureLayout(
		DDMStructureLayout ddmStructureLayout) {
		ddmStructureLayout.setNew(true);

		return ddmStructureLayoutPersistence.update(ddmStructureLayout);
	}

	/**
	 * Creates a new d d m structure layout with the primary key. Does not add the d d m structure layout to the database.
	 *
	 * @param structureLayoutId the primary key for the new d d m structure layout
	 * @return the new d d m structure layout
	 */
	@Override
	public DDMStructureLayout createDDMStructureLayout(long structureLayoutId) {
		return ddmStructureLayoutPersistence.create(structureLayoutId);
	}

	/**
	 * Deletes the d d m structure layout with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param structureLayoutId the primary key of the d d m structure layout
	 * @return the d d m structure layout that was removed
	 * @throws PortalException if a d d m structure layout with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public DDMStructureLayout deleteDDMStructureLayout(long structureLayoutId)
		throws PortalException {
		return ddmStructureLayoutPersistence.remove(structureLayoutId);
	}

	/**
	 * Deletes the d d m structure layout from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmStructureLayout the d d m structure layout
	 * @return the d d m structure layout that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public DDMStructureLayout deleteDDMStructureLayout(
		DDMStructureLayout ddmStructureLayout) {
		return ddmStructureLayoutPersistence.remove(ddmStructureLayout);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(DDMStructureLayout.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return ddmStructureLayoutPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) {
		return ddmStructureLayoutPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator) {
		return ddmStructureLayoutPersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return ddmStructureLayoutPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) {
		return ddmStructureLayoutPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public DDMStructureLayout fetchDDMStructureLayout(long structureLayoutId) {
		return ddmStructureLayoutPersistence.fetchByPrimaryKey(structureLayoutId);
	}

	/**
	 * Returns the d d m structure layout matching the UUID and group.
	 *
	 * @param uuid the d d m structure layout's UUID
	 * @param groupId the primary key of the group
	 * @return the matching d d m structure layout, or <code>null</code> if a matching d d m structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchDDMStructureLayoutByUuidAndGroupId(
		String uuid, long groupId) {
		return ddmStructureLayoutPersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the d d m structure layout with the primary key.
	 *
	 * @param structureLayoutId the primary key of the d d m structure layout
	 * @return the d d m structure layout
	 * @throws PortalException if a d d m structure layout with the primary key could not be found
	 */
	@Override
	public DDMStructureLayout getDDMStructureLayout(long structureLayoutId)
		throws PortalException {
		return ddmStructureLayoutPersistence.findByPrimaryKey(structureLayoutId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(DDMStructureLayout.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName("structureLayoutId");

		return actionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(DDMStructureLayout.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName("structureLayoutId");
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {
		final ExportActionableDynamicQuery exportActionableDynamicQuery = new ExportActionableDynamicQuery() {
				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary = portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(stagedModelType.toString(),
						modelAdditionCount);

					long modelDeletionCount = ExportImportHelperUtil.getModelDeletionCount(portletDataContext,
							stagedModelType);

					manifestSummary.addModelDeletionCount(stagedModelType.toString(),
						modelDeletionCount);

					return modelAdditionCount;
				}
			};

		initActionableDynamicQuery(exportActionableDynamicQuery);

		exportActionableDynamicQuery.setAddCriteriaMethod(new ActionableDynamicQuery.AddCriteriaMethod() {
				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(dynamicQuery,
						"modifiedDate");
				}
			});

		exportActionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setGroupId(portletDataContext.getScopeGroupId());

		exportActionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object)
					throws PortalException {
					DDMStructureLayout stagedModel = (DDMStructureLayout)object;

					StagedModelDataHandlerUtil.exportStagedModel(portletDataContext,
						stagedModel);
				}
			});
		exportActionableDynamicQuery.setStagedModelType(new StagedModelType(
				PortalUtil.getClassNameId(DDMStructureLayout.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return ddmStructureLayoutLocalService.deleteDDMStructureLayout((DDMStructureLayout)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return ddmStructureLayoutPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns all the d d m structure layouts matching the UUID and company.
	 *
	 * @param uuid the UUID of the d d m structure layouts
	 * @param companyId the primary key of the company
	 * @return the matching d d m structure layouts, or an empty list if no matches were found
	 */
	@Override
	public List<DDMStructureLayout> getDDMStructureLayoutsByUuidAndCompanyId(
		String uuid, long companyId) {
		return ddmStructureLayoutPersistence.findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of d d m structure layouts matching the UUID and company.
	 *
	 * @param uuid the UUID of the d d m structure layouts
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of d d m structure layouts
	 * @param end the upper bound of the range of d d m structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching d d m structure layouts, or an empty list if no matches were found
	 */
	@Override
	public List<DDMStructureLayout> getDDMStructureLayoutsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator) {
		return ddmStructureLayoutPersistence.findByUuid_C(uuid, companyId,
			start, end, orderByComparator);
	}

	/**
	 * Returns the d d m structure layout matching the UUID and group.
	 *
	 * @param uuid the d d m structure layout's UUID
	 * @param groupId the primary key of the group
	 * @return the matching d d m structure layout
	 * @throws PortalException if a matching d d m structure layout could not be found
	 */
	@Override
	public DDMStructureLayout getDDMStructureLayoutByUuidAndGroupId(
		String uuid, long groupId) throws PortalException {
		return ddmStructureLayoutPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the d d m structure layouts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m structure layouts
	 * @param end the upper bound of the range of d d m structure layouts (not inclusive)
	 * @return the range of d d m structure layouts
	 */
	@Override
	public List<DDMStructureLayout> getDDMStructureLayouts(int start, int end) {
		return ddmStructureLayoutPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of d d m structure layouts.
	 *
	 * @return the number of d d m structure layouts
	 */
	@Override
	public int getDDMStructureLayoutsCount() {
		return ddmStructureLayoutPersistence.countAll();
	}

	/**
	 * Updates the d d m structure layout in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ddmStructureLayout the d d m structure layout
	 * @return the d d m structure layout that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDMStructureLayout updateDDMStructureLayout(
		DDMStructureLayout ddmStructureLayout) {
		return ddmStructureLayoutPersistence.update(ddmStructureLayout);
	}

	/**
	 * Returns the d d m structure layout local service.
	 *
	 * @return the d d m structure layout local service
	 */
	public DDMStructureLayoutLocalService getDDMStructureLayoutLocalService() {
		return ddmStructureLayoutLocalService;
	}

	/**
	 * Sets the d d m structure layout local service.
	 *
	 * @param ddmStructureLayoutLocalService the d d m structure layout local service
	 */
	public void setDDMStructureLayoutLocalService(
		DDMStructureLayoutLocalService ddmStructureLayoutLocalService) {
		this.ddmStructureLayoutLocalService = ddmStructureLayoutLocalService;
	}

	/**
	 * Returns the d d m structure layout persistence.
	 *
	 * @return the d d m structure layout persistence
	 */
	public DDMStructureLayoutPersistence getDDMStructureLayoutPersistence() {
		return ddmStructureLayoutPersistence;
	}

	/**
	 * Sets the d d m structure layout persistence.
	 *
	 * @param ddmStructureLayoutPersistence the d d m structure layout persistence
	 */
	public void setDDMStructureLayoutPersistence(
		DDMStructureLayoutPersistence ddmStructureLayoutPersistence) {
		this.ddmStructureLayoutPersistence = ddmStructureLayoutPersistence;
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
		persistedModelLocalServiceRegistry.register("com.liferay.dynamic.data.mapping.model.DDMStructureLayout",
			ddmStructureLayoutLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.dynamic.data.mapping.model.DDMStructureLayout");
	}

	/**
	 * Returns the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	@Override
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	@Override
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	protected Class<?> getModelClass() {
		return DDMStructureLayout.class;
	}

	protected String getModelClassName() {
		return DDMStructureLayout.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = ddmStructureLayoutPersistence.getDataSource();

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

	@BeanReference(type = DDMStructureLayoutLocalService.class)
	protected DDMStructureLayoutLocalService ddmStructureLayoutLocalService;
	@BeanReference(type = DDMStructureLayoutPersistence.class)
	protected DDMStructureLayoutPersistence ddmStructureLayoutPersistence;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.service.UserLocalService.class)
	protected com.liferay.portal.service.UserLocalService userLocalService;
	@BeanReference(type = com.liferay.portal.service.UserService.class)
	protected com.liferay.portal.service.UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
	private String _beanIdentifier;
}
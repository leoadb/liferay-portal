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

import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.service.persistence.DDMStructureVersionPersistence;

import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the ddm structure version local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.dynamic.data.mapping.service.impl.DDMStructureVersionLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMStructureVersionLocalServiceImpl
 * @see com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class DDMStructureVersionLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements DDMStructureVersionLocalService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalServiceUtil} to access the ddm structure version local service.
	 */

	/**
	 * Adds the ddm structure version to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmStructureVersion the ddm structure version
	 * @return the ddm structure version that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDMStructureVersion addDDMStructureVersion(
		DDMStructureVersion ddmStructureVersion) {
		ddmStructureVersion.setNew(true);

		return ddmStructureVersionPersistence.update(ddmStructureVersion);
	}

	/**
	 * Creates a new ddm structure version with the primary key. Does not add the ddm structure version to the database.
	 *
	 * @param structureVersionId the primary key for the new ddm structure version
	 * @return the new ddm structure version
	 */
	@Override
	public DDMStructureVersion createDDMStructureVersion(
		long structureVersionId) {
		return ddmStructureVersionPersistence.create(structureVersionId);
	}

	/**
	 * Deletes the ddm structure version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param structureVersionId the primary key of the ddm structure version
	 * @return the ddm structure version that was removed
	 * @throws PortalException if a ddm structure version with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public DDMStructureVersion deleteDDMStructureVersion(
		long structureVersionId) throws PortalException {
		return ddmStructureVersionPersistence.remove(structureVersionId);
	}

	/**
	 * Deletes the ddm structure version from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmStructureVersion the ddm structure version
	 * @return the ddm structure version that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public DDMStructureVersion deleteDDMStructureVersion(
		DDMStructureVersion ddmStructureVersion) {
		return ddmStructureVersionPersistence.remove(ddmStructureVersion);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(DDMStructureVersion.class,
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
		return ddmStructureVersionPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStructureVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return ddmStructureVersionPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStructureVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return ddmStructureVersionPersistence.findWithDynamicQuery(dynamicQuery,
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
		return ddmStructureVersionPersistence.countWithDynamicQuery(dynamicQuery);
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
		return ddmStructureVersionPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public DDMStructureVersion fetchDDMStructureVersion(long structureVersionId) {
		return ddmStructureVersionPersistence.fetchByPrimaryKey(structureVersionId);
	}

	/**
	 * Returns the ddm structure version matching the UUID and group.
	 *
	 * @param uuid the ddm structure version's UUID
	 * @param groupId the primary key of the group
	 * @return the matching ddm structure version, or <code>null</code> if a matching ddm structure version could not be found
	 */
	@Override
	public DDMStructureVersion fetchDDMStructureVersionByUuidAndGroupId(
		String uuid, long groupId) {
		return ddmStructureVersionPersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the ddm structure version with the primary key.
	 *
	 * @param structureVersionId the primary key of the ddm structure version
	 * @return the ddm structure version
	 * @throws PortalException if a ddm structure version with the primary key could not be found
	 */
	@Override
	public DDMStructureVersion getDDMStructureVersion(long structureVersionId)
		throws PortalException {
		return ddmStructureVersionPersistence.findByPrimaryKey(structureVersionId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(ddmStructureVersionLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(DDMStructureVersion.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("structureVersionId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(ddmStructureVersionLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(DDMStructureVersion.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"structureVersionId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(ddmStructureVersionLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(DDMStructureVersion.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("structureVersionId");
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

					manifestSummary.addModelAdditionCount(stagedModelType,
						modelAdditionCount);

					long modelDeletionCount = ExportImportHelperUtil.getModelDeletionCount(portletDataContext,
							stagedModelType);

					manifestSummary.addModelDeletionCount(stagedModelType,
						modelDeletionCount);

					return modelAdditionCount;
				}
			};

		initActionableDynamicQuery(exportActionableDynamicQuery);

		exportActionableDynamicQuery.setAddCriteriaMethod(new ActionableDynamicQuery.AddCriteriaMethod() {
				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Criterion modifiedDateCriterion = portletDataContext.getDateRangeCriteria(
							"modifiedDate");

					if (modifiedDateCriterion != null) {
						Conjunction conjunction = RestrictionsFactoryUtil.conjunction();

						conjunction.add(modifiedDateCriterion);

						Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

						disjunction.add(RestrictionsFactoryUtil.gtProperty(
								"modifiedDate", "lastPublishDate"));

						Property lastPublishDateProperty = PropertyFactoryUtil.forName(
								"lastPublishDate");

						disjunction.add(lastPublishDateProperty.isNull());

						conjunction.add(disjunction);

						modifiedDateCriterion = conjunction;
					}

					Criterion statusDateCriterion = portletDataContext.getDateRangeCriteria(
							"statusDate");

					if ((modifiedDateCriterion != null) &&
							(statusDateCriterion != null)) {
						Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

						disjunction.add(modifiedDateCriterion);
						disjunction.add(statusDateCriterion);

						dynamicQuery.add(disjunction);
					}

					Property workflowStatusProperty = PropertyFactoryUtil.forName(
							"status");

					if (portletDataContext.isInitialPublication()) {
						dynamicQuery.add(workflowStatusProperty.ne(
								WorkflowConstants.STATUS_IN_TRASH));
					}
					else {
						StagedModelDataHandler<?> stagedModelDataHandler = StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(DDMStructureVersion.class.getName());

						dynamicQuery.add(workflowStatusProperty.in(
								stagedModelDataHandler.getExportableStatuses()));
					}
				}
			});

		exportActionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setGroupId(portletDataContext.getScopeGroupId());

		exportActionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<DDMStructureVersion>() {
				@Override
				public void performAction(
					DDMStructureVersion ddmStructureVersion)
					throws PortalException {
					StagedModelDataHandlerUtil.exportStagedModel(portletDataContext,
						ddmStructureVersion);
				}
			});
		exportActionableDynamicQuery.setStagedModelType(new StagedModelType(
				PortalUtil.getClassNameId(DDMStructureVersion.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return ddmStructureVersionLocalService.deleteDDMStructureVersion((DDMStructureVersion)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return ddmStructureVersionPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns all the ddm structure versions matching the UUID and company.
	 *
	 * @param uuid the UUID of the ddm structure versions
	 * @param companyId the primary key of the company
	 * @return the matching ddm structure versions, or an empty list if no matches were found
	 */
	@Override
	public List<DDMStructureVersion> getDDMStructureVersionsByUuidAndCompanyId(
		String uuid, long companyId) {
		return ddmStructureVersionPersistence.findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of ddm structure versions matching the UUID and company.
	 *
	 * @param uuid the UUID of the ddm structure versions
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of ddm structure versions
	 * @param end the upper bound of the range of ddm structure versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching ddm structure versions, or an empty list if no matches were found
	 */
	@Override
	public List<DDMStructureVersion> getDDMStructureVersionsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DDMStructureVersion> orderByComparator) {
		return ddmStructureVersionPersistence.findByUuid_C(uuid, companyId,
			start, end, orderByComparator);
	}

	/**
	 * Returns the ddm structure version matching the UUID and group.
	 *
	 * @param uuid the ddm structure version's UUID
	 * @param groupId the primary key of the group
	 * @return the matching ddm structure version
	 * @throws PortalException if a matching ddm structure version could not be found
	 */
	@Override
	public DDMStructureVersion getDDMStructureVersionByUuidAndGroupId(
		String uuid, long groupId) throws PortalException {
		return ddmStructureVersionPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the ddm structure versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStructureVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm structure versions
	 * @param end the upper bound of the range of ddm structure versions (not inclusive)
	 * @return the range of ddm structure versions
	 */
	@Override
	public List<DDMStructureVersion> getDDMStructureVersions(int start, int end) {
		return ddmStructureVersionPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of ddm structure versions.
	 *
	 * @return the number of ddm structure versions
	 */
	@Override
	public int getDDMStructureVersionsCount() {
		return ddmStructureVersionPersistence.countAll();
	}

	/**
	 * Updates the ddm structure version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ddmStructureVersion the ddm structure version
	 * @return the ddm structure version that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDMStructureVersion updateDDMStructureVersion(
		DDMStructureVersion ddmStructureVersion) {
		return ddmStructureVersionPersistence.update(ddmStructureVersion);
	}

	/**
	 * Returns the ddm structure version local service.
	 *
	 * @return the ddm structure version local service
	 */
	public DDMStructureVersionLocalService getDDMStructureVersionLocalService() {
		return ddmStructureVersionLocalService;
	}

	/**
	 * Sets the ddm structure version local service.
	 *
	 * @param ddmStructureVersionLocalService the ddm structure version local service
	 */
	public void setDDMStructureVersionLocalService(
		DDMStructureVersionLocalService ddmStructureVersionLocalService) {
		this.ddmStructureVersionLocalService = ddmStructureVersionLocalService;
	}

	/**
	 * Returns the ddm structure version persistence.
	 *
	 * @return the ddm structure version persistence
	 */
	public DDMStructureVersionPersistence getDDMStructureVersionPersistence() {
		return ddmStructureVersionPersistence;
	}

	/**
	 * Sets the ddm structure version persistence.
	 *
	 * @param ddmStructureVersionPersistence the ddm structure version persistence
	 */
	public void setDDMStructureVersionPersistence(
		DDMStructureVersionPersistence ddmStructureVersionPersistence) {
		this.ddmStructureVersionPersistence = ddmStructureVersionPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.dynamic.data.mapping.model.DDMStructureVersion",
			ddmStructureVersionLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.dynamic.data.mapping.model.DDMStructureVersion");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return DDMStructureVersionLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return DDMStructureVersion.class;
	}

	protected String getModelClassName() {
		return DDMStructureVersion.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = ddmStructureVersionPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = DDMStructureVersionLocalService.class)
	protected DDMStructureVersionLocalService ddmStructureVersionLocalService;
	@BeanReference(type = DDMStructureVersionPersistence.class)
	protected DDMStructureVersionPersistence ddmStructureVersionPersistence;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}
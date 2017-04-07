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

package com.liferay.dynamic.data.mapping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for DDMStructureVersion. This utility wraps
 * {@link com.liferay.dynamic.data.mapping.service.impl.DDMStructureVersionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureVersionLocalService
 * @see com.liferay.dynamic.data.mapping.service.base.DDMStructureVersionLocalServiceBaseImpl
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMStructureVersionLocalServiceImpl
 * @generated
 */
@ProviderType
public class DDMStructureVersionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.dynamic.data.mapping.service.impl.DDMStructureVersionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMForm getStructureVersionDDMForm(
		com.liferay.dynamic.data.mapping.model.DDMStructureVersion structureVersion)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getStructureVersionDDMForm(structureVersion);
	}

	/**
	* Adds the ddm structure version to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureVersion the ddm structure version
	* @return the ddm structure version that was added
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMStructureVersion addDDMStructureVersion(
		com.liferay.dynamic.data.mapping.model.DDMStructureVersion ddmStructureVersion) {
		return getService().addDDMStructureVersion(ddmStructureVersion);
	}

	/**
	* Creates a new ddm structure version with the primary key. Does not add the ddm structure version to the database.
	*
	* @param structureVersionId the primary key for the new ddm structure version
	* @return the new ddm structure version
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMStructureVersion createDDMStructureVersion(
		long structureVersionId) {
		return getService().createDDMStructureVersion(structureVersionId);
	}

	/**
	* Deletes the ddm structure version from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureVersion the ddm structure version
	* @return the ddm structure version that was removed
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMStructureVersion deleteDDMStructureVersion(
		com.liferay.dynamic.data.mapping.model.DDMStructureVersion ddmStructureVersion) {
		return getService().deleteDDMStructureVersion(ddmStructureVersion);
	}

	/**
	* Deletes the ddm structure version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureVersionId the primary key of the ddm structure version
	* @return the ddm structure version that was removed
	* @throws PortalException if a ddm structure version with the primary key could not be found
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMStructureVersion deleteDDMStructureVersion(
		long structureVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteDDMStructureVersion(structureVersionId);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructureVersion fetchDDMStructureVersion(
		long structureVersionId) {
		return getService().fetchDDMStructureVersion(structureVersionId);
	}

	/**
	* Returns the ddm structure version matching the UUID and group.
	*
	* @param uuid the ddm structure version's UUID
	* @param groupId the primary key of the group
	* @return the matching ddm structure version, or <code>null</code> if a matching ddm structure version could not be found
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMStructureVersion fetchDDMStructureVersionByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService()
				   .fetchDDMStructureVersionByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns the ddm structure version with the primary key.
	*
	* @param structureVersionId the primary key of the ddm structure version
	* @return the ddm structure version
	* @throws PortalException if a ddm structure version with the primary key could not be found
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMStructureVersion getDDMStructureVersion(
		long structureVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDDMStructureVersion(structureVersionId);
	}

	/**
	* Returns the ddm structure version matching the UUID and group.
	*
	* @param uuid the ddm structure version's UUID
	* @param groupId the primary key of the group
	* @return the matching ddm structure version
	* @throws PortalException if a matching ddm structure version could not be found
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMStructureVersion getDDMStructureVersionByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDDMStructureVersionByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructureVersion getLatestStructureVersion(
		long structureId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLatestStructureVersion(structureId);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructureVersion getStructureVersion(
		long structureId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getStructureVersion(structureId, version);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructureVersion getStructureVersion(
		long structureVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getStructureVersion(structureVersionId);
	}

	/**
	* Updates the ddm structure version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureVersion the ddm structure version
	* @return the ddm structure version that was updated
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMStructureVersion updateDDMStructureVersion(
		com.liferay.dynamic.data.mapping.model.DDMStructureVersion ddmStructureVersion) {
		return getService().updateDDMStructureVersion(ddmStructureVersion);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of ddm structure versions.
	*
	* @return the number of ddm structure versions
	*/
	public static int getDDMStructureVersionsCount() {
		return getService().getDDMStructureVersionsCount();
	}

	public static int getStructureVersionsCount(long structureId) {
		return getService().getStructureVersionsCount(structureId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
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
	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructureVersion> getDDMStructureVersions(
		int start, int end) {
		return getService().getDDMStructureVersions(start, end);
	}

	/**
	* Returns all the ddm structure versions matching the UUID and company.
	*
	* @param uuid the UUID of the ddm structure versions
	* @param companyId the primary key of the company
	* @return the matching ddm structure versions, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructureVersion> getDDMStructureVersionsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .getDDMStructureVersionsByUuidAndCompanyId(uuid, companyId);
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
	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructureVersion> getDDMStructureVersionsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMStructureVersion> orderByComparator) {
		return getService()
				   .getDDMStructureVersionsByUuidAndCompanyId(uuid, companyId,
			start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructureVersion> getStructureVersions(
		long structureId) {
		return getService().getStructureVersions(structureId);
	}

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructureVersion> getStructureVersions(
		long structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMStructureVersion> orderByComparator) {
		return getService()
				   .getStructureVersions(structureId, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static DDMStructureVersionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DDMStructureVersionLocalService, DDMStructureVersionLocalService> _serviceTracker =
		ServiceTrackerFactory.open(DDMStructureVersionLocalService.class);
}
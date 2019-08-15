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

package com.liferay.dynamic.data.lists.service;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for DDLRecordSet. This utility wraps
 * <code>com.liferay.dynamic.data.lists.service.impl.DDLRecordSetLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordSetLocalService
 * @generated
 */
@ProviderType
public class DDLRecordSetLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.dynamic.data.lists.service.impl.DDLRecordSetLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the ddl record set to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddlRecordSet the ddl record set
	 * @return the ddl record set that was added
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordSet
		addDDLRecordSet(
			com.liferay.dynamic.data.lists.model.DDLRecordSet ddlRecordSet) {

		return getService().addDDLRecordSet(ddlRecordSet);
	}

	/**
	 * Adds a record set referencing the DDM structure.
	 *
	 * @param userId the primary key of the record set's creator/owner
	 * @param groupId the primary key of the record set's group
	 * @param ddmStructureId the primary key of the record set's DDM structure
	 * @param recordSetKey the record set's mnemonic primary key. If
	 <code>null</code>, the record set key will be autogenerated.
	 * @param nameMap the record set's locales and localized names
	 * @param descriptionMap the record set's locales and localized
	 descriptions
	 * @param minDisplayRows the record set's minimum number of rows to be
	 displayed in spreadsheet view.
	 * @param scope the record set's scope, used to scope the record set's
	 data. For more information search
	 <code>DDLRecordSetConstants</code> in the
	 <code>dynamic.data.lists.api</code> module for constants starting
	 with the "SCOPE_" prefix.
	 * @param serviceContext the service context to be applied. Can set the
	 UUID, guest permissions, and group permissions for the record
	 set.
	 * @return the record set
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordSet
			addRecordSet(
				long userId, long groupId, long ddmStructureId,
				String recordSetKey,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				int minDisplayRows, int scope,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addRecordSet(
			userId, groupId, ddmStructureId, recordSetKey, nameMap,
			descriptionMap, minDisplayRows, scope, serviceContext);
	}

	/**
	 * Adds the resources to the record set.
	 *
	 * @param recordSet the record set
	 * @param addGroupPermissions whether to add group permissions
	 * @param addGuestPermissions whether to add guest permissions
	 * @throws PortalException if a portal exception occurred
	 */
	public static void addRecordSetResources(
			com.liferay.dynamic.data.lists.model.DDLRecordSet recordSet,
			boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addRecordSetResources(
			recordSet, addGroupPermissions, addGuestPermissions);
	}

	/**
	 * Adds the model resources with the permissions to the record set.
	 *
	 * @param recordSet the record set
	 * @param modelPermissions the model permissions
	 * @throws PortalException if a portal exception occurred
	 */
	public static void addRecordSetResources(
			com.liferay.dynamic.data.lists.model.DDLRecordSet recordSet,
			com.liferay.portal.kernel.service.permission.ModelPermissions
				modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addRecordSetResources(recordSet, modelPermissions);
	}

	/**
	 * Adds the model resources with the permissions to the record set.
	 *
	 * @param recordSet the record set
	 * @param groupPermissions whether to add group permissions
	 * @param guestPermissions whether to add guest permissions
	 * @throws PortalException if a portal exception occurred
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #addRecordSetResources(DDLRecordSet, ModelPermissions)}
	 */
	@Deprecated
	public static void addRecordSetResources(
			com.liferay.dynamic.data.lists.model.DDLRecordSet recordSet,
			String[] groupPermissions, String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addRecordSetResources(
			recordSet, groupPermissions, guestPermissions);
	}

	/**
	 * Creates a new ddl record set with the primary key. Does not add the ddl record set to the database.
	 *
	 * @param recordSetId the primary key for the new ddl record set
	 * @return the new ddl record set
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordSet
		createDDLRecordSet(long recordSetId) {

		return getService().createDDLRecordSet(recordSetId);
	}

	/**
	 * Deletes the ddl record set from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddlRecordSet the ddl record set
	 * @return the ddl record set that was removed
	 * @throws PortalException
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordSet
			deleteDDLRecordSet(
				com.liferay.dynamic.data.lists.model.DDLRecordSet ddlRecordSet)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteDDLRecordSet(ddlRecordSet);
	}

	/**
	 * Deletes the ddl record set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param recordSetId the primary key of the ddl record set
	 * @return the ddl record set that was removed
	 * @throws PortalException if a ddl record set with the primary key could not be found
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordSet
			deleteDDLRecordSet(long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteDDLRecordSet(recordSetId);
	}

	public static void deleteDDMStructureRecordSets(long ddmStructureId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteDDMStructureRecordSets(ddmStructureId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the record set and its resources.
	 *
	 * @param recordSet the record set to be deleted
	 * @throws PortalException if a portal exception occurred
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #deleteDDLRecordSet(DDLRecordSet)}
	 */
	@Deprecated
	public static void deleteRecordSet(
			com.liferay.dynamic.data.lists.model.DDLRecordSet recordSet)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteRecordSet(recordSet);
	}

	/**
	 * Deletes the record set and its resources.
	 *
	 * @param recordSetId the primary key of the record set to be deleted
	 * @throws PortalException if a portal exception occurred
	 */
	public static void deleteRecordSet(long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteRecordSet(recordSetId);
	}

	/**
	 * Deletes the record set and its resources.
	 *
	 * <p>
	 * This operation updates the record set matching the group and
	 * recordSetKey.
	 * </p>
	 *
	 * @param groupId the primary key of the record set's group
	 * @param recordSetKey the record set's mnemonic primary key
	 * @throws PortalException if a portal exception occurred
	 */
	public static void deleteRecordSet(long groupId, String recordSetKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteRecordSet(groupId, recordSetKey);
	}

	/**
	 * Deletes all the record sets matching the group.
	 *
	 * @param groupId the primary key of the record set's group
	 * @throws PortalException if a portal exception occurred
	 */
	public static void deleteRecordSets(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteRecordSets(groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.lists.model.impl.DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.lists.model.impl.DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.dynamic.data.lists.model.DDLRecordSet
		fetchDDLRecordSet(long recordSetId) {

		return getService().fetchDDLRecordSet(recordSetId);
	}

	/**
	 * Returns the ddl record set matching the UUID and group.
	 *
	 * @param uuid the ddl record set's UUID
	 * @param groupId the primary key of the group
	 * @return the matching ddl record set, or <code>null</code> if a matching ddl record set could not be found
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordSet
		fetchDDLRecordSetByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchDDLRecordSetByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns the record set with the ID.
	 *
	 * @param recordSetId the primary key of the record set
	 * @return the record set with the ID, or <code>null</code> if a matching
	 record set could not be found
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordSet
		fetchRecordSet(long recordSetId) {

		return getService().fetchRecordSet(recordSetId);
	}

	/**
	 * Returns the record set matching the group and record set key.
	 *
	 * @param groupId the primary key of the record set's group
	 * @param recordSetKey the record set's mnemonic primary key
	 * @return the record set matching the group and record set key, or
	 <code>null</code> if a matching record set could not be found
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordSet
		fetchRecordSet(long groupId, String recordSetKey) {

		return getService().fetchRecordSet(groupId, recordSetKey);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the ddl record set with the primary key.
	 *
	 * @param recordSetId the primary key of the ddl record set
	 * @return the ddl record set
	 * @throws PortalException if a ddl record set with the primary key could not be found
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordSet
			getDDLRecordSet(long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDDLRecordSet(recordSetId);
	}

	/**
	 * Returns the ddl record set matching the UUID and group.
	 *
	 * @param uuid the ddl record set's UUID
	 * @param groupId the primary key of the group
	 * @return the matching ddl record set
	 * @throws PortalException if a matching ddl record set could not be found
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordSet
			getDDLRecordSetByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDDLRecordSetByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the ddl record sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.lists.model.impl.DDLRecordSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @return the range of ddl record sets
	 */
	public static java.util.List
		<com.liferay.dynamic.data.lists.model.DDLRecordSet> getDDLRecordSets(
			int start, int end) {

		return getService().getDDLRecordSets(start, end);
	}

	/**
	 * Returns all the ddl record sets matching the UUID and company.
	 *
	 * @param uuid the UUID of the ddl record sets
	 * @param companyId the primary key of the company
	 * @return the matching ddl record sets, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.dynamic.data.lists.model.DDLRecordSet>
			getDDLRecordSetsByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getDDLRecordSetsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of ddl record sets matching the UUID and company.
	 *
	 * @param uuid the UUID of the ddl record sets
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of ddl record sets
	 * @param end the upper bound of the range of ddl record sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching ddl record sets, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.dynamic.data.lists.model.DDLRecordSet>
			getDDLRecordSetsByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.lists.model.DDLRecordSet>
						orderByComparator) {

		return getService().getDDLRecordSetsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of ddl record sets.
	 *
	 * @return the number of ddl record sets
	 */
	public static int getDDLRecordSetsCount() {
		return getService().getDDLRecordSetsCount();
	}

	public static java.util.List
		<com.liferay.dynamic.data.lists.model.DDLRecordSet>
			getDDMStructureRecordSets(long ddmStructureId) {

		return getService().getDDMStructureRecordSets(ddmStructureId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the record set with the ID.
	 *
	 * @param recordSetId the primary key of the record set
	 * @return the record set with the ID
	 * @throws PortalException if the the matching record set could not be found
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordSet
			getRecordSet(long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRecordSet(recordSetId);
	}

	/**
	 * Returns the record set matching the group and record set key.
	 *
	 * @param groupId the primary key of the record set's group
	 * @param recordSetKey the record set's mnemonic primary key
	 * @return the record set matching the group and record set key
	 * @throws PortalException if the the matching record set could not be found
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordSet
			getRecordSet(long groupId, String recordSetKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRecordSet(groupId, recordSetKey);
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSet
			getRecordSet(String uuid, long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRecordSet(uuid, recordSetId);
	}

	/**
	 * Returns all the record sets belonging the group.
	 *
	 * @return the record sets belonging to the group
	 */
	public static java.util.List
		<com.liferay.dynamic.data.lists.model.DDLRecordSet> getRecordSets(
			long groupId) {

		return getService().getRecordSets(groupId);
	}

	public static java.util.List
		<com.liferay.dynamic.data.lists.model.DDLRecordSet> getRecordSets(
			long groupId, int start, int end) {

		return getService().getRecordSets(groupId, start, end);
	}

	/**
	 * Returns the number of all the record sets belonging the group.
	 *
	 * @param groupId the primary key of the record set's group
	 * @return the number of record sets belonging to the group
	 */
	public static int getRecordSetsCount(long groupId) {
		return getService().getRecordSetsCount(groupId);
	}

	/**
	 * Returns the number of all the record sets belonging the group and
	 * associated with the DDMStructure.
	 *
	 * @param groupId the primary key of the record set's group
	 * @return the number of record sets belonging to the group
	 */
	public static int getRecordSetsCount(
		long groupId, long ddmStructureId, boolean andOperator) {

		return getService().getRecordSetsCount(
			groupId, ddmStructureId, andOperator);
	}

	/**
	 * Returns the record set's settings as a DDMFormValues object. For more
	 * information see <code>DDMFormValues</code> in the
	 * <code>dynamic.data.mapping.api</code> module.
	 *
	 * @param recordSet the record set
	 * @return the record set settings as a DDMFormValues object
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.dynamic.data.mapping.storage.DDMFormValues
			getRecordSetSettingsDDMFormValues(
				com.liferay.dynamic.data.lists.model.DDLRecordSet recordSet)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRecordSetSettingsDDMFormValues(recordSet);
	}

	/**
	 * Returns the record set's settings.
	 *
	 * @param recordSet the record set
	 * @return the record set settings
	 * @throws PortalException if a portal exception occurred
	 * @see #getRecordSetSettingsDDMFormValues(DDLRecordSet)
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordSetSettings
			getRecordSetSettingsModel(
				com.liferay.dynamic.data.lists.model.DDLRecordSet recordSet)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRecordSetSettingsModel(recordSet);
	}

	/**
	 * Returns a range of all record sets matching the parameters, including a
	 * keywords parameter for matching string values to the record set's name or
	 * description.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to <code>QueryUtil.ALL_POS</code> will return the
	 * full result set.
	 * </p>
	 *
	 * @param companyId the primary key of the record set's company
	 * @param groupId the primary key of the record set's group
	 * @param keywords the keywords (space separated) to look for and match in
	 the record set name or description (optionally
	 <code>null</code>). If the keywords value is not
	 <code>null</code>, the search uses the OR operator in connecting
	 query criteria; otherwise it uses the AND operator.
	 * @param scope the record set's scope. A constant used to scope the record
	 set's data. For more information search the
	 <code>dynamic.data.lists.api</code> module's
	 <code>DDLRecordSetConstants</code> class for constants prefixed
	 with "SCOPE_".
	 * @param start the lower bound of the range of record sets to return
	 * @param end the upper bound of the range of recor sets to return (not
	 inclusive)
	 * @param orderByComparator the comparator to order the record sets
	 * @return the range of matching record sets ordered by the comparator
	 */
	public static java.util.List
		<com.liferay.dynamic.data.lists.model.DDLRecordSet> search(
			long companyId, long groupId, String keywords, int scope, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.lists.model.DDLRecordSet>
					orderByComparator) {

		return getService().search(
			companyId, groupId, keywords, scope, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of record sets. Company ID and group ID must be
	 * matched. If the and operator is set to <code>true</code>, only record
	 * sets with a matching name, description, and scope are returned. If the
	 * and operator is set to <code>false</code>, only one parameter of name,
	 * description, and scope is needed to return matching record sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to <code>QueryUtil.ALL_POS</code> will return the
	 * full result set.
	 * </p>
	 *
	 * @param companyId the primary key of the record set's company
	 * @param groupId the primary key of the record set's group
	 * @param name the name keywords (space separated, optionally
	 <code>null</code>)
	 * @param description the description keywords (space separated, optionally
	 <code>null</code>)
	 * @param scope the record set's scope. A constant used to scope the record
	 set's data. For more information search the
	 <code>dynamic.data.lists.api</code> module's
	 <code>DDLRecordSetConstants</code> class for constants prefixed
	 with "SCOPE_".
	 * @param andOperator whether every field must match its value or keywords,
	 or just one field must match. Company and group must match their
	 values.
	 * @param start the lower bound of the range of record sets to return
	 * @param end the upper bound of the range of recor sets to return (not
	 inclusive)
	 * @param orderByComparator the comparator to order the record sets
	 * @return the range of matching record sets ordered by the comparator
	 */
	public static java.util.List
		<com.liferay.dynamic.data.lists.model.DDLRecordSet> search(
			long companyId, long groupId, String name, String description,
			int scope, boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.lists.model.DDLRecordSet>
					orderByComparator) {

		return getService().search(
			companyId, groupId, name, description, scope, andOperator, start,
			end, orderByComparator);
	}

	/**
	 * Returns the number of record sets matching the parameters. The keywords
	 * parameter is used for matching the record set's name or description
	 *
	 * @param companyId the primary key of the record set's company
	 * @param groupId the primary key of the record set's group.
	 * @param keywords the keywords (space separated) to look for and match in
	 the record set name or description (optionally
	 <code>null</code>). If the keywords value is not
	 <code>null</code>, the OR operator is used in connecting query
	 criteria; otherwise it uses the AND operator.
	 * @param scope the record set's scope. A constant used to scope the record
	 set's data. For more information search the
	 <code>dynamic.data.lists.api</code> module's
	 <code>DDLRecordSetConstants</code> class for constants prefixed
	 with "SCOPE_".
	 * @return the number of matching record sets
	 */
	public static int searchCount(
		long companyId, long groupId, String keywords, int scope) {

		return getService().searchCount(companyId, groupId, keywords, scope);
	}

	/**
	 * Returns the number of all record sets matching the parameters. name and
	 * description keywords. Company ID and group ID must be matched. If the and
	 * operator is set to <code>true</code>, only record sets with a matching
	 * name, description, and scope are counted. If the and operator is set to
	 * <code>false</code>, only one parameter of name, description, and scope is
	 * needed to count matching record sets.
	 *
	 * @param companyId the primary key of the record set's company
	 * @param groupId the primary key of the record set's group
	 * @param name the name keywords (space separated). This can be
	 <code>null</code>.
	 * @param description the description keywords (space separated). This can
	 be <code>null</code>.
	 * @param scope the record set's scope. A constant used to scope the record
	 set's data. For more information search the
	 <code>dynamic.data.lists.api</code> module's
	 <code>DDLRecordSetConstants</code> class for constants prefixed
	 with "SCOPE_".
	 * @param andOperator whether every field must match its value or keywords,
	 or just one field must match. Company and group must match their
	 values.
	 * @return the number of matching record sets
	 */
	public static int searchCount(
		long companyId, long groupId, String name, String description,
		int scope, boolean andOperator) {

		return getService().searchCount(
			companyId, groupId, name, description, scope, andOperator);
	}

	/**
	 * Updates the ddl record set in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ddlRecordSet the ddl record set
	 * @return the ddl record set that was updated
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordSet
		updateDDLRecordSet(
			com.liferay.dynamic.data.lists.model.DDLRecordSet ddlRecordSet) {

		return getService().updateDDLRecordSet(ddlRecordSet);
	}

	/**
	 * Updates the number of minimum rows to display for the record set. Useful
	 * when the record set is being displayed in spreadsheet.
	 *
	 * @param recordSetId the primary key of the record set
	 * @param minDisplayRows the record set's minimum number of rows to be
	 displayed in spreadsheet view
	 * @param serviceContext the service context to be applied. This can set
	 the record set modified date.
	 * @return the record set
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordSet
			updateMinDisplayRows(
				long recordSetId, int minDisplayRows,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateMinDisplayRows(
			recordSetId, minDisplayRows, serviceContext);
	}

	/**
	 * Updates the the record set's settings.
	 *
	 * @param recordSetId the primary key of the record set
	 * @param settingsDDMFormValues the record set's settings. For more
	 information see <code>DDMFormValues</code> in the
	 <code>dynamic.data.mapping.api</code> the module.
	 * @return the record set
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordSet
			updateRecordSet(
				long recordSetId,
				com.liferay.dynamic.data.mapping.storage.DDMFormValues
					settingsDDMFormValues)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateRecordSet(recordSetId, settingsDDMFormValues);
	}

	/**
	 * Updates the DDM structure, name, description, and minimum number of
	 * display rows for the record set matching the record set ID.
	 *
	 * @param recordSetId the primary key of the record set
	 * @param ddmStructureId the primary key of the record set's DDM structure
	 * @param nameMap the record set's locales and localized names
	 * @param descriptionMap the record set's locales and localized
	 descriptions
	 * @param minDisplayRows the record set's minimum number of rows to be
	 displayed in spreadsheet view
	 * @param serviceContext the service context to be applied. This can set
	 the record set modified date.
	 * @return the record set
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordSet
			updateRecordSet(
				long recordSetId, long ddmStructureId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				int minDisplayRows,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateRecordSet(
			recordSetId, ddmStructureId, nameMap, descriptionMap,
			minDisplayRows, serviceContext);
	}

	/**
	 * Updates the DDM strucutre, name, description, and minimum number of
	 * display rows for the record set matching the record set key and group ID.
	 *
	 * @param groupId the primary key of the record set's group
	 * @param ddmStructureId the primary key of the record set's DDM structure
	 * @param recordSetKey the record set's mnemonic primary key
	 * @param nameMap the record set's locales and localized names
	 * @param descriptionMap the record set's locales and localized
	 descriptions
	 * @param minDisplayRows the record set's minimum number of rows to be
	 displayed in spreadsheet view
	 * @param serviceContext the service context to be applied. This can set
	 the record set modified date.
	 * @return the record set
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordSet
			updateRecordSet(
				long groupId, long ddmStructureId, String recordSetKey,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				int minDisplayRows,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateRecordSet(
			groupId, ddmStructureId, recordSetKey, nameMap, descriptionMap,
			minDisplayRows, serviceContext);
	}

	public static DDLRecordSetLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DDLRecordSetLocalService, DDLRecordSetLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DDLRecordSetLocalService.class);

		ServiceTracker<DDLRecordSetLocalService, DDLRecordSetLocalService>
			serviceTracker =
				new ServiceTracker
					<DDLRecordSetLocalService, DDLRecordSetLocalService>(
						bundle.getBundleContext(),
						DDLRecordSetLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
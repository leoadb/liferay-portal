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

package com.liferay.dynamic.data.lists.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.lists.model.DDLRecordSetVersion;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the ddl record set version service. This utility wraps {@link com.liferay.dynamic.data.lists.service.persistence.impl.DDLRecordSetVersionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordSetVersionPersistence
 * @see com.liferay.dynamic.data.lists.service.persistence.impl.DDLRecordSetVersionPersistenceImpl
 * @generated
 */
@ProviderType
public class DDLRecordSetVersionUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(DDLRecordSetVersion ddlRecordSetVersion) {
		getPersistence().clearCache(ddlRecordSetVersion);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DDLRecordSetVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDLRecordSetVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDLRecordSetVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DDLRecordSetVersion> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DDLRecordSetVersion update(
		DDLRecordSetVersion ddlRecordSetVersion) {
		return getPersistence().update(ddlRecordSetVersion);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DDLRecordSetVersion update(
		DDLRecordSetVersion ddlRecordSetVersion, ServiceContext serviceContext) {
		return getPersistence().update(ddlRecordSetVersion, serviceContext);
	}

	/**
	* Returns all the ddl record set versions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching ddl record set versions
	*/
	public static List<DDLRecordSetVersion> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the ddl record set versions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of ddl record set versions
	* @param end the upper bound of the range of ddl record set versions (not inclusive)
	* @return the range of matching ddl record set versions
	*/
	public static List<DDLRecordSetVersion> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the ddl record set versions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of ddl record set versions
	* @param end the upper bound of the range of ddl record set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching ddl record set versions
	*/
	public static List<DDLRecordSetVersion> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<DDLRecordSetVersion> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the ddl record set versions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of ddl record set versions
	* @param end the upper bound of the range of ddl record set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching ddl record set versions
	*/
	public static List<DDLRecordSetVersion> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<DDLRecordSetVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first ddl record set version in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddl record set version
	* @throws NoSuchRecordSetVersionException if a matching ddl record set version could not be found
	*/
	public static DDLRecordSetVersion findByUuid_First(java.lang.String uuid,
		OrderByComparator<DDLRecordSetVersion> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetVersionException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first ddl record set version in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddl record set version, or <code>null</code> if a matching ddl record set version could not be found
	*/
	public static DDLRecordSetVersion fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<DDLRecordSetVersion> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last ddl record set version in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddl record set version
	* @throws NoSuchRecordSetVersionException if a matching ddl record set version could not be found
	*/
	public static DDLRecordSetVersion findByUuid_Last(java.lang.String uuid,
		OrderByComparator<DDLRecordSetVersion> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetVersionException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last ddl record set version in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddl record set version, or <code>null</code> if a matching ddl record set version could not be found
	*/
	public static DDLRecordSetVersion fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<DDLRecordSetVersion> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the ddl record set versions before and after the current ddl record set version in the ordered set where uuid = &#63;.
	*
	* @param recordSetVersionId the primary key of the current ddl record set version
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ddl record set version
	* @throws NoSuchRecordSetVersionException if a ddl record set version with the primary key could not be found
	*/
	public static DDLRecordSetVersion[] findByUuid_PrevAndNext(
		long recordSetVersionId, java.lang.String uuid,
		OrderByComparator<DDLRecordSetVersion> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetVersionException {
		return getPersistence()
				   .findByUuid_PrevAndNext(recordSetVersionId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the ddl record set versions where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of ddl record set versions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching ddl record set versions
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the ddl record set version where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchRecordSetVersionException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching ddl record set version
	* @throws NoSuchRecordSetVersionException if a matching ddl record set version could not be found
	*/
	public static DDLRecordSetVersion findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetVersionException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the ddl record set version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching ddl record set version, or <code>null</code> if a matching ddl record set version could not be found
	*/
	public static DDLRecordSetVersion fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the ddl record set version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching ddl record set version, or <code>null</code> if a matching ddl record set version could not be found
	*/
	public static DDLRecordSetVersion fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the ddl record set version where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the ddl record set version that was removed
	*/
	public static DDLRecordSetVersion removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetVersionException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of ddl record set versions where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching ddl record set versions
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the ddl record set versions where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching ddl record set versions
	*/
	public static List<DDLRecordSetVersion> findByUuid_C(
		java.lang.String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the ddl record set versions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of ddl record set versions
	* @param end the upper bound of the range of ddl record set versions (not inclusive)
	* @return the range of matching ddl record set versions
	*/
	public static List<DDLRecordSetVersion> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the ddl record set versions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of ddl record set versions
	* @param end the upper bound of the range of ddl record set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching ddl record set versions
	*/
	public static List<DDLRecordSetVersion> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<DDLRecordSetVersion> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the ddl record set versions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of ddl record set versions
	* @param end the upper bound of the range of ddl record set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching ddl record set versions
	*/
	public static List<DDLRecordSetVersion> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<DDLRecordSetVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first ddl record set version in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddl record set version
	* @throws NoSuchRecordSetVersionException if a matching ddl record set version could not be found
	*/
	public static DDLRecordSetVersion findByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<DDLRecordSetVersion> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetVersionException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first ddl record set version in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddl record set version, or <code>null</code> if a matching ddl record set version could not be found
	*/
	public static DDLRecordSetVersion fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<DDLRecordSetVersion> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last ddl record set version in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddl record set version
	* @throws NoSuchRecordSetVersionException if a matching ddl record set version could not be found
	*/
	public static DDLRecordSetVersion findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<DDLRecordSetVersion> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetVersionException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last ddl record set version in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddl record set version, or <code>null</code> if a matching ddl record set version could not be found
	*/
	public static DDLRecordSetVersion fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<DDLRecordSetVersion> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the ddl record set versions before and after the current ddl record set version in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param recordSetVersionId the primary key of the current ddl record set version
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ddl record set version
	* @throws NoSuchRecordSetVersionException if a ddl record set version with the primary key could not be found
	*/
	public static DDLRecordSetVersion[] findByUuid_C_PrevAndNext(
		long recordSetVersionId, java.lang.String uuid, long companyId,
		OrderByComparator<DDLRecordSetVersion> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetVersionException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(recordSetVersionId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the ddl record set versions where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of ddl record set versions where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching ddl record set versions
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the ddl record set versions where recordSetId = &#63;.
	*
	* @param recordSetId the record set ID
	* @return the matching ddl record set versions
	*/
	public static List<DDLRecordSetVersion> findByRecordSetId(long recordSetId) {
		return getPersistence().findByRecordSetId(recordSetId);
	}

	/**
	* Returns a range of all the ddl record set versions where recordSetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param start the lower bound of the range of ddl record set versions
	* @param end the upper bound of the range of ddl record set versions (not inclusive)
	* @return the range of matching ddl record set versions
	*/
	public static List<DDLRecordSetVersion> findByRecordSetId(
		long recordSetId, int start, int end) {
		return getPersistence().findByRecordSetId(recordSetId, start, end);
	}

	/**
	* Returns an ordered range of all the ddl record set versions where recordSetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param start the lower bound of the range of ddl record set versions
	* @param end the upper bound of the range of ddl record set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching ddl record set versions
	*/
	public static List<DDLRecordSetVersion> findByRecordSetId(
		long recordSetId, int start, int end,
		OrderByComparator<DDLRecordSetVersion> orderByComparator) {
		return getPersistence()
				   .findByRecordSetId(recordSetId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the ddl record set versions where recordSetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param start the lower bound of the range of ddl record set versions
	* @param end the upper bound of the range of ddl record set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching ddl record set versions
	*/
	public static List<DDLRecordSetVersion> findByRecordSetId(
		long recordSetId, int start, int end,
		OrderByComparator<DDLRecordSetVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByRecordSetId(recordSetId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first ddl record set version in the ordered set where recordSetId = &#63;.
	*
	* @param recordSetId the record set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddl record set version
	* @throws NoSuchRecordSetVersionException if a matching ddl record set version could not be found
	*/
	public static DDLRecordSetVersion findByRecordSetId_First(
		long recordSetId,
		OrderByComparator<DDLRecordSetVersion> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetVersionException {
		return getPersistence()
				   .findByRecordSetId_First(recordSetId, orderByComparator);
	}

	/**
	* Returns the first ddl record set version in the ordered set where recordSetId = &#63;.
	*
	* @param recordSetId the record set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddl record set version, or <code>null</code> if a matching ddl record set version could not be found
	*/
	public static DDLRecordSetVersion fetchByRecordSetId_First(
		long recordSetId,
		OrderByComparator<DDLRecordSetVersion> orderByComparator) {
		return getPersistence()
				   .fetchByRecordSetId_First(recordSetId, orderByComparator);
	}

	/**
	* Returns the last ddl record set version in the ordered set where recordSetId = &#63;.
	*
	* @param recordSetId the record set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddl record set version
	* @throws NoSuchRecordSetVersionException if a matching ddl record set version could not be found
	*/
	public static DDLRecordSetVersion findByRecordSetId_Last(long recordSetId,
		OrderByComparator<DDLRecordSetVersion> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetVersionException {
		return getPersistence()
				   .findByRecordSetId_Last(recordSetId, orderByComparator);
	}

	/**
	* Returns the last ddl record set version in the ordered set where recordSetId = &#63;.
	*
	* @param recordSetId the record set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddl record set version, or <code>null</code> if a matching ddl record set version could not be found
	*/
	public static DDLRecordSetVersion fetchByRecordSetId_Last(
		long recordSetId,
		OrderByComparator<DDLRecordSetVersion> orderByComparator) {
		return getPersistence()
				   .fetchByRecordSetId_Last(recordSetId, orderByComparator);
	}

	/**
	* Returns the ddl record set versions before and after the current ddl record set version in the ordered set where recordSetId = &#63;.
	*
	* @param recordSetVersionId the primary key of the current ddl record set version
	* @param recordSetId the record set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ddl record set version
	* @throws NoSuchRecordSetVersionException if a ddl record set version with the primary key could not be found
	*/
	public static DDLRecordSetVersion[] findByRecordSetId_PrevAndNext(
		long recordSetVersionId, long recordSetId,
		OrderByComparator<DDLRecordSetVersion> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetVersionException {
		return getPersistence()
				   .findByRecordSetId_PrevAndNext(recordSetVersionId,
			recordSetId, orderByComparator);
	}

	/**
	* Removes all the ddl record set versions where recordSetId = &#63; from the database.
	*
	* @param recordSetId the record set ID
	*/
	public static void removeByRecordSetId(long recordSetId) {
		getPersistence().removeByRecordSetId(recordSetId);
	}

	/**
	* Returns the number of ddl record set versions where recordSetId = &#63;.
	*
	* @param recordSetId the record set ID
	* @return the number of matching ddl record set versions
	*/
	public static int countByRecordSetId(long recordSetId) {
		return getPersistence().countByRecordSetId(recordSetId);
	}

	/**
	* Returns all the ddl record set versions where groupId = &#63; and companyId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @return the matching ddl record set versions
	*/
	public static List<DDLRecordSetVersion> findByG_C(long groupId,
		long companyId) {
		return getPersistence().findByG_C(groupId, companyId);
	}

	/**
	* Returns a range of all the ddl record set versions where groupId = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param start the lower bound of the range of ddl record set versions
	* @param end the upper bound of the range of ddl record set versions (not inclusive)
	* @return the range of matching ddl record set versions
	*/
	public static List<DDLRecordSetVersion> findByG_C(long groupId,
		long companyId, int start, int end) {
		return getPersistence().findByG_C(groupId, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the ddl record set versions where groupId = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param start the lower bound of the range of ddl record set versions
	* @param end the upper bound of the range of ddl record set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching ddl record set versions
	*/
	public static List<DDLRecordSetVersion> findByG_C(long groupId,
		long companyId, int start, int end,
		OrderByComparator<DDLRecordSetVersion> orderByComparator) {
		return getPersistence()
				   .findByG_C(groupId, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the ddl record set versions where groupId = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param start the lower bound of the range of ddl record set versions
	* @param end the upper bound of the range of ddl record set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching ddl record set versions
	*/
	public static List<DDLRecordSetVersion> findByG_C(long groupId,
		long companyId, int start, int end,
		OrderByComparator<DDLRecordSetVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C(groupId, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first ddl record set version in the ordered set where groupId = &#63; and companyId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddl record set version
	* @throws NoSuchRecordSetVersionException if a matching ddl record set version could not be found
	*/
	public static DDLRecordSetVersion findByG_C_First(long groupId,
		long companyId, OrderByComparator<DDLRecordSetVersion> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetVersionException {
		return getPersistence()
				   .findByG_C_First(groupId, companyId, orderByComparator);
	}

	/**
	* Returns the first ddl record set version in the ordered set where groupId = &#63; and companyId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddl record set version, or <code>null</code> if a matching ddl record set version could not be found
	*/
	public static DDLRecordSetVersion fetchByG_C_First(long groupId,
		long companyId, OrderByComparator<DDLRecordSetVersion> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_First(groupId, companyId, orderByComparator);
	}

	/**
	* Returns the last ddl record set version in the ordered set where groupId = &#63; and companyId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddl record set version
	* @throws NoSuchRecordSetVersionException if a matching ddl record set version could not be found
	*/
	public static DDLRecordSetVersion findByG_C_Last(long groupId,
		long companyId, OrderByComparator<DDLRecordSetVersion> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetVersionException {
		return getPersistence()
				   .findByG_C_Last(groupId, companyId, orderByComparator);
	}

	/**
	* Returns the last ddl record set version in the ordered set where groupId = &#63; and companyId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddl record set version, or <code>null</code> if a matching ddl record set version could not be found
	*/
	public static DDLRecordSetVersion fetchByG_C_Last(long groupId,
		long companyId, OrderByComparator<DDLRecordSetVersion> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_Last(groupId, companyId, orderByComparator);
	}

	/**
	* Returns the ddl record set versions before and after the current ddl record set version in the ordered set where groupId = &#63; and companyId = &#63;.
	*
	* @param recordSetVersionId the primary key of the current ddl record set version
	* @param groupId the group ID
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ddl record set version
	* @throws NoSuchRecordSetVersionException if a ddl record set version with the primary key could not be found
	*/
	public static DDLRecordSetVersion[] findByG_C_PrevAndNext(
		long recordSetVersionId, long groupId, long companyId,
		OrderByComparator<DDLRecordSetVersion> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetVersionException {
		return getPersistence()
				   .findByG_C_PrevAndNext(recordSetVersionId, groupId,
			companyId, orderByComparator);
	}

	/**
	* Removes all the ddl record set versions where groupId = &#63; and companyId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	*/
	public static void removeByG_C(long groupId, long companyId) {
		getPersistence().removeByG_C(groupId, companyId);
	}

	/**
	* Returns the number of ddl record set versions where groupId = &#63; and companyId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @return the number of matching ddl record set versions
	*/
	public static int countByG_C(long groupId, long companyId) {
		return getPersistence().countByG_C(groupId, companyId);
	}

	/**
	* Returns the ddl record set version where recordSetId = &#63; and version = &#63; or throws a {@link NoSuchRecordSetVersionException} if it could not be found.
	*
	* @param recordSetId the record set ID
	* @param version the version
	* @return the matching ddl record set version
	* @throws NoSuchRecordSetVersionException if a matching ddl record set version could not be found
	*/
	public static DDLRecordSetVersion findByRS_V(long recordSetId,
		java.lang.String version)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetVersionException {
		return getPersistence().findByRS_V(recordSetId, version);
	}

	/**
	* Returns the ddl record set version where recordSetId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param recordSetId the record set ID
	* @param version the version
	* @return the matching ddl record set version, or <code>null</code> if a matching ddl record set version could not be found
	*/
	public static DDLRecordSetVersion fetchByRS_V(long recordSetId,
		java.lang.String version) {
		return getPersistence().fetchByRS_V(recordSetId, version);
	}

	/**
	* Returns the ddl record set version where recordSetId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param recordSetId the record set ID
	* @param version the version
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching ddl record set version, or <code>null</code> if a matching ddl record set version could not be found
	*/
	public static DDLRecordSetVersion fetchByRS_V(long recordSetId,
		java.lang.String version, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByRS_V(recordSetId, version, retrieveFromCache);
	}

	/**
	* Removes the ddl record set version where recordSetId = &#63; and version = &#63; from the database.
	*
	* @param recordSetId the record set ID
	* @param version the version
	* @return the ddl record set version that was removed
	*/
	public static DDLRecordSetVersion removeByRS_V(long recordSetId,
		java.lang.String version)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetVersionException {
		return getPersistence().removeByRS_V(recordSetId, version);
	}

	/**
	* Returns the number of ddl record set versions where recordSetId = &#63; and version = &#63;.
	*
	* @param recordSetId the record set ID
	* @param version the version
	* @return the number of matching ddl record set versions
	*/
	public static int countByRS_V(long recordSetId, java.lang.String version) {
		return getPersistence().countByRS_V(recordSetId, version);
	}

	/**
	* Returns all the ddl record set versions where recordSetId = &#63; and status = &#63;.
	*
	* @param recordSetId the record set ID
	* @param status the status
	* @return the matching ddl record set versions
	*/
	public static List<DDLRecordSetVersion> findByRS_S(long recordSetId,
		int status) {
		return getPersistence().findByRS_S(recordSetId, status);
	}

	/**
	* Returns a range of all the ddl record set versions where recordSetId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param status the status
	* @param start the lower bound of the range of ddl record set versions
	* @param end the upper bound of the range of ddl record set versions (not inclusive)
	* @return the range of matching ddl record set versions
	*/
	public static List<DDLRecordSetVersion> findByRS_S(long recordSetId,
		int status, int start, int end) {
		return getPersistence().findByRS_S(recordSetId, status, start, end);
	}

	/**
	* Returns an ordered range of all the ddl record set versions where recordSetId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param status the status
	* @param start the lower bound of the range of ddl record set versions
	* @param end the upper bound of the range of ddl record set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching ddl record set versions
	*/
	public static List<DDLRecordSetVersion> findByRS_S(long recordSetId,
		int status, int start, int end,
		OrderByComparator<DDLRecordSetVersion> orderByComparator) {
		return getPersistence()
				   .findByRS_S(recordSetId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the ddl record set versions where recordSetId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param status the status
	* @param start the lower bound of the range of ddl record set versions
	* @param end the upper bound of the range of ddl record set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching ddl record set versions
	*/
	public static List<DDLRecordSetVersion> findByRS_S(long recordSetId,
		int status, int start, int end,
		OrderByComparator<DDLRecordSetVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByRS_S(recordSetId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first ddl record set version in the ordered set where recordSetId = &#63; and status = &#63;.
	*
	* @param recordSetId the record set ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddl record set version
	* @throws NoSuchRecordSetVersionException if a matching ddl record set version could not be found
	*/
	public static DDLRecordSetVersion findByRS_S_First(long recordSetId,
		int status, OrderByComparator<DDLRecordSetVersion> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetVersionException {
		return getPersistence()
				   .findByRS_S_First(recordSetId, status, orderByComparator);
	}

	/**
	* Returns the first ddl record set version in the ordered set where recordSetId = &#63; and status = &#63;.
	*
	* @param recordSetId the record set ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddl record set version, or <code>null</code> if a matching ddl record set version could not be found
	*/
	public static DDLRecordSetVersion fetchByRS_S_First(long recordSetId,
		int status, OrderByComparator<DDLRecordSetVersion> orderByComparator) {
		return getPersistence()
				   .fetchByRS_S_First(recordSetId, status, orderByComparator);
	}

	/**
	* Returns the last ddl record set version in the ordered set where recordSetId = &#63; and status = &#63;.
	*
	* @param recordSetId the record set ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddl record set version
	* @throws NoSuchRecordSetVersionException if a matching ddl record set version could not be found
	*/
	public static DDLRecordSetVersion findByRS_S_Last(long recordSetId,
		int status, OrderByComparator<DDLRecordSetVersion> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetVersionException {
		return getPersistence()
				   .findByRS_S_Last(recordSetId, status, orderByComparator);
	}

	/**
	* Returns the last ddl record set version in the ordered set where recordSetId = &#63; and status = &#63;.
	*
	* @param recordSetId the record set ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddl record set version, or <code>null</code> if a matching ddl record set version could not be found
	*/
	public static DDLRecordSetVersion fetchByRS_S_Last(long recordSetId,
		int status, OrderByComparator<DDLRecordSetVersion> orderByComparator) {
		return getPersistence()
				   .fetchByRS_S_Last(recordSetId, status, orderByComparator);
	}

	/**
	* Returns the ddl record set versions before and after the current ddl record set version in the ordered set where recordSetId = &#63; and status = &#63;.
	*
	* @param recordSetVersionId the primary key of the current ddl record set version
	* @param recordSetId the record set ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ddl record set version
	* @throws NoSuchRecordSetVersionException if a ddl record set version with the primary key could not be found
	*/
	public static DDLRecordSetVersion[] findByRS_S_PrevAndNext(
		long recordSetVersionId, long recordSetId, int status,
		OrderByComparator<DDLRecordSetVersion> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetVersionException {
		return getPersistence()
				   .findByRS_S_PrevAndNext(recordSetVersionId, recordSetId,
			status, orderByComparator);
	}

	/**
	* Removes all the ddl record set versions where recordSetId = &#63; and status = &#63; from the database.
	*
	* @param recordSetId the record set ID
	* @param status the status
	*/
	public static void removeByRS_S(long recordSetId, int status) {
		getPersistence().removeByRS_S(recordSetId, status);
	}

	/**
	* Returns the number of ddl record set versions where recordSetId = &#63; and status = &#63;.
	*
	* @param recordSetId the record set ID
	* @param status the status
	* @return the number of matching ddl record set versions
	*/
	public static int countByRS_S(long recordSetId, int status) {
		return getPersistence().countByRS_S(recordSetId, status);
	}

	/**
	* Caches the ddl record set version in the entity cache if it is enabled.
	*
	* @param ddlRecordSetVersion the ddl record set version
	*/
	public static void cacheResult(DDLRecordSetVersion ddlRecordSetVersion) {
		getPersistence().cacheResult(ddlRecordSetVersion);
	}

	/**
	* Caches the ddl record set versions in the entity cache if it is enabled.
	*
	* @param ddlRecordSetVersions the ddl record set versions
	*/
	public static void cacheResult(
		List<DDLRecordSetVersion> ddlRecordSetVersions) {
		getPersistence().cacheResult(ddlRecordSetVersions);
	}

	/**
	* Creates a new ddl record set version with the primary key. Does not add the ddl record set version to the database.
	*
	* @param recordSetVersionId the primary key for the new ddl record set version
	* @return the new ddl record set version
	*/
	public static DDLRecordSetVersion create(long recordSetVersionId) {
		return getPersistence().create(recordSetVersionId);
	}

	/**
	* Removes the ddl record set version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recordSetVersionId the primary key of the ddl record set version
	* @return the ddl record set version that was removed
	* @throws NoSuchRecordSetVersionException if a ddl record set version with the primary key could not be found
	*/
	public static DDLRecordSetVersion remove(long recordSetVersionId)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetVersionException {
		return getPersistence().remove(recordSetVersionId);
	}

	public static DDLRecordSetVersion updateImpl(
		DDLRecordSetVersion ddlRecordSetVersion) {
		return getPersistence().updateImpl(ddlRecordSetVersion);
	}

	/**
	* Returns the ddl record set version with the primary key or throws a {@link NoSuchRecordSetVersionException} if it could not be found.
	*
	* @param recordSetVersionId the primary key of the ddl record set version
	* @return the ddl record set version
	* @throws NoSuchRecordSetVersionException if a ddl record set version with the primary key could not be found
	*/
	public static DDLRecordSetVersion findByPrimaryKey(long recordSetVersionId)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordSetVersionException {
		return getPersistence().findByPrimaryKey(recordSetVersionId);
	}

	/**
	* Returns the ddl record set version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param recordSetVersionId the primary key of the ddl record set version
	* @return the ddl record set version, or <code>null</code> if a ddl record set version with the primary key could not be found
	*/
	public static DDLRecordSetVersion fetchByPrimaryKey(long recordSetVersionId) {
		return getPersistence().fetchByPrimaryKey(recordSetVersionId);
	}

	public static java.util.Map<java.io.Serializable, DDLRecordSetVersion> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the ddl record set versions.
	*
	* @return the ddl record set versions
	*/
	public static List<DDLRecordSetVersion> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the ddl record set versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ddl record set versions
	* @param end the upper bound of the range of ddl record set versions (not inclusive)
	* @return the range of ddl record set versions
	*/
	public static List<DDLRecordSetVersion> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the ddl record set versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ddl record set versions
	* @param end the upper bound of the range of ddl record set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of ddl record set versions
	*/
	public static List<DDLRecordSetVersion> findAll(int start, int end,
		OrderByComparator<DDLRecordSetVersion> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the ddl record set versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ddl record set versions
	* @param end the upper bound of the range of ddl record set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of ddl record set versions
	*/
	public static List<DDLRecordSetVersion> findAll(int start, int end,
		OrderByComparator<DDLRecordSetVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the ddl record set versions from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of ddl record set versions.
	*
	* @return the number of ddl record set versions
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static DDLRecordSetVersionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DDLRecordSetVersionPersistence, DDLRecordSetVersionPersistence> _serviceTracker =
		ServiceTrackerFactory.open(DDLRecordSetVersionPersistence.class);
}
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

import com.liferay.dynamic.data.lists.exception.NoSuchRecordVersionException;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the ddl record version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.dynamic.data.lists.service.persistence.impl.DDLRecordVersionPersistenceImpl
 * @see DDLRecordVersionUtil
 * @generated
 */
@ProviderType
public interface DDLRecordVersionPersistence extends BasePersistence<DDLRecordVersion> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDLRecordVersionUtil} to access the ddl record version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the ddl record versions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the ddl record versions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of ddl record versions
	* @param end the upper bound of the range of ddl record versions (not inclusive)
	* @return the range of matching ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the ddl record versions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of ddl record versions
	* @param end the upper bound of the range of ddl record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns an ordered range of all the ddl record versions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of ddl record versions
	* @param end the upper bound of the range of ddl record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first ddl record version in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddl record version
	* @throws NoSuchRecordVersionException if a matching ddl record version could not be found
	*/
	public DDLRecordVersion findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException;

	/**
	* Returns the first ddl record version in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddl record version, or <code>null</code> if a matching ddl record version could not be found
	*/
	public DDLRecordVersion fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns the last ddl record version in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddl record version
	* @throws NoSuchRecordVersionException if a matching ddl record version could not be found
	*/
	public DDLRecordVersion findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException;

	/**
	* Returns the last ddl record version in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddl record version, or <code>null</code> if a matching ddl record version could not be found
	*/
	public DDLRecordVersion fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns the ddl record versions before and after the current ddl record version in the ordered set where uuid = &#63;.
	*
	* @param recordVersionId the primary key of the current ddl record version
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ddl record version
	* @throws NoSuchRecordVersionException if a ddl record version with the primary key could not be found
	*/
	public DDLRecordVersion[] findByUuid_PrevAndNext(long recordVersionId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException;

	/**
	* Removes all the ddl record versions where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of ddl record versions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching ddl record versions
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the ddl record version where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchRecordVersionException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching ddl record version
	* @throws NoSuchRecordVersionException if a matching ddl record version could not be found
	*/
	public DDLRecordVersion findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchRecordVersionException;

	/**
	* Returns the ddl record version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching ddl record version, or <code>null</code> if a matching ddl record version could not be found
	*/
	public DDLRecordVersion fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the ddl record version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching ddl record version, or <code>null</code> if a matching ddl record version could not be found
	*/
	public DDLRecordVersion fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the ddl record version where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the ddl record version that was removed
	*/
	public DDLRecordVersion removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchRecordVersionException;

	/**
	* Returns the number of ddl record versions where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching ddl record versions
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the ddl record versions where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the ddl record versions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of ddl record versions
	* @param end the upper bound of the range of ddl record versions (not inclusive)
	* @return the range of matching ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the ddl record versions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of ddl record versions
	* @param end the upper bound of the range of ddl record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns an ordered range of all the ddl record versions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of ddl record versions
	* @param end the upper bound of the range of ddl record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first ddl record version in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddl record version
	* @throws NoSuchRecordVersionException if a matching ddl record version could not be found
	*/
	public DDLRecordVersion findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException;

	/**
	* Returns the first ddl record version in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddl record version, or <code>null</code> if a matching ddl record version could not be found
	*/
	public DDLRecordVersion fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns the last ddl record version in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddl record version
	* @throws NoSuchRecordVersionException if a matching ddl record version could not be found
	*/
	public DDLRecordVersion findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException;

	/**
	* Returns the last ddl record version in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddl record version, or <code>null</code> if a matching ddl record version could not be found
	*/
	public DDLRecordVersion fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns the ddl record versions before and after the current ddl record version in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param recordVersionId the primary key of the current ddl record version
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ddl record version
	* @throws NoSuchRecordVersionException if a ddl record version with the primary key could not be found
	*/
	public DDLRecordVersion[] findByUuid_C_PrevAndNext(long recordVersionId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException;

	/**
	* Removes all the ddl record versions where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of ddl record versions where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching ddl record versions
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the ddl record versions where recordId = &#63;.
	*
	* @param recordId the record ID
	* @return the matching ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findByRecordId(long recordId);

	/**
	* Returns a range of all the ddl record versions where recordId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordId the record ID
	* @param start the lower bound of the range of ddl record versions
	* @param end the upper bound of the range of ddl record versions (not inclusive)
	* @return the range of matching ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findByRecordId(long recordId,
		int start, int end);

	/**
	* Returns an ordered range of all the ddl record versions where recordId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordId the record ID
	* @param start the lower bound of the range of ddl record versions
	* @param end the upper bound of the range of ddl record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findByRecordId(long recordId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns an ordered range of all the ddl record versions where recordId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordId the record ID
	* @param start the lower bound of the range of ddl record versions
	* @param end the upper bound of the range of ddl record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findByRecordId(long recordId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first ddl record version in the ordered set where recordId = &#63;.
	*
	* @param recordId the record ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddl record version
	* @throws NoSuchRecordVersionException if a matching ddl record version could not be found
	*/
	public DDLRecordVersion findByRecordId_First(long recordId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException;

	/**
	* Returns the first ddl record version in the ordered set where recordId = &#63;.
	*
	* @param recordId the record ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddl record version, or <code>null</code> if a matching ddl record version could not be found
	*/
	public DDLRecordVersion fetchByRecordId_First(long recordId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns the last ddl record version in the ordered set where recordId = &#63;.
	*
	* @param recordId the record ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddl record version
	* @throws NoSuchRecordVersionException if a matching ddl record version could not be found
	*/
	public DDLRecordVersion findByRecordId_Last(long recordId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException;

	/**
	* Returns the last ddl record version in the ordered set where recordId = &#63;.
	*
	* @param recordId the record ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddl record version, or <code>null</code> if a matching ddl record version could not be found
	*/
	public DDLRecordVersion fetchByRecordId_Last(long recordId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns the ddl record versions before and after the current ddl record version in the ordered set where recordId = &#63;.
	*
	* @param recordVersionId the primary key of the current ddl record version
	* @param recordId the record ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ddl record version
	* @throws NoSuchRecordVersionException if a ddl record version with the primary key could not be found
	*/
	public DDLRecordVersion[] findByRecordId_PrevAndNext(long recordVersionId,
		long recordId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException;

	/**
	* Removes all the ddl record versions where recordId = &#63; from the database.
	*
	* @param recordId the record ID
	*/
	public void removeByRecordId(long recordId);

	/**
	* Returns the number of ddl record versions where recordId = &#63;.
	*
	* @param recordId the record ID
	* @return the number of matching ddl record versions
	*/
	public int countByRecordId(long recordId);

	/**
	* Returns all the ddl record versions where groupId = &#63; and companyId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @return the matching ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findByG_C(long groupId,
		long companyId);

	/**
	* Returns a range of all the ddl record versions where groupId = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param start the lower bound of the range of ddl record versions
	* @param end the upper bound of the range of ddl record versions (not inclusive)
	* @return the range of matching ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findByG_C(long groupId,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the ddl record versions where groupId = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param start the lower bound of the range of ddl record versions
	* @param end the upper bound of the range of ddl record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findByG_C(long groupId,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns an ordered range of all the ddl record versions where groupId = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param start the lower bound of the range of ddl record versions
	* @param end the upper bound of the range of ddl record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findByG_C(long groupId,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first ddl record version in the ordered set where groupId = &#63; and companyId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddl record version
	* @throws NoSuchRecordVersionException if a matching ddl record version could not be found
	*/
	public DDLRecordVersion findByG_C_First(long groupId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException;

	/**
	* Returns the first ddl record version in the ordered set where groupId = &#63; and companyId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddl record version, or <code>null</code> if a matching ddl record version could not be found
	*/
	public DDLRecordVersion fetchByG_C_First(long groupId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns the last ddl record version in the ordered set where groupId = &#63; and companyId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddl record version
	* @throws NoSuchRecordVersionException if a matching ddl record version could not be found
	*/
	public DDLRecordVersion findByG_C_Last(long groupId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException;

	/**
	* Returns the last ddl record version in the ordered set where groupId = &#63; and companyId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddl record version, or <code>null</code> if a matching ddl record version could not be found
	*/
	public DDLRecordVersion fetchByG_C_Last(long groupId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns the ddl record versions before and after the current ddl record version in the ordered set where groupId = &#63; and companyId = &#63;.
	*
	* @param recordVersionId the primary key of the current ddl record version
	* @param groupId the group ID
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ddl record version
	* @throws NoSuchRecordVersionException if a ddl record version with the primary key could not be found
	*/
	public DDLRecordVersion[] findByG_C_PrevAndNext(long recordVersionId,
		long groupId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException;

	/**
	* Removes all the ddl record versions where groupId = &#63; and companyId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	*/
	public void removeByG_C(long groupId, long companyId);

	/**
	* Returns the number of ddl record versions where groupId = &#63; and companyId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @return the number of matching ddl record versions
	*/
	public int countByG_C(long groupId, long companyId);

	/**
	* Returns all the ddl record versions where recordSetId = &#63; and recordSetVersion = &#63;.
	*
	* @param recordSetId the record set ID
	* @param recordSetVersion the record set version
	* @return the matching ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findByR_R(long recordSetId,
		java.lang.String recordSetVersion);

	/**
	* Returns a range of all the ddl record versions where recordSetId = &#63; and recordSetVersion = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param recordSetVersion the record set version
	* @param start the lower bound of the range of ddl record versions
	* @param end the upper bound of the range of ddl record versions (not inclusive)
	* @return the range of matching ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findByR_R(long recordSetId,
		java.lang.String recordSetVersion, int start, int end);

	/**
	* Returns an ordered range of all the ddl record versions where recordSetId = &#63; and recordSetVersion = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param recordSetVersion the record set version
	* @param start the lower bound of the range of ddl record versions
	* @param end the upper bound of the range of ddl record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findByR_R(long recordSetId,
		java.lang.String recordSetVersion, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns an ordered range of all the ddl record versions where recordSetId = &#63; and recordSetVersion = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordSetId the record set ID
	* @param recordSetVersion the record set version
	* @param start the lower bound of the range of ddl record versions
	* @param end the upper bound of the range of ddl record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findByR_R(long recordSetId,
		java.lang.String recordSetVersion, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first ddl record version in the ordered set where recordSetId = &#63; and recordSetVersion = &#63;.
	*
	* @param recordSetId the record set ID
	* @param recordSetVersion the record set version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddl record version
	* @throws NoSuchRecordVersionException if a matching ddl record version could not be found
	*/
	public DDLRecordVersion findByR_R_First(long recordSetId,
		java.lang.String recordSetVersion,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException;

	/**
	* Returns the first ddl record version in the ordered set where recordSetId = &#63; and recordSetVersion = &#63;.
	*
	* @param recordSetId the record set ID
	* @param recordSetVersion the record set version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddl record version, or <code>null</code> if a matching ddl record version could not be found
	*/
	public DDLRecordVersion fetchByR_R_First(long recordSetId,
		java.lang.String recordSetVersion,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns the last ddl record version in the ordered set where recordSetId = &#63; and recordSetVersion = &#63;.
	*
	* @param recordSetId the record set ID
	* @param recordSetVersion the record set version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddl record version
	* @throws NoSuchRecordVersionException if a matching ddl record version could not be found
	*/
	public DDLRecordVersion findByR_R_Last(long recordSetId,
		java.lang.String recordSetVersion,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException;

	/**
	* Returns the last ddl record version in the ordered set where recordSetId = &#63; and recordSetVersion = &#63;.
	*
	* @param recordSetId the record set ID
	* @param recordSetVersion the record set version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddl record version, or <code>null</code> if a matching ddl record version could not be found
	*/
	public DDLRecordVersion fetchByR_R_Last(long recordSetId,
		java.lang.String recordSetVersion,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns the ddl record versions before and after the current ddl record version in the ordered set where recordSetId = &#63; and recordSetVersion = &#63;.
	*
	* @param recordVersionId the primary key of the current ddl record version
	* @param recordSetId the record set ID
	* @param recordSetVersion the record set version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ddl record version
	* @throws NoSuchRecordVersionException if a ddl record version with the primary key could not be found
	*/
	public DDLRecordVersion[] findByR_R_PrevAndNext(long recordVersionId,
		long recordSetId, java.lang.String recordSetVersion,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException;

	/**
	* Removes all the ddl record versions where recordSetId = &#63; and recordSetVersion = &#63; from the database.
	*
	* @param recordSetId the record set ID
	* @param recordSetVersion the record set version
	*/
	public void removeByR_R(long recordSetId, java.lang.String recordSetVersion);

	/**
	* Returns the number of ddl record versions where recordSetId = &#63; and recordSetVersion = &#63;.
	*
	* @param recordSetId the record set ID
	* @param recordSetVersion the record set version
	* @return the number of matching ddl record versions
	*/
	public int countByR_R(long recordSetId, java.lang.String recordSetVersion);

	/**
	* Returns the ddl record version where recordId = &#63; and version = &#63; or throws a {@link NoSuchRecordVersionException} if it could not be found.
	*
	* @param recordId the record ID
	* @param version the version
	* @return the matching ddl record version
	* @throws NoSuchRecordVersionException if a matching ddl record version could not be found
	*/
	public DDLRecordVersion findByR_V(long recordId, java.lang.String version)
		throws NoSuchRecordVersionException;

	/**
	* Returns the ddl record version where recordId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param recordId the record ID
	* @param version the version
	* @return the matching ddl record version, or <code>null</code> if a matching ddl record version could not be found
	*/
	public DDLRecordVersion fetchByR_V(long recordId, java.lang.String version);

	/**
	* Returns the ddl record version where recordId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param recordId the record ID
	* @param version the version
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching ddl record version, or <code>null</code> if a matching ddl record version could not be found
	*/
	public DDLRecordVersion fetchByR_V(long recordId, java.lang.String version,
		boolean retrieveFromCache);

	/**
	* Removes the ddl record version where recordId = &#63; and version = &#63; from the database.
	*
	* @param recordId the record ID
	* @param version the version
	* @return the ddl record version that was removed
	*/
	public DDLRecordVersion removeByR_V(long recordId, java.lang.String version)
		throws NoSuchRecordVersionException;

	/**
	* Returns the number of ddl record versions where recordId = &#63; and version = &#63;.
	*
	* @param recordId the record ID
	* @param version the version
	* @return the number of matching ddl record versions
	*/
	public int countByR_V(long recordId, java.lang.String version);

	/**
	* Returns all the ddl record versions where recordId = &#63; and status = &#63;.
	*
	* @param recordId the record ID
	* @param status the status
	* @return the matching ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findByR_S(long recordId, int status);

	/**
	* Returns a range of all the ddl record versions where recordId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordId the record ID
	* @param status the status
	* @param start the lower bound of the range of ddl record versions
	* @param end the upper bound of the range of ddl record versions (not inclusive)
	* @return the range of matching ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findByR_S(long recordId,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the ddl record versions where recordId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordId the record ID
	* @param status the status
	* @param start the lower bound of the range of ddl record versions
	* @param end the upper bound of the range of ddl record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findByR_S(long recordId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns an ordered range of all the ddl record versions where recordId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordId the record ID
	* @param status the status
	* @param start the lower bound of the range of ddl record versions
	* @param end the upper bound of the range of ddl record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findByR_S(long recordId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first ddl record version in the ordered set where recordId = &#63; and status = &#63;.
	*
	* @param recordId the record ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddl record version
	* @throws NoSuchRecordVersionException if a matching ddl record version could not be found
	*/
	public DDLRecordVersion findByR_S_First(long recordId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException;

	/**
	* Returns the first ddl record version in the ordered set where recordId = &#63; and status = &#63;.
	*
	* @param recordId the record ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddl record version, or <code>null</code> if a matching ddl record version could not be found
	*/
	public DDLRecordVersion fetchByR_S_First(long recordId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns the last ddl record version in the ordered set where recordId = &#63; and status = &#63;.
	*
	* @param recordId the record ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddl record version
	* @throws NoSuchRecordVersionException if a matching ddl record version could not be found
	*/
	public DDLRecordVersion findByR_S_Last(long recordId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException;

	/**
	* Returns the last ddl record version in the ordered set where recordId = &#63; and status = &#63;.
	*
	* @param recordId the record ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddl record version, or <code>null</code> if a matching ddl record version could not be found
	*/
	public DDLRecordVersion fetchByR_S_Last(long recordId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns the ddl record versions before and after the current ddl record version in the ordered set where recordId = &#63; and status = &#63;.
	*
	* @param recordVersionId the primary key of the current ddl record version
	* @param recordId the record ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ddl record version
	* @throws NoSuchRecordVersionException if a ddl record version with the primary key could not be found
	*/
	public DDLRecordVersion[] findByR_S_PrevAndNext(long recordVersionId,
		long recordId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException;

	/**
	* Removes all the ddl record versions where recordId = &#63; and status = &#63; from the database.
	*
	* @param recordId the record ID
	* @param status the status
	*/
	public void removeByR_S(long recordId, int status);

	/**
	* Returns the number of ddl record versions where recordId = &#63; and status = &#63;.
	*
	* @param recordId the record ID
	* @param status the status
	* @return the number of matching ddl record versions
	*/
	public int countByR_S(long recordId, int status);

	/**
	* Caches the ddl record version in the entity cache if it is enabled.
	*
	* @param ddlRecordVersion the ddl record version
	*/
	public void cacheResult(DDLRecordVersion ddlRecordVersion);

	/**
	* Caches the ddl record versions in the entity cache if it is enabled.
	*
	* @param ddlRecordVersions the ddl record versions
	*/
	public void cacheResult(java.util.List<DDLRecordVersion> ddlRecordVersions);

	/**
	* Creates a new ddl record version with the primary key. Does not add the ddl record version to the database.
	*
	* @param recordVersionId the primary key for the new ddl record version
	* @return the new ddl record version
	*/
	public DDLRecordVersion create(long recordVersionId);

	/**
	* Removes the ddl record version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recordVersionId the primary key of the ddl record version
	* @return the ddl record version that was removed
	* @throws NoSuchRecordVersionException if a ddl record version with the primary key could not be found
	*/
	public DDLRecordVersion remove(long recordVersionId)
		throws NoSuchRecordVersionException;

	public DDLRecordVersion updateImpl(DDLRecordVersion ddlRecordVersion);

	/**
	* Returns the ddl record version with the primary key or throws a {@link NoSuchRecordVersionException} if it could not be found.
	*
	* @param recordVersionId the primary key of the ddl record version
	* @return the ddl record version
	* @throws NoSuchRecordVersionException if a ddl record version with the primary key could not be found
	*/
	public DDLRecordVersion findByPrimaryKey(long recordVersionId)
		throws NoSuchRecordVersionException;

	/**
	* Returns the ddl record version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param recordVersionId the primary key of the ddl record version
	* @return the ddl record version, or <code>null</code> if a ddl record version with the primary key could not be found
	*/
	public DDLRecordVersion fetchByPrimaryKey(long recordVersionId);

	@Override
	public java.util.Map<java.io.Serializable, DDLRecordVersion> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the ddl record versions.
	*
	* @return the ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findAll();

	/**
	* Returns a range of all the ddl record versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ddl record versions
	* @param end the upper bound of the range of ddl record versions (not inclusive)
	* @return the range of ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findAll(int start, int end);

	/**
	* Returns an ordered range of all the ddl record versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ddl record versions
	* @param end the upper bound of the range of ddl record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns an ordered range of all the ddl record versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ddl record versions
	* @param end the upper bound of the range of ddl record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of ddl record versions
	*/
	public java.util.List<DDLRecordVersion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the ddl record versions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of ddl record versions.
	*
	* @return the number of ddl record versions
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}
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

package com.liferay.report.definitions.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import com.liferay.report.definitions.exception.NoSuchReportDefinitionException;
import com.liferay.report.definitions.model.ReportDefinition;

/**
 * The persistence interface for the report definition service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.report.definitions.service.persistence.impl.ReportDefinitionPersistenceImpl
 * @see ReportDefinitionUtil
 * @generated
 */
@ProviderType
public interface ReportDefinitionPersistence extends BasePersistence<ReportDefinition> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ReportDefinitionUtil} to access the report definition persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the report definitions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching report definitions
	*/
	public java.util.List<ReportDefinition> findByUuid(String uuid);

	/**
	* Returns a range of all the report definitions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ReportDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of report definitions
	* @param end the upper bound of the range of report definitions (not inclusive)
	* @return the range of matching report definitions
	*/
	public java.util.List<ReportDefinition> findByUuid(String uuid, int start,
		int end);

	/**
	* Returns an ordered range of all the report definitions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ReportDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of report definitions
	* @param end the upper bound of the range of report definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching report definitions
	*/
	public java.util.List<ReportDefinition> findByUuid(String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ReportDefinition> orderByComparator);

	/**
	* Returns an ordered range of all the report definitions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ReportDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of report definitions
	* @param end the upper bound of the range of report definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching report definitions
	*/
	public java.util.List<ReportDefinition> findByUuid(String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ReportDefinition> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first report definition in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching report definition
	* @throws NoSuchReportDefinitionException if a matching report definition could not be found
	*/
	public ReportDefinition findByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ReportDefinition> orderByComparator)
		throws NoSuchReportDefinitionException;

	/**
	* Returns the first report definition in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching report definition, or <code>null</code> if a matching report definition could not be found
	*/
	public ReportDefinition fetchByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ReportDefinition> orderByComparator);

	/**
	* Returns the last report definition in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching report definition
	* @throws NoSuchReportDefinitionException if a matching report definition could not be found
	*/
	public ReportDefinition findByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ReportDefinition> orderByComparator)
		throws NoSuchReportDefinitionException;

	/**
	* Returns the last report definition in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching report definition, or <code>null</code> if a matching report definition could not be found
	*/
	public ReportDefinition fetchByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ReportDefinition> orderByComparator);

	/**
	* Returns the report definitions before and after the current report definition in the ordered set where uuid = &#63;.
	*
	* @param reportDefinitionId the primary key of the current report definition
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next report definition
	* @throws NoSuchReportDefinitionException if a report definition with the primary key could not be found
	*/
	public ReportDefinition[] findByUuid_PrevAndNext(long reportDefinitionId,
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ReportDefinition> orderByComparator)
		throws NoSuchReportDefinitionException;

	/**
	* Removes all the report definitions where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(String uuid);

	/**
	* Returns the number of report definitions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching report definitions
	*/
	public int countByUuid(String uuid);

	/**
	* Returns the report definition where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchReportDefinitionException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching report definition
	* @throws NoSuchReportDefinitionException if a matching report definition could not be found
	*/
	public ReportDefinition findByUUID_G(String uuid, long groupId)
		throws NoSuchReportDefinitionException;

	/**
	* Returns the report definition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching report definition, or <code>null</code> if a matching report definition could not be found
	*/
	public ReportDefinition fetchByUUID_G(String uuid, long groupId);

	/**
	* Returns the report definition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching report definition, or <code>null</code> if a matching report definition could not be found
	*/
	public ReportDefinition fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the report definition where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the report definition that was removed
	*/
	public ReportDefinition removeByUUID_G(String uuid, long groupId)
		throws NoSuchReportDefinitionException;

	/**
	* Returns the number of report definitions where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching report definitions
	*/
	public int countByUUID_G(String uuid, long groupId);

	/**
	* Returns all the report definitions where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching report definitions
	*/
	public java.util.List<ReportDefinition> findByUuid_C(String uuid,
		long companyId);

	/**
	* Returns a range of all the report definitions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ReportDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of report definitions
	* @param end the upper bound of the range of report definitions (not inclusive)
	* @return the range of matching report definitions
	*/
	public java.util.List<ReportDefinition> findByUuid_C(String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the report definitions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ReportDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of report definitions
	* @param end the upper bound of the range of report definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching report definitions
	*/
	public java.util.List<ReportDefinition> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ReportDefinition> orderByComparator);

	/**
	* Returns an ordered range of all the report definitions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ReportDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of report definitions
	* @param end the upper bound of the range of report definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching report definitions
	*/
	public java.util.List<ReportDefinition> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ReportDefinition> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first report definition in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching report definition
	* @throws NoSuchReportDefinitionException if a matching report definition could not be found
	*/
	public ReportDefinition findByUuid_C_First(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ReportDefinition> orderByComparator)
		throws NoSuchReportDefinitionException;

	/**
	* Returns the first report definition in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching report definition, or <code>null</code> if a matching report definition could not be found
	*/
	public ReportDefinition fetchByUuid_C_First(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ReportDefinition> orderByComparator);

	/**
	* Returns the last report definition in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching report definition
	* @throws NoSuchReportDefinitionException if a matching report definition could not be found
	*/
	public ReportDefinition findByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ReportDefinition> orderByComparator)
		throws NoSuchReportDefinitionException;

	/**
	* Returns the last report definition in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching report definition, or <code>null</code> if a matching report definition could not be found
	*/
	public ReportDefinition fetchByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ReportDefinition> orderByComparator);

	/**
	* Returns the report definitions before and after the current report definition in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param reportDefinitionId the primary key of the current report definition
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next report definition
	* @throws NoSuchReportDefinitionException if a report definition with the primary key could not be found
	*/
	public ReportDefinition[] findByUuid_C_PrevAndNext(
		long reportDefinitionId, String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ReportDefinition> orderByComparator)
		throws NoSuchReportDefinitionException;

	/**
	* Removes all the report definitions where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(String uuid, long companyId);

	/**
	* Returns the number of report definitions where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching report definitions
	*/
	public int countByUuid_C(String uuid, long companyId);

	/**
	* Returns all the report definitions where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching report definitions
	*/
	public java.util.List<ReportDefinition> findByGroupId(long groupId);

	/**
	* Returns a range of all the report definitions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ReportDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of report definitions
	* @param end the upper bound of the range of report definitions (not inclusive)
	* @return the range of matching report definitions
	*/
	public java.util.List<ReportDefinition> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the report definitions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ReportDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of report definitions
	* @param end the upper bound of the range of report definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching report definitions
	*/
	public java.util.List<ReportDefinition> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ReportDefinition> orderByComparator);

	/**
	* Returns an ordered range of all the report definitions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ReportDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of report definitions
	* @param end the upper bound of the range of report definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching report definitions
	*/
	public java.util.List<ReportDefinition> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ReportDefinition> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first report definition in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching report definition
	* @throws NoSuchReportDefinitionException if a matching report definition could not be found
	*/
	public ReportDefinition findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ReportDefinition> orderByComparator)
		throws NoSuchReportDefinitionException;

	/**
	* Returns the first report definition in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching report definition, or <code>null</code> if a matching report definition could not be found
	*/
	public ReportDefinition fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ReportDefinition> orderByComparator);

	/**
	* Returns the last report definition in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching report definition
	* @throws NoSuchReportDefinitionException if a matching report definition could not be found
	*/
	public ReportDefinition findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ReportDefinition> orderByComparator)
		throws NoSuchReportDefinitionException;

	/**
	* Returns the last report definition in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching report definition, or <code>null</code> if a matching report definition could not be found
	*/
	public ReportDefinition fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ReportDefinition> orderByComparator);

	/**
	* Returns the report definitions before and after the current report definition in the ordered set where groupId = &#63;.
	*
	* @param reportDefinitionId the primary key of the current report definition
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next report definition
	* @throws NoSuchReportDefinitionException if a report definition with the primary key could not be found
	*/
	public ReportDefinition[] findByGroupId_PrevAndNext(
		long reportDefinitionId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ReportDefinition> orderByComparator)
		throws NoSuchReportDefinitionException;

	/**
	* Removes all the report definitions where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of report definitions where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching report definitions
	*/
	public int countByGroupId(long groupId);

	/**
	* Caches the report definition in the entity cache if it is enabled.
	*
	* @param reportDefinition the report definition
	*/
	public void cacheResult(ReportDefinition reportDefinition);

	/**
	* Caches the report definitions in the entity cache if it is enabled.
	*
	* @param reportDefinitions the report definitions
	*/
	public void cacheResult(java.util.List<ReportDefinition> reportDefinitions);

	/**
	* Creates a new report definition with the primary key. Does not add the report definition to the database.
	*
	* @param reportDefinitionId the primary key for the new report definition
	* @return the new report definition
	*/
	public ReportDefinition create(long reportDefinitionId);

	/**
	* Removes the report definition with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param reportDefinitionId the primary key of the report definition
	* @return the report definition that was removed
	* @throws NoSuchReportDefinitionException if a report definition with the primary key could not be found
	*/
	public ReportDefinition remove(long reportDefinitionId)
		throws NoSuchReportDefinitionException;

	public ReportDefinition updateImpl(ReportDefinition reportDefinition);

	/**
	* Returns the report definition with the primary key or throws a {@link NoSuchReportDefinitionException} if it could not be found.
	*
	* @param reportDefinitionId the primary key of the report definition
	* @return the report definition
	* @throws NoSuchReportDefinitionException if a report definition with the primary key could not be found
	*/
	public ReportDefinition findByPrimaryKey(long reportDefinitionId)
		throws NoSuchReportDefinitionException;

	/**
	* Returns the report definition with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param reportDefinitionId the primary key of the report definition
	* @return the report definition, or <code>null</code> if a report definition with the primary key could not be found
	*/
	public ReportDefinition fetchByPrimaryKey(long reportDefinitionId);

	@Override
	public java.util.Map<java.io.Serializable, ReportDefinition> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the report definitions.
	*
	* @return the report definitions
	*/
	public java.util.List<ReportDefinition> findAll();

	/**
	* Returns a range of all the report definitions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ReportDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of report definitions
	* @param end the upper bound of the range of report definitions (not inclusive)
	* @return the range of report definitions
	*/
	public java.util.List<ReportDefinition> findAll(int start, int end);

	/**
	* Returns an ordered range of all the report definitions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ReportDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of report definitions
	* @param end the upper bound of the range of report definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of report definitions
	*/
	public java.util.List<ReportDefinition> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ReportDefinition> orderByComparator);

	/**
	* Returns an ordered range of all the report definitions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ReportDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of report definitions
	* @param end the upper bound of the range of report definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of report definitions
	*/
	public java.util.List<ReportDefinition> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ReportDefinition> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the report definitions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of report definitions.
	*
	* @return the number of report definitions
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}
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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceShipmentItemLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentItemLocalService
 * @generated
 */
@ProviderType
public class CommerceShipmentItemLocalServiceWrapper
	implements CommerceShipmentItemLocalService,
		ServiceWrapper<CommerceShipmentItemLocalService> {
	public CommerceShipmentItemLocalServiceWrapper(
		CommerceShipmentItemLocalService commerceShipmentItemLocalService) {
		_commerceShipmentItemLocalService = commerceShipmentItemLocalService;
	}

	/**
	* Adds the commerce shipment item to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceShipmentItem the commerce shipment item
	* @return the commerce shipment item that was added
	*/
	@Override
	public com.liferay.commerce.model.CommerceShipmentItem addCommerceShipmentItem(
		com.liferay.commerce.model.CommerceShipmentItem commerceShipmentItem) {
		return _commerceShipmentItemLocalService.addCommerceShipmentItem(commerceShipmentItem);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipmentItem addCommerceShipmentItem(
		long commerceShipmentId, long commerceOrderItemId, int quantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShipmentItemLocalService.addCommerceShipmentItem(commerceShipmentId,
			commerceOrderItemId, quantity, serviceContext);
	}

	/**
	* Creates a new commerce shipment item with the primary key. Does not add the commerce shipment item to the database.
	*
	* @param commerceShipmentItemId the primary key for the new commerce shipment item
	* @return the new commerce shipment item
	*/
	@Override
	public com.liferay.commerce.model.CommerceShipmentItem createCommerceShipmentItem(
		long commerceShipmentItemId) {
		return _commerceShipmentItemLocalService.createCommerceShipmentItem(commerceShipmentItemId);
	}

	/**
	* Deletes the commerce shipment item from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceShipmentItem the commerce shipment item
	* @return the commerce shipment item that was removed
	*/
	@Override
	public com.liferay.commerce.model.CommerceShipmentItem deleteCommerceShipmentItem(
		com.liferay.commerce.model.CommerceShipmentItem commerceShipmentItem) {
		return _commerceShipmentItemLocalService.deleteCommerceShipmentItem(commerceShipmentItem);
	}

	/**
	* Deletes the commerce shipment item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceShipmentItemId the primary key of the commerce shipment item
	* @return the commerce shipment item that was removed
	* @throws PortalException if a commerce shipment item with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceShipmentItem deleteCommerceShipmentItem(
		long commerceShipmentItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShipmentItemLocalService.deleteCommerceShipmentItem(commerceShipmentItemId);
	}

	@Override
	public void deleteCommerceShipmentItems(long commerceShipment) {
		_commerceShipmentItemLocalService.deleteCommerceShipmentItems(commerceShipment);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShipmentItemLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceShipmentItemLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _commerceShipmentItemLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _commerceShipmentItemLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _commerceShipmentItemLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _commerceShipmentItemLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _commerceShipmentItemLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipmentItem fetchCommerceShipmentItem(
		long commerceShipmentItemId) {
		return _commerceShipmentItemLocalService.fetchCommerceShipmentItem(commerceShipmentItemId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceShipmentItemLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the commerce shipment item with the primary key.
	*
	* @param commerceShipmentItemId the primary key of the commerce shipment item
	* @return the commerce shipment item
	* @throws PortalException if a commerce shipment item with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceShipmentItem getCommerceShipmentItem(
		long commerceShipmentItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShipmentItemLocalService.getCommerceShipmentItem(commerceShipmentItemId);
	}

	/**
	* Returns a range of all the commerce shipment items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce shipment items
	* @param end the upper bound of the range of commerce shipment items (not inclusive)
	* @return the range of commerce shipment items
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceShipmentItem> getCommerceShipmentItems(
		int start, int end) {
		return _commerceShipmentItemLocalService.getCommerceShipmentItems(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceShipmentItem> getCommerceShipmentItems(
		long commerceShipmentId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceShipmentItem> orderByComparator) {
		return _commerceShipmentItemLocalService.getCommerceShipmentItems(commerceShipmentId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce shipment items.
	*
	* @return the number of commerce shipment items
	*/
	@Override
	public int getCommerceShipmentItemsCount() {
		return _commerceShipmentItemLocalService.getCommerceShipmentItemsCount();
	}

	@Override
	public int getCommerceShipmentItemsCount(long commerceShipmentId) {
		return _commerceShipmentItemLocalService.getCommerceShipmentItemsCount(commerceShipmentId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceShipmentItemLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceShipmentItemLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShipmentItemLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce shipment item in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceShipmentItem the commerce shipment item
	* @return the commerce shipment item that was updated
	*/
	@Override
	public com.liferay.commerce.model.CommerceShipmentItem updateCommerceShipmentItem(
		com.liferay.commerce.model.CommerceShipmentItem commerceShipmentItem) {
		return _commerceShipmentItemLocalService.updateCommerceShipmentItem(commerceShipmentItem);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipmentItem updateCommerceShipmentItem(
		long commerceShipmentItemId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShipmentItemLocalService.updateCommerceShipmentItem(commerceShipmentItemId,
			quantity);
	}

	@Override
	public CommerceShipmentItemLocalService getWrappedService() {
		return _commerceShipmentItemLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceShipmentItemLocalService commerceShipmentItemLocalService) {
		_commerceShipmentItemLocalService = commerceShipmentItemLocalService;
	}

	private CommerceShipmentItemLocalService _commerceShipmentItemLocalService;
}
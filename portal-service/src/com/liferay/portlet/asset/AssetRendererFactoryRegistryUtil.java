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

package com.liferay.portlet.asset;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;
import com.liferay.registry.collections.ServiceRegistrationMapImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Bruno Farache
 * @author Marcellus Tavares
 */
@ProviderType
public class AssetRendererFactoryRegistryUtil {

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #getAssetRendererFactories(long)}
	 */
	@Deprecated
	public static List<AssetRendererFactory<?>> getAssetRendererFactories() {
		return _instance._getAssetRendererFactories();
	}

	public static List<AssetRendererFactory<?>> getAssetRendererFactories(
		long companyId) {

		return _instance._getAssetRendererFactories(companyId);
	}

	public static List<AssetRendererFactory<?>>
		getAssetRendererFactoriesByClassName(String className) {

		return _instance._assetRenderFactoriesMapByClassName.get(className);
	}

	public static <T> AssetRendererFactory<T> getAssetRendererFactoryByClass(
		Class<T> clazz) {

		return _instance._getAssetRendererFactoryByClass(clazz);
	}

	public static AssetRendererFactory<?> getAssetRendererFactoryByClassName(
		String className) {

		return _instance._getAssetRendererFactoryByClassName(className);
	}

	public static AssetRendererFactory<?> getAssetRendererFactoryByClassNameId(
		long classNameId) {

		return _instance._getAssetRendererFactoryByClassNameId(classNameId);
	}

	public static AssetRendererFactory<?> getAssetRendererFactoryByType(
		String type) {

		return _instance._getAssetRendererFactoryByType(type);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getClassNameIds( long)}
	 */
	@Deprecated
	public static long[] getClassNameIds() {
		return _instance._getClassNameIds(0, false);
	}

	public static long[] getClassNameIds(long companyId) {
		return _instance._getClassNameIds(companyId, false);
	}

	public static long[] getClassNameIds(
		long companyId, boolean filterSelectable) {

		return _instance._getClassNameIds(companyId, filterSelectable);
	}

	public static void register(AssetRendererFactory<?> assetRendererFactory) {
		_instance._register(assetRendererFactory);
	}

	public static void register(
		List<AssetRendererFactory<?>> assetRendererFactories) {

		for (AssetRendererFactory<?> assetRendererFactory :
				assetRendererFactories) {

			register(assetRendererFactory);
		}
	}

	public static void unregister(
		AssetRendererFactory<?> assetRendererFactory) {

		_instance._unregister(assetRendererFactory);
	}

	public static void unregister(
		List<AssetRendererFactory<?>> assetRendererFactories) {

		for (AssetRendererFactory<?> assetRendererFactory :
				assetRendererFactories) {

			unregister(assetRendererFactory);
		}
	}

	private AssetRendererFactoryRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			(Class<AssetRendererFactory<?>>)(Class<?>)
				AssetRendererFactory.class,
			new AssetRendererFactoryServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private List<AssetRendererFactory<?>> _createListFromMapValues(
		Map<String, List<AssetRendererFactory<?>>> map) {

		List<AssetRendererFactory<?>> assetRendererFactories =
			new ArrayList<>();

		for (Map.Entry<String, List<AssetRendererFactory<?>>> entry :
				map.entrySet()) {

			assetRendererFactories.addAll(entry.getValue());
		}

		return assetRendererFactories;
	}

	private List<AssetRendererFactory<?>> _filterAssetRendererFactories(
		long companyId, List<AssetRendererFactory<?>> assetRendererFactories,
		boolean filterSelectable) {

		List<AssetRendererFactory<?>> filteredAssetRendererFactories =
			new ArrayList<>();

		for (AssetRendererFactory<?> assetRendererFactory :
				assetRendererFactories) {

			if (assetRendererFactory.isActive(companyId) &&
				(!filterSelectable || assetRendererFactory.isSelectable())) {

				filteredAssetRendererFactories.add(assetRendererFactory);
			}
		}

		return filteredAssetRendererFactories;
	}

	private Map<String, List<AssetRendererFactory<?>>>
		_filterAssetRendererFactories(
			long companyId,
			Map<String, List<AssetRendererFactory<?>>> assetRendererFactories,
			boolean filterSelectable) {

		Map<String, List<AssetRendererFactory<?>>>
			filteredAssetRendererFactories = new ConcurrentHashMap<>();

		for (Map.Entry<String, List<AssetRendererFactory<?>>> entry :
				assetRendererFactories.entrySet()) {

			List<AssetRendererFactory<?>> assRendererFactoryList =
				_filterAssetRendererFactories(
					companyId, entry.getValue(), filterSelectable);

			if (!assRendererFactoryList.isEmpty()) {
				filteredAssetRendererFactories.put(
					entry.getKey(), assRendererFactoryList);
			}
		}

		return filteredAssetRendererFactories;
	}

	private List<AssetRendererFactory<?>> _getAssetRendererFactories() {
		return _createListFromMapValues(_assetRenderFactoriesMapByClassName);
	}

	private List<AssetRendererFactory<?>> _getAssetRendererFactories(
		long companyId) {

		return _createListFromMapValues(
			_filterAssetRendererFactories(
				companyId, _assetRenderFactoriesMapByClassName, false));
	}

	private <T> AssetRendererFactory<T> _getAssetRendererFactoryByClass(
		Class<T> clazz) {

		return (AssetRendererFactory<T>)_assetRenderFactoriesMapByClassName.get(
			clazz.getName());
	}

	private AssetRendererFactory<?> _getAssetRendererFactoryByClassName(
		String className) {

		List<AssetRendererFactory<?>> assetRendererFactories =
			_assetRenderFactoriesMapByClassName.get(className);

		if (!ListUtil.isEmpty(assetRendererFactories)) {
			return assetRendererFactories.get(0);
		}

		return null;
	}

	private AssetRendererFactory<?> _getAssetRendererFactoryByClassNameId(
		long classNameId) {

		return _getAssetRendererFactoryByClassName(
			PortalUtil.getClassName(classNameId));
	}

	private AssetRendererFactory<?> _getAssetRendererFactoryByType(
		String type) {

		return _assetRenderFactoriesMapByClassType.get(type);
	}

	private long[] _getClassNameIds(long companyId, boolean filterSelectable) {
		Map<String, List<AssetRendererFactory<?>>> assetRenderFactories =
			_assetRenderFactoriesMapByClassName;

		if (companyId > 0) {
			assetRenderFactories = _filterAssetRendererFactories(
				companyId, _assetRenderFactoriesMapByClassName,
				filterSelectable);
		}

		long[] classNameIds = new long[assetRenderFactories.size()];

		int i = 0;

		for (Entry<String, List<AssetRendererFactory<?>>> entry :
				assetRenderFactories.entrySet()) {

			AssetRendererFactory<?> assetRendererFactory =
				entry.getValue().get(0);

			classNameIds[i] = assetRendererFactory.getClassNameId();

			i++;
		}

		return classNameIds;
	}

	private void _register(AssetRendererFactory<?> assetRendererFactory) {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<AssetRendererFactory<?>> serviceRegistration =
			registry.registerService(
				(Class<AssetRendererFactory<?>>)(Class<?>)
					AssetRendererFactory.class, assetRendererFactory);

		_serviceRegistrations.put(assetRendererFactory, serviceRegistration);
	}

	private void _unregister(AssetRendererFactory<?> assetRendererFactory) {
		ServiceRegistration<AssetRendererFactory<?>> serviceRegistration =
			_serviceRegistrations.remove(assetRendererFactory);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetRendererFactoryRegistryUtil.class);

	private static final AssetRendererFactoryRegistryUtil _instance =
		new AssetRendererFactoryRegistryUtil();

	private final Map<String, List<AssetRendererFactory<?>>>
		_assetRenderFactoriesMapByClassName = new ConcurrentHashMap<>();
	private final Map<String, AssetRendererFactory<?>>
		_assetRenderFactoriesMapByClassType = new ConcurrentHashMap<>();
	private final ServiceRegistrationMap<AssetRendererFactory<?>>
		_serviceRegistrations = new ServiceRegistrationMapImpl<>();
	private final
		ServiceTracker<AssetRendererFactory<?>, AssetRendererFactory<?>>
			_serviceTracker;

	private class AssetRendererFactoryServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<AssetRendererFactory<?>, AssetRendererFactory<?>> {

		@Override
		public AssetRendererFactory<?> addingService(
			ServiceReference<AssetRendererFactory<?>> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			AssetRendererFactory<?> assetRendererFactory = registry.getService(
				serviceReference);

			String className = assetRendererFactory.getClassName();

			final String type = assetRendererFactory.getType();

			List<AssetRendererFactory<?>> assetRendererFactories = null;

			if (_assetRenderFactoriesMapByClassName.containsKey(className)) {
				assetRendererFactories =
					_assetRenderFactoriesMapByClassName.get(className);
			}
			else {
				assetRendererFactories = new ArrayList<>();
				_assetRenderFactoriesMapByClassName.put(
					className, assetRendererFactories);
			}

			if (_assetRenderFactoriesMapByClassType.containsKey(type)) {
				PredicateFilter<AssetRendererFactory<?>> predicateFilter =
					new PredicateFilter<AssetRendererFactory<?>>() {

					@Override
					public boolean filter(AssetRendererFactory<?> arf) {
						return arf.getType().equals(type);
					}
				};

				List<AssetRendererFactory<?>> found = ListUtil.filter(
					assetRendererFactories, predicateFilter);

				if (_log.isWarnEnabled() && !found.isEmpty()) {
					_log.warn(
						"Replacing " + found.get(0) +
							" for class name " + className + " with " +
								assetRendererFactory);
				}

				assetRendererFactories.removeAll(found);
			}

			assetRendererFactories.add(assetRendererFactory);

			AssetRendererFactory<?> typeAssetRendererFactory =
				_assetRenderFactoriesMapByClassType.put(
					type, assetRendererFactory);

			if (_log.isWarnEnabled() && (typeAssetRendererFactory != null)) {
				_log.warn(
					"Replacing " + typeAssetRendererFactory + " for type " +
						type + " with " + assetRendererFactory);
			}

			return assetRendererFactory;
		}

		@Override
		public void modifiedService(
			ServiceReference<AssetRendererFactory<?>> serviceReference,
			AssetRendererFactory<?> assetRendererFactory) {
		}

		@Override
		public void removedService(
			ServiceReference<AssetRendererFactory<?>> serviceReference,
			AssetRendererFactory<?> assetRendererFactory) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_assetRenderFactoriesMapByClassName.remove(
				assetRendererFactory.getClassName());
			_assetRenderFactoriesMapByClassType.remove(
				assetRendererFactory.getType());
		}

	}

}
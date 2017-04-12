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

package com.liferay.portal.template.soy.internal;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.template.soy.utils.SoyTemplateResourcesCollector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Bruno Basto
 */
public class SoyCapabilityBundleTrackerCustomizer
	implements BundleTrackerCustomizer<List<BundleCapability>> {

	public SoyCapabilityBundleTrackerCustomizer(
		String namespace, Map<Long, Bundle> bundleMap,
		List<TemplateResource> templateResources) {

		_bundleMap = bundleMap;
		_soyTofuCacheHandler = new SoyTofuCacheHandler(_portalCache);
		_templateResources = templateResources;
	}

	@Override
	public List<BundleCapability> addingBundle(
		Bundle bundle, BundleEvent bundleEvent) {

		_addTemplateResourcesToList(bundle);

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		List<BundleCapability> bundleCapabilities =
			bundleWiring.getCapabilities("soy");

		return bundleCapabilities;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		List<BundleCapability> bundleCapabilities) {

		removedBundle(bundle, bundleEvent, bundleCapabilities);

		List<BundleCapability> newBundleCapabilities = addingBundle(
			bundle, bundleEvent);

		bundleCapabilities.clear();

		bundleCapabilities.addAll(newBundleCapabilities);
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		List<BundleCapability> bundleCapabilities) {

		List<TemplateResource> removedTemplateResources =
			_removeBundleTemplateResourcesFromList(bundle);

		_soyTofuCacheHandler.removeIfAny(removedTemplateResources);

		_bundleMap.remove(bundle.getBundleId());
	}

	private void _addTemplateResourcesToList(Bundle bundle) {
		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		for (BundleWire bundleWire : bundleWiring.getRequiredWires("soy")) {
			BundleRevision bundleRevision = bundleWire.getProvider();

			Bundle providerBundle = bundleRevision.getBundle();

			_bundleMap.put(providerBundle.getBundleId(), providerBundle);
		}

		_bundleMap.put(bundle.getBundleId(), bundle);

		SoyTemplateResourcesCollector soyTemplateResourceCollector =
			new SoyTemplateResourcesCollector(bundle, StringPool.SLASH);

		try {
			List<TemplateResource> templateResources =
				soyTemplateResourceCollector.getTemplateResources();

			templateResources.stream().forEach(
				templateResource -> {
					if (!_templateResources.contains(templateResource)) {
						_templateResources.add(templateResource);
					}
				});
		}
		catch (TemplateException te) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to add template resources for bundle " +
						bundle.getBundleId(),
					te);
			}
		}
	}

	private long _getBundleId(String templateId) {
		int pos = templateId.indexOf(TemplateConstants.BUNDLE_SEPARATOR);

		if (pos == -1) {
			throw new IllegalArgumentException(
				String.format(
					"The templateId \"%s\" does not map to a Soy template",
					templateId));
		}

		return Long.valueOf(templateId.substring(0, pos));
	}

	private List<TemplateResource> _removeBundleTemplateResourcesFromList(
		Bundle bundle) {

		List<TemplateResource> removedTemplateResources = new ArrayList<>();

		Iterator<TemplateResource> iterator = _templateResources.iterator();

		while (iterator.hasNext()) {
			TemplateResource templateResource = iterator.next();

			long bundleId = _getBundleId(templateResource.getTemplateId());

			if (bundle.getBundleId() == bundleId) {
				removedTemplateResources.add(templateResource);
			}
		}

		_templateResources.removeAll(removedTemplateResources);

		return removedTemplateResources;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SoyCapabilityBundleTrackerCustomizer.class);

	private final Map<Long, Bundle> _bundleMap;
	private final PortalCache<HashSet<TemplateResource>, SoyTofuCacheBag>
		_portalCache = SingleVMPoolUtil.getPortalCache(
			SoyTemplate.class.getName());
	private final SoyTofuCacheHandler _soyTofuCacheHandler;
	private final List<TemplateResource> _templateResources;

}
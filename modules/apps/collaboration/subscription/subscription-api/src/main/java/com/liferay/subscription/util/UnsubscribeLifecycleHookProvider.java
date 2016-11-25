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

package com.liferay.subscription.util;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = UnsubscribeLifecycleHookProvider.class)
public class UnsubscribeLifecycleHookProvider {

	public Optional<UnsubscribeLifecycleHook> get() {
		return Optional.ofNullable(_unsubscribeLifecycleHook);
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	public void setUnsubscribeLifecycleHook(
		UnsubscribeLifecycleHook unsubscribeLifecycleHook) {

		_unsubscribeLifecycleHook = unsubscribeLifecycleHook;
	}

	public void unsetUnsubscribeLifecycleHook(
		UnsubscribeLifecycleHook unsubscribeLifecycleHook) {

		_unsubscribeLifecycleHook = null;
	}

	private UnsubscribeLifecycleHook _unsubscribeLifecycleHook;

}
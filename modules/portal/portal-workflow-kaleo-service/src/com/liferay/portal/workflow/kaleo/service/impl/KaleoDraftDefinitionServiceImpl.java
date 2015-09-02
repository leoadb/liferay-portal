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

package com.liferay.portal.workflow.kaleo.service.impl;

import java.util.Locale;
import java.util.Map;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.workflow.kaleo.model.KaleoDraftDefinition;
import com.liferay.portal.workflow.kaleo.service.base.KaleoDraftDefinitionServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class KaleoDraftDefinitionServiceImpl
	extends KaleoDraftDefinitionServiceBaseImpl {

/*	public KaleoDraftDefinition addKaleoDraftDefinition(
			long userId, long groupId, String name,
			Map<Locale, String> titleMap, String content, int version,
			int draftVersion, ServiceContext serviceContext)
		throws PortalException {

		KaleoDesignerPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_DRAFT);

		return kaleoDraftDefinitionLocalService.addKaleoDraftDefinition(
			userId, groupId, name, titleMap, content, version, draftVersion,
			serviceContext);
	}

	public void deleteKaleoDraftDefinitions(
			String name, int version, ServiceContext serviceContext)
		throws PortalException {

		KaleoDraftDefinition kaleoDraftDefinition =
			kaleoDraftDefinitionLocalService.getLatestKaleoDraftDefinition(
				name, version, serviceContext);

		KaleoDraftDefinitionPermission.check(
			getPermissionChecker(), kaleoDraftDefinition, ActionKeys.DELETE);

		kaleoDraftDefinitionLocalService.deleteKaleoDraftDefinitions(
			name, version, serviceContext);
	}

	public KaleoDraftDefinition getKaleoDraftDefinition(
			String name, int version, int draftVersion,
			ServiceContext serviceContext)
		throws PortalException {

		KaleoDraftDefinition kaleoDraftDefinition =
			kaleoDraftDefinitionLocalService.getKaleoDraftDefinition(
				name, version, draftVersion, serviceContext);

		KaleoDraftDefinitionPermission.check(
			getPermissionChecker(), kaleoDraftDefinition, ActionKeys.VIEW);

		return kaleoDraftDefinition;
	}

	public List<KaleoDraftDefinition> getKaleoDraftDefinitions()
		throws PortalException {

		List<KaleoDraftDefinition> kaleoDraftDefinitions =
			kaleoDraftDefinitionLocalService.getKaleoDraftDefinitions(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		return filterKaleoDraftDefinitions(
			kaleoDraftDefinitions, ActionKeys.VIEW);
	}

	public KaleoDraftDefinition getLatestKaleoDraftDefinition(
			String name, int version, ServiceContext serviceContext)
		throws PortalException {

		KaleoDraftDefinition latestKaleoDraftDefinition =
			kaleoDraftDefinitionLocalService.getLatestKaleoDraftDefinition(
				name, version, serviceContext);

		KaleoDraftDefinitionPermission.check(
			getPermissionChecker(), latestKaleoDraftDefinition,
			ActionKeys.VIEW);

		return latestKaleoDraftDefinition;
	}

	public List<KaleoDraftDefinition> getLatestKaleoDraftDefinitions(
			long companyId, int version, int start, int end,
			OrderByComparator orderByComparator)
		throws PortalException {

		List<KaleoDraftDefinition> latestKaleoDraftDefinitions =
			kaleoDraftDefinitionLocalService.getLatestKaleoDraftDefinitions(
				companyId, version, start, end, orderByComparator);

		return filterKaleoDraftDefinitions(
			latestKaleoDraftDefinitions, ActionKeys.VIEW);
	}

	public KaleoDraftDefinition publishKaleoDraftDefinition(
			long userId, long groupId, String name,
			Map<Locale, String> titleMap, String content,
			ServiceContext serviceContext)
		throws PortalException {

		KaleoDesignerPermission.check(
			getPermissionChecker(), groupId, ActionKeys.PUBLISH);

		return kaleoDraftDefinitionLocalService.publishKaleoDraftDefinition(
			userId, groupId, name, titleMap, content, serviceContext);
	}

	public KaleoDraftDefinition updateKaleoDraftDefinition(
			long userId, String name, Map<Locale, String> titleMap,
			String content, int version, ServiceContext serviceContext)
		throws PortalException {

		KaleoDraftDefinition latestKaleoDraftDefinition =
			getLatestKaleoDraftDefinition(name, version, serviceContext);

		KaleoDraftDefinitionPermission.check(
			getPermissionChecker(), latestKaleoDraftDefinition,
			ActionKeys.UPDATE);

		return kaleoDraftDefinitionLocalService.updateKaleoDraftDefinition(
			userId, name, titleMap, content, version, serviceContext);
	}

	protected List<KaleoDraftDefinition> filterKaleoDraftDefinitions(
			List<KaleoDraftDefinition> kaleoDraftDefinitions, String actionId)
		throws PrincipalException {

		List<KaleoDraftDefinition> filteredKaleoDraftDefinitions =
			new ArrayList<>();

		for (KaleoDraftDefinition kaleoDraftDefinition :
				kaleoDraftDefinitions) {

			if (KaleoDraftDefinitionPermission.contains(
					getPermissionChecker(), kaleoDraftDefinition, actionId)) {

				filteredKaleoDraftDefinitions.add(kaleoDraftDefinition);
			}
		}

		return Collections.unmodifiableList(filteredKaleoDraftDefinitions);
	}*/
}
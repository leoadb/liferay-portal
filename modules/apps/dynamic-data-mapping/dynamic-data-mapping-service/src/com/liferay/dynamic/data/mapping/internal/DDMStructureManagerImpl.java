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

package com.liferay.dynamic.data.mapping.internal;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.DDMForm;
import com.liferay.portlet.dynamicdatamapping.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.DDMStructureManager;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDDeserializerUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalService;
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;
import com.liferay.portlet.dynamicdatamapping.util.comparator.StructureStructureKeyComparator;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true)
public class DDMStructureManagerImpl implements DDMStructureManager {

	@Override
	public DDMStructure addStructure(
			long userId, long groupId, String parentStructureKey,
			long classNameId, String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMForm ddmForm,
			DDMFormLayout ddmFormLayout, String storageType, int type,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMStructure
				structure = _ddmStructureLocalService.addStructure(
					userId, groupId, parentStructureKey, classNameId,
					structureKey, nameMap, descriptionMap, translate(ddmForm),
					translate(ddmFormLayout), storageType, type,
					serviceContext);

			return new DDMStructureImpl(structure);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public DDMStructure addStructure(
			long userId, long groupId, String parentStructureKey,
			long classNameId, String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String definition,
			String storageType, int type, ServiceContext serviceContext)
		throws PortalException {

		try {
			DDMXMLUtil.validateXML(definition);

			com.liferay.portlet.dynamicdatamapping.model.DDMForm form =
				DDMFormXSDDeserializerUtil.deserialize(definition);

			DDMForm ddmForm = translate(form);

			com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout formLayout =
				DDMUtil.getDefaultDDMFormLayout(form);

			DDMFormLayout ddmFormLayout = translate(formLayout);

			return addStructure(
				userId, groupId, parentStructureKey, classNameId, structureKey,
				nameMap, descriptionMap, ddmForm, ddmFormLayout, storageType,
				type, serviceContext);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public void deleteStructure(long structureId) throws PortalException {
		_ddmStructureLocalService.deleteStructure(structureId);
	}

	@Override
	public void deleteStructures(long groupId) throws PortalException {
		_ddmStructureLocalService.deleteStructures(groupId);
	}

	@Override
	public Element exportReferenceStagedModel(
		PortletDataContext portletDataContext, StagedModel referrerStagedModel,
		long structureId,
		String referenceType) throws Exception {

		com.liferay.portlet.dynamicdatamapping.model.DDMStructure structure =
			_ddmStructureLocalService.getDDMStructure(structureId);

		return StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, referrerStagedModel, structure, referenceType);
	}

	@Override
	public DDMStructure fetchStructure(long structureId) {
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure structure =
			_ddmStructureLocalService.fetchDDMStructure(structureId);

		if (structure != null) {
			return new DDMStructureImpl(structure);
		}

		return null;
	}

	@Override
	public DDMStructure fetchStructure(
		long groupId, long classNameId, String structureKey) {

		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMStructure
				structure = _ddmStructureLocalService.fetchStructure(
					groupId, classNameId, structureKey);

			return new DDMStructureImpl(structure);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public DDMStructure fetchStructureByUuidAndGroupId(
		String uuid, long groupId) {

		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMStructure
				structure =
					_ddmStructureLocalService.fetchDDMStructureByUuidAndGroupId(
						uuid, groupId);

			return new DDMStructureImpl(structure);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public List<DDMStructure> getClassStructures(
		long companyId, long classNameId) {

		try {
			List<DDMStructure> ddmStructures = new ArrayList<>();

			List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure>
				structures = _ddmStructureLocalService.getClassStructures(
					companyId, classNameId);

			for (com.liferay.portlet.dynamicdatamapping.model.DDMStructure
					structure : structures) {

				ddmStructures.add(new DDMStructureImpl(structure));
			}

			return ddmStructures;
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public List<DDMStructure> getClassStructures(
		long companyId, long classNameId, int start, int end) {

		try {
			List<DDMStructure> ddmStructures = new ArrayList<>();

			List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure>
				structures = _ddmStructureLocalService.getClassStructures(
					companyId, classNameId, start, end);

			for (com.liferay.portlet.dynamicdatamapping.model.DDMStructure
					structure : structures) {

				ddmStructures.add(new DDMStructureImpl(structure));
			}

			return ddmStructures;
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public List<DDMStructure> getClassStructuresUsingKeyComparator(
		long companyId, long classNameId) {

		try {
			List<DDMStructure> ddmStructures = new ArrayList<>();

			List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure>
				structures = _ddmStructureLocalService.getClassStructures(
					companyId, classNameId,
					new StructureStructureKeyComparator(true));

			for (com.liferay.portlet.dynamicdatamapping.model.DDMStructure
					structure : structures) {

				ddmStructures.add(new DDMStructureImpl(structure));
			}

			return ddmStructures;
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public DDMStructure getStructure(long structureId) throws PortalException {
		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMStructure
				structure = _ddmStructureLocalService.getStructure(structureId);

			return new DDMStructureImpl(structure);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public DDMStructure getStructure(
			long groupId, long classNameId, String structureKey)
		throws PortalException {

		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMStructure
				structure = _ddmStructureLocalService.getStructure(
					groupId, classNameId, structureKey);

			return new DDMStructureImpl(structure);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public DDMStructure getStructureByUuidAndGroupId(String uuid, long groupId)
		throws PortalException {

		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMStructure
				structure =
					_ddmStructureLocalService.getDDMStructureByUuidAndGroupId(
						uuid, groupId);

			return new DDMStructureImpl(structure);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public DDMStructure updateStructure(DDMStructure structure)
		throws PortalException {

		try {
			return new DDMStructureImpl(
				_ddmStructureLocalService.updateDDMStructure(
					translate(structure)));
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public DDMStructure updateStructure(
			long userId, long structureId, long parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMStructure
				structure = _ddmStructureLocalService.updateStructure(
					userId, structureId, parentStructureId, nameMap,
					descriptionMap, translate(ddmForm), translate(ddmFormLayout),
					serviceContext);

			return new DDMStructureImpl(structure);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public DDMStructure updateStructure(
			long userId, long structureId, long parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String definition, ServiceContext serviceContext)
		throws PortalException {

		try {
			DDMXMLUtil.validateXML(definition);

			com.liferay.portlet.dynamicdatamapping.model.DDMForm form =
				DDMFormXSDDeserializerUtil.deserialize(definition);

			DDMForm ddmForm = translate(form);

			com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout formLayout =
				DDMUtil.getDefaultDDMFormLayout(form);

			DDMFormLayout ddmFormLayout = translate(formLayout);

			return updateStructure(
				userId, structureId, parentStructureId, nameMap, descriptionMap,
				ddmForm, ddmFormLayout, serviceContext);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Reference
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	protected DDMForm translate(
			com.liferay.portlet.dynamicdatamapping.model.DDMForm ddmForm)
		throws Exception {

		return BeanPropertiesUtil.deepCopyProperties(ddmForm, DDMForm.class);
	}

	protected DDMFormLayout translate(
			com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout ddmFormLayout)
		throws Exception {

		return BeanPropertiesUtil.deepCopyProperties(
			ddmFormLayout, DDMFormLayout.class);
	}

	protected com.liferay.portlet.dynamicdatamapping.model.DDMForm translate(
			DDMForm ddmForm)
		throws Exception {

		return BeanPropertiesUtil.deepCopyProperties(
			ddmForm,
			com.liferay.portlet.dynamicdatamapping.model.DDMForm.class);
	}

	protected com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout translate(
			DDMFormLayout ddmFormLayout)
		throws Exception {

		return BeanPropertiesUtil.deepCopyProperties(
			ddmFormLayout,
			com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout.class);
	}

	protected com.liferay.portlet.dynamicdatamapping.model.DDMStructure translate(
			DDMStructure ddmStructure)
		throws Exception {

		return BeanPropertiesUtil.deepCopyProperties(
			ddmStructure,
			com.liferay.portlet.dynamicdatamapping.model.DDMStructure.class);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMStructureManagerImpl.class);

	private DDMStructureLocalService _ddmStructureLocalService;

}
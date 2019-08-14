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

package com.liferay.data.engine.service.impl;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.model.DEDataLayout;
import com.liferay.data.engine.model.DEDataLayoutColumn;
import com.liferay.data.engine.model.DEDataLayoutPage;
import com.liferay.data.engine.model.DEDataLayoutRow;
import com.liferay.data.engine.model.builder.DEModelBuilderFactory;
import com.liferay.data.engine.model.impl.DEDataLayoutColumnImpl;
import com.liferay.data.engine.model.impl.DEDataLayoutImpl;
import com.liferay.data.engine.model.impl.DEDataLayoutPageImpl;
import com.liferay.data.engine.model.impl.DEDataLayoutRowImpl;
import com.liferay.data.engine.service.base.DEDataLayoutLocalServiceBaseImpl;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @see DEDataLayoutLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.liferay.data.engine.model.DEDataLayout",
	service = AopService.class
)
public class DEDataLayoutLocalServiceImpl
	extends DEDataLayoutLocalServiceBaseImpl {

	@Override
	public DEDataLayout addDataLayout(
			DEDataLayout deDataLayout, ServiceContext serviceContext)
		throws PortalException {

		if (MapUtil.isEmpty(deDataLayout.getNameMap())) {
			throw new PortalException("Name is required");
		}

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			deDataLayout.getDeDataDefinitionId());

		deDataLayout = _toDEDataLayout(
			_ddmStructureLayoutLocalService.addStructureLayout(
				deDataLayout.getUserId(), ddmStructure.getGroupId(),
				_portal.getClassNameId(DEDataLayout.class),
				deDataLayout.getDataLayoutKey(),
				_getDDMStructureVersionId(deDataLayout.getDeDataDefinitionId()),
				deDataLayout.getNameMap(), deDataLayout.getDescriptionMap(),
				_toJSON(deDataLayout), serviceContext));

		_resourceLocalService.addModelResources(
			deDataLayout.getCompanyId(), ddmStructure.getGroupId(),
			deDataLayout.getUserId(), DEDataLayout.class.getName(),
			deDataLayout.getDeDataLayoutId(),
			serviceContext.getModelPermissions());

		return deDataLayout;
	}

	@Override
	public DEDataLayout getDataLayout(long deDataLayoutId)
		throws PortalException {

		return _toDEDataLayout(
			_ddmStructureLayoutLocalService.getDDMStructureLayout(
				deDataLayoutId));
	}

	@Override
	public DEDataLayout getDataLayout(long groupId, String deDataLayoutKey)
		throws Exception {

		return _toDEDataLayout(
			_ddmStructureLayoutLocalService.getStructureLayout(
				groupId, _portal.getClassNameId(DEDataLayout.class),
				deDataLayoutKey));
	}

	@Override
	public DEDataLayout updateDataLayout(
			DEDataLayout deDataLayout, ServiceContext serviceContext)
		throws PortalException {

		if (MapUtil.isEmpty(deDataLayout.getNameMap())) {
			throw new PortalException("Name is required");
		}

		return _toDEDataLayout(
			_ddmStructureLayoutLocalService.updateStructureLayout(
				deDataLayout.getDeDataLayoutId(),
				_getDDMStructureVersionId(deDataLayout.getDeDataDefinitionId()),
				deDataLayout.getNameMap(), deDataLayout.getDescriptionMap(),
				_toJSON(deDataLayout), serviceContext));
	}

	private long _getDDMStructureId(DDMStructureLayout ddmStructureLayout)
		throws PortalException {

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getDDMStructureVersion(
				ddmStructureLayout.getStructureVersionId());

		DDMStructure ddmStructure = ddmStructureVersion.getStructure();

		return ddmStructure.getStructureId();
	}

	private long _getDDMStructureVersionId(Long deDataDefinitionId)
		throws PortalException {

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getLatestStructureVersion(
				deDataDefinitionId);

		return ddmStructureVersion.getStructureVersionId();
	}

	private DEDataLayout _toDEDataLayout(DDMStructureLayout ddmStructureLayout)
		throws PortalException {

		DEDataLayout deDataLayout = _toDEDataLayout(
			ddmStructureLayout.getDefinition());

		deDataLayout.setCreateDate(ddmStructureLayout.getCreateDate());
		deDataLayout.setModifiedDate(ddmStructureLayout.getModifiedDate());
		deDataLayout.setDeDataDefinitionId(
			_getDDMStructureId(ddmStructureLayout));
		deDataLayout.setDataLayoutKey(
			ddmStructureLayout.getStructureLayoutKey());
		deDataLayout.setDescriptionMap(ddmStructureLayout.getDescriptionMap());
		deDataLayout.setDeDataLayoutId(
			ddmStructureLayout.getStructureLayoutId());
		deDataLayout.setNameMap(ddmStructureLayout.getNameMap());
		deDataLayout.setGroupId(ddmStructureLayout.getGroupId());
		deDataLayout.setUserId(ddmStructureLayout.getUserId());

		return deDataLayout;
	}

	private DEDataLayout _toDEDataLayout(String json) {
		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

			return new DEDataLayoutImpl() {
				{
					setDEDataLayoutPages(
						JSONUtil.toList(
							jsonObject.getJSONArray("pages"),
							pageJSONObject -> _toDEDataLayoutPage(
								pageJSONObject)));
					setPaginationMode(jsonObject.getString("paginationMode"));
				}
			};
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			return new DEDataLayoutImpl();
		}
	}

	private DEDataLayoutColumn _toDEDataLayoutColumn(JSONObject jsonObject) {
		return new DEDataLayoutColumnImpl() {
			{
				setColumnSize(
					GetterUtil.getInteger(jsonObject.getInt("columnSize"), 12));
				setFieldNames(
					JSONUtil.toStringArray(
						jsonObject.getJSONArray("fieldNames")));
			}
		};
	}

	private DEDataLayoutPage _toDEDataLayoutPage(JSONObject jsonObject)
		throws Exception {

		return new DEDataLayoutPageImpl() {
			{
				setDEDataLayoutRows(
					JSONUtil.toList(
						jsonObject.getJSONArray("rows"),
						rowJSONObject -> _toDEDataLayoutRow(rowJSONObject)));
				setDescription(
					LocalizedValueUtil.toLocalizedValues(
						jsonObject.getJSONObject("description")));
				setTitle(
					LocalizedValueUtil.toLocalizedValues(
						jsonObject.getJSONObject("title")));
			}
		};
	}

	private DEDataLayoutRow _toDEDataLayoutRow(JSONObject jsonObject)
		throws Exception {

		return new DEDataLayoutRowImpl() {
			{
				setDEDataLayoutColumns(
					JSONUtil.toList(
						jsonObject.getJSONArray("columns"),
						columnJSONObject -> _toDEDataLayoutColumn(
							columnJSONObject)));
			}
		};
	}

	private String _toJSON(DEDataLayout deDataLayout) {
		try {
			return JSONUtil.put(
				"pages",
				JSONUtil.toJSONArray(
					deDataLayout.getDEDataLayoutPages(),
					dataLayoutPage -> _toJSONObject(dataLayoutPage))
			).put(
				"paginationMode", deDataLayout.getPaginationMode()
			).toString();
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return StringPool.BLANK;
	}

	private JSONObject _toJSONObject(DEDataLayoutColumn deDataLayoutColumn) {
		return JSONUtil.put(
			"columnSize", deDataLayoutColumn.getColumnSize()
		).put(
			"fieldNames", JSONUtil.putAll(deDataLayoutColumn.getFieldNames())
		);
	}

	private JSONObject _toJSONObject(DEDataLayoutPage deDataLayoutPage)
		throws Exception {

		return JSONUtil.put(
			"description",
			LocalizedValueUtil.toJSONObject(deDataLayoutPage.getDescription())
		).put(
			"rows",
			JSONUtil.toJSONArray(
				deDataLayoutPage.getDEDataLayoutRows(),
				deDataLayoutRow -> _toJSONObject(deDataLayoutRow))
		).put(
			"title",
			LocalizedValueUtil.toJSONObject(deDataLayoutPage.getTitle())
		);
	}

	private JSONObject _toJSONObject(DEDataLayoutRow deDataLayoutRow)
		throws Exception {

		return JSONUtil.put(
			"columns",
			JSONUtil.toJSONArray(
				deDataLayoutRow.getDEDataLayoutColumns(),
				deDataLayoutColumn -> _toJSONObject(deDataLayoutColumn)));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DEDataLayoutLocalServiceImpl.class);

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	@Reference
	private DEModelBuilderFactory _deModelBuilderFactory;

	@Reference
	private Portal _portal;

	@Reference
	private ResourceLocalService _resourceLocalService;

}
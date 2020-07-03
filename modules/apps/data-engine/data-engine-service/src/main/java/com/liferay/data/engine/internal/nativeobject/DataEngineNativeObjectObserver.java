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

package com.liferay.data.engine.internal.nativeobject;

import com.liferay.data.engine.nativeobject.DataEngineNativeObject;
import com.liferay.data.engine.nativeobject.DataEngineNativeObjectField;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.security.auth.GuestOrUserUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.sql.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(immediate = true, service = DataEngineNativeObjectObserver.class)
public class DataEngineNativeObjectObserver {

	public void createDataEngineNativeObject(
			Long companyId, DataEngineNativeObject dataEngineNativeObject)
		throws Exception {

		DataDefinitionResource dataDefinitionResource =
			DataDefinitionResource.builder(
			).checkPermissions(
				false
			).user(
				GuestOrUserUtil.getGuestOrUser(companyId)
			).build();

		Company company = _companyLocalService.getCompany(companyId);

		DataDefinition dataDefinition = null;

		try {
			dataDefinition =
				dataDefinitionResource.
					getSiteDataDefinitionByContentTypeByDataDefinitionKey(
						_portal.getSiteGroupId(company.getGroupId()),
						"native-object", dataEngineNativeObject.getClassName());
		}
		catch (Exception exception) {
			if (!(exception instanceof NoSuchStructureException) &&
				!(exception.getCause() instanceof NoSuchStructureException)) {

				throw exception;
			}

			dataDefinition = new DataDefinition() {
				{
					availableLanguageIds = new String[] {defaultLanguageId};
					dataDefinitionKey = dataEngineNativeObject.getClassName();
					defaultDataLayout = new DataLayout();
					storageType = "json";
				}
			};
		}

		dataDefinition.setDataDefinitionFields(
			_toDataDefinitionFields(
				Optional.ofNullable(
					dataDefinition.getDataDefinitionFields()
				).orElse(
					new DataDefinitionField[0]
				),
				dataEngineNativeObject.getDataEngineNativeObjectFields()));

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		dataDefinition.setName(
			HashMapBuilder.<String, Object>putAll(
				Optional.ofNullable(
					dataDefinition.getName()
				).orElse(
					new HashMap<>()
				)
			).put(
				defaultLanguageId, dataEngineNativeObject.getName()
			).build());

		if (Validator.isNull(dataDefinition.getId())) {
			dataDefinitionResource.postDataDefinitionByContentType(
				"native-object", dataDefinition);
		}
		else {
			dataDefinitionResource.putDataDefinition(
				dataDefinition.getId(), dataDefinition);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, DataEngineNativeObject.class, null,
			(serviceReference, emitter) -> {
				DataEngineNativeObject dataEngineNativeObject =
					bundleContext.getService(serviceReference);

				try {
					_dataEngineNativeObjectPortalExecutor.execute(
						() -> _createDataEngineNativeObjects(
							dataEngineNativeObject));

					emitter.emit(dataEngineNativeObject.getClassName());
				}
				finally {
					bundleContext.ungetService(serviceReference);
				}
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	@Reference(unbind = "-")
	protected void setDataDefinitionResourceFactory(
		DataDefinitionResource.Factory dataDefinitionResourceFactory) {
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private void _createDataEngineNativeObjects(
			DataEngineNativeObject dataEngineNativeObject)
		throws Exception {

		for (Long companyId : _portal.getCompanyIds()) {
			createDataEngineNativeObject(companyId, dataEngineNativeObject);
		}
	}

	private String _getFieldType(String customType, int sqlType) {
		if (ArrayUtil.contains(_BASIC_FIELD_TYPES, customType)) {
			return customType;
		}

		String type = "text";

		if (sqlType == Types.ARRAY) {
			type = "select";
		}
		else if (sqlType == Types.BOOLEAN) {
			type = "radio";
		}
		else if ((sqlType == Types.BIGINT) || (sqlType == Types.DECIMAL) ||
				 (sqlType == Types.DOUBLE) || (sqlType == Types.FLOAT) ||
				 (sqlType == Types.INTEGER) || (sqlType == Types.NUMERIC) ||
				 (sqlType == Types.TINYINT)) {

			type = "numeric";
		}
		else if ((sqlType == Types.DATE) || (sqlType == Types.TIME) ||
				 (sqlType == Types.TIMESTAMP)) {

			type = "date";
		}

		return type;
	}

	private DataDefinitionField[] _toDataDefinitionFields(
		DataDefinitionField[] dataDefinitionFields,
		List<DataEngineNativeObjectField> dataEngineNativeObjectFields) {

		if (ListUtil.isEmpty(dataEngineNativeObjectFields)) {
			return new DataDefinitionField[0];
		}

		List<DataDefinitionField> list = new ArrayList<>();

		for (DataEngineNativeObjectField dataEngineNativeObjectField :
				dataEngineNativeObjectFields) {

			Column<?, ?> column = dataEngineNativeObjectField.getColumn();

			String defaultLanguageId = LocaleUtil.toLanguageId(
				LocaleUtil.getDefault());

			DataDefinitionField dataDefinitionField = Stream.of(
				dataDefinitionFields
			).filter(
				field -> Objects.equals(column.getName(), field.getName())
			).findFirst(
			).orElse(
				new DataDefinitionField() {
					{
						customProperties = HashMapBuilder.<String, Object>put(
							"fieldNamespace", StringPool.BLANK
						).put(
							"nativeField", true
						).build();
						defaultValue = HashMapBuilder.<String, Object>put(
							defaultLanguageId, StringPool.BLANK
						).build();
						label = HashMapBuilder.<String, Object>put(
							defaultLanguageId,
							GetterUtil.getString(
								dataEngineNativeObjectField.getCustomName(),
								column.getName())
						).build();
						localizable = true;
						name = column.getName();
						tip = HashMapBuilder.<String, Object>put(
							defaultLanguageId, StringPool.BLANK
						).build();
					}
				}
			);

			dataDefinitionField.setFieldType(
				_getFieldType(
					dataEngineNativeObjectField.getCustomType(),
					column.getSQLType()));
			dataDefinitionField.setRequired(!column.isNullAllowed());

			if (Objects.equals(
					dataDefinitionField.getFieldType(), "checkbox_multiple") ||
				Objects.equals(dataDefinitionField.getFieldType(), "radio") ||
				Objects.equals(dataDefinitionField.getFieldType(), "select")) {

				Map<String, Object> customProperties =
					dataDefinitionField.getCustomProperties();

				if (MapUtil.isEmpty((Map)customProperties.get("options"))) {
					customProperties.put(
						"options",
						HashMapBuilder.<String, Object>put(
							defaultLanguageId,
							new String[] {
								JSONUtil.put(
									"label", "Option"
								).put(
									"value", "option"
								).toJSONString()
							}
						).build());
				}
			}

			list.add(dataDefinitionField);
		}

		return list.toArray(new DataDefinitionField[0]);
	}

	private static final String[] _BASIC_FIELD_TYPES = {
		"checkbox_multiple", "date", "numeric", "radio", "select", "text"
	};

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DataEngineNativeObjectPortalExecutor
		_dataEngineNativeObjectPortalExecutor;

	@Reference
	private Portal _portal;

	private ServiceTrackerMap<String, DataEngineNativeObject>
		_serviceTrackerMap;

}
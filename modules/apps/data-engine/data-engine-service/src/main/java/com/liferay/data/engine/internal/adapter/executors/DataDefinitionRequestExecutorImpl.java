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

package com.liferay.data.engine.internal.adapter.executors;

import com.liferay.data.engine.adapter.DataEngineError;
import com.liferay.data.engine.adapter.DataEngineErrorCode;
import com.liferay.data.engine.adapter.data.definition.DataDefinitionRequestExecutor;
import com.liferay.data.engine.adapter.data.definition.DeleteDataDefinitionRequest;
import com.liferay.data.engine.adapter.data.definition.DeleteDataDefinitionResponse;
import com.liferay.data.engine.adapter.data.definition.SaveDataDefinitionRequest;
import com.liferay.data.engine.adapter.data.definition.SaveDataDefinitionResponse;
import com.liferay.data.engine.adapter.data.record.collection.DataRecordCollectionExecutor;
import com.liferay.data.engine.adapter.data.record.collection.SaveDataRecordCollectionRequest;
import com.liferay.data.engine.exception.DataEngineException;
import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.internal.security.permission.DataEnginePermissionUtil;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.data.engine.model.DEDataRecordCollection;
import com.liferay.data.engine.service.DEDataDefinitionFieldLinkLocalService;
import com.liferay.data.engine.service.DEDataListViewLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidationExpression;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DataDefinitionRequestExecutor.class)
public class DataDefinitionRequestExecutorImpl
	implements DataDefinitionRequestExecutor {

	@Override
	public DeleteDataDefinitionResponse execute(
		DeleteDataDefinitionRequest deleteDataDefinitionRequest) {

		DDMStructure ddmStructure = null;

		try {
			ddmStructure = _ddmStructureLocalService.getDDMStructure(
				deleteDataDefinitionRequest.getDEDataDefinitionId());

			if (deleteDataDefinitionRequest.isPermissionAware()) {
				DataEnginePermissionUtil.checkPermission(
					ActionKeys.ACCESS, _groupLocalService,
					ddmStructure.getGroupId());
			}

			_ddlRecordSetLocalService.deleteDDMStructureRecordSets(
				deleteDataDefinitionRequest.getDEDataDefinitionId());

			_ddmStructureLocalService.deleteDDMStructure(
				deleteDataDefinitionRequest.getDEDataDefinitionId());

			List<DDMStructureVersion> ddmStructureVersions =
				_ddmStructureVersionLocalService.getStructureVersions(
					deleteDataDefinitionRequest.getDEDataDefinitionId());

			for (DDMStructureVersion ddmStructureVersion :
					ddmStructureVersions) {

				_ddmStructureLayoutLocalService.deleteDDMStructureLayouts(
					ddmStructure.getClassNameId(), ddmStructureVersion);

				_ddmStructureVersionLocalService.deleteDDMStructureVersion(
					ddmStructureVersion);
			}

			_deDataDefinitionFieldLinkLocalService.
				deleteDEDataDefinitionFieldLinks(
					deleteDataDefinitionRequest.getDEDataDefinitionId());

			_deDataListViewLocalService.deleteDEDataListViews(
				deleteDataDefinitionRequest.getDEDataDefinitionId());

			DeleteDataDefinitionResponse deleteDataDefinitionResponse =
				new DeleteDataDefinitionResponse();

			deleteDataDefinitionResponse.setDEDataDefinitionId(
				deleteDataDefinitionRequest.getDEDataDefinitionId());

			return deleteDataDefinitionResponse;
		}
		catch (NoSuchStructureException nsse) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsse, nsse);
			}

			String errorMessage =
				"No such data definition: " +
					deleteDataDefinitionRequest.getDEDataDefinitionId();

			throw new DataEngineException(
				Arrays.asList(
					new DataEngineError(
						DataEngineErrorCode.NO_SUCH_DATA_DEFINITION,
						errorMessage)),
				nsse);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			String errorMessage =
				"Unable to delete data definition: " +
					deleteDataDefinitionRequest.getDEDataDefinitionId();

			throw new DataEngineException(
				Arrays.asList(
					new DataEngineError(
						DataEngineErrorCode.UNABLE_TO_DELETE_DATA_DEFINITION,
						errorMessage)),
				e);
		}
	}

	@Override
	public SaveDataDefinitionResponse execute(
		SaveDataDefinitionRequest saveDataDefinitionRequest) {

		DEDataDefinition deDataDefinition =
			saveDataDefinitionRequest.getDEDataDefinition();

		if (deDataDefinition.getDEDataDefinitionId() == 0) {
			return insert(saveDataDefinitionRequest);
		}

		return update(saveDataDefinitionRequest);
	}

	protected DDMFormFieldOptions getDDMFormFieldOptions(
		Map<String, List<Map<String, String>>> options) {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		if (MapUtil.isEmpty(options)) {
			return ddmFormFieldOptions;
		}

		for (Map.Entry<String, List<Map<String, String>>> entry :
				options.entrySet()) {

			for (Map<String, String> option : entry.getValue()) {
				ddmFormFieldOptions.addOptionLabel(
					MapUtil.getString(option, "value"),
					LocaleUtil.fromLanguageId(entry.getKey()),
					MapUtil.getString(option, "label"));
			}
		}

		return ddmFormFieldOptions;
	}

	protected DDMFormFieldValidation getDDMFormFieldValidation(
		Map<String, Object> value) {

		if (MapUtil.isEmpty(value)) {
			return null;
		}

		Map<String, String> expression = (Map<String, String>)value.get(
			"expression");

		if (Validator.isNull(MapUtil.getString(expression, "value"))) {
			return null;
		}

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		ddmFormFieldValidation.setDDMFormFieldValidationExpression(
			new DDMFormFieldValidationExpression() {
				{
					setName(MapUtil.getString(expression, "name"));
					setValue(MapUtil.getString(expression, "value"));
				}
			});
		ddmFormFieldValidation.setErrorMessageLocalizedValue(
			LocalizedValueUtil.toLocalizedValue(
				(Map<String, Object>)value.get("errorMessage")));
		ddmFormFieldValidation.setParameterLocalizedValue(
			LocalizedValueUtil.toLocalizedValue(
				(Map<String, Object>)value.get("parameter")));

		return ddmFormFieldValidation;
	}

	protected Map<String, DDMFormField> getSettingsDDMFormFields(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		String type) {

		DDMFormFieldType ddmFormFieldType =
			ddmFormFieldTypeServicesTracker.getDDMFormFieldType(type);

		Class<? extends DDMFormFieldTypeSettings> ddmFormFieldTypeSettings =
			DefaultDDMFormFieldTypeSettings.class;

		if (ddmFormFieldType != null) {
			ddmFormFieldTypeSettings =
				ddmFormFieldType.getDDMFormFieldTypeSettings();
		}

		DDMForm settingsDDMForm = DDMFormFactory.create(
			ddmFormFieldTypeSettings);

		return settingsDDMForm.getDDMFormFieldsMap(true);
	}

	protected SaveDataDefinitionResponse insert(
		SaveDataDefinitionRequest saveDataDefinitionRequest) {

		try {
			DEDataDefinition deDataDefinition =
				saveDataDefinitionRequest.getDEDataDefinition();

			long groupId = deDataDefinition.getGroupId();

			if (groupId == 0) {
				Company company = _companyLocalService.getCompanyById(
					deDataDefinition.getCompanyId());

				groupId = company.getGroupId();
			}

			if (saveDataDefinitionRequest.isPermissionAware()) {
				DataEnginePermissionUtil.checkPermission(
					ActionKeys.ACCESS, _groupLocalService, groupId);
			}

			ServiceContext serviceContext = new ServiceContext();

			DDMFormSerializerSerializeRequest.Builder builder =
				DDMFormSerializerSerializeRequest.Builder.newBuilder(
					toDDMForm(
						_ddmFormFieldTypeServicesTracker, deDataDefinition));

			DDMFormSerializerSerializeResponse
				ddmFormSerializerSerializeResponse =
					_ddmFormSerializer.serialize(builder.build());

			deDataDefinition = toDEDataDefinition(
				_ddmFormFieldTypeServicesTracker,
				_ddmStructureLocalService.addStructure(
					PrincipalThreadLocal.getUserId(), groupId,
					DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
					deDataDefinition.getClassNameId(),
					deDataDefinition.getDEDataDefinitionKey(),
					deDataDefinition.getName(),
					deDataDefinition.getDescription(),
					ddmFormSerializerSerializeResponse.getContent(),
					deDataDefinition.getStorageType(), serviceContext));

			SaveDataRecordCollectionRequest saveDataRecordCollectionRequest =
				new SaveDataRecordCollectionRequest();

			saveDataRecordCollectionRequest.setDEDataRecordCollection(
				DEDataRecordCollection.newBuilder(
					deDataDefinition.getDEDataDefinitionId()
				).deDataRecordCollectionKey(
					deDataDefinition.getDEDataDefinitionKey()
				).description(
					deDataDefinition.getDescription()
				).name(
					deDataDefinition.getName()
				).build());
			saveDataRecordCollectionRequest.setPermissionAware(false);

			_dataRecordCollectionExecutor.execute(
				saveDataRecordCollectionRequest);

			SaveDataDefinitionResponse saveDataDefinitionResponse =
				new SaveDataDefinitionResponse();

			saveDataDefinitionResponse.setDEDataDefinition(deDataDefinition);

			return saveDataDefinitionResponse;
		}
		catch (DataEngineException dee) {
			throw dee;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			throw new DataEngineException(
				Arrays.asList(
					new DataEngineError(
						DataEngineErrorCode.UNABLE_TO_DELETE_DATA_DEFINITION,
						"Unable to insert data definition")),
				e);
		}
	}

	protected DEDataDefinitionField toDataDefinitionField(
		DDMFormField ddmFormField,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		return DEDataDefinitionField.newBuilder(
			ddmFormField.getType(), ddmFormField.getName()
		).indexable(
			Validator.isNotNull(ddmFormField.getIndexType())
		).indexType(
			DEDataDefinitionField.IndexType.create(ddmFormField.getIndexType())
		).label(
			LocalizedValueUtil.toLocalizedValuesMap(ddmFormField.getLabel())
		).localizable(
			ddmFormField.isLocalizable()
		).nestedDEDataDefinitionFields(
			toDataDefinitionFields(
				ddmFormField.getNestedDDMFormFields(),
				ddmFormFieldTypeServicesTracker)
		).predefinedValue(
			LocalizedValueUtil.toLocalizedValuesMap(
				ddmFormField.getPredefinedValue())
		).readOnly(
			ddmFormField.isReadOnly()
		).repeatable(
			ddmFormField.isRepeatable()
		).required(
			ddmFormField.isRequired()
		).showLabel(
			ddmFormField.isShowLabel()
		).tip(
			LocalizedValueUtil.toLocalizedValuesMap(ddmFormField.getTip())
		).build();
	}

	protected List<DEDataDefinitionField> toDataDefinitionFields(
		List<DDMFormField> ddmFormFields,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		if (ListUtil.isEmpty(ddmFormFields)) {
			return Collections.emptyList();
		}

		Stream<DDMFormField> stream = ddmFormFields.stream();

		return stream.map(
			ddmFormField -> toDataDefinitionField(
				ddmFormField, ddmFormFieldTypeServicesTracker)
		).collect(
			Collectors.toList()
		);
	}

	protected DDMForm toDDMForm(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		DEDataDefinition deDataDefinition) {

		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(deDataDefinition.getAvailableLocales());
		ddmForm.setDDMFormFields(
			toDDMFormFields(
				ddmFormFieldTypeServicesTracker,
				deDataDefinition.getDEDataDefinitionFields()));
		ddmForm.setDefaultLocale(deDataDefinition.getDefaultLocale());

		return ddmForm;
	}

	protected DDMFormField toDDMFormField(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		DEDataDefinitionField deDataDefinitionField) {

		DDMFormField ddmFormField = new DDMFormField();

		ddmFormField.setIndexType(deDataDefinitionField.getIndexTypeAsString());
		ddmFormField.setLabel(
			LocalizedValueUtil.toLocalizedValue(
				deDataDefinitionField.getLabel()));
		ddmFormField.setLocalizable(deDataDefinitionField.isLocalizable());
		ddmFormField.setName(deDataDefinitionField.getName());
		ddmFormField.setNestedDDMFormFields(
			toDDMFormFields(
				ddmFormFieldTypeServicesTracker,
				deDataDefinitionField.getNestedDEDataDefinitionFields()));
		ddmFormField.setPredefinedValue(
			LocalizedValueUtil.toLocalizedValue(
				deDataDefinitionField.getPredefinedValue()));
		ddmFormField.setReadOnly(deDataDefinitionField.isReadOnly());
		ddmFormField.setRepeatable(deDataDefinitionField.isRepeatable());
		ddmFormField.setRequired(deDataDefinitionField.isRequired());
		ddmFormField.setShowLabel(deDataDefinitionField.isShowLabel());
		ddmFormField.setTip(
			LocalizedValueUtil.toLocalizedValue(
				deDataDefinitionField.getTip()));
		ddmFormField.setType(deDataDefinitionField.getFieldType());

		Map<String, Object> customProperties =
			deDataDefinitionField.getCustomProperties();

		if (MapUtil.isNotEmpty(customProperties)) {
			Map<String, DDMFormField> settingsDDMFormFieldsMap =
				getSettingsDDMFormFields(
					ddmFormFieldTypeServicesTracker,
					deDataDefinitionField.getFieldType());

			for (Map.Entry<String, Object> entry :
					customProperties.entrySet()) {

				if (ArrayUtil.contains(
						_PREDEFINED_PROPERTIES, entry.getKey())) {

					continue;
				}

				DDMFormField settingsDDMFormField =
					settingsDDMFormFieldsMap.get(entry.getKey());

				if (settingsDDMFormField != null) {
					if (settingsDDMFormField.isLocalizable()) {
						ddmFormField.setProperty(
							entry.getKey(),
							LocalizedValueUtil.toLocalizedValue(
								(Map<String, Object>)entry.getValue()));
					}
					else if (Objects.equals(
								settingsDDMFormField.getDataType(),
								"boolean")) {

						ddmFormField.setProperty(
							entry.getKey(),
							GetterUtil.getBoolean(entry.getValue()));
					}
					else if (Objects.equals(
								settingsDDMFormField.getDataType(),
								"ddm-options")) {

						ddmFormField.setProperty(
							entry.getKey(),
							getDDMFormFieldOptions(
								(Map<String, List<Map<String, String>>>)
									entry.getValue()));
					}
					else if (Objects.equals(
								settingsDDMFormField.getType(), "validation")) {

						ddmFormField.setProperty(
							entry.getKey(),
							getDDMFormFieldValidation(
								(Map<String, Object>)entry.getValue()));
					}
					else {
						ddmFormField.setProperty(
							entry.getKey(), entry.getValue());
					}
				}
			}
		}

		return ddmFormField;
	}

	protected List<DDMFormField> toDDMFormFields(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		List<DEDataDefinitionField> deDataDefinitionFields) {

		if (ListUtil.isEmpty(deDataDefinitionFields)) {
			return Collections.emptyList();
		}

		Stream<DEDataDefinitionField> stream = deDataDefinitionFields.stream();

		return stream.map(
			deDataDefinitionField -> toDDMFormField(
				ddmFormFieldTypeServicesTracker, deDataDefinitionField)
		).collect(
			Collectors.toList()
		);
	}

	protected DEDataDefinition toDEDataDefinition(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		DDMStructure ddmStructure) {

		DDMForm ddmForm = ddmStructure.getDDMForm();

		return DEDataDefinition.newBuilder(
			ddmStructure.getClassNameId(), ddmStructure.getCompanyId(),
			toDataDefinitionFields(
				ddmForm.getDDMFormFields(), ddmFormFieldTypeServicesTracker),
			ddmStructure.getGroupId()
		).availableLocales(
			ddmForm.getAvailableLocales()
		).dateCreated(
			ddmStructure.getCreateDate()
		).dateModified(
			ddmStructure.getModifiedDate()
		).deDataDefinitionId(
			ddmStructure.getStructureId()
		).deDataDefinitionKey(
			ddmStructure.getStructureKey()
		).defaultLocale(
			ddmForm.getDefaultLocale()
		).description(
			ddmStructure.getDescriptionMap()
		).name(
			ddmStructure.getNameMap()
		).storageType(
			ddmStructure.getStorageType()
		).userId(
			ddmStructure.getUserId()
		).build();
	}

	protected SaveDataDefinitionResponse update(
		SaveDataDefinitionRequest saveDataDefinitionRequest) {

		try {
			DEDataDefinition deDataDefinition =
				saveDataDefinitionRequest.getDEDataDefinition();

			if (saveDataDefinitionRequest.isPermissionAware()) {
				DataEnginePermissionUtil.checkPermission(
					ActionKeys.ACCESS, _groupLocalService,
					deDataDefinition.getGroupId());
			}

			DDMFormSerializerSerializeRequest.Builder builder =
				DDMFormSerializerSerializeRequest.Builder.newBuilder(
					toDDMForm(
						_ddmFormFieldTypeServicesTracker, deDataDefinition));

			DDMFormSerializerSerializeResponse
				ddmFormSerializerSerializeResponse =
					_ddmFormSerializer.serialize(builder.build());

			deDataDefinition = toDEDataDefinition(
				_ddmFormFieldTypeServicesTracker,
				_ddmStructureLocalService.updateStructure(
					PrincipalThreadLocal.getUserId(),
					deDataDefinition.getDEDataDefinitionId(),
					DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
					deDataDefinition.getName(),
					deDataDefinition.getDescription(),
					ddmFormSerializerSerializeResponse.getContent(),
					new ServiceContext()));

			SaveDataDefinitionResponse saveDataDefinitionResponse =
				new SaveDataDefinitionResponse();

			saveDataDefinitionResponse.setDEDataDefinition(deDataDefinition);

			return saveDataDefinitionResponse;
		}
		catch (NoSuchStructureException nsse) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsse, nsse);
			}

			String errorMessage =
				"No such data definition: " +
					saveDataDefinitionRequest.getDEDataDefinition();

			throw new DataEngineException(
				Arrays.asList(
					new DataEngineError(
						DataEngineErrorCode.NO_SUCH_DATA_DEFINITION,
						errorMessage)),
				nsse);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			String errorMessage =
				"Unable to update data definition: " +
					saveDataDefinitionRequest.getDEDataDefinition();

			throw new DataEngineException(
				Arrays.asList(
					new DataEngineError(
						DataEngineErrorCode.UNABLE_TO_SAVE_DATA_DEFINITION,
						errorMessage)),
				e);
		}
	}

	private static final String[] _PREDEFINED_PROPERTIES = {
		"indexType", "label", "localizable", "name", "predefinedValue",
		"readOnly", "repeatable", "required", "showLabel", "tip", "type"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		DataDefinitionRequestExecutorImpl.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DataRecordCollectionExecutor _dataRecordCollectionExecutor;

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference(target = "(ddm.form.serializer.type=json)")
	private DDMFormSerializer _ddmFormSerializer;

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	@Reference
	private DEDataDefinitionFieldLinkLocalService
		_deDataDefinitionFieldLinkLocalService;

	@Reference
	private DEDataListViewLocalService _deDataListViewLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

}
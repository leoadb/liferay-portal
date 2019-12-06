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

package com.liferay.data.engine.rest.internal.resource.v2_0;

import com.liferay.data.engine.adapter.DataEngineAdapter;
import com.liferay.data.engine.adapter.data.definition.DeleteDataDefinitionRequest;
import com.liferay.data.engine.adapter.data.definition.SaveDataDefinitionRequest;
import com.liferay.data.engine.adapter.data.definition.SaveDataDefinitionResponse;
import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionField;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Leonardo Barros
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v2_0/data-definition.properties",
	scope = ServiceScope.PROTOTYPE, service = DataDefinitionResource.class
)
public class DataDefinitionResourceImpl extends BaseDataDefinitionResourceImpl {

	@Override
	public void deleteDataDefinition(Long dataDefinitionId) throws Exception {
		DeleteDataDefinitionRequest deleteDataDefinitionRequest =
			new DeleteDataDefinitionRequest();

		deleteDataDefinitionRequest.setDEDataDefinitionId(dataDefinitionId);

		_dataEngineAdapter.execute(deleteDataDefinitionRequest);
	}

	@Override
	public DataDefinition postDataDefinition(DataDefinition dataDefinition)
		throws Exception {

		SaveDataDefinitionRequest saveDataDefinitionRequest =
			new SaveDataDefinitionRequest();

		saveDataDefinitionRequest.setDEDataDefinition(
			toDEDataDefinition(dataDefinition, contextCompany.getCompanyId()));

		SaveDataDefinitionResponse saveDataDefinitionResponse =
			_dataEngineAdapter.execute(saveDataDefinitionRequest);

		return toDataDefinition(
			saveDataDefinitionResponse.getDEDataDefinition());
	}

	protected DataDefinition toDataDefinition(
		DEDataDefinition deDataDefinition) {

		return new DataDefinition() {
			{
				availableLanguageIds = toLanguageIds(
					deDataDefinition.getAvailableLocales());
				classNameId = deDataDefinition.getClassNameId();
				dataDefinitionFields = toDataDefinitionFields(
					_ddmFormFieldTypeServicesTracker,
					deDataDefinition.getDEDataDefinitionFields());
				dataDefinitionKey = deDataDefinition.getDEDataDefinitionKey();
				dateCreated = deDataDefinition.getDateCreated();
				dateModified = deDataDefinition.getDateModified();
				defaultLanguageId = LanguageUtil.getLanguageId(
					deDataDefinition.getDefaultLocale());
				description = LocalizedValueUtil.toStringObjectMap(
					deDataDefinition.getDescription());
				id = deDataDefinition.getDEDataDefinitionId();
				name = LocalizedValueUtil.toStringObjectMap(
					deDataDefinition.getName());
				siteId = deDataDefinition.getGroupId();
				storageType = deDataDefinition.getStorageType();
				userId = deDataDefinition.getUserId();
			}
		};
	}

	protected DataDefinitionField toDataDefinitionField(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		DEDataDefinitionField deDataDefinitionField) {

		return new DataDefinitionField() {
			{
				customProperties = deDataDefinitionField.getCustomProperties();
				defaultValue = deDataDefinitionField.getPredefinedValue();
				fieldType = deDataDefinitionField.getFieldType();
				indexable = Validator.isNotNull(
					deDataDefinitionField.getIndexType());
				indexType = DataDefinitionField.IndexType.create(
					deDataDefinitionField.getIndexTypeAsString());
				label = deDataDefinitionField.getLabel();
				localizable = deDataDefinitionField.isLocalizable();
				name = deDataDefinitionField.getName();
				nestedDataDefinitionFields = toDataDefinitionFields(
					ddmFormFieldTypeServicesTracker,
					deDataDefinitionField.getNestedDEDataDefinitionFields());
				readOnly = deDataDefinitionField.isReadOnly();
				repeatable = deDataDefinitionField.isRepeatable();
				required = deDataDefinitionField.isRequired();
				showLabel = deDataDefinitionField.isShowLabel();
				tip = deDataDefinitionField.getTip();
			}
		};
	}

	protected DataDefinitionField[] toDataDefinitionFields(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		List<DEDataDefinitionField> deDataDefinitionFields) {

		if (ListUtil.isEmpty(deDataDefinitionFields)) {
			return new DataDefinitionField[0];
		}

		Stream<DEDataDefinitionField> stream = deDataDefinitionFields.stream();

		return stream.map(
			deDataDefinitionField -> toDataDefinitionField(
				ddmFormFieldTypeServicesTracker, deDataDefinitionField)
		).collect(
			Collectors.toList()
		).toArray(
			new DataDefinitionField[0]
		);
	}

	protected DEDataDefinition toDEDataDefinition(
		DataDefinition dataDefinition, Long instanceId) {

		return DEDataDefinition.newBuilder(
			dataDefinition.getClassNameId(), instanceId,
			toDEDataDefinitionFields(dataDefinition.getDataDefinitionFields())
		).build();
	}

	protected DEDataDefinitionField toDEDataDefinitionField(
		DataDefinitionField dataDefinitionField) {

		DEDataDefinitionField.Builder builder =
			DEDataDefinitionField.newBuilder(
				dataDefinitionField.getFieldType(),
				dataDefinitionField.getName()
			).indexType(
				DEDataDefinitionField.IndexType.create(
					dataDefinitionField.getIndexTypeAsString())
			).indexable(
				dataDefinitionField.getIndexable()
			).label(
				dataDefinitionField.getLabel()
			).localizable(
				dataDefinitionField.getLocalizable()
			).nestedDEDataDefinitionFields(
				toDEDataDefinitionFields(
					dataDefinitionField.getNestedDataDefinitionFields())
			).predefinedValue(
				dataDefinitionField.getDefaultValue()
			).required(
				dataDefinitionField.getRequired()
			).repeatable(
				dataDefinitionField.getRepeatable()
			).readOnly(
				dataDefinitionField.getReadOnly()
			).showLabel(
				dataDefinitionField.getShowLabel()
			).tip(
				dataDefinitionField.getTip()
			);

		Map<String, Object> customProperties =
			dataDefinitionField.getCustomProperties();

		for (Map.Entry<String, Object> entry : customProperties.entrySet()) {
			builder = builder.customProperty(entry.getKey(), entry.getValue());
		}

		return builder.build();
	}

	protected List<DEDataDefinitionField> toDEDataDefinitionFields(
		DataDefinitionField[] dataDefinitionFields) {

		return Stream.of(
			dataDefinitionFields
		).map(
			this::toDEDataDefinitionField
		).collect(
			Collectors.toList()
		);
	}

	protected String[] toLanguageIds(Set<Locale> locales) {
		Stream<Locale> stream = locales.stream();

		return stream.map(
			LanguageUtil::getLanguageId
		).collect(
			Collectors.toList()
		).toArray(
			new String[0]
		);
	}

	@Reference
	private DataEngineAdapter _dataEngineAdapter;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

}
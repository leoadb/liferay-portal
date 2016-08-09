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

package com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_2;

import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.expression.VariableDependencies;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONSerializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.upgrade.AutoBatchPreparedStatementUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class UpgradeVisibilityExpression extends UpgradeProcess {

	public UpgradeVisibilityExpression(
		DDMExpressionFactory ddmExpressionFactory,
		DDMFormJSONDeserializer ddmFormJSONDeserializer,
		DDMFormJSONSerializer ddmFormJSONSerializer) {

		_ddmExpressionFactory = ddmExpressionFactory;
		_ddmFormJSONDeserializer = ddmFormJSONDeserializer;
		_ddmFormJSONSerializer = ddmFormJSONSerializer;
	}

	protected void createDDMFormRule(
		DDMForm ddmForm, String ddmFormFieldName, String visibilityExpression) {

		String action = String.format(
			"set(fieldAt(\"%s\", 0), \"visible\", %s)", ddmFormFieldName,
			visibilityExpression);

		List<String> actions = new ArrayList<>();
		actions.add(action);

		DDMFormRule ddmFormRule = new DDMFormRule("TRUE", actions);
		ddmForm.addDDMFormRule(ddmFormRule);
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateDDMStructureDefinitions();
		updateDDMStructureVersionDefinitions();
	}

	protected DDMForm getDDMForm(long structureId, String definition)
		throws Exception {

		DDMForm ddmForm = _ddmForms.get(structureId);

		if (ddmForm != null) {
			return ddmForm;
		}

		ddmForm = _ddmFormJSONDeserializer.deserialize(definition);

		_ddmForms.put(structureId, ddmForm);

		return ddmForm;
	}

	protected void updateDDMStructureDefinitions() throws Exception {
		StringBundler sb1 = new StringBundler(3);

		sb1.append("select structureId, definition from DDMStructure ");
		sb1.append("where exists (select 1 from DDLRecordSet ");
		sb1.append("where DDMStructureId = structureId and scope = 2)");

		try (PreparedStatement ps1 = connection.prepareStatement(
				sb1.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructure set definition = ? " +
						"where structureId = ?");
			ResultSet rs = ps1.executeQuery();) {

			while (rs.next()) {
				long structureId = rs.getLong("structureId");
				String definition = rs.getString("definition");

				String newDefinition = updateDefinition(
					structureId, definition);

				if (!newDefinition.equals(definition)) {
					ps2.setString(1, newDefinition);
					ps2.setLong(2, structureId);

					ps2.addBatch();
				}
			}

			ps2.executeBatch();
		}
	}

	protected void updateDDMStructureVersionDefinitions() throws Exception {
		StringBundler sb1 = new StringBundler(4);

		sb1.append("select structureId, structureVersionId, definition ");
		sb1.append("from DDMStructureVersion ");
		sb1.append("where exists (select 1 from DDLRecordSet ");
		sb1.append("where DDMStructureId = structureId and scope = 2)");

		try (PreparedStatement ps1 = connection.prepareStatement(
				sb1.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureVersion " + "set definition = ? " +
						"where structureVersionId = ?");
			ResultSet rs = ps1.executeQuery();) {

			while (rs.next()) {
				long structureId = rs.getLong("structureId");
				long structureVersionId = rs.getLong("structureVersionId");
				String definition = rs.getString("definition");

				String newDefinition = updateDefinition(
					structureId, definition);

				if (!newDefinition.equals(definition)) {
					ps2.setString(1, newDefinition);
					ps2.setLong(2, structureVersionId);

					ps2.addBatch();
				}
			}

			ps2.executeBatch();
		}
	}

	protected String updateDefinition(long structureId, String definition)
		throws Exception {

		DDMForm ddmForm = getDDMForm(structureId, definition);

		Map<String, DDMFormField> ddmFormFieldMap = ddmForm.getDDMFormFieldsMap(
			true);

		boolean needUpdate = false;

		for (Map.Entry<String, DDMFormField> entry :
				ddmFormFieldMap.entrySet()) {

			DDMFormField ddmFormField = entry.getValue();

			if (Validator.isNotNull(ddmFormField.getVisibilityExpression())) {
				upgradeVisibilityExpression(ddmFormField);

				needUpdate = true;
			}
		}

		if (needUpdate) {
			_ddmForms.put(structureId, ddmForm);
		}

		return _ddmFormJSONSerializer.serialize(ddmForm);
	}

	protected void upgradeVisibilityExpression(DDMFormField ddmFormField)
		throws Exception {

		String visibilityExpression = ddmFormField.getVisibilityExpression();

		DDMForm ddmForm = ddmFormField.getDDMForm();

		DDMExpression<Boolean> expression =
			_ddmExpressionFactory.createBooleanDDMExpression(
				visibilityExpression);

		Map<String, VariableDependencies> variableDependenciesMap =
			expression.getVariableDependenciesMap();

		for (String ddmFormFieldName : variableDependenciesMap.keySet()) {
			visibilityExpression = visibilityExpression.replace(
				ddmFormFieldName,
				String.format(
					"get(fieldAt(\"%s\",0),\"value\")", ddmFormFieldName));
		}

		createDDMFormRule(
			ddmForm, ddmFormField.getName(), visibilityExpression);

		ddmFormField.setVisibilityExpression(StringPool.BLANK);
	}

	private final DDMExpressionFactory _ddmExpressionFactory;
	private final DDMFormJSONDeserializer _ddmFormJSONDeserializer;
	private final DDMFormJSONSerializer _ddmFormJSONSerializer;
	private final Map<Long, DDMForm> _ddmForms = new HashMap<>();

}
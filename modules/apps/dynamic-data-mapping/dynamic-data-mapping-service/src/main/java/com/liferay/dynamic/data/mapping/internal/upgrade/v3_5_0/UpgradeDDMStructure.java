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

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_5_0;

import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Marcela Cunha
 */
public class UpgradeDDMStructure extends UpgradeProcess {

	public UpgradeDDMStructure(
		DDM ddm, DDMFormLayoutSerializer ddmFormLayoutSerializer,
		DDMFormDeserializer ddmFormJSONDeserializer,
		DDMFormDeserializer ddmFormXSDDeserializer) {

		_ddm = ddm;
		_ddmFormLayoutSerializer = ddmFormLayoutSerializer;
		_ddmFormJSONDeserializer = ddmFormJSONDeserializer;
		_ddmFormXSDDeserializer = ddmFormXSDDeserializer;
	}

	protected DDMForm deserialize(String content, String type) {
		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(content);

		DDMFormDeserializer ddmFormDeserializer = null;

		if (StringUtil.equalsIgnoreCase(type, "json")) {
			ddmFormDeserializer = _ddmFormJSONDeserializer;
		}
		else if (StringUtil.equalsIgnoreCase(type, "xsd")) {
			ddmFormDeserializer = _ddmFormXSDDeserializer;
		}

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				ddmFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb1 = new StringBundler(6);

		sb1.append("insert into DDMStructureVersion (structureVersionId, ");
		sb1.append("groupId, companyId, userId, userName, createDate, ");
		sb1.append("structureId, version, parentStructureId, name, ");
		sb1.append("description, definition, storageType, type_, status, ");
		sb1.append("statusByUserId, statusByUserName, statusDate) values (?, ");
		sb1.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		StringBundler sb2 = new StringBundler(5);

		sb2.append("insert into DDMStructureLayout (uuid_, ");
		sb2.append("structureLayoutId, groupId, companyId, userId, userName, ");
		sb2.append("createDate, modifiedDate, structureLayoutKey, ");
		sb2.append("structureVersionId, definition) values (?, ?, ?, ?, ?, ");
		sb2.append("?, ?, ?, ?, ?, ?)");

		try (PreparedStatement ps1 = connection.prepareStatement(
				"select * from DDMStructure where classNameId = ? or " +
					"classNameId = ?");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructure set definition = ? where " +
						"structureId = ?");
			PreparedStatement ps3 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb1.toString());
			PreparedStatement ps4 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb2.toString())) {

			ps1.setLong(
				1,
				PortalUtil.getClassNameId(
					"com.liferay.journal.model.JournalArticle"));
			ps1.setLong(
				2,
				PortalUtil.getClassNameId(
					"com.liferay.document.library.kernel.model." +
						"DLFileEntryMetadata"));

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					String definition = upgradeDefinition(
						rs.getLong("companyId"), rs.getString("definition"));

					ps2.setString(1, definition);

					ps2.setLong(2, rs.getLong("structureId"));
					ps2.addBatch();

					long structureVersionId = increment();

					ps3.setLong(1, structureVersionId);

					ps3.setLong(2, rs.getLong("groupId"));
					ps3.setLong(3, rs.getLong("companyId"));
					ps3.setLong(4, rs.getLong("userId"));
					ps3.setString(5, rs.getString("userName"));
					ps3.setTimestamp(6, rs.getTimestamp("modifiedDate"));
					ps3.setLong(7, rs.getLong("structureId"));
					ps3.setString(8, getVersion(rs.getLong("structureId")));
					ps3.setLong(9, rs.getLong("parentStructureId"));
					ps3.setString(10, rs.getString("name"));
					ps3.setString(11, rs.getString("description"));
					ps3.setString(12, definition);
					ps3.setString(13, rs.getString("storageType"));
					ps3.setInt(14, rs.getInt("type_"));
					ps3.setInt(15, WorkflowConstants.STATUS_APPROVED);
					ps3.setLong(16, rs.getLong("userId"));
					ps3.setString(17, rs.getString("userName"));
					ps3.setTimestamp(18, rs.getTimestamp("modifiedDate"));
					ps3.addBatch();

					DDMForm ddmForm = getDDMForm(
						definition, rs.getString("storageType"));

					String ddmFormLayoutDefinition =
						getDefaultDDMFormLayoutDefinition(ddmForm);

					ps4.setString(1, PortalUUIDUtil.generate());
					ps4.setLong(2, increment());
					ps4.setLong(3, rs.getLong("groupId"));
					ps4.setLong(4, rs.getLong("companyId"));
					ps4.setLong(5, rs.getLong("userId"));
					ps4.setString(6, rs.getString("userName"));
					ps4.setTimestamp(7, rs.getTimestamp("modifiedDate"));
					ps4.setTimestamp(8, rs.getTimestamp("modifiedDate"));
					ps4.setString(9, rs.getString("structureKey"));
					ps4.setLong(10, structureVersionId);
					ps4.setString(11, ddmFormLayoutDefinition);

					ps4.addBatch();
				}

				ps2.executeBatch();

				ps3.executeBatch();

				ps4.executeBatch();
			}
		}
	}

	protected DDMForm getDDMForm(String definition, String storageType) {
		if (storageType.equals("expando") || storageType.equals("xml")) {
			return deserialize(definition, "xsd");
		}

		return deserialize(definition, "json");
	}

	protected String getDefaultDDMFormLayoutDefinition(DDMForm ddmForm) {
		DDMFormLayout ddmFormLayout = _ddm.getDefaultDDMFormLayout(ddmForm);

		DDMFormLayoutSerializerSerializeRequest.Builder builder =
			DDMFormLayoutSerializerSerializeRequest.Builder.newBuilder(
				ddmFormLayout);

		DDMFormLayoutSerializerSerializeResponse
			ddmFormLayoutSerializerSerializeResponse =
				_ddmFormLayoutSerializer.serialize(builder.build());

		return ddmFormLayoutSerializerSerializeResponse.getContent();
	}

	protected String getNextVersion(String version) {
		int[] versionParts = StringUtil.split(version, StringPool.PERIOD, 0);

		return versionParts[0] + StringPool.PERIOD + ++versionParts[1];
	}

	protected String getVersion(Long structureId) throws Exception {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select MAX(version) from DDMStructureVersion where " +
					"structureId = ?")) {

			ps1.setLong(1, structureId);

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					String lastVersion = rs.getString(1);

					return getNextVersion(lastVersion);
				}
			}
		}

		return DDMStructureConstants.VERSION_DEFAULT;
	}

	protected void upgradeColorField(JSONObject jsonObject) {
		upgradeFieldType(
			StringPool.BLANK,
			jsonObject.put(
				"dataType", "string"
			).put(
				"visibilityExpression", StringPool.BLANK
			));
	}

	protected void upgradeDateField(JSONObject jsonObject) {
		upgradeFieldType(
			StringPool.BLANK,
			jsonObject.put(
				"dataType", "string"
			).put(
				"visibilityExpression", StringPool.BLANK
			));
	}

	protected void upgradeDecimalField(JSONObject jsonObject) {
		upgradeFieldType(
			"numeric",
			jsonObject.put(
				"dataType", "decimal"
			).put(
				"visibilityExpression", StringPool.BLANK
			));
	}

	protected String upgradeDefinition(long companyId, String definition)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(definition);

		jsonObject.put(
			"fields",
			upgradeFields(companyId, jsonObject.getJSONArray("fields")));

		return jsonObject.toString();
	}

	protected void upgradeDocumentLibraryField(JSONObject jsonObject) {
		upgradeFieldType(
			"document_library",
			jsonObject.put(
				"dataType", "string"
			).put(
				"visibilityExpression", StringPool.BLANK
			));
	}

	protected JSONArray upgradeFields(long companyId, JSONArray fieldsJSONArray)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		if (fieldsJSONArray != null) {
			for (int i = 0; i < fieldsJSONArray.length(); i++) {
				JSONObject jsonObject = fieldsJSONArray.getJSONObject(i);

				String type = jsonObject.getString("type");

				if (StringUtil.equals(type, "ddm-color")) {
					upgradeColorField(jsonObject);
				}
				else if (StringUtil.equals(type, "ddm-date")) {
					upgradeDateField(jsonObject);
				}
				else if (type.startsWith("ddm-decimal")) {
					upgradeDecimalField(jsonObject);
				}
				else if (type.startsWith("ddm-documentlibrary")) {
					upgradeDocumentLibraryField(jsonObject);
				}
				else if (type.startsWith("ddm-geolocation")) {
					upgradeGeolocation(jsonObject);
				}
				else if (type.startsWith("ddm-integer")) {
					upgradeIntegerField(jsonObject);
				}
				else if (type.startsWith("ddm-number")) {
					upgradeNumberField(jsonObject);
				}
				else if (StringUtil.equals(type, "ddm-separator")) {
					upgradeSeparatorField(jsonObject);
				}
				else if (type.startsWith("ddm-")) {
					upgradeFieldType(StringPool.BLANK, jsonObject);
				}
				else if (StringUtil.equals(type, "text")) {
					upgradeTextField(companyId, jsonObject);
				}
				else if (StringUtil.equals(type, "textarea")) {
					upgradeTextArea(jsonObject);
				}

				if (jsonObject.has("nestedFields")) {
					jsonObject.put(
						"nestedFields",
						upgradeFields(
							companyId,
							jsonObject.getJSONArray("nestedFields")));
				}

				jsonArray.put(jsonObject);
			}
		}

		return jsonArray;
	}

	protected void upgradeFieldType(String fieldType, JSONObject jsonObject) {
		String type = jsonObject.getString("type");

		if (Validator.isNull(fieldType.isEmpty())) {
			jsonObject.put("type", type.substring(4));
		}
		else {
			jsonObject.put("type", fieldType);
		}
	}

	protected void upgradeGeolocation(JSONObject jsonObject) {
		upgradeFieldType(
			StringPool.BLANK, jsonObject.put("dataType", "string"));
	}

	protected void upgradeIntegerField(JSONObject jsonObject) {
		upgradeFieldType(
			"numeric",
			jsonObject.put("visibilityExpression", StringPool.BLANK));
	}

	protected void upgradeNumberField(JSONObject jsonObject) {
		upgradeFieldType(
			"numeric",
			jsonObject.put(
				"dataType", "decimal"
			).put(
				"visibilityExpression", StringPool.BLANK
			));
	}

	protected void upgradeSeparatorField(JSONObject jsonObject) {
		jsonObject.put("dataType", StringPool.BLANK);
	}

	protected void upgradeTextArea(JSONObject jsonObject) {
		upgradeFieldType(
			"paragraph",
			jsonObject.put(
				"dataType", StringPool.BLANK
			).put(
				"fieldNamespace", StringPool.BLANK
			).put(
				"text", jsonObject.getJSONObject("predefinedValue")
			).put(
				"visibilityExpression", StringPool.BLANK
			));
	}

	protected void upgradeTextField(long companyId, JSONObject jsonObject)
		throws Exception {

		jsonObject.put(
			"autocomplete", false
		).put(
			"dataSourceType", "manual"
		).put(
			"ddmDataProviderInstanceId", "[]"
		).put(
			"ddmDataProviderInstanceOutput", "[]"
		).put(
			"displayStyle", "singleline"
		).put(
			"fieldNamespace", StringPool.BLANK
		).put(
			"options",
			JSONUtil.put(
				JSONUtil.put(
					"label",
					JSONUtil.put(
						UpgradeProcessUtil.getDefaultLanguageId(companyId),
						GetterUtil.getString("Option"))
				).put(
					"value", "Option"
				))
		).put(
			"placeholder",
			JSONUtil.put(
				UpgradeProcessUtil.getDefaultLanguageId(companyId),
				StringPool.BLANK)
		).put(
			"tooltip",
			JSONUtil.put(
				UpgradeProcessUtil.getDefaultLanguageId(companyId),
				StringPool.BLANK)
		).put(
			"visibilityExpression", StringPool.BLANK
		);
	}

	private final DDM _ddm;
	private final DDMFormDeserializer _ddmFormJSONDeserializer;
	private final DDMFormLayoutSerializer _ddmFormLayoutSerializer;
	private final DDMFormDeserializer _ddmFormXSDDeserializer;

}
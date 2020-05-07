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

package com.liferay.data.engine.rest.dto.v2_0.util;

import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutColumn;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutPage;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutRow;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.runners.MockitoJUnitRunner;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Mateus Santana
 */
@RunWith(MockitoJUnitRunner.class)
public class DataEngineUtilTest extends PowerMockito {

	@Before
	public void setUp() {
		_setUpJSONFactoryUtil();
	}

	@Test
	public void testToDataDefinition() {
		Assert.assertEquals(
			new DataDefinition() {
				{
					availableLanguageIds = new String[] {"en_US", "pt_BR"};
					dataDefinitionFields = new DataDefinitionField[] {
						new DataDefinitionField() {
							{
								customProperties =
									HashMapBuilder.<String, Object>put(
										"dataSourceType",
										new String[] {"manual"}
									).put(
										"dataType", "string"
									).put(
										"ddmDataProviderInstanceId", new Long[0]
									).put(
										"ddmDataProviderInstanceOutput",
										new Long[0]
									).put(
										"fieldNamespace", ""
									).put(
										"multiple", false
									).put(
										"options",
										HashMapBuilder.<String, Object>put(
											"en_US",
											new String[] {
												JSONUtil.put(
													"label", "Label 1"
												).put(
													"value", "value1"
												).toJSONString(),
												JSONUtil.put(
													"label", "Label 2"
												).put(
													"value", "value2"
												).toJSONString()
											}
										).build()
									).put(
										"validation",
										HashMapBuilder.<String, Object>put(
											"errorMessage",
											new HashMap<String, Object>()
										).put(
											"expression",
											new HashMap<String, Object>()
										).put(
											"parameter",
											new HashMap<String, Object>()
										).put(
											"visibilityExpression", ""
										).build()
									).build();
								defaultValue =
									HashMapBuilder.<String, Object>put(
										"en_US", new String[0]
									).build();
								fieldType = "select";
								label = HashMapBuilder.<String, Object>put(
									"en_US", "Select from list"
								).put(
									"pt_BR", "Selecione da lista"
								).build();
								localizable = true;
								name = "SelectFromList";
								nestedDataDefinitionFields =
									new DataDefinitionField[0];
								readOnly = false;
								repeatable = false;
								required = false;
								showLabel = true;
								tip = HashMapBuilder.<String, Object>put(
									"en_US", ""
								).build();
							}
						}
					};
					defaultDataLayout = new DataLayout() {
						{
							dataLayoutPages = new DataLayoutPage[] {
								new DataLayoutPage() {
									{
										dataLayoutRows = new DataLayoutRow[] {
											new DataLayoutRow() {
												{
													dataLayoutColumns =
														new DataLayoutColumn[] {
															new DataLayoutColumn() {
																{
																	columnSize =
																		12;
																	fieldNames =
																		new
																		String[]
																			 {
																				"SelectFromList"
																			};
																}
															}
														};
												}
											}
										};
									}
								}
							};
							name = HashMapBuilder.<String, Object>put(
								"en_US", "Struct"
							).build();
							paginationMode = "wizard";
						}
					};
					defaultLanguageId = "en_US";
					name = HashMapBuilder.<String, Object>put(
						"en_US", "Struct"
					).build();
				}
			},
			DataEngineUtil.toDataDefinition(
				new DataDefinition() {
					{
						availableLanguageIds = new String[] {"en_US", "pt_BR"};
						dataDefinitionFields = new DataDefinitionField[] {
							new DataDefinitionField() {
								{
									customProperties =
										HashMapBuilder.<String, Object>put(
											"dataSourceType",
											new String[] {"manual"}
										).put(
											"dataType", "string"
										).put(
											"ddmDataProviderInstanceId",
											new Long[0]
										).put(
											"ddmDataProviderInstanceOutput",
											new Long[0]
										).put(
											"fieldNamespace", ""
										).put(
											"multiple", false
										).put(
											"options",
											HashMapBuilder.<String, Object>put(
												"en_US",
												new String[] {
													JSONUtil.put(
														"label", "Label 1"
													).put(
														"value", "value1"
													).toJSONString(),
													JSONUtil.put(
														"label", "Label 2"
													).put(
														"value", "value2"
													).toJSONString()
												}
											).build()
										).put(
											"validation",
											HashMapBuilder.<String, Object>put(
												"errorMessage",
												new HashMap<String, Object>()
											).put(
												"expression",
												new HashMap<String, Object>()
											).put(
												"parameter",
												new HashMap<String, Object>()
											).put(
												"visibilityExpression", ""
											).build()
										).build();
									defaultValue =
										HashMapBuilder.<String, Object>put(
											"en_US", new String[0]
										).build();
									fieldType = "select";
									label = HashMapBuilder.<String, Object>put(
										"en_US", "Select from list"
									).put(
										"pt_BR", "Selecione da lista"
									).build();
									localizable = true;
									name = "SelectFromList";
									nestedDataDefinitionFields =
										new DataDefinitionField[0];
									readOnly = false;
									repeatable = false;
									required = false;
									showLabel = true;
									tip = HashMapBuilder.<String, Object>put(
										"en_US", ""
									).build();
								}
							}
						};
						defaultDataLayout = new DataLayout() {
							{
								dataLayoutPages = new DataLayoutPage[] {
									new DataLayoutPage() {
										{
											dataLayoutRows =
												new DataLayoutRow[] {
													new DataLayoutRow() {
														{
															dataLayoutColumns =
																new
																DataLayoutColumn
																	[] {
																	new DataLayoutColumn() {
																		{
																			columnSize =
																				12;
																			fieldNames =
																				new
																				String
																					[]
																					 {
																						"SelectFromList"
																					};
																		}
																	}
																};
														}
													}
												};
										}
									}
								};
								name = HashMapBuilder.<String, Object>put(
									"en_US", "Struct"
								).build();
								paginationMode = "wizard";
							}
						};
						defaultLanguageId = "en_US";
						name = HashMapBuilder.<String, Object>put(
							"en_US", "Struct"
						).build();
					}
				}));
	}

	@Test
	public void testToDataDefinitionWithEmptyDataDefinition() {
		Assert.assertNotNull(
			DataEngineUtil.toDataDefinition(new DataDefinition()));
	}

	protected void _setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

}
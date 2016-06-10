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

package com.liferay.dynamic.data.mapping.form.evaluator.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.liferay.dynamic.data.mapping.form.evaluator.internal.rules.DDMFormRuleEvaluatorContext;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.rules.DDMFormRuleEvaluatorGraphHelper;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.rules.DDMFormRuleEvaluatorNode;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldRuleType;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Leonardo Barros
 */
@PrepareForTest({DDMFormFactory.class})
@RunWith(PowerMockRunner.class)
public class DDMFormRuleEvaluatorGraphHelperTest 
	extends DDMFormRuleEvaluatorBaseTest {

	@Test
	public void testCreateDDMFormRuleEvaluatorNodeEdges() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField fieldDDMFormField0 = new DDMFormField("field0", "text");

		ddmForm.addDDMFormField(fieldDDMFormField0);
		
		DDMFormField fieldDDMFormField1 = new DDMFormField("field1", "text");

		ddmForm.addDDMFormField(fieldDDMFormField1);

		DDMFormValues ddmFormValues = createDDMFormValues();

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		DDMFormFieldValue fieldDDMFormFieldValue0 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("value0"));

		ddmFormFieldValues.add(fieldDDMFormFieldValue0);

		DDMFormFieldValue fieldDDMFormFieldValue1 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_instanceId", "field1", new UnlocalizedValue("value1"));

		ddmFormFieldValues.add(fieldDDMFormFieldValue1);

		ddmFormValues.setDDMFormFieldValues(ddmFormFieldValues);
		
		DDMFormRuleEvaluatorContext ddmFormRuleEvaluatorContext =
			createDDMFormRuleEvaluatorContext(ddmForm, ddmFormValues);
		
		DDMFormRuleEvaluatorGraphHelper ddmFormRuleEvaluatorGraphHelper = 
			new DDMFormRuleEvaluatorGraphHelper(ddmFormRuleEvaluatorContext);
		
		Set<DDMFormRuleEvaluatorNode> nodes = 
			ddmFormRuleEvaluatorGraphHelper.createDDMFormRuleEvaluatorNodeEdges(
				"isReadOnly(field1)");
		
		Assert.assertEquals(2, nodes.size());
		
		DDMFormRuleEvaluatorNode node = new DDMFormRuleEvaluatorNode(
			"field1", StringPool.BLANK, DDMFormFieldRuleType.READ_ONLY, 
			StringPool.BLANK);
		
		Assert.assertTrue(nodes.contains(node));
		
		node = new DDMFormRuleEvaluatorNode(
			"field1", StringPool.BLANK, DDMFormFieldRuleType.VALUE, 
			StringPool.BLANK);
		
		Assert.assertTrue(nodes.contains(node));
	}
	
	@Test
	public void testCreateDDMFormRuleEvaluatorNodeEdges2() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField fieldDDMFormField0 = new DDMFormField("field0", "text");

		ddmForm.addDDMFormField(fieldDDMFormField0);
		
		DDMFormField fieldDDMFormField1 = new DDMFormField("field1", "text");

		ddmForm.addDDMFormField(fieldDDMFormField1);

		DDMFormValues ddmFormValues = createDDMFormValues();

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		DDMFormFieldValue fieldDDMFormFieldValue0 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("value0"));

		ddmFormFieldValues.add(fieldDDMFormFieldValue0);

		DDMFormFieldValue fieldDDMFormFieldValue1 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_instanceId", "field1", new UnlocalizedValue("value1"));

		ddmFormFieldValues.add(fieldDDMFormFieldValue1);

		ddmFormValues.setDDMFormFieldValues(ddmFormFieldValues);
		
		DDMFormRuleEvaluatorContext ddmFormRuleEvaluatorContext =
			createDDMFormRuleEvaluatorContext(ddmForm, ddmFormValues);
		
		DDMFormRuleEvaluatorGraphHelper ddmFormRuleEvaluatorGraphHelper = 
			new DDMFormRuleEvaluatorGraphHelper(ddmFormRuleEvaluatorContext);
		
		Set<DDMFormRuleEvaluatorNode> nodes = 
			ddmFormRuleEvaluatorGraphHelper.createDDMFormRuleEvaluatorNodeEdges(
				"isVisible(field1) && contains(field0,\"value\")");
		
		Assert.assertEquals(3, nodes.size());
		
		DDMFormRuleEvaluatorNode node = new DDMFormRuleEvaluatorNode(
			"field1", StringPool.BLANK, DDMFormFieldRuleType.VISIBILITY, 
			StringPool.BLANK);
		
		Assert.assertTrue(nodes.contains(node));
		
		node = new DDMFormRuleEvaluatorNode(
			"field1", StringPool.BLANK, DDMFormFieldRuleType.VALUE, 
			StringPool.BLANK);
		
		Assert.assertTrue(nodes.contains(node));
		
		node = new DDMFormRuleEvaluatorNode(
			"field0", StringPool.BLANK, DDMFormFieldRuleType.VALUE, 
			StringPool.BLANK);
		
		Assert.assertTrue(nodes.contains(node));
	}
	
	@Test
	public void testCreateDDMFormRuleEvaluatorNodeEdges3() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField fieldDDMFormField0 = new DDMFormField("field0", "text");

		ddmForm.addDDMFormField(fieldDDMFormField0);

		DDMFormValues ddmFormValues = createDDMFormValues();

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		DDMFormFieldValue fieldDDMFormFieldValue0 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("value0"));

		ddmFormFieldValues.add(fieldDDMFormFieldValue0);

		ddmFormValues.setDDMFormFieldValues(ddmFormFieldValues);
		
		DDMFormRuleEvaluatorContext ddmFormRuleEvaluatorContext =
			createDDMFormRuleEvaluatorContext(ddmForm, ddmFormValues);
		
		DDMFormRuleEvaluatorGraphHelper ddmFormRuleEvaluatorGraphHelper = 
			new DDMFormRuleEvaluatorGraphHelper(ddmFormRuleEvaluatorContext);
		
		Set<DDMFormRuleEvaluatorNode> nodes = 
			ddmFormRuleEvaluatorGraphHelper.createDDMFormRuleEvaluatorNodeEdges(
				"2 + 3 > 4");
		
		Assert.assertEquals(0, nodes.size());
	}
	
	@Test
	public void testCreateDDMFormRuleEvaluatorNodeEdges4() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField fieldDDMFormField0 = new DDMFormField("field0", "text");

		ddmForm.addDDMFormField(fieldDDMFormField0);

		DDMFormValues ddmFormValues = createDDMFormValues();

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		DDMFormFieldValue fieldDDMFormFieldValue0 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("value0"));

		ddmFormFieldValues.add(fieldDDMFormFieldValue0);

		ddmFormValues.setDDMFormFieldValues(ddmFormFieldValues);
		
		DDMFormRuleEvaluatorContext ddmFormRuleEvaluatorContext =
			createDDMFormRuleEvaluatorContext(ddmForm, ddmFormValues);
		
		DDMFormRuleEvaluatorGraphHelper ddmFormRuleEvaluatorGraphHelper = 
			new DDMFormRuleEvaluatorGraphHelper(ddmFormRuleEvaluatorContext);
		
		Set<DDMFormRuleEvaluatorNode> nodes = 
			ddmFormRuleEvaluatorGraphHelper.createDDMFormRuleEvaluatorNodeEdges(
				"call(1,\"\",\"field0=key\")");
		
		Assert.assertEquals(2, nodes.size());
		
		DDMFormRuleEvaluatorNode node = new DDMFormRuleEvaluatorNode(
			"field0", StringPool.BLANK, DDMFormFieldRuleType.DATA_PROVIDER, 
			StringPool.BLANK);
		
		Assert.assertTrue(nodes.contains(node));
		
		node = new DDMFormRuleEvaluatorNode(
			"field0", StringPool.BLANK, DDMFormFieldRuleType.VALUE, 
			StringPool.BLANK);
		
		Assert.assertTrue(nodes.contains(node));
	}
	
	@Test
	public void testCreateDDMFormRuleEvaluatorNodeEdges5() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField fieldDDMFormField0 = new DDMFormField("country", "text");

		ddmForm.addDDMFormField(fieldDDMFormField0);

		DDMFormValues ddmFormValues = createDDMFormValues();

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		DDMFormFieldValue fieldDDMFormFieldValue0 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"country_instanceId", "country", 
				new UnlocalizedValue("value0"));

		ddmFormFieldValues.add(fieldDDMFormFieldValue0);

		ddmFormValues.setDDMFormFieldValues(ddmFormFieldValues);
		
		DDMFormRuleEvaluatorContext ddmFormRuleEvaluatorContext =
			createDDMFormRuleEvaluatorContext(ddmForm, ddmFormValues);
		
		DDMFormRuleEvaluatorGraphHelper ddmFormRuleEvaluatorGraphHelper = 
			new DDMFormRuleEvaluatorGraphHelper(ddmFormRuleEvaluatorContext);
		
		Set<DDMFormRuleEvaluatorNode> nodes = 
			ddmFormRuleEvaluatorGraphHelper.createDDMFormRuleEvaluatorNodeEdges(
				"call(1,\"\",\"country={\"key\":\"countryId\"," +
					"\"value\":\"nameCurrentValue\"}\")");
		
		Assert.assertEquals(2, nodes.size());
		
		DDMFormRuleEvaluatorNode node = new DDMFormRuleEvaluatorNode(
			"country", StringPool.BLANK, DDMFormFieldRuleType.DATA_PROVIDER, 
			StringPool.BLANK);
		
		Assert.assertTrue(nodes.contains(node));
		
		node = new DDMFormRuleEvaluatorNode(
			"country", StringPool.BLANK, DDMFormFieldRuleType.VALUE, 
			StringPool.BLANK);
		
		Assert.assertTrue(nodes.contains(node));
	}
}

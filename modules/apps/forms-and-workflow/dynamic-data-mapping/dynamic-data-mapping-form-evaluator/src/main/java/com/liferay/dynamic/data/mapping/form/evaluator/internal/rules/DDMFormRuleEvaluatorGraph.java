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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.rules;

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Leonardo Barros
 */
public class DDMFormRuleEvaluatorGraph {

	public DDMFormRuleEvaluatorGraph(
			Set<DDMFormRuleEvaluatorNode> nodes,
			DDMFormRuleEvaluatorContext ddmFormRuleEvaluatorContext)
		throws Exception {

		_nodes = nodes;
		_allNodes = new ArrayList<>(nodes);
		_ddmFormRuleEvaluatorGraphHelper = new DDMFormRuleEvaluatorGraphHelper(
			ddmFormRuleEvaluatorContext);

		createEdges();
	}

	public List<DDMFormFieldEvaluationResult> execute() throws Exception {
		_ddmFormRuleEvaluatorGraphHelper.addDDMFormFieldRuleEvaluationResults();

		List<DDMFormRuleEvaluatorNode> sortedNodes = sortNodes();

		for (DDMFormRuleEvaluatorNode node : sortedNodes) {
			_ddmFormRuleEvaluatorGraphHelper.executeDDMFormRuleEvaluatorNode(
				node);
		}

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResults =
				_ddmFormRuleEvaluatorGraphHelper.
					getDDMFormFieldEvaluationResults();

		return ListUtil.fromCollection(ddmFormFieldEvaluationResults.values());
	}

	protected void createEdges() throws Exception {
		Set<DDMFormRuleEvaluatorNode> copiedNodes = new HashSet<>(_nodes);

		for (DDMFormRuleEvaluatorNode node : copiedNodes) {
			createEdges(node);
		}
	}

	protected void createEdges(DDMFormRuleEvaluatorNode node) throws Exception {
		String expression = node.getExpression();

		boolean hasDependencies =
			_ddmFormRuleEvaluatorGraphHelper.hasDependencies(expression);

		if (!hasDependencies) {
			return;
		}

		_nodes.remove(node);

		Set<DDMFormRuleEvaluatorNode> edges =
			_ddmFormRuleEvaluatorGraphHelper.
				createDDMFormRuleEvaluatorNodeEdges(expression);

		for (DDMFormRuleEvaluatorNode edge : edges) {
			if (_allNodes.contains(edge)) {
				int indexOf = _allNodes.indexOf(edge);
				DDMFormRuleEvaluatorNode foundNode = _allNodes.get(indexOf);
				node.getEdges().add(foundNode);
				_nodes.remove(foundNode);
			}
			else {
				node.getEdges().add(edge);
				_allNodes.add(edge);
				_nodes.add(edge);
			}
		}
	}

	protected List<DDMFormRuleEvaluatorNode> sortNodes() throws Exception {
		List<DDMFormRuleEvaluatorNode> sortedNodes = new ArrayList<>();

		for (DDMFormRuleEvaluatorNode node : _allNodes) {
			node.visit(sortedNodes);
		}

		return sortedNodes;
	}

	private final List<DDMFormRuleEvaluatorNode> _allNodes;
	private final DDMFormRuleEvaluatorGraphHelper
		_ddmFormRuleEvaluatorGraphHelper;
	private final Set<DDMFormRuleEvaluatorNode> _nodes;

}
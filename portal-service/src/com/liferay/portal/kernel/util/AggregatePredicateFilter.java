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

package com.liferay.portal.kernel.util;

/**
 * @author Marcellus Tavares
 */
public class AggregatePredicateFilter<T> implements PredicateFilter<T> {

	public AggregatePredicateFilter(PredicateFilter<T> predicateFilter) {
		_predicateFilterFunction = new IdentityPredicateFilterFunction(
			predicateFilter);
	}

	public AggregatePredicateFilter<T> and(PredicateFilter<T> predicateFilter) {
		_predicateFilterFunction = new AndPredicateFilterFunction(
			_predicateFilterFunction,
			new IdentityPredicateFilterFunction(predicateFilter));

		return this;
	}

	@Override
	public boolean filter(T t) {
		return _predicateFilterFunction.apply(t);
	}

	public AggregatePredicateFilter<T> negate() {
		_predicateFilterFunction = new NegatePredicateFilterFunction(
			_predicateFilterFunction);

		return this;
	}

	public AggregatePredicateFilter<T> or(PredicateFilter<T> predicateFilter) {
		_predicateFilterFunction = new OrPredicateFilterFunction(
			_predicateFilterFunction,
			new IdentityPredicateFilterFunction(predicateFilter));

		return this;
	}

	private PredicateFilterFunction<T> _predicateFilterFunction;

	private class AndPredicateFilterFunction
		implements PredicateFilterFunction<T> {

		public AndPredicateFilterFunction(
			PredicateFilterFunction<T> left, PredicateFilterFunction<T> right) {

			_left = left;
			_right = right;
		}

		@Override
		public Boolean apply(T t) {
			return _left.apply(t) && _right.apply(t);
		}

		private PredicateFilterFunction<T> _left;
		private PredicateFilterFunction<T> _right;

	}

	private class IdentityPredicateFilterFunction
		implements PredicateFilterFunction<T> {

		public IdentityPredicateFilterFunction(
			PredicateFilter<T> predicateFilter) {

			_predicateFilter = predicateFilter;
		}

		@Override
		public Boolean apply(T t) {
			return _predicateFilter.filter(t);
		}

		private final PredicateFilter<T> _predicateFilter;

	}

	private class NegatePredicateFilterFunction
		implements PredicateFilterFunction<T> {

		public NegatePredicateFilterFunction(
			PredicateFilterFunction<T> predicateFilterFunction) {

			_predicateFilterFunction = predicateFilterFunction;
		}

		@Override
		public Boolean apply(T t) {
			return !_predicateFilterFunction.apply(t);
		}

		private PredicateFilterFunction<T> _predicateFilterFunction;

	}

	private class OrPredicateFilterFunction
		implements PredicateFilterFunction<T> {

		public OrPredicateFilterFunction(
			PredicateFilterFunction<T> left, PredicateFilterFunction<T> right) {

			_left = left;
			_right = right;
		}

		@Override
		public Boolean apply(T t) {
			return _left.apply(t) || _right.apply(t);
		}

		private PredicateFilterFunction<T> _left;
		private PredicateFilterFunction<T> _right;

	}

	private interface PredicateFilterFunction<T> extends Function<T, Boolean> {
	}

}
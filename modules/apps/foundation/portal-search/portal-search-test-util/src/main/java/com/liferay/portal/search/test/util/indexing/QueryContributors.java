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

package com.liferay.portal.search.test.util.indexing;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;

/**
 * @author André de Oliveira
 */
public class QueryContributors {

	public static QueryContributor dummy() {
		return booleanQuery -> {
		};
	}

	public static QueryContributor must(Query query) {
		return booleanQuery -> QueryContributor.add(
			booleanQuery, query, BooleanClauseOccur.MUST);
	}

	public static QueryContributor mustMatch(String field, String value) {
		return must(new MatchQuery(field, value));
	}

	public static QueryContributor mustNot(Query query) {
		return booleanQuery -> QueryContributor.add(
			booleanQuery, query, BooleanClauseOccur.MUST_NOT);
	}

	public static QueryContributor mustNotMatch(String field, String value) {
		return mustNot(new MatchQuery(field, value));
	}

	public static QueryContributor mustNotTerm(String field, String value) {
		return mustNot(new TermQueryImpl(field, value));
	}

	public static QueryContributor mustTerm(String field, String value) {
		return must(new TermQueryImpl(field, value));
	}

}
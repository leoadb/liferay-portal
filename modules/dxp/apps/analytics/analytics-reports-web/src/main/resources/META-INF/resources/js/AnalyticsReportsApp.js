/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React from 'react';

import BasicInformation from './components/BasicInformation';
import Chart from './components/Chart';
import TotalCount from './components/TotalCount';
import APIService from './utils/APIService';
import {numberFormat} from './utils/numberFormat';

export default function({context, props}) {
	const {endpoints, languageTag, namespace, page} = context;
	const {authorName, publishDate, title} = props;

	const {
		getAnalyticsReportsHistoricalReadsURL,
		getAnalyticsReportsHistoricalViewsURL,
		getAnalyticsReportsTotalReadsURL,
		getAnalyticsReportsTotalViewsURL
	} = endpoints;

	const api = APIService({
		endpoints: {
			getAnalyticsReportsHistoricalReadsURL,
			getAnalyticsReportsHistoricalViewsURL,
			getAnalyticsReportsTotalReadsURL,
			getAnalyticsReportsTotalViewsURL
		},
		namespace,
		page
	});

	function _handleTotalReads() {
		return api.getTotalReads().then(response => {
			return numberFormat(
				languageTag,
				response.analyticsReportsTotalReads
			);
		});
	}
	function _handleTotalViews() {
		return api.getTotalViews().then(response => {
			return numberFormat(
				languageTag,
				response.analyticsReportsTotalViews
			);
		});
	}

	function _handleHistoricalViews() {
		return api.getHistoricalViews();
	}

	function _handleHistoricalReads() {
		return api.getHistoricalReads();
	}

	return (
		<div className="p-3">
			<BasicInformation
				authorName={authorName}
				publishDate={publishDate}
				title={title}
			/>

			<TotalCount
				className="mt-4"
				dataProvider={_handleTotalViews}
				label={Liferay.Util.sub(Liferay.Language.get('total-views'))}
				popoverHeader={Liferay.Language.get('total-views')}
				popoverMessage={Liferay.Language.get(
					'this-number-refers-to-the-total-number-of-views-since-the-content-was-published'
				)}
			/>
			<TotalCount
				className="mt-2"
				dataProvider={_handleTotalReads}
				label={Liferay.Util.sub(Liferay.Language.get('total-reads'))}
				popoverHeader={Liferay.Language.get('total-reads')}
				popoverMessage={Liferay.Language.get(
					'this-number-refers-to-the-total-number-of-reads-since-the-content-was-published'
				)}
			/>
			<hr />
			<Chart
				dataProviders={[_handleHistoricalViews, _handleHistoricalReads]}
				languageTag={languageTag}
			/>
		</div>
	);
}

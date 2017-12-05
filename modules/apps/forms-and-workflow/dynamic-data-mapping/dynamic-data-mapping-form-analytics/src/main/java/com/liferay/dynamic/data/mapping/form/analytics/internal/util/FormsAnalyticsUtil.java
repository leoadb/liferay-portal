package com.liferay.dynamic.data.mapping.form.analytics.internal.util;

import com.liferay.analytics.model.AnalyticsEventsMessage;
import com.liferay.analytics.model.AnalyticsEventsMessage.Builder;
import com.liferay.analytics.model.AnalyticsEventsMessage.Event;
import com.liferay.dynamic.data.mapping.form.analytics.internal.metrics.DDMFormAnalyticsEventEntry;
import com.liferay.portal.kernel.json.JSONObject;

import jodd.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class FormsAnalyticsUtil {

	public static void sendMessage(
			long userId, String eventId, String sessionId,
			DDMFormAnalyticsEventEntry ddmFormAnalyticsEventEntry)
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		JSONObject attributes = ddmFormAnalyticsEventEntry.getAttributes();

		attributes.keys().forEachRemaining(
			key -> properties.put(key, String.valueOf(attributes.get(key)))
		);

		properties.put("userId", String.valueOf(userId));
		properties.put(
			"date", ddmFormAnalyticsEventEntry.getTimestamp().toString());

		Map<String, String> context = new HashMap<>();

		context.put("sessionId", sessionId);

		sendMessage(String.valueOf(userId), eventId, context, properties);
	
	}

	private static void sendMessage(
			String userId, String eventId, Map<String, String> context,
			Map<String, String> properties)
		throws Exception {

		AnalyticsEventsMessage.Event.Builder eventBuilder =
			AnalyticsEventsMessage.Event.builder(_applicationId, eventId);

		eventBuilder.properties(properties);

		Event build = eventBuilder.build();

		Builder builder = AnalyticsEventsMessage.builder(
			_analyticsKey, userId).event(build).context(context);

		sendAnalytics(builder.build());
	}

	private static void sendAnalytics(AnalyticsEventsMessage analyticsEventsMessage)
		throws Exception {

		String map = _jsonObjectMapper.map(analyticsEventsMessage);

		HttpRequest.post("http://192.168.108.90:8081/").body(map).send();
	}

	private static final AnalyticsEventsMessageJSONObjectMapper
		_jsonObjectMapper = new AnalyticsEventsMessageJSONObjectMapper();

	private static final String _analyticsKey = "analyticskey:test";
	private static final String _applicationId =
		"com.liferay.dynamic.data.mapping.forms.analytics:1.0.0";
}

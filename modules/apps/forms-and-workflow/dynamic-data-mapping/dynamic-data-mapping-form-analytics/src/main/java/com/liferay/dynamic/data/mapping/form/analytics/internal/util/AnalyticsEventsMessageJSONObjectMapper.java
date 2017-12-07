package com.liferay.dynamic.data.mapping.form.analytics.internal.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.analytics.data.binding.JSONObjectMapper;
import com.liferay.analytics.model.AnalyticsEventsMessage;

import java.io.IOException;

import java.util.List;
import java.util.Map;

public class AnalyticsEventsMessageJSONObjectMapper
	implements JSONObjectMapper<AnalyticsEventsMessage> {

	@Override
	public String map(AnalyticsEventsMessage analyticsEventsMessage)
		throws IOException {

		return _objectMapper.writeValueAsString(analyticsEventsMessage);
	}

	@Override
	public AnalyticsEventsMessage map(String jsonString) throws IOException {
		return _objectMapper.readValue(
			jsonString, AnalyticsEventsMessage.class);
	}

	private final ObjectMapper _objectMapper = new ObjectMapper();

	{
		_objectMapper.addMixIn(
			AnalyticsEventsMessage.class, AnalyticsEventsMessageMixIn.class);

		_objectMapper.addMixIn(
			AnalyticsEventsMessage.Event.class, EventMixIn.class);

		_objectMapper.configure(
			DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	private static final class AnalyticsEventsMessageMixIn {

		@JsonProperty("analyticsKey")
		private String _analyticsKey;

		@JsonProperty("context")
		private Map<String, String> _context;

		@JsonProperty("events")
		private List<?> _events;

		@JsonProperty("protocolVersion")
		private String _protocolVersion;

		@JsonProperty("userId")
		private String _userId;

	}

	private static final class EventMixIn {

		@JsonProperty("applicationId")
		private String _applicationId;

		@JsonProperty("eventId")
		private String _eventId;

		@JsonProperty("properties")
		private Map<String, String> _properties;

	}

}
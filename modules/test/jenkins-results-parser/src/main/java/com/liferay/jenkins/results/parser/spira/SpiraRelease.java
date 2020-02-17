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

package com.liferay.jenkins.results.parser.spira;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil.HttpRequestMethod;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraRelease extends IndentLevelSpiraArtifact {

	public static SpiraRelease createSpiraRelease(
			SpiraProject spiraProject, String releaseName)
		throws IOException {

		return createSpiraRelease(spiraProject, releaseName, null);
	}

	public static SpiraRelease createSpiraRelease(
			SpiraProject spiraProject, String releaseName,
			Integer parentReleaseID)
		throws IOException {

		String urlPath = "projects/{project_id}/releases{parent_release_id}";

		Map<String, String> urlPathReplacements = new HashMap<>();

		if ((parentReleaseID == null) || (parentReleaseID == 0)) {
			urlPathReplacements.put("parent_release_id", "");
		}
		else {
			urlPathReplacements.put("parent_release_id", "/" + parentReleaseID);
		}

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put(
			"Name", StringEscapeUtils.unescapeJava(releaseName));
		requestJSONObject.put("ReleaseStatusId", STATUS_PLANNED);
		requestJSONObject.put("ReleaseTypeId", TYPE_MAJOR_RELEASE);

		Calendar calendar = Calendar.getInstance();

		requestJSONObject.put("StartDate", toDateString(calendar));

		calendar.add(Calendar.MONTH, 1);

		requestJSONObject.put("EndDate", toDateString(calendar));

		JSONObject responseJSONObject = SpiraRestAPIUtil.requestJSONObject(
			urlPath, null, urlPathReplacements, HttpRequestMethod.POST,
			requestJSONObject.toString());

		return spiraProject.getSpiraReleaseByID(
			responseJSONObject.getInt("ReleaseId"));
	}

	public static SpiraRelease createSpiraReleaseByPath(
			SpiraProject spiraProject, String releasePath)
		throws IOException {

		List<SpiraRelease> spiraReleases = spiraProject.getSpiraReleasesByPath(
			releasePath);

		if (!spiraReleases.isEmpty()) {
			return spiraReleases.get(0);
		}

		String releaseName = getPathName(releasePath);
		String parentReleasePath = getParentPath(releasePath);

		if (parentReleasePath.isEmpty()) {
			return createSpiraRelease(spiraProject, releaseName);
		}

		SpiraRelease parentSpiraRelease = createSpiraReleaseByPath(
			spiraProject, parentReleasePath);

		return createSpiraRelease(
			spiraProject, releaseName, parentSpiraRelease.getID());
	}

	public static void deleteSpiraReleaseByID(
			SpiraProject spiraProject, int releaseID)
		throws IOException {

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));
		urlPathReplacements.put("release_id", String.valueOf(releaseID));

		SpiraRestAPIUtil.request(
			"projects/{project_id}/releases/{release_id}", null,
			urlPathReplacements, HttpRequestMethod.DELETE, null);

		_spiraReleases.remove(
			_createSpiraReleaseKey(spiraProject.getID(), releaseID));
	}

	public static void deleteSpiraReleasesByPath(
			SpiraProject spiraProject, String releasePath)
		throws IOException {

		List<SpiraRelease> spiraReleases = spiraProject.getSpiraReleasesByPath(
			releasePath);

		for (SpiraRelease spiraRelease : spiraReleases) {
			deleteSpiraReleaseByID(spiraProject, spiraRelease.getID());
		}
	}

	@Override
	public int getID() {
		return jsonObject.getInt("ReleaseId");
	}

	public SpiraRelease getParentSpiraRelease() {
		PathSpiraArtifact parentSpiraArtifact = getParentSpiraArtifact();

		if (parentSpiraArtifact == null) {
			return null;
		}

		if (!(parentSpiraArtifact instanceof SpiraRelease)) {
			throw new RuntimeException(
				"Invalid parent object " + parentSpiraArtifact);
		}

		return (SpiraRelease)parentSpiraArtifact;
	}

	public SpiraReleaseBuild getSpiraReleaseBuildByID(int releaseBuildID)
		throws IOException {

		List<SpiraReleaseBuild> spiraReleaseBuilds =
			SpiraReleaseBuild.getSpiraReleaseBuilds(
				getSpiraProject(), this,
				new SearchParameter("BuildId", releaseBuildID));

		if (spiraReleaseBuilds.size() > 1) {
			throw new RuntimeException(
				"Duplicate release build id " + releaseBuildID);
		}

		if (spiraReleaseBuilds.isEmpty()) {
			throw new RuntimeException(
				"Missing release build id " + releaseBuildID);
		}

		return spiraReleaseBuilds.get(0);
	}

	protected static List<SpiraRelease> getSpiraReleases(
			SpiraProject spiraProject, SearchParameter... searchParameters)
		throws IOException {

		List<SpiraRelease> spiraReleases = new ArrayList<>();

		for (SpiraRelease spiraRelease : _spiraReleases.values()) {
			if (spiraRelease.matches(searchParameters)) {
				spiraReleases.add(spiraRelease);
			}
		}

		if (!spiraReleases.isEmpty()) {
			return spiraReleases;
		}

		Map<String, String> urlParameters = new HashMap<>();

		urlParameters.put("number_rows", String.valueOf(15000));
		urlParameters.put("start_row", String.valueOf(1));

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		JSONArray requestJSONArray = new JSONArray();

		for (SearchParameter searchParameter : searchParameters) {
			requestJSONArray.put(searchParameter.toFilterJSONObject());
		}

		JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
			"projects/{project_id}/releases/search", urlParameters,
			urlPathReplacements, HttpRequestMethod.POST,
			requestJSONArray.toString());

		for (int i = 0; i < responseJSONArray.length(); i++) {
			SpiraRelease spiraRelease = new SpiraRelease(
				responseJSONArray.getJSONObject(i));

			_spiraReleases.put(
				_createSpiraReleaseKey(
					spiraProject.getID(), spiraRelease.getID()),
				spiraRelease);

			if (spiraRelease.matches(searchParameters)) {
				spiraReleases.add(spiraRelease);
			}
		}

		return spiraReleases;
	}

	@Override
	protected PathSpiraArtifact getSpiraArtifactByIndentLevel(
		String indentLevel) {

		SpiraProject spiraProject = getSpiraProject();

		try {
			return spiraProject.getSpiraReleaseByIndentLevel(indentLevel);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	protected static final int STATUS_CANCELED = 5;

	protected static final int STATUS_CLOSED = 3;

	protected static final int STATUS_DEFERRED = 4;

	protected static final int STATUS_IN_PROGRESS = 2;

	protected static final int STATUS_PLANNED = 1;

	protected static final int TYPE_MAJOR_RELEASE = 1;

	protected static final int TYPE_MINOR_RELEASE = 2;

	protected static final int TYPE_PHASE = 4;

	protected static final int TYPE_SPRINT = 3;

	private static String _createSpiraReleaseKey(int projectID, int releaseID) {
		return projectID + "-" + releaseID;
	}

	private SpiraRelease(JSONObject jsonObject) {
		super(jsonObject);
	}

	private static final Map<String, SpiraRelease> _spiraReleases =
		new HashMap<>();

}
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

package com.liferay.structured.content.apio.internal.architect.form;

import com.liferay.apio.architect.form.Form;
import com.liferay.category.apio.architect.identifier.CategoryIdentifier;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.structure.apio.architect.identifier.ContentStructureIdentifier;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * Instances of this class represent the values extracted from a structured
 * content form.
 *
 * @author Alejandro Hernández
 * @review
 */
public class StructuredContentCreatorForm {

	/**
	 * Builds a {@code Form} that generates {@code StructuredContentCreatorForm}
	 * depending on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return a structured content creator form
	 * @review
	 */
	public static Form<StructuredContentCreatorForm> buildForm(
		Form.Builder<StructuredContentCreatorForm> formBuilder) {

		return formBuilder.title(
			__ -> "The structured content creator form"
		).description(
			__ -> "This form can be used to create a structured content"
		).constructor(
			StructuredContentCreatorForm::new
		).addOptionalDate(
			"datePublished", StructuredContentCreatorForm::setPublishedDate
		).addOptionalLinkedModelList(
			"category", CategoryIdentifier.class,
			StructuredContentCreatorForm::setCategories
		).addOptionalNestedModelList(
			"values", StructuredContentValuesForm::buildForm,
			StructuredContentCreatorForm::setStructuredContentValuesForms
		).addOptionalString(
			"description", StructuredContentCreatorForm::setDescription
		).addOptionalStringList(
			"keywords", StructuredContentCreatorForm::setKeywords
		).addRequiredLinkedModel(
			"contentStructure", ContentStructureIdentifier.class,
			StructuredContentCreatorForm::setContentStructureId
		).addRequiredString(
			"title", StructuredContentCreatorForm::setTitle
		).build();
	}

	public List<Long> getCategories() {
		return _categories;
	}

	/**
	 * Returns the structured content's structure ID.
	 *
	 * @return the structured content's structure ID
	 * @review
	 */
	public Long getContentStructureId() {
		return _contentStructureId;
	}

	/**
	 * Returns the structured content's description map.
	 *
	 * @return the structured content's description map
	 * @review
	 */
	public Map<Locale, String> getDescriptionMap(Locale locale) {
		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(locale, _description);

		return descriptionMap;
	}

	/**
	 * Returns the structured content's published date day.
	 *
	 * @return the structured content's published date day
	 * @review
	 */
	public Optional<Integer> getPublishedDateDayOptional() {
		return Optional.ofNullable(_publishedDateDay);
	}

	/**
	 * Returns the structured content's published date hour.
	 *
	 * @return the structured content's published date hour
	 * @review
	 */
	public Optional<Integer> getPublishedDateHourOptional() {
		return Optional.ofNullable(_publishedDateHour);
	}

	/**
	 * Returns the structured content's published date minute.
	 *
	 * @return the structured content's published date minute
	 * @review
	 */
	public Optional<Integer> getPublishedDateMinuteOptional() {
		return Optional.ofNullable(_publishedDateMinute);
	}

	/**
	 * Returns the structured content's published date month.
	 *
	 * @return the structured content's published date month
	 * @review
	 */
	public Optional<Integer> getPublishedDateMonthOptional() {
		return Optional.ofNullable(_publishedDateMonth);
	}

	/**
	 * Returns the structured content's published date year.
	 *
	 * @return the structured content's published date year
	 * @review
	 */
	public Optional<Integer> getPublishedDateYearOptional() {
		return Optional.ofNullable(_publishedDateYear);
	}

	/**
	 * Returns the service context related with this form
	 *
	 * @param  groupId the group ID
	 * @return the service context
	 * @review
	 */
	public ServiceContext getServiceContext(long groupId) {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		if (ListUtil.isNotEmpty(_keywords)) {
			serviceContext.setAssetTagNames(ArrayUtil.toStringArray(_keywords));
		}

		if (ListUtil.isNotEmpty(_categories)) {
			serviceContext.setAssetCategoryIds(
				ArrayUtil.toLongArray(_categories));
		}

		serviceContext.setScopeGroupId(groupId);

		return serviceContext;
	}

	public List<StructuredContentValuesForm> getStructuredContentValuesForms() {
		return _structuredContentValuesForms;
	}

	/**
	 * Returns the structured content's title map.
	 *
	 * @return the structured content's title map
	 * @review
	 */
	public Map<Locale, String> getTitleMap(Locale locale) {
		Map<Locale, String> titleMap = new HashMap<>();

		titleMap.put(locale, _title);

		return titleMap;
	}

	public void setCategories(List<Long> categories) {
		_categories = categories;
	}

	public void setContentStructureId(Long contentStructureId) {
		_contentStructureId = contentStructureId;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setKeywords(List<String> keywords) {
		_keywords = keywords;
	}

	public void setPublishedDate(Date publishedDate) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(publishedDate);

		_publishedDateDay = calendar.get(Calendar.DATE);
		_publishedDateHour = calendar.get(Calendar.HOUR);
		_publishedDateMinute = calendar.get(Calendar.MINUTE);
		_publishedDateMonth = calendar.get(Calendar.MONTH);
		_publishedDateYear = calendar.get(Calendar.YEAR);
	}

	public void setStructuredContentValuesForms(
		List<StructuredContentValuesForm> structuredContentValuesForms) {

		_structuredContentValuesForms = structuredContentValuesForms;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private List<Long> _categories;
	private Long _contentStructureId;
	private String _description;
	private List<String> _keywords;
	private Integer _publishedDateDay;
	private Integer _publishedDateHour;
	private Integer _publishedDateMinute;
	private Integer _publishedDateMonth;
	private Integer _publishedDateYear;
	private List<StructuredContentValuesForm> _structuredContentValuesForms =
		new ArrayList<>();
	private String _title;

}
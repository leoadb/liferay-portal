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

package com.liferay.dynamic.data.mapping.validator.exception;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class DDMFormValidationException extends PortalException {

	public static class AvailableLocalesNotSet
		extends DDMFormValidationException {

		public AvailableLocalesNotSet() {
			super("The available locales property was never set for DDM form");
		}

	}

	public static class DefaultLocaleNotSet extends DDMFormValidationException {

		public DefaultLocaleNotSet() {
			super("The default locale property was never set for DDM form");
		}

	}

	public static class DefaultLocaleNotSetAsAvailableLocale
		extends DDMFormValidationException {

		public DefaultLocaleNotSetAsAvailableLocale(Locale defaultLocale) {
			super(
				String.format(
					"The default locale %s should be set as a valid available" +
						" locale",
					defaultLocale));
		}

	}

	public static class DuplicateFieldName extends DDMFormValidationException {

		public DuplicateFieldName(String fieldName) {
			super(
				String.format(
					"The field name %s was defined more than once", fieldName));
		}

	}

	public static class EmptyOptionSet extends DDMFormValidationException {

		public EmptyOptionSet(String fieldName) {
			super(
				String.format(
					"At least one option should be set for field %s",
						fieldName));
		}

	}

	public static class FieldTypeNotSet extends DDMFormValidationException {

		public FieldTypeNotSet() {
			super("The field type was never set for DDM form field");
		}

	}

	public static class InvalidAvailableLocalesSetForProperty
		extends DDMFormValidationException {

		public InvalidAvailableLocalesSetForProperty(
			String fieldName, String property) {

			super(
				String.format(
					"Invalid available locales set for property '%s' of " +
						"field name %s",
					property, fieldName));
		}

	}

	public static class InvalidCharactersSetForFieldName
		extends DDMFormValidationException {

		public InvalidCharactersSetForFieldName(String fieldName) {
			super(
				String.format(
					"Invalid characters were defined for field name %s",
						fieldName));
		}

	}

	public static class InvalidCharactersSetForFieldType
		extends DDMFormValidationException {

		public InvalidCharactersSetForFieldType(String fieldType) {
			super(
				String.format(
					"Invalid characters were defined for field type %s",
						fieldType));
		}

	}

	public static class InvalidDefaultLocaleSetForProperty
		extends DDMFormValidationException {

		public InvalidDefaultLocaleSetForProperty(
			String fieldName, String property) {

			super(
				String.format(
					"Invalid default locale set for property '%s' of " +
						"field name %s",
					property, fieldName));
		}

	}

	public static class InvalidIndexTypeSet extends DDMFormValidationException {

		public InvalidIndexTypeSet(String fieldName) {
			super(
				String.format(
					"Invalid index type set for field %s", fieldName));
		}

	}

	private DDMFormValidationException(String message) {
		super(message);
	}

}
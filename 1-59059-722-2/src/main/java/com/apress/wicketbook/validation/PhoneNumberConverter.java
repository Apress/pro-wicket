package com.apress.wicketbook.validation;

import java.util.Locale;
import java.util.regex.Pattern;

import wicket.util.convert.ITypeConverter;
import wicket.util.convert.converters.AbstractConverter;

import com.apress.wicketbook.common.PhoneNumber;

public class PhoneNumberConverter extends AbstractConverter {
	Pattern pattern = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");

	/**
	 * The singleton instance for a phone number converter
	 */
	public static final ITypeConverter INSTANCE = new PhoneNumberConverter();

	@Override
	protected Class getTargetType() {
		return PhoneNumber.class;
	}

	public Object convert(Object value, Locale locale) {
		
		if (value == null) {
			return null;
		}
		
		if (value.getClass() == getTargetType()) {
			return value;
		}
		// Before converting the value, make sure that it matches the pattern.
		// If it doesn't, Wicket expects you to throw the built-in
		// runtime exception - ConversionException.
		if (!pattern.matcher((String) value).matches()) {
			throw newConversionException("Supplied value " + value
					+ " does not match the pattern " + pattern.toString(),
					value, locale);
		}
		String numericString = stripExtraChars((String) value);
		String areaCode = numericString.substring(0, 3);
		String prefix = numericString.substring(3, 6);
		String number = numericString.substring(6);
		PhoneNumber phoneNumber = new PhoneNumber(areaCode, prefix, number);
		return phoneNumber;
	}

	private String stripExtraChars(String input) {
		return input.replaceAll("[^0-9]", "");
	}
}

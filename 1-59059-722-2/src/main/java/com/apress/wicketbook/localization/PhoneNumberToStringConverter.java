package com.apress.wicketbook.localization;

import java.util.Locale;

import wicket.util.convert.ITypeConverter;
import wicket.util.convert.converters.AbstractConverter;


public class PhoneNumberToStringConverter extends AbstractConverter {
	public static ITypeConverter INSTANCE = new PhoneNumberToStringConverter();

	@Override
	protected Class getTargetType() {
		return String.class;
	}

	public Object convert(Object value, Locale locale) {
		if (value == null)
			return null;
		PhoneNumber phoneNumber = (PhoneNumber) value;
		return phoneNumber.getAreaCode() + "-" + phoneNumber.getPrefix() + "-"
		 + phoneNumber.getNumber();
	}
}

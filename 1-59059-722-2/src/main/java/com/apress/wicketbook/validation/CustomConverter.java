package com.apress.wicketbook.validation;

import java.util.Locale;

import wicket.util.convert.Converter;
import wicket.util.convert.converters.StringConverter;

import com.apress.wicketbook.common.PhoneNumber;

public class CustomConverter extends Converter {
	public CustomConverter(Locale locale) {
		super();
		setLocale(locale);
		// Register the custom ITypeConverter. Now it will be globally
		// available.
		set(PhoneNumber.class, PhoneNumberConverter.INSTANCE);
		// Get the converter Wicket uses to convert model objects to String.
		StringConverter sConverter = (StringConverter)get(String.class);
		//Register the custom ITypeConverter to convert PhoneNumber to its String
		// representation.
		sConverter.set(PhoneNumber.class, PhoneNumberToStringConverter.INSTANCE);
	}
}	

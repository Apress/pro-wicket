package com.apress.wicketbook.validation;

import java.util.Locale;

import wicket.protocol.http.WebApplication;
import wicket.util.convert.IConverter;
import wicket.util.convert.IConverterFactory;

public class ValidationApplication extends WebApplication {
	public ValidationApplication() {
	}

	public void init() {
		super.init();
		getExceptionSettings().setThrowExceptionOnMissingResource(false);
		getApplicationSettings().setConverterFactory(new IConverterFactory() {
			public IConverter newConverter(final Locale locale) {
				return new CustomConverter(locale);
			}
		});
	}

	public Class getHomePage() {
		return UserProfilePage.class;
	}
}
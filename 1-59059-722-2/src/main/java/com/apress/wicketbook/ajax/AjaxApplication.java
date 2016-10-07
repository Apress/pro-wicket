package com.apress.wicketbook.ajax;

import java.util.Locale;

import wicket.protocol.http.WebApplication;
import wicket.util.convert.IConverter;
import wicket.util.convert.IConverterFactory;

import com.apress.wicketbook.validation.CustomConverter;

public class AjaxApplication extends WebApplication {
	public AjaxApplication() {
	}

	public void init() {
		super.init();
		getExceptionSettings().setThrowExceptionOnMissingResource(false);
		getApplicationSettings().setConverterFactory(new IConverterFactory() {
			public IConverter newConverter(final Locale locale) {
				return new CustomConverter(locale);
			}
		});
		//getAjaxSettings().setAjaxDebugModeEnabled(false);
	}

	public Class getHomePage() {
		return UserProfilePage.class;
		//return AutoCompleteEmail.class;
		//return TestAjaxFallbackDropDown.class;
	}
}
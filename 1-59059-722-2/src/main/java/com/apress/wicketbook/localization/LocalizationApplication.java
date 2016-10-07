package com.apress.wicketbook.localization;

import java.util.Locale;
import wicket.protocol.http.WebApplication;
import wicket.util.convert.IConverter;
import wicket.util.convert.IConverterFactory;

public class LocalizationApplication extends WebApplication {
	// ..
	public void init() {
		super.init();
		// ..
		// Strip Wicket tags when rendering. This will ensure that the page
		// title
		// renders correctly.
		//getMarkupSettings().setStripWicketTags(true);
		
		getApplicationSettings().setConverterFactory(new IConverterFactory() {
			public IConverter newConverter(final Locale locale) {
				return new CustomConverter(locale);
			}
		});
	}
	// ..

	@Override
	public Class getHomePage() {
		return UserProfilePage.class;		
	}
}
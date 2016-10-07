package com.apress.wicketbook.localization;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import wicket.markup.html.form.validation.IValidator;
import wicket.markup.html.form.validation.PatternValidator;

public class CurrencyValidator implements Serializable {
	/* Maintain a mapping of locale to currency pattern. */
	private static final Map localeCurrencyPatternMap = new HashMap();
	static {
		localeCurrencyPatternMap.put(Locale.US, Pattern
				.compile("^\\d{1,3}(,?\\d{3})*\\.?(\\d{1,2})?"));
		localeCurrencyPatternMap.put(new Locale("es"), Pattern
				.compile("^\\d{1,3}(\\.?\\d{3})*,?(\\d{1,2})?"));
	}

	/*
	 * When requested for a CurrencyValidator, return a PatternValidator for the
	 * locale-specific pattern.
	 */
	public static IValidator forLocale(Locale locale) {
		Pattern pattern = (Pattern) localeCurrencyPatternMap.get(locale);
		return new PatternValidator(pattern);
	}
}

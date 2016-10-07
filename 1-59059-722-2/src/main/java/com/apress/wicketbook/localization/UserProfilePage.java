package com.apress.wicketbook.localization;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import wicket.markup.html.WebPage;
import wicket.markup.html.form.Button;
import wicket.markup.html.form.ChoiceRenderer;
import wicket.markup.html.form.DropDownChoice;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.RadioChoice;
import wicket.markup.html.form.TextField;
import wicket.markup.html.form.validation.NumberValidator;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.CompoundPropertyModel;
import wicket.model.IModel;
import wicket.model.Model;
import wicket.model.PropertyModel;
import wicket.model.ResourceModel;
import wicket.model.StringResourceModel;
import wicket.util.convert.ConversionException;
import wicket.util.convert.IConverter;

public class UserProfilePage extends WebPage {

	/** Relevant locales wrapped in a list. */
	private static final List LOCALES = Arrays.asList(new Locale[] {
			Locale.ENGLISH, new Locale("es") });
	
	private static final List COUNTRIES = Arrays.asList(new Locale[] {
			Locale.US, new Locale("es", "ES"), Locale.UK,
			new Locale("hi", "IN") });

	/* Set the locale on the session based on user selection. */
	public void setLocale(Locale locale) {
		if (locale != null) {
			getSession().setLocale(locale);
		}
	}

	public UserProfilePage() {
		UserProfile userProfile = new UserProfile();
		CompoundPropertyModel userProfileModel = new CompoundPropertyModel(
				userProfile);
		Form form = new UserProfileForm("userProfile", userProfileModel);

		TextField userNameComp = new TextField("name");
		TextField addressComp = new TextField("address");
		TextField cityComp = new TextField("city");
		/*
		 * Corresponding to HTML Select, we have a DropDownChoice component in
		 * Wicket. The constructor passes in the component ID "country" (that
		 * maps to wicket:id in the HTML template) as usual and along with it a
		 * list for the DropDownChoice component to render
		 */
		DropDownChoice countriesComp = new DropDownChoice("country", COUNTRIES,new CountryRenderer());
		TextField pinComp = new TextField("pin");
		form.add(userNameComp);
		form.add(addressComp);
		form.add(cityComp);
		form.add(countriesComp);
		form.add(pinComp);

		userNameComp.setRequired(true);
		pinComp.setRequired(true);
		pinComp.add(NumberValidator.range(0, 5000));
		pinComp.setType(int.class);

		TextField phoneComp = new TextField("phoneNumber", PhoneNumber.class);
		
		TextField txtAmount = new TextField("salary");
//		 Associate the CurrencyValidator with the locale.
		txtAmount.add(CurrencyValidator.forLocale(getLocale()));
		//form.add(txtAmount);
		form.add(phoneComp);
		add(form);
		add(new FeedbackPanel("feedback"));
		// ..in addition to already existing code.
		form.add(new LocaleRadioChoice("locale", new PropertyModel(this,
				"locale"), LOCALES).setSuffix(""));
		form.add(new Button("save",new ResourceModel("userProfile.save")));
	}

	class LocaleRadioChoice extends RadioChoice {
		public LocaleRadioChoice(String id, IModel model, List choices) {
			super(id, model, choices, new LocaleChoiceRenderer());
		}

		protected boolean wantOnSelectionChangedNotifications() {
			return true;
		}
	}

	private final class LocaleChoiceRenderer extends ChoiceRenderer {
		public LocaleChoiceRenderer() {
		}

		/**
		 * @see wicket.markup.html.form.IChoiceRenderer#getDisplayValue(Object)
		 */
		public Object getDisplayValue(Object object) {
			Locale locale = (Locale) object;
			String display = locale.getDisplayName(getLocale());
			return display;
		}
	}

	class UserProfileForm extends Form {
		// PropertyModel is an IModel implementation
		public UserProfileForm(String id, IModel model) {
			super(id, model);
		}

		@Override
		public void onSubmit() {
			
			Locale currLocale = getLocale();
			UserProfile up = (UserProfile) getModelObject();
//			 Get the country representation in accordance with
//			 the current locale.
			String displayCountry =
			up.getCountry()==null?"":up.getCountry().getDisplayCountry(
			currLocale);
			String infoMessage = new StringResourceModel("user.message", this,
			new Model(up), new Object[] {displayCountry}).getString();
			info(infoMessage);			
		}
	}

	public static class PhoneNumberConverter implements IConverter {
		private Locale locale;

		final Pattern pattern = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");

		// This is the method that the framework calls
		public Object convert(Object value, Class c) {

			if (value == null) {
				return null;
			}
			// If the value is already of the same type 'c'
			// then return it without any modifications.

			if (value.getClass() == c) {
				return value;
			}

			// If the target type for conversion is String,
			// convert the PhoneNumber to the form xxx-xxx-xxxx
			if (c == String.class) {
				PhoneNumber phoneNumber = (PhoneNumber) value;
				return phoneNumber.getAreaCode() + "-"
						+ phoneNumber.getPrefix() + "-"
						+ phoneNumber.getNumber();
			}

			if (!pattern.matcher((String) value).matches()) {
				// If the pattern does not match, throw ConversionException
				throw new ConversionException("Supplied value " + value
						+ " does not match the pattern " + pattern.toString());
			}
			// Assume for now that the input is of the form xxx-xxx-xxxx
			String numericString = stripExtraChars((String) value);
			String areaCode = numericString.substring(0, 3);
			String prefix = numericString.substring(3, 6);
			String number = numericString.substring(6);
			PhoneNumber phoneNumber = new PhoneNumber(areaCode, prefix, number);
			return phoneNumber;
		}

		// Removes all nonnumeric characters from the input.
		// If supplied with 123-456-7890, it returns 1234567890.
		private String stripExtraChars(String input) {
			return input.replaceAll("[^0-9]", "");
		}

		// Currently you are not doing locale-specific parsing
		public void setLocale(Locale locale) {
			this.locale = locale;
		}

		public Locale getLocale() {
			return this.locale;
		}
	}

	private final class CountryRenderer extends ChoiceRenderer {
		/**
		 * Constructor.
		 */
		public CountryRenderer() {
		}

		/**
		 * @see wicket.markup.html.form.IChoiceRenderer#getDisplayValue(Object)
		 */
		public Object getDisplayValue(Object object) {
			Locale locale = (Locale) object;
			String display = locale.getDisplayCountry(getLocale());
			return display;
		}
	}

}
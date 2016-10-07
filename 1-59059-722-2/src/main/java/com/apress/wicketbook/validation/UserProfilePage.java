package com.apress.wicketbook.validation;

import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Pattern;

import wicket.Component;
import wicket.feedback.ErrorLevelFeedbackMessageFilter;
import wicket.feedback.FeedbackMessage;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.DropDownChoice;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextField;
import wicket.markup.html.form.validation.NumberValidator;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.model.AbstractReadOnlyModel;
import wicket.model.CompoundPropertyModel;
import wicket.model.IModel;
import wicket.model.PropertyModel;
import wicket.util.convert.ConversionException;
import wicket.util.convert.IConverter;

import com.apress.wicketbook.common.PhoneNumber;
import com.apress.wicketbook.common.UserProfile;

public class UserProfilePage extends WebPage {
	public UserProfilePage() {
		UserProfile userProfile = new UserProfile();
		CompoundPropertyModel userProfileModel = new CompoundPropertyModel(
				userProfile);
		Form form = new UserProfileForm("userProfile", userProfileModel);
		add(form);
		TextField userNameComp = new TextField("name");
		TextField addressComp = new TextField("address");
		TextField cityComp = new TextField("city");
		/*
		 * Corresponding to HTML Select, we have a DropDownChoice component in
		 * Wicket. The constructor passes in the component ID "country" (that
		 * maps to wicket:id in the HTML template) as usual and along with it a
		 * list for the DropDownChoice component to render
		 */
		DropDownChoice countriesComp = new DropDownChoice("country", Arrays
				.asList(new String[] { "India", "US", "UK" }));
		TextField pinComp = new TextField("pin");
		form.add(userNameComp);
		form.add(addressComp);
		form.add(cityComp);
		form.add(countriesComp);
		form.add(pinComp);
		
		userNameComp.setRequired(true);
		pinComp.setRequired(true);
		pinComp.add(NumberValidator.range(1000,5000));
		pinComp.setType(int.class);

		TextField phoneComp = new TextField("phoneNumber", PhoneNumber.class)
		/*
		 * { public IConverter getConverter() { return new
		 * PhoneNumberConverter(); } }
		 */
		;
		form.add(phoneComp);

		IModel messagesModel = new AbstractReadOnlyModel() {
			// Wicket calls this method to get the actual "model object"
			// at runtime
			public Object getObject(Component component) {
				return component.getPage().getFeedbackMessages().messages(
						new ErrorLevelFeedbackMessageFilter(FeedbackMessage.ERROR));				
			}
		};

		ListView feedback = new ListView("feedback", messagesModel) {
			public void populateItem(ListItem listItem) {
				// Access the item from the the FeedbackMessages list that
				// you supplied earlier.
				FeedbackMessage message = (FeedbackMessage) listItem
						.getModelObject();
				listItem.add(new Label("message", new PropertyModel(message,
						"message")));
				listItem.add(new Label("level", new PropertyModel(message,
						"level")));
			}
		};
		add(feedback);
	}

	class UserProfileForm extends Form {
		// PropertyModel is an IModel implementation
		public UserProfileForm(String id, IModel model) {
			super(id, model);
		}

		@Override
		public void onSubmit() {
			/* Print the contents of its own model object */
			info(getModelObjectAsString());
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

}
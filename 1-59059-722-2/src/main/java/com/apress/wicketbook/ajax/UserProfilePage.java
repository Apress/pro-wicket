package com.apress.wicketbook.ajax;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import wicket.Component;
import wicket.ajax.AbstractDefaultAjaxBehavior;
import wicket.ajax.AjaxRequestTarget;
import wicket.ajax.IAjaxIndicatorAware;
import wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import wicket.ajax.form.AjaxFormValidatingBehavior;
import wicket.markup.ComponentTag;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.WebPage;
import wicket.markup.html.border.Border;
import wicket.markup.html.form.DropDownChoice;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.FormComponent;
import wicket.markup.html.form.TextField;
import wicket.markup.html.form.validation.NumberValidator;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.AbstractReadOnlyModel;
import wicket.model.CompoundPropertyModel;
import wicket.model.IModel;

import com.apress.wicketbook.common.PhoneNumber;
import com.apress.wicketbook.common.UserProfile;

public class UserProfilePage extends WebPage {

	private Map countryToStateMap = new HashMap();

	// Maintain a map of the country to its states/counties.
	private void initStates() {
		countryToStateMap.put("India", new String[] { "Maharashtra",
				"Tamilnadu", "Sikkim", "Kashmir", "Karnataka" });
		countryToStateMap.put("US", new String[] { "California", "Texas",
				"Washington", "Michigan" });
		countryToStateMap.put("UK", new String[] { "Lancashire", "Middlesex",
				"Yorkshire", "Sussex" });
	}

	public UserProfilePage() {
		final UserProfile userProfile = new UserProfile();
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
				.asList(new String[] { "India", "US", "UK" })) {
			protected boolean wantOnSelectionChangedNotifications() {
				// Returning true from here would result in a page submit when
				// selection changes in the drop-down. You instead want to
				// respond
				// to an Ajax call. So return false.
				return false;
			}
		};

		TextField pinComp = new TextField("pin");

		userNameComp.setRequired(true);
		pinComp.setRequired(true);
		pinComp.add(NumberValidator.range(1000, 5000));
		pinComp.setType(int.class);

		TextField phoneComp = new TextField("phoneNumber", PhoneNumber.class);

		form.add(addressComp);
		form.add(cityComp);
		form.add(countriesComp);

		final Border nameBorder = addWithBorder(form, userNameComp,
				"nameBorder");
		final Border pinBorder = addWithBorder(form, pinComp, "pinBorder");
		final Border phoneBorder = addWithBorder(form, phoneComp, "phoneBorder");

		FeedbackPanel feedback = new FeedbackPanel("feedback");
		add(feedback);
		/*
		 * feedback.setOutputMarkupId(true);
		 * addAjaxBehaviorToComponent(userNameComp,nameBorder,feedback);
		 * addAjaxBehaviorToComponent(pinComp,pinBorder,feedback);
		 * addAjaxBehaviorToComponent(phoneComp,phoneBorder,feedback);
		 */
		AjaxFormValidatingBehavior.addToAllFormComponents(form, "onchange",
				wicket.util.time.Duration.seconds(7));

		// Initialize the country-state mapping -->
		initStates();
		// ..
		// The state choices that need to be displayed are determined by the
		// chosen country.
		// Remember, IModel serves as an ideal data locator in this case.
		// Only at runtime do you discover the
		// states that need to be displayed in response.
		IModel statechoices = new AbstractReadOnlyModel() {
			// Wicket calls this method when it tries to look
			// for the model object for displaying the states.
			// This is the indirection introduced by the Wicket's models.
			public Object getObject(Component component) {
				String[] models = (String[]) countryToStateMap.get(userProfile
						.getCountry());
				if (models == null) {
					return Collections.EMPTY_LIST;
				}
				return Arrays.asList(models);
			}
		};
		final DropDownChoice stateComp = new DropDownChoice("state",
				statechoices);
		stateComp.setOutputMarkupId(true);
		
		// Add an Ajax component updating behavior to the country drop-down.
		// Pass in the JavaScript event ("onchange") that needs to trigger this
		// request.
		/*
		countriesComp.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			protected void onUpdate(AjaxRequestTarget target) {
				// Supply the component that you want to re-render.
				target.addComponent(stateComp);
			}
		});
		*/
		// ..

		/*
		final WebMarkupContainer imgContainer = new WebMarkupContainer(
				"indicatorImg") {
			public void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				// AbstractDefaultAjaxBehavior.INDICATOR is a package reference
				// to the
				// default indicator.gif (refer to Chapter 7 for discussion on
				// package references.
				tag.put("src", urlFor(AbstractDefaultAjaxBehavior.INDICATOR));
			}
		};
		*/
		final AjaxIndicator imgContainer =
			new AjaxIndicator("indicatorImg");
		imgContainer.setOutputMarkupId(true);
		form.add(imgContainer);
		form.add(stateComp);

		class CountryDDAjaxBehavior extends AjaxFormComponentUpdatingBehavior
				implements IAjaxIndicatorAware {
			CountryDDAjaxBehavior() {
				super("onchange");
			}

			protected void onUpdate(AjaxRequestTarget target) {
				// Sleep for 5 seconds just to make sure that
				// the busy indicator works as it is supposed to.
				// You would obviously not want this (Thread.sleep)
				// piece of code in production.
				try {
					Thread.sleep(5000);
				} catch (InterruptedException ignore) {
				}
				// Add the Ajax target component as earlier.
				target.addComponent(stateComp);
			}

			/** Return indicator's markup ID* */
			public String getAjaxIndicatorMarkupId() {
				return imgContainer.getMarkupId();
			}
		}
		
		countriesComp.add(new CountryDDAjaxBehavior());
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

	// A helper to add Border to the FormComponent
	Border addWithBorder(Form form, FormComponent component, String borderId) {
		// FormComponentFeedbackBorder border = new FormComponentFeedbackBorder(
		// borderId);
		AjaxResponsiveFormComponentFeedbackBorder border = new AjaxResponsiveFormComponentFeedbackBorder(
				borderId);
		// Get the border components to emit value for id.
		border.setOutputMarkupId(true);
		border.add(component);
		form.add(border);
		return border;
	}

	// Component whose input needs to be validated should be
	// configured with AjaxFormComponentUpdatingBehavior.
	void addAjaxBehaviorToComponent(FormComponent formComponent,
			final Border border, final FeedbackPanel feedback) {
		formComponent.add(new AjaxFormComponentUpdatingBehavior("onblur") {
			// You want the FeedbackPanel and the FormComponentFeedbackBorder
			// to update themselves based on the Ajax validation result.
			// Accordingly, you need to add them to the AjaxRequestTarget.
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.addComponent(feedback);
				target.addComponent(border);
			}
		});
	}

}
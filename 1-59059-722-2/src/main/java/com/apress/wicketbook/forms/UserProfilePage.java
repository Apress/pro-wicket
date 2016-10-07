package com.apress.wicketbook.forms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import wicket.Component;
import wicket.ajax.AbstractDefaultAjaxBehavior;
import wicket.behavior.HeaderContributor;
import wicket.extensions.util.resource.TextTemplateHeaderContributor;
import wicket.markup.html.form.DropDownChoice;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextField;
import wicket.model.AbstractReadOnlyModel;
import wicket.model.CompoundPropertyModel;
import wicket.model.IModel;
import wicket.model.Model;

import com.apress.wicketbook.common.UserProfile;

public class UserProfilePage extends AppBasePage {

	public UserProfilePage() {
		UserProfile userProfile = new UserProfile();
		CompoundPropertyModel userProfileModel = new CompoundPropertyModel(
				userProfile);
		final Form form = new UserProfileForm("userProfile", userProfileModel);

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
	}

	class UserProfileForm extends Form {
		// PropertyModel is an IModel implementation
		public UserProfileForm(String id, IModel model) {
			super(id, model);
		}

		@Override
		public void onSubmit() {
			/* Print the contents of its own model object */
			System.out.println(getModelObject());
		}
	}
}
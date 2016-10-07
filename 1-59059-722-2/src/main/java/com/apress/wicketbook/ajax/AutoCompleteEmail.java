package com.apress.wicketbook.ajax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import wicket.Response;
import wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteRenderer;
import wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteBehavior;
import wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import wicket.extensions.ajax.markup.html.autocomplete.IAutoCompleteRenderer;
import wicket.extensions.ajax.markup.html.autocomplete.StringAutoCompleteRenderer;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextField;
import wicket.model.Model;

public class AutoCompleteEmail extends WebPage {
	// An in-memory list of email addresses
	private List emailAddresses = Arrays.asList(new Email[] {
			new Email("aaron@some-company.com", true),
			new Email("amit@developers.net", false),
			new Email("akshay@dev.com", false),
			new Email("bob@developers.net", false),
			new Email("abc@hello.com", true),
			new Email("best@developer.com", true),
			new Email("craig@yourcompany.com", false),
			new Email("chris@broadnetworks.com", true) });

	public AutoCompleteEmail() {
		Form form = new Form("form");
		TextField txtEmail = new TextField("email", new Model());
		/*
		 * Adding autocomplete behavior to the TextField component.
		 * 
		 */
		txtEmail
				.add(new AutoCompleteBehavior(new StringAutoCompleteRenderer()) {
					/**
					 * Return the results that match the input supplied by the
					 * user. In a real-world application, you might run a search
					 * against the database. In this case, you shall use an
					 * in-memory representation. Just check if the email starts
					 * with the input that was typed in and return it.
					 */
					@Override
					protected Iterator getChoices(String input) {
						List completions = new ArrayList();
						Iterator iter = emailAddresses.iterator();
						while (iter.hasNext()) {
							String email = ((Email) iter.next()).email;
							if (email.startsWith(input)) {
								completions.add(email);
							}
						}
						return completions.iterator();
					}
				});
		form.add(txtEmail);
		// Define the custom renderer. In addition to the email, you also render
		// additional information.
		IAutoCompleteRenderer informativeRenderer = new AbstractAutoCompleteRenderer() {
			@Override
			protected void renderChoice(Object object, Response r) {
				String val = ((Email) object).email;
				r.write("<div style='float:left; color:red; '>");
				r.write(val);
				r.write("</div><div style='text-align:right; width:100%;'>");
				r.write("" + ((Email) object).getEmailInfo());
				r.write("</div>");
			}

			// AbstractAutoAssistRenderer calls this method to get the actual
			// value that will show up on
			// selection. StringAutoAssistRenderer, which you use for the
			// "txtEmail" component, overrides
			// this method by returning a "String"
			// representation of the object (does a toString()). Since
			// you add email instances (see getChoices() below), the "object"
			// param in this case is of type "Email".
			protected String getTextValue(Object object) {
				return ((Email) object).email;
			}


		};
		/*
		 * The TextField component "txtEmail" has Ajax behavior added to it. You
		 * also have the option of "committing" to an implementation up front:
		 * You directly program to an Ajax-ified text field here, passing in the
		 * custom renderer.
		 */
		TextField txtEmailInfo = new AutoCompleteTextField("emailInfo",
				new Model(), informativeRenderer) {
			@Override
			protected Iterator getChoices(String input) {
				List completions = new ArrayList();
				Iterator iter = emailAddresses.iterator();
				while (iter.hasNext()) {
					Email emailObj = (Email) iter.next();
					// Check for the email "String" but add Email objects!
					if (emailObj.email.startsWith(input)) {
						completions.add(emailObj);
					}
				}
				return completions.iterator();
			}
		};
		form.add(txtEmailInfo);
		add(form);
	}

}

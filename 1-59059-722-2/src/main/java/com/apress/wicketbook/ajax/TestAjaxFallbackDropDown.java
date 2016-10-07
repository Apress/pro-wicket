package com.apress.wicketbook.ajax;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import wicket.Component;
import wicket.ajax.AjaxRequestTarget;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.DropDownChoice;
import wicket.markup.html.form.Form;
import wicket.model.AbstractReadOnlyModel;
import wicket.model.IModel;
import wicket.model.PropertyModel;

public class TestAjaxFallbackDropDown extends WebPage {

	String country;
	
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public TestAjaxFallbackDropDown() {
		initStates();
		Form form = new Form("form");
				
		IModel statechoices = new AbstractReadOnlyModel() {
			// Wicket calls this method when it tries to look
			// for the model object for displaying the states.
			// This is the indirection introduced by the Wicket's models.
			public Object getObject(Component component) {
				String[] models = (String[]) countryToStateMap.get(getCountry());
				if (models == null) {
					return Collections.EMPTY_LIST;
				}
				return Arrays.asList(models);
			}
		};
		final DropDownChoice stateDD = new DropDownChoice("state",statechoices);	
		DropDownChoice countryDD = new AjaxFallbackDropDown("country", Arrays
				.asList(new String[] { "India", "US", "UK" })) {
			public void onSelectionChanged(Object obj) {
				//obj will contain the DropDownChoice selection
				//in the absence of Ajax (fallback behavior)
				//or the AjaxRequestTarget is Ajax is enabled
				System.out.println(getCountry());
				//If Ajax is enabled, this part of the code will execute
				//else it will fallback automatically to form submit on
				//'country' DropDown Change 
				if(obj != null && obj instanceof AjaxRequestTarget){
					((AjaxRequestTarget)obj).addComponent(stateDD);
				}
			}
		};
		countryDD.setModel(new PropertyModel(this, "country"));		
		form.add(countryDD);
		form.add(stateDD);
		add(form);
	}
}

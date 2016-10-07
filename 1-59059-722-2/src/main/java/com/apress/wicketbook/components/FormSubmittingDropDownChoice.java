package com.apress.wicketbook.components;

import java.util.List;

import wicket.markup.ComponentTag;
import wicket.markup.html.form.DropDownChoice;
import wicket.markup.html.form.Form;
import wicket.model.IModel;

public class FormSubmittingDropDownChoice extends DropDownChoice implements
		MyOnChangeListener {
	public FormSubmittingDropDownChoice(String id, IModel model, List choices) {
		super(id, model, choices);
	}

	public void userSelectionChanged() {
		getForm().process();
		onSelectionChanged(getModelObject());
	}

	protected void onComponentTag(final ComponentTag tag) {
		super.onComponentTag(tag);
		// URL that points to this component's IOnChangeListener method
		final String url = urlFor(MyOnChangeListener.INTERFACE).toString();
		Form form = getForm();
		tag.put("onChange", form.getJsForInterfaceUrl(url));
		System.out.println("URL for our listener " + url);
		System.out.println("Javascript code for our URL "
				+ form.getJsForInterfaceUrl(url));
	}
}

package com.apress.wicketbook.integration;

import java.util.List;

import wicket.markup.html.form.DropDownChoice;
import wicket.model.IModel;

class CategoryDropDownChoice extends DropDownChoice {
	public CategoryDropDownChoice(String id, IModel model, List choices) {
		super(id, model, choices);
	}

	// You would require a server-side notification when
	// the book category is changed.
	public boolean wantOnSelectionChangedNotifications() {
		return true;
	}
}
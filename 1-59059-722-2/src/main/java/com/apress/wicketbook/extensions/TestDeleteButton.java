package com.apress.wicketbook.extensions;

import wicket.markup.html.WebPage;
import wicket.markup.html.form.Button;
import wicket.markup.html.form.Form;
import wicket.markup.html.panel.FeedbackPanel;

public class TestDeleteButton extends WebPage {
	public TestDeleteButton() {
		add(new FeedbackPanel("feedback"));
		Form form = new Form("form");
		Button deleteButton1 = new Button("deleteButton1") {
			public void onSubmit() {
				info(" You clicked deleteButton1 ");
			}
		};
		deleteButton1.add(new ConfirmDeleteBehavior());
		form.add(deleteButton1);
		Button deleteButton2 = new Button("deleteButton2") {
			public void onSubmit() {
				info(" You clicked deleteButton2 ");
			}
		};
		deleteButton2.add(new ConfirmDeleteBehavior() {
			// Override the client-side JavaScript alert
			// message.
			public String getJSMessage() {
				return "You clicked Custom Delete Button!!";
			}
		});
		form.add(deleteButton2);
		add(form);
	}
}

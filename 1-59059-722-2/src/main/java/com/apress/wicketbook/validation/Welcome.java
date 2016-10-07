package com.apress.wicketbook.validation;

import wicket.Page;
import wicket.PageParameters;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.html.link.Link;
import wicket.model.PropertyModel;

public class Welcome extends WebPage {
	private String userId;
	private Page prevPage;
	public Welcome() {
		add(new Label("message", new PropertyModel(this, "userId")));
		Link linkToUserProfile = new Link("linkToUserProfile") {
			public void onClick() {
				// Set the response page
				setResponsePage(UserProfilePage.class);
			}
		};
		Link linkToLogin = new Link("linkToLogin") {
			public void onClick() {
				setResponsePage(prevPage==null?new 
		                Login():prevPage);
			}
		};
		// Don't forget to add them to the Form
		add(linkToUserProfile);
		add(linkToLogin);
	}

	public Welcome(PageParameters params) {
		this();
		/*
		 * PageParameters class has methods to get to the parameter value when
		 * supplied with the key.
		 */
		setUserId(params.getString("userId"));
	}
	public Welcome(String userId, Page prevPage){
	       this();
	       this.userId = userId;
	       this.prevPage = prevPage;
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
package com.apress.wicketbook.shop;

import wicket.Page;
import wicket.PageParameters;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.model.PropertyModel;

public class Welcome extends WebPage {
	private String userId;
	private Page prevPage;
	public Welcome() {
		add(new Label("message", new PropertyModel(this, "userId")));
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
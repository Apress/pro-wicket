package com.apress.wicketbook.forms;

import wicket.protocol.http.WebApplication;
import wicket.protocol.http.WebSession;

import com.apress.wicketbook.common.User;

public class HelloWorldSession extends WebSession {
	private User user;

	/** WebSession needs a reference to the Application class. * */
	public HelloWorldSession(WebApplication application) {
		super(application);
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return this.user;
	}

	// A helper to determine whether the user is logged in
	public boolean isUserLoggedIn() {
		return (user != null);
	}
}

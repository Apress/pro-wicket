package com.apress.wicketbook.forms;

import wicket.RestartResponseAtInterceptPageException;
import wicket.markup.html.WebPage;

public abstract class SecuredBasePage extends WebPage {
	public SecuredBasePage() {
		verifyAccess();
	}

	protected void verifyAccess() {
		// Redirect to Login page on invalid access.
		if (!isUserLoggedIn()) {
			throw new RestartResponseAtInterceptPageException(Login.class);
		}
	}

	protected boolean isUserLoggedIn() {
		return ((HelloWorldSession) getSession()).isUserLoggedIn();
	}
}

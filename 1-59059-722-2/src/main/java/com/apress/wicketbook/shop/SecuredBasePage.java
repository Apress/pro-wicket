package com.apress.wicketbook.shop;

import wicket.RestartResponseAtInterceptPageException;
import wicket.Session;
import wicket.markup.html.WebPage;

public class SecuredBasePage extends WebPage {
	public SecuredBasePage(){
		if (!isAuthorized()){
			throw new RestartResponseAtInterceptPageException(Login.class);
		}
	}
	protected boolean isAuthorized() {
		BookStoreSession session = ((BookStoreSession) Session.get());
		return session == null ? false : session.isUserLoggedIn();
	}
}

package com.apress.wicketbook.shop;

import wicket.Component;
import wicket.Page;
import wicket.RestartResponseAtInterceptPageException;
import wicket.Session;
import wicket.authorization.IUnauthorizedComponentInstantiationListener;
import wicket.authorization.strategies.page.AbstractPageAuthorizationStrategy;

public class StoreAuthorizationStrategy extends
		AbstractPageAuthorizationStrategy implements
		IUnauthorizedComponentInstantiationListener {
	public StoreAuthorizationStrategy() {
	}

	/**
	 * @see wicket.authorization.strategies.page.AbstractPageAuthorizationStrategy#
	 *      isPageAuthorized(java.lang.Class) If a page has the specified
	 *      annotation, check for authorization.
	 */
	protected boolean isPageAuthorized(final Class pageClass) {
		if (pageClass.isAnnotationPresent(SecuredWicketPage.class)) {
			return isAuthorized();
		}
		// Allow construction by default
		return true;
	}

	/**
	 * Gets whether the current user/session is authorized to instantiate a page
	 * class that contains the tagging annotation passed to the constructor.
	 * 
	 * @return True if the instantiation should be allowed to proceed, false if
	 *         the user should be directed to the application's sign-in page.
	 */
	protected boolean isAuthorized() {
		BookStoreSession session = ((BookStoreSession) Session.get());
		return session == null ? false : session.isUserLoggedIn();
	}

	/**
	 * On unauthorized access, you redirect the user to the SignOnPage.
	 */
	public void onUnauthorizedInstantiation(Component component) {
		if (component instanceof Page) {
			throw new RestartResponseAtInterceptPageException(Login.class);
		}
	}
}

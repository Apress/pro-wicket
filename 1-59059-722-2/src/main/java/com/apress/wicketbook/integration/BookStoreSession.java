package com.apress.wicketbook.integration;

import wicket.protocol.http.WebApplication;
import wicket.protocol.http.WebSession;

import com.apress.wicketbook.common.Cart;
import com.apress.wicketbook.common.User;

public class BookStoreSession extends WebSession {
	private Cart cart;

	private User user;

	public BookStoreSession(WebApplication app) {
		super(app);

	}

	/*
	 * Some users might not be interesting in buying a book. Maybe they are
	 * interested in reading a book review, for example. So create the cart on
	 * demand and not by default.
	 */
	public Cart getCart() {
		if (cart == null)
			cart = new Cart();
		return cart;
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

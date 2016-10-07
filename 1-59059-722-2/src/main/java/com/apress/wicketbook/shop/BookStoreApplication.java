package com.apress.wicketbook.shop;

import java.io.Serializable;

import wicket.ISessionFactory;
import wicket.Session;
import wicket.authorization.strategies.CompoundAuthorizationStrategy;
import wicket.protocol.http.WebApplication;

public class BookStoreApplication extends WebApplication implements
		Serializable {
	private BookDao bookDao;

	public BookStoreApplication() {
		// Instantiate the only instance of BookDao.
		bookDao = new BookDao();
	}

	public void init() {
		StoreAuthorizationStrategy storeAuthStrategy = new StoreAuthorizationStrategy();		
		getSecuritySettings().setAuthorizationStrategy(storeAuthStrategy);
		getSecuritySettings().setUnauthorizedComponentInstantiationListener(
				storeAuthStrategy);
	}

	public BookDao getBookDao() {
		return bookDao;
	}

	public ISessionFactory getSessionFactory() {
		return new ISessionFactory() {
			public Session newSession() {
				return new BookStoreSession(BookStoreApplication.this);
			}
		};
	}

	public Class getHomePage() {
		return ViewBooks.class;
	}
}

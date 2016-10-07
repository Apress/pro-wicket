package com.apress.wicketbook.layout;

import java.io.Serializable;

import wicket.ISessionFactory;
import wicket.Session;
import wicket.protocol.http.WebApplication;


public class BookStoreApplication extends WebApplication implements
		Serializable {
	private BookDao bookDao;

	public BookStoreApplication() {
		// Instantiate the only instance of BookDao.
		bookDao = new BookDao();
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

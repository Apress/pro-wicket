package com.apress.wicketbook.integration;

import wicket.ISessionFactory;
import wicket.Session;
import wicket.spring.injection.annot.AnnotSpringWebApplication;

import com.apress.wicketbook.shop.model.IBookDao;

public class AnnotBookStoreApplication extends AnnotSpringWebApplication {
	
	private IBookDao bookDao;
	
	public IBookDao getBookDao() {
		return bookDao;
	}

	public void setBookDao(IBookDao bookDao) {
		this.bookDao = bookDao;
	}

	public ISessionFactory getSessionFactory() {
		return new ISessionFactory() {
			public Session newSession() {
				return new BookStoreSession(AnnotBookStoreApplication.this);
			}
		};
	}

	public void init() {
		getBookDao().init();		
	}

	
	public Class getHomePage() {
		// You will be developing this class next.
		return BooksWithDI.class;
	}
}
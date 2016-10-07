package com.apress.wicketbook.integration;

import java.io.Serializable;

import wicket.ISessionFactory;
import wicket.Session;
import wicket.protocol.http.WebApplication;

import com.apress.wicketbook.shop.model.IBookDao;


public class BookStoreApplication extends WebApplication implements
		Serializable {
	private IBookDao bookDao;

	public BookStoreApplication() {
		// Instantiate the only instance of BookDao.
		//bookDao = new BookDao();
	}


	public IBookDao getBookDao() {
		return bookDao;
	}

	public ISessionFactory getSessionFactory() {
		return new ISessionFactory() {
			public Session newSession() {
				return new BookStoreSession(BookStoreApplication.this);
			}
		};
	}
	//	 Use Spring's setter injection technique to have the dependency resolved.
	public void setBookDao(IBookDao bookDao){
		this.bookDao = bookDao;
	}
	
	public Class getHomePage() {
		//return Books.class;
		return BooksWithDI.class;
	}
}

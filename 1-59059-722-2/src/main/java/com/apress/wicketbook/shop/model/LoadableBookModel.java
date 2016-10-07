package com.apress.wicketbook.shop.model;

import wicket.Application;
import wicket.model.LoadableDetachableModel;

import com.apress.wicketbook.common.Book;
import com.apress.wicketbook.shop.BookDao;
import com.apress.wicketbook.shop.BookStoreApplication;

public class LoadableBookModel extends LoadableDetachableModel {
	private final int id;

	public LoadableBookModel(Book book) {
		this(book, book.getId());
	}

	public LoadableBookModel(Book book, int id) {
		// The book instance passed to the LoadableDetachableModel
		// constructor is marked as a transient object. This
		// takes care of the serialization issue.
		super(book);
		if (id == 0) {
			throw new IllegalArgumentException();
		}
		this.id = id;
	}

	private BookDao getBookDao() {
		return ((BookStoreApplication) Application.get()).getBookDao();
	}

	// You are expected to return the model object.
	@Override
	protected Object load() {
		return getBookDao().getBook(id);
	}
}

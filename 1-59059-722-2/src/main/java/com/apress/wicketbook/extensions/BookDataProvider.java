package com.apress.wicketbook.extensions;

import java.util.Iterator;
import wicket.Application;
import wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import wicket.model.IModel;

import com.apress.wicketbook.common.Book;


public class BookDataProvider extends SortableDataProvider {
	// Holds on to the current user-selected category
	// ('ALL'/'J2EE'/'Scripting')
	private String category;

	public BookDataProvider(String category) {
		this.category = category;
	}

	// By default display all books.
	public BookDataProvider() {
		this(BookDao.CATEGORY_ALL);
	}

	/***************************************************************************
	 * @see Iterator IDataProvider.iterator( final int first, final int count)
	 **************************************************************************/
	// The data for the "current" page
	public Iterator iterator(final int first, final int count) {
		return getBookDao().getBooksForCategory(category, first, count)
				.iterator();
	}

	/** @see int IDataProvider.size() * */
	// This is required to determine the total number of
	// Pages the DataView or an equivalent "repeater"
	// component is working with.
	public int size() {
		return getBookDao().getBookCount(category);
	}

	/** @see IModel IDataProvider.model(Object object) * */
	public IModel model(Object object) {
		// You will see shortly what you need to be
		// returning from this method.
		return new DetachableBookModel((Book)object);
	}

	// The BookDao has to be looked up when required.
	private BookDao getBookDao() {
		return ((BookStoreApplication) Application.get()).getBookDao();
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}

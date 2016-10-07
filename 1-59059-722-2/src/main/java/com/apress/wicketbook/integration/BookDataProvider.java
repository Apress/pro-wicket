package com.apress.wicketbook.integration;

import java.util.Iterator;

import wicket.extensions.markup.html.repeater.data.IDataProvider;
import wicket.model.IModel;

import com.apress.wicketbook.common.Book;
import com.apress.wicketbook.shop.model.IBookDao;

public class BookDataProvider implements IDataProvider {
	// Holds on to the current user-selected category
	// ('ALL'/'J2EE'/'Scripting')
	private String category;
	
	//if the BookDao is light weight proxy(as injected by Wicket-Spring-Annot)	
	//then it can be directly accessed 
	
	private IBookDao bookDao;
	
	public BookDataProvider(IBookDao bookDao,String category){
		this.bookDao = bookDao;
		this.category = category;
	}
	public BookDataProvider(IBookDao bookDao) {
		this(bookDao,IBookDao.CATEGORY_ALL);
	}

	/***************************************************************************
	 * @see Iterator IDataProvider.iterator( final int first, final int count)
	 **************************************************************************/
	// The data for the "current" page
	public Iterator iterator(final int first, final int count) {
		return bookDao.getBooksForCategory(category, first, count)
				.iterator();
	}

	/** @see int IDataProvider.size() * */
	// This is required to determine the total number of
	// Pages the DataView or an equivalent "repeater"
	// component is working with.
	public int size() {
		//access bookDao directly now as its light weight
		return bookDao.getBookCount(category);

	}

	/** @see IModel IDataProvider.model(Object object) * */
	public IModel model(Object object) {	
		return new DetachableBookModel((Book)object,bookDao);
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}

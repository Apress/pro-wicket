package com.apress.wicketbook.shop.model;

import java.util.List;

import com.apress.wicketbook.common.Book;



public interface IBookDao {
	// Move the constants to the interface so that
	// it will be visible to all the DAOs.
	/* Some Publishers */
	public static String APRESS = "Apress";

	public static String MANNING = "Manning";

	public static String OREILLY = "Oreilly";

	/* Some categories */
	public static String CATEGORY_J2EE = "J2EE";

	public static String CATEGORY_SCRIPTING = "Scripting";

	public static String CATEGORY_ALL = "All";

	public static String[] categories = new String[] { CATEGORY_J2EE,
			CATEGORY_SCRIPTING, CATEGORY_ALL };

	public void addBook(Book book);

	public Book getBook(int id);

	public List getBooksForCategory(String category, int start, int count);

	public int getBookCount(String category);

	public List findBooksForCategory(String category);

	public List findAllBooks();

	public List getAllCategories();

	public List getSupportedCategories();
	
	public void init();
}

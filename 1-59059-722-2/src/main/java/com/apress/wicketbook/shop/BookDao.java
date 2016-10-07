package com.apress.wicketbook.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.apress.wicketbook.common.Book;

public class BookDao implements Serializable {
	/* Some publishers */
	public static String APRESS = "Apress";

	public static String MANNING = "Manning";

	public static String OREILLY = "Oreilly";

	/* Some categories */
	public static String CATEGORY_J2EE = "J2EE";

	public static String CATEGORY_SCRIPTING = "Scripting";

	public static String CATEGORY_ALL = "All";

	private List books = new ArrayList();

	private String[] categories = new String[] { CATEGORY_J2EE,
			CATEGORY_SCRIPTING, CATEGORY_ALL };

	// Add a few books to the book database.
	public BookDao() {
		addBook(new Book("Rob Harrop", CATEGORY_J2EE, "Pro Spring", 30.00f,
				APRESS));
		addBook(new Book("Damian Conway", CATEGORY_SCRIPTING,
				"Object Oriented Perl", 40.00f, MANNING));
		addBook(new Book("Ted Husted", CATEGORY_J2EE, "Struts In Action",
				40.00f, MANNING));
		addBook(new Book("Alex Martelli", CATEGORY_SCRIPTING,
				"Python in a Nutshell", 35.00f, OREILLY));
		addBook(new Book("Alex Martelli", CATEGORY_SCRIPTING,
				"Python Cookbook", 35.00f, OREILLY));
	}

	public void addBook(Book book) {
		books.add(book);
	}

	/** Retrieve a book given its ID. * */
	public Book getBook(int id) {
		for (int i = 0; i < books.size(); i++) {
			Book book = (Book) books.get(i);
			if (book.getId() == id) {
				return book;
			}
		}
		throw new RuntimeException("Book with id " + id + " not found ");
	}

	/* Get the number of books belonging to a category. */
	public int getBookCount(String category) {
		if (CATEGORY_ALL.equals(category)) {
			return findAllBooks().size();
		}
		int count = 0;
		for (int i = 0; i < books.size(); i++) {
			Book book = (Book) books.get(i);
			if (book.getCategory().equals(category)) {
				count++;
			}
		}
		return count;
	}

	/** Get books that belong to a particular category. * */
	public List findBooksForCategory(String category) {
		if (CATEGORY_ALL.equals(category)) {
			return findAllBooks();
		}
		List result = new ArrayList();
		for (int i = 0; i < books.size(); i++) {
			Book book = (Book) books.get(i);
			if (book.getCategory().equals(category)) {
				result.add(book);
			}
		}
		return result;
	}

	public List findAllBooks() {
		return books;
	}

	/* Get the supported book categories. */
	public List getSupportedCategories() {
		return Arrays.asList(categories);
	}

	/* You will see why you need this later. */

	public List getBooksForCategory(String category, int start, int count) {
		return findBooksForCategory(category).subList(start, start + count);
	}
}

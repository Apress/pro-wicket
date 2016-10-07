package com.apress.wicketbook.integration;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.apress.wicketbook.common.Book;
import com.apress.wicketbook.shop.model.IBookDao;


//HIBERNATE'S EJB3 IMPLEMENTATION WILL GENERATE THE BOOK ID.
//PLEASE COMMENT THE FOLLOWING LINE IN CODE 
//id = ++counter;
//IN CLASS com.apress.wicketbook.common.Book WHEN USING EJB3

public class HibernateBookDao implements IBookDao {
	private EntityManagerFactory entityManagerFactory;

	public HibernateBookDao(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
		init();
	}

	private EntityManager getEntityManager() {
		return getEntityManagerFactory().createEntityManager();
	}

	public void addBook(final Book book) {
		EntityManager manager = getEntityManager();
		EntityTransaction trans = manager.getTransaction();
		try {
			trans.begin();
			manager.persist(book);
			trans.commit();
		} catch (Exception e) {
			trans.rollback();
		} finally {
			manager.clear();
			manager.close();
		}
	}

	public Book getBook(final int id) {
		EntityManager manager = getEntityManager();
		try {
			return manager.find(Book.class, new Integer(id));
		} finally {
			manager.close();
		}
	}

	public List getBooksForCategory(String category, int start, int count) {
		if (CATEGORY_ALL.equals(category)){
			return findAllBooks(start,count);
		}
		EntityManager manager = getEntityManager();
		try {
			Query query = manager.createQuery(
					" select book from Book book where book.category=?1")
					.setParameter(1, category).setFirstResult(start)
					.setMaxResults(count);
			return query.getResultList();
		} finally {
			manager.close();
		}
	}

	public int getBookCount(String category) {
		EntityManager manager = getEntityManager();
		if (CATEGORY_ALL.equals(category)){
			return getTotalBookCount();
		}
		try {
			Query query = manager
					.createQuery("select count(*) from Book where category = ?1");
			query.setParameter(1, category);
			return ((Long) query.getSingleResult()).intValue();
		} finally {
			manager.close();
		}
	}
	
	public int getTotalBookCount(){
		EntityManager manager = getEntityManager();
		try {
			Query query = manager
					.createQuery(" select count(*) from Book");
			return ((Long) query.getSingleResult()).intValue();
		} finally {
			manager.close();
		}
	}
	
	public List findBooksForCategory(String category) {
		EntityManager manager = getEntityManager();
		if (CATEGORY_ALL.equals(category)) {
			return findAllBooks();
		}
		try {
			Query query = manager
					.createQuery("select book from Book as book where book.category = ?1");
			query.setParameter(1, category);
			return query.getResultList();
		} finally {
			manager.close();
		}
	}

	public List findAllBooks() {
		EntityManager manager = getEntityManager();
		try {
			return manager.createQuery("select book from Book as book")
					.getResultList();
		} finally {
			manager.close();
		}
	}
	public List findAllBooks(int start, int count) {
		EntityManager manager = getEntityManager();
		try {
			Query query = manager.createQuery("select book from Book as book")
					.setFirstResult(start)
					.setMaxResults(count);
			return query.getResultList();
		} finally {
			manager.close();
		}
	}

	public List getAllCategories() {
		return null;
	}

	public List getSupportedCategories() {
		return Arrays.asList(categories);
	}

	public List getSearchResult(String bookNameStartsWith) {
		EntityManager manager = getEntityManager();
		try {
			String strQuery = " select book from Book as book where book.title like ?1% ";
			Query query = getEntityManager().createQuery(strQuery);
			query.setParameter(1, bookNameStartsWith);
			return query.getResultList();
		} finally {
			manager.close();
		}
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void init() {
		addBook(new Book("Rob Harrop", CATEGORY_J2EE, "Pro Spring", 30.00f,
				APRESS));
		addBook(new Book("Damian Conway", CATEGORY_SCRIPTING,
				"Object Oriented Perl", 40.00f, MANNING));
		addBook(new Book("Alex Martelli", CATEGORY_SCRIPTING,
				"Python in a Nutshell", 35.00f, OREILLY));
		addBook(new Book("Alex Martelli", CATEGORY_SCRIPTING,
				"Python Cookbook", 35.00f, OREILLY));

			
	}


}

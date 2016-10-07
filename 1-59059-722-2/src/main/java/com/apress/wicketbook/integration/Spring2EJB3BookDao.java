package com.apress.wicketbook.integration;

//Spring 2.0 imports
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apress.wicketbook.common.Book;
import com.apress.wicketbook.shop.model.IBookDao;

public class Spring2EJB3BookDao extends JpaDaoSupport implements IBookDao {
	
	/**
	 * Indicate that we need a new transaction to execute this method
	 * call through Spring's @Transactional annotation
	 */
	
	@Transactional(propagation = Propagation.REQUIRED) 
	public void addBook(final Book book) {
		getJpaTemplate().persist(book);
	}

	public Book getBook(final int id) {
		return getJpaTemplate().find(Book.class, new Integer(id));
	}

	public List getBooksForCategory(final String category, final int start,
			final int count) {
		if (CATEGORY_ALL.equals(category)) {
			return findAllBooks(start,count);
		}
		return (List) getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager manager)
					throws PersistenceException {
				Query query = manager.createQuery(
						" select book from Book book where book.category=?1")
						.setParameter(1, category).setFirstResult(start)
						.setMaxResults(count);
				return query.getResultList();
			}
		});
	}
	

	public int getBookCount(final String category) {
		if (CATEGORY_ALL.equals(category)) {
			return getTotalBookCount();
		}
		Long count = (Long) getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager manager)
					throws PersistenceException {
				Query query = manager
						.createQuery("select count(*) from Book where category = ?1");
				query.setParameter(1, category);
				return query.getSingleResult();
			}
		});
		return count.intValue();
	}
	
	public int getTotalBookCount(){
		Long count = (Long) getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager manager)
					throws PersistenceException {
				Query query = manager
						.createQuery("select count(*) from Book");
				return query.getSingleResult();
			}
		});
		return count.intValue();
	}


	public List findBooksForCategory(String category) {
		return getJpaTemplate().find(
				"select book from Book as book where book.category = ?1",
				category);
	}

	public List findAllBooks(final int start,
			final int count) {
		return (List) getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager manager)
					throws PersistenceException {
				Query query = manager.createQuery(
						" select book from Book book")
						.setFirstResult(start)
						.setMaxResults(count);
				return query.getResultList();
			}
		});
	}
	public List findAllBooks() {
		return getJpaTemplate().find("select book from Book as book");
	}

	/*
	 * Concrete subclasses can override this for custom initialization behavior.
	 * Gets called after population of this instance's bean properties by
	 * Spring's JpaDaoSupport class. In this case, the Dao initialization
	 * routine goes here.
	 * 
	 * IF 'addBook' IS CALLED FROM HERE IT DOES NOT RESULT IN A NEW TRANSACTION
	 * BEING CREATED. So it is being called from within AnnotBookStoreApplication.
	 *  
	 */
	
	@Transactional(propagation = Propagation.REQUIRED)
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

	public List getAllCategories() {
		return null;
	}

	public List getSupportedCategories() {
		return Arrays.asList(categories);
	}
}

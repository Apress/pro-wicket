package com.apress.wicketbook.integration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.FactoryBean;

public class EntityManageFactoryObjectFactory implements FactoryBean {
	private EntityManagerFactory entityManagerFactory;

	public Object getObject() throws BeansException {
		/*
		 * Only one instance of EntityManagerFactory should exist per
		 * application
		 */
		if (entityManagerFactory != null)
			return entityManagerFactory;
		try {
			entityManagerFactory = Persistence
					.createEntityManagerFactory("wicketPersistenceManager");
			return entityManagerFactory;
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new BeanCreationException(
					"Error creating EntityManagerFactory ", ex);
		}
	}

	public Class getObjectType() {
		return EntityManagerFactory.class;			
	}

	public boolean isSingleton() {
		return true;			
	}
}

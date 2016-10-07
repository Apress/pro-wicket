package com.apress.wicketbook.common;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Book")

public class Book implements Serializable {
	// Internal counter to determine book ID
	private static int counter;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Basic
	@Column(name="TITLE", nullable=false,updatable=false)
	private String title;

	@Basic
	@Column(name="AUTHOR", nullable=false,updatable=false)
	private String author;

	@Basic
	@Column(name="PRICE", nullable=false,updatable=true)
	private float price;

	@Basic
	@Column(name="PUBLISHER", nullable=false,updatable=false)
	private String publisher;

	@Basic
	@Column(name="CATEGORY", nullable=false,updatable=false)
	private String category;

	//just for book examples. You should never store such attributes here
	private boolean selected;

	public Book(){ }

	public Book(String author, String category, String title, float price,
			String publisher) {
		super();
		//Generate internal book ID.
		//WHEN USING EJB3, THE CONTAINER WILL GENERATE THE ID. YOU SHOULD NOT
		//BE GENERATING THE ID.
		id = ++counter;
		this.author = author;
		this.category = category;
		this.title = title;
		this.price = price;
		this.publisher = publisher;
	}

	// Define Java bean style getters for all the properties.

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	//just for book examples
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}
}

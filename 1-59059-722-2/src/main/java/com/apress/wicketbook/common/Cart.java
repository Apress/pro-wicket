package com.apress.wicketbook.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {
	private List checkoutBooks;

	public Cart() {
		checkoutBooks = new ArrayList();
	}

	public void addToCart(CheckoutBook book) {
		if (!checkoutBooks.contains(book)) {
			checkoutBooks.add(book);
		}
	}

	public boolean containsBook(int bookId) {
		for (int i = 0; i < checkoutBooks.size(); i++) {
			if ((((CheckoutBook) checkoutBooks.get(i)).getBook().getId()) == bookId) {
				return true;
			}
		}
		return false;
	}

	public List getCheckoutBooks() {
		return checkoutBooks;
	}

	/*
	 * Computes the total price of the books in the cart
	 */
	public float getTotalPrice() {
		float totalPrice = 0;
		for (int i = 0; i < checkoutBooks.size(); i++) {
			totalPrice += ((CheckoutBook) checkoutBooks.get(i)).getTotalPrice();
		}
		return totalPrice;
	}
}
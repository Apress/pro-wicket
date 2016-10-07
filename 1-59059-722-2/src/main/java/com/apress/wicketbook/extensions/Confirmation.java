package com.apress.wicketbook.extensions;

import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.model.CompoundPropertyModel;
import wicket.model.PropertyModel;

import com.apress.wicketbook.common.Cart;
import com.apress.wicketbook.common.CheckoutBook;


public class Confirmation extends WebPage {
	public Confirmation() {
		add(new ListView("booksBought", getCart().getCheckoutBooks()) {
			protected void populateItem(ListItem item) {
				CheckoutBook book = (CheckoutBook) item.getModelObject();
				item.setModel(new CompoundPropertyModel(book));
				item.add(new Label("book.title"));
				item.add(new Label("quantity"));
				item.add(new Label("totalPrice"));
			}
		});
		add(new Label("totalPrice", new PropertyModel(getCart(), "totalPrice")));
	}

	private Cart getCart() {
		return ((BookStoreSession) getSession()).getCart();
	}
}

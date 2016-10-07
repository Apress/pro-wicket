package com.apress.wicketbook.shop;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import wicket.extensions.markup.html.repeater.data.DataView;
import wicket.extensions.markup.html.repeater.data.ListDataProvider;
import wicket.extensions.markup.html.repeater.refreshing.Item;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.Button;
import wicket.markup.html.form.CheckBox;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextField;
import wicket.markup.html.link.Link;
import wicket.model.CompoundPropertyModel;
import wicket.model.PropertyModel;

import com.apress.wicketbook.common.Book;
import com.apress.wicketbook.common.Cart;
import com.apress.wicketbook.common.CheckoutBook;

public class Checkout extends WebPage {
	private Cart cart;

	public Cart getCart() {
		return cart;
	}

	// You might get to this page from another link. So you need a
	// default constructor as well.
	public Checkout() {
		this(Collections.EMPTY_LIST);
	}

	public Checkout(List checkoutBooksIds) {		
		cart = ((BookStoreSession) getSession()).getCart();		
		addBooksToCart(checkoutBooksIds);		
		Form checkoutForm = new Form("checkoutForm");
		final DataView books = new DataView("checkoutBooks",
				new ListDataProvider(cart.getCheckoutBooks())) {
			protected void populateItem(final Item item) {
				CheckoutBook cBook = (CheckoutBook) item.getModelObject();
				final CompoundPropertyModel model = new CompoundPropertyModel(
						cBook);
				// Model is set at parent level, and child components will look
				// it up.
				item.setModel(model);
				// Evaluates model to cBook.getBook().getTitle()
				item.add(new Label("book.title"));
				// Evaluates model to cBook.getBook().getAuthor()
				item.add(new Label("book.author"));
				// Evaluates model to cBook.getBook().getPrice()
				item.add(new Label("book.price"));
				// Evaluates to cBook.getQuantity() & cBook.setQuantity()
				item.add(new TextField("quantity"));
				/* CheckoutBook is the model. */
				item.add(new CheckBox("markedForRemoval"));
			}
		};
		checkoutForm.add(books);
		// Get the cart to determine the total price.
		checkoutForm.add(new Label("priceTotal", new PropertyModel(this.cart,
				"totalPrice")));
		/*
		 * The book quantity is tied to the CheckoutBook that is present in the
		 * cart. The "total price" is also tied to the cart through the use of
		 * the PropertyModel class. Hence the new price calculation is
		 * automatically taken care of. So "recalculate" comes for free!
		 */
		checkoutForm.add(new Button("recalculate") {
			public void onSubmit() {
			}
		});
		
				
		Button checkOutButton = new Button("checkOut"){		
		
			public void onSubmit() {
				setRedirect(true);
				setResponsePage(new Confirmation());
			}
			
			//The checkout button should be enabled only
			//when the user is already logged in
			//Override Component.isEnabled()
			public boolean isEnabled(){
				return ((BookStoreSession) getSession()).isUserLoggedIn();
			}
		};
		
		checkoutForm.add(checkOutButton);
		checkOutButton.setDefaultFormProcessing(false);
		checkoutForm.add(new Button("removeBooks") {
			// When asked to remove the books, remove them from the cart.
			public void onSubmit() {
				Cart cart = ((BookStoreSession) getSession()).getCart();
				for (Iterator iter = cart.getCheckoutBooks().iterator(); iter
						.hasNext();) {
					CheckoutBook book = (CheckoutBook) iter.next();
					if (book.isMarkedForRemoval()) {
						iter.remove();
					}
				}
			}
		});
		checkoutForm.add(new Link("login"){

			@Override
			public void onClick() {
				setResponsePage(new Login(Checkout.this));					
			}			
			//The Login Link should be visible only
			//when the user is already NOT logged in
			//Override Component.isVisible()
			@Override
			public boolean isVisible(){
				return !((BookStoreSession) getSession()).isUserLoggedIn();
			}
		
		});
		add(checkoutForm);
	}

	private void addBooksToCart(List booksMarkedForCheckout) {
		BookDao bookDao = ((BookStoreApplication) getApplication())
				.getBookDao();
		Cart cart = getCart();
		for (Iterator iter = booksMarkedForCheckout.iterator(); iter.hasNext();) {
			int bookId = ((Integer) iter.next()).intValue();
			if (!cart.containsBook(bookId)) {
				Book book = bookDao.getBook(bookId);
				cart.addToCart(new CheckoutBook(book));
			}
		}
	}
	
	
}

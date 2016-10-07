package com.apress.wicketbook.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import wicket.Component;
import wicket.extensions.markup.html.repeater.data.DataView;
import wicket.extensions.markup.html.repeater.data.IDataProvider;
import wicket.extensions.markup.html.repeater.refreshing.Item;
import wicket.extensions.markup.html.repeater.refreshing.OddEvenItem;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.Button;
import wicket.markup.html.form.CheckBox;
import wicket.markup.html.form.DropDownChoice;
import wicket.markup.html.form.Form;
import wicket.markup.html.navigation.paging.PagingNavigator;
import wicket.model.CompoundPropertyModel;
import wicket.model.IModel;
import wicket.model.PropertyModel;

import com.apress.wicketbook.common.Book;

public class ViewBooks extends WebPage {

	private class CheckBoxModel implements IModel, Serializable {
		// Book ID the model represents
		private final Integer bookId;

		public CheckBoxModel(int bookId) {
			this.bookId = new Integer(bookId);
		}

		public IModel getNestedModel() {
			return null;
		}

		/*
		 * Wicket calls this method when rendering the check box. CheckBox needs
		 * to show up selected if the corresponding book has already been
		 * selected.
		 */
		public Object getObject(Component component) {
			return isBookAlreadyMarkedForCheckout();
		}

		private Boolean isBookAlreadyMarkedForCheckout() {
			if (booksMarkedForCheckout.contains(bookId))
				return Boolean.TRUE;
			else
				return Boolean.FALSE;
		}

		/*
		 * Wicket calls this method when pushing the user selection back to the
		 * model. If the user has selected a book, the method adds it to the
		 * back-end store after making sure that it has not been selected
		 * before. If the user has unchecked the check box, the method removes
		 * it from the back-end store if present.
		 */
		public void setObject(Component component, Object object) {
			boolean selected = ((Boolean) object).booleanValue();
			boolean previouslySelected = isBookAlreadyMarkedForCheckout()
					.booleanValue();
			if (selected) {
				if (!previouslySelected) {
					booksMarkedForCheckout.add(bookId);
				}
			} else {
				if (previouslySelected) {
					booksMarkedForCheckout.remove(bookId);
				}
			}
		}

		public void detach() {
		}
	}

	private List booksMarkedForCheckout = new ArrayList();

	// Fetches the supported categories from the BookDao that is registered
	// with the BookStoreApplication. Note that a Page (in fact all Wicket
	// components) has access to the
	// application object through getApplication().
	public List getBookCategories() {
		BookStoreApplication application = (BookStoreApplication) getApplication();
		return application.getBookDao().getSupportedCategories();
	}

	public ViewBooks() {
		final Form form = new Form("bookForm");
		final BookDataProvider dataProvider = new BookDataProvider();
		final DataView books = new BookDataView("books", dataProvider);
		/*
		 * As the method call indicates, this will ensure that only two items
		 * are displayed per page.
		 */
		books.setItemsPerPage(2);
		/*
		 * But a navigator needs to be associated with the DataView to achieve
		 * paging.
		 */
		form.add(new PagingNavigator("navigator", books));
		DropDownChoice categories = new CategoryDropDownChoice("categories",
				new PropertyModel(dataProvider, "category"),
				getBookCategories(), books);
		// The drop-down should show a valid value selected.
		categories.setNullValid(false);

		form.add(categories);
		form.add(books);
		form.add(new Button("addToCart") {
			public void onSubmit() {
				//System.out.println("Need to implement add to cart!!");
				//Set the response as the Checkout page passing in the books selected
				//by the user.
				setResponsePage(new Checkout(ViewBooks.this.booksMarkedForCheckout));
			}
		});
		add(form);
	}

	// DataView class for tabular data display.
	// It works similarly to the ListView component discussed in
	// Chapter 2.
	class BookDataView extends DataView {
		public BookDataView(String id, IDataProvider dataProvider) {
			super(id, dataProvider);
		}

		// DataView calls this method for populating the table rows.
		// Refer to Chapter 2 for a detailed discussion on
		// this callback method.
		protected void populateItem(final Item item) {
			Book book = (Book) item.getModelObject();
			// Use the Book object as the compound model for the
			// DataView components. The enclosed components can use
			// the Book object as their own model class.
			item.setModel(new CompoundPropertyModel(book));
			item.add(new Label("title"));
			item.add(new Label("author"));
			item.add(new Label("publisher"));
			item.add(new Label("price"));
			item
					.add(new MyCheckBox("selected", new CheckBoxModel(book
							.getId())));
			
			//item.add(new AttributeModifier("class", true, new AbstractReadOnlyModel(){
//				 You used this earlier as well with CheckBox model.
//				 It is through this method that Wicket adds a level of indirection
//				 when fetching the "actual" model object.
				//public Object getObject(Component component){
				//	return (item.getIndex() % 2 == 1) ? "even" : "odd";
				//}
			//}
			//));
				
			
			
		}
		
		@Override
		protected Item newItem(final String id, int index, final IModel model){
		return new OddEvenItem(id, index, model);
		}

		// A custom CheckBox that will result in Form submit
		// when checked/unchecked
		class MyCheckBox extends CheckBox {
			public MyCheckBox(String id, IModel model) {
				super(id, model);
			}

			protected boolean wantOnSelectionChangedNotifications() {
				return true;
			}
		}

	}

	// A DropDownChoice that represents the displayed categories
	class CategoryDropDownChoice extends DropDownChoice {
		DataView bookDataView;

		public CategoryDropDownChoice(String id, IModel model,
				List displayData, DataView bookDataView) {
			super(id, model, displayData);
			this.bookDataView = bookDataView;
		}

		// Indicate that you want a server-side notification
		// when the user changes the drop-down selection.
		public boolean wantOnSelectionChangedNotifications() {
			return true;
		}

		public void onSelectionChanged(java.lang.Object newSelection) {
			/*
			 * Note that you are not required to explicitly update the category -
			 * dataProvider.setCategory(newSelection.toString());
			 * 
			 * BookDataProvider's category field is set as the model for
			 * DropdownChoice and hence will be automatically updated when the
			 * form submits. But the DataView model that displays the books
			 * belonging to a particular category needs to reset its current
			 * page. You do that through the following method call.
			 */
			bookDataView.setCurrentPage(0);
			// When selection changes, update the Form component model.
			getForm().process();
		}
	}
}

package com.apress.wicketbook.extensions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import wicket.AttributeModifier;
import wicket.Component;
import wicket.MarkupContainer;
import wicket.extensions.markup.html.repeater.data.DataView;
import wicket.extensions.markup.html.repeater.data.IDataProvider;
import wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import wicket.extensions.markup.html.repeater.data.table.DataTable;
import wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import wicket.extensions.markup.html.repeater.data.table.IColumn;
import wicket.extensions.markup.html.repeater.data.table.NavigationToolbar;
import wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import wicket.extensions.markup.html.repeater.refreshing.Item;
import wicket.extensions.markup.html.repeater.refreshing.OddEvenItem;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.Button;
import wicket.markup.html.form.CheckBox;
import wicket.markup.html.form.Form;
import wicket.markup.html.navigation.paging.PagingNavigator;
import wicket.markup.html.panel.Fragment;
import wicket.markup.html.panel.Panel;
import wicket.model.CompoundPropertyModel;
import wicket.model.IModel;
import wicket.model.Model;
import wicket.model.PropertyModel;

import com.apress.wicketbook.common.Book;
import com.apress.wicketbook.components.FormSubmittingDropDownChoice;

public class ViewBooksPanel extends Panel {

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

	public ViewBooksPanel(String id) {
		super(id);
		final Form form = new Form("bookForm");
		final BookDataProvider dataProvider = new BookDataProvider();

		final DataTable books = new CustomDataTable("books", getColumnsForTable(),
				dataProvider, 4);
		// Adds the default navigation toolbar
		books.addTopToolbar(new NavigationToolbar(books));
		// Adds the column headers with sorting enabled through links
		books.addTopToolbar(new HeadersToolbar(books, dataProvider));

		// final DataView books = new BookDataView("books", dataProvider);
		/*
		 * As the method call indicates, this will ensure that only two items
		 * are displayed per page.
		 */
		// books.setItemsPerPage(2);
		/*
		 * But a navigator needs to be associated with the DataView to achieve
		 * paging.
		 */
		form.add(new PagingNavigator("navigator", books));
		// Instead of DropDownChoice you need the following.
		FormSubmittingDropDownChoice categories = new FormSubmittingDropDownChoice(
				"categories", new PropertyModel(dataProvider, "category"),
				getBookCategories()) {
			public void onSelectionChanged(Object newSelection) {
				books.setCurrentPage(0);
			}
		};
		// The drop-down should show a valid value selected.
		categories.setNullValid(false);

		form.add(categories);
		form.add(books);
		form.add(new Button("addToCart") {
			public void onSubmit() {
				// System.out.println("Need to implement add to cart!!");
				// Set the response as the Checkout page passing in the books
				// selected
				// by the user.
				setResponsePage(new Checkout(
						ViewBooksPanel.this.booksMarkedForCheckout));
			}
		});
		add(form);
	}

	// ..
	// A helper method that constructs the columns required by the
	// DataTable component.
	protected IColumn[] getColumnsForTable() {
		List columns = new ArrayList();
		// Create a column with header "Title", make it sortable, and
		// pass the property "title" to the SortableDataProvider and
		// use "title" as the property expression against the current row's
		// model
		// object.The model object is the actual row from the BookDao (i.e., a
		// Book
		// instance).
		columns.add(new PropertyColumn(new Model("Title"), "title", "title"));
		// Create a column with header "Author" and
		// use "author" as the property expression against the current row's
		// model
		// object.
		columns.add(new PropertyColumn(new Model("Author"), "author"));
		columns.add(new PropertyColumn(new Model("Publisher"), "publisher",
				"publisher"));
		columns.add(new PropertyColumn(new Model("Price"), "price", "price"));
		// Special handling of the check box through the AbstractColumn class
		columns.add(new AbstractColumn(new Model("")) {
			public void populateItem(Item cellItem, String componentId,
					IModel rowModel) {
				final Book book = (Book) rowModel.getObject(null);
				// The following Fragment will replace the markup with wicket:id
				// componentId, while its own markup ID is
				// "checkBoxFrag". Also, the Fragment markup
				// can be found within the ViewBooks page itself.
				Fragment frag = new BookSelectionFragment(componentId,
						"checkBoxFrag", ViewBooksPanel.this, book);
				cellItem.add(frag);
			}
		});
		/**
		columns.add(new AbstractColumn(new Model("")) {
			// DataTable will call this method when rendering the table cell,
			// passing in the ID used in the template, the IModel, and Item.
			public void populateItem(Item cellItem, String componentId,
					IModel rowModel) {
				// rowModel represents the data from the database for each row
				// so the model object (not the model!) is the Book object from
				// the
				// database. Set it as the model.
				setModel(rowModel);
				// Access the model object again to retrieve the Book instance.
				Book book = (Book) getModelObject();
				// Add the component to Item as in DataView.
				cellItem.add(new BookSelectionPanel(componentId, book));
			}
		});
		**/
		return (IColumn[]) columns.toArray(new IColumn[0]);
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

			// item.add(new AttributeModifier("class", true, new
			// AbstractReadOnlyModel(){
			// You used this earlier as well with CheckBox model.
			// It is through this method that Wicket adds a level of indirection
			// when fetching the "actual" model object.
			// public Object getObject(Component component){
			// return (item.getIndex() % 2 == 1) ? "even" : "odd";
			// }
			// }
			// ));

		}

		@Override
		protected Item newItem(final String id, int index, final IModel model) {
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

	public class BookSelectionPanel extends Panel {
		public BookSelectionPanel(String id, Book book) {
			super(id);
			add(new CheckBox("selected", new CheckBoxModel(book.getId())) {
				protected boolean wantOnSelectionChangedNotifications() {
					return true;
				}
			});
		}
	}

	class CustomDataTable extends DataTable {
		public CustomDataTable(String id, IColumn[] columns,
				IDataProvider dataProvider, int rowsPerPage) {
			super(id, columns, dataProvider, rowsPerPage);
		}

		// Item represents a table row (<tr>). Add the class attribute using the
		// factory method provided specifically for such custom processing.
		protected Item newRowItem(final String id, int index, final IModel model) {
			Item item = new Item(id, index, model);
			item.add(new AttributeModifier("class", true, new Model(
					index % 2 == 0 ? "odd" : "even")));
			return item;
		}
	}

	public class BookSelectionFragment extends Fragment {
		// The markupProvider is crucial. It identifies the component
		// whose markup contains the Fragment's markup.
		public BookSelectionFragment(String id, String markupId,
				MarkupContainer markupProvider, Book book) {
			super(id, markupId, markupProvider);
			add(new CheckBox("selected", new CheckBoxModel(book.getId())) {
				protected boolean wantOnSelectionChangedNotifications() {
					return true;
				}
			});
		}
	}

}

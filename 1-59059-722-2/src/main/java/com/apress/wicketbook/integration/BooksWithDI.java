package com.apress.wicketbook.integration;

import wicket.extensions.markup.html.repeater.data.DataView;
import wicket.extensions.markup.html.repeater.data.IDataProvider;
import wicket.extensions.markup.html.repeater.refreshing.Item;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.DropDownChoice;
import wicket.markup.html.form.Form;
import wicket.model.CompoundPropertyModel;
import wicket.model.PropertyModel;
import wicket.spring.injection.annot.SpringBean;

import com.apress.wicketbook.common.Book;
import com.apress.wicketbook.shop.model.IBookDao;

public class BooksWithDI extends WebPage {
	private String category = BookDao.CATEGORY_ALL;

	// Get IBookDao injected from Spring Application context.
	@SpringBean	private IBookDao bookDao;

	// @SpringBean private IBookDao bookDao = null; -> Avoid this by all means!
	// ..
	public BooksWithDI() {
		Form form = new Form("viewBookForm");
		// Pass the reference of the injected Spring Object.
		IDataProvider dataProvider = new BookDataProvider(bookDao);
		form.add(new BookDataView("books", dataProvider));
		// Directly look up categories using bookDao.
		DropDownChoice categories = new CategoryDropDownChoice("categories",
				new PropertyModel(dataProvider, "category"), bookDao.getSupportedCategories());
		form.add(categories);
		add(form);
		// ..
	}

	class BookDataView extends DataView {
		public BookDataView(String id, IDataProvider dataProvider) {
			super(id, dataProvider);
		}

		protected void populateItem(final Item item) {
			Book book = (Book) item.getModelObject();
			item.setModel(new CompoundPropertyModel(book));
			item.add(new Label("title"));
			item.add(new Label("author"));
			item.add(new Label("publisher"));
			item.add(new Label("price"));
		}
	}
	//Note that the helper method getBookCategories() is not needed any longer.
	//as the injected IBookDao instance is light weight proxy and can be stored as
	//an instance variable and can be directly accessed
}
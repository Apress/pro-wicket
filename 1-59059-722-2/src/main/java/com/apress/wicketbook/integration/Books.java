package com.apress.wicketbook.integration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wicket.Component;
import wicket.contrib.markup.html.freemarker.FreeMarkerPanel;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.DropDownChoice;
import wicket.markup.html.form.Form;
import wicket.model.Model;
import wicket.model.PropertyModel;
import wicket.util.resource.IStringResourceStream;
import wicket.util.resource.UrlResourceStream;

import com.apress.wicketbook.shop.model.IBookDao;

public class Books extends WebPage {
	// By default books belonging to ALL categories will be displayed.
	private String category = BookDao.CATEGORY_ALL;

	class BookDetailsModel extends Model {
		/*
		 * Since the category that is selected isn't known until runtime, you
		 * need to make use of the indirection introduced by the model. The
		 * component will call this method on the model every time it needs
		 * access to the underlying "model object."
		 */
		public Object getObject(Component comp) {
			Map data = new HashMap();
			// Fetch the books belonging to the selected book category.
			data.put("bookList", getBookDao().findBooksForCategory(
					getCategory()));
			return data;
		}

		public IBookDao getBookDao() {
			BookStoreApplication application = (BookStoreApplication) getApplication();
			return application.getBookDao();
		}
	}

	public Books() {
		Form form = new Form("viewBookForm");
		BookDetailsModel bookDetailsModel = new BookDetailsModel();

		// The DropDownChoice model is mapped to the Page property
		// ("category") and is directly accessed by
		// the VelocityPanel model. Therefore, nothing special
		// needs to be done in DropDownChoice.onSelectionChanged().

		DropDownChoice categories = new CategoryDropDownChoice("categories",
				new PropertyModel(this, "category"), getBookCategories());

		form.add(categories);

		// Read the velocity template in the form of a stream first.
		IStringResourceStream velocityTemplateStream = new UrlResourceStream(
				getClass().getResource("BookDetails.vm"));

		//Uncomment this when using Velocity
		//Initialize VelocityPanel with the stream and the model.
		//VelocityPanel bookDetailsPanel = new VelocityPanel("bookDetails",
		//		velocityTemplateStream, bookDetailsModel);
		//bookDetailsPanel.setThrowVelocityExceptions(true);
		
		IStringResourceStream freemarkerPanelStream = new UrlResourceStream(
				getClass().getResource("BookDetails.ftl"));		
		FreeMarkerPanel bookDetailsPanel = new FreeMarkerPanel("bookDetails",
				freemarkerPanelStream, bookDetailsModel);
		bookDetailsPanel.setThrowFreeMarkerExceptions(true);
		form.add(bookDetailsPanel);
		
		add(form);
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List getBookCategories() {
		BookStoreApplication application = (BookStoreApplication) getApplication();
		return application.getBookDao().getSupportedCategories();
	}

	
}

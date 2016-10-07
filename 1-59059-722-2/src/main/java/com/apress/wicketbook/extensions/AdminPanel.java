package com.apress.wicketbook.extensions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import wicket.extensions.markup.html.form.palette.Palette;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.IChoiceRenderer;
import wicket.markup.html.panel.Panel;
import wicket.model.Model;

public class AdminPanel extends Panel {
	// A helper to retrieve BookDao
	protected BookDao getBookDao() {
		return ((BookStoreApplication) getApplication()).getBookDao();
	}

	public AdminPanel(String id) {
		super(id);
		configurePalette();
	}

	protected void configurePalette() {
		// This will be displayed in the Available choices section
		// of the palette.
		List categories = getBookDao().getAllCategories();
		// The user selection will be displayed here.
		List displayFilter = getBookDao().getSupportedCategories();
		IChoiceRenderer renderer = new CustomChoiceRenderer();
		final Palette palette = new Palette("palette", new Model(
				(Serializable) displayFilter), new Model(
				(Serializable) categories), renderer, 10, true);
		Form form = new PaletteForm("form", palette);
		form.add(palette);
		add(form);
	}

	// The server-side representation of categories is a list of String objects,
	// and
	// they are unique in the online bookstore application. So use those as the
	// display and ID.
	class CustomChoiceRenderer implements IChoiceRenderer {
		// For a given item in the list, use the String representation for
		// display.
		public Object getDisplayValue(Object object) {
			return object.toString();
		}

		// For a given item in the list, use the String representation for the
		// ID
		// as well.
		public String getIdValue(Object object, int index) {
			return object.toString();
		}
	}

	class PaletteForm extends Form {
		private Palette palette;

		public PaletteForm(String id, Palette palette) {
			super(id);
			this.palette = palette;
		}

		public void onSubmit() {
			List displayFilter = new ArrayList();
			for (Iterator iter = palette.getSelectedChoices(); iter.hasNext();) {
				displayFilter.add(iter.next());
			}
			getBookDao().setDisplayFilter(displayFilter);
		}
	}
}

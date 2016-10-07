package com.apress.wicketbook.layout;

import wicket.markup.html.WebPage;

public class CollapsibleLinksPage extends WebPage {
	public CollapsibleLinksPage() {
		add(new CollapsibleBorder("search") {
			// Specify the header for the search panel.
			public String getHeader() {
				return "Employee Search";
			}
		});
		add(new CollapsibleBorder("searchResults") {
			// Specify the header for the search results panel.
			public String getHeader() {
				return "Employee Search Results";
			}
		});
	}
}
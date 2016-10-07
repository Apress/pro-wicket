package com.apress.wicketbook.layout;

import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.html.link.BookmarkablePageLink;
import wicket.model.PropertyModel;

public abstract class BookShopTemplatePage extends WebPage {
	public BookShopTemplatePage() {
		add(new Label("pageTitle", new PropertyModel(this, "pageTitle")));
		//add(new BoxBorder("pageLinksBorder").setTransparentResolver(true));
		//addLinksToOtherPages();
		// Add the Border components.
		//add(new BoxBorder("pageBorder").setTransparentResolver(true));
	}

	protected void addLinksToOtherPages() {
		add(new BookmarkablePageLink("linkToBooks", ViewBooks.class)
				.setAutoEnable(true));
		add(new BookmarkablePageLink("linkToPromotions", BookPromotions.class)
				.setAutoEnable(true));
		add(new BookmarkablePageLink("linkToArticles", Articles.class)
				.setAutoEnable(true));
	}

	// To be overridden by "child" templates
	public abstract String getPageTitle();
}

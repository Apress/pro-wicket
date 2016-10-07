package com.apress.wicketbook.layout;

public class BookPromotions extends BookShopTemplatePage {
	public BookPromotions() {
		// addLinksToOtherPages();
	}

	// protected void addLinksToOtherPages(){
	// add(new BookmarkablePageLink("linkToBooks", ViewBooks.class));
	// add(new BookmarkablePageLink("linkToPromotions", BookPromotions.class));
	// add(new BookmarkablePageLink("linkToArticles", Articles.class));
	// }

	public String getPageTitle() {
		return "Book Promotions";
	}
}

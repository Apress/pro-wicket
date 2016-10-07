package com.apress.wicketbook.layout;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import wicket.markup.html.basic.Label;
import wicket.markup.html.link.ExternalLink;
import wicket.markup.html.link.PopupSettings;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;

public class Articles extends BookShopTemplatePage {
	public Articles() {
		//addLinksToOtherPages();
		add(new ListView("articles", fetchArticlesFromStore()) {
			private static final long serialVersionUID = 1L;

			protected void populateItem(ListItem item) {
				// Initialize PopupSettings.
				PopupSettings popupSettings = new PopupSettings(
						PopupSettings.RESIZABLE | PopupSettings.SCROLLBARS
								| PopupSettings.LOCATION_BAR
								| PopupSettings.TOOL_BAR
								| PopupSettings.MENU_BAR
								| PopupSettings.STATUS_BAR);
				ArticleLink link = (ArticleLink) item.getModelObject();
				// Configure the ExternalLink with the PopupSettings.
				item.add(new ExternalLink("webPageLink", link.getHref())
						.setPopupSettings(popupSettings));
				item.add(new Label("display", link.getDisplay()));
			}
		});
	}

	//protected void addLinksToOtherPages() {
	//	add(new BookmarkablePageLink("linkToBooks", ViewBooks.class));
	//	add(new BookmarkablePageLink("linkToPromotions", BookPromotions.class));
	//	add(new BookmarkablePageLink("linkToArticles", Articles.class));
	//}

	// Links are typically fetched from some repository store
	// like Database. For now, return an in-memory list.
	private List fetchArticlesFromStore() {
		return Arrays
				.asList(new ArticleLink[] {
						new ArticleLink("Javalobby Wicket Article",
								"http://www.javalobby.org/java/forums/t60786.html"),
						new ArticleLink("Why Somebody Loves Wicket",
								"http://weblogs.java.net/blog/gfx/archive/2005/08/get_to_love_web.html") });
	}

	// Holds onto the link's href and display
	class ArticleLink implements Serializable{
		private String display;

		private String href;

		public ArticleLink(String display, String href) {
			this.display = display;
			this.href = href;
		}

		public String getDisplay() {
			return display;
		}

		public String getHref() {
			return href;
		}

		private static final long serialVersionUID = 1L;
	}
	public String getPageTitle() {
		return "Articles";
	}
}

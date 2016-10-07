package com.apress.wicketbook.components;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import wicket.markup.html.basic.Label;
import wicket.markup.html.link.ExternalLink;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;

public class Deck extends Panel {

	List headers;
	public Deck(String id, IModel model) {
		super(id, model);	
	}

	public Deck(String id) {
		super(id);
		
		headers = Arrays.asList(
				new Header("Java",
						Arrays.asList(new Detail[] {
								new Detail("Pro Wicket",
										"http:// www.apress.com/book/bookDisplay.html?bID=10189"),
								new Detail("Hibernate",
										"http://www.apress.com/book/bookDisplay.html?bID=10151") 
						})
				),
				new Header("Open Source",
						Arrays.asList(new Detail[] {
								new Detail("Pro MySQL",
									"http:// www.apress.com/book/bookDisplay.html?bID=10189"),
								new Detail("Pro Linux",
									"http://www.apress.com/book/bookDisplay.html?bID=10151") 
				})
		));
		
		ListView lv = new ListView("headers",headers) {
			
			protected void populateItem(ListItem item) {
				Header header = (Header)item.getModelObject();
				item.add(new Label("headerTitle",header.title));
				
				item.add(new ListView("detail",header.details){
					protected void populateItem(ListItem item) {
						Detail detail = (Detail) item.getModelObject();
						ExternalLink exLink = null;
						item.add(exLink = new ExternalLink("bookLink", detail.href));
						exLink.add(new Label("bookTitle", detail.title));
					}
				});
			}				
		};
		add(lv);
	}
	
	class Header implements Serializable{
		List details;
		String title;
		boolean isClicked;
		public Header(String title,List details){
			this.title = title;
			this.details = details;
		}
	}

	class Detail implements Serializable {
		public String title;
		public String href;

		Detail(String title, String href) {
			this.title = title;
			this.href = href;
		}
	}
}

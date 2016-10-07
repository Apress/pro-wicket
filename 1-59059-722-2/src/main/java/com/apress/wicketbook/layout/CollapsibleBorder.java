package com.apress.wicketbook.layout;

import wicket.markup.ComponentTag;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.border.Border;
import wicket.model.PropertyModel;

public abstract class CollapsibleBorder extends Border {
	public CollapsibleBorder(String id) {
		super(id);
		WebMarkupContainer collapsibleBorder = new WebMarkupContainer(
				"collapsibleBorder");
		// It's essential that the div outputs its
		// "id" for the JavaScript to toggle its
		// display property at runtime.
		collapsibleBorder.setOutputMarkupId(true);
		WebMarkupContainer header = new Header("header", collapsibleBorder);
		add(header);
		add(collapsibleBorder);
		// The text to identify
		header.add(new Label("headerText", new PropertyModel(this, "header")));
	}

	public abstract String getHeader();

	private class Header extends WebMarkupContainer {
		// The CollapsibleBorder element reference is required in order
		// to determine its "id" at runtime.
		WebMarkupContainer collapsibleBorder;

		public Header(String id, WebMarkupContainer collapsibleBorder) {
			super(id);
			this.collapsibleBorder = collapsibleBorder;
		}

		protected void onComponentTag(ComponentTag tag) {
			String collapsibleBorderId = collapsibleBorder.getMarkupId();
			// This will add an attribute "onclick" that might show up as
			// follows:
			// < <div class="header" wicket:id="header"
			// onclick="toggle('border_collapsibleBorder')">
			tag.put("onclick", "toggle('" + collapsibleBorderId + "')");
		}
	}
}

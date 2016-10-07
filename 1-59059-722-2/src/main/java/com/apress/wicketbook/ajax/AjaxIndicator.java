package com.apress.wicketbook.ajax;

import wicket.ajax.AbstractDefaultAjaxBehavior;
import wicket.markup.ComponentTag;
import wicket.markup.html.WebMarkupContainer;

public class AjaxIndicator extends WebMarkupContainer {
	public AjaxIndicator(String id) {
		super(id);
		setOutputMarkupId(true);
	}

	public void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		tag.put("src", urlFor(AbstractDefaultAjaxBehavior.INDICATOR));
	}
}

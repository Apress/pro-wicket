package com.apress.wicketbook.layout;

import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.html.border.Border;
import wicket.model.Model;

public class MyPage extends WebPage {
	public MyPage() {
		Border border = new MyBorder("myborder");
		add(border);
		border.add(new Label("label",
		new Model(" Wicket Rocks 8-) ")));
	}
}
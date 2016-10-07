package com.apress.wicketbook.extensions;

import java.util.ArrayList;
import java.util.List;

import wicket.Component;
import wicket.extensions.markup.html.tabs.AbstractTab;
import wicket.extensions.markup.html.tabs.ITab;
import wicket.extensions.markup.html.tabs.TabbedPanel;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.html.panel.Panel;
import wicket.model.Model;

public class BookShopTabbedPanelPage extends WebPage {

	

	public BookShopTabbedPanelPage() {
		
		configureTabs();
	}

	// A helper method that configures the tabs
	protected void configureTabs() {
		// Create a list of ITab objects used to feed the tabbed panel.
		final List tabs = new ArrayList();
		
		
		// The model here represents the tab text.
		// Add Books tab and get it to return the appropriate
		// Panel as well. You will be developing the Panels
		// referenced here next.
		tabs.add(new AbstractTab(new Model("Books")) {
			public Panel getPanel(String panelId) {
				return new ViewBooksPanel(panelId);
			}
		});
		// Add Promotions tab as well.
		tabs.add(new AbstractTab(new Model("Promotions")) {
			public Panel getPanel(String panelId) {
				return new BookPromotionsPanel(panelId);
			}
		});
		// Add Articles tab as well.
		tabs.add(new AbstractTab(new Model("Articles")) {
			public Panel getPanel(String panelId) {
				return new ArticlesPanel(panelId);
			}
		});
		// Add Articles tab as well.
		tabs.add(new AbstractTab(new Model("Admin")) {
			public Panel getPanel(String panelId) {
				return new AdminPanel(panelId);
			}
		});

		// Configure the TabbedPanel component with the tabs.
		final TabbedPanel panel = new TabbedPanel("tabs", tabs);
		class TabTitleModel extends Model {
			public Object getObject(Component comp) {
				return ((ITab) tabs.get(panel.getSelectedTab())).getTitle()
						.getObject(null);
			}
		}
		
		add(new Label("title", new TabTitleModel()));

		add(panel);
	}
}
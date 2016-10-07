package com.apress.wicketbook.components;

import java.util.List;
import java.util.regex.Pattern;

import wicket.Application;
import wicket.AttributeModifier;
import wicket.IInitializer;
import wicket.behavior.HeaderContributor;
import wicket.extensions.markup.html.tabs.TabbedPanel;
import wicket.markup.html.PackageResource;
import wicket.model.Model;

public class CustomTabbedPanel extends TabbedPanel {
	public final static class ComponentInitializer implements IInitializer {
		/**
		 * @see wicket.IInitializer#init(wicket.Application)
		 */
		public void init(Application application) {
			// Register all .js, .css, and .gif files as shared resources. This
			// allows
			// you to specify a Java regex pattern to capture all images, .css
			// files,
			// etc.
			PackageResource.bind(application, CustomTabbedPanel.class, Pattern
					.compile(".*\\.css|.*\\.gif"));
		}
	}

	public CustomTabbedPanel(String id, List tabs) {
		super(id, tabs);
		// Add a reference to the panel CSS.
		add(HeaderContributor.forCss(CustomTabbedPanel.class, "panel.css"));
		// You shall identify the div that holds the panel through the class
		// "tabpanel".
		// This is necessary because your style sheet uses it.
		add(new AttributeModifier("class", true, new Model("tabpanel")));
	}
}

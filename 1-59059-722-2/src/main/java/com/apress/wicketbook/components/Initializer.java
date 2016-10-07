package com.apress.wicketbook.components;

import wicket.Application;
import wicket.IInitializer;

/**
 * Initializer for your custom components
 */
public class Initializer implements IInitializer {
	/**
	 * @see wicket.IInitializer#init(wicket.Application)
	 */
	public void init(Application application) {
		// Call the component initializers.
		new CustomTabbedPanel.ComponentInitializer().init(application);
	}
}

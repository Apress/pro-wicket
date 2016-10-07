package com.apress.wicketbook.extensions;

import java.util.Map;

import wicket.Component;
import wicket.behavior.AbstractBehavior;
import wicket.extensions.util.resource.TextTemplateHeaderContributor;
import wicket.markup.ComponentTag;
import wicket.model.AbstractReadOnlyModel;
import wicket.model.IModel;
import wicket.util.collections.MicroMap;

public class ConfirmDeleteBehavior extends AbstractBehavior {
	Component component;

	// Bind the JS call to the onclick event.
	public void onComponentTag(Component component, ComponentTag tag) {
		tag.put("onclick", "return " + getJSFuncName() + "()");
	}

	// TextTemplateHeaderContributor evaluates the template
	// with interpolation variables based on the supplied
	// context, which is a Java map.
	// It is quite similar to VelocityContext, for example.
	IModel variables = new AbstractReadOnlyModel() {
		private Map variables;

		public Object getObject(Component component) {
			if (variables == null) {
				this.variables = new MicroMap();
				variables.put("jsfunc", getJSFuncName());
				variables.put("msg", getJSMessage());
			}
			return variables;
		}
	};

	// This method is called after the behavior is associated
	// with the component through the Component.add(IBehavior)
	// method call. You will need to determine the component
	// markup ID later, as you will find out.
	public void bind(Component component) {
		this.component = component;
		// It is absolutely essential that you output
		// component markup ID, as it will be used later
		// to determine the JavaScript function name uniquely.
		component.setOutputMarkupId(true);
		// TextTemplateHeaderContributor accepts the class
		// to be used for retrieving the classloader for
		// loading the packaged template. Since you specify
		// ConfirmDeleteBehavior as the class, you need to make
		// sure that you keep the confirmdelete.js file in the same
		// package folder structure as ConfirmDeleteBehavior.
		component.add(TextTemplateHeaderContributor.forJavaScript(getClass(),
				"confirmdelete.js", variables));
	}

	// Allow subclasses to specify the
	// custom display message.
	protected String getJSMessage() {
		return "Delete Yes/No?";
	}

	// Use the Markup ID to provide a unique name
	// for the JavaScript function.
	private final String getJSFuncName() {
		return "confirmDelete" + component.getMarkupId();
	}
}

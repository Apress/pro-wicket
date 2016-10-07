package com.apress.wicketbook.ajax;

import wicket.AttributeModifier;
import wicket.feedback.ContainerFeedbackMessageFilter;
import wicket.feedback.IFeedback;
import wicket.feedback.IFeedbackMessageFilter;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.border.Border;
import wicket.model.IModel;
import wicket.model.Model;

public class AjaxResponsiveFormComponentFeedbackBorder extends Border implements
		IFeedback {
	private boolean visible;

	/**
	 * Error indicator that will be shown whenever there is an error-level
	 * message for the collecting component.
	 */
	private final class ErrorIndicator extends WebMarkupContainer {
		public ErrorIndicator(String id) {
			super(id);
			add(new ErrorTextLabel("errorText", new Model("*")));
			add(new ErrorStyleAttributeModifier("style", true, new Model(
					"color:red;")));
		}

		// An error style whose visiblity is determined by the presence
		// of feedback error messages.
		class ErrorStyleAttributeModifier extends AttributeModifier {
			public ErrorStyleAttributeModifier(String attribute,
					boolean addAttributeIfNotPresent, IModel replaceModel) {
				super(attribute, addAttributeIfNotPresent, replaceModel);
			}

			public boolean isVisible() {
				return visible;
			}
		}

		// An error text label whose visibility is determined by the presence
		// of feedback error messages.
		class ErrorTextLabel extends Label {
			public ErrorTextLabel(String id, IModel model) {
				super(id, model);
			}

			public boolean isVisible() {
				return visible;
			}
		}
	}

	public AjaxResponsiveFormComponentFeedbackBorder(final String id) {
		super(id);
		add(new ErrorIndicator("errorIndicator"));
	}

	// The ContainerFeedbackMessageFilter is used to filter out
	// feedback messages belonging to this component.
	protected IFeedbackMessageFilter getMessagesFilter() {
		return new ContainerFeedbackMessageFilter(this);
	}

	/*
	 * This method will be called on the component during Ajax render so that it
	 * gets a chance to determine the presence of error messages.
	 */
	public void updateFeedback() {
		visible = getPage().getFeedbackMessages().messages(getMessagesFilter())
				.size() != 0;
	}
}

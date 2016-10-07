package com.apress.wicketbook.components;

import wicket.IRequestListener;
import wicket.RequestListenerInterface;

public interface MyOnChangeListener extends IRequestListener {
	public static final RequestListenerInterface INTERFACE = new RequestListenerInterface(
			MyOnChangeListener.class);

	// Expect Wicket to call this method when user selection changes.
	void userSelectionChanged();
}

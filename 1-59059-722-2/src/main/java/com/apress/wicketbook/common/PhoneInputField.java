package com.apress.wicketbook.common;

import wicket.markup.html.form.TextField;
import wicket.model.Model;
import wicket.util.convert.IConverter;

import com.apress.wicketbook.validation.UserProfilePage.PhoneNumberConverter;

public class PhoneInputField extends TextField {
	public PhoneInputField(String id, Model model) {
		super(id, model, PhoneNumber.class);
	}

	public PhoneInputField(String id) {
		super(id, PhoneNumber.class);
	}

	public IConverter getConverter() {
		return new PhoneNumberConverter();
	}
}

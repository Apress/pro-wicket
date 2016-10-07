package com.apress.wicketbook.validation;

import wicket.markup.html.WebPage;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.PasswordTextField;
import wicket.markup.html.form.TextField;
import wicket.markup.html.form.validation.EqualPasswordInputValidator;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.PropertyModel;

// Other imports
public class CreateAccount extends WebPage {
	private String userId;

	private String password;

	private String confirmPassword;

	public CreateAccount() {
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		Form form = new CreateAccountForm("createAccountForm");
		form.add(new TextField("userId", new PropertyModel(this, "userId"))
				.setRequired(true));
		PasswordTextField password = (PasswordTextField) new PasswordTextField(
				"password", new PropertyModel(this, "password"));
		password.setResetPassword(false);
		form.add(password);
		PasswordTextField confirmPassword = (PasswordTextField) new PasswordTextField(
				"confirmPassword", new PropertyModel(this, "confirmPassword"))
				.setRequired(true);
		confirmPassword.setResetPassword(false);
		form.add(confirmPassword);
		form.add(new EqualPasswordInputValidator(password, confirmPassword));
		add(form);
		add(feedback);
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	class CreateAccountForm extends Form {
		public CreateAccountForm(String id) {
			super(id);
		}
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}

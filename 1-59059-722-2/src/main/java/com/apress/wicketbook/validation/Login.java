package com.apress.wicketbook.validation;

import wicket.Session;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.PasswordTextField;
import wicket.markup.html.form.TextField;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.PropertyModel;

public class Login extends WebPage {
	private String userId;

	private String password;

	public Login() {
		TextField userIdField = new TextField("userId", new PropertyModel(this,
				"userId"));
		PasswordTextField passField = new PasswordTextField("password",
				new PropertyModel(this, "password"));
//		 Create the panel that will display feedback messages
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		Form form = new LoginForm("loginForm");
		form.add(userIdField);
		form.add(passField);
		add(feedback);
		add(form);
	}

	class LoginForm extends Form {
		public LoginForm(String id) {
			super(id);
		}

		@Override
		public void onSubmit() {
			if (authenticate(userId, password)) {
				String userId = getUserId();
				String password = getPassword();
				Session.get().info("You have logged in successfully");
				Welcome welcomePage = new Welcome(userId, Login.this);
				setResponsePage(welcomePage);
			} else {
				String errMsg = getLocalizer().getString(
					"login.errors.invalidCredentials", Login.this,
					"Unable to sign you in");
					//Register this error message with the form component.
						error(errMsg);
			}
		}
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public final boolean authenticate(final String username,
			final String password) {
		if ("wicket".equalsIgnoreCase(username)
				&& "wicket".equalsIgnoreCase(password))
			return true;
		else
			return false;
	}
}

package com.apress.wicketbook.forms;

import wicket.markup.html.form.Form;
import wicket.markup.html.form.PasswordTextField;
import wicket.markup.html.form.TextField;
import wicket.model.PropertyModel;

public class Login extends AppBasePage {
	private String userId;

	private String password;

	public Login() {
		TextField userIdField = new TextField("userId", new PropertyModel(this,
				"userId"));
		PasswordTextField passField = new PasswordTextField("password",
				new PropertyModel(this, "password"));
		Form form = new LoginForm("loginForm");
		form.add(userIdField);
		form.add(passField);
		add(form);
	}

	class LoginForm extends Form {
		public LoginForm(String id) {
			super(id);
		}

		@Override
		public void onSubmit() {
			if (authenticate(userId, password)) {
				//User loggedInUser = new User(userId);
				//HelloWorldSession session = (HelloWorldSession)getSession();
				//session.setUser(loggedInUser);
				String userId = getUserId();
				String password = getPassword();
				// Continue to original request if present. Else display
				//	Welcome page.
				//if (!continueToOriginalDestination()) {
					Welcome welcomePage = new Welcome(userId, Login.this);
					setResponsePage(welcomePage);
				//}
			} else {
				System.out
						.println("The user id/ password combination is incorrect!\n");
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

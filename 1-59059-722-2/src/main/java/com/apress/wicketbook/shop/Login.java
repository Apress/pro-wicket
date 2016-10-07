package com.apress.wicketbook.shop;

import wicket.Page;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.PasswordTextField;
import wicket.markup.html.form.TextField;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.PropertyModel;

import com.apress.wicketbook.common.User;
import com.apress.wicketbook.forms.HelloWorldSession;

public class Login extends WebPage {
	private String userId;

	private String password;
	
	private Page prevPage;

	public Login() {
		TextField userIdField = new TextField("userId", new PropertyModel(this,
				"userId"));
		PasswordTextField passField = new PasswordTextField("password",
				new PropertyModel(this, "password"));
		Form form = new LoginForm("loginForm");
		form.add(userIdField);
		form.add(passField);
		add(form);
		add(new FeedbackPanel("feedback"));
	}

	public Login(Page prevPage) {
		this();
		this.prevPage = prevPage;
	}

	class LoginForm extends Form {
		public LoginForm(String id) {
			super(id);
		}

		@Override
		public void onSubmit() {
			if (authenticate(getUserId(),getPassword())) {
				User loggedInUser = new User(getUserId());
				BookStoreSession session = (BookStoreSession)getSession();
				session.setUser(loggedInUser);
				// Continue to original request if present. Else display
				//	Welcome page.
				if (prevPage != null) {
					setResponsePage(prevPage);
				}else{
					Welcome welcomePage = new Welcome(getUserId(), Login.this);
					setResponsePage(welcomePage);
				}
			} else {
				error("Try wicket/wicket as the user /password combination");
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

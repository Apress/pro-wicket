package com.apress.wicketbook.forms;

import wicket.Application;
import wicket.ISessionFactory;
import wicket.Session;
import wicket.protocol.http.WebApplication;

public class HelloWorldApplication extends WebApplication {
	public HelloWorldApplication() {
	}

	public void init() {
		String deploymentMode = getWicketServlet().getInitParameter(
				Application.CONFIGURATION);
		configure(deploymentMode);
		// mount("/pages", PackageName.forPackage(Login.class.getPackage()));
		// mountBookmarkablePage("/login", Login.class);
		// mountBookmarkablePage("/userprofile", UserProfilePage.class);
	}

	public Class getHomePage() {
		return Login.class;		
	}

	public ISessionFactory getSessionFactory() {
		return new ISessionFactory() {
			public Session newSession() {
				return new HelloWorldSession(HelloWorldApplication.this);
			}
		};
	}
}
package com.apress.wicketbook.ajax;

import java.io.Serializable;

class Email implements Serializable {
	String email;

	boolean isPersonal;

	Email(String email, boolean isPersonal) {
		this.email = email;
		this.isPersonal = isPersonal;
	}

	// Will use this to provide more info
	String getEmailInfo() {
		return isPersonal ? "Personal Email" : "Official Email";
	}

	public String toString() {
		return email;
	}
}

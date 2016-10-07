package com.apress.wicketbook.common;

import java.io.Serializable;

public class User implements Serializable {
	private String userId;

	public User(String userId) {
		if (userId == null || userId.trim().length() == 0)
			throw new IllegalArgumentException(
					"A user needs to have an associated Id");
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}
}
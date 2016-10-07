package com.apress.wicketbook.common;

import java.io.Serializable;

public class PhoneNumber implements Serializable {
	private String areaCode;

	private String prefix;

	private String number;

	public PhoneNumber(String code, String prefix,String number) {
		this.areaCode = code;
		this.number = number;
		this.prefix = prefix;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public String getNumber() {
		return number;
	}

	public String getPrefix() {
		return prefix;
	}
}

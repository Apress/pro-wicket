package com.apress.wicketbook.localization;

import java.io.Serializable;
import java.util.Locale;

public class UserProfile implements Serializable {
	private String name;

	private String address;

	private String city;

	// private String country;

	// Store the ISO code of the country so that you can switch country display
	// per locale.
	private Locale country;

	private int pin;

	// ..in addition to other things, handle salary as well.
	private double salary;

	// ..
	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	private PhoneNumber phoneNumber;

	public PhoneNumber getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	/*
	 * public String getCountry() { return country; }
	 * 
	 * public void setCountry(String country) { this.country = country; }
	 */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	 * You can return an int!
	 */
	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	/* Returns a friendly representation of the UserProfile object */
	public String toString() {
		String result = " Mr " + getName();
		result += "\n resides at " + getAddress();
		result += "\n in the city " + getCity();
		result += "\n having Pin Code " + getPin();
		result += "\n in the country " + getCountry();
		return result;
	}

	private static final long serialVersionUID = 1L;

	public Locale getCountry() {
		return country;
	}

	public void setCountry(Locale country) {
		this.country = country;
	}
}

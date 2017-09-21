package com.models;

import java.util.Date;
import java.util.Set;

public class AccountSettingsResponse {

	public String email;
	public String login;
	public String language;
	public String accountType;
	public String defaultCurrency;
	public String companyName;
	public String companyStreet;
	public String companyPostalCode;
	public String companyCity;
	public String companyCountry;
	public String companyNip;
	public String firstName;
	public String lastName;
	public String street;
	public String postalCode;
	public String country;
	public Date birthDate;
	public Boolean marketingAgreement;
	public String twoFactorAuthMethod;
	public Set<String> enabledNotificationsTypes;
	public String notificationSound;
	public String phoneNumber;
	public String gaSecret;
}

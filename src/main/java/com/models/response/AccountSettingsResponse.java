package com.models.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.Set;

@ApiModel(value = "Account Settings")
public class AccountSettingsResponse {

	@ApiModelProperty(required = true, notes = "user's email")
	public String email;

	@ApiModelProperty(required = true, notes = "user's login", position = 1)
	public String login;

	@ApiModelProperty(required = true, notes = "language", position = 2)
	public String language;

	@ApiModelProperty(required = true, notes = "type of user's account", position = 3)
	public String accountType;

	@ApiModelProperty(required = true, notes = "default currency", position = 4)
	public String defaultCurrency;

	@ApiModelProperty(required = true, notes = "name of the company if the user represents one", position = 5)
	public String companyName;

	@ApiModelProperty(required = true, notes = "name of the street the company is located on", position = 6)
	public String companyStreet;

	@ApiModelProperty(required = true, notes = "postal code of the company", position = 7)
	public String companyPostalCode;

	@ApiModelProperty(required = true, notes = "name of the city the company is located in", position = 8)
	public String companyCity;

	@ApiModelProperty(required = true, notes = "name of the country the company is located in", position = 9)
	public String companyCountry;

	@ApiModelProperty(required = true, notes = "nip code of the company", position = 10)
	public String companyNip;

	@ApiModelProperty(required = true, notes = "user's first name", position = 11)
	public String firstName;

	@ApiModelProperty(required = true, notes = "user's last name", position = 12)
	public String lastName;

	@ApiModelProperty(required = true, notes = "name of the street of the user's place of residence", position = 13)
	public String street;

	@ApiModelProperty(required = true, notes = "postal code of the user's place of residence", position = 14)
	public String postalCode;

	@ApiModelProperty(required = true, notes = "country name of the user's place of residence", position = 15)
	public String country;

	@ApiModelProperty(required = true, notes = "user's birth date", position = 16)
	public Date birthDate;

	@ApiModelProperty(required = true, notes = "marketing agreement", position = 17)
	public Boolean marketingAgreement;

	@ApiModelProperty(required = true, notes = "two factor authentication", position = 18)
	public String twoFactorAuthMethod;

	@ApiModelProperty(required = true, notes = "list of enabled account notifications", position = 19)
	public Set<String> enabledNotificationsTypes;

	@ApiModelProperty(required = true, notes = "sound of a notification", position = 20)
	public String notificationSound;

	@ApiModelProperty(required = true, notes = "user's phone number", position = 21)
	public String phoneNumber;

	@ApiModelProperty(required = true, notes = "ga secret", position = 22)
	public String gaSecret;
}

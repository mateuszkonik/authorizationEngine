package com.models;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.util.Date;
import java.util.List;

public class RegistrationData {

	@ApiModelProperty(required = true, notes = "user's email")
	public String email;

	@ApiModelProperty(required = true, notes = "user's login", position = 1)
	public String login;

	@ApiModelProperty(required = true, notes = "user's previous password", position = 2)
	public String old_password;

	@ApiModelProperty(required = true, notes = "user's current password", position = 3)
	public String password;

	@ApiModelProperty(required = true, notes = "language", position = 4)
	public String lang;

	@ApiModelProperty(required = true, notes = "type of account", position = 5)
	public String account_type;

	@ApiModelProperty(required = true, notes = "default currency", position = 6)
	public String default_currency;

	@ApiModelProperty(required = true, notes = "pin used to contact helpline", position = 7)
	public Integer helpline_pin;

	@ApiModelProperty(required = true, notes = "name of the company if the user represents one", position = 8)
	public String company_name;

	@ApiModelProperty(required = true, notes = "name of the street the company is located on", position = 9)
	public String company_street;

	@ApiModelProperty(required = true, notes = "postal code of the company", position = 10)
	public String company_postal_code;

	@ApiModelProperty(required = true, notes = "name of the city the company is located in", position = 11)
	public String company_city;

	@ApiModelProperty(required = true, notes = "name of the country the company is located in", position = 12)
	public String company_country;

	@ApiModelProperty(required = true, notes = "nip code of the company", position = 13)
	public String company_nip;

	@ApiModelProperty(required = true, notes = "list of company scanned documents", position = 14)
	public List<String> company_documents_scans;

	@ApiModelProperty(required = true, notes = "user's first name", position = 15)
	public String first_name;

	@ApiModelProperty(required = true, notes = "user's last name", position = 16)
	public String last_name;

	@ApiModelProperty(required = true, notes = "name of the street of the user's place of residence", position = 17)
	public String street;

	@ApiModelProperty(required = true, notes = "postal code of the user's place of residence", position = 18)
	public String postal_code;

	@ApiModelProperty(required = true, notes = "country name of the user's place of residence", position = 19)
	public String country;

	@ApiModelProperty(required = true, notes = "number of user's identity card", position = 20)
	public String identity_card_number;

	@ApiModelProperty(required = true, notes = "date the identity card was issued on", position = 21)
	public Date identity_card_issue_date;

	@ApiModelProperty(required = true, notes = "date the identity card expires", position = 22)
	public Date identity_card_expiration_date;

	@ApiModelProperty(required = true, notes = "user's birth date", position = 23)
	public Date birth_date;

	@ApiModelProperty(required = true, notes = "list of identity card scans", position = 24)
	public List<String> identity_card_scans;

	@ApiModelProperty(required = true, notes = "list of user's address confirmation documents", position = 25)
	public List<String> address_confirmation_scans;

	@ApiModelProperty(required = true, notes = "marketing agreement", position = 26)
	public Boolean marketing_agreement;

	@ApiModelProperty(required = true, notes = "two factor authentication", position = 27)
	public String twofa_method;

	@ApiModelProperty(required = true, notes = "list of enabled account notifications", position = 28)
	public List<String> enabled_notifications_types;

	@ApiModelProperty(required = true, notes = "sound of a notification", position = 29)
	public String notification_sound;

	@ApiModelProperty(required = true, notes = "user's phone number", position = 30)
	public String phone_number;
}

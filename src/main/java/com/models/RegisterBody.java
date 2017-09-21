package com.models;

import java.util.Date;
import java.util.List;

public class RegisterBody {

	public String email;
	public String login;
	public String old_password;
	public String password;
	public String lang;
	public String account_type;
	public String default_currency;
	public Integer helpline_pin;
	public String company_name;
	public String company_street;
	public String company_postal_code;
	public String company_city;
	public String company_country;
	public String company_nip;
	public List<String> company_documents_scans;
	public String first_name;
	public String last_name;
	public String street;
	public String postal_code;
	public String country;
	public String identity_card_number;
	public Date identity_card_issue_date;
	public Date identity_card_expiration_date;
	public Date birth_date;
	public List<String> identity_card_scans;
	public List<String> address_confirmation_scans;
	public Boolean marketing_agreement;
	public String twofa_method;
	public List<String> enabled_notifications_types;
	public String notification_sound;
	public String phone_number;
}

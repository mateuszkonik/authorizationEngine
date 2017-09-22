package future.models;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

public class RegistrationData {

	@ApiModelProperty(required = true, notes = "user's email")
	private String email;

	@ApiModelProperty(required = true, notes = "user's login", position = 1)
	private String login;

	@ApiModelProperty(required = true, notes = "user's previous password", position = 2)
	private String old_password;

	@ApiModelProperty(required = true, notes = "user's current password", position = 3)
	private String password;

	@ApiModelProperty(required = true, notes = "language", position = 4)
	private String lang;

	@ApiModelProperty(required = true, notes = "type of account", position = 5)
	private String account_type;

	@ApiModelProperty(required = true, notes = "default currency", position = 6)
	private String default_currency;

	@ApiModelProperty(required = true, notes = "pin used to contact helpline", position = 7)
	private Integer helpline_pin;

	@ApiModelProperty(required = true, notes = "name of the company if the user represents one", position = 8)
	private String company_name;

	@ApiModelProperty(required = true, notes = "name of the street the company is located on", position = 9)
	private String company_street;

	@ApiModelProperty(required = true, notes = "postal code of the company", position = 10)
	private String company_postal_code;

	@ApiModelProperty(required = true, notes = "name of the city the company is located in", position = 11)
	private String company_city;

	@ApiModelProperty(required = true, notes = "name of the country the company is located in", position = 12)
	private String company_country;

	@ApiModelProperty(required = true, notes = "nip code of the company", position = 13)
	private String company_nip;

	@ApiModelProperty(required = true, notes = "list of company scanned documents", position = 14)
	private List<String> company_documents_scans;

	@ApiModelProperty(required = true, notes = "user's first name", position = 15)
	private String first_name;

	@ApiModelProperty(required = true, notes = "user's last name", position = 16)
	private String last_name;

	@ApiModelProperty(required = true, notes = "name of the street of the user's place of residence", position = 17)
	private String street;

	@ApiModelProperty(required = true, notes = "postal code of the user's place of residence", position = 18)
	private String postal_code;

	@ApiModelProperty(required = true, notes = "country name of the user's place of residence", position = 19)
	private String country;

	@ApiModelProperty(required = true, notes = "number of user's identity card", position = 20)
	private String identity_card_number;

	@ApiModelProperty(required = true, notes = "date the identity card was issued on", position = 21)
	private Date identity_card_issue_date;

	@ApiModelProperty(required = true, notes = "date the identity card expires", position = 22)
	private Date identity_card_expiration_date;

	@ApiModelProperty(required = true, notes = "user's birth date", position = 23)
	private Date birth_date;

	@ApiModelProperty(required = true, notes = "list of identity card scans", position = 24)
	private List<String> identity_card_scans;

	@ApiModelProperty(required = true, notes = "list of user's address confirmation documents", position = 25)
	private List<String> address_confirmation_scans;

	@ApiModelProperty(required = true, notes = "marketing agreement", position = 26)
	private Boolean marketing_agreement;

	@ApiModelProperty(required = true, notes = "two factor authentication", position = 27)
	private String twofa_method;

	@ApiModelProperty(required = true, notes = "list of enabled account notifications", position = 28)
	private List<String> enabled_notifications_types;

	@ApiModelProperty(required = true, notes = "sound of a notification", position = 29)
	private String notification_sound;

	@ApiModelProperty(required = true, notes = "user's phone number", position = 30)
	private String phone_number;

	public Boolean isFieldNull(String field_name) {
		try {
			return this.getClass().getSuperclass().getDeclaredField(field_name).get(this) == null;

		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();

			return true;
		}
	}

	//GETTERS AND SETTERS

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getOld_password() {
		return old_password;
	}

	public void setOld_password(String old_password) {
		this.old_password = old_password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getAccount_type() {
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}

	public String getDefault_currency() {
		return default_currency;
	}

	public void setDefault_currency(String default_currency) {
		this.default_currency = default_currency;
	}

	public Integer getHelpline_pin() {
		return helpline_pin;
	}

	public void setHelpline_pin(Integer helpline_pin) {
		this.helpline_pin = helpline_pin;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getCompany_street() {
		return company_street;
	}

	public void setCompany_street(String company_street) {
		this.company_street = company_street;
	}

	public String getCompany_postal_code() {
		return company_postal_code;
	}

	public void setCompany_postal_code(String company_postal_code) {
		this.company_postal_code = company_postal_code;
	}

	public String getCompany_city() {
		return company_city;
	}

	public void setCompany_city(String company_city) {
		this.company_city = company_city;
	}

	public String getCompany_country() {
		return company_country;
	}

	public void setCompany_country(String company_country) {
		this.company_country = company_country;
	}

	public String getCompany_nip() {
		return company_nip;
	}

	public void setCompany_nip(String company_nip) {
		this.company_nip = company_nip;
	}

	public List<String> getCompany_documents_scans() {
		return company_documents_scans;
	}

	public void setCompany_documents_scans(List<String> company_documents_scans) {
		this.company_documents_scans = company_documents_scans;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIdentity_card_number() {
		return identity_card_number;
	}

	public void setIdentity_card_number(String identity_card_number) {
		this.identity_card_number = identity_card_number;
	}

	public Date getIdentity_card_issue_date() {
		return identity_card_issue_date;
	}

	public void setIdentity_card_issue_date(Date identity_card_issue_date) {
		this.identity_card_issue_date = identity_card_issue_date;
	}

	public Date getIdentity_card_expiration_date() {
		return identity_card_expiration_date;
	}

	public void setIdentity_card_expiration_date(Date identity_card_expiration_date) {
		this.identity_card_expiration_date = identity_card_expiration_date;
	}

	public Date getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(Date birth_date) {
		this.birth_date = birth_date;
	}

	public List<String> getIdentity_card_scans() {
		return identity_card_scans;
	}

	public void setIdentity_card_scans(List<String> identity_card_scans) {
		this.identity_card_scans = identity_card_scans;
	}

	public List<String> getAddress_confirmation_scans() {
		return address_confirmation_scans;
	}

	public void setAddress_confirmation_scans(List<String> address_confirmation_scans) {
		this.address_confirmation_scans = address_confirmation_scans;
	}

	public Boolean getMarketing_agreement() {
		return marketing_agreement;
	}

	public void setMarketing_agreement(Boolean marketing_agreement) {
		this.marketing_agreement = marketing_agreement;
	}

	public String getTwofa_method() {
		return twofa_method;
	}

	public void setTwofa_method(String twofa_method) {
		this.twofa_method = twofa_method;
	}

	public List<String> getEnabled_notifications_types() {
		return enabled_notifications_types;
	}

	public void setEnabled_notifications_types(List<String> enabled_notifications_types) {
		this.enabled_notifications_types = enabled_notifications_types;
	}

	public String getNotification_sound() {
		return notification_sound;
	}

	public void setNotification_sound(String notification_sound) {
		this.notification_sound = notification_sound;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
}

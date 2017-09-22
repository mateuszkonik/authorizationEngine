package future.models.data;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import future.util.totp.TOTPTools;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table(name = "users")
public class Users {

	//todo: appID aktualnie nie uzywane
	@PartitionKey(0)
	@Column(name = "app_id")
	private UUID appId;

	@PartitionKey(1)
	@Column(name = "user_id")
	private UUID userId;

	private String login;

	private String password;

	private String email;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	private Set<String> permissions;

	private Language language;

	@Column(name = "email_verified")
	private Boolean emailVerified = false;

	@Column(name = "account_confirmed")
	private Boolean accountConfirmed = false;

	@Column(name = "2fa_method")
	private String twoFactorMethod = "none";

	@Column(name = "2fa_required_permissions")
	private Set<String> twoFactorRequiredPermissions = new HashSet<>();

	@Column(name = "ga_secret")
	private String gaSecret;

	@Column(name = "default_currency")
	private String defaultCurrency;

	@Column(name = "helpline_pin")
	private Integer helplinePin;

	@Column(name = "account_type")
	private String accountType;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "company_street")
	private String companyStreet;

	@Column(name = "company_postal_code")
	private String companyPostalCode;

	@Column(name = "company_city")
	private String companyCity;

	@Column(name = "company_country")
	private String companyCountry;

	@Column(name = "company_nip")
	private String companyNip;

	@Column(name = "company_documents_scans")
	private Set<String> companyDocumentsScans;

	@Column(name = "street")
	private String street;

	@Column(name = "postal_code")
	private String postalCode;

	@Column(name = "country")
	private String country;

	@Column(name = "identity_card_number")
	private String identityCardNumber;

	@Column(name = "identity_card_issue_date")
	private Date identityCardIssueDate;

	@Column(name = "identity_card_expiration_date")
	private Date identityCardExpirationDate;

	@Column(name = "birth_date")
	private Date birthDate;

	@Column(name = "identity_card_scans")
	private Set<String> identityCardScans;

	@Column(name = "address_confirmation_scans")
	private Set<String> addressConfirmationScans;

	@Column(name = "marketing_agreement")
	private Boolean marketingAgreement;

	@Column(name = "enabled_notifications_types")
	private Set<String> enabledEotificationsTypes;

	@Column(name = "notification_sound")
	private String notificationSound;

	@Column(name = "phone_number")
	private String phoneNumber;

	public static Users createNew(Config config) {

		Users user = new Users();
		user.setAppId(config.getAppId());
		user.setUserId(UUID.randomUUID());
        /*
        user.setEmail(email);
        user.setLogin(login);
        user.setPassword(Passwords.hash(password));*/
		user.setPermissions(config.getDefaultPermissions());
		user.setTwoFactorRequiredPermissions(config.getDefaultTwoFactorAuthRequiredPermissions());
        /*user.setLanguage(lang);*/
		user.setGaSecret(TOTPTools.getRandomSecretKey());

		return user;
	}

	@Override
	public String toString() {
		return "User [userId : "+this.getUserId()+" email : "+this.getEmail()+" login : "+this.getLogin()+"]";
	}

	//GETTERS AND SETTERS

	public String getDefaultCurrency() {
		return defaultCurrency;
	}

	public void setDefaultCurrency(String defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}

	public Integer getHelplinePin() {
		return helplinePin;
	}

	public void setHelplinePin(Integer helplinePin) {
		this.helplinePin = helplinePin;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyStreet() {
		return companyStreet;
	}

	public void setCompanyStreet(String companyStreet) {
		this.companyStreet = companyStreet;
	}

	public String getCompanyPostalCode() {
		return companyPostalCode;
	}

	public void setCompanyPostalCode(String companyPostalCode) {
		this.companyPostalCode = companyPostalCode;
	}

	public String getCompanyCity() {
		return companyCity;
	}

	public void setCompanyCity(String companyCity) {
		this.companyCity = companyCity;
	}

	public String getCompanyCountry() {
		return companyCountry;
	}

	public void setCompanyCountry(String companyCountry) {
		this.companyCountry = companyCountry;
	}

	public String getCompanyNip() {
		return companyNip;
	}

	public void setCompanyNip(String companyNip) {
		this.companyNip = companyNip;
	}

	public Set<String> getCompanyDocumentsScans() {
		return companyDocumentsScans;
	}

	public void setCompanyDocumentsScans(Set<String> companyDocumentsScans) {
		this.companyDocumentsScans = companyDocumentsScans;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIdentityCardNumber() {
		return identityCardNumber;
	}

	public void setIdentityCardNumber(String identityCardNumber) {
		this.identityCardNumber = identityCardNumber;
	}

	public Date getIdentityCardIssueDate() {
		return identityCardIssueDate;
	}

	public void setIdentityCardIssueDate(Date identityCardIssueDate) {
		this.identityCardIssueDate = identityCardIssueDate;
	}

	public Date getIdentityCardExpirationDate() {
		return identityCardExpirationDate;
	}

	public void setIdentityCardExpirationDate(Date identityCardExpirationDate) {
		this.identityCardExpirationDate = identityCardExpirationDate;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Set<String> getIdentityCardScans() {
		return identityCardScans;
	}

	public void setIdentityCardScans(Set<String> identityCardScans) {
		this.identityCardScans = identityCardScans;
	}

	public Set<String> getAddressConfirmationScans() {
		return addressConfirmationScans;
	}

	public void setAddressConfirmationScans(Set<String> addressConfirmationScans) {
		this.addressConfirmationScans = addressConfirmationScans;
	}

	public UUID getAppId() {
		return appId;
	}

	public void setAppId(UUID appId) {
		this.appId = appId;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPasswordHash(String password) {
		this.setPassword(Passwords.hash(password));
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}

	public void appendPermissions(Set<String> permissions) {
		this.permissions.addAll(permissions);
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getTwoFactorMethod() {
		return twoFactorMethod;
	}

	public void setTwoFactorMethod(String twoFactorMethod) {
		this.twoFactorMethod = twoFactorMethod;
	}

	public Set<String> getTwoFactorRequiredPermissions() {
		return twoFactorRequiredPermissions;
	}

	public void setTwoFactorRequiredPermissions(Set<String> twoFactorRequiredPermissions) {
		this.twoFactorRequiredPermissions = twoFactorRequiredPermissions;
	}

	public Boolean getAccountConfirmed() {
		return accountConfirmed;
	}

	public void setAccountConfirmed(Boolean accountConfirmed) {
		this.accountConfirmed = accountConfirmed;
	}

	public String getGaSecret() {
		return gaSecret;
	}

	public void setGaSecret(String gaSecret) {
		this.gaSecret = gaSecret;
	}


	public Boolean getMarketingAgreement() {
		return marketingAgreement;
	}

	public void setMarketingAgreement(Boolean marketingAgreement) {
		this.marketingAgreement = marketingAgreement;
	}

	public Set<String> getEnabledEotificationsTypes() {
		return enabledEotificationsTypes;
	}

	public void setEnabledEotificationsTypes(Set<String> enabledEotificationsTypes) {
		this.enabledEotificationsTypes = enabledEotificationsTypes;
	}

	public String getNotificationSound() {
		return notificationSound;
	}

	public void setNotificationSound(String notificationSound) {
		this.notificationSound = notificationSound;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}

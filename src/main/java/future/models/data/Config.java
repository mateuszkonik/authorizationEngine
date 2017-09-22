package future.models.data;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import future.database.Database;
import future.models.RegistrationData;
import future.response.ErrorMessage;
import future.util.*;
import future.util.exceptions.UsersExceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static future.response.ErrorMessage.*;
import static future.util.RegistrationStep.ACCOUNT_SETTINGS;

@Table(name = "config")
public class Config {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(Config.class);

	@Column(name = "app_id")
	@PartitionKey
	private UUID appId;

	public enum LoginVia{ EMAIL, LOGIN , BOTH };

	@Column(name = "login_via")
	private LoginVia loginVia;

	@Column(name = "require_login")
	private Boolean requireLogin;

	@Column(name = "login_min_length")
	private int loginMinLength;

	@Column(name = "password_min_length")
	private int passwordMinLength;

	@Column(name = "registration_email_templates")
	private Map<Language,Template> registrationEmailTemplates;

	@Column(name = "reset_password_email_templates")
	private Map<Language,Template> resetPasswordEmailTemplates;

	@Column(name = "allow_multi_login")
	private Boolean allowMultiLogin = false;

	@Column(name = "token_valid_seconds")
	private Integer tokenValidSeconds = 900;

	@Column(name = "default_permissions")
	private Set<String> defaultPermissions;

	@Column(name = "permissions_after_email_confirmation")
	private Set<String> permissionsAfterEmailConfirmation;

	@Column(name = "permissions_after_account_confirmation")
	private Set<String> permissionsAfterAccountConfirmation;

	@Column(name = "allowed_domains")
	private Set<String> allowedDomains = new HashSet<>();

	@Column(name = "allowed_account_types")
	private Set<String> allowedAccountTypes = new HashSet<>();

	@Column(name = "auto_login_after_registration")
	private Boolean autoLoginAfterRegistration = false;

	@Column(name = "registration_fields")
	private Set<String> registrationFields = new HashSet<>();

	@Column(name = "email_verification_fields")
	private Set<String> emailVerificationFields = new HashSet<>();

	@Column(name = "account_confirmation_fields")
	private Set<String> accountConfirmationFields = new HashSet<>();

	@Column(name = "account_settings_fields")
	private Set<String> accountSettingsFields = new HashSet<>();

	@Column(name = "2fa_required_fields")
	private Set<String> twoFactorAuthRequiredFields = new HashSet<>();

	@Column(name = "default_2fa_required_permissions")
	private Set<String> defaultTwoFactorAuthRequiredPermissions = new HashSet<>();

	@Column(name = "additional_2fa_required_permissions")
	private Set<String> additionalTwoFactorAuthRequiredPermissions = new HashSet<>();

	public void validatePassword(String password) throws PasswordIsTooShort{
		if (password.length() < this.passwordMinLength)
			throw new PasswordIsTooShort();
	}

	public void validateLogin(String password) throws PasswordIsTooShort{
		if (password.length() < this.passwordMinLength)
			throw new PasswordIsTooShort();
	}


	public static Config get(UUID app_id) throws NotFound{
		Mapper<Config> mapper = Database.getMapper().mapper(Config.class);
		Config config = mapper.get(app_id);
		if (config == null) throw new NotFound();
		return config;
	}

	public static class NotFound extends Exception{
		public NotFound() {
			super("Not found app config");
		}
	}

	public static class PasswordIsTooShort extends Exception{}

	public Set<String> getFieldsByStep(RegistrationStep step) {
		Set<String> fields = new HashSet<>();

		switch(step) {
			case REGISTRATION:
				fields = this.getRegistrationFields();
				break;
			case EMAIL_VERIFICATION:
				fields = this.getEmailVerificationFields();
				break;
			case ACCOUNT_CONFIRMATION:
				fields = this.getAccountConfirmationFields();
				break;
			case ACCOUNT_SETTINGS:
				fields = this.getAccountSettingsFields();
				break;
		}

		return fields;
	}


	public List<ErrorMessage> validateRegistrationData(Users user, RegistrationStep step, RegistrationData registrationData) {
		List<ErrorMessage> errors = new ArrayList<>();
		Set<String> fields = this.getFieldsByStep(step);

		// Email validation
		if (fields.contains("email")) {
			if (registrationData.getEmail() == null) {

				if (step != ACCOUNT_SETTINGS)
					errors.add(EMAIL_NOT_PROVIDED);

			} else if (!Validator.get().validateEmail(registrationData.getEmail()))
				errors.add(INCCORRECT_EMAIL_FORMAT);
			else {

				try {
					UsersTools.findByEmail(this.getAppId(), registrationData.getEmail());
					errors.add(EMAIL_IS_TAKEN);
				} catch (UsersExceptions.NotFound ex) {
					log.debug("Email not found, everything is fine");
				}
			}
		}

		// Login validation
		if (fields.contains("login")) {
			if (registrationData.getLogin() == null && step != ACCOUNT_SETTINGS) {

				if (step != ACCOUNT_SETTINGS)
					errors.add(LOGIN_NOT_PROVIDED);

			}else if (registrationData.getLogin().length() < this.getLoginMinLength())
				errors.add(LOGIN_TOO_SHORT);
			else {
				try {
					UsersTools.findByLogin(this.getAppId(), registrationData.getLogin());
					if (this.getRequireLogin())
						errors.add(LOGIN_IS_TAKEN);
				} catch (UsersExceptions.NotFound ex) {
					log.debug("Login not found, everything is fine");
				}
			}
		}


		// Language
		if (fields.contains("language")) {
			if (registrationData.getLang() == null) {

				if (step != ACCOUNT_SETTINGS)
					errors.add(LANG_NOT_PROVIDED);

			}else if (!Validator.get().validateLanguage(registrationData.getLang()))
				errors.add(NOT_RECOGNIZED_LANG_CODE);
		}


		// Password validation
		if (fields.contains("password")) {

			if (registrationData.getPassword() == null) {

				if (step != ACCOUNT_SETTINGS)
					errors.add(PASSWORD_NOT_PROVIDED);

			} else {
				if (registrationData.getPassword().length() < this.getPasswordMinLength()) {
					errors.add(PASSWORD_IS_TOO_SHORT);
				}

				if (step == ACCOUNT_SETTINGS) {
					try {
						if (TokenTools.checkCredentials(this,user.getEmail(),user.getLogin(),registrationData.getOld_password()) == null) {
							errors.add(INVALID_OLD_USER_PASSWORD);
						}
					} catch (UsersExceptions.NotFound notFound) {
						errors.add(INVALID_OLD_USER_PASSWORD);
					}
				}
			}
		}


		// Default currency validation
		if (fields.contains("default_currency")) {
			if (registrationData.getDefault_currency() == null) {

				if (step != ACCOUNT_SETTINGS)
					errors.add(DEFAULT_CURRENCY_NOT_PROVIDED);

			}else if (!Validator.get().validateCurrency(registrationData.getDefault_currency()))
				errors.add(NOT_RECOGNIZED_DEFAULT_CURRENCY);
		}


		// Helpline pin validation
		if (fields.contains("helpline_pin")) {
			if (registrationData.getHelpline_pin() == null) {

				if (step != ACCOUNT_SETTINGS)
					errors.add(HELPLINE_PIN_NOT_PROVIDED);

			}else if (registrationData.getHelpline_pin().toString().length() < 4 || registrationData.getHelpline_pin().toString().length() > 6)
				errors.add(HELPLINE_PIN_INCORRECT_LENGTH);
		}


		// Account type validation
		if (fields.contains("account_type")) {
			if (registrationData.getAccount_type() == null) {

				if (step != ACCOUNT_SETTINGS)
					errors.add(ACCOUNT_TYPE_NOT_PROVIDED);

			}else if (!allowedAccountTypes.contains(registrationData.getAccount_type())) {
				errors.add(ACCOUNT_TYPE_NOT_ALLOWED);
			}
		}


		if ( registrationData.getAccount_type() != null && registrationData.getAccount_type().equals("COMPANY_ACCOUNT") ||
				registrationData.getAccount_type() == null && user != null && user.getAccountType() != null && user.getAccountType().equals("COMPANY_ACCOUNT")) {

			// Company name validation
			if (fields.contains("company_name")) {
				if (registrationData.getCompany_name() == null ||  registrationData.getCompany_name().equals("")) {

					if (step != ACCOUNT_SETTINGS)
						errors.add(COMPANY_NAME_NOT_PROVIDED);

				}
			}

			// Company street validation
			if (fields.contains("company_street")) {
				if (registrationData.getCompany_street() == null ||  registrationData.getCompany_street().equals("")) {

					if (step != ACCOUNT_SETTINGS)
						errors.add(COMPANY_POSTAL_CODE_NOT_PROVIDED);

				}
			}

			// Company postal code validation
			if (fields.contains("company_postal_code")) {
				if (registrationData.getCompany_postal_code() == null ||  registrationData.getCompany_postal_code().equals("")) {

					if (step != ACCOUNT_SETTINGS)
						errors.add(COMPANY_POSTAL_CODE_NOT_PROVIDED);

				}
			}

			// Company city validation
			if (fields.contains("company_city")) {
				if (registrationData.getCompany_city() == null ||  registrationData.getCompany_city().equals("")) {

					if (step != ACCOUNT_SETTINGS)
						errors.add(COMPANY_CITY_NOT_PROVIDED);

				}
			}

			// Company country validation
			if (fields.contains("company_country")) {
				if (registrationData.getCompany_country() == null ||  registrationData.getCompany_country().equals("")) {

					if (step != ACCOUNT_SETTINGS)
						errors.add(COMPANY_COUNTRY_NOT_PROVIDED);

				}
			}

			// Company nip validation
			if (fields.contains("company_nip")) {
				if ( registrationData.getCompany_country() != null && registrationData.getCompany_country().equals("PL") ||
						registrationData.getCompany_country() == null && user != null && user.getCompanyCountry() != null && user.getCompanyCountry().equals("PL") ) {

					if (registrationData.getCompany_nip() == null || registrationData.getCompany_nip().equals(""))  {

						if (step != ACCOUNT_SETTINGS)
							errors.add(COMPANY_NIP_NOT_PROVIDED);

					} else if (!Validator.get().validateNIP(registrationData.getCompany_nip())) {
						errors.add(COMPANY_NIP_INCORRECT);
					}
				}
			}

			// Company documents scans validation
			if (fields.contains("company_documents_scans")) {
				if (registrationData.getCompany_documents_scans() == null ||  registrationData.getCompany_documents_scans().size() == 0)  {

					if (step != ACCOUNT_SETTINGS)
						errors.add(COMPANY_DOCUMENTS_SCANS_NOT_PROVIDED);

				}
			}
		}


		// First name validation
		if (fields.contains("first_name")) {
			if (registrationData.getFirst_name() == null ||  registrationData.getFirst_name().equals(""))  {

				if (step != ACCOUNT_SETTINGS)
					errors.add(FIRST_NAME_NOT_PROVIDED);

			}
		}


		// Last name validation
		if (fields.contains("last_name")) {
			if (registrationData.getLast_name() == null ||  registrationData.getLast_name().equals(""))  {

				if (step != ACCOUNT_SETTINGS)
					errors.add(LAST_NAME_NOT_PROVIDED);

			}
		}


		// Street validation
		if (fields.contains("street")) {
			if (registrationData.getStreet() == null ||  registrationData.getStreet().equals(""))  {

				if (step != ACCOUNT_SETTINGS)
					errors.add(STREET_NOT_PROVIDED);

			}
		}


		// Postal code validation
		if (fields.contains("postal_code")) {
			if (registrationData.getPostal_code() == null ||  registrationData.getPostal_code().equals(""))  {

				if (step != ACCOUNT_SETTINGS)
					errors.add(POSTAL_CODE_NOT_PROVIDED);

			}
		}


		// Country validation
		if (fields.contains("country")) {
			if (registrationData.getCountry() == null ||  registrationData.getCountry().equals(""))  {

				if (step != ACCOUNT_SETTINGS)
					errors.add(COUNTRY_NOT_PROVIDED);

			}
		}


		// Identity card number validation
		if (fields.contains("identity_card_number")) {
			if (registrationData.getIdentity_card_number() == null ||  registrationData.getIdentity_card_number().equals(""))  {

				if (step != ACCOUNT_SETTINGS)
					errors.add(IDENTITY_CARD_NUMBER_NOT_PROVIDED);

			}
		}


		// Identity card issue date validation
		if (fields.contains("identity_card_issue_date")) {
			if (registrationData.getIdentity_card_issue_date() == null ||  registrationData.getIdentity_card_issue_date().equals(""))  {

				if (step != ACCOUNT_SETTINGS)
					errors.add(IDENTITY_CARD_ISSUE_DATE_NOT_PROVIDED);

			}
		}


		// Identity card expiration date validation
		if (fields.contains("identity_card_expiration_date")) {
			if (registrationData.getIdentity_card_expiration_date() == null ||  registrationData.getIdentity_card_expiration_date().equals(""))  {

				if (step != ACCOUNT_SETTINGS)
					errors.add(IDENTITY_CARD_EXPIRATION_DATE_NOT_PROVIDED);

			}
		}


		// Birth date validation
		if (fields.contains("birth_date")) {
			if (registrationData.getBirth_date() == null ||  registrationData.getBirth_date().equals(""))  {

				if (step != ACCOUNT_SETTINGS)
					errors.add(BIRTH_DATE_NOT_PROVIDED);

			}
		}


		// Identity card scans validation
		if (fields.contains("identity_card_scans")) {
			if (registrationData.getIdentity_card_scans() == null ||  registrationData.getIdentity_card_scans().size() == 0)  {

				if (step != ACCOUNT_SETTINGS)
					errors.add(IDENTITY_CARD_SCANS_PROVIDED);

			}
		}


		// Address confirmation scans validation
		if (fields.contains("address_confirmation_scans")) {
			if (registrationData.getAddress_confirmation_scans() == null ||  registrationData.getAddress_confirmation_scans().size() == 0)  {

				if (step != ACCOUNT_SETTINGS)
					errors.add(ADDRESS_CONFIRMATION_SCANS_PROVIDED);

			}
		}


		// Marketing agreement validation
		if (fields.contains("marketing_agreement")) {
			if (registrationData.getMarketing_agreement() == null)  {

				if (step != ACCOUNT_SETTINGS)
					errors.add(MARKETING_AGREEMENT_NOT_PROVIDED);

			}
		}


		// 2FA method validation
		if (fields.contains("twofa_method")) {
			if (registrationData.getTwofa_method() == null)  {

				if (step != ACCOUNT_SETTINGS)
					errors.add(TWO_FACTOR_METHOD_NOT_PROVIDED);

			} else if (!TwoFactorAuthTools.availableMethods.contains(registrationData.getTwofa_method())) {
				errors.add(NOT_RECOGNIZED_TWO_FACTOR_METHOD);
			}
		}


		// Enabled notifications types validation
		if (fields.contains("enabled_notifications_types")) {
			if (registrationData.getEnabled_notifications_types() == null)  {

				if (step != ACCOUNT_SETTINGS)
					errors.add(ENABLED_NOTIFICATIONS_TYPES_NOT_PROVIDED);

			}
		}


		// Notification sound validation
		if (fields.contains("notification_sound")) {
			if (registrationData.getNotification_sound() == null)  {

				if (step != ACCOUNT_SETTINGS)
					errors.add(NOTIFICATION_SOUND_NOT_PROVIDED);

			}
		}


		// Phone number validation
		if (fields.contains("phone_number")) {
			if (registrationData.getPhone_number() == null)  {

				if (step != ACCOUNT_SETTINGS)
					errors.add(PHONE_NUMBER_NOT_PROVIDED);

			}
		}



		return errors;
	}

	public void setRegistrationData(Users user, RegistrationStep step, RegistrationData registrationData) {

		Set<String> fields = this.getFieldsByStep(step);

		// Email
		if (fields.contains("email")) {
			if (registrationData.getEmail() != null && !registrationData.getEmail().equals(user.getEmail())) {

				if (user.getEmail() != null) UsersTools.deleteEmailHandlings(user);

				user.setEmail(registrationData.getEmail());
				UsersTools.saveEmailHandlings(user);
			}
		}

		// Login
		if (fields.contains("login")) {
			if (registrationData.getLogin() != null && !registrationData.getLogin().equals(user.getLogin())) {

				if (user.getLogin() != null) UsersTools.deleteLoginHandlings(user);

				user.setLogin(registrationData.getLogin());
				UsersTools.saveLoginHandlings(user);
			}
		}

		// Language
		if (fields.contains("language")) {
			if (registrationData.getLang() != null) {
				Language language = null;

				try {
					language = Language.recognize(registrationData.getLang());
				} catch (Language.NotRecognizedException e) {
					e.printStackTrace();
				}

				user.setLanguage(language);
			}
		}

		// Password
		if (fields.contains("password")) {
			if (registrationData.getPassword() != null) {
				user.setPassword(Passwords.hash(registrationData.getPassword()));
			}
		}

		// Default currency
		if (fields.contains("default_currency")) {
			if (registrationData.getDefault_currency() != null) {
				Currency c = Currency.getInstance(registrationData.getDefault_currency());

				user.setDefaultCurrency(c.getCurrencyCode());
			}
		}

		// Helpline pin
		if (fields.contains("helpline_pin")) {
			if (registrationData.getHelpline_pin() != null) {
				user.setHelplinePin(registrationData.getHelpline_pin());
			}
		}

		// Account type
		if (fields.contains("account_type")) {
			if (registrationData.getAccount_type() != null) {
				user.setAccountType(registrationData.getAccount_type());
			}
		}

		// Company name
		if (fields.contains("company_name")) {
			if (registrationData.getCompany_name() != null) {
				user.setCompanyName(registrationData.getCompany_name());
			}
		}

		// Company street
		if (fields.contains("company_street")) {
			if (registrationData.getCompany_street() != null) {
				user.setCompanyStreet(registrationData.getCompany_street());
			}
		}

		// Company postal code
		if (fields.contains("company_postal_code")) {
			if (registrationData.getCompany_postal_code() != null) {
				user.setCompanyPostalCode(registrationData.getCompany_postal_code());
			}
		}

		// Company city
		if (fields.contains("company_city")) {
			if (registrationData.getCompany_city() != null) {
				user.setCompanyCity(registrationData.getCompany_city());
			}
		}

		// Company country
		if (fields.contains("company_country")) {
			if (registrationData.getCompany_country() != null) {
				user.setCompanyCountry(registrationData.getCompany_country());
			}
		}

		// Company nip
		if (fields.contains("company_nip")) {
			if (registrationData.getCompany_nip() != null) {
				user.setCompanyCountry(registrationData.getCompany_nip());
			}
		}

		// Company documents scans
		if (fields.contains("company_documents_scans")) {
			if (registrationData.getCompany_documents_scans() != null) {
				user.setCompanyDocumentsScans(new HashSet<String>(registrationData.getCompany_documents_scans()));
			}
		}

		// First name
		if (fields.contains("first_name")) {
			if (registrationData.getFirst_name() != null) {
				user.setFirstName(registrationData.getFirst_name());
			}
		}

		// Last name
		if (fields.contains("last_name")) {
			if (registrationData.getLast_name() != null) {
				user.setLastName(registrationData.getLast_name());
			}
		}

		// Street
		if (fields.contains("street")) {
			if (registrationData.getStreet() != null) {
				user.setStreet(registrationData.getStreet());
			}
		}

		// Postal code
		if (fields.contains("postal_code")) {
			if (registrationData.getPostal_code() != null) {
				user.setPostalCode(registrationData.getPostal_code());
			}
		}

		// Country
		if (fields.contains("country")) {
			if (registrationData.getCountry() != null) {
				user.setCountry(registrationData.getCountry());
			}
		}

		// Identity card number
		if (fields.contains("identity_card_number")) {
			if (registrationData.getIdentity_card_number() != null) {
				user.setIdentityCardNumber(registrationData.getIdentity_card_number());
			}
		}

		// Identity card issue date
		if (fields.contains("identity_card_issue_date")) {
			if (registrationData.getIdentity_card_issue_date() != null) {
				user.setIdentityCardIssueDate(registrationData.getIdentity_card_issue_date());
			}
		}

		// Identity card expiration date
		if (fields.contains("identity_card_expiration_date")) {
			if (registrationData.getIdentity_card_expiration_date() != null) {
				user.setIdentityCardExpirationDate(registrationData.getIdentity_card_expiration_date());
			}
		}

		// Birth date
		if (fields.contains("birth_date")) {
			if (registrationData.getBirth_date() != null) {
				user.setBirthDate(registrationData.getBirth_date());
			}
		}

		// Identity card scans
		if (fields.contains("identity_card_scans")) {
			if (registrationData.getIdentity_card_scans() != null) {
				user.setIdentityCardScans(new HashSet<String>(registrationData.getIdentity_card_scans()));
			}
		}

		// Address confirmation scans
		if (fields.contains("address_confirmation_scans")) {
			if (registrationData.getAddress_confirmation_scans() != null) {
				user.setAddressConfirmationScans(new HashSet<String>(registrationData.getAddress_confirmation_scans()));
			}
		}

		// Marketing agreement
		if (fields.contains("marketing_agreement")) {
			if (registrationData.getMarketing_agreement() != null) {
				user.setMarketingAgreement(registrationData.getMarketing_agreement());
			}
		}

		// 2FA method
		if (fields.contains("twofa_method")) {
			if (registrationData.getTwofa_method() != null) {
				user.setTwoFactorMethod(registrationData.getTwofa_method());

				if (registrationData.getTwofa_method().equals("none")) {

					// Remove additional 2FA permissions
					user.getTwoFactorRequiredPermissions().removeAll(this.getAdditionalTwoFactorAuthRequiredPermissions());
				} else {

					// Add additional 2FA permissions
					user.getTwoFactorRequiredPermissions().addAll(this.getAdditionalTwoFactorAuthRequiredPermissions());
				}
			}
		}

		// Notification sound
		if (fields.contains("notification_sound")) {
			if (registrationData.getNotification_sound() != null) {
				user.setNotificationSound(registrationData.getNotification_sound());
			}
		}

		// Phone number
		if (fields.contains("phone_number")) {
			if (registrationData.getPhone_number() != null) {
				user.setPhoneNumber(registrationData.getPhone_number());
			}
		}
	}


	public Boolean checkFieldsRequire2fa(RegistrationStep step, RegistrationData registrationData) {

		Set<String> fields = this.getFieldsByStep(step);
		Set<String> filedsWith2faRequired = this.getTwoFactorAuthRequiredFields();

		for(String field : fields) {
			if (!registrationData.isFieldNull(field) && filedsWith2faRequired.contains(field)) {
				return true;
			}
		}

		return false;
	}


	@Override
	public String toString() {
		return "[Config id: "+this.appId+" loginVia: "+this.loginVia+" requireLogin: "+this.requireLogin+" ]";
	}

	//GETTERS AND SETTERS

	public UUID getAppId() {
		return appId;
	}

	public void setAppId(UUID appId) {
		this.appId = appId;
	}

	public LoginVia getLoginVia() {
		return loginVia;
	}

	public void setLoginVia(LoginVia loginVia) {
		this.loginVia = loginVia;
	}

	public Boolean getRequireLogin() {
		return requireLogin;
	}

	public void setRequireLogin(Boolean requireLogin) {
		this.requireLogin = requireLogin;
	}

	public int getLoginMinLength() {
		return loginMinLength;
	}

	public void setLoginMinLength(int loginMinLength) {
		this.loginMinLength = loginMinLength;
	}

	public int getPasswordMinLength() {
		return passwordMinLength;
	}

	public void setPasswordMinLength(int passwordMinLength) {
		this.passwordMinLength = passwordMinLength;
	}

	public Map<Language, Template> getRegistrationEmailTemplates() {
		return registrationEmailTemplates;
	}

	public void setRegistrationEmailTemplates(Map<Language, Template> registrationEmailTemplates) {
		this.registrationEmailTemplates = registrationEmailTemplates;
	}

	public Boolean getAllowMultiLogin() {
		return allowMultiLogin;
	}

	public void setAllowMultiLogin(Boolean allowMultiLogin) {
		this.allowMultiLogin = allowMultiLogin;
	}

	public Integer getTokenValidSeconds() {
		return tokenValidSeconds;
	}

	public void setTokenValidSeconds(Integer tokenValidSeconds) {
		this.tokenValidSeconds = tokenValidSeconds;
	}

	public Set<String> getDefaultPermissions() {
		return defaultPermissions;
	}

	public void setDefaultPermissions(Set<String> defaultPermissions) {
		this.defaultPermissions = defaultPermissions;
	}

	public Set<String> getPermissionsAfterEmailConfirmation() {
		return permissionsAfterEmailConfirmation;
	}

	public void setPermissionsAfterEmailConfirmation(Set<String> permissionsAfterEmailConfirmation) {
		this.permissionsAfterEmailConfirmation = permissionsAfterEmailConfirmation;
	}

	public Set<String> getAllowedDomains() {
		return allowedDomains;
	}

	public void setAllowedDomains(Set<String> allowedDomains) {
		this.allowedDomains = allowedDomains;
	}

	public Boolean getAutoLoginAfterRegistration() {
		return autoLoginAfterRegistration;
	}

	public void setAutoLoginAfterRegistration(Boolean autoLoginAfterRegistration) {
		this.autoLoginAfterRegistration = autoLoginAfterRegistration;
	}

	public Set<String> getAllowedAccountTypes() {
		return allowedAccountTypes;
	}

	public void setAllowedAccountTypes(Set<String> allowedAccountTypes) {
		this.allowedAccountTypes = allowedAccountTypes;
	}

	public static Logger getLog() {
		return log;
	}

	public Set<String> getRegistrationFields() {
		return registrationFields;
	}

	public void setRegistrationFields(Set<String> registrationFields) {
		this.registrationFields = registrationFields;
	}

	public Set<String> getEmailVerificationFields() {
		return emailVerificationFields;
	}

	public void setEmailVerificationFields(Set<String> emailVerificationFields) {
		this.emailVerificationFields = emailVerificationFields;
	}

	public Set<String> getAccountConfirmationFields() {
		return accountConfirmationFields;
	}

	public void setAccountConfirmationFields(Set<String> accountConfirmationFields) {
		this.accountConfirmationFields = accountConfirmationFields;
	}

	public Set<String> getPermissionsAfterAccountConfirmation() {
		return permissionsAfterAccountConfirmation;
	}

	public void setPermissionsAfterAccountConfirmation(Set<String> permissionsAfterAccountConfirmation) {
		this.permissionsAfterAccountConfirmation = permissionsAfterAccountConfirmation;
	}

	public Set<String> getAccountSettingsFields() {
		return accountSettingsFields;
	}

	public Set<String> getTwoFactorAuthRequiredFields() {
		return twoFactorAuthRequiredFields;
	}

	public void setTwoFactorAuthRequiredFields(Set<String> twoFactorAuthRequiredFields) {
		this.twoFactorAuthRequiredFields = twoFactorAuthRequiredFields;
	}

	public void setAccountSettingsFields(Set<String> accountSettingsFields) {
		this.accountSettingsFields = accountSettingsFields;
	}

	public Set<String> getDefaultTwoFactorAuthRequiredPermissions() {
		return defaultTwoFactorAuthRequiredPermissions;
	}

	public void setDefaultTwoFactorAuthRequiredPermissions(Set<String> defaultTwoFactorAuthRequiredPermissions) {
		this.defaultTwoFactorAuthRequiredPermissions = defaultTwoFactorAuthRequiredPermissions;
	}

	public Set<String> getAdditionalTwoFactorAuthRequiredPermissions() {
		return additionalTwoFactorAuthRequiredPermissions;
	}

	public void setAdditionalTwoFactorAuthRequiredPermissions(Set<String> additionalTwoFactorAuthRequiredPermissions) {
		this.additionalTwoFactorAuthRequiredPermissions = additionalTwoFactorAuthRequiredPermissions;
	}

	public Map<Language, Template> getResetPasswordEmailTemplates() {
		return resetPasswordEmailTemplates;
	}

	public void setResetPasswordEmailTemplates(Map<Language, Template> resetPasswordEmailTemplates) {
		this.resetPasswordEmailTemplates = resetPasswordEmailTemplates;
	}
}

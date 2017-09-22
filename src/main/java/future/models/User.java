package future.models;

import future.models.data.Language;
import future.models.data.Users;

import java.util.Set;
import java.util.UUID;

public class User {

	public UUID id;
	public String email;
	public String login;
	public Set<String> permissions;
	public Language language;
	public Boolean emailVerified;
	public Boolean accountConfirmed;
	public String twoFactorAuthMethod;
	public Set<String> twoFactorRequiredPermissions;

	public User(Users user) {
		this.id = user.getUserId();
		this.email = user.getEmail();
		this.login = user.getLogin();
		this.emailVerified = user.getEmailVerified();
		this.accountConfirmed = user.getAccountConfirmed();
		this.language = user.getLanguage();
		this.permissions = user.getPermissions();
		this.twoFactorAuthMethod = user.getTwoFactorMethod();
		this.twoFactorRequiredPermissions = user.getTwoFactorRequiredPermissions();
	}

	//GETTERS AND SETTERS

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

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

	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
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

	public Boolean getAccountConfirmed() {
		return accountConfirmed;
	}

	public void setAccountConfirmed(Boolean accountConfirmed) {
		this.accountConfirmed = accountConfirmed;
	}

	public String getTwoFactorAuthMethod() {
		return twoFactorAuthMethod;
	}

	public void setTwoFactorAuthMethod(String twoFactorAuthMethod) {
		this.twoFactorAuthMethod = twoFactorAuthMethod;
	}

	public Set<String> getTwoFactorRequiredPermissions() {
		return twoFactorRequiredPermissions;
	}

	public void setTwoFactorRequiredPermissions(Set<String> twoFactorRequiredPermissions) {
		this.twoFactorRequiredPermissions = twoFactorRequiredPermissions;
	}
}

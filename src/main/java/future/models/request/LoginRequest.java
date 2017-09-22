package future.models.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Login")
public class LoginRequest {

	@ApiModelProperty(required = true, notes = "user's email")
	public String email;

	@ApiModelProperty(required = true, notes = "user's login", position = 1)
	public String login;

	@ApiModelProperty(required = true, notes = "user's password", position = 2)
	public String password;

	@ApiModelProperty(required = true, notes = "two factor authentication code", position = 3)
	public String twoFactorCode;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTwoFactorCode() {
		return twoFactorCode;
	}

	public void setTwoFactorCode(String twoFactorCode) {
		this.twoFactorCode = twoFactorCode;
	}
}

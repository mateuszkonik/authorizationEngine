package future.models.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Reset")
public class ResetRequest{

	@ApiModelProperty(required = true, notes = "code used to reset the password")
	private String code;

	@ApiModelProperty(required = true, notes = "two factor authentication", position = 1)
	private String twoFactorCode;

	@ApiModelProperty(required = true, notes = "user's password", position = 2)
	private String password;

	//GETTERS AND SETTERS

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTwoFactorCode() {
		return twoFactorCode;
	}

	public void setTwoFactorCode(String twoFactorCode) {
		this.twoFactorCode = twoFactorCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

package future.models.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Two Factor Authentication")
public class TwoFactorAuthenticationRequest {

	@ApiModelProperty(required = true, notes = "two factor authentication code")
	private String code;

	//GETTERS AND SETTERS

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}

package com.models.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Two Factor Authentication")
public class TwoFactorAuthenticationRequest {

	@ApiModelProperty(required = true, notes = "two factor authentication code")
	public String code;
}

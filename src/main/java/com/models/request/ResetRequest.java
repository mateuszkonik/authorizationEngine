package com.models.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Reset")
public class ResetRequest{

	@ApiModelProperty(required = true, notes = "code used to reset the password")
	public String code;

	@ApiModelProperty(required = true, notes = "two factor authentication", position = 1)
	public String twoFactorCode;

	@ApiModelProperty(required = true, notes = "user's password", position = 2)
	public String password;
}

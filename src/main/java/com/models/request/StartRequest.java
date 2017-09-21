package com.models.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Start")
public class StartRequest {

	@ApiModelProperty(required = true, notes = "user's email")
	public String email;

	@ApiModelProperty(required = true, notes = "user's login", position = 1)
	public String login;

	@ApiModelProperty(required = true, notes = "confirmation url", position = 2)
	public String confirmation_url;
}

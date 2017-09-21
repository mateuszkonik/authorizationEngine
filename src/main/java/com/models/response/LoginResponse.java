package com.models.response;

import io.swagger.annotations.ApiModelProperty;

import java.util.UUID;

public class LoginResponse {

	@ApiModelProperty(required = true, notes = "date the token expires")
	public String expirationDate;

	@ApiModelProperty(required = true, notes = "time in seconds until the token expires", position = 1)
	public Integer expirationInSeconds;

	@ApiModelProperty(required = true, notes = "user's email", position = 2)
	public Boolean valid = false;

	@ApiModelProperty(required = true, notes = "user's data", position = 3)
	public Object user;

	@ApiModelProperty(required = true, notes = "authentication token", position = 4)
	public UUID token;
}

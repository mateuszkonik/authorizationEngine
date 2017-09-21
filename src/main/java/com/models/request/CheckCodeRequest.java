package com.models.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Check Code")
public class CheckCodeRequest {

	@ApiModelProperty(required = true, notes = "code")
	public String code;
}

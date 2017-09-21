package com.models.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "Response")
public class DefaultResponse {

	@ApiModelProperty(required = true, notes = "status of the response")
	public String status;

	@ApiModelProperty(required = true, notes = "list of messages", position = 1)
	public List<String> messages;
}

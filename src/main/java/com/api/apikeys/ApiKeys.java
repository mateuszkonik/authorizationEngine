package com.api.apikeys;

import com.response.StatusResponse;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ApiKeys {

	@RequestMapping(value = "/apikeys", method = GET)
	public ResponseEntity getApiKeysAction(
			@RequestHeader @ApiParam(value = "user authentication token", required = true) UUID authorization){
		return StatusResponse.success(OK);
	}

	@RequestMapping(value = "/apikeys", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity createApiKeyAction(
			@RequestHeader @ApiParam(value = "user authentication token", required = true) UUID authorization,
			@RequestBody String body){
		return StatusResponse.success(OK);
	}

	@RequestMapping(value = "/apikeys/delete/{public_key}", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity deleteApiKeyAction(
			@PathVariable @ApiParam(value = "api action public key") UUID public_key,
			@RequestHeader @ApiParam(value = "user authentication token", required = true) UUID authorization){
		return StatusResponse.success(OK);
	}
}

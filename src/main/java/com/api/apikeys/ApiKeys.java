package com.api.apikeys;

import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ApiKeys {

	@RequestMapping(value = "/apikeys", method = GET)
	public ResponseEntity getApiKeysAction(
			@RequestHeader UUID authorization){
		return new ResponseEntity(HttpStatus.OK);
	}

	@RequestMapping(value = "/apikeys", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity createApiKeyAction(
			@RequestHeader UUID authorization,
			@RequestBody String body){
		return new ResponseEntity(HttpStatus.OK);
	}

	@RequestMapping(value = "/apikeys/delete/{public_key}", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity deleteApiKeyAction(
			@PathVariable @ApiParam(value = "api action public key") UUID public_key,
			@RequestHeader UUID authorization){
		return new ResponseEntity(HttpStatus.OK);
	}
}

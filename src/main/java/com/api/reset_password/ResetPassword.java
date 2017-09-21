package com.api.reset_password;

import com.models.ResetRequest;
import com.models.StartRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ResetPassword {

	@ApiOperation(value = "resetPasswordStart", notes = "This API call starts the process of resetting user password.", response = StartRequest.class)
	@RequestMapping(value = "/reset-password/start", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity resetPassword(
			@RequestBody String body) {
		return new ResponseEntity(HttpStatus.OK);
	}

	@RequestMapping(value = "/reset-password/check-code", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity checkCode(
			@RequestBody String body) {
		return new ResponseEntity(HttpStatus.OK);
	}

	@ApiOperation(value = "resetPasswordReset", notes = "This API call finishes the process of resetting user password.", response = ResetRequest.class)
	@RequestMapping(value = "/reset-password/reset", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity checkCode(
			@RequestBody String body,
			@RequestHeader(name="AuthId", required = false) UUID authId) {
		return new ResponseEntity(HttpStatus.OK);
	}
}

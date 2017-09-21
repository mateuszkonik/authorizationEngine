package com.api.reset_password;

import com.models.request.CheckCodeRequest;
import com.models.request.ResetRequest;
import com.models.request.StartRequest;
import com.models.response.DefaultResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ResetPassword {

	@ApiOperation(value = "resetPasswordStart", response = DefaultResponse.class, notes = "This API call starts the process of resetting user password.")
	@RequestMapping(value = "/reset-password/start", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity resetPassword(@RequestBody StartRequest body) {
		return new ResponseEntity(HttpStatus.OK);
	}

	@ApiOperation(value = "checkCode", response = DefaultResponse.class)
	@RequestMapping(value = "/reset-password/check-code", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity checkCode(@RequestBody CheckCodeRequest body) {
		return new ResponseEntity(HttpStatus.OK);
	}

	@ApiOperation(value = "resetPasswordReset", response = DefaultResponse.class, notes = "This API call finishes the process of resetting user password.")
	@RequestMapping(value = "/reset-password/reset", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity checkCode(
			@RequestHeader(name="AuthId", required = false) UUID authId,
			@RequestBody ResetRequest body) {
		return new ResponseEntity(HttpStatus.OK);
	}
}

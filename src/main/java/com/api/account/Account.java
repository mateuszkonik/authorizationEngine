package com.api.account;

import com.models.request.AccountConfirmationRequest;
import com.models.request.AccountSettingsRequest;
import com.models.request.LoginRequest;
import com.models.response.AccountConfirmationResponse;
import com.models.response.AccountSettingsResponse;
import com.models.response.DefaultResponse;
import com.models.response.LoginResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class Account {

	@ApiOperation(value = "getAccountSettings", response = AccountSettingsResponse.class, notes = "This API call returns user account settings")
	@RequestMapping(value = "/account-settings", method = GET)
	public ResponseEntity getAccountSettings(
			@RequestHeader @ApiParam(value = "user authentication token", required = true) UUID authorization,
			@RequestHeader(required = false) @ApiParam(value = "two factor authorization token") UUID twoFactorAuthToken) {
		return new ResponseEntity(HttpStatus.OK);
	}

	@ApiOperation(value = "setAccountSettings", response = AccountSettingsResponse.class, notes = "This API call sets user account settings")
	@RequestMapping(value = "/account-settings", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity setAccountSettings(
			@RequestHeader @ApiParam(value = "user authentication token", required = true) UUID authorization,
			@RequestHeader(required = false) @ApiParam(value = "two factor authorization token") UUID twoFactorAuthToken,
			@RequestBody AccountSettingsRequest body) {
		return new ResponseEntity(HttpStatus.OK);
	}

	@ApiOperation(value = "login", response = LoginResponse.class, notes = "This API call logs in the user, and returns a token necessary for executing all other actions by \"authorization\" header.")
	@RequestMapping(value = "/login", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity login(
			@RequestHeader(name="AuthId", required = false) UUID authId,
			@RequestHeader(name="User-Agent", required = false) String userAgent,
			@RequestHeader(name="remote-ip", required = false) String remoteIP,
			@RequestBody LoginRequest body) {
		return new ResponseEntity(HttpStatus.OK);
	}

	@ApiOperation(value = "logout", response = DefaultResponse.class, notes = "This API call logs the user out and the token obtained by logging in, is deactivated.")
	@RequestMapping(value = "/user", method = DELETE)
	public ResponseEntity logout(@RequestHeader(value = "Authorization") UUID authorization) {
		return new ResponseEntity(HttpStatus.OK);
	}

	@ApiOperation(value = "accountConfirmation", response = AccountConfirmationResponse.class, notes = "This API call changes the account state to confirmed.")
	@RequestMapping(value = "/account-confirmation", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity accountConfirmation(
			@RequestHeader @ApiParam(value = "user authentication token", required = true) UUID authorization,
			@RequestBody AccountConfirmationRequest body) {
		return new ResponseEntity(HttpStatus.OK);
	}
}

package com.api.account;

import com.models.AccountSettingsResponse;
import com.models.TokenResponse;
import com.response.StatusResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class Account {

	@ApiOperation(value = "getAccountSettings", notes = "This API call returns user account settings", response = AccountSettingsResponse.class)
	@RequestMapping(value = "/account-settings", method = GET)
	public ResponseEntity getAccountSettings(
			@RequestHeader UUID authorization,
			@RequestHeader(required = false) @ApiParam(value = "two factor authorization token") UUID twoFactorAuthToken) {
		return new ResponseEntity(HttpStatus.OK);
	}

	@ApiOperation(value = "setAccountSettings", notes = "This API call sets user account settings", response = AccountSettingsResponse.class)
	@RequestMapping(value = "/account-settings", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity setAccountSettings(
			@RequestHeader UUID authorization,
			@RequestHeader(required = false) @ApiParam(value = "two factor authorization token") UUID twoFactorAuthToken,
			@RequestBody @ApiParam String body) {
		return new ResponseEntity(HttpStatus.OK);
	}

	@ApiOperation(value = "login", notes = "This API call logs in the user, and returns a token necessary for executing all other actions by \"authorization\" header.", response = TokenResponse.class)
	@RequestMapping( value = "/login", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity login(
			@RequestHeader(name="AuthId", required = false) UUID authId,
			@RequestHeader(name="User-Agent", required = false) String userAgent,
			@RequestHeader(name="remote-ip", required = false) String remoteIP,
			@RequestBody String body) {
		return new ResponseEntity(HttpStatus.OK);
	}

	@ApiOperation(value = "logout", notes = "This API call logs the user out and the token obtained by logging in is no longer active.")
	@RequestMapping(value = "/user", method = DELETE)
	public ResponseEntity logout(
			@RequestHeader(value="Authorization") UUID authorization) {
		return new ResponseEntity(HttpStatus.OK);
	}

	@ApiOperation(value = "accountConfirmation", notes = "This API call changes the account state to confirmed.")
	@RequestMapping(value = "/account-confirmation", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity accountConfirmation(
			@RequestHeader UUID authorization,
			@RequestBody String body){
		return new ResponseEntity(HttpStatus.OK);
	}
}

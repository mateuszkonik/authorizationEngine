package future.api.account;

import future.response.StatusResponse;
import future.models.request.AccountConfirmationRequest;
import future.models.request.AccountSettingsRequest;
import future.models.request.LoginRequest;
import future.models.response.AccountConfirmationResponse;
import future.models.response.AccountSettingsResponse;
import future.models.response.DefaultResponse;
import future.models.response.LoginResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

import static future.Main.APP_ID;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class Account {

	private GetAccountSettings getAccountSettings = new GetAccountSettings();
	private SetAccountSettings setAccountSettings = new SetAccountSettings();
	private Login login = new Login();
	private Logout logout = new Logout();
	private AccountConfirmation accountConfirmation = new AccountConfirmation();

	@ApiOperation(value = "getAccountSettings", response = AccountSettingsResponse.class, notes = "This API call returns user account settings")
	@RequestMapping(value = "/account-settings", method = GET)
	public StatusResponse getAccountSettings(@RequestHeader @ApiParam(value = "user authentication token", required = true) UUID authorization) {
		return getAccountSettings.respond(authorization);
	}

	@ApiOperation(value = "setAccountSettings", response = AccountSettingsResponse.class, notes = "This API call sets user account settings")
	@RequestMapping(value = "/account-settings", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public StatusResponse setAccountSettings(
			@RequestHeader @ApiParam(value = "user authentication token", required = true) UUID authorization,
			@RequestHeader(required = false) @ApiParam(value = "two factor authorization token") UUID twoFactorAuthToken,
			@RequestBody AccountSettingsRequest body) {
		return setAccountSettings.respond(authorization, twoFactorAuthToken, body);
	}

	@ApiOperation(value = "login", response = LoginResponse.class, notes = "This API call logs in the user, and returns a token necessary for executing all other actions by \"authorization\" header.")
	@RequestMapping(value = "/login", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public StatusResponse login(
			@RequestHeader(name = "AuthId", required = false) UUID authId,
			@RequestHeader(name = "User-Agent", required = false) String userAgent,
			@RequestHeader(name = "remote-ip", required = false) String remoteIP,
			@RequestBody LoginRequest body,
			@ApiParam(hidden = true) HttpServletRequest request) {
		return login.respond(authId, userAgent, remoteIP, body, request);
	}

	@ApiOperation(value = "logout", response = DefaultResponse.class, notes = "This API call logs the user out and the token obtained by logging in, is deactivated.")
	@RequestMapping(value = "/user", method = DELETE)
	public StatusResponse logout(@RequestHeader(value = "authorization") UUID authorization) {
		return logout.respond(authorization);
	}

	@ApiOperation(value = "accountConfirmation", response = AccountConfirmationResponse.class, notes = "This API call changes the account state to confirmed.")
	@RequestMapping(value = "/account-confirmation", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public StatusResponse accountConfirmation(
			@RequestHeader @ApiParam(value = "user authentication token", required = true) UUID authorization,
			@RequestBody AccountConfirmationRequest body) {
		return accountConfirmation.respond(authorization, body);
	}
}

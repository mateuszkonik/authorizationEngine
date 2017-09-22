package future.api.reset_password;

import future.response.StatusResponse;
import future.models.request.CheckCodeRequest;
import future.models.request.ResetRequest;
import future.models.request.StartRequest;
import future.models.response.DefaultResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ResetPassword {

	private final String permission = "PASSWORD_RESET";

	private ResetPasswordStart resetPasswordStart = new ResetPasswordStart(permission);
	private CheckCode checkCode = new CheckCode(permission);
	private ResetPasswordReset resetPasswordReset = new ResetPasswordReset(permission);

	@ApiOperation(value = "resetPasswordStart", response = DefaultResponse.class, notes = "This API call starts the process of resetting user password.")
	@RequestMapping(value = "/reset-password/start", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public StatusResponse resetPassword(@RequestBody StartRequest body) {
		return resetPasswordStart.respond(body);
	}

	@ApiOperation(value = "checkCode", response = DefaultResponse.class)
	@RequestMapping(value = "/reset-password/check-code", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public StatusResponse checkCode(@RequestBody CheckCodeRequest body) {
		return checkCode.respond(body);
	}

	@ApiOperation(value = "resetPasswordReset", response = DefaultResponse.class, notes = "This API call finishes the process of resetting user password.")
	@RequestMapping(value = "/reset-password/reset", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public StatusResponse resetPasswordReset(
			@RequestHeader(name = "AuthId", required = false) UUID authId,
			@RequestBody ResetRequest body) {
		return resetPasswordReset.respond(authId, body);
	}
}

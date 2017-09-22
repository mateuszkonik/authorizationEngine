package future.api.two_factor_authentication;

import future.response.StatusResponse;
import future.models.request.TwoFactorAuthenticationRequest;
import future.models.response.DefaultResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class TwoFactorAuthentication {

	private GenerateToken generateToken = new GenerateToken();

	@ApiOperation(value = "generateToken", response = DefaultResponse.class, notes = "This API call allows you to generate a new two factor authentication token.")
	@RequestMapping(value = "/2fauth/code/{auth_id}", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public StatusResponse generateToken(
			@PathVariable UUID auth_id,
			@RequestBody TwoFactorAuthenticationRequest body) {
		return generateToken.respond(auth_id, body);
	}
}

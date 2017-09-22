package future.api.apikeys;


import future.models.request.ApiKeyCreationRequest;
import future.response.StatusResponse;
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

	private GetApiKeyAction getApiKeyAction = new GetApiKeyAction();
	private CreateApiKeyAction createApiKeyAction = new CreateApiKeyAction();
	private DeleteApiKeyAction deleteApiKeyAction = new DeleteApiKeyAction();

	@RequestMapping(value = "/apikeys", method = GET)
	public StatusResponse getApiKeysAction(@RequestHeader @ApiParam(value = "user authentication token", required = true) UUID authorization) {
		return getApiKeyAction.respond(authorization);
	}

	@RequestMapping(value = "/apikeys", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public StatusResponse createApiKeyAction(
			@RequestHeader @ApiParam(value = "user authentication token", required = true) UUID authorization,
			@RequestBody ApiKeyCreationRequest body) {
		return createApiKeyAction.respond(authorization, body);
	}

	@RequestMapping(value = "/apikeys/delete/{public_key}", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public StatusResponse deleteApiKeyAction(
			@PathVariable @ApiParam(value = "api action public key") UUID public_key,
			@RequestHeader @ApiParam(value = "user authentication token", required = true) UUID authorization) {
		return deleteApiKeyAction.respond(public_key, authorization);
	}
}

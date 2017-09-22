package future.api.confirmation;


import future.models.request.ConfirmRegistrationRequest;
import future.response.StatusResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

public class Confirmation {

	private Check check = new Check();
	private ConfirmRegistration confirmRegistration = new ConfirmRegistration();

	@RequestMapping(value = "/confirmation/{code}", method = GET)
	public StatusResponse check(@PathVariable String code) {
		return check.respond(code);
	}

	@RequestMapping(value = "/confirmation/{code}", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public StatusResponse confirmRegistration(
			@PathVariable String code,
			@RequestBody ConfirmRegistrationRequest body) {
		return confirmRegistration.respond(code, body);
	}
}

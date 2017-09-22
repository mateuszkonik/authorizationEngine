package future.api.register;

import future.models.request.RegisterRequest;
import future.models.response.RegisterResponse;
import future.response.StatusResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class Register {

	private DoRegister doRegister = new DoRegister();

	@ApiOperation(value = "register", response = RegisterResponse.class, notes = "This API call creates a new user.")
	@RequestMapping(value = "/users", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public StatusResponse register(@RequestBody RegisterRequest body) {
		return doRegister.respond(body);
	}
}

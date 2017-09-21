package com.api.register;

import com.models.request.RegisterRequest;
import com.models.response.RegisterResponse;
import com.response.StatusResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.response.SuccessMessage.CHECK_YOUR_EMAIL_TO_CONFIRM_ACCOUNT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class Register {

	@ApiOperation(value = "register", response = RegisterResponse.class, notes = "This API call creates a new user.")
	@RequestMapping(value = "/users", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity register(@RequestBody RegisterRequest body) {
		return StatusResponse.success(CHECK_YOUR_EMAIL_TO_CONFIRM_ACCOUNT, new RegisterRequest());
	}
}

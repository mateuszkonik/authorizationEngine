package com.api.register;

import com.models.RegisterBody;
import com.response.Confirm;
import com.response.StatusResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class Register {

	@RequestMapping(value = "/users", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity register(@RequestBody RegisterBody body) {
		return StatusResponse.success(Confirm.CHECK_YOUR_EMAIL_TO_CONFIRM_ACCOUNT, new RegisterBody());
	}
}

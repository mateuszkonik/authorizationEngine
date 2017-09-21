package com.api.twoFactorAuth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class TwoFactorAuth {

	@RequestMapping(value = "/2fauth/code/{auth_id}", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity generateToken(
			@PathVariable UUID auth_id,
			@RequestBody String body) {
		return new ResponseEntity(HttpStatus.OK);
	}
}

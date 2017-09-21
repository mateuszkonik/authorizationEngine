package com.api.confirmation;

import com.response.StatusResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

public class Confirmation {

	@RequestMapping(value = "/confirmation/{code}", method = GET)
	public ResponseEntity<?> check(@PathVariable String code) {
		return new ResponseEntity(HttpStatus.OK);
	}

	@RequestMapping(value = "/confirmation/{code}", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<?> confirmRegistration(
			@PathVariable String code,
			@RequestBody String body) {
		return new ResponseEntity(HttpStatus.OK);
	}
}

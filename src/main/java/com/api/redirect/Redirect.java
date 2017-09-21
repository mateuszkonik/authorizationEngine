package com.api.redirect;

import com.response.StatusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

public class Redirect {

	@RequestMapping(value = "/redirect/{code}", method = GET)
	public ResponseEntity redirect(@PathVariable String code) {
		return StatusResponse.success(OK);
	}
}

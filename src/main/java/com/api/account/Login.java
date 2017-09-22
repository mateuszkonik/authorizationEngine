package com.api.account;

import com.models.request.LoginRequest;
import com.response.StatusResponse;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

public class Login {

	public ResponseEntity respond(UUID authId,
	                              String userAgent,
	                              String remoteIP,
	                              LoginRequest body) {
		return StatusResponse.success(OK);
	}
}

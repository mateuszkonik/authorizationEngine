package com.api.account;

import com.response.StatusResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GetAccountSettings {

	public ResponseEntity respond() {
		return StatusResponse.success(HttpStatus.OK);
	}
}

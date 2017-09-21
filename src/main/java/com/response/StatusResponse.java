package com.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class StatusResponse {

	public static <T> ResponseEntity success(Confirm confirm, T body) {
		HttpStatus status = HttpStatus.valueOf(confirm.code);

		return new ResponseEntity(body, status);
	}

	public static ResponseEntity error(ErrorMessage errorMsg) {
		HttpStatus status = HttpStatus.valueOf(errorMsg.code);

		return new ResponseEntity(new Error(errorMsg), status);
	}
}

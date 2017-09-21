package com.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class StatusResponse {

	public static ResponseEntity success(HttpStatus status) {
		return new ResponseEntity<>("{}", status);
	}

	public static <T> ResponseEntity success(HttpStatus status, T body) {
		return new ResponseEntity<>(body, status);
	}

	public static ResponseEntity success(SuccessMessage successMsg) {
		HttpStatus status = HttpStatus.valueOf(successMsg.code);

		return new ResponseEntity<>(status);
	}

	public static <T> ResponseEntity success(SuccessMessage successMsg, T body) {
		HttpStatus status = HttpStatus.valueOf(successMsg.code);

		return new ResponseEntity<>(body, status);
	}

	public static ResponseEntity error(ErrorMessage errorMsg) {
		HttpStatus status = HttpStatus.valueOf(errorMsg.code);

		return new ResponseEntity<>(new Error(errorMsg), status);
	}
}

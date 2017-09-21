package com.response;

public enum SuccessMessage {

	ACCOUNT_EMAIL_CONFIRMED,
	ACCOUNT_CONFIRMED,
	SUCCESSFULLY_CREATED_TOKEN,
	REDIRECTING,
	CHECK_YOUR_EMAIL_TO_CONFIRM_ACCOUNT,
	CHECK_YOUR_EMAIL_TO_RESET_PASSWORD,
	CONFIRMATION_CODE_VALID,
	TOKEN_DESTROYED,
	ACCOUNT_SETTINGS_CHANGED,
	RETURNING_REQUESTED_DATA,
	VALID_PASSWORD_RESET_CODE,
	VALID_PASSWORD_RESET_CODE_REQUIRED_2FA,
	VALID_CREDENTIALS_REQUIRED_2FA;

	public int code;

	SuccessMessage() {
		code = 200;
	}

	SuccessMessage(int code) {
		this.code = code;
	}
}
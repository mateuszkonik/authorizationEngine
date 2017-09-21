package com.models;

import java.util.UUID;

public class TokenResponse {

	public String expirationDate;
	public Integer expirationInSeconds;
	public Boolean valid = false;
	public UUID appId;
	public Object user;
	public UUID token;
}

package com.response;

import java.util.ArrayList;
import java.util.List;

public class Error {

	private String status = "Fail";

	private List<ErrorMessage> messages = new ArrayList<>();

	public Error(ErrorMessage message){
		this.messages.add(message);
	}

	public Error(List<ErrorMessage> messages){
		this.messages = messages;
	}

	public List<ErrorMessage> getMessages() {
		return messages;
	}
}

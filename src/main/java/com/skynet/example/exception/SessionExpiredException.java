package com.skynet.example.exception;

import org.springframework.web.client.HttpClientErrorException;

import com.skynet.example.constants.Constants;

public class SessionExpiredException extends ApplicationRuntimeException {

	private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

	public SessionExpiredException() {
		super();
	}

	public SessionExpiredException(String message) {
		super(message);
	}

	public SessionExpiredException(String message, HttpClientErrorException e) {
		super(message, e);
	}

}

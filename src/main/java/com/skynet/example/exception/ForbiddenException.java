package com.skynet.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.skynet.example.constants.Constants;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends ApplicationRuntimeException {

	private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

	public ForbiddenException() {
		super();
	}

	public ForbiddenException(String message) {
		super(message);
	}

	public ForbiddenException(String message, HttpClientErrorException e) {
		super(message, e);
	}

}

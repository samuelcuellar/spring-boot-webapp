package com.skynet.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.skynet.example.constants.Constants;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends ApplicationRuntimeException {

	private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

	public NotFoundException() {
		super();
	}

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(String message, HttpClientErrorException e) {
		super(message, e);
	}

}

package com.skynet.example.exception;

import com.skynet.example.constants.Constants;

public class ApplicationRuntimeException extends RuntimeException {

	private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

	public ApplicationRuntimeException() {
		super();
	}

	public ApplicationRuntimeException(String message) {
		super(message);
	}

	public ApplicationRuntimeException(String msg, Throwable e) {
		super(msg, e);
	}

	public ApplicationRuntimeException(Throwable e) {
		super(e);
	}
}

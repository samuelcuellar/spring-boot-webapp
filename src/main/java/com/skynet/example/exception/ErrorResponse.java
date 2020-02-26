package com.skynet.example.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ErrorResponse {

	private HttpStatus status;

	private String code;

	private String message;

	private ErrorResponse() {
	}

	ErrorResponse(HttpStatus status) {
		this();
		this.status = status;
		this.code = "UNKNOWN";
	}

	ErrorResponse(HttpStatus status, String code, String message) {
		this();
		this.status = status;
		this.code = code;
		this.message = message;
	}
}

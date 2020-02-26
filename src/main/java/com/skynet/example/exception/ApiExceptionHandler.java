package com.skynet.example.exception;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.skynet.example.constants.Constants;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  public static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

  public ResponseEntity<Object> handleFilterExceptions(Exception ex, HttpServletRequest request) {
    if (ex instanceof SessionExpiredException) {
      return handleExpiredJwt(ex, request);
    } else {
      return handleGenericRuntimeExeption(ex, request);
    }
  }

  @ExceptionHandler(value = BadCredentialsException.class)
  protected ResponseEntity<Object> handleBadCredentials(RuntimeException ex, WebRequest request) {
    return handleError(ex, new ErrorResponse(HttpStatus.FORBIDDEN,
        Constants.BAD_CREDENTIALS_ERROR, Constants.BAD_CREDENTIALS_MESSAGE));
  }

  @ExceptionHandler(value = SessionExpiredException.class)
  protected ResponseEntity<Object> handleExpiredJwt(Exception ex, HttpServletRequest request) {
    return handleError(ex, new ErrorResponse(HttpStatus.FORBIDDEN,
        Constants.SESSION_EXPIRED_ERROR, Constants.SESSION_EXPIRED_MESSAGE));
  }

  @ExceptionHandler(value = NotFoundException.class)
  protected ResponseEntity<Object> handleNotFoundException(Exception ex,
      HttpServletRequest request) {
    String msg;
    if (!StringUtils.isEmpty(ex.getMessage())) {
      msg = ex.getMessage();
    } else {
      msg = Constants.NOT_FOUND_MESSAGE;
    }
    return handleError(ex,
        new ErrorResponse(HttpStatus.NOT_FOUND, Constants.NOT_FOUND_ERROR, msg));
  }

  @ExceptionHandler(value = ApplicationRuntimeException.class)
  protected ResponseEntity<Object> handleGenericRuntimeExeption(Exception ex,
      HttpServletRequest request) {
    return handleError(ex, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
        Constants.GENERIC_ERROR, Constants.GENERIC_MESSAGE));
  }

  private ResponseEntity<Object> handleError(Exception ex, ErrorResponse errorResponse) {
    logger.error("Handling error: {}", errorResponse, ex);
    return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
  }
}

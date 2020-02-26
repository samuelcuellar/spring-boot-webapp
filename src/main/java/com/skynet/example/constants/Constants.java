package com.skynet.example.constants;

public class Constants {

  public static final long SERIAL_VERSION_UID = 204l;

  public static final String EMPTY_STRING = "";
  public static final int RANDOM_PASSWORD_LENGHT = 8;
  public static final int TOKEN_EXPIRATION = 60 * 24;
  
  public static final String ROLE_ADMIN = "ADMIN";
  public static final String ROLE_USER = "USER";

  /*** Common errors ***/

  public static final String GENERIC_ERROR = "100";
  public static final String GENERIC_MESSAGE = "Runtime error";

  public static final String NOT_FOUND_ERROR = "101";
  public static final String NOT_FOUND_MESSAGE = "Not found";


  /*** Authentication errors ***/

  public static final String BAD_CREDENTIALS_ERROR = "110";
  public static final String BAD_CREDENTIALS_MESSAGE = "Bad credentials";

  public static final String SESSION_EXPIRED_ERROR = "111";
  public static final String SESSION_EXPIRED_MESSAGE = "Session expired";

}

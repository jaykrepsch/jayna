package me.jayna.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

  public static final String ADMIN = "ROLE_ADMIN";

  public static final String ANONYMOUS = "ROLE_ANONYMOUS";

  public static final String USER = "ROLE_USER";

  public static final String ROLE_USER_LIGHT = "ROLE_USER_LIGHT";
  public static final String ROLE_USER_BASIC = "ROLE_USER_BASIC";
  public static final String ROLE_USER_PRO = "ROLE_USER_PRO";

  public static final String ROLE_BUSINESS_LIGHT = "ROLE_BUSINESS_LIGHT";
  public static final String ROLE_BUSINESS_BASIC = "ROLE_BUSINESS_BASIC";
  public static final String ROLE_BUSINESS_PRO = "ROLE_BUSINESS_PRO";

  private AuthoritiesConstants() {
  }
}

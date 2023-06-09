package at.schulgong.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote This class handles the exceptions thrown by the JwtAuthenticationFilter
 * @since June 2023
 */
public class JwtAuthenticationException extends AuthenticationException {
  /**
   * Constructor
   *
   * @param msg Message to be displayed
   */
  public JwtAuthenticationException(String msg) {
    super(msg);
  }
}

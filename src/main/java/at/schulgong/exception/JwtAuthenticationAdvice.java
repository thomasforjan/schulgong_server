package at.schulgong.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote This class handles the exceptions thrown by the JwtAuthenticationFilter
 * @since June 2023
 */
@ControllerAdvice
public class JwtAuthenticationAdvice {

  /**
   * Handler, to handle Request, if entry is not found
   *
   * @param ex Custom exception
   * @return Errormessage
   */
  @ResponseBody
  @ExceptionHandler(JwtAuthenticationException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String JwtAuthenticationException(EntityNotFoundException ex) {
    return ex.getMessage();
  }
}


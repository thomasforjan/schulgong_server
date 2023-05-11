package at.schulgong.test;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote practical project
 * @since March 2023
 */
@ControllerAdvice
public class TestNotFoundAdvice {

  /**
   * Method to indicate that the entry was not found
   * @param ex exception
   * @return message of the exception
   */
  @ResponseBody
  @ExceptionHandler(TestNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String sensorNotFoundHandler(TestNotFoundException ex) {
    return ex.getMessage();
  }
}

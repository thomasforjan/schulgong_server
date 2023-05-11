package at.schulgong.test;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote practical project
 * @since March 2023
 */
public class TestNotFoundException extends RuntimeException{

  /**
   *Method to catch errors when a call was not found.
   *
   * @param id of requested entry
   */
  TestNotFoundException(Long id) {
    super("Could not find test " + id);
  }
}

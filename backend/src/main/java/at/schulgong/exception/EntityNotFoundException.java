package at.schulgong.exception;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote Custom exception for error-handling
 * @since April 2023
 */
public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException(long id, String entity) {
    super("Could not find " + entity + " " + id);
  }

}

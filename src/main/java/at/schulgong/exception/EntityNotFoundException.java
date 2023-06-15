package at.schulgong.exception;

/**
 * Custom exception for error-handling
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @since April 2023
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Custom exception
     *
     * @param id id of entry
     * @param entity name of entity (e.g. ringtone)
     */
    public EntityNotFoundException(long id, String entity) {
        super("Could not find " + entity + " " + id);
    }
}

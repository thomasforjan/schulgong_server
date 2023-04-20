package at.schulgong.exception;

public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException(long id, String entity){
    super("Could not find " + entity + " " + id);
  }

}

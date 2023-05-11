package at.schulgong.util;

import lombok.Getter;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @implNote Config-Enum to configure global values
 * @since April 2023
 */
@Getter
public enum Config {

  RINGTONE("ringtones", "", "ringtone"),
  RINGTIME("ringtimes", "", "ringtime"),
  HOLIDAY("holidays", "", "holiday"),
  FILEPATH("", "C:/Users/kralm/Desktop/Audiofiles", "");

  private final String url, path, exception;

  /**
   * Constructor of enum
   *
   * @param url       url for CRUD
   * @param path      path for file saving
   * @param exception name of entity for exception
   */
  Config(String url, String path, String exception) {
    this.url = url;
    this.path = path;
    this.exception = exception;
  }
}

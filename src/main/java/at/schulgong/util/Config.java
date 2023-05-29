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
  SPEAKER_API_SETTINGS_PATH("", "src/main/resources/python/config/speaker_api_settings.json", ""),
  FILEPATH("", "Audiofiles", ""), ANNOUNCEMENT_PATH("", "C:/Users/kralm/Desktop/Audiofiles/Durchsage/Durchsage.mp3", ""),
  ANNOUNCEMENT_PATH_WEBA_FORMAT("", "C:/Users/kralm/Desktop/Audiofiles/Durchsage/Durchsage.weba", ""), SONG("live/music/songs", "", "song"),
  PLAYLIST_PATH("", "C:/Users/kralm/Desktop/Audiofiles/Playlist/", "");

  private final String url;
  private final String path;
  private final String exception;

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

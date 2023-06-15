package at.schulgong.util;

import lombok.Getter;

/**
 * Config-Enum to configure global values
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @since April 2023
 */
@Getter
public enum Config {
    RINGTONE("api/ringtones", "", "ringtone"),
    RINGTIME("api/ringtimes", "", "ringtime"),
    HOLIDAY("api/holidays", "", "holiday"),
    SONG("api/live/music/songs", "", "song"),
    SPEAKER_API_SETTINGS_PATH("", "src/main/resources/python/config/speaker_api_settings.json", ""),
    FILEPATH("", "Audiofiles/Ringtones", ""),
    ANNOUNCEMENT_PATH("", "Audiofiles/Announcements", ""),
    SPEAKER_CONFIG_PATH("", "src/main/resources/python/config/speaker_config.json", ""),
    PLAYLIST_PATH("", "Audiofiles/Playlist/", ""),
    CONFIGURATION_PATH("", "src/main/resources/configuration.json", ""),
    SPEAKER_RINGTONE(
            "http://" + ReadHostIp.readHostIp() + ":8080" + "/api/speaker/play/ringtone/", "", ""),
    SPEAKER_PLAYLIST(
            "http://" + ReadHostIp.readHostIp() + ":8080" + "/api/speaker/play/playlist/", "", ""),
    SPEAKER_ANNOUNCEMENT(
            "http://"
                    + ReadHostIp.readHostIp()
                    + ":8080"
                    + "/api/speaker/play/announcement/Durchsage.mp3",
            "",
            "");

    private final String url;
    private final String path;
    private final String exception;

    /**
     * Constructor of enum
     *
     * @param url url for CRUD
     * @param path path for file saving
     * @param exception name of entity for exception
     */
    Config(String url, String path, String exception) {
        this.url = url;
        this.path = path;
        this.exception = exception;
    }
}

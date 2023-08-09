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
    SPEAKER_API_SETTINGS_PATH("", "/home/schulgong/speaker_api_settings.json", ""),
    ANNOUNCEMENT_PATH("", "shared/Announcements", ""),
    SPEAKER_CONFIG_PATH("", "/home/schulgong/speaker_config.json", ""),
    CONFIGURATION_PATH("", "/home/schulgong/configuration.json", ""),
    SPEAKER_RINGTONE(
            "https://" + ReadHostIp.readHostIp() + ":8080" + "/api/speaker/play/ringtone/", "", ""),
    SPEAKER_PLAYLIST(
            "https://" + ReadHostIp.readHostIp() + ":8080" + "/api/speaker/play/playlist/", "", ""),
    SPEAKER_ANNOUNCEMENT(
            "https://"
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

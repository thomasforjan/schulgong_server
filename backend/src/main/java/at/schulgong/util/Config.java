package at.schulgong.util;

import lombok.Getter;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote Config-Enum to configure global values
 * @since April 2023
 */
@Getter
public enum Config {
    RINGTONE("ringtones", "", "ringtone"),
    HOLIDAY("holidays", "", "holiday"),
    FILEPATH("", "C://Users/phili/OneDrive/Desktop/sonos_music", ""),

    SPEAKER_API_SETTINGS_PATH(
            "",
            "D://01-Studium/Software Engineering und vernetzte Systeme/4."
                + " Semester/Praxisprojekt/schulgong_server/backend/src/main/resources/python/config/speaker_api_settings.json",
            "");
    private final String url, path, exception;

    Config(String url, String path, String exception) {
        this.url = url;
        this.path = path;
        this.exception = exception;
    }
}

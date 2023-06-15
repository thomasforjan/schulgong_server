package at.schulgong.util;

import lombok.Getter;

/**
 * Audio format for the audio files
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @since May 2023
 */
@Getter
public enum AudioFormat {
    MP3("audio/mpeg"),
    M4A("audio/m4a"),
    OGG("application/ogg"),
    WMA("audio/wma"),
    FLAC("audio/flac");

    private final String type;

    AudioFormat(String type) {
        this.type = type;
    }
}

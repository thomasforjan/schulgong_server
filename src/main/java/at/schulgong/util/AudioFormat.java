package at.schulgong.util;

import lombok.Getter;

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

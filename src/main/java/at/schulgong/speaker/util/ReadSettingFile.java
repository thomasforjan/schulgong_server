package at.schulgong.speaker.util;

import at.schulgong.util.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.stream.Collectors;

/**
 * Read speaker settings from config file
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @since May 2023
 */
public class ReadSettingFile {

    /**
     * Method to get settings from the config-file
     *
     * @return settings
     */
    public static Setting getSettingFromConfigFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            Path path = Paths.get(Config.SPEAKER_API_SETTINGS_PATH.getPath());
            return objectMapper.readValue(new File(path.toAbsolutePath().toUri()), Setting.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new Setting.SettingBuilder()
                    .type("python")
                    .executedFilePath("python/config/main_sonos_api.py")
                    .loadRingtimeTime(LocalTime.of(0, 1))
                    .build();
        }
    }
}

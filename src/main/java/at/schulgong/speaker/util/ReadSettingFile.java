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
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote Read speaker settings from config file
 * @since May 2023
 */
public class ReadSettingFile {

    /**
     * Method to get settings from the config-file
     *
     * @return settings
     */
    public static Setting getSettingFromConfigFile2() {
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

    public static Setting getSettingFromConfigFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String contents;
        try {
            Path path = Paths.get(Config.SPEAKER_API_SETTINGS_PATH.getPath());
            try (InputStream inputStream =
                            ReadSettingFile.class
                                    .getClassLoader()
                                    .getResourceAsStream("speaker_api_settings.json");
                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(inputStream))) {
                contents = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
            return objectMapper.readValue(contents, Setting.class);
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

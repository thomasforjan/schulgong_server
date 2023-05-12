package at.schulgong.speaker.util;

import at.schulgong.util.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote Read speaker settings from config file
 * @since Mai 2023
 */
public class ReadSettingFile {

  /**
   * Method to get settings from the config-file
   *
   * @return
   */
  public static Setting getSettingFromConfigFile() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    try {
      return objectMapper.readValue(
        new File(Config.SPEAKER_API_SETTINGS_PATH.getPath()), Setting.class);
    } catch (IOException e) {
      e.printStackTrace();
      Setting setting =
        new Setting.SettingBuilder()
          .type("python")
          .executedFilePath(
            "D://01-Studium/Software Engineering und vernetzte Systeme/4."
              + " Semester/Praxisprojekt/schulgong_server/backend/src/main/resources/python/scripts/main_sonos_api.py")
          .loadRingtimeTime(LocalTime.of(0, 1))
          .build();
      return null;
    }
  }
}
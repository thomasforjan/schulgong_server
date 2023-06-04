package at.schulgong.util;

import at.schulgong.dto.ConfigurationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.minidev.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote Read application configuration from configuration file
 * @since May 2023
 */
public class ReadWriteConfigurationFile {

  /**
   * Method to get configurationDTO from the configiguration-file
   *
   * @param filePath Path from the configuration file
   * @return congigurationDTO
   */
  public static ConfigurationDTO readConfigurationDTOFromConfigFile(String filePath) {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    try {
      Path path = Paths.get(filePath);
      return objectMapper.readValue(new File(path.toAbsolutePath().toUri()), ConfigurationDTO.class);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Method to write configurations into the file
   *
   * @param configurationFilePath Path from the configuration file
   * @param writeConfigurationDTO ConfigurationDTO to write into the file
   * @throws RuntimeException
   */
  public static void writeConfigurationDTOFromConfigFile(String configurationFilePath, ConfigurationDTO writeConfigurationDTO) throws RuntimeException {
    JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put("password", writeConfigurationDTO.getPassword());
      jsonObject.put("ringtimeVolume", writeConfigurationDTO.getRingtimeVolume());
      jsonObject.put("alarmVolume", writeConfigurationDTO.getAlarmVolume());
      jsonObject.put("announcementVolume", writeConfigurationDTO.getAnnouncementVolume());
      jsonObject.put("ringtimeDirectory", writeConfigurationDTO.getRingtimeDirectory());
      jsonObject.put("playlistDirectory", writeConfigurationDTO.getPlaylistDirectory());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    try (FileWriter file = new FileWriter(configurationFilePath))
    {
      file.write(jsonObject.toJSONString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
}

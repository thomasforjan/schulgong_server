package at.schulgong.util;

import at.schulgong.dto.ConfigurationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
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
}

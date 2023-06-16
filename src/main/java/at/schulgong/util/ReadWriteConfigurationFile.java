package at.schulgong.util;

import at.schulgong.dto.ConfigurationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * Read application configuration from configuration file
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
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
    public static void writeConfigurationDTOFromConfigFile(
            String configurationFilePath, ConfigurationDTO writeConfigurationDTO)
            throws RuntimeException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // This will format the output
        try {
            objectMapper.writeValue(new File(configurationFilePath), writeConfigurationDTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

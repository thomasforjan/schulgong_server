package at.schulgong.speaker.util.speakerconfig;

import at.schulgong.util.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Read speaker settings from config file
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @since May 2023
 */
public class ReadSpeakerConfigFile {

    /**
     * Method to get speaker objects from the config-file
     *
     * @return list of speaker objects
     */
    public static SpeakerObjects getSpeakerObjectListFromConfigFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Path path = Paths.get(Config.SPEAKER_CONFIG_PATH.getPath());
        return objectMapper.readValue(
                new File(path.toAbsolutePath().toUri()), SpeakerObjects.class);
    }
}

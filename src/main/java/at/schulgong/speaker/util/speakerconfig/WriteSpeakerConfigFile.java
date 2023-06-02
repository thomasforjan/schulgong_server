package at.schulgong.speaker.util.speakerconfig;


import at.schulgong.util.Config;
import net.minidev.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote Read speaker settings from config file
 * @since May 2023
 */
public class WriteSpeakerConfigFile {

  /**
   * Method to get speaker objects from the config-file
   *
   * @return list of speaker objects
   */
  public static void writeSpeakerObjectListToConfigFile(SpeakerObjects speakerObjects) throws IOException {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("speakerObjects", speakerObjects.getSpeakerObjects());
    FileWriter file = new FileWriter(Config.SPEAKER_CONFIG_PATH.getPath());
    file.write(jsonObject.toString());
    file.close();
  }
}

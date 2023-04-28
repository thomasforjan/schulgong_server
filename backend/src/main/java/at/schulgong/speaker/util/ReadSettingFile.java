package at.schulgong.speaker.util;


import at.schulgong.util.Config;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ReadSettingFile {

  public static Setting getSettingFromConfigFile() {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readValue(new File(Config.SPEAKER_API_SETTINGS_PATH.getPath()), Setting.class);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }



}

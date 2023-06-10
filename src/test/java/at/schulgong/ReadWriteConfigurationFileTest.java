package at.schulgong;

import at.schulgong.dto.ConfigurationDTO;
import at.schulgong.util.ReadWriteConfigurationFile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote practical project
 * @since March 2023
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReadWriteConfigurationFileTest {

  private final String CONFIGURATION_FILE_PATH = Paths.get("src/test/resources/configuration.json").toAbsolutePath().toString();
  private ConfigurationDTO dummyReadConfigurationDTO;
  private ConfigurationDTO dummyWriteConfigurationDTO;

  @BeforeAll
  public void initialize() {
    dummyReadConfigurationDTO = ConfigurationDTO.builder()
      .password("$argon2id$v=19$m=60000,t=10,p=1$VpM7WJo3vSg5J31VLs74GA$e/t1NrAAYKPuDTT9TIgHzMT8zXu6x5mbNLkspVzQjUk")
      .ringtimeVolume(1)
      .alarmVolume(5)
      .announcementVolume(5)
      .ringtimeDirectory("C:/Users/phili/OneDrive/Desktop/sonos_music")
      .playlistDirectory("C:/Users/phili/OneDrive/Desktop/sonos_music/playlist").build();

    dummyWriteConfigurationDTO = ConfigurationDTO.builder()
      .password("5678")
      .ringtimeVolume(6)
      .alarmVolume(15)
      .announcementVolume(25)
      .ringtimeDirectory("C:/Users/phili/OneDrive/Desktop/ringtimes")
      .playlistDirectory("C:/Users/phili/OneDrive/Desktop/playlist").build();
  }

  @AfterEach
  public void writeInitialValuesToFile() {
    ReadWriteConfigurationFile.writeConfigurationDTOFromConfigFile(CONFIGURATION_FILE_PATH, dummyReadConfigurationDTO);
  }


  @Test
  public void testReadConfigurationDTOFromConfigFile() {
    ConfigurationDTO configurationDTO = ReadWriteConfigurationFile.readConfigurationDTOFromConfigFile(CONFIGURATION_FILE_PATH);
    assertEquals(dummyReadConfigurationDTO.getPassword(), configurationDTO.getPassword());
    assertEquals(dummyReadConfigurationDTO.getRingtimeVolume(), configurationDTO.getRingtimeVolume());
    assertEquals(dummyReadConfigurationDTO.getAlarmVolume(), configurationDTO.getAlarmVolume());
    assertEquals(dummyReadConfigurationDTO.getAnnouncementVolume(), configurationDTO.getAnnouncementVolume());
    assertEquals(dummyReadConfigurationDTO.getRingtimeDirectory(), configurationDTO.getRingtimeDirectory());
    assertEquals(dummyReadConfigurationDTO.getPlaylistDirectory(), configurationDTO.getPlaylistDirectory());
  }

  @Test
  public void testWriteConfigurationDTOFromConfigFile() {
    ReadWriteConfigurationFile.writeConfigurationDTOFromConfigFile(CONFIGURATION_FILE_PATH, dummyWriteConfigurationDTO);
    ConfigurationDTO configurationDTO = ReadWriteConfigurationFile.readConfigurationDTOFromConfigFile(CONFIGURATION_FILE_PATH);

    assertEquals(dummyWriteConfigurationDTO.getPassword(), configurationDTO.getPassword());
    assertEquals(dummyWriteConfigurationDTO.getRingtimeVolume(), configurationDTO.getRingtimeVolume());
    assertEquals(dummyWriteConfigurationDTO.getAlarmVolume(), configurationDTO.getAlarmVolume());
    assertEquals(dummyWriteConfigurationDTO.getAnnouncementVolume(), configurationDTO.getAnnouncementVolume());
    assertEquals(dummyWriteConfigurationDTO.getRingtimeDirectory(), configurationDTO.getRingtimeDirectory());
    assertEquals(dummyWriteConfigurationDTO.getPlaylistDirectory(), configurationDTO.getPlaylistDirectory());
  }

}

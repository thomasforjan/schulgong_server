package at.schulgong.controller;

import at.schulgong.dto.ConfigurationDTO;
import at.schulgong.speaker.api.PlayRingtones;
import at.schulgong.util.Config;
import at.schulgong.util.ReadWriteConfigurationFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @implNote Controller to provide CRUD-functionality
 * @since May 2023
 */
@RestController
@RequestMapping("settings")
@CrossOrigin
public class SettingsController {

  private final PlayRingtones playRingtones;

  /**
   * Controller of Settings
   */
  public SettingsController(PlayRingtones playRingtones) {
    this.playRingtones = playRingtones;
  }

  /**
   * Get data from the configuration file
   *
   * @return isPlayingAlarm flag
   */
  @GetMapping
  public ResponseEntity<ConfigurationDTO> getConfiguration() {
    return ResponseEntity.ok(ReadWriteConfigurationFile.readConfigurationDTOFromConfigFile(Config.CONFIGURATION_PATH.getPath()));
  }

  /**
   * Set ringtime volume
   *
   * @return response entity
   */
  @PostMapping("volume/ringtime/{volume}")
  public ResponseEntity saveRingtimeVolume(@PathVariable int volume) {
    ConfigurationDTO configurationDTO = ReadWriteConfigurationFile.readConfigurationDTOFromConfigFile(Config.CONFIGURATION_PATH.getPath());
    if(configurationDTO != null) {
      configurationDTO.setRingtimeVolume(volume);
      try {
        ReadWriteConfigurationFile.writeConfigurationDTOFromConfigFile(Config.CONFIGURATION_PATH.getPath(), configurationDTO);
        playRingtones.setConfigurationDTO(configurationDTO);
      } catch (RuntimeException e) {
        ResponseEntity.internalServerError().build();
      }
    }else {
      ResponseEntity.internalServerError().build();
    }
    return ResponseEntity.ok().build();
  }

  /**
   * Set alarm volume
   *
   * @return response entity
   */
  @PostMapping("volume/alarm/{volume}")
  public ResponseEntity saveAlarmVolume(@PathVariable int volume) {
    ConfigurationDTO configurationDTO = ReadWriteConfigurationFile.readConfigurationDTOFromConfigFile(Config.CONFIGURATION_PATH.getPath());
    if(configurationDTO != null) {
      configurationDTO.setAlarmVolume(volume);
      try {
        ReadWriteConfigurationFile.writeConfigurationDTOFromConfigFile(Config.CONFIGURATION_PATH.getPath(), configurationDTO);
        playRingtones.setConfigurationDTO(configurationDTO);
      } catch (RuntimeException e) {
        ResponseEntity.internalServerError().build();
      }
    }else {
      ResponseEntity.internalServerError().build();
    }
    return ResponseEntity.ok().build();
  }

  /**
   * Set announcement volume
   *
   * @return response entity
   */
  @PostMapping("volume/announcement/{volume}")
  public ResponseEntity saveAnnouncementVolume(@PathVariable int volume) {
    ConfigurationDTO configurationDTO = ReadWriteConfigurationFile.readConfigurationDTOFromConfigFile(Config.CONFIGURATION_PATH.getPath());
    if(configurationDTO != null) {
      configurationDTO.setAnnouncementVolume(volume);
      try {
        ReadWriteConfigurationFile.writeConfigurationDTOFromConfigFile(Config.CONFIGURATION_PATH.getPath(), configurationDTO);
        playRingtones.setConfigurationDTO(configurationDTO);
      } catch (RuntimeException e) {
        ResponseEntity.internalServerError().build();
      }
    }else {
      ResponseEntity.internalServerError().build();
    }
    return ResponseEntity.ok().build();
  }

}

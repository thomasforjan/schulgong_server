package at.schulgong.controller;

import at.schulgong.dto.ConfigurationDTO;
import at.schulgong.speaker.api.PlayRingtones;
import at.schulgong.util.Config;
import at.schulgong.util.ReadWriteConfigurationFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @implNote Controller to provide CRUD-functionality
 * @since May 2023
 */
@RestController
@RequestMapping("api/settings")
@CrossOrigin
public class SettingsController {
  Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder(16, 32, 1, 60000, 10);
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
    if (configurationDTO != null) {
      configurationDTO.setRingtimeVolume(volume);
      try {
        ReadWriteConfigurationFile.writeConfigurationDTOFromConfigFile(Config.CONFIGURATION_PATH.getPath(), configurationDTO);
        playRingtones.setConfigurationDTO(configurationDTO);
      } catch (RuntimeException e) {
        ResponseEntity.internalServerError().build();
      }
    } else {
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
    if (configurationDTO != null) {
      configurationDTO.setAlarmVolume(volume);
      try {
        ReadWriteConfigurationFile.writeConfigurationDTOFromConfigFile(Config.CONFIGURATION_PATH.getPath(), configurationDTO);
        playRingtones.setConfigurationDTO(configurationDTO);
      } catch (RuntimeException e) {
        ResponseEntity.internalServerError().build();
      }
    } else {
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
    if (configurationDTO != null) {
      configurationDTO.setAnnouncementVolume(volume);
      try {
        ReadWriteConfigurationFile.writeConfigurationDTOFromConfigFile(Config.CONFIGURATION_PATH.getPath(), configurationDTO);
        playRingtones.setConfigurationDTO(configurationDTO);
      } catch (RuntimeException e) {
        ResponseEntity.internalServerError().build();
      }
    } else {
      ResponseEntity.internalServerError().build();
    }
    return ResponseEntity.ok().build();
  }

  /**
   * POST request to update the password
   *
   * @param payload Map with the current password, the new password and the confirm new password
   * @return ResponseEntity with the status code and a message
   */
  @PostMapping("/updatePassword")
  public ResponseEntity updatePassword(@RequestBody Map<String, String> payload) {
    String currentPassword = payload.get("currentPassword");
    String newPassword = payload.get("newPassword");
    String confirmNewPassword = payload.get("confirmNewPassword");

    ConfigurationDTO configurationDTO = ReadWriteConfigurationFile.readConfigurationDTOFromConfigFile(Config.CONFIGURATION_PATH.getPath());

    // Check if the current password is correct
    if (configurationDTO != null && !argon2PasswordEncoder.matches(currentPassword, configurationDTO.getPassword())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Aktuelles Passwort ist falsch!");
    }

    // Check if the new passwort is equal to the confirm new password
    if (!newPassword.equals(confirmNewPassword)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwörter stimmen nicht überein!");
    }

    // Check if the new password is at least 6 characters long
    if (newPassword.length() < 6) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwort muss mindestens 6 Zeichen lang sein.");
    }

    // Continue with updating the password
    if (configurationDTO != null) {
      String hashedPassword = argon2PasswordEncoder.encode(newPassword);
      configurationDTO.setPassword(hashedPassword);

      try {
        ReadWriteConfigurationFile.writeConfigurationDTOFromConfigFile(Config.CONFIGURATION_PATH.getPath(), configurationDTO);
      } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    return ResponseEntity.status(200).body("Passwort wurde erfolgreich aktualisiert!");
  }
}

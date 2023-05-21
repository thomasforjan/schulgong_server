package at.schulgong.controller;

import at.schulgong.speaker.api.PlayRingtones;
import at.schulgong.util.AudioConverter;
import at.schulgong.util.Config;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @implNote Controller to provide CRUD-functionality
 * @since May 2023
 */
@RestController
@RequestMapping("live")
@CrossOrigin
public class LiveController {
  private final PlayRingtones playRingtones;

  /**
   * Controller of Live
   *
   * @param playRingtones
   */
  public LiveController(PlayRingtones playRingtones) {
    this.playRingtones = playRingtones;
  }

  /**
   * Get isPlayingAlarm flag.
   *
   * @return isPlayingAlarm flag
   */
  @GetMapping("alarm/isplaying")
  public ResponseEntity<Boolean> alarmIsPlaying() {
    return ResponseEntity.ok(playRingtones.isPlayingAlarm());
  }

  /**
   * Start or stop playing the alarm.
   *
   * @param isPlayingAlarm flag if alarm has to start or stop
   * @return no Content
   */
  @PostMapping("alarm")
  ResponseEntity alarmStartStopPlaying(@RequestBody boolean isPlayingAlarm) {
    if (isPlayingAlarm) {
      playRingtones.playAlarm();
    } else {
      playRingtones.stopAlarm();
    }
    return ResponseEntity.noContent().build();
  }

  /**
   * Play announcement.
   *
   * @param announcement takes announcement as byte array
   * @return no Content
   */
  @PostMapping(consumes = "audio/webm")
  ResponseEntity liveAnnouncement(@RequestBody byte[] announcement) {
    try {
      Path filePath = Paths.get(Config.ANNOUNCEMENT_PATH_WEBA_FORMAT.getPath());
      File outputFile = new File(filePath.toString());
      try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
        outputStream.write(announcement);
      }
    } catch (Exception e) {
    }
    AudioConverter.convertWebAToMP3(Config.ANNOUNCEMENT_PATH_WEBA_FORMAT.getPath(), Config.ANNOUNCEMENT_PATH.getPath());

    System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTT");
    playRingtones.playAnnouncement();
    return ResponseEntity.noContent().build();
  }

}

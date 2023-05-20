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

  //  /**
  //   * Get isPlayingAlarm flag.
  //   *
  //   * @return isPlayingAlarm flag
  //   */
  //  @GetMapping("alarm/isplaying")
  //  public LiveDTO alarmIsPlaying() {
  //    return LiveDTO.builder().isPlayingAlarm(playRingtones.isPlayingAlarm()).build();
  //  }

  /**
   * Get isPlayingAlarm flag.
   *
   * @return isPlayingAlarm flag
   */
  @GetMapping("alarm/isplaying")
  public ResponseEntity<Boolean> alarmIsPlaying() {
    System.out.println(
      "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    System.out.println(playRingtones.isPlayingAlarm());
    return ResponseEntity.ok(playRingtones.isPlayingAlarm());
  }

  /**
   * Add new sensor.
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
}

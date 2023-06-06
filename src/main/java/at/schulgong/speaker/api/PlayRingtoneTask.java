package at.schulgong.speaker.api;

import at.schulgong.dto.RingtoneDTO;
import at.schulgong.speaker.util.SpeakerCommand;
import lombok.NoArgsConstructor;

import java.util.TimerTask;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote Task to execute speaker control at a specific time
 * @since May 2023
 */
@NoArgsConstructor
public class PlayRingtoneTask extends TimerTask {

  private PlayRingtones playRingtones;

  private RingtoneDTO ringtoneDTO;

  public PlayRingtoneTask(RingtoneDTO ringtoneDTO, PlayRingtones playRingtones) {
    this.ringtoneDTO = ringtoneDTO;
    this.playRingtones = playRingtones;
  }

  /**
   * Run method to execute speaker control at specific time
   */
  @Override
  public void run() {
    String[] argsList = {
      SpeakerCommand.PLAY_URI.getCommand(),
      convertPath(this.ringtoneDTO.getPath())
    };
    playRingtones.executeSpeakerAction(argsList);
    playRingtones.setPlayingFromQueue(false);
    playRingtones.setPlayingPlaylist(false);
  }

  /**
   * Convert a path in the correct form
   *
   * @param path path of ringtone
   * @return Path
   */
  public static String convertPath(String path) {
    String os = System.getProperty("os.name");
    if (os.contains("Windows")) {
      path = path.replace("\\", "/");
      String[] pathArray = path.split(":");
      if (pathArray.length > 1) {
        path = pathArray[1];
      }
    }
    return path;
  }
}
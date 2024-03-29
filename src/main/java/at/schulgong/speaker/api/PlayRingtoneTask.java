package at.schulgong.speaker.api;

import at.schulgong.dto.RingtoneDTO;
import at.schulgong.speaker.util.SpeakerCommand;
import java.util.TimerTask;
import lombok.NoArgsConstructor;

/**
 * Task to execute speaker control at a specific time
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
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

    /** Run method to execute speaker control at specific time */
    @Override
    public void run() {
        String[] argsList = {
            SpeakerCommand.PLAY_URI_VOLUME_MUTE.getCommand(),
            convertPath(this.ringtoneDTO.getPath()),
            playRingtones.isPlayingAlarm()
                    ? String.valueOf(playRingtones.getConfigurationDTO().getAlarmVolume())
                    : String.valueOf(playRingtones.getConfigurationDTO().getRingtimeVolume()),
            "False"
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
                path = "x-file-cifs://DESKTOP-Q0STA8D" + pathArray[1];
            }
        } else {
            path = "x-file-cifs://schulgong/" + path;
        }
        return path;
    }
}

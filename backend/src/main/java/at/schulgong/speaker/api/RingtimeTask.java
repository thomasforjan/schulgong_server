package at.schulgong.speaker.api;

import at.schulgong.dto.RingtimeDTO;
import at.schulgong.speaker.util.SpeakerCommand;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class RingtimeTask extends TimerTask {

    private PlayRingtones playRingtones;

    private RingtimeDTO ringtimeDTO;
    private String[] args;
    private AtomicBoolean cancel = new AtomicBoolean(false);

    public AtomicBoolean getCancel() {
        return cancel;
    }

    public void setCancel(AtomicBoolean cancel) {
        this.cancel = cancel;
    }

    public RingtimeTask() {}

    public RingtimeTask(RingtimeDTO ringtimeDTO, PlayRingtones playRingtones) {
        this.ringtimeDTO = ringtimeDTO;
        this.playRingtones = playRingtones;
    }

    @Override
    public void run() {
        String[] argsList = {
            SpeakerCommand.PLAY_URI.getCommand(),
            convertPath(this.ringtimeDTO.getRingtoneDTO().getPath())
        };
        playRingtones.executeSpeakerAction(argsList);
    }

    public PlayRingtones getPlayRingtones() {
        return playRingtones;
    }

    public void setPlayRingtones(PlayRingtones playRingtones) {
        this.playRingtones = playRingtones;
    }

    public RingtimeDTO getRingtimeDTO() {
        return ringtimeDTO;
    }

    public void setRingtimeDTO(RingtimeDTO ringtimeDTO) {
        this.ringtimeDTO = ringtimeDTO;
    }

    private String convertPath(String path) {
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

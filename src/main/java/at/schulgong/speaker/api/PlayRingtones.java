package at.schulgong.speaker.api;

import at.schulgong.dto.HolidayDTO;
import at.schulgong.dto.PlaylistSongDTO;
import at.schulgong.dto.RingtimeDTO;
import at.schulgong.dto.RingtoneDTO;
import at.schulgong.speaker.util.ReadSettingFile;
import at.schulgong.speaker.util.Setting;
import at.schulgong.speaker.util.SpeakerActionStatus;
import at.schulgong.speaker.util.SpeakerCommand;
import at.schulgong.util.Config;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @implNote Class to playing ringtones
 * @since May 2023
 */
@Configuration
public class PlayRingtones {

  @Autowired
  private PlayRingtonesService playRingtonesService;
  private Timer timer;
  private List<RingtimeDTO> ringtimeDTOList;
  private List<PlayRingtoneTask> playRingtoneTaskList;
  private PlayRingtoneTask playAlarmTask;
  private Setting setting;
  private boolean isPlayingAlarm;
  private boolean isPlayingAnnouncement;


  @Bean
  @ApplicationScope
  public PlayRingtones playRingtonesBean() {
    return new PlayRingtones();
  }

  /**
   * Loading ring times from database
   */
  public void loadRingtimes() {
    ringtimeDTOList = new ArrayList<>();
    if (!playRingtonesService.isHolidayAtCurrentDate()) {
      System.out.println("NO HOLIDAYS");
      ringtimeDTOList = playRingtonesService.findRingtimeForCurrentDateAndWeekday();
    }
  }

  /**
   * Initial method to initialize all variables
   */
  @PostConstruct
  public void init() {
    setting = ReadSettingFile.getSettingFromConfigFile();
    timer = new Timer();
    runEveryDayTask();
    playRingtoneTaskList = new ArrayList<>();
    System.out.println("POST CONSTRACT");
    playRingtonesFromRingtimes();
  }

  /**
   * Start the tasks for play ringtones from ring times
   */
  public void playRingtonesFromRingtimes() {
    if (ringtimeDTOList != null && !ringtimeDTOList.isEmpty()) {
      LocalDateTime ldtActualPlayTime = LocalDateTime.of(LocalDate.now(), ringtimeDTOList.get(0).getPlayTime());
      if (LocalDateTime.now().isAfter(ldtActualPlayTime)) {
        ringtimeDTOList.remove(0);
      }
      for (RingtimeDTO ringtime : ringtimeDTOList) {
        ldtActualPlayTime = LocalDateTime.of(LocalDate.now(), ringtime.getPlayTime());
        startRingtimeTask(ldtActualPlayTime, ringtime);
      }
    }
  }

  /**
   * Restarts the tasks for playing ringtones
   */
  public void restart() {
    if (!isPlayingAlarm && !isPlayingAnnouncement) {
      System.out.println("RESTART");
      stopTasks();
      loadRingtimes();
      playRingtonesFromRingtimes();
    }
  }

  /**
   * Stop all tasks
   */
  private void stopTasks() {
    if (playRingtoneTaskList != null && !playRingtoneTaskList.isEmpty()) {
      for (PlayRingtoneTask task : playRingtoneTaskList) {
        task.cancel();
      }
      playRingtoneTaskList = new ArrayList<>();
    }
  }

  /**
   * Run script for control a specific command on the network speakers
   *
   * @param argsList Array containing speaker command inclusive arguments
   * @return Output from the executed script in form of SpeakerActionStatus
   */
  public SpeakerActionStatus executeSpeakerAction(String[] argsList) {
    return SpeakerApi.runSpeakerApi(argsList);
  }

  /**
   * Start ring time task at a specific date time
   *
   * @param ldtPlayTime Date time for playing a ringtone
   * @param ringtimeDTO Ring time including the ringtone
   */
  private void startRingtimeTask(LocalDateTime ldtPlayTime, RingtimeDTO ringtimeDTO) {
    Date date = Date.from(ldtPlayTime.atZone(ZoneId.systemDefault()).toInstant());
    try {
      PlayRingtoneTask ringtimeTask = new PlayRingtoneTask(ringtimeDTO.getRingtoneDTO(), this);
      playRingtoneTaskList.add(ringtimeTask);
      timer.schedule(ringtimeTask, date);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Checks if the ring times have to be reloaded
   *
   * @param ringtimeDTO New, updated or deleted ring time
   * @return True if the ring times have to reload
   */
  public boolean checkLoadRingtimes(RingtimeDTO ringtimeDTO) {
    LocalDate ld = LocalDate.now();
    LocalDate startDate = ringtimeDTO.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate endDate = ringtimeDTO.getEndDate() != null ? ringtimeDTO.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
    if ((ld.isEqual(startDate) || ld.isAfter(startDate)) && (endDate == null || ld.isEqual(endDate) || ld.isBefore(endDate))) {
      DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
      switch (dayOfWeek) {
        case MONDAY -> {
          return ringtimeDTO.isMonday();
        }
        case TUESDAY -> {
          return ringtimeDTO.isTuesday();
        }
        case WEDNESDAY -> {
          return ringtimeDTO.isWednesday();
        }
        case THURSDAY -> {
          return ringtimeDTO.isThursday();
        }
        case FRIDAY -> {
          return ringtimeDTO.isFriday();
        }
        case SATURDAY -> {
          return ringtimeDTO.isSaturday();
        }
        case SUNDAY -> {
          return ringtimeDTO.isSunday();
        }
      }
    }
    return false;
  }

  /**
   * Check if holidays have to be reloaded
   *
   * @param holidayDTO New, updated or deleted holiday
   * @return True if the holidays have to reload
   */
  public boolean checkLoadHoliday(HolidayDTO holidayDTO) {
    LocalDate ld = LocalDate.now();
    return (ld.isEqual(holidayDTO.getStartDate()) || ld.isAfter(holidayDTO.getStartDate())) && (ld.isEqual(holidayDTO.getEndDate()) || ld.isBefore(holidayDTO.getEndDate()));
  }

  /**
   * Task which run one time every day to load the ring time for the actual day
   */
  private void runEveryDayTask() {
    if (!isPlayingAlarm && !isPlayingAnnouncement) {
      LocalDateTime ldt = LocalDateTime.of(LocalDate.now(), setting.getLoadRingtimeTime());
      Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
      long period = 1000L * 60L * 60L * 24L;
      timer.scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
          restart();
        }
      }, date, period);
    }
  }

  /**
   * Start playing the alarm
   */
  public void playAlarm() {
    isPlayingAlarm = true;
    stopTasks();
    RingtoneDTO ringtoneDTO = playRingtonesService.getRingtoneAlarm();
    System.out.println(ringtoneDTO.getPath());
    if (ringtoneDTO != null) {
      runAlarmTask(ringtoneDTO);
    }
  }

  /**
   * Stop the alarm
   */
  public void stopAlarm() {
    isPlayingAlarm = false;
    playAlarmTask.cancel();
    String[] argsListStop = {SpeakerCommand.STOP.getCommand(),};
    executeSpeakerAction(argsListStop);
    restart();
  }

  /**
   * Task which run the task for playing the alarm
   */
  private void runAlarmTask(RingtoneDTO ringtoneDTO) {
    try {
      playAlarmTask = new PlayRingtoneTask(ringtoneDTO, this);
      LocalDateTime ldt = LocalDateTime.now();
      Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
      long duration = getDurationOfMusicFile(ringtoneDTO.getPath());
      timer.scheduleAtFixedRate(playAlarmTask, date, duration);
    } catch (EncoderException e) {
      e.printStackTrace();
    }
  }

  public void playAnnouncement() {
    isPlayingAnnouncement = true;
    stopTasks();
    if (isPlayingAlarm) {
      playAlarmTask.cancel();
    }
    executePlayAnnouncement();
    runActionsAfterAnnouncement();
  }

  private void executePlayAnnouncement() {
    String[] argsListPlayAlarm = {SpeakerCommand.PLAY_URI.getCommand(), PlayRingtoneTask.convertPath(Config.ANNOUNCEMENT_PATH.getPath())};
    executeSpeakerAction(argsListPlayAlarm);
  }

  private void runActionsAfterAnnouncement() {
    try {
      timer.schedule(new TimerTask() {
        @Override
        public void run() {
          if (isPlayingAlarm) {
            playAlarm();
          } else {
            restart();
          }
          isPlayingAnnouncement = false;
        }
      }, getDurationOfMusicFile(Config.ANNOUNCEMENT_PATH.getPath()));
    } catch (EncoderException e) {
      e.printStackTrace();
    }
  }

  /**
   * Get the duration of the music file
   *
   * @param filePath of the music file
   * @return duration of the music file
   * @throws EncoderException
   */
  private long getDurationOfMusicFile(String filePath) throws EncoderException {
    File alarmFile = new File(filePath);
    MultimediaObject mmo = new MultimediaObject(alarmFile);
    return mmo.getInfo().getDuration();
  }

  /**
   * Getter for the attribute isPlayingAlarm
   *
   * @return isPlayingAlarm
   */
  public boolean isPlayingAlarm() {
    return isPlayingAlarm;
  }

  public void setPlaylist(List<PlaylistSongDTO> playlistSongDTOList) {
    playlistSongDTOList.sort(Comparator.comparingLong(PlaylistSongDTO::getIndex));
    String[] argsList = new String[]{SpeakerCommand.CLEAR_QUEUE.getCommand()};
    executeSpeakerAction(argsList);
    for (PlaylistSongDTO playlistSongDTO : playlistSongDTOList) {
      argsList = new String[]{SpeakerCommand.ADD_URI_TO_QUEUE.getCommand(), PlayRingtoneTask.convertPath(playlistSongDTO.getFilePath())};
      executeSpeakerAction(argsList);
    }
  }
}

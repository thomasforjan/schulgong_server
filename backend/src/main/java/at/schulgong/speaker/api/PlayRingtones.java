package at.schulgong.speaker.api;

import at.schulgong.dto.HolidayDTO;
import at.schulgong.dto.RingtimeDTO;
import at.schulgong.speaker.util.ReadSettingFile;
import at.schulgong.speaker.util.Setting;
import at.schulgong.speaker.util.SpeakerActionStatus;
import jakarta.annotation.PostConstruct;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;

@Configuration
// @Component
// @ApplicationScope
// @Service
public class PlayRingtones {

  @Autowired
  private PlayRingtonesService playRingtonesService;
  private Timer timer;
  private List<RingtimeDTO> ringtimeDTOList;

  private List<RingtimeTask> ringtimeTaskList;

  private Setting setting;

  @Bean
  @ApplicationScope
  public PlayRingtones playRingtonesBean() {
    return new PlayRingtones();
  }

  private static RingtimeTask ringtimeTask;

  public void loadRingtimes() {
    ringtimeDTOList = new ArrayList<>();
    if (playRingtonesService.getCountOfHolidayAtCurrentDate() == 0) {
      System.out.println("NO HOLIDAYS");
      ringtimeDTOList = playRingtonesService.findRingtimeForCurrentDateAndWeekday();
    }
  }

  @PostConstruct
  public void init() {
    setting = ReadSettingFile.getSettingFromConfigFile();
    timer = new Timer();
    runEveryDayTask();
    ringtimeTaskList = new ArrayList<>();
    System.out.println("POST CONSTRACT");
    playRingtonesFromRingtimes();
  }

  public void playRingtonesFromRingtimes() {
    if (ringtimeDTOList != null && ringtimeDTOList.size() > 0) {
      LocalDateTime ldtActualPlayTime = LocalDateTime.of(LocalDate.now(), ringtimeDTOList.get(0).getPlayTime());
      if (ldtActualPlayTime != null && LocalDateTime.now().isAfter(ldtActualPlayTime)) {
        ringtimeDTOList.remove(0);
      }
      for (RingtimeDTO ringtime : ringtimeDTOList) {
        ldtActualPlayTime = LocalDateTime.of(LocalDate.now(), ringtime.getPlayTime());
        startRingtimeTask(ldtActualPlayTime, ringtime);
      }
    }
  }

  public void stop() {
    if (timer != null) {
      timer.cancel();
    }
  }

  public void start() {
    timer = new Timer();
  }

  public void restart() {
    System.out.println("RESTART");
    stopTasks();
    loadRingtimes();
    playRingtonesFromRingtimes();
  }

  private void stopTasks() {
    if (ringtimeTaskList != null && ringtimeTaskList.size() > 0) {
      for (RingtimeTask task : ringtimeTaskList) {
        task.cancel();
      }
      ringtimeTaskList = new ArrayList<>();
    }
  }

  public SpeakerActionStatus executeSpeakerAction(String[] argsList) {
    return SpeakerApi.runSpeakerApi(argsList);
  }

  private void startRingtimeTask(LocalDateTime ldtPlayTime, RingtimeDTO ringtimeDTO) {
    Date date = Date.from(ldtPlayTime.atZone(ZoneId.systemDefault()).toInstant());
    try {
      RingtimeTask ringtimeTask = new RingtimeTask(ringtimeDTO, this);
      ringtimeTaskList.add(ringtimeTask);
      timer.schedule(ringtimeTask, date);
    } catch (Exception e) {
    }
  }

  public boolean checkLoadRingtimes(RingtimeDTO ringtimeDTO) {
    LocalDate ld = LocalDate.now();
    LocalDate startDate =
      ringtimeDTO.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate endDate =
      ringtimeDTO.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    if ((ld.isEqual(startDate) || ld.isAfter(startDate)) && (ld.isEqual(endDate) || ld.isBefore(endDate))) {
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

  public boolean checkLoadHoliday(HolidayDTO holidayDTO) {
    LocalDate ld = LocalDate.now();
    if ((ld.isEqual(holidayDTO.getStartDate()) || ld.isAfter(holidayDTO.getStartDate()))
      && (ld.isEqual(holidayDTO.getEndDate()) || ld.isBefore(holidayDTO.getEndDate()))) {
      return true;
    }
    return false;
  }

  private void runEveryDayTask() {
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

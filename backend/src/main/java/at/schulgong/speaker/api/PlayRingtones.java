package at.schulgong.speaker.api;

import at.schulgong.dto.RingtimeDTO;
import at.schulgong.speaker.util.SpeakerActionStatus;
import jakarta.annotation.PostConstruct;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;

@Configuration
// @Component
// @ApplicationScope
// @Service
public class PlayRingtones implements Runnable {

    @Autowired private PlayRingtonesService ringTimeRepository;

    private Thread worker;
    private static final AtomicBoolean running = new AtomicBoolean(false);
    private static final AtomicBoolean interrupt = new AtomicBoolean(false);
    private static Timer timer;
    private static List<RingtimeDTO> ringtimeDTOList;

    @Bean
    @ApplicationScope
    public PlayRingtones playRingtonesBean() {
        return new PlayRingtones();
    }

    private static RingtimeTask ringtimeTask;

    public void loadRingtimes() {
        ringtimeDTOList = new ArrayList<>();
        ringtimeDTOList = ringTimeRepository.testMethod();
    }

    @PostConstruct
    public void init() {
        System.out.println("POST CONSTRACT");
        loadRingtimes();
        playRingtonesFromRingtimes();
    }

    public void playRingtonesFromRingtimes() {
        worker = new Thread(this);
        worker.start();
    }

    public void stop() {
        interrupt.set(true);
        running.set(false);
        if (timer != null) {
            timer.cancel();
        }
    }

    public void restart() {
        System.out.println("RESTART");
        stop();
        loadRingtimes();
        playRingtonesFromRingtimes();
    }

    @Override
    public void run() {
        running.set(false);
        LocalDateTime ldtActualPlayTime =
                LocalDateTime.of(LocalDate.now(), ringtimeDTOList.get(0).getPlayTime());
        //        do {
        if (ringtimeDTOList != null && ringtimeDTOList.size() > 0) {
            if (ldtActualPlayTime != null
                    && (!running.get() && LocalDateTime.now().isBefore(ldtActualPlayTime)
                            || running.get() && LocalDateTime.now().isAfter(ldtActualPlayTime))) {
                ldtActualPlayTime =
                        LocalDateTime.of(LocalDate.now(), ringtimeDTOList.get(0).getPlayTime());
                startTimer(ldtActualPlayTime, ringtimeDTOList.get(0));
                ringtimeDTOList.remove(0);
            }
        } else {
            running.set(false);
        }
        //        } while (running.get() && !interrupt.get());
    }

    public SpeakerActionStatus executeSpeakerAction(String[] argsList) {
        return SpeakerApi.runSpeakerApi(argsList);
    }

    private void startTimer(LocalDateTime ldtPlayTime, RingtimeDTO ringtimeDTO) {
        Date date = Date.from(ldtPlayTime.atZone(ZoneId.systemDefault()).toInstant());
        try {
            timer = new Timer();
            timer.schedule(new RingtimeTask(ringtimeDTO, this), date);
            running.set(true);
        } catch (Exception e) {
        }
    }

    public boolean checkLoadRingtimes(RingtimeDTO ringtimeDTO) {
        LocalDate ld = LocalDate.now();
        LocalDate startDate =
                ringtimeDTO.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate =
                ringtimeDTO.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (ld.isAfter(startDate) && ld.isBefore(endDate)) {
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
}

package at.schulgong.speaker.api;

import at.schulgong.dto.RingtimeDTO;
import at.schulgong.dto.RingtoneDTO;
import at.schulgong.model.Holiday;
import at.schulgong.model.Ringtime;
import at.schulgong.model.Ringtone;
import at.schulgong.repository.HolidayRepository;
import at.schulgong.util.DtoConverter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote Service class to get specific data from database
 * @since May 2023
 */
@Service
public class PlayRingtonesService {

  private final HolidayRepository holidayRepository;
  @PersistenceContext
  private EntityManager entityManager;

  public PlayRingtonesService(HolidayRepository holidayRepository) {
    this.holidayRepository = holidayRepository;
  }

  /**
   * Get the ring times for the current date and weekday
   *
   * @return List of RingtimeDTO for the current date and weekday
   */
  public List<RingtimeDTO> findRingtimeForCurrentDateAndWeekday() {
    List<RingtimeDTO> ringtimeDTOList = new ArrayList<>();

    StringBuilder query =
      new StringBuilder(
        "SELECT r FROM Ringtime r WHERE CURDATE() between r.startDate and"
          + " IFNULL(r.endDate, CURDATE()) and ");
    switch (LocalDate.now().getDayOfWeek()) {
      case MONDAY -> query.append("r.monday = true");
      case TUESDAY -> query.append("r.tuesday = true");
      case WEDNESDAY -> query.append("r.wednesday = true");
      case THURSDAY -> query.append("r.thursday = true");
      case FRIDAY -> query.append("r.friday = true");
      case SATURDAY -> query.append("r.saturday = true");
      case SUNDAY -> query.append("r.sunday = true");
    }
    query.append(" ORDER BY r.hour.hour, r.minute.minute");
    TypedQuery<Ringtime> typedQuery =
      entityManager.createQuery(query.toString(), Ringtime.class);
    List<Ringtime> ringtimeList = typedQuery.getResultList();
    if (ringtimeList != null && !ringtimeList.isEmpty()) {
      for (Ringtime r : ringtimeList) {
        if (LocalTime.now()
          .isBefore(LocalTime.of(r.getHour().getHour(), r.getMinute().getMinute()))) {
          ringtimeDTOList.add(DtoConverter.convertRingtimeToDTO(r, true));
          System.out.println(r);
        }
      }
    }
    return ringtimeDTOList;
  }

  /**
   * Check if the current date is a holiday
   *
   * @return True if today is a holiday
   */
  public boolean isHolidayAtCurrentDate() {
    List<Holiday> holidayList = holidayRepository.findHolidaysAtCurrentDate();
    return holidayList != null && !holidayList.isEmpty();
  }

  /**
   * Get ringtone alarm from database
   *
   * @return ringtoneDTO
   */
  public RingtoneDTO getRingtoneAlarm() {
    RingtoneDTO ringtoneDTO = null;
    TypedQuery<Ringtone> typedQuery =
      entityManager.createQuery(
        "SELECT r FROM Ringtone r WHERE r.name = 'Alarm'", Ringtone.class);
    Ringtone ringtone = typedQuery.getSingleResult();
    if (ringtone != null) {
      ringtoneDTO = DtoConverter.convertRingtoneToDTO(ringtone);
    }
    return ringtoneDTO;
  }

}

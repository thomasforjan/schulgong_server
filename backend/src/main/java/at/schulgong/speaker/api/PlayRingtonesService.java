package at.schulgong.speaker.api;

import at.schulgong.Holiday;
import at.schulgong.Ringtime;
import at.schulgong.dto.RingtimeDTO;
import at.schulgong.repository.HolidayRepository;
import at.schulgong.repository.RingtimeRepository;
import at.schulgong.util.DtoConverter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PlayRingtonesService {

  private HolidayRepository holidayRepository;

  public PlayRingtonesService(HolidayRepository holidayRepository) {
    this.holidayRepository = holidayRepository;
  }

  @PersistenceContext
  private EntityManager entityManager;

//  public List<RingtimeDTO> testMethod() {
//    List<RingtimeDTO> ringtimeDTOList = new ArrayList<>();
//    List<Ringtime> ringtimesList = this.ringTimeRepository.findAll();
//    for (Ringtime ringtime : ringtimesList) {
//      RingtimeDTO ringtimeDTO = DtoConverter.convertRingtimeToDTO(ringtime, true);
//      ringtimeDTOList.add(ringtimeDTO);
//    }
//    return ringtimeDTOList;
//  }

  //  SELECT * FROM schulgong.ringtime r WHERE CURDATE() between r.startdate and r.enddate
  //  and IF(WEEKDAY(CURDATE()) = 0, r.monday = 1, IF(WEEKDAY(CURDATE()) = 1, r.tuesday = 1,
  // IF(WEEKDAY(CURDATE()) = 2, r.wednesday = 1, IF(WEEKDAY(CURDATE()) = 3, r.thursday = 1,
  // IF(WEEKDAY(CURDATE()) = 4, r.friday = 1, IF(WEEKDAY(CURDATE()) = 0, r.saturday = 1, r.sunday
  // = 1))))));

  public List<RingtimeDTO> findRingtimeForCurrentDateAndWeekday() {
    List<RingtimeDTO> ringtimeDTOList = new ArrayList<>();

    StringBuilder query = new StringBuilder("SELECT r FROM Ringtime r WHERE CURDATE() between r.startDate and IFNULL(r.endDate, CURDATE()) and ");
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
    TypedQuery<Ringtime> typedQuery = entityManager.createQuery(query.toString(), Ringtime.class);
    List<Ringtime> ringtimeList = typedQuery.getResultList();
    if (ringtimeList != null && ringtimeList.size() > 0) {
      for (Ringtime r : ringtimeList) {
        if (LocalTime.now().isBefore(LocalTime.of(r.getHour().getHour(), r.getMinute().getMinute()))) {
          ringtimeDTOList.add(DtoConverter.convertRingtimeToDTO(r, true));
          System.out.println(r.toString());
        }
      }
    }
    return ringtimeDTOList;
  }

  public boolean isHolidayAtCurrentDate() {
    List<Holiday> holidayList = holidayRepository.findHolidaysAtCurrentDate();
    boolean isHoliday = false;
    if (holidayList != null && holidayList.size() > 0) {
      isHoliday = true;
    }
    return isHoliday;
  }

}

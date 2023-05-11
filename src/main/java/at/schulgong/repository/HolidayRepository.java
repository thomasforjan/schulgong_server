package at.schulgong.repository;

import at.schulgong.model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @implNote Repository of holiday
 * @since April 2023
 */
public interface HolidayRepository extends JpaRepository<Holiday, Long> {
  @Query("SELECT h FROM Holiday h WHERE CURDATE() BETWEEN h.startDate AND h.endDate")
  List<Holiday> findHolidaysAtCurrentDate();
}

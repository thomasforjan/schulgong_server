package at.schulgong.repository;

import at.schulgong.model.Holiday;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository of holiday
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @since April 2023
 */
public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    @Query("SELECT h FROM Holiday h WHERE CURDATE() BETWEEN h.startDate AND h.endDate")
    List<Holiday> findHolidaysAtCurrentDate();
}

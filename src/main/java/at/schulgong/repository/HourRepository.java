package at.schulgong.repository;

import at.schulgong.model.Hour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository of Hour
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @since April 2023
 */
public interface HourRepository extends JpaRepository<Hour, Long> {
    @Query("SELECT h FROM Hour h WHERE h.hour=:hour")
    Hour findByHour(@Param("hour") Integer hour);
}

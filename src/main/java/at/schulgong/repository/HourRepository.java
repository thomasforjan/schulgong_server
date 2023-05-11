package at.schulgong.repository;

import at.schulgong.model.Hour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @implNote Repository of Hour
 * @since April 2023
 */
public interface HourRepository extends JpaRepository<Hour, Long> {
  @Query("SELECT h FROM Hour h WHERE h.hour=:hour")
  Hour findByHour(@Param("hour") Integer hour);
}

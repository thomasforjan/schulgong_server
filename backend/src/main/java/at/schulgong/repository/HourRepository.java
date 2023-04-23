package at.schulgong.repository;

import at.schulgong.Hour;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote Repository of Hour
 * @since April 2023
 */
public interface HourRepository extends JpaRepository<Hour, Long> {
  public Hour findByHour(int hour);
}

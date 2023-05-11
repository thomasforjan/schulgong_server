package at.schulgong.repository;

import at.schulgong.Minute;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote Repository of Minute
 * @since April 2023
 */
public interface MinuteRepository extends JpaRepository<Minute, Long> {
  public Minute findByMinute(int minute);
}

package at.schulgong.repository;

import at.schulgong.Ringtone;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote Repository of Ringtone
 * @since April 2023
 */
public interface RingtoneRepository extends JpaRepository<Ringtone, Long> {
}

package at.schulgong.repository;

import at.schulgong.model.Ringtone;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @implNote Repository of Ringtone
 * @since April 2023
 */
public interface RingtoneRepository extends JpaRepository<Ringtone, Long> {
}

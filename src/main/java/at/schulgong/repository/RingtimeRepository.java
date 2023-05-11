package at.schulgong.repository;

import at.schulgong.model.Ringtime;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @implNote Repository of Ringtime
 * @since April 2023
 */
public interface RingtimeRepository extends JpaRepository<Ringtime, Long> {
}

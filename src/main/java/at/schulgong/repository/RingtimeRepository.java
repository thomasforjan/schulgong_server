package at.schulgong.repository;

import at.schulgong.Ringtime;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote Repository of Ringtime
 * @since April 2023
 */
public interface RingtimeRepository extends JpaRepository<Ringtime, Long> {
}

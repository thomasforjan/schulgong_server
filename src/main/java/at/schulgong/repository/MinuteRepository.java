package at.schulgong.repository;

import at.schulgong.model.Minute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository of Minute
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @since April 2023
 */
public interface MinuteRepository extends JpaRepository<Minute, Long> {
    @Query("SELECT m FROM Minute m WHERE m.minute=:minute")
    Minute findByMinute(@Param("minute") Integer minute);
}

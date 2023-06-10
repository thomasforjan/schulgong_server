package at.schulgong.repository;

import at.schulgong.model.Ringtone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @implNote Repository of Ringtone
 * @since April 2023
 */
public interface RingtoneRepository extends JpaRepository<Ringtone, Long> {

    @Query("SELECT r FROM Ringtone r WHERE r.filename = :filename ")
    Ringtone findRingtoneByFilename(@Param("filename") String filename);
}

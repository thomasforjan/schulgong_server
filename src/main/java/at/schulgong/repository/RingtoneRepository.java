package at.schulgong.repository;

import at.schulgong.model.Ringtone;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository of Ringtone
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @since April 2023
 */
public interface RingtoneRepository extends JpaRepository<Ringtone, Long> {

    @Query("SELECT r FROM Ringtone r WHERE r.filename = :filename ")
    Ringtone findRingtoneByFilename(@Param("filename") String filename);

    /**
     * Custom Query to get all Ringtones, which are able to delete
     *
     * @return List of deletable ringtones
     */
    @Query(
            "SELECT r FROM Ringtone r LEFT OUTER JOIN Ringtime rt ON rt.ringtone.id = r.id WHERE"
                    + " rt.ringtone.id IS NULL AND LOWER(r.name) != 'alarm' ")
    List<Ringtone> findAllDeletableRingtones();
}

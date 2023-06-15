package at.schulgong.repository;

import at.schulgong.model.Song;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @implNote Repository of playlist
 * @since May 2023
 */
public interface SongRepository extends JpaRepository<Song, Long> {

    @Query(
            "SELECT s FROM Song s  LEFT OUTER JOIN PlaylistSong ps ON ps.song.id = s.id WHERE"
                    + " ps.song.id IS NULL")
    List<Song> findAvailableSongs();

    @Query("SELECT s FROM Song s WHERE s.name = :name ")
    Song findSongByName(@Param("name") String name);
}

package at.schulgong.repository;

import at.schulgong.model.PlaylistSong;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @implNote Repository of playlist
 * @since May 2023
 */
public interface PlaylistSongRepository extends JpaRepository<PlaylistSong, Long> {

}

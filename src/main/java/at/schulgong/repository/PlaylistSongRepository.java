package at.schulgong.repository;

import at.schulgong.model.PlaylistSong;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of playlist
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @since May 2023
 */
public interface PlaylistSongRepository extends JpaRepository<PlaylistSong, Long> {}

package at.schulgong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO of PlaylistSong Object
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @since May 2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistSongDTO extends SongDTO {
    private long index;
}

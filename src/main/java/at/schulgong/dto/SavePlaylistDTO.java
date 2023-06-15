package at.schulgong.dto;

import java.util.List;
import lombok.*;

/**
 * DTO of Saved Playlist Object
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @since May 2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavePlaylistDTO {
    private boolean songListChanged;
    private List<PlaylistSongDTO> playlistSongDTOList;
    private List<SongDTO> actualSongList;
}

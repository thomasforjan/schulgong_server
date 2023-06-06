package at.schulgong.dto;

import lombok.*;

import java.util.List;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @implNote DTO of Saved Playlist Object
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

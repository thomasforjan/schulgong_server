package at.schulgong.dto;

import at.schulgong.speaker.util.SpeakerState;
import lombok.*;

import java.util.List;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @implNote DTO of Playlist Object
 * @since May 2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaylistDTO {
  private SpeakerState speakerState;
  private int volume;
  private boolean mute;
  private boolean looping;
  private boolean playingPlaylist;
  private PlaylistSongDTO actualSong;
  private List<PlaylistSongDTO> songDTOList;

}

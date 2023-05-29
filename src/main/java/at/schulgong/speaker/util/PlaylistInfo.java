package at.schulgong.speaker.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @implNote Infos about the playlist (model)
 * @since May 2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistInfo {
  private int volume;
  private boolean mute;
  private boolean playingFromQueue;
  private int position;
  private String speakerState;
}

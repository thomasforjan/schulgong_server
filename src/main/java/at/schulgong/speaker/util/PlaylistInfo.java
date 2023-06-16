package at.schulgong.speaker.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Infos about the playlist (model)
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
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

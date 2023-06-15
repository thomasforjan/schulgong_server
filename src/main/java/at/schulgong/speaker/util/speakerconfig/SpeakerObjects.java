package at.schulgong.speaker.util.speakerconfig;

import java.util.List;
import lombok.*;

/**
 * Setting for controlling network speakers
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @since May 2023
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpeakerObjects {

    private List<Speaker> speakerObjects;
}

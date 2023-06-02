package at.schulgong.speaker.util.speakerconfig;

import lombok.*;

import java.util.List;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote Setting for controlling network speakers
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

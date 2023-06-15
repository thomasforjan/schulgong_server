package at.schulgong.speaker.util.speakerconfig;

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
public class Speaker {

    private String name;
    private String ip_address;

    public Speaker(String name) {
        this.name = name;
    }
}

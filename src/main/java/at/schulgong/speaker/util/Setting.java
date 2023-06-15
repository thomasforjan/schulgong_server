package at.schulgong.speaker.util;

import java.time.LocalTime;
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
public class Setting {

    private String type;
    private String executedFilePath;
    private LocalTime loadRingtimeTime;
}

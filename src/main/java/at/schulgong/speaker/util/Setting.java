package at.schulgong.speaker.util;

import lombok.*;

import java.time.LocalTime;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote Setting for controlling network speakers
 * @since Mai 2023
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

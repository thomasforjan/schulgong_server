package at.schulgong.speaker.util;

import lombok.*;

import java.time.LocalTime;

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

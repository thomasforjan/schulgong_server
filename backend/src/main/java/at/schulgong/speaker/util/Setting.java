package at.schulgong.speaker.util;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Setting {

  private String type;
  private String executedFilePath;

}

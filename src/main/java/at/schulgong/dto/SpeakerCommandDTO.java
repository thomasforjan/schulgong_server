package at.schulgong.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpeakerCommandDTO {
  private String command;
  private String parameter;
}

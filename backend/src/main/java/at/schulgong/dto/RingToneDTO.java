package at.schulgong.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RingToneDTO extends RepresentationModel<RingToneDTO> {
  private long id;
  private String name, filename, path, date;
  private int size;
}

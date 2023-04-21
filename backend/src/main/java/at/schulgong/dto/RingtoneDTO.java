package at.schulgong.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RingtoneDTO extends RepresentationModel<RingtoneDTO> {
  private long id;
  private String name, filename, path;
  private LocalDate date;
  private double size;
}

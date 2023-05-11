package at.schulgong.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @implNote DTO of Ringtone Object
 * @since April 2023
 */
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
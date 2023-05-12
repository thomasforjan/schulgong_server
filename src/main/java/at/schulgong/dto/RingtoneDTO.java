package at.schulgong.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RingtoneDTO that)) return false;
    if (!super.equals(o)) return false;
    return id == that.id && Double.compare(that.size, size) == 0 && Objects.equals(name, that.name) && Objects.equals(filename, that.filename) && Objects.equals(path, that.path) && Objects.equals(date, that.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), id, name, filename, path, date, size);
  }
}

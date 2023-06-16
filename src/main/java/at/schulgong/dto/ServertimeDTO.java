package at.schulgong.dto;

import java.util.Objects;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

/**
 * DTO of server time Object
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.2
 * @since May 2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServertimeDTO extends RepresentationModel<ServertimeDTO> {
    private String time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServertimeDTO that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), time);
    }
}
